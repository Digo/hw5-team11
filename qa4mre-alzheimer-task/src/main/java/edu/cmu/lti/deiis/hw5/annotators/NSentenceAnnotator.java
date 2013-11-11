package edu.cmu.lti.deiis.hw5.annotators;

import java.util.ArrayList;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;

import edu.cmu.lti.qalab.types.NSentence;
import edu.cmu.lti.qalab.types.Sentence;
import edu.cmu.lti.deiis.hw5.utils.Utils;

public class NSentenceAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    System.out.println("Processing N-Sentence");
    AnnotationIndex<Annotation> sIndex = jCas.getAnnotationIndex(Sentence.type);
    FSIterator<Annotation> sIterator = sIndex.iterator();
    getNSentence(jCas, 3, sIterator);
  }
  
  public void getNSentence(JCas jCas, int k,  FSIterator<Annotation> sIterator){
    ArrayList<Sentence> sentArray = new ArrayList<Sentence>();
    int i = 0;
    while(sIterator.hasNext() && i < k){
      Sentence sent = (Sentence)sIterator.next();
      sentArray.add(sent);
      i++;     
    }
    if(i < k)
      return;
    
    boolean ifNSent = true;
    for(i = 0; i < k - 1;i++){
      if((sentArray.get(i + 1).getBegin() - sentArray.get(i).getEnd()) > 1){
        ifNSent = false;
        break;
      }
    }
    if(ifNSent){
      NSentence nSent = new NSentence(jCas);
      FSList sentList = Utils.fromCollectionToFSList(jCas,sentArray);
      nSent.setSentenceList(sentList);
      nSent.setBegin(sentArray.get(0).getBegin());
      nSent.setEnd(sentArray.get(sentArray.size() - 1).getEnd());
      nSent.addToIndexes();
      //System.out.println("New NGram added");
    }
    
    
    while(sIterator.hasNext()){
      sentArray.remove(0);
      Sentence sent = (Sentence)sIterator.next();
      sentArray.add(sent);
      ifNSent = true;
      for(i = 0; i < k - 1;i++){
        if((sentArray.get(i + 1).getBegin() - sentArray.get(i).getEnd()) > 1){
          ifNSent = false;
          break;
        }
      }
      if(ifNSent){
        NSentence nSent = new NSentence(jCas);
        FSList sentList = Utils.fromCollectionToFSList(jCas,sentArray);
        nSent.setSentenceList(sentList);
        nSent.setBegin(sentArray.get(0).getBegin());
        nSent.setEnd(sentArray.get(sentArray.size() - 1).getEnd());
        nSent.addToIndexes();
        //System.out.println("New NGram added");
      }
    }

  }

}
