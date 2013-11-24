package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.lti.qalab.types.NSentence;
import edu.cmu.lti.qalab.types.NounPhrase;
import edu.cmu.lti.qalab.types.Token;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.qalab.utils.Utils;

public class NSentenceAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    getNSentence(jCas, 3);
  }

  public void getNSentence(JCas jCas, int k) {
    ArrayList<Sentence> sentList = Utils.getSentenceListFromTestDocCAS(jCas);
    ArrayList<Sentence> sentArray = new ArrayList<Sentence>();
    int index = 0;
    int i = 0;
    String NSentString = "";
    int sentNum = sentList.size();
    List<ArrayList<Token>> tokenList = new ArrayList<ArrayList<Token>>();
    List<ArrayList<NounPhrase>> phraseList = new ArrayList<ArrayList<NounPhrase>>();
    
    while (index < sentNum && i < k) {
      Sentence sent = sentList.get(index);
      tokenList.add(Utils.fromFSListToCollection(sent.getTokenList(), Token.class));
      phraseList.add(Utils.fromFSListToCollection(sent.getPhraseList(), NounPhrase.class));
      if (index == 0) {
        NSentString = NSentString + sent.getText();
      } else {
        NSentString = NSentString + " " + sent.getText();
      }
      sentArray.add(sent);
      index++;
      i++;
    }
    if (i < k)
      return;

    NSentence nSent = new NSentence(jCas);
    FSList sentFSList = Utils.fromCollectionToFSList(jCas, sentArray);
    nSent.setSentenceList(sentFSList);
    nSent.setText(NSentString);
    nSent.setTokenList(mergeNestedList(tokenList, jCas));
    nSent.setPhraseList(mergeNestedList(phraseList, jCas));
    nSent.addToIndexes();

    while (index < sentNum) {
      NSentString = NSentString.substring(sentArray.get(0).getText().length()).trim();
      sentArray.remove(0);
      tokenList.remove(0);
      phraseList.remove(0);
      Sentence sent = sentList.get(index);
      NSentString = NSentString + " " + sent.getText();
      sentArray.add(sent);
      tokenList.add(Utils.fromFSListToCollection(sent.getTokenList(), Token.class));
      phraseList.add(Utils.fromFSListToCollection(sent.getPhraseList(), NounPhrase.class));
      index++;

      nSent = new NSentence(jCas);
      sentFSList = Utils.fromCollectionToFSList(jCas, sentArray);
      nSent.setSentenceList(sentFSList);
      nSent.setText(NSentString);
      nSent.setTokenList(mergeNestedList(tokenList, jCas));
      nSent.setPhraseList(mergeNestedList(phraseList, jCas));
      nSent.addToIndexes();
    }

  }

  private <T extends Annotation> FSList mergeNestedList(List<ArrayList<T>> nestedList, JCas aJCas) {
    Collection<T> resultList = new ArrayList<T>();
    for (ArrayList<T> elementList : nestedList) {
      resultList.addAll(elementList);
    }
    return Utils.fromCollectionToFSList(aJCas, resultList);
  }
}
