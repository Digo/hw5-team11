

/* First created by JCasGen Mon Nov 11 17:31:31 EST 2013 */
package edu.cmu.cs.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Extend uima.tcas.Annotation by adding componentId field
 * Updated by JCasGen Wed Nov 13 11:09:42 EST 2013
 * XML source: /Users/xiaohua/git/hw5-team11/qa4mre-base/src/main/resources/TypeSystemDescriptor.xml
 * @generated */
public class Annotation extends org.apache.uima.jcas.tcas.Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Annotation.class);
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
  protected Annotation() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Annotation(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Annotation(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Annotation(JCas jcas, int begin, int end) {
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
  //* Feature: ComponentID

  /** getter for ComponentID - gets Source of the annotation
   * @generated */
  public String getComponentID() {
    if (Annotation_Type.featOkTst && ((Annotation_Type)jcasType).casFeat_ComponentID == null)
      jcasType.jcas.throwFeatMissing("ComponentID", "edu.cmu.cs.deiis.types.Annotation");
    return jcasType.ll_cas.ll_getStringValue(addr, ((Annotation_Type)jcasType).casFeatCode_ComponentID);}
    
  /** setter for ComponentID - sets Source of the annotation 
   * @generated */
  public void setComponentID(String v) {
    if (Annotation_Type.featOkTst && ((Annotation_Type)jcasType).casFeat_ComponentID == null)
      jcasType.jcas.throwFeatMissing("ComponentID", "edu.cmu.cs.deiis.types.Annotation");
    jcasType.ll_cas.ll_setStringValue(addr, ((Annotation_Type)jcasType).casFeatCode_ComponentID, v);}    
   
    
  //*--------------*
  //* Feature: confidence

  /** getter for confidence - gets Confidence score of the annotation
   * @generated */
  public double getConfidence() {
    if (Annotation_Type.featOkTst && ((Annotation_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "edu.cmu.cs.deiis.types.Annotation");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Annotation_Type)jcasType).casFeatCode_confidence);}
    
  /** setter for confidence - sets Confidence score of the annotation 
   * @generated */
  public void setConfidence(double v) {
    if (Annotation_Type.featOkTst && ((Annotation_Type)jcasType).casFeat_confidence == null)
      jcasType.jcas.throwFeatMissing("confidence", "edu.cmu.cs.deiis.types.Annotation");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Annotation_Type)jcasType).casFeatCode_confidence, v);}    
  }

    