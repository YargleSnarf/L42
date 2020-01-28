package is.L42.cache.exampleobjs;

import is.L42.cache.Cache;
import is.L42.cache.ForeignObject;
import is.L42.cache.ForeignObjectCache;
import is.L42.cache.RootCache;

public class A implements ForeignObject<A> {
  
  public static final Class<A> _class = A.class;
  private static final ForeignObjectCache<A> myCache;
  
  static
  {
    RootCache.addCacheableType(A.class, (Cache<A>) (myCache = new ForeignObjectCache<A>("A")));
    myCache.lateInitialize(int.class, int.class);
  }
  
  int i1, i2;

  public A(int i1, int i2) {
    super();
    this.i1 = i1;
    this.i2 = i2;
  }

  public int getI1() {
    return i1;
  }

  public int getI2() {
    return i2;
  }

  @Override
  public Object[] allFields() 
  {
    return new Object[] { i1, i2 };
  }

  @Override
  public void setField(int i, Object o) {
    switch(i) {
      case 0:
        i1 = ((Integer) o);
        break;
      case 1:
        i2 = ((Integer) o);
        break;
      default:
        throw new ArrayIndexOutOfBoundsException();
    }
  }
  
  @Override
  public Object getField(int i) {
    switch(i) {
      case 0:
        return i1;
      case 1:
        return i2;
      default:
        throw new ArrayIndexOutOfBoundsException();
    }
  }

  @Override
  public String toString() {
    return "A [i1=" + i1 + ", i2=" + i2 + "]";
  }

  @Override
  public Cache<A> myCache() 
  {
    return myCache;
  }
  
  @Override
  public String typename()
  {
    return "A";
  }
  
  
  private boolean norm = false;

  @Override
  public boolean norm(boolean set) {
    return norm |= set;
  }

}
