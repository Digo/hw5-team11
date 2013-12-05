package edu.cmu.lti.deiis.hw5.answer_selection;

import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang.ArrayUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.resource.ResourceInitializationException;
import edu.cmu.lti.qalab.types.Answer;
import edu.cmu.lti.qalab.types.QuestionAnswerSet;
import edu.cmu.lti.qalab.types.TestDocument;
import edu.cmu.lti.qalab.utils.Utils;

public class WeightLearningByLR extends JCasAnnotator_ImplBase {

//  static final int L = 1; // smoothing magnitude

//  static final int Y_CLASSES = 2; // number of values Y can take

  static final float ETA = (float) 0.01; // step size of Logistic Regression

  static final float EPSILON = (float) 0.001; // precision of convergence

  ArrayList<ArrayList<Double>> trainingData;
  ArrayList<TestDocument> docs;

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    trainingData = new ArrayList<ArrayList<Double>>();
    docs = new ArrayList<TestDocument>();
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);
    
    ArrayList<QuestionAnswerSet> qaSet = Utils.fromFSListToCollection(testDoc.getQaList(),
            QuestionAnswerSet.class);
    loadData(qaSet, trainingData);
    int featureNum = trainingData.get(0).size() - 1;
    testDoc.setLRWeights(new DoubleArray(aJCas, featureNum));
    docs.add(testDoc);
    
//    System.out.println("Best weights:");
//    System.out.println(weights.toString());
  }

  private double[] doIteration(ArrayList<ArrayList<Double>> trainingData, double[] weights) {
    int featureNum = weights.length;
    int dataSize = trainingData.size();
    double[] result = new double[weights.length];
    for (int i = 0; i < featureNum; i++) {
      double sum = 0;
      for (int j = 0; j < dataSize; j++) {
        sum += trainingData.get(j).get(i)
                * (trainingData.get(j).get(featureNum) - pXY(trainingData.get(j), weights));
      }
      result[i] = weights[i] + ETA * sum;
    }
    return result;
  }

  // computation of estimated P(Y=1)
  private static double pXY(ArrayList<Double> dataInstance, double[] weights) {
    double sum = 0;
    for (int i = 0; i < weights.length; i++) {
      sum += dataInstance.get(i) * weights[i];
    }
    return (1 - 1 / (1 + Math.exp(sum)));
  }

  private void loadData(ArrayList<QuestionAnswerSet> qaSet, ArrayList<ArrayList<Double>> data) {
    for (int i = 0; i < qaSet.size(); i++) {
//      System.out.println("Processing Question No." + (i + 1));
      ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      for (int j = 0; j < choiceList.size(); j++) {
        Answer candAnswer = choiceList.get(j);
        if (candAnswer.getText().equals("None of the above")) {
          continue;
        } else {
          DoubleArray baselineScore = candAnswer.getBaselineScore();
          Double[] doubleArray = ArrayUtils.toObject(baselineScore.toArray());
          ArrayList<Double> dataInstance = new ArrayList<Double>();
          dataInstance.addAll(Arrays.asList(doubleArray));
          dataInstance.add((double) 1);

          if (candAnswer.getIsCorrect()) {
            dataInstance.add((double) 1);
          } else {
            dataInstance.add((double) 0);
          }
          data.add(dataInstance);
        }
      }
    }
  }

  private boolean isConverged(double[] w1, double[] w2) {
    double sum = 0;
    for (int i = 0; i < w1.length; i++) {
      sum += Math.pow((w1[i] - w2[i]), 2);
    }
    sum = Math.pow(sum, 0.5);
    if (sum <= EPSILON)
      return true;
    else
      return false;
  }

  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    int featureNum = trainingData.get(0).size() - 1;
    double[] weights = new double[featureNum];
    double[] tmpWeights = doIteration(trainingData, weights);
    while (!isConverged(weights, tmpWeights)) {
      for (int i = 0; i < weights.length; i++) {
        weights[i] = tmpWeights[i];
      }
      tmpWeights = doIteration(trainingData, weights);
    }
    System.out.println(Arrays.toString(weights));
//    for (int i = 0; i < docs.size(); i++) {
//      for (int j = 0; j < featureNum; j++) {
//        docs.get(i).getLRWeights().set(j, weights[j]);
//      }
//    }
////    System.out.println("Best weights (last term is the contant)");
//    System.out.println(Arrays.toString(weights));
//    System.out.println("Probabilities of each answer being correct:");
//    for (int i = 0; i < trainingData.size(); i ++) {
//      System.out.println(pXY(trainingData.get(i),weights));
//    }
//    System.out.println("Done!");
  }
}
