package is.L42.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.google.common.cache.CacheBuilder;

import is.L42.cache.nativecache.ArrayListCache;
import is.L42.cache.nativecache.BoolCache;
import is.L42.cache.nativecache.ByteCache;
import is.L42.cache.nativecache.CharCache;
import is.L42.cache.nativecache.DoubleCache;
import is.L42.cache.nativecache.FlagsCache;
import is.L42.cache.nativecache.FloatCache;
import is.L42.cache.nativecache.IntCache;
import is.L42.cache.nativecache.LongCache;
import is.L42.cache.nativecache.ShortCache;
import is.L42.cache.nativecache.StringCache;
import is.L42.nativeCode.Flags;

public class L42CacheMap {
  
  //TODO: Change to string to get rid of reflection maybe?
  private static final Map<Class<?>, L42Cache<?>> commander;
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  private static final CacheBuilder<KeyNorm2D,Object> builder = (CacheBuilder<KeyNorm2D,Object>) ((CacheBuilder) CacheBuilder.newBuilder().softValues()); 
  
  static {    
    commander = new HashMap<>();
    commander.put(int.class, new IntCache());
    commander.put(Integer.class, commander.get(int.class));
    commander.put(boolean.class, new BoolCache());
    commander.put(Boolean.class, commander.get(boolean.class));
    commander.put(float.class, new FloatCache());
    commander.put(Float.class, commander.get(float.class));
    commander.put(double.class, new DoubleCache());
    commander.put(Double.class, commander.get(double.class));
    commander.put(long.class, new LongCache());
    commander.put(Long.class, commander.get(long.class));
    commander.put(short.class, new ShortCache());
    commander.put(Short.class, commander.get(short.class));
    commander.put(byte.class, new ByteCache());
    commander.put(Byte.class, commander.get(byte.class));
    commander.put(char.class, new CharCache());
    commander.put(Character.class, commander.get(char.class));
    commander.put(Flags.class, new FlagsCache());
    commander.put(String.class, new StringCache());
    commander.put(ArrayList.class, new ArrayListCache());
    }
  
  /**
   * Adds a cacheable type using the given class and cache object.
   * Once added, the specified cache will be returned by further
   * calls to <code>getCacheObject(...)</code> and indirectly
   * by calls to <code>normalize(...)</code> and
   * <code>isNorm()</code>
   * 
   * @param <T> The type of the cache
   * @param class_ The class object representing the type
   * @param cache The relevant cache object
   */
  public static <T,C extends L42Cache<T>> C addCacheableType(Class<T> class_, C cache) {
    commander.put(class_, cache);
    return cache;
    }
  
  @SuppressWarnings("unchecked")
  /**
   * Given a class object, retrieves the 
   * @param <T>
   * @param class_
   * @return
   */
  public static <T> L42Cache<T> getCacheObject(Class<T> class_) {
    return (L42Cache<T>) commander.get(class_);
    }
  
  @SuppressWarnings({ "rawtypes", "unchecked" }) 
  public static L42Cache[] getCacheArray(Class ... classes) {
    L42Cache[] caches = new L42Cache[classes.length];
    for(int i = 0; i < classes.length; i++) {
      caches[i] = getCacheObject(classes[i]);
      }
    return caches;
    }
  
  @SuppressWarnings("unchecked")
  public static <T> L42Cache<T> getCacheObject(T o) {
    if(o instanceof L42Cachable) { return ((L42Cachable<T>) o).myCache(); }
    return getCacheObject((Class<T>) o.getClass()).refine(o);
    }
  
  public static <T> T normalize(T t) {
    L42Cache<T> cache = getCacheObject(t);
    return cache.normalize(t);
    }
  
  @SuppressWarnings("rawtypes")
  public static <T> boolean isNorm(T t) {
    if(t == null) { return true; }
    if(t instanceof L42Cachable) { return ((L42Cachable) t).isNorm(); }
    L42Cache<T> cache = getCacheObject(t);
    return cache.isNorm(t);
    }
  
  public static <T> boolean identityEquals(T t1, T t2) {
    if(t1 instanceof L42Cachable) { return t1 == t2; }
    L42Cache<T> cache = getCacheObject(t1);
    return cache.identityEquals(t1, t2);
    }
  
  /**
   * Gets the key for an object, optionally normalizing it first
   * 
   * @param <T> type of object
   * @param t The object
   * @param norm Whether to normalize the object before getting
   * the key
   * 
   * @return The object's key
   */
  public static <T> KeyNorm2D getKey(T t, boolean norm) {
    L42Cache<T> cache = getCacheObject(t);
    t = norm ? cache.normalize(t) : t;
    return cache.computeKeyNN(t);
  }
  
  @SuppressWarnings({"rawtypes", "unchecked" })
  public static KeyNorm2D expandedKey(final Object obj, final boolean entireROG, final boolean norm) {
    final Map<Object, Integer> done = new IdentityHashMap<>();
    final ArrayList<Object[]> nkeylist = new ArrayList<>();
    class A { KeyVarID apply(int offset, Object toAdd, int toAddIndex, Object[][] subkey) {
      KeyVarID ourId = new KeyVarID(offset + toAddIndex);
        done.put(toAdd, ourId.value());
        L42Cache cache = getCacheObject(toAdd);
        for(int i = 1; i < subkey[toAddIndex].length; i++) {
          if(subkey[toAddIndex][i] instanceof KeyVarID) {
            KeyVarID oldId = (KeyVarID) subkey[toAddIndex][i];
            Object field = cache.f(toAdd, i - 1);
            if(!done.containsKey(field)) {
              KeyVarID newId = this.apply(offset, field, oldId.value(), subkey);
              subkey[toAddIndex][i] = newId;
              } else {
              KeyVarID newId = new KeyVarID(done.get(field)); 
              subkey[toAddIndex][i] = newId;
              }
            }
          }
        return ourId;
        }}
    A subkeyProcessor = new A();
    Function<Object, Object> addNewObject = (theObj) -> {
      if(isNorm(theObj) && !entireROG) { return theObj; }
      KeyNorm2D subkey = getKey(theObj, norm);
      Object[][] subkeylines = subkey.lines();
      int offset = nkeylist.size();
      KeyVarID nid = subkeyProcessor.apply(offset, theObj, 0, subkeylines);
      for(Object[] o : subkeylines) { nkeylist.add(o); }
      return nid;
      };
    addNewObject.apply(obj);
    for(int i = 0; i < nkeylist.size(); i++) {
      Object[] line = nkeylist.get(i);
      if(((L42Cache<?>) line[0]).isValueType()) { continue; }
      for(int j = 1; j < line.length; j++) {
        if(!(line[j] instanceof KeyVarID)) {
          if(done.containsKey(line[j])) {
            line[j] = new KeyVarID(done.get(line[j]));
            } else if(line[j] != null) {
            line[j] = addNewObject.apply(line[j]);
            }
          }
        }
      }
    
    //toArray doesn't work for whatever reason
    Object[][] narr = new Object[nkeylist.size()][];
    for(int i = 0; i < narr.length; i++) { narr[i] = nkeylist.get(i); }
    return new KeyNorm2D(narr);
  }
  
  public static Set<Object> identityHashSet() {
    return Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>());
    }
  
  /**
   * Produces a map that has the desired behavior of entries
   * being garbage collectable with the relevant object is 
   * no longer in use
   * 
   * @return A map from object to object that can be used
   * as a norm map, such that the references to the keys
   * are strong references and the references to the values
   * are weak references.
   */
  public static Map<KeyNorm2D, Object> newNormMap()
  {
    return builder.build().asMap();
  }

}
