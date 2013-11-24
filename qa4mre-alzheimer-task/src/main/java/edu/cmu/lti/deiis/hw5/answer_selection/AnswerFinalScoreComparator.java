package edu.cmu.lti.deiis.hw5.answer_selection;

import java.util.Comparator;

import edu.cmu.lti.qalab.types.Answer;

public class AnswerFinalScoreComparator implements Comparator<Answer> {

  @Override
  public int compare(Answer o1, Answer o2) {
    //sort from large to small
    return Double.compare(o2.getFinalScore(), o1.getFinalScore());
  }

}
