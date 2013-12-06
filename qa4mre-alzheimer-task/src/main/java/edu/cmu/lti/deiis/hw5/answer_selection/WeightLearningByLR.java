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

public class WeightLearningByLR extends AnswerSelection {

  // static final int L = 1; // smoothing magnitude

  // static final int Y_CLASSES = 2; // number of values Y can take

  static final float ETA = (float) 0.01; // step size of Logistic Regression

  static final float EPSILON = (float) 0.001; // precision of convergence

  ArrayList<ArrayList<Double>> trainingData;

  ArrayList<TestDocument> docs;

  private double[] weights = new double[] { 3.8314593520155467, -1.0025888266796763,
      -0.7632613316167572, 0.661848719252655, 2.020545273029166, 1.4984092249587118,
      1.3626076194333434, 0.0, -3.726978261892141 };

  @Override
  public void initialize(UimaContext context) throws ResourceInitializationException {
    super.initialize(context);
    trainingData = new ArrayList<ArrayList<Double>>();
    docs = new ArrayList<TestDocument>();
  }

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    super.process(aJCas);

    TestDocument testDoc = Utils.getTestDocumentFromCAS(aJCas);

    ArrayList<QuestionAnswerSet> qaSet = Utils.fromFSListToCollection(testDoc.getQaList(),
            QuestionAnswerSet.class);
    loadData(qaSet, trainingData);
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

    if (dataInstance == null) {
      // for "none of above"
      return 0;
    }

    for (int i = 0; i < weights.length; i++) {
      sum += dataInstance.get(i) * weights[i];
    }
    return (1 - 1 / (1 + Math.exp(sum)));
  }

  private void loadData(ArrayList<QuestionAnswerSet> qaSet, ArrayList<ArrayList<Double>> data) {
    for (int i = 0; i < qaSet.size(); i++) {
      // System.out.println("Processing Question No." + (i + 1));
      ArrayList<Answer> choiceList = Utils.fromFSListToCollection(qaSet.get(i).getAnswerList(),
              Answer.class);
      for (int j = 0; j < choiceList.size(); j++) {
        Answer candAnswer = choiceList.get(j);
        ArrayList<Double> instance = getInstance(candAnswer);
        if (instance == null) {
          continue;
        } else {
          data.add(instance);
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

  private ArrayList<Double> getInstance(Answer candAnswer) {
    if (candAnswer.getText().equals("None of the above")) {
      return null;
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
      return dataInstance;
    }
  }

  @Override
  double getFinalScore(Answer candAnswer) {
    ArrayList<Double> instance = getInstance(candAnswer);
    return pXY(instance, weights);
  }

  @Override
  public void collectionProcessComplete() throws AnalysisEngineProcessException {
    super.collectionProcessComplete();

    int featureNum = trainingData.get(0).size() - 1;
    double[] weights = new double[featureNum];
    double[] tmpWeights = doIteration(trainingData, weights);
    while (!isConverged(weights, tmpWeights)) {
      for (int i = 0; i < weights.length; i++) {
        weights[i] = tmpWeights[i];
      }
      tmpWeights = doIteration(trainingData, weights);
    }
    System.out.println("weight: " + Arrays.toString(weights));
  }
}
