
/* First created by JCasGen Mon Nov 11 17:31:31 EST 2013 */
package edu.cmu.cs.deiis.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;

/** Extend uima.tcas.Annotation by adding componentId field
 * Updated by JCasGen Tue Nov 12 11:36:42 EST 2013
 * @generated */
public class Annotation_Type extends org.apache.uima.jcas.tcas.Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Annotation_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Annotation_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Annotation(addr, Annotation_Type.this);
  			   Annotation_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Annotation(addr, Annotation_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Annotation.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.cs.deiis.types.Annotation");
 
  /** @generated */
  final Feature casFeat_ComponentID;
  /** @generated */
  final int     casFeatCode_ComponentID;
  /** @generated */ 
  public String getComponentID(int addr) {
        if (featOkTst && casFeat_ComponentID == null)
      jcas.throwFeatMissing("ComponentID", "edu.cmu.cs.deiis.types.Annotation");
    return ll_cas.ll_getStringValue(addr, casFeatCode_ComponentID);
  }
  /** @generated */    
  public void setComponentID(int addr, String v) {
        if (featOkTst && casFeat_ComponentID == null)
      jcas.throwFeatMissing("ComponentID", "edu.cmu.cs.deiis.types.Annotation");
    ll_cas.ll_setStringValue(addr, casFeatCode_ComponentID, v);}
    
  
 
  /** @generated */
  final Feature casFeat_confidence;
  /** @generated */
  final int     casFeatCode_confidence;
  /** @generated */ 
  public double getConfidence(int addr) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "edu.cmu.cs.deiis.types.Annotation");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_confidence);
  }
  /** @generated */    
  public void setConfidence(int addr, double v) {
        if (featOkTst && casFeat_confidence == null)
      jcas.throwFeatMissing("confidence", "edu.cmu.cs.deiis.types.Annotation");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_confidence, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Annotation_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_ComponentID = jcas.getRequiredFeatureDE(casType, "ComponentID", "uima.cas.String", featOkTst);
    casFeatCode_ComponentID  = (null == casFeat_ComponentID) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_ComponentID).getCode();

 
    casFeat_confidence = jcas.getRequiredFeatureDE(casType, "confidence", "uima.cas.Double", featOkTst);
    casFeatCode_confidence  = (null == casFeat_confidence) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_confidence).getCode();

  }
}



    