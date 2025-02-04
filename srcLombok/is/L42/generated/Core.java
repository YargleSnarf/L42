package is.L42.generated;
import lombok.Value;
import lombok.EqualsAndHashCode;
import lombok.experimental.Wither;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.CollectorVisitor;
import is.L42.visitors.Visitable;
import is.L42.visitors.InjectionToCore;
import is.L42.common.Constants;
import is.L42.common.Parse;
import is.L42.flyweight.*;
import is.L42.perftests.PerfCounters;

import static is.L42.tools.General.*;

public class Core {
  public static interface E extends HasPos,HasWf,HasVisitable{
    Visitable<? extends E> visitable();
    public static E parse(String s){
      var r=Parse.e(Constants.dummy,s);
      assert !r.hasErr():r;
      assert r.res.wf();
      var errors=new StringBuilder();
      E res= new InjectionToCore(errors,new Core.EVoid(new Pos(Constants.temp.toUri(),0,0)))._inject(r.res);
      assert errors.length() == 0:errors;
      assert res!=null;
      return res;
      }
    }
  public static interface Leaf extends E{}
  public static interface Wrapper extends E{ E e();}
  public static interface XP extends E{Visitable<? extends XP> visitable();}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  EX implements Leaf,Full.E,Full.Leaf, XP, Half.XP,Visitable<EX>{@Override public Visitable<EX>visitable(){return this;}@Override public EX accept(CloneVisitor cv){return cv.visitEX(this);}@Override public void accept(CollectorVisitor cv){cv.visitEX(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    X x;}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  PCastT implements Leaf, XP, Visitable<PCastT>{@Override public Visitable<PCastT>visitable(){return this;}@Override public PCastT accept(CloneVisitor cv){return cv.visitPCastT(this);}@Override public void accept(CollectorVisitor cv){cv.visitPCastT(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    P p;
    T t;}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  EVoid implements Leaf,Full.Leaf,Half.Leaf,Visitable<EVoid>{@Override public Visitable<EVoid>visitable(){return this;}@Override public EVoid accept(CloneVisitor cv){return cv.visitEVoid(this);}@Override public void accept(CollectorVisitor cv){cv.visitEVoid(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    }
  @EqualsAndHashCode(exclude={"poss"})@Value @Wither public static class
  MWT implements LDom.HasKey, Visitable<MWT>{@Override public MWT accept(CloneVisitor cv){return cv.visitMWT(this);}@Override public void accept(CollectorVisitor cv){cv.visitMWT(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    List<Pos> poss;
    List<Doc>docs; 
    MH mh; 
    String nativeUrl;
    E _e;
    @Override public S key(){return mh.s();}}
  @EqualsAndHashCode(exclude={"poss"})@Value @Wither public static class
  NC implements LDom.HasKey, Visitable<NC>{@Override public NC accept(CloneVisitor cv){return cv.visitNC(this);}@Override public void accept(CollectorVisitor cv){cv.visitNC(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    List<Pos> poss;
    List<Doc>docs;
    C key;
    CoreL l;
    }
  @Value @Wither public static class
  Info implements Visitable<Info>{@Override public Info accept(CloneVisitor cv){return cv.visitInfo(this);}@Override public void accept(CollectorVisitor cv){cv.visitInfo(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    boolean isTyped; 
    List<P.NCs> typeDep;
    List<P.NCs> coherentDep;
    List<P.NCs> metaCoherentDep;
    List<P.NCs> watched;
    List<PathSel> usedMethods;
    List<P.NCs> hiddenSupertypes;
    List<S>refined;
    boolean close;
    String nativeKind;
    List<P> nativePar;
    int _uniqueId;
    public static final Info empty=new Info(false,L(),L(),L(),L(),L(),L(),L(),false,"",L(),-1);
    public Info sumInfo(Info info2) {
      Info info1=this;
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
    }
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  MCall implements E,Visitable<MCall>{@Override public Visitable<MCall>visitable(){return this;}@Override public MCall accept(CloneVisitor cv){return cv.visitMCall(this);}@Override public void accept(CollectorVisitor cv){cv.visitMCall(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    XP xP;
    S s;
    List<E> es;
    public MCall(Pos pos,XP xP,S s,List<E> es){this.pos=pos;this.xP=xP;this.s=s;this.es=es;
      assert s.xs().size()==es.size();
      }}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  Block implements E,Visitable<Block>{@Override public Visitable<Block>visitable(){return this;}@Override public Block accept(CloneVisitor cv){return cv.visitBlock(this);}@Override public void accept(CollectorVisitor cv){cv.visitBlock(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    List<D> ds;
    List<K>ks;
    E e;}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  Loop implements Wrapper,Visitable<Loop>{@Override public Visitable<Loop>visitable(){return this;}@Override public Loop accept(CloneVisitor cv){return cv.visitLoop(this);}@Override public void accept(CollectorVisitor cv){cv.visitLoop(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    E e;}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  Throw implements Wrapper,Visitable<Throw>{@Override public Visitable<Throw>visitable(){return this;}@Override public Throw accept(CloneVisitor cv){return cv.visitThrow(this);}@Override public void accept(CollectorVisitor cv){cv.visitThrow(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    ThrowKind thr;
    E e;}
  @EqualsAndHashCode(exclude={"pos"})@Value @Wither public static class
  OpUpdate implements Wrapper,Visitable<OpUpdate>{@Override public Visitable<OpUpdate>visitable(){return this;}@Override public OpUpdate accept(CloneVisitor cv){return cv.visitOpUpdate(this);}@Override public void accept(CollectorVisitor cv){cv.visitOpUpdate(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}Pos pos;
    X x;
    E e;}
  //---
  @Value @Wither public static class
  D implements Visitable<D>{@Override public D accept(CloneVisitor cv){return cv.visitD(this);}@Override public void accept(CollectorVisitor cv){cv.visitD(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    boolean isVar;
    T t; 
    X x; 
    E e;}
  @Value @Wither public static class
  K implements Visitable<K>{@Override public K accept(CloneVisitor cv){return cv.visitK(this);}@Override public void accept(CollectorVisitor cv){cv.visitK(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    ThrowKind thr; 
    T t; 
    X x; 
    E e;}    
  @Value @Wither public static class
  T implements ST,Visitable<T>{@Override public Visitable<T>visitable(){return this;}@Override public T accept(CloneVisitor cv){return cv.visitT(this);}@Override public void accept(CollectorVisitor cv){cv.visitT(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    Mdf mdf; 
    List<Doc> docs; 
    P p;}
  @Value @Wither public static class
  Doc implements Visitable<Doc>{@Override public Doc accept(CloneVisitor cv){return cv.visitDoc(this);}@Override public void accept(CollectorVisitor cv){cv.visitDoc(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    PathSel _pathSel; 
    List<String>texts; 
    List<Doc> docs;}
  @Value @Wither public static class
  PathSel implements Visitable<PathSel>{@Override public PathSel accept(CloneVisitor cv){return cv.visitPathSel(this);}@Override public void accept(CollectorVisitor cv){cv.visitPathSel(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    P p; 
    S _s; 
    X _x;}
  @Value @Wither public static class
  MH implements LDom.HasKey, Visitable<MH>{@Override public MH accept(CloneVisitor cv){return cv.visitMH(this);}@Override public void accept(CollectorVisitor cv){cv.visitMH(this);}@Override public String toString(){return Constants.toS.apply(this);}@Override public boolean wf(){return Constants.wf.test(this);}
    Mdf mdf; 
    List<Doc> docs; 
    T t; 
    S s; 
    List<T> pars; 
    List<T> exceptions;
    public MH(Mdf mdf,List<Doc> docs,T t,S s,List<T> pars,List<T> exceptions){
      this.mdf=mdf;this.docs=docs;this.t=t;this.s=s;this.pars=pars;this.exceptions=exceptions;
      assert s.xs().size()==pars.size();
      }
    @Override public S key(){return s;}
    public List<T> parsWithThis(){
      return pushL(P.coreThis0.withMdf(mdf).withDocs(docs), pars);
      }
    }
  }