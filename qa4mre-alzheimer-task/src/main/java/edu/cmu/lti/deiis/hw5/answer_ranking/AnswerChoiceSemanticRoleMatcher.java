package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.util.Collection;

import org.apache.uima.jcas.cas.FSArray;
import org.uimafit.util.JCasUtil;

import edu.cmu.cs.deiis.types.SemanticRole;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.Question;
import edu.cmu.lti.qalab.types.Sentence;

public class AnswerChoiceSemanticRoleMatcher {
  private static boolean DEBUG = false;

  public static Double getSemanticRoleSim(Question question, Answer answer,
          Collection<Sentence> sentList) {

    String questionText = question.getText();
    questionText = AnswerChoiceBaselineScorer.reconstruct(question, "");
    String answerText = answer.getText();
    double score = 0;

    for (Sentence sent : sentList) {
      FSArray srlArr = sent.getSrlArray();
      if (srlArr != null) {
        Collection<SemanticRole> srlList = JCasUtil.select(sent.getSrlArray(), SemanticRole.class);
        for (SemanticRole sr : srlList) {
          if (sr.getParent() == null) {
            FSArray children = sr.getChildren();
            if (children != null && children.size() >= 2) {
              SemanticRole arg0 = (SemanticRole) children.get(0);
              SemanticRole arg1 = (SemanticRole) children.get(1);
              // TODO arg2 ?
              if (( RoleStringSim(arg0, sent, questionText) > 0.5 && RoleStringSim(arg1, sent, answerText) > 0.5) || 
                      (RoleStringSim(arg1, sent, questionText) > 0.5 && RoleStringSim(arg0, sent, answerText) > 0.5) ){
                score = 1;
                if (DEBUG){
                  System.err.println( (answer.getIsCorrect()? "Correct":"Wrong") + " MATCH <" + getRoleText(arg0, sent) + ">  <" + getRoleText(arg1, sent) +">");
                }
                
              }
            }
          }
        }
      }
    }
    return score;
  }
  
  static String getRoleText(SemanticRole sr, Sentence sent) {
    int beginInSent = sr.getBegin() - sent.getBegin();
    int endInSent = sr.getEnd() - sent.getBegin();
    String sentText = sent.getText();
    if (beginInSent < 0 || (endInSent - beginInSent) <= 0 || endInSent > sentText.length()) {
      System.err.println(sr);
      return "WrongSRLAnno";
    } else {
      return sentText.substring(beginInSent, endInSent);
    }

  }

  static double RoleStringSim(SemanticRole sr, Sentence sent, String qa) {
    return RoleStringSim(getRoleText(sr, sent), qa);
  }

  static double RoleStringSim(String role, String qa) {
    if (role.contains(qa) || qa.contains(role)) {
      return 1;
    } else {
      return 0;
    }
  }
}
