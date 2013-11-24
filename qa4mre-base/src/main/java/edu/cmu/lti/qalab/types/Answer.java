

/* First created by JCasGen Sun Feb 17 07:02:44 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


import org.apache.uima.jcas.cas.DoubleArray;


/** 
 * Updated by JCasGen Thu Nov 21 15:57:28 EST 2013
 * XML source: /home/diwang/deiis/hw5-team11/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class Answer extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Answer.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Answer() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Answer(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Answer(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Answer(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: id

  /** getter for id - gets 
   * @generated */
  public String getId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_id);}
    
  /** setter for id - sets  
   * @generated */
  public void setId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_id == null)
      jcasType.jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_id, v);}    
   
    
  //*--------------*
  //* Feature: questionId

  /** getter for questionId - gets 
   * @generated */
  public String getQuestionId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_questionId == null)
      jcasType.jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_questionId);}
    
  /** setter for questionId - sets  
   * @generated */
  public void setQuestionId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_questionId == null)
      jcasType.jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_questionId, v);}    
   
    
  //*--------------*
  //* Feature: docId

  /** getter for docId - gets 
   * @generated */
  public String getDocId() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_docId);}
    
  /** setter for docId - sets  
   * @generated */
  public void setDocId(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_docId == null)
      jcasType.jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_docId, v);}    
   
    
  //*--------------*
  //* Feature: synonyms

  /** getter for synonyms - gets 
   * @generated */
  public FSList getSynonyms() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_synonyms)));}
    
  /** setter for synonyms - sets  
   * @generated */
  public void setSynonyms(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_synonyms == null)
      jcasType.jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_synonyms, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: isCorrect

  /** getter for isCorrect - gets 
   * @generated */
  public boolean getIsCorrect() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect);}
    
  /** setter for isCorrect - sets  
   * @generated */
  public void setIsCorrect(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isCorrect == null)
      jcasType.jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isCorrect, v);}    
   
    
  //*--------------*
  //* Feature: isSelected

  /** getter for isSelected - gets 
   * @generated */
  public boolean getIsSelected() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isSelected == null)
      jcasType.jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isSelected);}
    
  /** setter for isSelected - sets  
   * @generated */
  public void setIsSelected(boolean v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_isSelected == null)
      jcasType.jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setBooleanValue(addr, ((Answer_Type)jcasType).casFeatCode_isSelected, v);}    
   
    
  //*--------------*
  //* Feature: nounPhraseList

  /** getter for nounPhraseList - gets 
   * @generated */
  public FSList getNounPhraseList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nounPhraseList == null)
      jcasType.jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nounPhraseList)));}
    
  /** setter for nounPhraseList - sets  
   * @generated */
  public void setNounPhraseList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nounPhraseList == null)
      jcasType.jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nounPhraseList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: nerList

  /** getter for nerList - gets 
   * @generated */
  public FSList getNerList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nerList)));}
    
  /** setter for nerList - sets  
   * @generated */
  public void setNerList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_nerList == null)
      jcasType.jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_nerList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: tokenList

  /** getter for tokenList - gets 
   * @generated */
  public FSList getTokenList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets  
   * @generated */
  public void setTokenList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: dependencies

  /** getter for dependencies - gets 
   * @generated */
  public FSList getDependencies() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies)));}
    
  /** setter for dependencies - sets  
   * @generated */
  public void setDependencies(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_dependencies == null)
      jcasType.jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_dependencies, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: reconText

  /** getter for reconText - gets 
   * @generated */
  public String getReconText() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconText == null)
      jcasType.jcas.throwFeatMissing("reconText", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Answer_Type)jcasType).casFeatCode_reconText);}
    
  /** setter for reconText - sets  
   * @generated */
  public void setReconText(String v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconText == null)
      jcasType.jcas.throwFeatMissing("reconText", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setStringValue(addr, ((Answer_Type)jcasType).casFeatCode_reconText, v);}    
   
    
  //*--------------*
  //* Feature: reconTokenList

  /** getter for reconTokenList - gets 
   * @generated */
  public FSList getReconTokenList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconTokenList == null)
      jcasType.jcas.throwFeatMissing("reconTokenList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconTokenList)));}
    
  /** setter for reconTokenList - sets  
   * @generated */
  public void setReconTokenList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconTokenList == null)
      jcasType.jcas.throwFeatMissing("reconTokenList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconTokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: reconNounPhraseList

  /** getter for reconNounPhraseList - gets 
   * @generated */
  public FSList getReconNounPhraseList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconNounPhraseList == null)
      jcasType.jcas.throwFeatMissing("reconNounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconNounPhraseList)));}
    
  /** setter for reconNounPhraseList - sets  
   * @generated */
  public void setReconNounPhraseList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconNounPhraseList == null)
      jcasType.jcas.throwFeatMissing("reconNounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconNounPhraseList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: reconDependencies

  /** getter for reconDependencies - gets 
   * @generated */
  public FSList getReconDependencies() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconDependencies == null)
      jcasType.jcas.throwFeatMissing("reconDependencies", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconDependencies)));}
    
  /** setter for reconDependencies - sets  
   * @generated */
  public void setReconDependencies(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconDependencies == null)
      jcasType.jcas.throwFeatMissing("reconDependencies", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconDependencies, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: reconNERList

  /** getter for reconNERList - gets 
   * @generated */
  public FSList getReconNERList() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconNERList == null)
      jcasType.jcas.throwFeatMissing("reconNERList", "edu.cmu.lti.qalab.types.Answer");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconNERList)));}
    
  /** setter for reconNERList - sets  
   * @generated */
  public void setReconNERList(FSList v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_reconNERList == null)
      jcasType.jcas.throwFeatMissing("reconNERList", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_reconNERList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: baselineScore

  /** getter for baselineScore - gets 
   * @generated */
  public DoubleArray getBaselineScore() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_baselineScore == null)
      jcasType.jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    return (DoubleArray)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore)));}
    
  /** setter for baselineScore - sets  
   * @generated */
  public void setBaselineScore(DoubleArray v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_baselineScore == null)
      jcasType.jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore, jcasType.ll_cas.ll_getFSRef(v));}    
    
  /** indexed getter for baselineScore - gets an indexed value - 
   * @generated */
  public double getBaselineScore(int i) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_baselineScore == null)
      jcasType.jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore), i);
    return jcasType.ll_cas.ll_getDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore), i);}

  /** indexed setter for baselineScore - sets an indexed value - 
   * @generated */
  public void setBaselineScore(int i, double v) { 
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_baselineScore == null)
      jcasType.jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    jcasType.jcas.checkArrayBounds(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore), i);
    jcasType.ll_cas.ll_setDoubleArrayValue(jcasType.ll_cas.ll_getRefValue(addr, ((Answer_Type)jcasType).casFeatCode_baselineScore), i, v);}
   
    
  //*--------------*
  //* Feature: PMIscore

  /** getter for PMIscore - gets 
   * @generated */
  public double getPMIscore() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_PMIscore == null)
      jcasType.jcas.throwFeatMissing("PMIscore", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Answer_Type)jcasType).casFeatCode_PMIscore);}
    
  /** setter for PMIscore - sets  
   * @generated */
  public void setPMIscore(double v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_PMIscore == null)
      jcasType.jcas.throwFeatMissing("PMIscore", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Answer_Type)jcasType).casFeatCode_PMIscore, v);}    
   
    
  //*--------------*
  //* Feature: finalScore

  /** getter for finalScore - gets stores the final score that combined baseline scores and PMI scores
   * @generated */
  public double getFinalScore() {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_finalScore == null)
      jcasType.jcas.throwFeatMissing("finalScore", "edu.cmu.lti.qalab.types.Answer");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Answer_Type)jcasType).casFeatCode_finalScore);}
    
  /** setter for finalScore - sets stores the final score that combined baseline scores and PMI scores 
   * @generated */
  public void setFinalScore(double v) {
    if (Answer_Type.featOkTst && ((Answer_Type)jcasType).casFeat_finalScore == null)
      jcasType.jcas.throwFeatMissing("finalScore", "edu.cmu.lti.qalab.types.Answer");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Answer_Type)jcasType).casFeatCode_finalScore, v);}    
  }

    