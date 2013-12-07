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

  static final float ETA = (float) 0.005; // step size of Logistic Regression

  static final float EPSILON = (float) 0.0005; // precision of convergence

  ArrayList<ArrayList<Double>> trainingData;

  ArrayList<TestDocument> docs;

//  private double[] weights = new double[] { 3.8314593520155467, -1.0025888266796763,
//      -0.7632613316167572, 0.661848719252655, 2.020545273029166, 1.4984092249587118,
//      1.3626076194333434, 0.0, -3.726978261892141 };
  
//  private double[] weights = new double[] { 3.9247950191258623, -1.2603316370950808,
//      -0.928116497358416, 0.627422361266495, 2.2550598337668557, 1.6512114544847767,
//      1.121563691873766, 0.0, -3.6634907964797896 };

  //stop words
//  private double[] weights = new double[] { 3.160910578554062, -0.799855622480026,
//      -0.7215910735314368, 0.9951180416205584, 2.136732827502515, 1.0921206073440055,
//      0.6133566363680563, 0.0, -3.2618858217365387 };
  
//  private double[] weights = new double[] { 4.063245343651004, -1.5186928403757662,
//      -1.0404048926100649, 0.5731793071757492, 2.4309367397414343, 1.7667627496046086,
//      1.075904593888227, 0.0, -3.651623089909427 };
  
//  private double[] weights = new double[] { 3.924570442405254, -1.2600858997489568,
//      -0.9278975826084308, 0.6275033502677905, 2.254875743735349, 1.6511045125217665,
//      1.1215005084040408, 0.0, -3.663450459924213 };
  
  private double[] weights = new double[] { 1.465784721273484, -0.7212604548627314,
      -0.3236595866897393, -1.3446086628237945, 0.579155766460458, 0.4150030889246327,
      -0.5177520289566909, 0.0, -1.186237557567135 };

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
      if (baselineScore == null){
        return null;
      }
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
