package edu.cmu.lti.deiis.hw5.answer_selection;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.uimafit.util.JCasUtil;

import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.NSentence;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class AnswerSelectionByLinearInterp extends JCasAnnotator_ImplBase {
  float SCORE_THR = (float) 0.1;
  
  private final boolean IS_DEBUG_MAX_NSENT = false;
  
  ArrayList<DocumentEvaluation> docEvals = new ArrayList<AnswerSelectionByLinearInterp.DocumentEvaluation>();
  
  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    SCORE_THR = (Float) context.getConfigParameterValue("SCORE_THR");
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    ArrayList<QuestionAnswerSet> qaSet = Utils.fromFSListToCollection(testDoc.getQaList(),
            QuestionAnswerSet.class);
    int matched = 0;
    int total = 0;
    int unanswered = 0;
    for (int i = 0; i < qaSet.size(); i++) {
      System.out.println("Processing Question No." + (i+1));
      Question question = qaSet.get(i).getQuestion();
      System.out.println("Question: " + question.getText());
      System.out.println("Candidate Answer" + "\t" + "finalScore");
      ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      
      for (int j = 0; j < choiceList.size(); j++) {
        Answer candAnswer = choiceList.get(j);
        DoubleArray baselineScore = candAnswer.getBaselineScore();
        double PMIScore = candAnswer.getPMIscore();
//        System.out.println(PMIScore);
        double finalScore = getFinalScore(baselineScore, PMIScore);
        candAnswer.setFinalScore(finalScore);
      }
      
      Collections.sort(choiceList, new AnswerFinalScoreComparator());
      Answer topAnswer = choiceList.get(0);
      if (topAnswer.getIsCorrect()){
        matched++;
      }
      
      if (IS_DEBUG_MAX_NSENT){
        Collection<NSentence> maxNSentence = JCasUtil.select(topAnswer.getMaxScoredNSentences(), NSentence.class);
        for (NSentence nSent : maxNSentence){
          System.out.println("MAX Sent: "+ nSent.getText());
        }
      }

      
      //XXX unanswered and matched at same time?
      if (topAnswer.getFinalScore() < SCORE_THR) {
        unanswered++;
      }
      
      for (Answer candAns : choiceList) {
        System.out.println(candAns.getText() + "\t" + candAns.getFinalScore() + "\t"
                + (candAns.getIsCorrect() ? "(gold standard)" : ""));
      }

      total++;
      System.out.println("================================================");
    }

    System.out.println("Correct: " + matched + "/" + total + "=" + ((matched * 100.0) / total)
            + "%");
    // TO DO: Reader of this pipe line should read from xmi generated by
    // SimpleRunCPE
    double cAt1 = (((double) matched) / ((double) total) * unanswered + (double) matched)
            * (1.0 / total);
    System.out.println("c@1 score:" + cAt1);
    

    TestDocument srcDoc = Utils.getTestDocumentFromCAS(aJCas);
    String docId = srcDoc.getId();
    docEvals.add(new DocumentEvaluation(docId, cAt1, matched));

  }

  private double getFinalScore(DoubleArray baselineScore, double PMIScore) {
    double result = 0;

    if (baselineScore == null || baselineScore.size() < 6) {
      System.err.println("ERROR: 6 > baselineScore.size() ");
      return PMIScore;
    }

    //max vector cosine, SRL
    double[] weights = { 0.2, 0.1, 0.1, 0.1, 0.1, 0.1, 0.4, 0.01 };
    for (int i = 0; i < baselineScore.size(); i++) {
      result += baselineScore.get(i) * weights[i];
    }

    result += PMIScore * 0.8;

    // result +=
    // + baselineScore.get(0) * 0.2 + baselineScore.get(1) * 0.1 + baselineScore.get(2) * 0.1
    // + baselineScore.get(3) * 0.1 + baselineScore.get(4) * 0.1 + baselineScore.get(5) * 0.1
    // + baselineScore.get(6) * 0.4 //max vector cosine
    // // + baselineScore.get(7) * 0.01 //SRL
    // + PMIScore * 0.8
    // ;
    return result;
  }
  
  static class DocumentEvaluation{
    String docName;
    double cAt1;
    int corrected;
    static final DecimalFormat df = new DecimalFormat("#.##");
    
    public DocumentEvaluation(String docName, double cAt1, int corrected) {
      super();
      this.docName = docName;
      this.cAt1 = cAt1;
      this.corrected = corrected;
    }

    @Override
    public String toString() {
      return "DocumentEvaluation [docName=" + docName + ", cAt1=" + df.format(cAt1) + ", corrected="
              + corrected + "]";
    }
  }

  @Override
  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    super.collectionProcessComplete();
    System.out.println("\nPeformance Summary: ");
    int totalCorrect = 0;
    for(DocumentEvaluation docEval : docEvals){
      System.out.println(docEval.toString());
      totalCorrect += docEval.corrected;
    }
    System.out.println("Total correct: "+ totalCorrect);
  }
}
