package is.L42.top;

import java.util.ArrayList;
import java.util.List;

import is.L42.common.CTz;
import is.L42.common.EndError;
import is.L42.common.G;
import is.L42.common.GX;
import is.L42.common.Program;
import is.L42.common.TypeManipulation;
import is.L42.generated.C;
import is.L42.generated.Core;
import is.L42.generated.Core.L.MWT;
import is.L42.generated.Core.Doc;
import is.L42.generated.Core.L.Info;
import is.L42.generated.Core.MH;
import is.L42.generated.Core.T;
import is.L42.generated.Full;
import is.L42.generated.Full.L.NC;
import is.L42.generated.Half;
import is.L42.generated.I;
import is.L42.generated.LL;
import is.L42.generated.P;
import is.L42.generated.Pos;
import is.L42.generated.ST;
import is.L42.generated.Y;
import is.L42.platformSpecific.javaTranslation.Loader;
import is.L42.visitors.Accumulate;
import typeSystem.FlagTyped;

import static is.L42.generated.LDom._elem;
import static is.L42.tools.General.*;

public class Top {
  public Program top(CTz ctz, Program p)throws EndError {
    Core.L coreL=SortHeader.coreTop(p, uniqueId++);//propagates the right header errors
    List<Full.L.M> ms=((Full.L)p.top).ms();
    List<Full.L.NC> ncs=typeFilter(ms.stream(),Full.L.NC.class);
    Program p0=p.update(coreL);
    List<Half.E> e1n=L(coreL.mwts(),(c,mwti)->{
      var memi=_elem(ms,mwti.key());
      Full.E _ei=null;
      if(memi!=null){_ei=memi._e();}
      ctzAdd(ctz,p0,mwti.mh(),_ei,c);
      });
    Program p1=topNC(ctz,p0,ncs);//propagate exceptions
    assert p1.top instanceof Core.L;
    List<Core.E> coreE1n=L(e1n,(c,_ei)->{
      if(_ei==null){c.add(null);return;}
      Core.E eri=infer(new I(null,p1,G.empty()),ctz,null,_ei);
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
    Program p2=FlagTyped.flagTyped(loader,p1.update(l));//propagate illTyped
    l=p2.topCore();
    l=l.withInfo(l.info().with_uniqueId(-1));
    return p2.update(l);
    }
  private Core.L updateInfo(Program p, List<MWT>mwts) {
    Core.L l=(Core.L)p.top;
    List<MWT> mwts0=L(l.mwts(),(c,m)->{
      var newM=_elem(mwts,m.key());
      if(newM==null){c.add(m);return;}
      assert newM.with_e(null).equals(m);
      });
    assert mwts0.size()+mwts.size()==l.mwts().size();
    ArrayList<P.NCs> typePs=new ArrayList<>();
    ArrayList<P.NCs> cohePs=new ArrayList<>();
    collectDeps(p,mwts,typePs,cohePs,true);
    Info info=Info.empty.withTypeDep(L(typePs.stream())).withCoherentDep(L(cohePs.stream())); 
    return new Core.L(l.poss(), l.isInterface(), l.ts(), merge(mwts0,mwts), l.ncs(),sumInfo(l.info(),info),l.docs());
    }
  private Info sumInfo(Info info1, Info info2) {
    assert info1._uniqueId()==-1 || info2._uniqueId()==-1;
    assert info1.nativeKind().equals("") || info2.nativeKind().equals("");
    assert info1.nativePar().isEmpty() || info2.nativePar().isEmpty();
    return new Info(info1.isTyped() && info2.isTyped(),
      mergeU(info1.typeDep(),info2.typeDep()),
      mergeU(info1.coherentDep(),info2.coherentDep()),
      mergeU(info1.friends(),info2.friends()),
      mergeU(info1.usedMethods(),info2.usedMethods()),
      mergeU(info1.privateSupertypes(),info2.privateSupertypes()),
      mergeU(info1.refined(),info2.refined()),
      info1.declaresClassMethods() || info2.declaresClassMethods(),
      info1.nativeKind()+info2.nativeKind(),
      info1.nativePar().isEmpty()?info2.nativePar():info1.nativePar(),
      Math.max(info1._uniqueId(),info2._uniqueId())
      );
    }
  private Core.L updateInfo(Program p1, Core.L.NC nc) {
    List<P.NCs>dep=new ArrayList<>();
    collectDeptDocs(nc.docs(),dep);
    Core.L l=(Core.L)p1.top;
    var info=l.info();
    info=info.withTypeDep(mergeU(info.typeDep(),dep));
    l=l.withNcs(pushL(l.ncs(),nc)).withInfo(info);
    return l;
    }
  public static void collectDeptDocs(List<Doc> docs, List<P.NCs> acc) {
    for(Doc d:docs){
      collectDeptDocs(d.docs(),acc);
      if(d._pathSel()==null){continue;}
      if(!d._pathSel().p().isNCs()){continue;}
      acc.add(d._pathSel().p().toNCs());
      }
    }
  private Core.E infer(I i,CTz ctz,CTz frommed,Half.E e) throws EndError{
    if(e instanceof Core.E){return (Core.E)e;}
    if(e instanceof Full.L){
      assert i._c()!=null;
      Program p=i.p().push(i._c(),(Full.L)e); 
      Program pr=top(frommed,p);//propagate errors
      return pr.topCore();
      }
    throw bug();
    }
  private int uniqueId=0;
  private final Loader loader=new Loader();
  private Program topNC(CTz ctz, Program p, List<NC> ncs)  throws EndError{
    if(ncs.isEmpty()){return p;}
    C c0=ncs.get(0).key();
    Full.E fe=ncs.get(0).e();
    List<Full.Doc> docs=ncs.get(0).docs();
    List<Pos> poss=ncs.get(0).poss();
    ncs=popL(ncs);
    Y y=new Y(p,GX.empty(),L(),null,L());
    HalfQuadruple hq=new HalfQuadruple(y,fe);
    Half.E he=hq.e;
    I i=new I(c0,p,G.empty());
    CTz frommedCTz=p.from(ctz,P.pThis1);
    Core.E ce=infer(i,ctz,frommedCTz,he); //propagates errors
    assert ce!=null;
    Core.T t=wellTyped(p,ce);//propagate errors
    Core.E ce0=adapt(ce,t);
    coherent(p,ce0); //propagate errors
    Core.L l=(Core.L)reduce(p,ce0);//propagate errors
    assert l!=null;
    Core.L.NC nc=new Core.L.NC(poss, TypeManipulation.toCoreDocs(docs), c0, l);
    Program p1 = p.update(updateInfo(p,nc));
    Program p2=FlagTyped.flagTyped(loader,p1);//propagate errors
    //TODO: try to understand if we can avoid any bytecode generation here (is this bytecode ever usable?)    
    Program res=topNC(p2.update(c0,frommedCTz),p2,ncs);
    return res; 
    }
  private Core.E reduce(Program p, is.L42.generated.Core.E ce0)throws EndError  {
    assert ce0 instanceof Core.L;
    //TODO: must wrap exceptions and java exceptions
    return ce0;
    }
  private void coherent(Program p, is.L42.generated.Core.E ce0)throws EndError {
    }
  private Core.E adapt(Core.E ce, T t) {
    return ce;
    }
  private T wellTyped(Program p, is.L42.generated.Core.E ce)  throws EndError{
    return P.coreLibrary; 
    }
  private void ctzAdd(CTz ctz0, Program p, MH mh, Full.E _e, ArrayList<Half.E> es) {
    if(_e==null){es.add(null);return;}
    Y y=new Y(p,GX.of(mh),L(mh.t()),null,L(mh.t()));
    var hq=new HalfQuadruple(y, _e);
    ctz0.plusAcc(p,hq.resSTz, L(mh.t()));
    es.add(hq.e);
    }
  static void collectDeps(Program p0, List<MWT> mwts, ArrayList<P.NCs> typePs, ArrayList<P.NCs> cohePs,boolean justBodies) {
    var deps=new Accumulate<ArrayList<P.NCs>>(){
      public ArrayList<P.NCs> empty(){return typePs;}
      @Override public void visitL(Full.L l){throw bug();}
      private void csAux(Program p,ArrayList<C> cs,Core.L l){
        TypeManipulation.skipThis0(l.info().typeDep().stream()
          .map(e->p.from(e,0, cs))).forEach(typePs::add);
        TypeManipulation.skipThis0(l.info().coherentDep().stream()
          .map(e->p.from(e,0, cs))).forEach(cohePs::add);
        Program pi=p.push(l);
        for(C c: l.domNC()){
          cs.add(c);
          csAux(pi,cs,l.c(c));
          cs.remove(cs.size()-1);
          }
        }
      @Override public void visitL(Core.L l){
        TypeManipulation.skipThis0(l.info().typeDep().stream()).forEach(typePs::add);
        TypeManipulation.skipThis0(l.info().coherentDep().stream()).forEach(cohePs::add);
        ArrayList<C> cs=new ArrayList<>();
        Program pi=p0.push(l);
        for(C c: l.domNC()){//a little of code duplication removes the map on the streams
          cs.add(c);
          csAux(pi,cs,l.c(c));
          cs.remove(cs.size()-1);
          }
        }
        @Override public void visitP(P p){
          if(p.isNCs()){typePs.add(p.toNCs());}
          }
        @Override public void visitPCastT(Core.PCastT p){
          super.visitPCastT(p);
          if(p.t().p()==P.pAny){return;}
          if(p.p().isNCs()){cohePs.add(p.p().toNCs());}
          }
        };
    if(!justBodies){for(var m:mwts){deps.of(m);}return;}
    for(var m:mwts){if(m._e()!=null){deps.of(m._e().visitable());}}
    }
  //end class
  }
      //Ps0 = CORE.L.Info.typeDep
      //Ps'0 = CORE.L.Info.coherent
      //Ps1..Psn = {CORE.L(Cs).Info.typeDep[from This.Cs;p]| Cs in dom(CORE.L)}
      //Ps'1..Ps'n = {CORE.L(Cs).Info.coherentDep[from This.Cs;p]| Cs in dom(CORE.L)}
class HalfQuadruple{
  public final Half.E e;
  public final List<ST> resSTz=L();
  public final List<ST> retSTz=L();
  public HalfQuadruple(Y y, Full.E fe) { 
    this.e=(Half.E)fe;
    }
  }