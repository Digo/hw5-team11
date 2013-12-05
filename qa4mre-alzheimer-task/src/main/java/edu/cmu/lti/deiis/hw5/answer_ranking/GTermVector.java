package edu.cmu.lti.deiis.hw5.answer_ranking;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public class GTermVector {

  public static void main(String[] args) throws IOException {
    GTermVector vec = new GTermVector();
    vec.loadModel("/usr0/home/diw1/data/alzheimer_vector.bin");
    System.out.println(vec.similarWords("Alzheimer"));
    System.out.println(vec.analogy("he", "his", "her"));
  }

  private HashMap<String, float[]> wordMap = new HashMap<String, float[]>();

  private int words;

  private int size;

  private int topNSize = 40;

  /**
   * Load Model
   * 
   * @param path
   *          The path model
   * @throws IOException
   */
  public void loadModel(String path) throws IOException {
    DataInputStream dis = null;
    BufferedInputStream bis = null;
    double len = 0;
    float vector = 0;
    try {
      bis = new BufferedInputStream(new FileInputStream(path));
      dis = new DataInputStream(bis);
      // //Number of words read
      words = Integer.parseInt(readString(dis));
      // //Size
      size = Integer.parseInt(readString(dis));
      System.out.println(">>> Loading vectors words:" + words + " size:" + size);
      String word;
      float[] vectors = null;
      for (int i = 0; i < words; i++) {
        word = readString(dis);
        vectors = new float[size];
        len = 0;
        for (int j = 0; j < size; j++) {
          vector = readFloat(dis);
          len += vector * vector;
          vectors[j] = (float) vector;
        }
        len = Math.sqrt(len);

        for (int j = 0; j < vectors.length; j++) {
          vectors[j] = (float) (vectors[j] / len);
        }
        wordMap.put(word, vectors);
        dis.read();
      }
      System.out.println("<<< Finished Loading vectors.");
    } finally {
      bis.close();
      dis.close();
    }
  }

  private static final int MAX_SIZE = 50;

  public double maxCosineSimilarity(String word1, Collection<String> wordSet) {
    double maxSim = 0;
    for (String word2 : wordSet) {
      double sim = cosineSimilarity(word1, word2);
      if (sim > maxSim) {
        maxSim = sim;
      }
    }
    return maxSim;
  }

  public double cosineSimilarity(String word1, String word2) {
    float[] wordVector1 = getWordVector(word1);
    float[] wordVector2 = getWordVector(word2);
    if (wordVector1 == null || wordVector2 == null) {
      return 0;
    }

    double cosineSimilarity = 0;
    double dotProduct = 0;
    double magnitude1 = 0;
    double magnitude2 = 0;
    for (int i = 0; i < wordVector1.length; i++) {
      dotProduct += wordVector1[i] * wordVector2[i];
      magnitude1 += Math.pow(wordVector1[i], 2); // (a^2)
      magnitude2 += Math.pow(wordVector2[i], 2); // (b^2)
    }

    magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
    magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

    if (magnitude1 != 0.0 | magnitude2 != 0.0) {
      cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
    } else {
      return 0.0;
    }

    return cosineSimilarity;
  }

  /**
   * Get synonyms
   * 
   * @param word
   * @return
   */
  public Set<WordEntry> similarWords(String word) {
    float[] wordVector = getWordVector(word);
    if (wordVector == null) {
      return null;
    }
    Set<Entry<String, float[]>> entrySet = wordMap.entrySet();
    float[] tempVector = null;
    List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
    String name = null;
    for (Entry<String, float[]> entry : entrySet) {
      name = entry.getKey();
      if (name.equals(word)) {
        continue;
      }
      float dist = 0;
      tempVector = entry.getValue();
      for (int i = 0; i < wordVector.length; i++) {
        dist += wordVector[i] * tempVector[i];
      }
      insertTopN(name, dist, wordEntrys);
    }
    return new TreeSet<WordEntry>(wordEntrys);
  }

  /**
   * Synonyms
   * 
   * @return
   */
  public TreeSet<WordEntry> analogy(String word0, String word1, String word2) {
    float[] wv0 = getWordVector(word0);
    float[] wv1 = getWordVector(word1);
    float[] wv2 = getWordVector(word2);

    if (wv1 == null || wv2 == null || wv0 == null) {
      return null;
    }
    float[] wordVector = new float[size];
    for (int i = 0; i < size; i++) {
      wordVector[i] = wv1[i] - wv0[i] + wv2[i];
    }
    float[] tempVector;
    String name;
    List<WordEntry> wordEntrys = new ArrayList<WordEntry>(topNSize);
    for (Entry<String, float[]> entry : wordMap.entrySet()) {
      name = entry.getKey();
      if (name.equals(word0) || name.equals(word1) || name.equals(word2)) {
        continue;
      }
      float dist = 0;
      tempVector = entry.getValue();
      for (int i = 0; i < wordVector.length; i++) {
        dist += wordVector[i] * tempVector[i];
      }
      insertTopN(name, dist, wordEntrys);
    }
    return new TreeSet<WordEntry>(wordEntrys);
  }

  private void insertTopN(String name, float score, List<WordEntry> wordsEntrys) {

    if (wordsEntrys.size() < topNSize) {
      wordsEntrys.add(new WordEntry(name, score));
      return;
    }
    float min = Float.MAX_VALUE;
    int minOffe = 0;
    for (int i = 0; i < topNSize; i++) {
      WordEntry wordEntry = wordsEntrys.get(i);
      if (min > wordEntry.score) {
        min = wordEntry.score;
        minOffe = i;
      }
    }

    if (score > min) {
      wordsEntrys.set(minOffe, new WordEntry(name, score));
    }

  }

  public class WordEntry implements Comparable<WordEntry> {
    public String name;

    public float score;

    public WordEntry(String name, float score) {
      this.name = name;
      this.score = score;
    }

    @Override
    public String toString() {

      return this.name + "\t" + score;
    }

    @Override
    public int compareTo(WordEntry o) {

      if (this.score > o.score) {
        return -1;
      } else {
        return 1;
      }
    }

  }

  /**
   * Get the word vector
   * 
   * @param word
   * @return
   */
  public float[] getWordVector(String word) {
    return wordMap.get(word);
  }

  public static float readFloat(InputStream is) throws IOException {
    byte[] bytes = new byte[4];
    is.read(bytes);
    return getFloat(bytes);
  }

  /**
   * Read a float
   * 
   * @param b
   * @return
   */
  public static float getFloat(byte[] b) {
    int accum = 0;
    accum = accum | (b[0] & 0xff) << 0;
    accum = accum | (b[1] & 0xff) << 8;
    accum = accum | (b[2] & 0xff) << 16;
    accum = accum | (b[3] & 0xff) << 24;
    return Float.intBitsToFloat(accum);
  }

  /**
   * Read a string
   * 
   * @param dis
   * @return
   * @throws IOException
   */
  private static String readString(DataInputStream dis) throws IOException {

    byte[] bytes = new byte[MAX_SIZE];
    byte b = dis.readByte();
    int i = -1;
    StringBuilder sb = new StringBuilder();
    while (b != 32 && b != 10) {
      i++;
      bytes[i] = b;
      b = dis.readByte();
      if (i == 49) {
        sb.append(new String(bytes));
        i = -1;
        bytes = new byte[MAX_SIZE];
      }
    }
    sb.append(new String(bytes, 0, i + 1));
    return sb.toString();
  }

  public int getTopNSize() {
    return topNSize;
  }

  public void setTopNSize(int topNSize) {
    this.topNSize = topNSize;
  }

  public HashMap<String, float[]> getWordMap() {
    return wordMap;
  }

  public int getWords() {
    return words;
  }

  public int getSize() {
    return size;
  }

}
