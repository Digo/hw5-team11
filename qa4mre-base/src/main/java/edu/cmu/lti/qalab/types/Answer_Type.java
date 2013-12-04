
/* First created by JCasGen Sun Feb 17 07:02:44 EST 2013 */
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
 * Updated by JCasGen Tue Dec 03 17:04:20 EST 2013
 * @generated */
public class Answer_Type extends Annotation_Type {
  /** @generated */
  @Override
  protected FSGenerator getFSGenerator() {return fsGenerator;}
  /** @generated */
  private final FSGenerator fsGenerator = 
    new FSGenerator() {
      public FeatureStructure createFS(int addr, CASImpl cas) {
  			 if (Answer_Type.this.useExistingInstance) {
  			   // Return eq fs instance if already created
  		     FeatureStructure fs = Answer_Type.this.jcas.getJfsFromCaddr(addr);
  		     if (null == fs) {
  		       fs = new Answer(addr, Answer_Type.this);
  			   Answer_Type.this.jcas.putJfsFromCaddr(addr, fs);
  			   return fs;
  		     }
  		     return fs;
        } else return new Answer(addr, Answer_Type.this);
  	  }
    };
  /** @generated */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = Answer.typeIndexID;
  /** @generated 
     @modifiable */
  @SuppressWarnings ("hiding")
  public final static boolean featOkTst = JCasRegistry.getFeatOkTst("edu.cmu.lti.qalab.types.Answer");
 
  /** @generated */
  final Feature casFeat_text;
  /** @generated */
  final int     casFeatCode_text;
  /** @generated */ 
  public String getText(int addr) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_text);
  }
  /** @generated */    
  public void setText(int addr, String v) {
        if (featOkTst && casFeat_text == null)
      jcas.throwFeatMissing("text", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setStringValue(addr, casFeatCode_text, v);}
    
  
 
  /** @generated */
  final Feature casFeat_id;
  /** @generated */
  final int     casFeatCode_id;
  /** @generated */ 
  public String getId(int addr) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_id);
  }
  /** @generated */    
  public void setId(int addr, String v) {
        if (featOkTst && casFeat_id == null)
      jcas.throwFeatMissing("id", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setStringValue(addr, casFeatCode_id, v);}
    
  
 
  /** @generated */
  final Feature casFeat_questionId;
  /** @generated */
  final int     casFeatCode_questionId;
  /** @generated */ 
  public String getQuestionId(int addr) {
        if (featOkTst && casFeat_questionId == null)
      jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_questionId);
  }
  /** @generated */    
  public void setQuestionId(int addr, String v) {
        if (featOkTst && casFeat_questionId == null)
      jcas.throwFeatMissing("questionId", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setStringValue(addr, casFeatCode_questionId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_docId;
  /** @generated */
  final int     casFeatCode_docId;
  /** @generated */ 
  public String getDocId(int addr) {
        if (featOkTst && casFeat_docId == null)
      jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_docId);
  }
  /** @generated */    
  public void setDocId(int addr, String v) {
        if (featOkTst && casFeat_docId == null)
      jcas.throwFeatMissing("docId", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setStringValue(addr, casFeatCode_docId, v);}
    
  
 
  /** @generated */
  final Feature casFeat_synonyms;
  /** @generated */
  final int     casFeatCode_synonyms;
  /** @generated */ 
  public int getSynonyms(int addr) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_synonyms);
  }
  /** @generated */    
  public void setSynonyms(int addr, int v) {
        if (featOkTst && casFeat_synonyms == null)
      jcas.throwFeatMissing("synonyms", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_synonyms, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isCorrect;
  /** @generated */
  final int     casFeatCode_isCorrect;
  /** @generated */ 
  public boolean getIsCorrect(int addr) {
        if (featOkTst && casFeat_isCorrect == null)
      jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isCorrect);
  }
  /** @generated */    
  public void setIsCorrect(int addr, boolean v) {
        if (featOkTst && casFeat_isCorrect == null)
      jcas.throwFeatMissing("isCorrect", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isCorrect, v);}
    
  
 
  /** @generated */
  final Feature casFeat_isSelected;
  /** @generated */
  final int     casFeatCode_isSelected;
  /** @generated */ 
  public boolean getIsSelected(int addr) {
        if (featOkTst && casFeat_isSelected == null)
      jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getBooleanValue(addr, casFeatCode_isSelected);
  }
  /** @generated */    
  public void setIsSelected(int addr, boolean v) {
        if (featOkTst && casFeat_isSelected == null)
      jcas.throwFeatMissing("isSelected", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setBooleanValue(addr, casFeatCode_isSelected, v);}
    
  
 
  /** @generated */
  final Feature casFeat_nounPhraseList;
  /** @generated */
  final int     casFeatCode_nounPhraseList;
  /** @generated */ 
  public int getNounPhraseList(int addr) {
        if (featOkTst && casFeat_nounPhraseList == null)
      jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_nounPhraseList);
  }
  /** @generated */    
  public void setNounPhraseList(int addr, int v) {
        if (featOkTst && casFeat_nounPhraseList == null)
      jcas.throwFeatMissing("nounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_nounPhraseList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_nerList;
  /** @generated */
  final int     casFeatCode_nerList;
  /** @generated */ 
  public int getNerList(int addr) {
        if (featOkTst && casFeat_nerList == null)
      jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_nerList);
  }
  /** @generated */    
  public void setNerList(int addr, int v) {
        if (featOkTst && casFeat_nerList == null)
      jcas.throwFeatMissing("nerList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_nerList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_tokenList;
  /** @generated */
  final int     casFeatCode_tokenList;
  /** @generated */ 
  public int getTokenList(int addr) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_tokenList);
  }
  /** @generated */    
  public void setTokenList(int addr, int v) {
        if (featOkTst && casFeat_tokenList == null)
      jcas.throwFeatMissing("tokenList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_tokenList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_dependencies;
  /** @generated */
  final int     casFeatCode_dependencies;
  /** @generated */ 
  public int getDependencies(int addr) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_dependencies);
  }
  /** @generated */    
  public void setDependencies(int addr, int v) {
        if (featOkTst && casFeat_dependencies == null)
      jcas.throwFeatMissing("dependencies", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_dependencies, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reconText;
  /** @generated */
  final int     casFeatCode_reconText;
  /** @generated */ 
  public String getReconText(int addr) {
        if (featOkTst && casFeat_reconText == null)
      jcas.throwFeatMissing("reconText", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getStringValue(addr, casFeatCode_reconText);
  }
  /** @generated */    
  public void setReconText(int addr, String v) {
        if (featOkTst && casFeat_reconText == null)
      jcas.throwFeatMissing("reconText", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setStringValue(addr, casFeatCode_reconText, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reconTokenList;
  /** @generated */
  final int     casFeatCode_reconTokenList;
  /** @generated */ 
  public int getReconTokenList(int addr) {
        if (featOkTst && casFeat_reconTokenList == null)
      jcas.throwFeatMissing("reconTokenList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_reconTokenList);
  }
  /** @generated */    
  public void setReconTokenList(int addr, int v) {
        if (featOkTst && casFeat_reconTokenList == null)
      jcas.throwFeatMissing("reconTokenList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_reconTokenList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reconNounPhraseList;
  /** @generated */
  final int     casFeatCode_reconNounPhraseList;
  /** @generated */ 
  public int getReconNounPhraseList(int addr) {
        if (featOkTst && casFeat_reconNounPhraseList == null)
      jcas.throwFeatMissing("reconNounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_reconNounPhraseList);
  }
  /** @generated */    
  public void setReconNounPhraseList(int addr, int v) {
        if (featOkTst && casFeat_reconNounPhraseList == null)
      jcas.throwFeatMissing("reconNounPhraseList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_reconNounPhraseList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reconDependencies;
  /** @generated */
  final int     casFeatCode_reconDependencies;
  /** @generated */ 
  public int getReconDependencies(int addr) {
        if (featOkTst && casFeat_reconDependencies == null)
      jcas.throwFeatMissing("reconDependencies", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_reconDependencies);
  }
  /** @generated */    
  public void setReconDependencies(int addr, int v) {
        if (featOkTst && casFeat_reconDependencies == null)
      jcas.throwFeatMissing("reconDependencies", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_reconDependencies, v);}
    
  
 
  /** @generated */
  final Feature casFeat_reconNERList;
  /** @generated */
  final int     casFeatCode_reconNERList;
  /** @generated */ 
  public int getReconNERList(int addr) {
        if (featOkTst && casFeat_reconNERList == null)
      jcas.throwFeatMissing("reconNERList", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_reconNERList);
  }
  /** @generated */    
  public void setReconNERList(int addr, int v) {
        if (featOkTst && casFeat_reconNERList == null)
      jcas.throwFeatMissing("reconNERList", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_reconNERList, v);}
    
  
 
  /** @generated */
  final Feature casFeat_baselineScore;
  /** @generated */
  final int     casFeatCode_baselineScore;
  /** @generated */ 
  public int getBaselineScore(int addr) {
        if (featOkTst && casFeat_baselineScore == null)
      jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore);
  }
  /** @generated */    
  public void setBaselineScore(int addr, int v) {
        if (featOkTst && casFeat_baselineScore == null)
      jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_baselineScore, v);}
    
   /** @generated */
  public double getBaselineScore(int addr, int i) {
        if (featOkTst && casFeat_baselineScore == null)
      jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i);
  return ll_cas.ll_getDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i);
  }
   
  /** @generated */ 
  public void setBaselineScore(int addr, int i, double v) {
        if (featOkTst && casFeat_baselineScore == null)
      jcas.throwFeatMissing("baselineScore", "edu.cmu.lti.qalab.types.Answer");
    if (lowLevelTypeChecks)
      ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i);
    ll_cas.ll_setDoubleArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_baselineScore), i, v);
  }
 
 
  /** @generated */
  final Feature casFeat_PMIscore;
  /** @generated */
  final int     casFeatCode_PMIscore;
  /** @generated */ 
  public double getPMIscore(int addr) {
        if (featOkTst && casFeat_PMIscore == null)
      jcas.throwFeatMissing("PMIscore", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_PMIscore);
  }
  /** @generated */    
  public void setPMIscore(int addr, double v) {
        if (featOkTst && casFeat_PMIscore == null)
      jcas.throwFeatMissing("PMIscore", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_PMIscore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_finalScore;
  /** @generated */
  final int     casFeatCode_finalScore;
  /** @generated */ 
  public double getFinalScore(int addr) {
        if (featOkTst && casFeat_finalScore == null)
      jcas.throwFeatMissing("finalScore", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getDoubleValue(addr, casFeatCode_finalScore);
  }
  /** @generated */    
  public void setFinalScore(int addr, double v) {
        if (featOkTst && casFeat_finalScore == null)
      jcas.throwFeatMissing("finalScore", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setDoubleValue(addr, casFeatCode_finalScore, v);}
    
  
 
  /** @generated */
  final Feature casFeat_maxScoredNSentences;
  /** @generated */
  final int     casFeatCode_maxScoredNSentences;
  /** @generated */ 
  public int getMaxScoredNSentences(int addr) {
        if (featOkTst && casFeat_maxScoredNSentences == null)
      jcas.throwFeatMissing("maxScoredNSentences", "edu.cmu.lti.qalab.types.Answer");
    return ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences);
  }
  /** @generated */    
  public void setMaxScoredNSentences(int addr, int v) {
        if (featOkTst && casFeat_maxScoredNSentences == null)
      jcas.throwFeatMissing("maxScoredNSentences", "edu.cmu.lti.qalab.types.Answer");
    ll_cas.ll_setRefValue(addr, casFeatCode_maxScoredNSentences, v);}
    
   /** @generated */
  public int getMaxScoredNSentences(int addr, int i) {
        if (featOkTst && casFeat_maxScoredNSentences == null)
      jcas.throwFeatMissing("maxScoredNSentences", "edu.cmu.lti.qalab.types.Answer");
    if (lowLevelTypeChecks)
      return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i);
  return ll_cas.ll_getRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i);
  }
   
  /** @generated */ 
  public void setMaxScoredNSentences(int addr, int i, int v) {
        if (featOkTst && casFeat_maxScoredNSentences == null)
      jcas.throwFeatMissing("maxScoredNSentences", "edu.cmu.lti.qalab.types.Answer");
    if (lowLevelTypeChecks)
      ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i, v, true);
    jcas.checkArrayBounds(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i);
    ll_cas.ll_setRefArrayValue(ll_cas.ll_getRefValue(addr, casFeatCode_maxScoredNSentences), i, v);
  }
 



  /** initialize variables to correspond with Cas Type and Features
	* @generated */
  public Answer_Type(JCas jcas, Type casType) {
    super(jcas, casType);
    casImpl.getFSClassRegistry().addGeneratorForType((TypeImpl)this.casType, getFSGenerator());

 
    casFeat_text = jcas.getRequiredFeatureDE(casType, "text", "uima.cas.String", featOkTst);
    casFeatCode_text  = (null == casFeat_text) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_text).getCode();

 
    casFeat_id = jcas.getRequiredFeatureDE(casType, "id", "uima.cas.String", featOkTst);
    casFeatCode_id  = (null == casFeat_id) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_id).getCode();

 
    casFeat_questionId = jcas.getRequiredFeatureDE(casType, "questionId", "uima.cas.String", featOkTst);
    casFeatCode_questionId  = (null == casFeat_questionId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_questionId).getCode();

 
    casFeat_docId = jcas.getRequiredFeatureDE(casType, "docId", "uima.cas.String", featOkTst);
    casFeatCode_docId  = (null == casFeat_docId) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_docId).getCode();

 
    casFeat_synonyms = jcas.getRequiredFeatureDE(casType, "synonyms", "uima.cas.FSList", featOkTst);
    casFeatCode_synonyms  = (null == casFeat_synonyms) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_synonyms).getCode();

 
    casFeat_isCorrect = jcas.getRequiredFeatureDE(casType, "isCorrect", "uima.cas.Boolean", featOkTst);
    casFeatCode_isCorrect  = (null == casFeat_isCorrect) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isCorrect).getCode();

 
    casFeat_isSelected = jcas.getRequiredFeatureDE(casType, "isSelected", "uima.cas.Boolean", featOkTst);
    casFeatCode_isSelected  = (null == casFeat_isSelected) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_isSelected).getCode();

 
    casFeat_nounPhraseList = jcas.getRequiredFeatureDE(casType, "nounPhraseList", "uima.cas.FSList", featOkTst);
    casFeatCode_nounPhraseList  = (null == casFeat_nounPhraseList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_nounPhraseList).getCode();

 
    casFeat_nerList = jcas.getRequiredFeatureDE(casType, "nerList", "uima.cas.FSList", featOkTst);
    casFeatCode_nerList  = (null == casFeat_nerList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_nerList).getCode();

 
    casFeat_tokenList = jcas.getRequiredFeatureDE(casType, "tokenList", "uima.cas.FSList", featOkTst);
    casFeatCode_tokenList  = (null == casFeat_tokenList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_tokenList).getCode();

 
    casFeat_dependencies = jcas.getRequiredFeatureDE(casType, "dependencies", "uima.cas.FSList", featOkTst);
    casFeatCode_dependencies  = (null == casFeat_dependencies) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_dependencies).getCode();

 
    casFeat_reconText = jcas.getRequiredFeatureDE(casType, "reconText", "uima.cas.String", featOkTst);
    casFeatCode_reconText  = (null == casFeat_reconText) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reconText).getCode();

 
    casFeat_reconTokenList = jcas.getRequiredFeatureDE(casType, "reconTokenList", "uima.cas.FSList", featOkTst);
    casFeatCode_reconTokenList  = (null == casFeat_reconTokenList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reconTokenList).getCode();

 
    casFeat_reconNounPhraseList = jcas.getRequiredFeatureDE(casType, "reconNounPhraseList", "uima.cas.FSList", featOkTst);
    casFeatCode_reconNounPhraseList  = (null == casFeat_reconNounPhraseList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reconNounPhraseList).getCode();

 
    casFeat_reconDependencies = jcas.getRequiredFeatureDE(casType, "reconDependencies", "uima.cas.FSList", featOkTst);
    casFeatCode_reconDependencies  = (null == casFeat_reconDependencies) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reconDependencies).getCode();

 
    casFeat_reconNERList = jcas.getRequiredFeatureDE(casType, "reconNERList", "uima.cas.FSList", featOkTst);
    casFeatCode_reconNERList  = (null == casFeat_reconNERList) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_reconNERList).getCode();

 
    casFeat_baselineScore = jcas.getRequiredFeatureDE(casType, "baselineScore", "uima.cas.DoubleArray", featOkTst);
    casFeatCode_baselineScore  = (null == casFeat_baselineScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_baselineScore).getCode();

 
    casFeat_PMIscore = jcas.getRequiredFeatureDE(casType, "PMIscore", "uima.cas.Double", featOkTst);
    casFeatCode_PMIscore  = (null == casFeat_PMIscore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_PMIscore).getCode();

 
    casFeat_finalScore = jcas.getRequiredFeatureDE(casType, "finalScore", "uima.cas.Double", featOkTst);
    casFeatCode_finalScore  = (null == casFeat_finalScore) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_finalScore).getCode();

 
    casFeat_maxScoredNSentences = jcas.getRequiredFeatureDE(casType, "maxScoredNSentences", "uima.cas.FSArray", featOkTst);
    casFeatCode_maxScoredNSentences  = (null == casFeat_maxScoredNSentences) ? JCas.INVALID_FEATURE_CODE : ((FeatureImpl)casFeat_maxScoredNSentences).getCode();

  }
}



    