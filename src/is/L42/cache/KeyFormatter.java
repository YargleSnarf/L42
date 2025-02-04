package is.L42.cache;

import static is.L42.tools.General.L;
import static is.L42.tools.General.range;
import static is.L42.tools.General.unreachable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import is.L42.cache.nativecache.L42ValueCache;
import is.L42.common.Program;
import is.L42.flyweight.C;
import is.L42.flyweight.CoreL;
import is.L42.flyweight.P;
import is.L42.flyweight.X;
import is.L42.maps.L42£AbsMap.MapCache;
import is.L42.maps.L42£Set.SetCache;
import is.L42.nativeCode.TrustedKind;
import is.L42.platformSpecific.javaTranslation.Resources;
import is.L42.tools.General;

interface FormatKind{
  P path();
  String format(boolean isInterface,int size);
  String specialS();
  }
class Format42 implements FormatKind{
  L42Cache<?> cache;
  final P.NCs path;
  int lineN;
  Object o;
  KeyFormatter f;
  boolean isInterface(int i){
    assert i>0;
    i=i-1;//0 in input is for the cache object
    if(cache instanceof L42StandardCache<?> c){
      return c.rawFieldCache(null, i)==null;
      }
    if(cache instanceof L42ValueCache<?>){return false;}
    if(cache instanceof L42SingletonCache<?>){return false;}
    var gen1=cache instanceof AbstractListCache || cache instanceof SetCache<?>;
    if(gen1){return isInterfaceNativePar(0,path);}
    assert cache instanceof MapCache<?,?,?>:cache;
    return isInterfaceNativePar(i%2,path);
    }
  static boolean isInterfaceNativePar(int i,P.NCs path){
    assert i>=0;
    var p=Resources.currentP.navigate(path);
    var pars=p.topCore().info().nativePar();
    if(pars.size()<=i){return false;}
    var pi=p.from(pars.get(i),P.pThis0);
    return p._ofCore(pi).isInterface();
    }
  String miniPath(){
    if(path.cs().isEmpty()){return path.toString();}
    C first=path.cs().get(0);
    Program pp=f.p;
    for(@SuppressWarnings("unused") int i:range(path.n())){
      if(pp.topCore().inDom(first)){return path.toString();}
      pp=pp.pop();
      }
    return path.cs().stream().map(c->c.toString()).collect(Collectors.joining("."));
    }
  public String format(boolean isInterface,int size){
    assert f.k.lines().length>lineN;
    String name=isInterface?miniPath():"";
    var l=f.p._ofCore(path);
    boolean isOpt=l.info().nativeKind().equals("Opt");
    boolean nativePrint=!l.info().nativeKind().isEmpty();
    if(isOpt){
      var elem=f.k.lines()[lineN][1];
      if(elem==null){
        return name+"<>"; }
      var res=f.formatDispatch(null,path,null,null,
          elem,false,size+name.length());
      var roundy = res.length()>2 && res.startsWith("(") && res.endsWith(")");
      if(roundy){ res=res.substring(1,res.length()-1); }
      return name+"<"+res+">";
      }
    if(nativePrint){
      assert isInterface || isOpt;
      String res=f.formatDispatch(path,path,
        null,null,f.k.lines()[lineN][1],false,size+name.length());
      if(res.startsWith("\"")||res.startsWith("[")){return name+res;}
      if(res.startsWith("-")){return name+"\""+res+"\"";}
      return res+name; 
      }
    assert l!=null:path;
    List<X> xs=l.mwts().stream()
      .filter(m->m._e()==null && m.mh().mdf().isClass())
      .map(m->m.key().xs())
      .findFirst().get();//can be more then one in case factories
      //.distinct()//has parameters in different order
      //.reduce(toOneOrBug()).get();
    Object[] fields=f.k.lines()[lineN];
    assert fields!=null;
    //assert Stream.of(fields).noneMatch(e->e==null):Stream.of(fields).toList();//can be null for ops
    int estimateSize=xs.size()*7+name.length();    
    StringBuilder res=new StringBuilder(name+"(");
    if(!xs.isEmpty() && !xs.get(0).equals(X.thatX)){res.append(xs.get(0)+"=");}
    for(int i:range(1,fields.length)){
      res.append(f.formatDispatch(null,path,l,xs.get(i-1),fields[i],isInterface(i),estimateSize));
      if(i+1!=fields.length){res.append(", "+xs.get(i)+"=");}    
      }
    res.append(")");
    return res.toString();
    }
  private C fromS(String s){
    int colon=s.indexOf(":");
    if(colon==-1){return C.of(s,-1);}
    String a=s.substring(0,colon);
    String b=s.substring(colon+2);
    return C.of(a,Integer.parseInt(b));
    }
  public Format42(P.NCs hint,KeyFormatter f, int lineN,Object o) {
    this.cache=(L42Cache<?>)f.k.lines()[lineN][0];
    this.lineN=lineN;
    this.f=f;
    this.o=o;
    if(hint!=null){
      this.path=hint;
      assert null!=f.p._ofCore(this.path);
      return;
      }
    var oNames=cache.typename();
    if(oNames instanceof String[] names) {
      List<C> cs=L(range(names.length),(c,i)->c.add(fromS(names[i])));
      this.path=f.p.minimize(P.of(f.p.dept(), cs));
      assert null!=f.p._ofCore(this.path):cs;
      return;
      }
    throw General.unreachable(/*oNames+" "+oNames.getClass()*/);
    }
  @Override public String specialS(){return null;}
  @Override public P path(){return path;}
  }
class FormatTrusted implements FormatKind{
  P hint;KeyFormatter f; int lineN;Object o;
  public FormatTrusted(P hint,KeyFormatter f, int lineN,Object o) {
    this.hint=hint;this.f=f;this.lineN=lineN;this.o=o;
    }
  @Override public P path(){return hint;}
  @Override public String format(boolean isInterface, int size){return specialS();}
  @Override public String specialS(){
    String res= f.k.lines()[lineN][1].toString();
    if(!res.isEmpty() && res.matches("[0-9]+")){return res;}
    res=res.replace("\"", "\\dq").replace("\n","\\n");
    return "\""+res+"\"";
    }
  }
class FormatList extends Format42{
  public FormatList(P.NCs hint,KeyFormatter f, int lineN,Object o){
    super(hint,f,lineN,o);
    }
  @Override public String format(boolean isInterface, int size){
    assert f.k.lines().length>lineN;
    String name=isInterface?miniPath():"";
    Object[] fields=f.k.lines()[lineN];
    int estimateSize=fields.length*3+name.length();    
    StringBuilder res=new StringBuilder(name+"[");
    for(int i=1;i<fields.length;i+=2){
      res.append(f.formatDispatch(null,path,null,null,fields[i],isInterface(i),estimateSize));
      if(i+2!=fields.length){res.append("; ");}    
      }
    res.append("]");
    return res.toString();
    }
  }
class FormatSet extends Format42{
  public FormatSet(P.NCs hint,KeyFormatter f, int lineN,Object o){
    super(hint,f,lineN,o);
    }
  @Override public String format(boolean isInterface, int size){
    assert f.k.lines().length>lineN;
    String name=isInterface?miniPath():"";
    Object[] fields=f.k.lines()[lineN];
    int estimateSize=fields.length*3+name.length();    
    StringBuilder res=new StringBuilder(name+"{");
    for(int i=1;i<fields.length;i+=1){
      res.append(f.formatDispatch(null,path,null,null,fields[i],isInterface(i),estimateSize));
      if(i+1!=fields.length){res.append("; ");}    
      }
    res.append("}");
    return res.toString();
    }
  }
class FormatMap extends Format42{
  public FormatMap(P.NCs hint,KeyFormatter f, int lineN,Object o){
    super(hint,f,lineN,o);
    }
  @Override public String format(boolean isInterface, int size){
    assert f.k.lines().length>lineN;
    String name=isInterface?miniPath():"";
    Object[] fields=f.k.lines()[lineN];
    int estimateSize=fields.length*3+name.length();    
    StringBuilder res=new StringBuilder(name+"{");
    assert fields.length%2==1:fields.length;
    for(int i=1;i<fields.length;i+=2){
      res.append(f.formatDispatch(null,path,null,null,fields[i],isInterface(i),estimateSize));
      res.append("->");
      res.append(f.formatDispatch(null,path,null,null,fields[i+1],isInterface(i),estimateSize));
      if(i+2!=fields.length){ res.append("; "); }
      }
    res.append("}");
    return res.toString();
    }
  }
class KeyFormatter{
  KeyNorm2D k;
  Program p=Resources.currentP;
  record NameKSize(String name,FormatKind k,int size){
    String get(){ return name+" = "+k.format(true,size); }
    }
  HashMap<Integer,NameKSize> expanded=new HashMap<>();
  HashSet<Integer> visited= new HashSet<>();
  public String varName(P path){
    String name=path.toString();
    int i=name.lastIndexOf(".");
    if(i!=-1){name=name.substring(i+1);}
    String first=name.substring(0,1).toLowerCase();
    String last=name.substring(name.length()-1);
    String optUnderscore="";
    if("0123456789".contains(last)){optUnderscore="_";}
    return first+name.substring(1)+optUnderscore;
    }
  public FormatKind newFormatKind(P.NCs hint,int lineN,Object o){
    var cache=(L42Cache<?>)k.lines()[lineN][0];
    if(cache.typename() == TrustedKind.List){return new FormatList(hint,this,lineN,o);}
    if(cache.typename() == TrustedKind.SelfList){return new FormatList(hint,this,lineN,o);}
    if(cache.typename() == TrustedKind.HSet){return new FormatSet(hint,this,lineN,o);}
    if(cache.typename() == TrustedKind.HIMap){return new FormatMap(hint,this,lineN,o);}
    if(cache.typename() == TrustedKind.HMMap){return new FormatMap(hint,this,lineN,o);}
    if(cache.typename() instanceof TrustedKind){return new FormatTrusted(hint,this,lineN,o);}
    if(cache.typename() instanceof String[]){return new Format42(hint,this,lineN,o);}
    assert false:
      cache.typename();
    throw unreachable();
    }
  private String formatDispatchId(int id,P.NCs hint,Object o, boolean isInterface, int size) {
    FormatKind k=newFormatKind(hint,id,o);
    String special=k.specialS();
    if(special!=null){return special;}
    String name=varName(k.path())+id;
    if(visited.contains(id)){
      expanded.put(id,new NameKSize(name,k,size));
      return name;
      }
    visited.add(id);
    if(size<50){return k.format(isInterface,size);}
    expanded.put(id,new NameKSize(name,k,size));
    return name;    
    }
  public String formatDispatch(P.NCs hint,P.NCs source,CoreL l,X x, Object o, boolean isInterface, int size) {
    if(o==null) {
      return "<>";}
    int id=((KeyVarID)o).value();
    var cache=(L42Cache<?>)k.lines()[id][0];
    boolean isNative=cache.typename() instanceof TrustedKind;
    if(hint==null && isNative){hint=_computeHint(source, l, x);}
    return formatDispatchId(id,hint,o,isInterface,size);
    }
  private final static List<String>hintable=List.of("List","HSet","HIMap","HMMap","Opt");
  private P.NCs _computeHint(P.NCs source, CoreL l, X x){
    if(l==null){
      l=p._ofCore(source);
      if(!hintable.contains(l.info().nativeKind())){ return null; }
      if(l.info().nativePar().isEmpty()){ return null; }
      P res= p.from(l.info().nativePar().get(0),source);
      if(res.isNCs()){return res.toNCs();}
      return null;
      }
    List<P> types=L(l.mwts(),(c,m)->{
      if(m._e()!=null || !m.mh().mdf().isClass()){return;}
      int index=m.key().xs().indexOf(x);
      assert index!=-1;
      P pi=p.from(m.mh().pars().get(index).p(),source);
      if(p._ofCore(pi).isInterface()){return;}
      c.add(pi);
      });
    assert types.stream().distinct().count()==1;
    P p=types.get(0);
    if(p.isNCs()){return p.toNCs();}
    return null;
    }
  public static String start(KeyNorm2D k,Object o){
    KeyFormatter f=new KeyFormatter();
    f.k=k;
    FormatKind first=f.newFormatKind(null,0,o);
    String res=first.format(true,0);
    if(f.expanded.isEmpty()){return res;}
    res=first.format(true,0);
    f.fixPoint(o,Set.of());
    res+="\n  where:\n"+f.expanded.entrySet().stream()
      .filter(e->e.getKey()!=0)
      .sorted((e1,e2)->e1.getKey()-e2.getKey())
      .map(e->"    "+e.getValue().get())
      .collect(Collectors.joining("\n"));
    if(f.expanded.keySet().contains(0)){
      res=f.varName(first.path())+"0 = "+res;
      }
    return res;
    }
  void fixPoint(Object o,Set<Integer>before){
    var after=this.expanded.entrySet().stream().collect(Collectors.toSet());
    var afterK=this.expanded.keySet().stream().collect(Collectors.toSet());
    boolean more=false;
    for(var a:after) {
      if(before.contains(a.getKey())) {continue;}
      more=true;
      P p=a.getValue().k.path();
      FormatKind novel=this.newFormatKind(p.toNCs(),a.getKey(),o);
      @SuppressWarnings("unused")
      var unused = novel.format(true,0);
      }
    if(!more){return;}
    fixPoint(o,afterK);
    }
  }