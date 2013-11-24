
/* First created by JCasGen Sun Nov 10 14:41:55 EST 2013 */
package edu.cmu.lti.qalab.types;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.cas.impl.CASImpl;
import org.apache.uima.cas.impl.FSGenerator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.impl.TypeImpl;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.impl.FeatureImpl;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.tcas.Annotation_Type;

/** 
 * Updated by JCasGen Thu Nov 21 15:57:28 EST 2013
 * @generated */
public class NSentence_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (NSentence_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = NSentence_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new NSentence(addr, NSentence_Type.this);
  			   NSentence_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new NSentence(addr, NSentence_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = NSentence.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.qalab.types.NSentence");
 
  /** @generated */
  final Feature casFeat_sentenceList;
  /** @generated */
  final int     casFeatCode_sentenceList;
  /** @generated */ 
  public int getSentenceList(int addr) {
        if (featOkTst && casFeat_sentenceList == null)
      jcas.throwFeatMissing("sentenceList", "edu.cmu.lti.qalab.types.NSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_sentenceList);
  }
  /** @generated */    
  public void setSentenceList(int addr, int v) {
        if (featOkTst && casFeat_sentenceList == null)
      jcas.throwFeatMissing("sentenceList", "edu.cmu.lti.qalab.types.NSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_sentenceList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_text;
  /** @generated */
  final int     casFeatCode_text;
  /** @generated */ 
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.NSentence");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
  /** @generated */    
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.NSentence");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tokenList;
  /** @generated */
  final int     casFeatCode_tokenList;
  /** @generated */ 
  public int getTokenList(int addr) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.NSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokenList);
  }
  /** @generated */    
  public void setTokenList(int addr, int v) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.NSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokenList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_phraseList;
  /** @generated */
  final int     casFeatCode_phraseList;
  /** @generated */ 
  public int getPhraseList(int addr) {
        if (featOkTst && casFeat_phraseList == null)
      jcas.throwFeatMissing("phraseList", "edu.cmu.lti.qalab.types.NSentence");
    return ll_cas.ll_getRefValue(addr, casFeatCode_phraseList);
  }
  /** @generated */    
  public void setPhraseList(int addr, int v) {
        if (featOkTst && casFeat_phraseList == null)
      jcas.throwFeatMissing("phraseList", "edu.cmu.lti.qalab.types.NSentence");
    ll_cas.ll_setRefValue(addr, casFeatCode_phraseList, v);}
    
  



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public NSentence_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_sentenceList = jcas.getRequiredFeatureDE(casType, "sentenceList", "uima.cas.FSList", featOkTst);
    casFeatCode_sentenceList  = (null == casFeat_sentenceList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_sentenceList).getCode();

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_tokenList = jcas.getRequiredFeatureDE(casType, "tokenList", "uima.cas.FSList", featOkTst);
    casFeatCode_tokenList  = (null == casFeat_tokenList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenList).getCode();

 
    casFeat_phraseList = jcas.getRequiredFeatureDE(casType, "phraseList", "uima.cas.FSList", featOkTst);
    casFeatCode_phraseList  = (null == casFeat_phraseList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_phraseList).getCode();

  }
}



    