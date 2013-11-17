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
import edu.cmu.lti.qalab.utils.Utils;

public class NSentenceAnnotator extends JCasAnnotator_ImplBase {
  private String docText;

  @Override
  public void process(JCas jCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    System.out.println("Processing N-Sentence");
    docText = jCas.getDocumentText();
    AnnotationIndex<Annotation> sIndex = jCas.getAnnotationIndex(Sentence.type);
    FSIterator<Annotation> sIterator = sIndex.iterator();
    getNSentence(jCas, 3);
  }
  
  public void getNSentence(JCas jCas, int k){
    ArrayList<Sentence> sentList = Utils.getSentenceListFromTestDocCAS(jCas);
    ArrayList<Sentence> sentArray = new ArrayList<Sentence>();
    int index = 0;
    int i = 0;
    String NSentString = "";
    int sentNum = sentList.size();
    while(index < sentNum && i < k){
      Sentence sent = sentList.get(index);
      if(index == 0){
        NSentString = NSentString + sent.getText();
      }
      else {
        NSentString = NSentString + " " + sent.getText();
      }
      
      sentArray.add(sent);
      index++;
      i++;     
    }
    if(i < k)
      return;

    

    NSentence nSent = new NSentence(jCas);
    FSList sentFSList = Utils.fromCollectionToFSList(jCas,sentArray);
    nSent.setSentenceList(sentFSList);
    nSent.setText(NSentString);
    nSent.addToIndexes();
    System.out.println("New NGram added");
    System.out.println(NSentString);
    
    
    while(index < sentNum){
      NSentString = NSentString.substring(sentArray.get(0).getText().length()).trim();
      sentArray.remove(0);      
      Sentence sent = sentList.get(index);
      NSentString = NSentString + " " + sent.getText();
      sentArray.add(sent);
      index++;
            
      nSent = new NSentence(jCas);
      sentFSList = Utils.fromCollectionToFSList(jCas,sentArray);
      nSent.setSentenceList(sentFSList);
      nSent.setText(NSentString);
      nSent.addToIndexes();
      System.out.println("New NGram added");
      System.out.println(NSentString);
    }

  }
  public void printNSent(ArrayList<Sentence> sentArray){
    System.out.println("NSent Text: ");
    for(Sentence sent : sentArray){
      System.out.print(sent.getText() + " ");
    }
    System.out.println();
  }
}
