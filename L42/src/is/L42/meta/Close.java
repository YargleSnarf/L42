package is.L42.meta;

import static is.L42.generated.LDom._elem;
import static is.L42.tools.General.L;
import static is.L42.tools.General.bug;
import static is.L42.tools.General.popL;
import static is.L42.tools.General.toOneOr;
import static is.L42.tools.General.toOneOrBug;
import static is.L42.tools.General.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import is.L42.common.EndError;
import is.L42.common.Program;
import is.L42.generated.C;
import is.L42.generated.Core;
import is.L42.generated.Core.Doc;
import is.L42.generated.Core.MCall;
import is.L42.generated.Core.T;
import is.L42.generated.Core.MH;
import is.L42.generated.Core.L.MWT;
import is.L42.generated.Mdf;
import is.L42.generated.P;
import is.L42.generated.Pos;
import is.L42.generated.S;
import is.L42.generated.X;
import is.L42.nativeCode.EagerCacheGenerator;
import is.L42.platformSpecific.javaTranslation.L42Any;
import is.L42.platformSpecific.javaTranslation.L42Error;
import is.L42.platformSpecific.javaTranslation.L42£LazyMsg;
import is.L42.tools.General;
import is.L42.translationToJava.J;
import is.L42.typeSystem.Coherence;

public class Close extends GuessFields{
  Program p;
  HashMap<X,MH> capsMuts=new HashMap<>();
  ArrayList<MH> ks=new ArrayList<>();
  HashSet<X> fieldNames=new HashSet<>();
  ArrayList<MWT> newMWTs=new ArrayList<>();
  public Core.L close(Program p,List<C> cs,Function<L42£LazyMsg,L42Any>wrap){
    if(cs.isEmpty()){return close(p,wrap);}
    var pIn=p.navigate(P.of(0, cs));
    var l=close(pIn,wrap);
    pIn=pIn.update(l,false);
    return pIn._ofCore(P.of(cs.size(),General.L()));
    }
  public Core.L close(Program p,Function<L42£LazyMsg,L42Any>wrap){
    this.p=p;
    this.err=new MetaError(wrap);
    var l=p.topCore();
    if(l.info().close()){err.throwErr(l,"Class is already close");}
    this.addGettersSetters(p);
    for(var m:abs){
      if(!Coherence.validConstructorSignature(p, m.mh())){continue;}
      if(!m.key().xs().containsAll(getters.keySet())){continue;}
      //constructors can initialize more fields then the getters
      fieldNames.addAll(m.key().xs());
      ks.add(m.mh());
      }
    for(MH k:ks){
      if(k.key().xs().size()!=fieldNames.size()){errorAmbiguousK(l,k);}
      }
    for(var m:l.mwts()){process(m);}
    l= l.withMwts(L(this.newMWTs.stream())).withInfo(l.info().withClose(true));
    J newJ=new J(p.update(l,false),null,false,null,true);
    assert newJ.fields!=null:
    "";
    //TODO: need more? is this needed?//that is, can this code be triggered?
    try{new EagerCacheGenerator().clearCacheGood(newJ);}
    catch(EndError ee){err.throwErr(l,ee.getMessage());}
    return l;
    }
  private void errorAmbiguousK(Core.L l,MH k){
    Supplier<Object> t=()->l.mwts().stream().filter(m->m.mh()==k).reduce(toOneOrBug()).get();
    Supplier<String> msg=()->"abstract class method "+k.key()
      +" is an ambiguos constructor; all those fields should be initialized: "+
     L(fieldNames.stream().sorted((x1,x2)->x1.inner().compareTo(x2.inner())));
    err.throwErr(t,msg);
    }
  public void process(MWT m){
    if(m._e()==null){
      if(ks.contains(m.mh())){processK(m);return;}
      if(Coherence.allowedAbstract(m.mh(),ks)){processAllowedAbs(m);return;}
      if(m.key().xs().isEmpty()){processGetter(m);return;}
      if(m.key().xs().size()==1){processSetter(m);return;}
      processAllowedAbs(m);//we just let it alone
      }
    if(match("lazyCache",m)){processLazyCache(m);return;}
    var invalidate=match("invalidateCache",m);
    var eager=match("readEagerCache",m);
    if(!invalidate &&!eager){processBase(m);return;}
    if(!m.mh().mdf().isClass()){err.throwErr(m,"must be a class method");}
    if(invalidate){processInvalidate(m);return;}
    if(eager){processEager(m);return;}
    assert false;
    }
  public Core.E fCapsExposer(MWT mErr,Pos pos,X x,T t){
    MH mh=this.capsMuts.get(x);
    if(mh!=null){
      if(!mh.t().equals(t)){
        err.throwErr(mErr,"first parameter does not correspond to a capsule field; ambiguous field type: "+mh.t()+" or "+t);
        }
      return Utils.thisCall(pos,mh.key(),General.L());
      }    
    if(!fieldNames.contains(x)){
      err.throwErr(mErr,"first parameter does not correspond to a capsule field: the parameter name is not a field");
      }
    for(var ki:ks){
      int i=ki.key().xs().indexOf(x);
      assert i!=-1;
      T ti=ki.pars().get(i);
      if(!ti.p().equals(t.p())){
        err.throwErr(mErr,"first parameter does not correspond to a capsule field; ambiguous field type: "+ti+" or "+t);
        }
      if(!ti.mdf().isCapsule()){
        err.throwErr(mErr,"first parameter does not correspond to a capsule field; constructor "+ki+" initializes it as "+ti);
        }
      }
    var setter=setters.get(x);
    if(setter!=null){for(MWT mi:setter){
      T ti=mi.mh().pars().get(0);
      if(!ti.p().equals(t.p())){
        err.throwErr(mErr,"first parameter does not correspond to a capsule field; ambiguous field type: "+ti+" or "+t);
        }
      if(!ti.mdf().isCapsule()){
        err.throwErr(mErr,"first parameter does not correspond to a capsule field; setter "+mi+" update it as "+ti);
        }      
      }}    
    int countHs=0;
    for(MWT m:abs){
      X xi=Coherence.fieldName(m.mh());
      if(!xi.equals(x)){continue;}
      assert !m.key().hasUniqueNum();
      countHs=Math.max(countHs,m.key().m().length()-xi.inner().length());
      if(!m.mh().mdf().isIn(Mdf.Mutable,Mdf.Lent)){continue;}
      if(!m.mh().t().mdf().isIn(Mdf.Mutable,Mdf.Lent)){continue;}
      err.throwErr(mErr,"first parameter does not correspond to a capsule field: the field is exposed by "+mh);
      }
    String hs="#".repeat(countHs+1);
    S s=new S(hs+x.inner(),General.L(),0);
    mh=new MH(Mdf.Mutable,General.L(),t,s,General.L(),General.L());
    this.capsMuts.put(x,mh);
    this.newMWTs.add(new MWT(General.L(pos),General.L(),mh,"",null));
    return Utils.thisCall(pos,mh.key(),General.L());
    }
  String errMsgMoreThenOne(X x,Mdf recMdf1,Mdf recMdf2){
    return "More then one candidate getter/exposer with method modifier"
      +recMdf1+" or "+recMdf2+" in:"+
      General.L(abs.stream().filter(m->
        m.mh().mdf().isIn(recMdf1,recMdf2) 
        && Coherence.fieldName(m.mh()).equals(x)));
    }  
  public Core.E fAcc(MWT mErr,Pos pos,X x,Mdf recMdf1,Mdf recMdf2){
    var mh=abs.stream().filter(m->
      m.mh().mdf().isIn(recMdf1,recMdf2) 
      && Coherence.fieldName(m.mh()).equals(x)
      ).reduce(toOneOr(()->err.throwErr(mErr,()->errMsgMoreThenOne(x,recMdf1,recMdf2))));
    if(!mh.isPresent()){
      err.throwErr(mErr, "No candidate getter/exposer with name "+x
        +" and method modifier "  +recMdf1+" or "+recMdf2);
      }
    for(var ki:ks){
      int i=ki.key().xs().indexOf(x);
      assert i!=-1;
      T ti=ki.pars().get(i);
      if(!ti.mdf().isIn(Mdf.Immutable, Mdf.Capsule,Mdf.Class)){
        if(ti.mdf().isFwdImm()){
          err.throwErr(mErr,"parameter "+x+" is initialized with fwd in the constructor "+ki);  
          }
        err.throwErr(mErr,"parameter "+x+" does not correspond to an imm/capsule/class field; constructor "+ki+" initializes it as "+ti);
        }
      }
    var setter=setters.get(x);
    if(setter!=null){for(MWT mi:setter){
      T ti=mi.mh().pars().get(0);
      if(!ti.mdf().isIn(Mdf.Immutable, Mdf.Capsule,Mdf.Class)){
        err.throwErr(mErr,"parameter "+x+" does not correspond to an imm/capsule/class field; setter "+mi+" updates it as "+ti);
        }      
      }}    
    return Utils.thisCall(pos,mh.get().key().withUniqueNum(0),General.L());
    }
  public void processState(MWT m){
    var s=m.key();
    var s2=s.withUniqueNum(0);
    assert !s.hasUniqueNum();
    var m2=m.withMh(m.mh().withS(s2));
    Pos pos=m.poss().get(0);
    List<Core.E> exs=General.L(s.xs(),(ci,xi)->ci.add(new Core.EX(pos,xi)));
    m=m.with_e(Utils.thisCall(pos,s2,exs));
    newMWTs.add(m);
    newMWTs.add(m2);
    }
  public void processK(MWT m){processState(m);}
  public void processGetter(MWT m){processState(m);}
  public void processSetter(MWT m){processState(m);}
  public void processLazyCache(MWT m){
    if(!m.nativeUrl().isEmpty()){err.throwErr(m,"can not be made cached, since it is already native");}    
    newMWTs.add(m.withNativeUrl("trusted:lazyCache"));
    }
  public void processInvalidate(MWT m){
    if(m.key().xs().isEmpty()){
      err.throwErr(m,"first parameter must refer to a capsule field as mut or lent");
      }
    Mdf first=m.mh().pars().get(0).mdf();
    if(!first.isIn(Mdf.Mutable,Mdf.Lent)){
      err.throwErr(m,"first parameter must refer to a capsule field as mut or lent");
      }
    if(!m.mh().pars().stream().skip(1).allMatch(t->t.mdf().isIn(Mdf.Immutable,Mdf.Readable,Mdf.Class))){
      err.throwErr(m,"non first parameters must be imm, readable or class");
      }
    var pos=m._e().pos();
    S s=m.key();
    assert !s.xs().isEmpty();
    var xs1=popL(s.xs());
    var ts1=popL(m.mh().pars());
    S s1=s.withXs(xs1);
    List<Core.E> exs1=General.L(ci->{
      X x=s.xs().get(0);
      T t=m.mh().pars().get(0);
      ci.add(fCapsExposer(m,pos,x,t));
      for(var xi:xs1){ci.add(new Core.EX(pos,xi));}
      });
    var mh1=new MH(Mdf.Mutable,m.mh().docs(),m.mh().t(),s1,ts1,m.mh().exceptions());
    var m1=m.withMh(mh1);
    m1=m1.with_e(Utils.ThisCall(pos,s,exs1));
    newMWTs.add(m1);
    newMWTs.add(m);
    }
  public void processEager(MWT m){
    var ok=m.mh().pars().stream().allMatch(t->t.mdf().isIn(Mdf.Immutable,Mdf.Readable,Mdf.Class));
    if(!ok){err.throwErr(m,"all parameters must be imm, readable or class");}
    var s=m.key();
    var s1=s.withXs(General.L());
    assert !s.hasUniqueNum();
    var old=_elem(p.topCore().mwts(),s1);
    if(old!=null){
      assert false: s1+" "+s+" "+old;
      throw todo();/*make sum*/}
    var mh1=new MH(Mdf.Readable,m.mh().docs(),m.mh().t(),s1,General.L(),General.L());
    var m1=m.withMh(mh1);//m1: the no arg meth calling the static method s
    List<Core.E> exs1=General.L(s.xs(),(ci,xi)->ci.add(fAcc(m,m._e().pos(),xi,Mdf.Immutable,Mdf.Readable)));
    m1=m1.with_e(Utils.ThisCall(m.poss().get(0),s,exs1));
    newMWTs.add(m1.withNativeUrl("trusted:readEagerCache"));
    newMWTs.add(m);    
    }
  public void processAllowedAbs(MWT m){newMWTs.add(m);}
  public void processBase(MWT m){newMWTs.add(m);}
  public boolean match(String target,MWT m){return Utils.match(p,err,target,m);}
  }