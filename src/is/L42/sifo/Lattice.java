package is.L42.sifo;

import static is.L42.tools.General.L;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import is.L42.tools.General;

public abstract class Lattice<T> {
  // all the invariants: for example, no circles
  // make sure 1 top,
  // bottom marker?P p=P.pAny;
  // constructor takes in input the top
  // lub and subtype
  // look to the varius tests, like testSifo, and make a test suit for Lattice
  
  /**
   * Key: some Level
   * Value: All direct higher Levels
   */
  Map<T, ArrayList<T>> inner;
  T top;

  public Lattice(T top) {
    this.inner = new HashMap<>();
    this.inner.put(top, new ArrayList<>());
    this.top = top;
  }
  
  public Lattice() {
    this.inner = new HashMap<>();
  }

  public void traverseInterfaceHierarchy(T level) {
    for (T nextLevel : lowerLevels(level)) {
      addElementToKey(nextLevel, level);
      traverseInterfaceHierarchy(nextLevel);
    }
  }

  /**
   * return the direct lower levels of p
   * @param p the higher node
   * @return list of lower nodes
   */
  protected abstract ArrayList<T> lowerLevels(T p);
  
  protected void putElement(T morePublic, T moreSecret) {
    inner.put(morePublic, new ArrayList<>(List.of(moreSecret)));
  }
  
  protected void putElements(T morePublic, ArrayList<T> moreSecret) {
    inner.put(morePublic, moreSecret);
  }
  
  public void addElementToKey(T morePublic, T moreSecret) {
    if (inner.containsKey(morePublic)) {
      inner.get(morePublic).add(moreSecret);
    } else {
      putElement(morePublic, moreSecret);
    }
  }
  
  public Set<T> getAllLevels() {
    return inner.keySet();
  }
  
  public ArrayList<T> getHigherLevels(T level) {
    return inner.get(level);
  }

  public abstract T getBottom();
  
  public T getTop() {
    return top;
  }
  
  public <Sub extends T> T leastUpperBound(Sub level1, Sub level2){
    return leastUpperBound(List.of(level1, level2));
  }
  
  public <Sub extends T> T leastUpperBound(List<Sub> levels){
    levels=L(levels.stream().filter(l->!getBottom().equals(l)));
    if(levels.isEmpty()){return getBottom();}
    return leastUpperBoundAux(levels);
    }
  
  private <Sub extends T> T leastUpperBoundAux(List<Sub> levels){
    if (levels.size() == 1) {
      return levels.get(0);
    }
    if (levels.size() < 1) {throw General.bug();}
      
    T compareLevel = levels.get(0);
    for (int i = 1; i < levels.size(); i++) {
      T secondLevel = levels.get(i);
      if (compareLevel.equals(secondLevel)) {
        continue;
      }
      Map<T, Integer> upper1 = getUpper(compareLevel);
      Map<T, Integer> upper2 = getUpper(secondLevel);
      compareLevel = calculateLeast(upper1, upper2);
    }
    return compareLevel;
  }

  protected Map<T, Integer> getUpper(T level) {
    Map<T, Integer> uppers = new HashMap<T, Integer>();
    uppers.put(level, 0);
    for (T upperLevel : inner.get(level)) {
      uppers.put(upperLevel, 1);
      getUpper(uppers, upperLevel, 1);
    }
    return uppers;
  }
  
  protected void getUpper(Map<T, Integer> uppers, T level, int i) {
    for (T upperLevel : inner.get(level)) {
      uppers.put(upperLevel, i + 1);
      getUpper(uppers, upperLevel, i + 1);
    }
  }

  public boolean secondHigherThanFirst(T level1, T level2) {
    if(level1.equals(getBottom())){return true;}
    Map<T, Integer> uppers = getUpper(level1);
    return uppers.keySet().contains(level2);
  }
  
  public <Sub extends T> List<T> levelsBetween(Sub lowerLevel, Sub higherLevel) {
    Map<T,Integer> upperFromLowMap = getUpper(lowerLevel);
    Set<T> upperFromHigh = getUpper(higherLevel).keySet();
    upperFromHigh.remove(higherLevel);
    for (T t : upperFromHigh){upperFromLowMap.remove(t);}
    upperFromLowMap.keySet().removeIf(e-> !getUpper(e).keySet().contains(higherLevel));   
    List<T> returnList = new ArrayList<>();
    Integer steps=upperFromLowMap.get(higherLevel);
    if(steps==null){return L();}
    for(int i = 0; i <= steps; i++){
      for(T t : upperFromLowMap.keySet()){
        if(upperFromLowMap.get(t) == i){returnList.add(t);}
        }
      }    
    return returnList;
    }

  protected T calculateLeast(Map<T, Integer> upper1, Map<T, Integer> upper2) {
    T least = null;
    int distance = Integer.MAX_VALUE;
    for (T level1 : upper1.keySet()) {
      if (upper2.containsKey(level1)) {
        int newDistance = upper1.get(level1) + upper2.get(level1);
        if (newDistance < distance) {
          least = level1;
          distance = newDistance;
        } else if (newDistance == distance) {
          throw new Error("ERROR SAME DISTANCE -> NO LATTICE: " + "current level: " + level1 + " has same distance as: " + least);
        }
      }
    }
    return least;
  }
  
  @Override
  public String toString() {
    StringBuilder mapAsString = new StringBuilder("{");
    for (T key : inner.keySet()) {
        mapAsString.append(key + "->" + inner.get(key) + ", ");
    }
    mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");
    return mapAsString.toString();
  }
}
