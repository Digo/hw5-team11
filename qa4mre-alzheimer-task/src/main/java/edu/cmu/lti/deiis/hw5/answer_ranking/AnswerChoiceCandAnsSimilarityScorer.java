package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.resource.ResourceInitializationException;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.CandidateAnswer;
import edu.cmu.lti.qalab.types.CandidateSentence;
import edu.cmu.lti.qalab.types.NER;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class AnswerChoiceCandAnsSimilarityScorer extends JCasAnnotator_ImplBase{

    int K_CANDIDATES = 5;
    protected static ArrayList<String> connectors;

    @Override
    public void initialize(UimaContext context)
	    throws ResourceInitializationException {
	super.initialize(context);
	K_CANDIDATES = (Integer) context
		.getConfigParameterValue("K_CANDIDATES");

	// connecting words for question/answer reconstruction
	connectors = new ArrayList<String>();
	connectors.add("do");
	connectors.add("does");
	connectors.add("did");
	connectors.add("is");
	connectors.add("was");
	connectors.add("are");
    }

    @Override
    public void process(JCas aJCas) throws AnalysisEngineProcessException {
	TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
	// String testDocId = testDoc.getId();
	ArrayList<QuestionAnswerSet> qaSet = Utils
		.getQuestionAnswerSetFromTestDocCAS(aJCas);

	for(int i = 0; i < qaSet.size(); i++){

	    Question question = qaSet.get(i).getQuestion();
	    System.out.println("Question: " + question.getText());
	    ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet
		    .get(i).getAnswerList(), Answer.class);

	    // Question/Answer sentence reconstruction
	    for(Answer answer : choiceList){
		String recon = reconstruct(question, answer);
		System.out.println("Reconstructed: " + recon);
		answer.setText(recon);
	    }// end for

	    ArrayList<CandidateSentence> candSentList = Utils
		    .fromFSListToCollection(qaSet.get(i)
			    .getCandidateSentenceList(),
			    CandidateSentence.class);

	    int topK = Math.min(K_CANDIDATES, candSentList.size());
	    for(int c = 0; c < topK; c++){

		CandidateSentence candSent = candSentList.get(c);

		ArrayList<NounPhrase> candSentNouns = Utils
			.fromFSListToCollection(candSent.getSentence()
				.getPhraseList(), NounPhrase.class);
		ArrayList<NER> candSentNers = Utils.fromFSListToCollection(
			candSent.getSentence().getNerList(), NER.class);

		ArrayList<CandidateAnswer> candAnsList = new ArrayList<CandidateAnswer>();
		for(int j = 0; j < choiceList.size(); j++){

		    Answer answer = choiceList.get(j);
		    ArrayList<NounPhrase> choiceNouns = Utils
			    .fromFSListToCollection(answer.getNounPhraseList(),
				    NounPhrase.class);
		    ArrayList<NER> choiceNERs = Utils.fromFSListToCollection(
			    answer.getNerList(), NER.class);

		    int nnMatch = 0;
		    for(int k = 0; k < candSentNouns.size(); k++){
			for(int l = 0; l < choiceNERs.size(); l++){
			    if(candSentNouns.get(k).getText()
				    .contains(choiceNERs.get(l).getText())){
				nnMatch++;
			    }
			}
			for(int l = 0; l < choiceNouns.size(); l++){
			    if(candSentNouns.get(k).getText()
				    .contains(choiceNouns.get(l).getText())){
				nnMatch++;
			    }
			}
		    }

		    for(int k = 0; k < candSentNers.size(); k++){
			for(int l = 0; l < choiceNERs.size(); l++){
			    if(candSentNouns.get(k).getText()
				    .contains(choiceNERs.get(l).getText())){
				nnMatch++;
			    }
			}
			for(int l = 0; l < choiceNouns.size(); l++){
			    if(candSentNouns.get(k).getText()
				    .contains(choiceNouns.get(l).getText())){
				nnMatch++;
			    }
			}

		    }

		    System.out.println(choiceList.get(j).getText() + "\t"
			    + nnMatch);
		    CandidateAnswer candAnswer = null;
		    if(candSent.getCandAnswerList() == null){
			candAnswer = new CandidateAnswer(aJCas);
		    }
		    else{
			candAnswer = Utils.fromFSListToCollection(
				candSent.getCandAnswerList(),
				CandidateAnswer.class).get(j);// new
							      // CandidateAnswer(aJCas);;

		    }
		    candAnswer.setText(answer.getText());
		    candAnswer.setQId(answer.getQuestionId());
		    candAnswer.setChoiceIndex(j);
		    candAnswer.setSimilarityScore(nnMatch);
		    candAnsList.add(candAnswer);
		}

		FSList fsCandAnsList = Utils.fromCollectionToFSList(aJCas,
			candAnsList);
		candSent.setCandAnswerList(fsCandAnsList);
		candSentList.set(c, candSent);

	    }

	    System.out
		    .println("================================================");
	    FSList fsCandSentList = Utils.fromCollectionToFSList(aJCas,
		    candSentList);
	    qaSet.get(i).setCandidateSentenceList(fsCandSentList);

	}
	FSList fsQASet = Utils.fromCollectionToFSList(aJCas, qaSet);
	testDoc.setQaList(fsQASet);
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
    public static String reconstruct(Question quest, Answer ans) {
	// Get text
	String a = ans.getText();
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
	    while(((!npfound) || (npList.contains(noun + " " + nextword))) && (x < qwords.size())){
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
		|| (first.equals("What") && !connectors.contains(qwords.get(1)))){ // what
										   // noun
										   // is
										   // ...
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
	    while(((!npfound) || (npList.contains(noun + " " + nextword)))
		    && (x < qwords.size())){
		noun += " " + nextword;
		x++;
		if((!npfound) && (npList.contains(noun)))
		    npfound = true;
		if(x < qwords.size())
		    nextword = qwords.get(x);
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
	}

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

	// NAIVE METHOD
	return q + " " + a;
    }// end reconstruct method

}
