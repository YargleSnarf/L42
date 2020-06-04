package is.L42.top;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import is.L42.common.CTz;
import is.L42.common.EndError;
import is.L42.common.Err;
import is.L42.common.G;
import is.L42.common.GX;
import is.L42.common.Program;
import is.L42.constraints.FreshNames;
import is.L42.constraints.InferToCore;
import is.L42.constraints.ToHalf;
import is.L42.generated.C;
import is.L42.generated.Core;
import is.L42.generated.Core.L.MWT;
import is.L42.generated.Core.Block;
import is.L42.generated.Core.Doc;
import is.L42.generated.Core.L;
import is.L42.generated.Core.L.Info;
import is.L42.generated.Core.MH;
import is.L42.generated.Core.T;
import is.L42.generated.Full;
import is.L42.generated.Full.L.NC;
import is.L42.generated.Half;
import is.L42.generated.I;
import is.L42.generated.LL;
import is.L42.generated.Mdf;
import is.L42.generated.P;
import is.L42.generated.P.NCs;
import is.L42.generated.Pos;
import is.L42.generated.S;
import is.L42.generated.ST;
import is.L42.generated.X;
import is.L42.generated.Y;
import is.L42.platformSpecific.inMemoryCompiler.InMemoryJavaCompiler.ClassFile;
import is.L42.platformSpecific.inMemoryCompiler.InMemoryJavaCompiler.CompilationError;
import is.L42.platformSpecific.inMemoryCompiler.InMemoryJavaCompiler.MapClassLoader.SClassFile;
import is.L42.platformSpecific.javaTranslation.L42£Library;
import is.L42.platformSpecific.javaTranslation.Resources;
import is.L42.translationToJava.J;
import is.L42.translationToJava.Loader;
import is.L42.typeSystem.Coherence;
import is.L42.typeSystem.FlagTyped;
import is.L42.typeSystem.MdfTypeSystem;
import is.L42.typeSystem.PathTypeSystem;
import is.L42.typeSystem.TypeManipulation;
import is.L42.visitors.Accumulate;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.PropagatorCollectorVisitor;
import is.L42.visitors.WellFormedness;

import static is.L42.generated.LDom._elem;
import static is.L42.tools.General.*;

//TODO: also remove Cache from srcLombock
public class Top {
  public static Core.L topCache(CachedTop c,String code){
    return topCache(c,new Init(code));
    }    
  public static Core.L topCache(CachedTop c,Full.L code){
    return topCache(c,new Init(code));
    }
    public static Core.L topCache(CachedTop c,Init init){
    State.loader=new Loader();
    State s=new State(new FreshNames(),new ArrayList<>(),0,new ArrayList<>(),new ArrayList<>(),null);
    LayerE l=LayerL.empty.push(init.p.top,new CTz().releaseMap());
    R res=c.openClose(new GLOpen(l,s));
    if(res.isErr()){throw res._err;}
    return (Core.L)res._obj;    
    }
  /*public Program topNoCache(CTz ctz,Program p){
    State.loader=new Loader();
    State s=new State(new FreshNames(),new ArrayList<>(),0,new ArrayList<>(),new ArrayList<>(),null);
    if(p.dept()>0){//only for tests
      var l=LayerL.empty.push(Program.emptyL,new CTz().releaseMap()).push(p.pop(),0,L(),L(),L()).push(p.top,ctz.releaseMap());
      return new DirectTop().top(new GLOpen(l,s));
      return new CachedTop().top(Cache.of(), new GLOpen(l,s));
      }
    return new CachedTop().top(Cache.of(),new GLOpen(LayerL.empty.push(p.top,ctz.releaseMap()),s));
    }*/
  public Program __topNoCache(CTz ctz,Program p){
    alreadyCoherent.add(new HashSet<>());
    assert p.dept()+1>=alreadyCoherent.size(): p.dept()+"!="+alreadyCoherent.size();
    Core.L coreL=SortHeader.coreTop(p,uniqueId++);//propagates the right header errors
    Full.L topL=(Full.L)p.top;
    List<Full.L.M> ms=topL.ms();
    List<Full.L.NC> ncs=typeFilter(ms.stream(),Full.L.NC.class);
    //var info=new HashDollarInfo(topL);//TODO: what to do with this?
    Program p0=p.update(coreL,false);
    //next line, is mhs to be closer to the formalism
    List<MWT> mhs=L(coreL.mwts().stream().filter(mi->_elem(ms, mi.key())!=null));
    List<Half.E> e1n=L(mhs,(c,mhi)->{
      var memi=_elem(ms,mhi.key());
      Full.E _ei=null;
      if(memi!=null){_ei=memi._e();}
      ctzAdd(ctz,p0,mhi.mh(),_ei,c);
      });
    assert p.top.isFullL();
    ArrayList<SClassFile> allByteCode=new ArrayList<>();//TODO: those two will need to be saved for caching
    ArrayList<L42£Library> allLibs=new ArrayList<>();
    try{this.loader.loadNow(p0,allByteCode,allLibs);}
    catch(CompilationError e){throw new Error(e);}
    //assert loader.bytecodeSize()==allByteCode.size();//TODO: or not since they are in loader?
    //assert loader.libsCachedSize()==allLibs.size();
    Program p1=topNC(ctz,p0,ncs);//propagate exceptions
    assert p1.top instanceof Core.L;
    assert p1.topCore().wf();//Check the new core top is well formed
    List<Core.E> coreE1n=L(mhs,e1n,(c,mhi,_ei)->{
      if(_ei==null){c.add(null);return;}
      Core.E eri=infer(new I(null,p1,G.of(mhi.mh())),ctz,null,_ei);
      c.add(eri);
      });//and propagate errors out
    List<MWT> coreMWTs=L(mhs,coreE1n,(c,mhi,_ei)->{//mwt'1..mwt'n
      var memi=_elem(ms,mhi.key());
      String nat="";
      if(memi instanceof Full.L.MWT){nat=((Full.L.MWT)memi).nativeUrl();}
      c.add(mhi.withNativeUrl(nat).with_e(_ei));
      });
    Core.L l=updateInfo(p1,coreMWTs);//mwt'1..mwt'n
    assert l.info()._uniqueId()!=-1;
    Program p2=p1.update(l,false);//propagate illTyped
    l=p2.topCore();
    l=l.withInfo(l.info().with_uniqueId(-1));
    alreadyCoherent.remove(alreadyCoherent.size()-1);
    Program p3=p2.update(l,false);
    return p3;
    }
//--------------
  public Program topNC(CTz ctz, Program p, List<Full.L.NC> allNCs){
    if(allNCs.isEmpty()){return p;}
    NC current=allNCs.get(0);
    CTz frommedCTz=null;//TODO:p.push(current.key(),Program.emptyL).from(ctz,P.pThis1);
    Full.E fe=current.e();
    C c0=current.key();
    System.out.println("Now considering main "+c0+" "+p.topCore().info()._uniqueId());
    List<Full.Doc> docs=current.docs();
    List<Pos> poss=current.poss();
    //var info=new HashDollarInfo(current);//TODO: more stuff to remove/handle?
    Core.E e=toCoreTypeCoherent(ctz,frommedCTz,poss,p,c0,fe,allNCs);
    Core.L l=null;
    ArrayList<SClassFile> allByteCode=new ArrayList<>();//TODO: those two will need to be saved for caching
    ArrayList<L42£Library> allLibs=new ArrayList<>();
    if(e instanceof Core.L){l=(Core.L)e;}
    else{l=reduce(p,c0,e,allByteCode,allLibs);}//propagate errors
    //assert loader.bytecodeSize()==allByteCode.size():loader.bytecodeSize()+" "+allByteCode.size();
    System.out.println(c0+ " reduced");
    assert l!=null:c0+" "+e;
    //now, generating the new nc and adding it to the top of the program
    Core.L.NC nc=new Core.L.NC(poss, TypeManipulation.toCoreDocs(docs), c0, l);
    Program p1=p.update(updateInfo(p,e,nc),false);
    //note: we generate also the last round of bytecode to be cache friendly (if a new nested is added afterwards)
    //assert loader.bytecodeSize()==allByteCode.size():loader.bytecodeSize()+" "+allByteCode.size();
    p1=flagTyped(p1,allByteCode,allLibs);//propagate errors
    CTz newCTz=null;//TODO:p1.from(frommedCTz,P.of(0,L(c0)));
    //assert loader.bytecodeSize()==allByteCode.size():loader.bytecodeSize()+" "+allByteCode.size();
    return topNC(newCTz,p1,popL(allNCs)); 
    }
//-----------------
//  public Program top(Program p)throws EndError {return topNoCache(new CTz(),p);}
  private Core.L updateInfo(Program p, List<Core.L.MWT>mwts) {
    Core.L l=(Core.L)p.top;
    List<Core.L.MWT> mwts0=L(l.mwts(),(c,m)->{
      var newM=_elem(mwts,m.key());
      if(newM==null){c.add(m);return;}
      });
    assert mwts0.size()+mwts.size()==l.mwts().size();
    Deps collected=new Deps().collectDeps(p,mwts);
    Info info=collected.toInfo(true);
    var allMwts=merge(mwts,mwts0);
    var bridges=WellFormedness.bridge(allMwts);
    var closeState=!WellFormedness.hasOpenState(l.isInterface(),allMwts,bridges);
    Info info1=sumInfo(l.info(),info).withClose(closeState);
    return l.withMwts(allMwts).withInfo(info1);
    }
  public static Info sumInfo(Info info1, Info info2) {
    assert info1._uniqueId()==-1 || info2._uniqueId()==-1;
    assert info1.nativeKind().equals("") || info2.nativeKind().equals("");
    assert info1.nativePar().isEmpty() || info2.nativePar().isEmpty();
    var allW=mergeU(info1.watched(),info2.watched());
    List<Core.PathSel> allU=L(c->{//avoid the ps in watched
      for(var ps:info1.usedMethods()){if(!allW.contains(ps.p()) && !c.contains(ps)){c.add(ps);}}
      for(var ps:info2.usedMethods()){if(!allW.contains(ps.p()) && !c.contains(ps)){c.add(ps);}}
      });
    Info res=new Info(info1.isTyped() && info2.isTyped(),
      mergeU(info1.typeDep(),info2.typeDep()),
      mergeU(info1.coherentDep(),info2.coherentDep()),
      mergeU(info1.metaCoherentDep(),info2.metaCoherentDep()),
      allW,allU,
      mergeU(info1.hiddenSupertypes(),info2.hiddenSupertypes()),
      mergeU(info1.refined(),info2.refined()),
      info1.close() || info2.close(),
      info1.nativeKind()+info2.nativeKind(),
      info1.nativePar().isEmpty()?info2.nativePar():info1.nativePar(),
      Math.max(info1._uniqueId(),info2._uniqueId())
      );
    if(res.equals(info1)){return info1;}
    if(res.equals(info2)){return info2;}
    return res;
    }
  private Core.L updateInfo(Program p1,Core.E source, Core.L.NC nc) {
    //if nc.key().hasUniqueNum() this can cause a type error in the outer (is ok)
    Core.L l=(Core.L)p1.top;
    if(!(source instanceof LL)){
      nc=nc.withL(new UniqueNsRefresher(true).refreshUniqueNs(nc.l()));
      }
    var info=l.info();
    Deps deps=new Deps().collectDocs(p1,nc.docs());
    if(nc.key().hasUniqueNum()){deps.collectDepsE(p1, nc.l());}
    info=Top.sumInfo(info,deps.toInfo(true));
    l=l.withNcs(pushL(l.ncs(),nc)).withInfo(info);
    return l;
    }
  private Core.E infer(I i,CTz ctz,CTz frommed,Half.E e) throws EndError{
    return new InferToCore(i,ctz,this).compute(e);
    }
  private final FreshNames freshNames;
  private final ArrayList<HashSet<List<C>>> alreadyCoherent=new ArrayList<>();
  private int uniqueId=0;
  public final Loader loader;
  private final Path initialPath;//TODO:
  public Top(FreshNames freshNames, int uniqueId, Loader loader,Path initialPath) {
    this.freshNames=freshNames;
    this.uniqueId=uniqueId;
    this.loader=loader;
    this.initialPath=initialPath;
    }  
  private Core.E toCoreTypeCoherent(CTz ctz,CTz frommedCTz,List<Pos>poss,Program p,C c0,Full.E fe,List<NC> allNCs){
      Y y=new Y(p,GX.empty(),L(),null,L());
      var  hq=toHalf(ctz,y,freshNames,fe);
      Half.E he=hq.e;
      I i=new I(c0,p,G.empty());
      Core.E ce=infer(i,ctz,frommedCTz,he); //propagates errors
      assert ce!=null;
      WellFormedness.of(ce.visitable());
      Deps deps=new Deps();
      P pRes=wellTyped(p,ce,deps,allNCs);//propagate errors //ncs is passed just to provide better errors
      Core.E ce0=adapt(ce,pRes);
      coherentAllPs(p,deps.cohePs); //propagate errors
      return ce0;    
    }
  protected Program flagTyped(Program p1,ArrayList<SClassFile> cBytecode,ArrayList<L42£Library> newLibs) throws EndError{
    Program p=FlagTyped.flagTyped(this.loader,p1);//but can be overridden as a testing handler
    try {this.loader.loadNow(p,cBytecode,newLibs);}
    catch (CompilationError e) {throw new Error(e);}
    return p;
    }
  protected Core.L reduce(Program p,C c,Core.E e,ArrayList<? super SClassFile> outNewBytecode,ArrayList<? super L42£Library> newLibs)throws EndError {
    try{return loader.runNow(p, c, e,outNewBytecode,newLibs);}
    catch(InvocationTargetException e1){
      if(e1.getCause()instanceof RuntimeException){throw (RuntimeException) e1.getCause();}
            if(e1.getCause()instanceof Error){throw (Error) e1.getCause();} 
      throw new Error(e1.getCause());
      }
    catch(CompilationError e1){throw new Error(e1);}
    }
  protected Core.L reduceByName(Program p, C c)throws EndError {
    String name="£c"+c;
    if(!p.pTails.isEmpty()){name=J.classNameStr(p)+name;}
    try{return loader.runMainName(p,c,name);}
    catch(InvocationTargetException e1){
      if(e1.getCause()instanceof RuntimeException){throw (RuntimeException) e1.getCause();}
            if(e1.getCause()instanceof Error){throw (Error) e1.getCause();} 
      throw new Error(e1.getCause());
      }
    }

  public void coherentAllPs(Program p, List<P.NCs> cohePs)throws EndError{
    Coherence.coherentAllPs(p,cohePs,alreadyCoherent);
    }
  private Core.E adapt(Core.E ce, P path) {
    if(path==P.pLibrary){return ce;}
    X x=new X(freshNames.fresh("main"));
    if(path==P.pVoid){
      Core.D d=new Core.D(false,P.coreVoid,x,ce);
      return new Core.Block(ce.pos(),L(d),L(),Program.emptyL);
      }
    var mCall=new Core.MCall(ce.pos(), new Core.EX(ce.pos(),x),toLibraryS,L());
    Core.D d=new Core.D(false,P.coreAny.withP(path),x,ce);
    return new Core.Block(ce.pos(),L(d),L(),mCall);
    }
  private P wellTyped(Program p, Core.E ce,Deps deps,List<Full.L.NC>moreNCs)  throws EndError{
    var depsV=deps.new DepsV(p){@Override public void visitL(Core.L l){return;}};
    depsV.of(ce.visitable());
    for(var pi:deps.typePs){
      LL ll=p.of(pi,ce.poss());//propagate errors for path not existent
      Core.L l=(Core.L)ll;
      if(!l.info().isTyped()){
        new CircularityIssue(pi,l,p,ce,moreNCs).reportError();
        }
      }
    List<P> ps=L(P.pAny);
    var g=G.empty();
    var pts=new PathTypeSystem(false,p,g,L(),ps,P.pAny);
    ce.visitable().accept(pts);
    var cmp=pts._computed();
    if(cmp==null){cmp=P.pVoid;}
    ce.visitable().accept(new MdfTypeSystem(p,g,Collections.emptySet(),Mdf.Immutable));
    if(cmp==P.pLibrary || cmp==P.pVoid){return cmp;}
    var l=p._ofCore(cmp);
    var mwt=_elem(l.mwts(),toLibraryS);
    if(mwt==null){
      throw new EndError.TypeError(ce.poss(),
        Err.methodDoesNotExists(toLibraryS,L(l.mwts().stream().map(m->m.key()))));
      }
    if(!mwt.mh().mdf().isIn(Mdf.Immutable,Mdf.Readable)){
      throw new EndError.TypeError(ce.poss(),
        Err.methCallNoCompatibleMdfParametersSignature(toLibraryS,"reciever not in imm,read"));
      }
    return cmp;
    }
  private static final S toLibraryS=S.parse("#toLibrary()");
  private static ToHalf.Res<Half.E> toHalf(CTz ctz,Y y, FreshNames fresh,Full.E fe){
    return new ToHalf(y, ctz, fresh).compute(fe);
    }
  private void ctzAdd(CTz ctz0, Program p, MH mh, Full.E _e, ArrayList<Half.E> es) {
    if(_e==null){es.add(null);return;}
    Y y=new Y(p,GX.of(mh),L(mh.t()),null,L(mh.t()));
    var hq=toHalf(ctz0,y,freshNames,_e);
    ctz0.plusAcc(p,hq.resSTz, L(mh.t()));
    es.add(hq.e);
    }
  }
  /*private void flushBadCache(){
  if(!validCache){return;}
  assert cache.size()>=cacheIndex: cache.size()+" "+cacheIndex; 
  for(int i=cache.size()-1;i>=cacheIndex;i-=1){cache.remove(i);}
  }
CacheEntry newCache(CacheEntry c){
  System.out.println("New cache for "+c._key);
  flushBadCache();
  validCache=false;
  cache.add(c);
  return c;
  }
CacheEntry oldCache(CacheEntry c,boolean loadLibs){
  System.out.println("Old cache for "+c._key);
  if(loadLibs && c._mByteCode!=null){
      int size0=loader.libsCachedSize();
      assert size0==c.sizeWithMLibs-c._mLibs.size():size0+" "+c.sizeWithMLibs+" "+c._mLibs.size();
      loader.loadByteCodeFromCache(c._mByteCode,c._mLibs);
      int size1=loader.libsCachedSize();
      assert size0+c._mLibs.size()==size1;
    }
  int size0=loader.libsCachedSize();
  assert size0==c.sizeWithCLibs-c.cLibs.size(): size0+" "+c.sizeWithCLibs+" "+c.cLibs.size();
  loader.loadByteCodeFromCache(c.cByteCode,c.cLibs);
  int size1=loader.libsCachedSize();
  assert size0+c.cLibs.size()==size1;
  return c;
  }
private Program topNC(CTz ctz, Program p, List<NC> ncs)throws EndError{
  if(ncs.isEmpty()){return p;}
  NC current=ncs.get(0);
  CTz frommedCTz=p.push(current.key(),Program.emptyL).from(ctz,P.pThis1);
  CacheEntry c=topNC1(ctz,p,frommedCTz,current,ncs);
  ncs=popL(ncs);
  CTz newCTz=c.p.from(c.frommedCTz,P.of(0,L(current.key())));
  Program res=topNC(newCTz,c.p,ncs);
  return res; 
  }*/

    /*private CacheEntry topNC1(CTz ctz, Program p,CTz frommedCTz,NC current,List<NC>allNCs){
    //assert !validCache || ( ctz==null && frommedCTz==null);
    //assert  validCache || ( ctz!=null && frommedCTz!=null); 
    C c0=current.key();
    System.out.println("Now considering main "+c0);
    Full.E fe=current.e();
    List<Full.Doc> docs=current.docs();
    List<Pos> poss=current.poss();
    //caching part
    var info=new HashDollarInfo(current);
    CacheEntry c=null;
    if(validCache && !info.hashDollar && cacheIndex+info.steps<cache.size()){
      c=cache.get(cacheIndex+info.steps);
      assert c._key!=null: "";
      if(c._key.equals(current)){//all good! skip steps
        var cachesToLoad=cache.subList(cacheIndex,cacheIndex+info.steps+1);
        for(var ci:cachesToLoad){oldCache(ci,true);}
        cacheIndex+=info.steps+1;
        return c;//since c contains p,CTz and frommedCTz
        }
      }
    Core.E e=toCoreTypeCohereht(ctz,frommedCTz,poss,p,c0,fe,allNCs);
    if(!validCache || cacheIndex+info.steps>=cache.size()){
      var mByteCode=new HashMap<String,SClassFile>();
      int size0=loader.libsCachedSize();
      var newMLibs=new ArrayList<L42Library>();
      Core.L l=reduce(p,c0,e,mByteCode,newMLibs);//propagate errors
      int size1=loader.libsCachedSize();
      assert size0+newMLibs.size()==size1;
      System.out.println(c0+ " reduced");
      assert l!=null:c0+" "+e;
      Core.L.NC nc=new Core.L.NC(poss, TypeManipulation.toCoreDocs(docs), c0, l);
      Program p1=p.update(updateInfo(p,nc),false);
      //TODO: try to understand if we can avoid any bytecode generation here (is this bytecode ever usable?)
      //that is, the last nested class would not generate usable bytecode
      var cByteCode=new HashMap<String,SClassFile>();
      var newCLibs=new ArrayList<L42Library>();
      int size2=loader.libsCachedSize();
      p1=flagTyped(p1,cByteCode,newCLibs);//propagate errors
      int size3=loader.libsCachedSize();
      assert size2+newCLibs.size()==size3;
      return newCache(new CacheEntry(current,frommedCTz,p1,newMLibs,newCLibs,size1,size3,mByteCode,cByteCode));
      }
    c=cache.get(cacheIndex);//that is, the 'current' cache, not the next one
    assert c._key!=null;
    //cacheIndex+=info.steps+1;
    //assert info.steps==0;//info.steps is irrelevent from now on?
    cacheIndex+=1;
    if(!info.hashDollarTop){return oldCache(c,true);}
    assert c._mByteCode!=null;
    int size0=loader.libsCachedSize();
    assert size0==c.sizeWithMLibs-c._mLibs.size();
    loader.loadByteCodeFromCache(c._mByteCode,c._mLibs);
    int size1=loader.libsCachedSize();
    assert size0+c._mLibs.size()==size1;
    assert size1==c.sizeWithMLibs;
    Core.L l=this.reduceByName(p,c0);
    var cByteCode=new HashMap<String,SClassFile>();
    var newLibs=new ArrayList<L42Library>();    
    Core.L.NC nc=new Core.L.NC(poss, TypeManipulation.toCoreDocs(docs), c0, l);
    Program p1=p.update(updateInfo(p,nc),false);
    int size2=loader.libsCachedSize();
    p1=flagTyped(p1,cByteCode,newLibs);//propagate errors
    int size3=loader.libsCachedSize();
    assert c.sizeWithCLibs==size3:
      c.sizeWithCLibs+" "+size3+" "+size2+" "+newLibs.size();//TODO:remove
    assert size2+newLibs.size()==size3;
    Core.L newL=_elem(p1.topCore().ncs(),c0).l();//This may be typed, while l may not
    Core.L cachedL=_elem(c.p.topCore().ncs(),c0).l();
    if(newL.equals(cachedL)){return oldCache(c,false);}
    return newCache(new CacheEntry(current,frommedCTz,p1,c._mLibs,newLibs,size1,size3,c._mByteCode,cByteCode));    
    }*/

    
    
    /*
top:
  sortHeader
  flagTyped
  topNCs
  inferMeth and Info
topNC1:
  full->half->core
  type
  coherent
  reduce
  flagTyped

-Presence/absence of #$ is cached, thus is about the old version

top:
              allNo#$                  reuseNo#$           bodyNo#$           bothHave#$
allEq         useCache                 reuseHeader         checkHeaderEq      stepByStep
eqReuse+Meth  reuseHeader*             reuseHeader*        stepByStep*        stepByStep*
eqNCs         dropCache                dropCache           dropCache          dropCache 
allDiff       dropCache                dropCache           dropCache          dropCache

* == assert !validCache after topNCs
useCache:
  just return the cached result
reuseHeader: 
  import header from cache, call topNCs, if validCache, just return the cached result
  else recompute inferMeth and Info and return new cache
checkHeaderEq:
  l=sortHeader; if l==cache.sortHeader, just return the cached result
  else cacheIsInvalid, flagTyped;  topNCs;  inferMeth and Info
stepByStep:
  l=sortHeader; if l==cache.sortHeader, reuseHeader
  else cacheIsInvalid, flagTyped;  topNCs;  inferMeth and Info
dropCache:
  cacheIsInvalid, sortHeader; flagTyped;  topNCs;  inferMeth and Info

topNC1:
                  no#$               e#$               Ls#$            both#$
  allEq           useCache           rerunE            rerunI          rerunEI
  ExprEq,LDiff    rerunI*            rerunEI*          rerunI*         rerunEI*
  allDiff         dropCache          dropCache         dropCache       dropCache

useCache:
  just return the cached result
rerunE:
  l=run cache.core_e, if l==cache.l just return the cached result
  else cacheIsInvalid, flagTyped
rerunI:
  core_e=full->half->core, if cache still valid (assert core_e==cache.core_e)  just return the cached result
  else update libs, l=run core_e (old bytecode, new libs), flagTyped  
rerunEI:
  core_e=full->half->core, if cache still valid (assert core_e==cache.core_e) rerunE
  else update libs, l=run core_e (old bytecode, new libs), flagTyped

dropCache:
  cacheIsInvalid, full->half->core;  type;  coherent;  reduce;  flagTyped
*/

    /*
  private CTz setInvalid(Cache.InOut in,CTz ctz){
    if(!validCache){return ctz;}
    validCache=false;
    assert cache.allByteCode().size()>=in.nCByteCode();
    assert cache.allByteCode().size()>=in.nMByteCode();
    assert cache.allLibs().size()>=in.nLibs(); 
    for(int i=cache.allByteCode().size()-1;i>=in.nMByteCode();i-=1){
      cache.allByteCode().remove(i);
      }
    for(int i=cache.allLibs().size()-1;i>=in.nLibs();i-=1){
      cache.allLibs().remove(i);
      }
    assert ctz==null;
    return in.ctz();
    }
  public Cache.CTop top(CTz ctz,Program p, Cache.CTop ctop)throws EndError {
    var currentState=new Cache.InOut(loader.bytecodeSize(),-1,loader.libsCachedSize(),ctz,alreadyCoherent,p);
    var currentEq=ctop.in().equals(currentState);
    if(!ctop.hasHD() && currentEq){return ctop;}
    alreadyCoherent.add(new HashSet<>());
    assert p.dept()+1>=alreadyCoherent.size(): p.dept()+"!="+alreadyCoherent.size();
    if(validCache && ctop!=null){uniqueId=ctop.in().p().topCore().info()._uniqueId();}
    Core.L coreL=SortHeader.coreTop(p, uniqueId++);//propagates the right header errors
    List<Full.L.M> ms=((Full.L)p.top).ms();
    List<Full.L.NC> ncs=typeFilter(ms.stream(),Full.L.NC.class);
    if(validCache && coreL.equals(ctop.sortedHeader())){
      //-load bytecode about the header
      List<Cache.CTopNC1> p1Cache=topNC(ctznull,ctop.ncs(),ncs);//propagate exceptions
      if(validCache && currentEq){return ctop;}//currentEq can be different if meth body is different
      ctz=setInvalid(ctop.in(),ctz);
      //-may load e1n from cache?
      //-make new cache, return new cache
      }
    //different header (either after reuse #$ or just updated reuse/methods)
    ctz=setInvalid(ctop.in(),ctz);
    var newCache=new Cache.CTop(currentState, null, new ArrayList<>(), hasHDfalse, coreL, -1, -1);
    //-compute hasHD;
    Program p0=p.update(coreL,false);
    List<Half.E> e1n=L(coreL.mwts(),(c,mwti)->{
      var memi=_elem(ms,mwti.key());
      Full.E _ei=null;
      if(memi!=null){_ei=memi._e();}
      ctzAdd(ctz,p0,mwti.mh(),_ei,c);
      });
    assert p.top.isFullL();
    //-update newCache by saving the new bytecode/libs
    try{this.loader.loadNow(p0,cache, newCache);}
    catch(CompilationError e){throw new Error(e);}
    int size1=loader.libsCachedSize();
    assert size0+newCLibs.size()==size1;
    return newCache(new CacheEntry(null,null,p,null,newCLibs,size0,size1,null,cByteCode));
    //----
    Program p1=topNC(ctz,cache.p,ncs);//propagate exceptions
    assert p1.top instanceof Core.L;
    WellFormedness.of(p1.topCore());//Check the new core top is well formed
    List<Core.E> coreE1n=L(coreL.mwts(),e1n,(c,mwti,_ei)->{
      if(_ei==null){c.add(null);return;}
      Core.E eri=infer(new I(null,p1,G.of(mwti.mh())),ctz,null,_ei);
      c.add(eri);
      });//and propagate errors out
    List<MWT> coreMWTs=L(coreL.mwts(),coreE1n,(c,mwti,_ei)->{//mwt'1..mwt'n
      var memi=_elem(ms,mwti.key());
      String nat="";
      if(memi instanceof Full.L.MWT){nat=((Full.L.MWT)memi).nativeUrl();}
      c.add(mwti.withNativeUrl(nat).with_e(_ei));
      });
    Core.L l=updateInfo(p1,coreMWTs);//mwt'1..mwt'n
    assert l.info()._uniqueId()!=-1;
    Program p2=p1.update(l,false);//propagate illTyped
    l=p2.topCore();
    l=l.withInfo(l.info().with_uniqueId(-1));
    alreadyCoherent.remove(alreadyCoherent.size()-1);
    return p2.update(l,false);
    }*/
