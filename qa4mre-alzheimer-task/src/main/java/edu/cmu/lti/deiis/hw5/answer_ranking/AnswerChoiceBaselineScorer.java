package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.FSCollectionFactory;
import org.uimafit.util.JCasUtil;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NSentence;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.utils.Utils;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.trees.semgraph.SemanticGraph;
import edu.stanford.nlp.trees.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.trees.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import edu.umass.cs.mallet.base.types.Vector;
import abner.Tagger;

public class AnswerChoiceBaselineScorer extends JCasAnnotator_ImplBase{

    // int K_CANDIDATES = 5;
    protected static ArrayList<String> connectors;

    HashSet<String> hshStopWords = new HashSet<String>();

    private StanfordCoreNLP stanfordAnnotator;

    Tagger abnerTagger = null;

    private TokenVector tokenVector;

    boolean useWordVector = false;
    boolean useSrl = false;

    @Override
    public void initialize(UimaContext context)
	    throws ResourceInitializationException {
	super.initialize(context);
	// K_CANDIDATES=(Integer)context.getConfigParameterValue("K_CANDIDATES");

	// connecting words for question/answer reconstruction
	connectors = new ArrayList<String>();
	connectors.add("do");
	connectors.add("does");
	connectors.add("did");
	connectors.add("is");
	connectors.add("was");
	connectors.add("are");

	// NLP annotator for recostructed sentences
	Properties props = new Properties();
	props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse");// ,
	// ssplit
	stanfordAnnotator = new StanfordCoreNLP(props);
	abnerTagger = new Tagger(Tagger.BIOCREATIVE);

	if(useWordVector){
	    tokenVector = new TokenVector();
	    try{
		tokenVector
			.loadModel("/usr0/home/diw1/data/alzheimer_vector.bin");
	    }
	    catch(IOException e){
		e.printStackTrace();
	    }
	}
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
	// TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
	// String testDocId = testDoc.getId();
	System.out.println("Calculating baseline scores...");
	ArrayList<QuestionAnswerSet> qaSet = Utils
		.getQuestionAnswerSetFromTestDocCAS(aJCas);
	Collection<Sentence> sentList = JCasUtil.select(aJCas, Sentence.class);
	Collection<NSentence> nSentenceCollection = JCasUtil.select(aJCas,
		NSentence.class);

	HashMap<String, Double> idfMap = getIDFMap(aJCas);

	// for (NSentence nSentence: nSentenceCollection){
	// System.out.println(nSentence.getPhraseList());
	// }

	// for (Sentence sentence : sentenceList) {
	// System.out.println(sentence.getText());
	// }

	// Collection<NSentence> NSentenceList = JCasUtil.select(aJCas,
	// NSentence.class);
	// for (NSentence nSentence: NSentenceList) {
	// for (Sentence sentence:
	// Utils.fromFSListToCollection(nSentence.getSentenceList(),
	// Sentence.class)) {
	// System.out.println(sentence.getText());
	// }
	// }

	for(int i = 0; i < qaSet.size(); i++){
	    // System.out.println("========================================================");
	    Question question = qaSet.get(i).getQuestion();
	    // System.out.println("Question: " + question.getText());
	    ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet
		    .get(i).getAnswerList(), Answer.class);

	    for(Answer answer : choiceList){
		try{
		    String recon = reconstruct(question, answer);
		    System.out.println(recon);
		    answer.setReconText(recon);

		    String nerTagged = abnerTagger.tagABNER(recon);
		    ArrayList<NER> abnerList = Utils.extractNER(nerTagged,
			    aJCas);
		    FSList fsNERList = Utils.createNERList(aJCas, abnerList);
		    answer.setReconNERList(fsNERList);

		    Annotation document = new Annotation(recon);
		    // System.out.println("Entering stanford annotation");
		    stanfordAnnotator.annotate(document);

		    ArrayList<Token> tokenList = new ArrayList<Token>();
		    // System.out.println(recon);
		    // System.out.println(tokenList);
		    CoreMap annotatedSent = document.get(
			    SentencesAnnotation.class).get(0);
		    for(CoreLabel token : annotatedSent
			    .get(TokensAnnotation.class)){
			String originalText = token.originalText();
			// this is the POS tag of the token
			String pos = token.get(PartOfSpeechAnnotation.class);
			// this is the NER label of the token
			String ne = token.get(NamedEntityTagAnnotation.class);
			Token annToken = new Token(aJCas);
			annToken.setText(originalText);
			annToken.setPos(pos);
			annToken.setNer(ne);
			tokenList.add(annToken);
		    }
		    ArrayList<NounPhrase> answerNPList = Utils
			    .extractNounPhrases(tokenList, aJCas);
		    FSList fsPhraseList = Utils.createNounPhraseList(aJCas,
			    answerNPList);
		    FSList fsTokenList = Utils
			    .createTokenList(aJCas, tokenList);
		    answer.setReconTokenList(fsTokenList);
		    answer.setReconNounPhraseList(fsPhraseList);

		    // this is the Stanford dependency graph of the
		    // reconstructed answer
		    SemanticGraph dependencies = annotatedSent
			    .get(CollapsedCCProcessedDependenciesAnnotation.class);
		    List<SemanticGraphEdge> depList = dependencies
			    .edgeListSorted();
		    FSList fsDependencyList = Utils.createDependencyList(aJCas,
			    depList);
		    answer.setReconDependencies(fsDependencyList);

		    // compute similarity between each reconstructed answer and
		    // testDoc sentence
		    ArrayList<Double> similarity = new ArrayList<Double>();
		    ArrayList<NSentence> maxNSentences = new ArrayList<NSentence>();

		    for(NSentence nSentence : nSentenceCollection){
			ArrayList<Double> resultList = (ArrayList<Double>) getSim(
				answer, nSentence, idfMap);
			try{
			    if(similarity.isEmpty()){
				similarity = resultList;
				for(int j = 0; j < resultList.size(); j++){
				    maxNSentences.add(nSentence);
				}
			    }
			    else{
				for(int i1 = 0; i1 < resultList.size(); i1++){
				    if(resultList.get(i1) > similarity.get(i1)){
					similarity.set(i1, resultList.get(i1));
					maxNSentences.set(i1, nSentence);
				    }
				}
			    }
			}
			catch(Exception e){
			    e.printStackTrace();
			}
		    }
		    DoubleArray simArray = new DoubleArray(aJCas,
			    similarity.size() + 1);
		    for(int j = 0; j < similarity.size(); j++){
			simArray.set(j, similarity.get(j));
		    }

		    double srlSimScore = 0;
		    if(useSrl){
			srlSimScore = AnswerChoiceSemanticRoleMatcher
				.getSemanticRoleSim(question, answer, sentList);
		    }

		    answer.setMaxScoredNSentences((FSArray) FSCollectionFactory
			    .createFSArray(aJCas, maxNSentences));

		    // FSCollectionFactory<NSentence> nSentArrary = new
		    // FSCollectionFactory<NSentence>();

		    simArray.set(similarity.size(), srlSimScore);

		    answer.setBaselineScore(simArray);
		    // System.out.println(simArray);
		    // System.out.println("Out of stanford annotation");
		}
		catch(Exception e){
		    e.printStackTrace();
		    return;
		}
		// System.out.println("========================================================");
	    }
	}
    }

    private List<Double> getSim(Answer answer, NSentence nSentence,
	    HashMap<String, Double> idfMap) {
	List<Double> resultList = new ArrayList<Double>();
	HashMap<String, Integer> answerNPCountMap = getTFMap(
		answer.getReconNounPhraseList(), "text");
	HashMap<String, Integer> docSentNPCountMap = getTFMap(
		nSentence.getPhraseList(), "text");

	HashMap<String, Integer> answerTokenCountMap = getTFMap(
		answer.getReconTokenList(), "text");
	HashMap<String, Integer> docSentTokenCountMap = getTFMap(
		nSentence.getTokenList(), "text");

	// HashMap<String, Integer> answerDepCountMap =
	// getTFMap(answer.getReconDependencies());
	// HashMap<String, Integer> docSentDepCountMap =
	// getTFMap(docSentence.getDependencyList());

	// HashMap<String, Integer> answerNERCountMap =
	// getTFMap(answer.getReconNERList(), "tag");
	// HashMap<String, Integer> docSentNERCountMap =
	// getTFMap(docSentence.getNerList(), "tag");

	double npCosineSim = getCosine(answerNPCountMap, docSentNPCountMap);
	double npDiceSim = getDice(answerNPCountMap, docSentNPCountMap);
	double npJaccardSim = getJaccard(answerNPCountMap, docSentNPCountMap);
	resultList.add(npCosineSim);
	resultList.add(npDiceSim);
	resultList.add(npJaccardSim);

	double tokenConsineSim = getCosine(answerTokenCountMap,
		docSentTokenCountMap);
	double tokenDiceSim = getDice(answerTokenCountMap, docSentTokenCountMap);
	double tokenJaccardSim = getJaccard(answerTokenCountMap,
		docSentTokenCountMap);
	resultList.add(tokenConsineSim);
	resultList.add(tokenDiceSim);
	resultList.add(tokenJaccardSim);

	if(useWordVector){
	    resultList.add(getMaxVectorCosine(answerTokenCountMap,
		    docSentTokenCountMap, idfMap));
	}
	else{
	    resultList.add(0.0);
	}

	// double nerCosineSim = getCosine(answerNERCountMap,
	// docSentNERCountMap);
	// double nerDiceSim = getDice(answerNERCountMap, docSentNERCountMap);
	// double nerJaccardSim = getJaccard(answerNERCountMap,
	// docSentNERCountMap);
	// resultList.add(nerCosineSim);
	// resultList.add(nerDiceSim);
	// resultList.add(nerJaccardSim);

	return resultList;
    }

    private double getDice(HashMap<String, Integer> tfMap1,
	    HashMap<String, Integer> tfMap2) {
	int match = 0;
	for(Entry<String, Integer> entry : tfMap1.entrySet()){
	    String keyString = entry.getKey();
	    if(tfMap2.containsKey(keyString)){
		match++;
	    }
	}
	return (double) 2 * match / (tfMap1.size() + tfMap2.size());
    }

    private double getJaccard(HashMap<String, Integer> tfMap1,
	    HashMap<String, Integer> tfMap2) {
	int match = 0;
	for(Entry<String, Integer> entry : tfMap1.entrySet()){
	    String keyString = entry.getKey();
	    if(tfMap2.containsKey(keyString)){
		match++;
	    }
	}
	Set<String> union = new HashSet<String>(tfMap1.keySet());
	union.addAll(tfMap2.keySet());
	return (double) match / (union.size());
    }

    private double getCosine(HashMap<String, Integer> tfMap1,
	    HashMap<String, Integer> tfMap2) {
	if(tfMap1.isEmpty() || tfMap2.isEmpty()){
	    return 0;
	}

	double score = 0.0;
	for(Entry<String, Integer> entry : tfMap1.entrySet()){
	    String tokenString = entry.getKey();
	    Integer count = entry.getValue();
	    if(tfMap2.containsKey(tokenString)){
		score += tfMap2.get(tokenString) * count;
	    }
	}
	return score / Math.sqrt(getLength(tfMap1) * getLength(tfMap2));
    }

    private double getMaxVectorCosine(HashMap<String, Integer> ansTfMap,
	    HashMap<String, Integer> sentTfMap, HashMap<String, Double> idfMap) {
	if(ansTfMap.isEmpty() || sentTfMap.isEmpty()){
	    return 0;
	}

	double score = 0.0;
	for(Entry<String, Integer> entry : ansTfMap.entrySet()){
	    String tokenString = entry.getKey();
	    Integer count = entry.getValue();
	    // score += tokenVector.maxCosineSimilarity(tokenString,
	    // tfMap2.keySet());

	    double maxSim = 0;
	    double maxSimConut = 1;
	    String maxWord2 = tokenString;
	    for(String word2 : sentTfMap.keySet()){
		double sim = tokenVector.cosineSimilarity(tokenString, word2);
		if(sim > maxSim){
		    maxSim = sim;
		    maxSimConut = sentTfMap.get(word2);
		    maxWord2 = word2;
		}
	    }

	    double swIdf = getIdf(idfMap, maxWord2, 1);
	    double awIdf = getIdf(idfMap, tokenString, swIdf);
	    score += maxSim * maxSimConut * count * swIdf * awIdf;
	    // score += maxSim;
	}

	return score / (getLength(ansTfMap) + getLength(sentTfMap));
	// / Math.sqrt(getLength(ansTfMap) * getLength(sentTfMap));
    }

    private double getLength(Map<String, Integer> bag) {
	double result = 0;
	for(Entry<String, Integer> tokenEntry : bag.entrySet()){
	    Integer count = tokenEntry.getValue();
	    result += count * count;
	}
	return result;
    }

    private HashMap<String, Integer> getTFMap(FSList fsList, String featureName) {
	HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
	int index = 0;
	while(true){
	    String keyString = null;
	    try{
		TOP element = fsList.getNthElement(index);
		keyString = element.getFeatureValueAsString(element.getType()
			.getFeatureByBaseName(featureName));
	    }
	    catch(Exception e){
		break;
	    }

	    if(resultMap.containsKey(keyString)){
		resultMap.put(keyString, resultMap.get(keyString) + 1);
	    }
	    else{
		resultMap.put(keyString, 1);
	    }
	    index++;
	}
	return resultMap;
    }

    private HashMap<String, Double> getIDFMap(JCas jCas) {
	HashMap<String, Integer> countMap = new HashMap<String, Integer>();
	Collection<Sentence> sentList = JCasUtil.select(jCas, Sentence.class);
	for(Sentence sent : sentList){
	    HashSet<String> sentTokenSet = new HashSet<String>();
	    if(sent.getTokenList() == null){
		continue;
	    }
	    Collection<Token> tokens = JCasUtil.select(sent.getTokenList(),
		    Token.class);
	    for(Token token : tokens){
		sentTokenSet.add(token.getText());
	    }

	    for(String termText : sentTokenSet){
		if(countMap.containsKey(termText)){
		    countMap.put(termText, countMap.get(termText) + 1);
		}
		else{
		    countMap.put(termText, 1);
		}
	    }
	}
	int sentNum = sentList.size();
	HashMap<String, Double> idfMap = new HashMap<String, Double>();
	for(Entry<String, Integer> entry : countMap.entrySet()){
	    double idf = Math.max(
		    0,
		    Math.log10((double) (sentNum + 0.5)
			    / (0.5 + entry.getValue())));
	    idfMap.put(entry.getKey(), idf);
	}
	return idfMap;
    }

    double getIdf(HashMap<String, Double> idfMap, String term, double backoff) {
	if(idfMap.containsKey(term)){
	    return idfMap.get(term);
	}
	else{
	    // System.err.println( "no idf:" + term );
	    return backoff;
	}
    }

    public static String reconstruct(Question quest, Answer ans) {
	return reconstruct(quest, ans.getText());
    }

    /**
     * Constructs a single sentence from a Question string and an Answer string
     * 
     * @param q
     *            the question text
     * @param a
     *            the answer text
     * @return the reconstructed sentence as a String
     */
    public static String reconstruct(Question quest, String ans) {
	// Get text
	String a = ans;
	String q = quest.getText().substring(0, quest.getText().length() - 1);
	q = q.replace('?', ' ').trim();

	// Set up tokenizer
	StringTokenizer tok = new StringTokenizer(q);
	ArrayList<String> qwords = new ArrayList<String>();
	while(tok.hasMoreTokens())
	    qwords.add(tok.nextToken());
	String first = qwords.get(0);
	String second = qwords.get(1);

	// Get noun phrase list
	ArrayList<NounPhrase> qnpList = Utils.fromFSListToCollection(
		quest.getNounList(), NounPhrase.class);
	ArrayList<String> npList = new ArrayList<String>();
	for(NounPhrase np : qnpList)
	    npList.add(np.getText());
	/*
	 * System.out.println("\tNounPhrases: " + npList.size()); for(String s :
	 * npList) System.out.println("\t" + s);
	 */

	// QUESTION TYPE METHOD

	if(first.equals("What") && (!connectors.contains(qwords.get(1)))){
	    // Get maximal noun phrase
	    boolean npfound = false;
	    int x = 1;
	    String noun = qwords.get(x++);
	    if(npList.contains(noun))
		npfound = true;
	    String nextword = qwords.get(x);
	    while(((!npfound) || (npList.contains(noun + " " + nextword)))
		    && (x < qwords.size())){
		noun += " " + nextword;
		x++;
		if((!npfound) && (npList.contains(noun)))
		    npfound = true;
		if(x < qwords.size())
		    nextword = qwords.get(x);
	    }// end while

	    if(x < qwords.size()){
		String verb = qwords.get(x);
		x++;

		if(!connectors.contains(verb)){
		    String rest = "";
		    for(int y = x; y < qwords.size(); y++)
			rest += qwords.get(y) + " ";
		    rest = rest.trim();

		    String constructed = a + " " + verb + " " + rest;
		    return constructed;
		}
	    }
	}

	// which + noun + is / what + noun + is
	// which, for which, in which, for what, in what
	if(first.equals("Which")
		|| second.equals("which") // which / in which
		|| (qwords.get(1).equals("what")) // for what / in what
		|| (first.equals("What") && !connectors.contains(qwords.get(1)))){
	    String noun = "";

	    // Get connecting word
	    int x = 0;
	    String word = qwords.get(x++);
	    while(!connectors.contains(word) && (x < qwords.size())){
		word = qwords.get(x++);
		if(!(word.equals("which") || word.equals("Which") || word
			.equals("what")))
		    noun += word + " ";
	    }
	    String connector = word;

	    // get rest of sentence
	    String rest = "";
	    for(int y = x; y < qwords.size(); y++)
		rest += qwords.get(y) + " ";
	    rest = rest.trim();

	    // reconstruct for/in constructions
	    if(first.equals("For") || first.equals("In"))
		connector = first.toLowerCase();

	    // System.out.println("\ta: " + a + "\n\tconnector: " + connector +
	    // "\n\trest: " + rest);

	    // construct sentence
	    String constructed;
	    if(connector.equals("do") || connector.equals("does"))
		constructed = a + " " + rest;
	    else if(connector.equals("is") || connector.equals("are")
		    || connector.equals("was"))
		constructed = a + " " + connector + " " + rest;
	    else
		constructed = rest + " " + connector + " " + a;
	    return constructed;
	}// end in which

	// where
	else if(first.equals("Where")){
	    String connector = qwords.get(1);

	    // Get maximal noun phrase
	    boolean npfound = false;
	    int x = 2;
	    String noun = qwords.get(x++);
	    if(npList.contains(noun))
		npfound = true;
	    String nextword = qwords.get(x);
	    while(((!npfound) || (npList.contains(noun + " " + nextword)))
		    && (x < qwords.size())){
		noun += " " + nextword;
		x++;
		if((!npfound) && (npList.contains(noun)))
		    npfound = true;
		if(x < qwords.size())
		    nextword = qwords.get(x);
	    }
	    // System.out.println("Found NP: " + noun);

	    // Get rest of sentence
	    String rest = "";
	    for(int y = x; y < qwords.size(); y++)
		rest += qwords.get(y) + " ";
	    rest = rest.trim();

	    // construct sentence
	    String constructed = noun + " " + connector + " " + rest + " in "
		    + a;
	    return constructed;
	}// end where

	// how does
	else if(first.equals("How")
		&& (second.equals("does") || second.equals("do") || second
			.equals("did"))){
	    String connector = second;

	    // Get maximal noun phrase
	    boolean npfound = false;
	    int x = 2;
	    String noun = qwords.get(x++);
	    if(npList.contains(noun))
		npfound = true;
	    String nextword = qwords.get(x);
	    while(npfound && (npList.contains(noun + " " + nextword))
		    && (x < qwords.size())){
		noun += " " + nextword;
		x++;
		if(x < qwords.size())
		    nextword = qwords.get(x);
		else npfound = false;
	    }// end while
	    
	    String verb = qwords.get(x);
	    x++;
	    if(connector.equals("does"))
		verb += "s";

	    String rest = "";
	    for(int y = x; y < qwords.size(); y++)
		rest += qwords.get(y) + " ";
	    rest = rest.trim();

	    String constructed = noun + " " + verb + " " + rest + " " + a;
	    return constructed;
	}// end how does

	// How many
	else if(first.equals("How") && second.equals("many")){

	    // Get maximal noun phrase
	    boolean npfound = false;
	    int x = 2;
	    String noun = qwords.get(x++);
	    if(npList.contains(noun))
		npfound = true;
	    String nextword = qwords.get(x);
	    while(npfound && (npList.contains(noun + " " + nextword))
		    && (x < qwords.size())){
		noun += " " + nextword;
		x++;
		if(x < qwords.size())
		    nextword = qwords.get(x);
		else npfound = false;
	    }// end while
	    
	    String connector = qwords.get(x);
	    x++;

	    String rest = "";
	    for(int y = x; y < qwords.size(); y++)
		rest += qwords.get(y) + " ";
	    rest = rest.trim();
	    
	    String constructed = "";
	    if(rest.length() > 0){
		int index = 0;
		if(rest.lastIndexOf(' ') >0)
		    index = rest.lastIndexOf(' ');
		String lastword = rest.substring(index+1);
		if(lastword.equals("have")){
		    if(connector.equals("does"))
			constructed = rest.substring(0, index) + " has " + a + " " + noun;
		    else
			constructed = rest.substring(0, index) + " have " + a + " " + noun;
		}
		else constructed = a + " " + noun + " " + rest;
	    }
	    else // How many noun connector rest?
		constructed = a + " " + noun + " " + rest;
	    
	    return constructed;
	}// end how many

	// what is noun?
	else if(first.equals("What")
		&& (second.equals("is") || second.equals("are") || second
			.equals("was"))){
	    String connector = second;
	    String rest = "";
	    for(int x = 2; x < qwords.size(); x++)
		rest += qwords.get(x) + " ";
	    rest = rest.trim();

	    String constructed = a + " " + connector + " " + rest;
	    return constructed;
	}

	// what noun verbs?
	/*
	 * else System.out.println("TYPE: Naive");
	 */
	// NAIVE METHOD
	return q + " " + a;
    }// end reconstruct method

}
