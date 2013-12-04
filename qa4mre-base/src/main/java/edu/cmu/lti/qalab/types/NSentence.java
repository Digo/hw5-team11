

/* First created by JCasGen Sun Nov 10 14:41:55 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;

import org.apache.uima.jcas.cas.FSList;
import org.apache.uima.jcas.tcas.Annotation;


/** 
 * Updated by JCasGen Tue Dec 03 17:04:20 EST 2013
 * XML source: /home/diwang/deiis/hw5-team11/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class NSentence extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(NSentence.class);
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
  protected NSentence() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public NSentence(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public NSentence(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public NSentence(JCas jcas, int begin, int end) {
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
  //* Feature: sentenceList

  /** getter for sentenceList - gets 
   * @generated */
  public FSList getSentenceList() {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_sentenceList == null)
      jcasType.jcas.throwFeatMissing("sentenceList", "edu.cmu.lti.qalab.types.NSentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_sentenceList)));}
    
  /** setter for sentenceList - sets  
   * @generated */
  public void setSentenceList(FSList v) {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_sentenceList == null)
      jcasType.jcas.throwFeatMissing("sentenceList", "edu.cmu.lti.qalab.types.NSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_sentenceList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: text

  /** getter for text - gets 
   * @generated */
  public String getText() {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.NSentence");
    return jcasType.ll_cas.ll_getStringValue(addr, ((NSentence_Type)jcasType).casFeatCode_text);}
    
  /** setter for text - sets  
   * @generated */
  public void setText(String v) {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_text == null)
      jcasType.jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.NSentence");
    jcasType.ll_cas.ll_setStringValue(addr, ((NSentence_Type)jcasType).casFeatCode_text, v);}    
   
    
  //*--------------*
  //* Feature: tokenList

  /** getter for tokenList - gets 
   * @generated */
  public FSList getTokenList() {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.NSentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_tokenList)));}
    
  /** setter for tokenList - sets  
   * @generated */
  public void setTokenList(FSList v) {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_tokenList == null)
      jcasType.jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.NSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_tokenList, jcasType.ll_cas.ll_getFSRef(v));}    
   
    
  //*--------------*
  //* Feature: phraseList

  /** getter for phraseList - gets 
   * @generated */
  public FSList getPhraseList() {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_phraseList == null)
      jcasType.jcas.throwFeatMissing("phraseList", "edu.cmu.lti.qalab.types.NSentence");
    return (FSList)(jcasType.ll_cas.ll_getFSForRef(jcasType.ll_cas.ll_getRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_phraseList)));}
    
  /** setter for phraseList - sets  
   * @generated */
  public void setPhraseList(FSList v) {
    if (NSentence_Type.featOkTst && ((NSentence_Type)jcasType).casFeat_phraseList == null)
      jcasType.jcas.throwFeatMissing("phraseList", "edu.cmu.lti.qalab.types.NSentence");
    jcasType.ll_cas.ll_setRefValue(addr, ((NSentence_Type)jcasType).casFeatCode_phraseList, jcasType.ll_cas.ll_getFSRef(v));}    
  }

    