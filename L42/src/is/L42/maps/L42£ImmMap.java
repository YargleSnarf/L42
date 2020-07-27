package is.L42.maps;
import java.util.function.Supplier;
import is.L42.cache.L42Cache;
public class L42£ImmMap<K,T,Extra> extends L42£AbsMap<K,T,L42£ImmMap<K,T,Extra>>{
  public <KK,TT>L42£ImmMap(Supplier<L42Cache<KK>> kCache,Supplier<L42Cache<TT>> vCache){super(kCache,vCache);}
  public T processVal(T val){return vCache.get().normalize(val);}
  @SuppressWarnings("unchecked")
  public static final Class<L42£ImmMap<?,?,?>> _class=(Class<L42£ImmMap<?,?,?>>)(Object)L42£ImmMap.class;
  @Override public L42£ImmMap<K,T,Extra> myCache(){return this;}
  @Override protected L42£ImmMap<K,T,Extra> newInstance(L42£ImmMap<K,T,Extra> t){return new L42£ImmMap<K,T,Extra>(kCache,vCache);}
  }