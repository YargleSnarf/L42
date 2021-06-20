package is.L42.cache;

import java.util.ArrayList;

import is.L42.nativeCode.Flags;
import is.L42.nativeCode.TrustedKind;

public class ArrayListCache extends AbstractStructuredCache<ArrayList<?>>{
  @Override public Object f(ArrayList<?> t, int i){return t.get(i+1);}
  @Override public void setF(ArrayList<?> t,int i,Object o){
    if(i+1>=t.size()){t.add(null);t.add(null);}//so that set is "also" an add
    @SuppressWarnings("unchecked")
    var tt=(ArrayList<Object>)t;
    tt.set(i+1, o);    
    }      
  @Override protected ArrayList<?> newInstance(ArrayList<?> t){
    var res=new ArrayList<>(t.size());
    res.add(null);
    return res;
    }
  @Override protected void add(KeyNorm2D key, ArrayList<?> t) {
    super.add(key,t);
    this.setMyNorm(t, t);
    }  
  @Override public boolean isNorm(ArrayList<?> t){return t==null ||t.get(0)!=null;}
  //above, if t==null, this cache is used for an Optional ArrayList, and the empty optional is
  //the normalized verion of itself
  @Override public int fn(ArrayList<?> t){return t.size()-1;}
  @Override public Object typename(){return TrustedKind.List;}
  @Override public L42Cache<?> rawFieldCache(Object o,int i){
      if(i%2!=0){return Flags.cache;}
      if(o!=null) {return L42CacheMap.getCacheObject(o);}
      return this;
      }
  @Override public ArrayList<?> getMyNorm(ArrayList<?> me){
      if(me==null) {return null;}///the normalized version of iself
      return (ArrayList<?>) ((Object[])me.get(0))[0];
      }
  @SuppressWarnings("unchecked") @Override 
  public void setMyNorm(ArrayList<?> me, ArrayList<?> norm){
    ((ArrayList<Object>)me).set(0, new Object[]{norm});
    //Norm wrapped up as array, otherwise when placed as hashmap key it would loop 
    }
  }