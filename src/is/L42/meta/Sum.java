package is.L42.meta;

import static is.L42.generated.LDom._elem;
import static is.L42.tools.General.L;
import static is.L42.tools.General.LL;
import static is.L42.tools.General.checkNoException;
import static is.L42.tools.General.mergeU;
import static is.L42.tools.General.pushL;
import static is.L42.tools.General.range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import is.L42.common.Program;
import is.L42.flyweight.C;
import is.L42.flyweight.CoreL;
import is.L42.flyweight.P;
import is.L42.generated.Core.Doc;
import is.L42.generated.Core.Info;
import is.L42.generated.Core.MH;
import is.L42.generated.Core.MWT;
import is.L42.generated.Core.NC;
import is.L42.generated.Core.T;
import is.L42.generated.Core;
import is.L42.generated.LL;
import is.L42.generated.Pos;
import is.L42.generated.S;
import is.L42.platformSpecific.javaTranslation.L42Any;
import is.L42.platformSpecific.javaTranslation.L42Error;
import is.L42.platformSpecific.javaTranslation.L42£LazyMsg;
import is.L42.tools.General;
import is.L42.visitors.Accumulate;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.WellFormedness;

public class Sum {
  MetaError errC;
  MetaError errM;
  LinkedHashMap<List<C>,LinkedHashSet<List<C>>> innerMap=new LinkedHashMap<>();
  LinkedHashMap<List<C>,LinkedHashSet<P.NCs>> map=new LinkedHashMap<>();
  boolean inRename;
  LinkedHashSet<List<C>> allWatchedRight;
  LinkedHashSet<List<C>> allRequiredCoherentRight;
  LinkedHashSet<List<C>> allHiddenSupertypesRight;
  LinkedHashSet<List<C>> allWatchedLeft;
  LinkedHashSet<List<C>> allRequiredCoherentLeft;
  LinkedHashSet<List<C>> allHiddenSupertypesLeft;
  CoreL topLeft;
  CoreL topRight;
  Program pOut;
  C cOut;
  public CoreL compose(Program pOut,C cOut,CoreL l1, CoreL l2_,Function<L42£LazyMsg,L42Any>wrapC,Function<L42£LazyMsg,L42Any>wrapM){
    var l2=normalizePrivates(l2_,otherNs(l1));
    MetaError errC=new MetaError(wrapC);
    MetaError errM=new MetaError(wrapM);
    
    //Not working in all the cases assert Rename.deepCheckInfo(pOut.push(cOut,l1),l1);
    //Not working in all the cases assert Rename.deepCheckInfo(pOut.push(cOut,l2),l2);
    assert checkNoException(()->l1.wf());
    assert checkNoException(()->l2.wf());
    assert checkNoException(()->WellFormedness.checkInfo(pOut.push(cOut,l1),l1)): " "+l1+"\n\n"+l2;
    assert checkNoException(()->WellFormedness.checkInfo(pOut.push(cOut,l2),l2));
    var res=compose(false,pOut,cOut,l1,l2,errC,errM);
    assert checkNoException(()->res.wf());
    assert checkNoException(()->WellFormedness.checkInfo(pOut.push(cOut,res),res));
    //Not working in all the cases assert Rename.deepCheckInfo(pOut.push(cOut,res),res);
    return res;
    }
  public CoreL compose(boolean inRename,Program pOut,C cOut,CoreL l1, CoreL l2,MetaError errC,MetaError errM){
    this.pOut=pOut;
    this.cOut=cOut;
    this.errC=errC;
    this.errM=errM;  
    this.inRename=inRename;
    topLeft=l1;
    topRight=l2;
    if(!inRename){fillMaps(l1, l2);}    
    singleMap(topLeft,topRight);
    transitiveMap();
    for(var e:innerMap.entrySet()){ map.put(e.getKey(),extraPz(e.getValue())); }
    if(!inRename){
      for(var cs:allHiddenSupertypesLeft){growHiddenError(l1,l2,cs);}
      for(var cs:allHiddenSupertypesRight){growHiddenError(l2,l1,cs);}
      }
/*    else{ //no checks, rename will do them
      l1.visitInnerLNoPrivate((li,csi)->growHiddenError(l1, l2, csi));
      l2.visitInnerLNoPrivate((li,csi)->growHiddenError(l2, l1, csi));
      }*/
    Plus plus=new Plus(L());
    CoreL l=plus.plus(l1, l2);
    wellFormedRefineAndNoCircularImplements(l);
    return l;
    }
  private void fillMaps(CoreL l1, CoreL l2) {
    General.Consumer3<LinkedHashSet<List<C>>,CoreL,List<C>> f;//needed for type inference
    f=(c,li,csi)->{for(var w:li.info().hiddenSupertypes()){addPublicCsOfP(w,csi,c);}};
    allHiddenSupertypesLeft=allFromInfo(l1,f);
    allHiddenSupertypesRight=allFromInfo(l2,f);
    f=(c,li,csi)->{for(var w:li.info().watched()){addPublicCsOfP(w,csi,c);}};
    allWatchedLeft=allFromInfo(l1,f);
    allWatchedRight=allFromInfo(l2,f);
    f=(c,li,csi)->{
      for(var w:li.info().coherentDep()){addPublicCsOfP(w,csi,c);}
      for(var w:li.info().metaCoherentDep()){addPublicCsOfP(w,csi,c);}
      };
    allRequiredCoherentLeft=allFromInfo(l1,f);
    allRequiredCoherentRight=allFromInfo(l2,f); 
    }
  private static final String errBase="The nested class can not be turned into an interface; ";
  //private static final String errInRename="The nested class can not be turned into an interface inside of a rename operation";
  private static final String errGrowHidden="This interface is privately implemented but the summed version is larger: ";
  RuntimeException err(IMWT fault1,IMWT fault2,Supplier<String> ss){
    Supplier<String> msg=()->ss.get()+"\n"+errM.pos(fault2.mwt)+"\n-----\n";
    return errM.throwErr(fault1.mwt,msg);
    }
  RuntimeException err(List<C> f1,CoreL l1,CoreL l2,Supplier<String> ss){
    Supplier<String> msg=()->
      errC.introName(f1)+errC.intro(l1,false)
      +(l2==null?"":errC.introName(f1)+errC.intro(l2,false))
      +ss.get()
      +"\n"+errC.pos(l1)+"\n-----\n"+"\n"+errC.pos(l2);
    var lazy=new L42£LazyMsg(msg);
    throw new L42Error(errC.wrap.apply(lazy));
    }
  private static HashSet<Integer> otherNs(CoreL other){
    return new Accumulate<HashSet<Integer>>(){
      @Override public HashSet<Integer>empty(){return new HashSet<>();}
      @Override public void visitMWT(MWT mwt){
        super.visitMWT(mwt);
        if(mwt.key().hasUniqueNum()){acc().add(mwt.key().uniqueNum());}
        }
      @Override public void visitNC(NC nc){
        super.visitNC(nc);
        if(nc.key().hasUniqueNum()){acc().add(nc.key().uniqueNum());}
        }
      }.of(other);
    }
  private static CoreL normalizePrivates(CoreL l,HashSet<Integer>otherNs){
    otherNs.remove(0);
    HashMap<Integer,Integer>next=new HashMap<>();
    for(int i:otherNs){
      int nextI=i+1;
      while(otherNs.contains(nextI)){nextI+=1;}
      next.put(i, nextI);
      }
    return l.accept(new CloneVisitor(){
      @Override public C visitC(C c){
        if(!c.hasUniqueNum()){return c;}
        Integer n=next.get(c.uniqueNum());
        if(n==null){return c;}
        return c.withUniqueNum(n);
        }
      @Override public S visitS(S s){
        if(!s.hasUniqueNum()){return s;}
        Integer n=next.get(s.uniqueNum());
        if(n==null){return s;}
        return s.withUniqueNum(n);
        }
      });
    }
  private void wellFormedRefineAndNoCircularImplements(CoreL l){
    l.visitInnerLNoPrivate((li,csi)->{
      if(li.ts().size()<=1){return;}
      HashMap<S,P>nonRefined=new HashMap<>();
      for(T t:li.ts()){
        var csj=_publicCsOfP(t.p(), csi);
        if(csj==null){continue;}
        if(t.p().equals(P.pThis0)){
          err(csi,li,null,()->"The sum would induce a circular interface implementation for "+li.ts());
          }
        var lj=l.cs(csj);
        for(var m:lj.mwts()){
          if(lj.info().refined().contains(m.key())){continue;}
          var wrong=nonRefined.get(m.key());
          if(wrong==null){nonRefined.put(m.key(),t.p());continue;}
          err(csi,li,null,()->"No unique source for "+m.key()+"; it originates from both "+t.p()+" and "+wrong);
          }
        }
      });
    }
  private void growHiddenError(CoreL l1, CoreL l2, List<C> cs) {
    var l2cs=l2._cs(cs);
    if(l2cs==null){return;}
    var l1cs=l1._cs(cs);
    if(l1cs==null){return;}
    if(!moreThen(l2cs,l1cs)){return;}
    assert !l1cs.info().close();
    err(cs,l1cs,l2cs,()->errGrowHidden+errC.intro(l2cs,false).stripTrailing());
    }
  public  static List<C> culpritFromInfo(CoreL l,BiFunction<CoreL,List<C>,List<C>> f){
    Object[] res={null};
    l.visitInnerLNoPrivate((li,csi)->{
      if(res[0]==null){res[0]=f.apply(li,csi);}
      });
    @SuppressWarnings("unchecked") var list = (List<C>)res[0];
    return list;    
    }
  public static LinkedHashSet<List<C>> allFromInfo(CoreL l,General.Consumer3<LinkedHashSet<List<C>>,CoreL,List<C>> f){
    LinkedHashSet<List<C>> res=new LinkedHashSet<>();
    l.visitInnerLNoPrivate((li,csi)->f.accept(res,li,csi));
    return res;
    }
  static void addPublicCsOfP(P.NCs p,List<C>csi,LinkedHashSet<List<C>> c){
    var cs=_publicCsOfP(p, csi);
    if(cs!=null){c.add(cs);}
    }
  public static boolean moreThen(CoreL l1,CoreL l2){
    return moreThen(l1,l2,(p1,p2)->p1.equals(p2));
    }
  public static boolean moreThen(CoreL l1,CoreL l2,BiFunction<P,P,Boolean>pathComp){
    if(!l2.isInterface()){return false;}
    for(T t1:l1.ts()){
      if(l2.ts().stream().noneMatch(t2->pathComp.apply(t1.p(),t2.p()))){return true;}
      }
    for(MWT m1:l1.mwts()){
      if(l2.mwts().stream().noneMatch(m2->m2.key().equals(m1.key()))){return true;}
      }
    return false;
    }
  public static boolean implemented(CoreL l,List<C> cs){
    boolean[]wasIn={false};
    l.visitInnerLNoPrivate((li,csi)->{//could short circut to be faster
      for(T ti:li.ts()){
        var _cs=_publicCsOfP(ti.p(), csi);
        if(cs.equals(_cs)){
          wasIn[0]=true;}
        }
      for(var pi:li.info().hiddenSupertypes()){
        P.NCs pj=Program.emptyP.from(pi,csi);
        if(pj.n()!=0){continue;}
        assert !pj.hasUniqueNum():pj;
        if(cs.equals(pj.cs())){
          wasIn[0]=true;}
        }
      });
    return wasIn[0];
    }
  public LinkedHashSet<P.NCs> extraPz(LinkedHashSet<List<C>>csz){
    var res = new LinkedHashSet<P.NCs>();
    for(var cs:csz){ fillExtraPz(res,topLeft,cs); fillExtraPz(res,topRight,cs); }
    return res;
    }
  void fillExtraPz(LinkedHashSet<P.NCs> res,CoreL l,List<C> cs){
    res.add(P.NCs.of(0, cs));
    var li=l._cs(cs);
    if(li==null){ return; }
    for(T t:li.ts()){
      P.NCs p=t.p().toNCs();
      if(p.n()<=cs.size()){ continue; }
      res.add(p.withN(p.n()-cs.size()));
      }
    }
  public void singleMap(CoreL l1,CoreL l2){
    l1.visitInnerLNoPrivate((li,csi)->singleMapOne(li,l1,l2,csi));
    l2.visitInnerLNoPrivate((li,csi)->singleMapOne(li,l2,l1,csi));
    }
  void transitiveMap(){for(var e:innerMap.values()){fixPoint(e);}}
  private void fixPoint(LinkedHashSet<List<C>> e) {
    int size=e.size();
    for(var cs:new ArrayList<>(e)){
      var mapped=innerMap.get(cs);
      if(mapped!=null){e.addAll(mapped);}
      }
    if(size!=e.size()){fixPoint(e);}
    }
  public static List<C> _publicCsOfP(P p,List<C> cs){
    if(!p.isNCs()){return null;}//not the place to give error for implements Void
    P.NCs pi=Program.emptyP.from(p.toNCs(),cs);
    if(pi.n()!=0){return null;}
    if(pi.hasUniqueNum()){return null;}
    return pi.cs();
    }
  public static P miniFrom(List<C> into,P.NCs that){
    if(that.n()==0){ return miniFromCs(into,that.cs()); }
    return that.withN(that.n()+into.size());
    }
  public static P miniFromCs(List<C> into,List<C> that){
    for(int i:range(into)){
      if(that.size()<=i){return P.of(into.size()-i,L());}
      if(that.get(i).equals(into.get(i))){continue;}
      return P.of(into.size()-i, L(that.stream().skip(i)));
      }
    return P.of(0,L(that.stream().skip(into.size())));
    }
  public void singleMapOne(CoreL lInner,CoreL lTopThis,CoreL lTopOther,List<C>cs){
    LinkedHashSet<List<C>> res=innerMap.get(cs);
    boolean inMap=res!=null;
    if(!inMap){res=new LinkedHashSet<>();}
    for(T t:lInner.ts()){
      var cst=_publicCsOfP(t.p(),cs);
      if(cst==null){continue;}
      CoreL liCs=lTopThis._cs(cst);
      if(liCs==null){continue;}
      CoreL ljCs=lTopOther._cs(cst);
      if(ljCs==null){continue;}
      if(Sum.moreThen(ljCs,liCs)){
        res.add(cst);
        for(T t0:ljCs.ts()){
          var cs0=_publicCsOfP(t0.p(),cst);
          if(cs0!=null){res.add(cs0);}
          }
        for(T t0:liCs.ts()){
          var cs0=_publicCsOfP(t0.p(),cst);
          if(cs0!=null){res.add(cs0);}
          }
        }//we could cache growing interfaces
      }
    if(!inMap && !res.isEmpty()){innerMap.put(cs, res);}
    }
  public static void paths(ArrayList<P.NCs> c,CoreL l,Program p0,P.NCs source){
    //Can be false when called under rename, since emptyLs are added 
    //assert l.isInterface(); 
    l.withNcs(L()).accept(new Accumulate<Void>(){
      @Override public void visitP(P p){
        if(!p.isNCs()){return;}
        P pp=p0.from(p,source);
        if(!c.contains(pp)){c.add(pp.toNCs());}
        }
      @Override public void visitMWT(MWT m){
        if(!m.key().hasUniqueNum()){super.visitMWT(m);}
        }
      });
    }
  public static void openImplements(Program p,Consumer<String> err){
    for(T t:p.topCore().ts()){
      var path=t.p().toNCs();
      if(path.hasUniqueNum()){err.accept("a private interface is implemented");}
      LL l=p.pop(path.n()).top._cs(path.cs());
      if(l==null || l.isFullL()){err.accept("an undefined interface is implemented");}
      if(((CoreL)l).info().close()){err.accept("a close interface is implemented");}
      }
    }
  class Plus{
    public Plus(Plus other, C c) {this.cs=pushL(other.cs, c);}
    public Plus(List<C> cs) {this.cs=cs;}
    List<C> cs;
    Plus addC(C c){return new Plus(this,c);}
    CoreL plus(CoreL l1,CoreL l2){
      boolean isInterface3=plusInterface(l1.isInterface(),l2.isInterface(),l1,l2);
      ArrayList<T> ts3=new ArrayList<>(l1.ts());
      for(T t:l2.ts()){plusEqualTs(ts3,t);}
      var mwts1=l1.mwts();
      var mwts2=l2.mwts();
      var ncs1=l1.ncs();
      var ncs2=l2.ncs();
      if(l1.isInterface()!=l2.isInterface()){
        if(l1.isInterface()){
          mwts2=L(mwts2.stream().filter(m->!m.key().hasUniqueNum()));
          ncs2=L(ncs2.stream().filter(m->!m.key().hasUniqueNum()));
          }
        else{
          mwts1=L(mwts1.stream().filter(m->!m.key().hasUniqueNum()));
          ncs1=L(ncs1.stream().filter(m->!m.key().hasUniqueNum()));        
          }
        }
      boolean i1=Sum.implemented(topLeft,cs);
      boolean i2=Sum.implemented(topRight,cs);
      ArrayList<IMWT> imwts=new ArrayList<>();
      for(var m:mwts1){imwts.add(new IMWT(i1,m));}
      for(var m:mwts2){imwts.add(new IMWT(i2,m));}
      Info info1=l1.info();
      Info info2=l2.info();
      if(isInterface3&&!l1.isInterface()){info1=info1.withWatched(L()).withCoherentDep(L());}
      if(isInterface3&&!l2.isInterface()){info2=info2.withWatched(L()).withCoherentDep(L());}
      if(!info1.nativeKind().isEmpty() && !info2.nativeKind().isEmpty()){
        if(!info1.nativeKind().equals(info2.nativeKind())){
          err(cs,l1,l2,()->"The two nativeKind are incompatible:"+l1.info().nativeKind()+" and "+l2.info().nativeKind());
          }
        info2=info2.withNativeKind("");
        }
      if(!info1.nativePar().isEmpty() && !info2.nativePar().isEmpty()){
        if(!info1.nativePar().equals(info2.nativePar())){
          err(cs,l1,l2,()->"The two nativePar are incompatible:"+l1.info().nativePar()+" and "+l2.info().nativePar());
          }
        info2=info2.withNativePar(L());
        }
      Info info3=info1.sumInfo(info2);
      LinkedHashSet<S> refineds=new LinkedHashSet<>(info3.refined());
      ArrayList<P.NCs> typeDep=new ArrayList<>(info3.typeDep());
      var mapped=innerMap.get(cs);
      if(mapped!=null){useMapped(ts3,refineds, imwts, typeDep, mapped,map.get(cs));}
      info3=info3.withTypeDep(LL(typeDep)).withRefined(L(refineds.stream()));
      List<MWT>mwts3=plusIMWTs(imwts,l1,l2);
      List<NC> ncs3=plusNCs(ncs1, ncs2);
      List<Doc> doc3=mergeU(l1.docs(),l2.docs());
      List<Pos> pos=mergeU(l1.poss(),l2.poss());
      return new CoreL(pos, isInterface3, LL(ts3), mwts3, ncs3, info3, doc3);
      }
    private void useMapped(ArrayList<T> ts3,LinkedHashSet<S> refineds, ArrayList<IMWT> imwts, ArrayList<P.NCs> typeDep,LinkedHashSet<List<C>> mapped,LinkedHashSet<P.NCs> fullMapped){
      assert fullMapped!=null;
      Program p;
      if(!topLeft.inDom(cs)){p=pOut.push(cOut,topRight).navigate(cs);}
      else{p=pOut.push(cOut,topLeft).navigate(cs);}
      for(var pi:fullMapped){
        plusEqualTs(ts3,P.coreThis0.withP(miniFrom(cs,pi))); 
        }
      for(var csi:mapped){
        P.NCs path=p.minimize(P.of(cs.size(),csi)).toNCs();
        var left=topLeft._cs(csi);
        if(left!=null){
          for(var m:left.mwts()){
            MWT mi=m.withMh(p.from(stripDocs(m.mh()),path));
            if(!Sum.this.inRename){imwts.add(new IMWT(false,mi));}
            refineds.add(mi.key());
            }
          paths(typeDep,left,p,path);
          }
        var right=topRight._cs(csi);
       if(right!=null){
          for(var m:right.mwts()){
            MWT mi=m.withMh(p.from(m.mh(),path));
            if(!Sum.this.inRename){imwts.add(new IMWT(false,mi));}
            refineds.add(mi.key());
            }
          paths(typeDep,right,p,path);
          }
        }
      }
    private MH stripDocs(MH mh) {
      return new MH(mh.mdf(),L(),mh.t().withDocs(L()),mh.key(),
        L(mh.pars(),t->t.withDocs(L())),
        L(mh.exceptions(),t->t.withDocs(L()))
        );
      }
    private void plusEqualTs(ArrayList<T> ts3, T t){
      if(ts3.stream().noneMatch(t3->t3.p().equals(t.p()))){ts3.add(t);}
      }
    void addAllFromCsi(CoreL top,List<C> csi,ArrayList<IMWT> imwts){
       var in=top._cs(csi);
       if(in==null){return;}
       for(var m:in.mwts()){
         MH mh=m.mh().withDocs(L());
         mh.withT(mh.t().withDocs(L()));
         mh.withPars(L(mh.pars(),t->t.withDocs(L())));
         mh.withExceptions(L(mh.exceptions(),t->t.withDocs(L())));
         m=m.withDocs(L()).withMh(mh);
         imwts.add(new IMWT(false,m));
         }
       }
    NC plus(NC nc1,NC nc2){
      assert nc1.key().equals(nc2.key());
      assert !nc1.key().hasUniqueNum();
      return new NC(
        mergeU(nc1.poss(),nc2.poss()),
        mergeU(nc1.docs(),nc2.docs()),
        nc1.key(),
        this.addC(nc1.key()).plus(nc1.l(),nc2.l())
        );
      }
    public static final String errConflict="Conflicting implementation: the method is implemented on both sides of the sum";
    IMWT plus(IMWT imwt1,IMWT imwt2,CoreL l1,CoreL l2){
      boolean eqRetT=Utils.equalT(imwt1.mwt.mh().t(),imwt2.mwt.mh().t());
      boolean eqMHParsExc=Utils.equalMHignoreRet(imwt1.mwt.mh(),imwt2.mwt.mh());
      boolean eqMH=eqRetT && eqMHParsExc;
      boolean abs1=imwt1.mwt._e()==null;
      boolean abs2=imwt2.mwt._e()==null;
      boolean oneInterf=imwt1.isInterface || imwt2.isInterface;
      if(!eqMHParsExc){err(imwt1,imwt2,()->
        "The two headers are incompatible:\n"+errM.intro(imwt2.mwt,false).stripTrailing());
        }
      if(!abs1 && !abs2){err(imwt1,imwt2,()->errConflict);}
      if(eqMH){
        //if(abs1 && abs2){return new IMWT(oneInterf,accDoc(imwt1,imwt2));} 
        if(abs2){return new IMWT(oneInterf,accDoc(imwt1,imwt2));} 
        var mwt=accDoc(imwt1,imwt2).with_e(imwt2.mwt._e()).withNativeUrl(imwt2.mwt.nativeUrl());
        return new IMWT(oneInterf,mwt);
        } 
      boolean loseSafeLeftiIs1=loseSafe(topLeft,l1,imwt1.mwt.key(),imwt2.mwt.mh());
      boolean loseSafeRightiIs1=loseSafe(topRight,l2,imwt1.mwt.key(),imwt2.mwt.mh());
      boolean loseSafeLeftiIs2=loseSafe(topLeft,l1,imwt2.mwt.key(),imwt1.mwt.mh());
      boolean loseSafeRightiIs2=loseSafe(topRight,l2,imwt2.mwt.key(),imwt1.mwt.mh());
      var safe1=loseSafeLeftiIs1 || loseSafeRightiIs1;//i=1
      var safe2=loseSafeLeftiIs2 || loseSafeRightiIs2;//i=2
      if(!abs1){return loseSafeUniqueRes(imwt1,imwt2,safe1,!safe2);}
      if(!abs2){return loseSafeUniqueRes(imwt2,imwt1,safe2,!safe1);}
      assert abs1 && abs2;
      if(imwt1.isInterface && imwt2.isInterface){
        err(imwt1,imwt2,()->"The two headers are incompatible:\n"+errM.intro(imwt2.mwt,false).stripTrailing());
        }
      if(imwt1.isInterface){return loseSafeUniqueRes(imwt1,imwt2,safe1,!safe2);}//i=1
      if(imwt2.isInterface){return loseSafeUniqueRes(imwt2,imwt1,safe2,!safe1);}//i=2
      boolean iIs1=loseSafeUnique(imwt1,imwt2.mwt.mh(),safe1,!safe2);
      boolean iIs2=loseSafeUnique(imwt2,imwt1.mwt.mh(),safe2,!safe1);
      assert !(iIs1 && iIs2);
      if(iIs1){return new IMWT(oneInterf,accDoc(imwt1,imwt2));}
      if(iIs2){return new IMWT(oneInterf,accDoc(imwt2,imwt1));}
      assert !iIs1 && !iIs2;
      var msg="The methods have different signatures:\n"+errM.intro(imwt2.mwt,false)
        +((safe1||safe2)?"But there is ambiguous refinement between those two signatures"
          :"But there is no local refinement between those two signatures");
      throw err(imwt1,imwt2,()->msg);
      }
    boolean loseSafe(CoreL l,CoreL lCs,S s,MH mh){
      Program p=pOut.push(cOut,l);
      if(!lCs.info().refined().contains(s)){return false;}
      for(T t:lCs.ts()){
        if(!t.p().isNCs()){continue;}
        Program pIn=p._navigate(cs);
        if(pIn==null){return false;}//Unclear, should be return false or continue?
        var ncs=t.p().toNCs();
        CoreL li=pIn._ofCore(ncs);
        if(li==null){continue;}
        var e=_elem(li.mwts(),s);
        if(e==null){continue;}
        MH oldMh=pIn.from(e.mh(),ncs);
        if(Utils.equalMH(oldMh,mh)){return true;}
        }
      return false;
      }    
    IMWT loseSafeUniqueRes(IMWT imwt,IMWT imwtLose,boolean canWin,boolean otherCanNot){
      var ok=loseSafeUnique(imwt,imwtLose.mwt.mh(),canWin,otherCanNot);
      if(ok){return new IMWT(imwt.isInterface||imwtLose.isInterface,accDoc(imwt,imwtLose));}
      throw err(imwt,imwtLose,
        ()->"The methods have different signatures:\n"+errM.intro(imwtLose.mwt,false)
        +"But there is no local refinement between those two signatures");
      }
    boolean loseSafeUnique(IMWT imwt,MH mh,boolean canWin,boolean otherCanNot){
      if(!canWin){return false;}
      if(imwt.mwt._e()!=null || imwt.isInterface){return true;}
      return otherCanNot;
      }
    MWT accDoc(IMWT ia,IMWT ib){return Utils.accDoc(ia.mwt,ib.mwt);}
    List<NC> plusNCs(List<NC> a,List<NC> b){
      return L(c->{
        for(var mi:a){
          var other=_elem(b,mi.key());
          if(other==null){
            c.add(mi.withL(addC(mi.key()).plusOnlyMap(topLeft,mi.l())));}//topLeft or topRight? it does not change any tests!
          else{c.add(plus(mi,other));}
          }
        for(var mi:b){
          var other=_elem(a,mi.key());
          if(other==null){c.add(mi.withL(addC(mi.key()).plusOnlyMap(topRight,mi.l())));}//topRight or topLeft?
          }
        });    
      }
    private CoreL plusOnlyMap(CoreL top, CoreL l) {
      var mapped=innerMap.get(cs);
      if(mapped==null){return l.withNcs(plusOnlyMap(top,l.ncs()));}
      var fullMapped=map.get(cs);
      assert fullMapped!=null;
      ArrayList<T> ts=new ArrayList<>(l.ts());
      boolean i=Sum.implemented(top,cs);
      ArrayList<IMWT> imwts=new ArrayList<>();
      for(var m:l.mwts()){imwts.add(new IMWT(i,m));}
      LinkedHashSet<S> refineds=new LinkedHashSet<>(l.info().refined());
      ArrayList<P.NCs> typeDep=new ArrayList<>(l.info().typeDep());
      useMapped(ts,refineds,imwts,typeDep,mapped,fullMapped);
      List<MWT>mwts=plusIMWTs(imwts,l,l);
      Info info0=l.info().withTypeDep(LL(typeDep)).withRefined(L(refineds.stream()));
      return l.withTs(ts).withMwts(mwts).withNcs(plusOnlyMap(top,l.ncs())).withInfo(info0);
      }
    private List<Core.NC> plusOnlyMap(CoreL top, List<Core.NC> ncs){
      return L(ncs,(c,nci)->{
        c.add(nci.withL(addC(nci.key()).plusOnlyMap(top,nci.l())));
        });
      }
    List<MWT> plusIMWTs(ArrayList<IMWT> that,CoreL l1,CoreL l2){return L(c->plusIMWTs(c,that,l1,l2));}
    void plusIMWTs(ArrayList<MWT> c,ArrayList<IMWT> that,CoreL l1,CoreL l2){
      //would be more efficient to start from the end and accumulate the sum in the center...
      //but would mess up the ordering
      while(!that.isEmpty()){
        IMWT current=that.get(0);
        S key=current.mwt.key();
        int index=1;
        IMWT inIndex=null;
        for(;index<that.size();index+=1){
          inIndex=that.get(index);
          if(inIndex.mwt.key().equals(key)){break;}
          }//index is now the position of the equal key
        if(index!=that.size()){
          that.set(0,plus(current,inIndex,l1,l2));
          that.remove(index);
          continue;
          }
        that.remove(0);
        c.add(current.mwt);
        }
      }
    boolean plusInterface(boolean interface1,boolean interface2,CoreL topLeftCs,CoreL topRightCs){
      if(interface1 && interface2){
        if(!topLeftCs.info().close() && !topRightCs.info().close()){return true;}
        err(cs,topLeftCs,topRightCs,()->"One of the two interfaces in "+errC.intro(cs,false)
          +"is close (have private methods or implements private interfaces)."
          +" Only open interfaces can be composed"
          );
        }
      if(!interface1 && !interface2){
        var leftClose=topLeftCs.info().close();
        var rightClose=topRightCs.info().close();
        if(!leftClose || !rightClose){return interface1;}
        err(cs,topLeftCs,topRightCs,()->"The two nested classes are both closed, thus can not be composed.");
        }
      if(!interface1 && interface2){
        Program pCs=pOut.push(cOut,topLeft).navigate(cs);
        return differentInterfaces(allRequiredCoherentLeft,allWatchedLeft,topLeftCs,pCs);  
        }
      assert interface1 && !interface2;
      Program pCs=pOut.push(cOut,topRight).navigate(cs);
      return differentInterfaces(allRequiredCoherentRight,allWatchedRight,topRightCs,pCs);
      }
    boolean differentInterfaces(LinkedHashSet<List<C>> coherents,LinkedHashSet<List<C>> watcheds, CoreL topCs,Program pCs){
      if(inRename){return true;} 
      var errCoherent=errBase+"since it is used with 'class' modifier (is required coherent)";
      if(coherents.contains(cs)){err(cs,topCs,null,()->errCoherent);}
      var errWatched=errBase+"since its privates are used by other code (is watched)";
      if(watcheds.contains(cs)){err(cs, topCs,null,()->errWatched);}
      var errImplemented=errBase+"some public methods are implemented";
      boolean absPublic=topCs.mwts().stream().allMatch(m->m._e()==null||m.key().hasUniqueNum());
      if(!absPublic){err(cs,topCs,null,()->errImplemented);}
      openImplements(pCs,s->err(cs,topCs,null,()->errBase+s));
      return true;
      }
    }
  }  
class IMWT{
  final boolean isInterface;
  final MWT mwt;
  @Override public String toString(){
    return "IMWT("+isInterface+","+mwt+")"; 
    }
  IMWT(boolean isInterface,MWT mwt){this.isInterface=isInterface;this.mwt=mwt;}
  public static List<IMWT> of(boolean isInterface,List<MWT> mwts){
    return L(mwts,(c,m)->c.add(new IMWT(isInterface,m)));
    }
  }