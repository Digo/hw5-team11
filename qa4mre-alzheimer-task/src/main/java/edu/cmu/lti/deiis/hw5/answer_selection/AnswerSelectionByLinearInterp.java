package edu.cmu.lti.deiis.hw5.answer_selection;

import org.apache.uima.jcas.cas.DoubleArray;

import edu.cmu.lti.qalab.types.Answer;

public class AnswerSelectionByLinearInterp extends AnswerSelection {

  double getFinalScore(Answer candAnswer) {
    double result = 0;
    DoubleArray baselineScore = candAnswer.getBaselineScore();
    double PMIScore = candAnswer.getPMIscore();
    
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
    return result;
  }
}
