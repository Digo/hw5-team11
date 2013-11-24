package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.JCasUtil;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NSentence;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
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
import abner.Tagger;

public class AnswerChoiceBaselineScorer extends JCasAnnotator_ImplBase {

  // int K_CANDIDATES = 5;
  protected static ArrayList<String> connectors;

  HashSet<String> hshStopWords = new HashSet<String>();

  private StanfordCoreNLP stanfordAnnotator;

  Tagger abnerTagger = null;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
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
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    // String testDocId = testDoc.getId();
    System.out.println("Calculating baseline scores...");
    ArrayList<QuestionAnswerSet> qaSet = Utils.getQuestionAnswerSetFromTestDocCAS(aJCas);
    Collection<NSentence> nSentenceCollection = JCasUtil.select(aJCas, NSentence.class);
//    for (NSentence nSentence: nSentenceCollection){
//      System.out.println(nSentence.getPhraseList());
//    }


    // for (Sentence sentence : sentenceList) {
    // System.out.println(sentence.getText());
    // }

    // Collection<NSentence> NSentenceList = JCasUtil.select(aJCas, NSentence.class);
    // for (NSentence nSentence: NSentenceList) {
    // for (Sentence sentence: Utils.fromFSListToCollection(nSentence.getSentenceList(),
    // Sentence.class)) {
    // System.out.println(sentence.getText());
    // }
    // }

    for (int i = 0; i < qaSet.size(); i++) {
//      System.out.println("========================================================");
      Question question = qaSet.get(i).getQuestion();
//      System.out.println("Question: " + question.getText());
      ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);

      for (Answer answer : choiceList) {
        try {
          String recon = reconstruct(question, answer);
//          System.out.println(recon);
          answer.setReconText(recon);

          String nerTagged = abnerTagger.tagABNER(recon);
          ArrayList<NER> abnerList = Utils.extractNER(nerTagged, aJCas);
          FSList fsNERList = Utils.createNERList(aJCas, abnerList);
          answer.setReconNERList(fsNERList);

          Annotation document = new Annotation(recon);
          // System.out.println("Entering stanford annotation");
          stanfordAnnotator.annotate(document);

          ArrayList<Token> tokenList = new ArrayList<Token>();
          // System.out.println(recon);
          // System.out.println(tokenList);
          CoreMap annotatedSent = document.get(SentencesAnnotation.class).get(0);
          for (CoreLabel token : annotatedSent.get(TokensAnnotation.class)) {
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
          ArrayList<NounPhrase> answerNPList = Utils.extractNounPhrases(tokenList, aJCas);
          FSList fsPhraseList = Utils.createNounPhraseList(aJCas, answerNPList);
          FSList fsTokenList = Utils.createTokenList(aJCas, tokenList);
          answer.setReconTokenList(fsTokenList);
          answer.setReconNounPhraseList(fsPhraseList);

          // this is the Stanford dependency graph of the reconstructed answer
          SemanticGraph dependencies = annotatedSent
                  .get(CollapsedCCProcessedDependenciesAnnotation.class);
          List<SemanticGraphEdge> depList = dependencies.edgeListSorted();
          FSList fsDependencyList = Utils.createDependencyList(aJCas, depList);
          answer.setReconDependencies(fsDependencyList);

          // compute similarity between each reconstructed answer and testDoc sentence
          List<Double> similarity = new ArrayList<Double>();
          for (NSentence nSentence : nSentenceCollection) {
            
            similarity = getSim(answer, nSentence, similarity);
          }
          DoubleArray simArray = new DoubleArray(aJCas, similarity.size());
          for (int j = 0; j < similarity.size(); j++) {
            simArray.set(j, similarity.get(j) / nSentenceCollection.size());
          }
          answer.setBaselineScore(simArray);
//          System.out.println(simArray);
          // System.out.println("Out of stanford annotation");
        } catch (Exception e) {
          e.printStackTrace();
          return;
        }
//        System.out.println("========================================================");
      }
    }
  }

  private List<Double> getSim(Answer answer, NSentence nSentence, List<Double> similarity) {
    List<Double> resultList = new ArrayList<Double>();
    HashMap<String, Integer> answerNPCountMap = getTFMap(answer.getReconNounPhraseList(), "text");
    HashMap<String, Integer> docSentNPCountMap = getTFMap(nSentence.getPhraseList(), "text");
    
    HashMap<String, Integer> answerTokenCountMap = getTFMap(answer.getReconTokenList(), "text");
    HashMap<String, Integer> docSentTokenCountMap = getTFMap(nSentence.getTokenList(), "text");
    
    
    // HashMap<String, Integer> answerDepCountMap = getTFMap(answer.getReconDependencies());
    // HashMap<String, Integer> docSentDepCountMap = getTFMap(docSentence.getDependencyList());

    // HashMap<String, Integer> answerNERCountMap = getTFMap(answer.getReconNERList(), "tag");
    // HashMap<String, Integer> docSentNERCountMap = getTFMap(docSentence.getNerList(), "tag");

    double npCosineSim = getCosine(answerNPCountMap, docSentNPCountMap);
    double npDiceSim = getDice(answerNPCountMap, docSentNPCountMap);
    double npJaccardSim = getJaccard(answerNPCountMap, docSentNPCountMap);
    resultList.add(npCosineSim);
    resultList.add(npDiceSim);
    resultList.add(npJaccardSim);
    
    
     double tokenConsineSim = getCosine(answerTokenCountMap, docSentTokenCountMap);
     double tokenDiceSim = getDice(answerTokenCountMap, docSentTokenCountMap);
     double tokenJaccardSim = getJaccard(answerTokenCountMap, docSentTokenCountMap);
     resultList.add(tokenConsineSim);
     resultList.add(tokenDiceSim);
     resultList.add(tokenJaccardSim);

    // double nerCosineSim = getCosine(answerNERCountMap, docSentNERCountMap);
    // double nerDiceSim = getDice(answerNERCountMap, docSentNERCountMap);
    // double nerJaccardSim = getJaccard(answerNERCountMap, docSentNERCountMap);
    // resultList.add(nerCosineSim);
    // resultList.add(nerDiceSim);
    // resultList.add(nerJaccardSim);

    try {
      for (int i = 0; i < resultList.size(); i++) {
        if (!similarity.isEmpty()) {
          resultList.set(i, resultList.get(i) + similarity.get(i));
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return resultList;
  }

  private double getDice(HashMap<String, Integer> tfMap1, HashMap<String, Integer> tfMap2) {
    int match = 0;
    for (Entry<String, Integer> entry : tfMap1.entrySet()) {
      String keyString = entry.getKey();
      if (tfMap2.containsKey(keyString)) {
        match++;
      }
    }
    return (double) 2 * match / (tfMap1.size() + tfMap2.size());
  }

  private double getJaccard(HashMap<String, Integer> tfMap1, HashMap<String, Integer> tfMap2) {
    int match = 0;
    for (Entry<String, Integer> entry : tfMap1.entrySet()) {
      String keyString = entry.getKey();
      if (tfMap2.containsKey(keyString)) {
        match++;
      }
    }
    Set<String> union = new HashSet<String>(tfMap1.keySet());
    union.addAll(tfMap2.keySet());
    return (double) match / (union.size());
  }

  private double getCosine(HashMap<String, Integer> tfMap1, HashMap<String, Integer> tfMap2) {
    if (tfMap1.isEmpty() || tfMap2.isEmpty()) {
      return 0;
    }

    double score = 0.0;
    for (Entry<String, Integer> entry : tfMap1.entrySet()) {
      String tokenString = entry.getKey();
      Integer count = entry.getValue();
      if (tfMap2.containsKey(tokenString)) {
        score += tfMap2.get(tokenString) * count;
      }
    }
    return score / Math.sqrt(getLength(tfMap1) * getLength(tfMap2));
  }

  private double getLength(Map<String, Integer> bag) {
    double result = 0;
    for (Entry<String, Integer> tokenEntry : bag.entrySet()) {
      Integer count = tokenEntry.getValue();
      result += count * count;
    }
    return result;
  }

  private HashMap<String, Integer> getTFMap(FSList fsList, String featureName) {
    HashMap<String, Integer> resultMap = new HashMap<String, Integer>();
    int index = 0;
    while (true) {
      String keyString = null;
      try {
        TOP element = fsList.getNthElement(index);
        keyString = element.getFeatureValueAsString(element.getType().getFeatureByBaseName(
                featureName));
      } catch (Exception e) {
        break;
      }

      if (resultMap.containsKey(keyString)) {
        resultMap.put(keyString, resultMap.get(keyString) + 1);
      } else {
        resultMap.put(keyString, 1);
      }
      index++;
    }
    return resultMap;
  }

  /**
   * Constructs a single sentence from a Question string and an Answer string
   * 
   * @param q
   *          the question text
   * @param a
   *          the answer text
   * @return the reconstructed sentence as a String
   */
  public static String reconstruct(Question quest, Answer ans) {
    // Get text
    String a = ans.getText();
    String q = quest.getText().substring(0, quest.getText().length() - 1);
    q = q.replace('?', ' ').trim();

    // Set up tokenizer
    StringTokenizer tok = new StringTokenizer(q);
    ArrayList<String> qwords = new ArrayList<String>();
    while (tok.hasMoreTokens())
      qwords.add(tok.nextToken());
    String first = qwords.get(0);
    String second = qwords.get(1);

    // Get noun phrase list
    ArrayList<NounPhrase> qnpList = Utils.fromFSListToCollection(quest.getNounList(),
            NounPhrase.class);
    ArrayList<String> npList = new ArrayList<String>();
    for (NounPhrase np : qnpList)
      npList.add(np.getText());
    /*
     * System.out.println("\tNounPhrases: " + npList.size()); for(String s : npList)
     * System.out.println("\t" + s);
     */

    // QUESTION TYPE METHOD

    if (first.equals("What") && (!connectors.contains(qwords.get(1)))) {
      // Get maximal noun phrase
      boolean npfound = false;
      int x = 1;
      String noun = qwords.get(x++);
      if (npList.contains(noun))
        npfound = true;
      String nextword = qwords.get(x);
      while (((!npfound) || (npList.contains(noun + " " + nextword))) && (x < qwords.size())) {
        noun += " " + nextword;
        x++;
        if ((!npfound) && (npList.contains(noun)))
          npfound = true;
        if (x < qwords.size())
          nextword = qwords.get(x);
      }// end while

      if (x < qwords.size()) {
        String verb = qwords.get(x);
        x++;

        if (!connectors.contains(verb)) {
          String rest = "";
          for (int y = x; y < qwords.size(); y++)
            rest += qwords.get(y) + " ";
          rest = rest.trim();

          String constructed = a + " " + verb + " " + rest;
          return constructed;
        }
      }
    }

    // which + noun + is / what + noun + is
    // which, for which, in which, for what, in what
    if (first.equals("Which") || second.equals("which") // which / in which
            || (qwords.get(1).equals("what")) // for what / in what
            || (first.equals("What") && !connectors.contains(qwords.get(1)))) { // what
                                                                                // noun
                                                                                // is
                                                                                // ...
      String noun = "";

      // Get connecting word
      int x = 0;
      String word = qwords.get(x++);
      while (!connectors.contains(word) && (x < qwords.size())) {
        word = qwords.get(x++);
        if (!(word.equals("which") || word.equals("Which") || word.equals("what")))
          noun += word + " ";
      }
      String connector = word;

      // get rest of sentence
      String rest = "";
      for (int y = x; y < qwords.size(); y++)
        rest += qwords.get(y) + " ";
      rest = rest.trim();

      // reconstruct for/in constructions
      if (first.equals("For") || first.equals("In"))
        connector = first.toLowerCase();

      // construct sentence
      String constructed;
      if (connector.equals("do") || connector.equals("does"))
        constructed = a + " " + rest;
      else if (connector.equals("is") || connector.equals("are") || connector.equals("was"))
        constructed = a + " " + connector + " " + rest;
      else
        constructed = rest + " " + connector + " " + a;
      return constructed;
    }// end in which

    // where
    else if (first.equals("Where")) {
      String connector = qwords.get(1);

      // Get maximal noun phrase
      boolean npfound = false;
      int x = 2;
      String noun = qwords.get(x++);
      if (npList.contains(noun))
        npfound = true;
      String nextword = qwords.get(x);
      while (((!npfound) || (npList.contains(noun + " " + nextword))) && (x < qwords.size())) {
        noun += " " + nextword;
        x++;
        if ((!npfound) && (npList.contains(noun)))
          npfound = true;
        if (x < qwords.size())
          nextword = qwords.get(x);
      }
      // System.out.println("Found NP: " + noun);

      // Get rest of sentence
      String rest = "";
      for (int y = x; y < qwords.size(); y++)
        rest += qwords.get(y) + " ";
      rest = rest.trim();

      // construct sentence
      String constructed = noun + " " + connector + " " + rest + " in " + a;
      return constructed;
    }// end where

    // how does
    else if (first.equals("How")
            && (second.equals("does") || second.equals("do") || second.equals("did"))) {
      String connector = second;

      // Get maximal noun phrase
      boolean npfound = false;
      int x = 2;
      String noun = qwords.get(x++);
      if (npList.contains(noun))
        npfound = true;
      String nextword = qwords.get(x);
      while (((!npfound) || (npList.contains(noun + " " + nextword))) && (x < qwords.size())) {
        noun += " " + nextword;
        x++;
        if ((!npfound) && (npList.contains(noun)))
          npfound = true;
        if (x < qwords.size())
          nextword = qwords.get(x);
      }// end while
      String verb = qwords.get(x);
      x++;
      if (connector.equals("does"))
        verb += "s";

      String rest = "";
      for (int y = x; y < qwords.size(); y++)
        rest += qwords.get(y) + " ";
      rest = rest.trim();

      String constructed = noun + " " + verb + " " + rest + " " + a;
      return constructed;
    }

    // what is noun?
    else if (first.equals("What")
            && (second.equals("is") || second.equals("are") || second.equals("was"))) {
      String connector = second;
      String rest = "";
      for (int x = 2; x < qwords.size(); x++)
        rest += qwords.get(x) + " ";
      rest = rest.trim();

      String constructed = a + " " + connector + " " + rest;
      return constructed;
    }

    // what noun verbs?

    // NAIVE METHOD
    return q + " " + a;
  }// end reconstruct method

}
