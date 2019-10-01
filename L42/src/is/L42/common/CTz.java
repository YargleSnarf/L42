package is.L42.common;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static is.L42.tools.General.L;
import static is.L42.tools.General.bug;
import static is.L42.tools.General.popL;
import static is.L42.tools.General.range;
import static is.L42.tools.General.toOneOr;

import java.util.ArrayList;
import java.util.HashMap;

import is.L42.generated.Core.L.MWT;
import is.L42.generated.Core.T;
import is.L42.generated.P;
import is.L42.generated.ST;


public class CTz {
  private final Map<ST,ArrayList<ST>> inner=new HashMap<>();
  @Override public String toString(){return inner.toString();}
  public boolean coherent(){
    for(var e:inner.entrySet()){
      ST st=e.getKey();
      List<ST> stz=e.getValue();
      assert stz.contains(st);
      for(ST st1:stz){
        assert stz.containsAll(inner.get(st1));
        }
      }
      return true;
    }
  public void plusAcc(Program p,ArrayList<ST> stz,ArrayList<ST>stz1){
    assert coherent();
    while(!stz.isEmpty()){
      var st=stz.get(0);
      stz.remove(0);
      plusAcc(p,st,stz1);
      minimize(p,stz);
      minimize(p,stz1);
      }
    assert coherent();
    }
  void plusAcc(Program p,ST st,List<ST>stz){
    for(var stzi:inner.values()){
       if(!stzi.contains(st)){continue;}
       for(ST stj: stz){if(!stzi.contains(stj)){stzi.add(stj);}}
       minimize(p,stzi);
       }
     //WRONG here, we never add the st->stz..  also.. do we need to grow the stz? check formalism
    }
  public Set<ST> dom(){return inner.keySet();}
  public List<ST> of(ST st){return L(inner.get(st).stream());}
  public List<ST> of(List<ST>stz){return L(stz,(c,sti)->c.addAll(of(sti)));}
  void minimize(Program p,ArrayList<ST>stz){
    ArrayList<T>tz=new ArrayList<>();
    for(int i=0;i<stz.size();){
      ST st=minimize(p,stz.get(i));
      if(st instanceof T){tz.add((T)st);stz.remove(i);}
      else if(stz.contains(st)){stz.remove(i);}
      else{stz.set(i,st);i+=1;}
      }
    while(!tz.isEmpty()){
      T t=tz.get(tz.size()-1);
      tz.remove(tz.size()-1);
      boolean noSub=tz.stream().noneMatch(ti->p.isSubtype(ti, t,null));
      if(noSub){stz.add(t);}
      }
    }
  ST minimize(Program p,ST st){
    if(st instanceof T){return st;}
    if(st instanceof ST.STMeth){return minimize(p,(ST.STMeth)st);}
    if(st instanceof ST.STOp){return minimize(p,(ST.STOp)st);}
    throw bug();
    }
  ST minimize(Program p,ST.STMeth st){
    List<T> ts;
    if(st.i()==-1){
      ts=L(inner.get(st.st()),(c,sti)->{
        if (!(sti instanceof T)){return;}
        T ti=(T)sti;
        if(!ti.p().isNCs()){return;}
        P.NCs pi=ti.p().toNCs();
        Optional<MWT> mwti=p.ofCore(pi).mwts().stream()
          .filter(m->m.key().equals(st.s()))
          .reduce(toOneOr(()->bug()));
        if(mwti.isEmpty()){return;}
        T t1=p.from(mwti.get().mh().t(),pi);
        boolean tI=p.ofCore(pi).isInterface();
        boolean t1I=p.ofCore(t1.p()).isInterface();
        boolean tEqSt=ti.equals(st.st());
        if(tI || t1I || tEqSt){c.add(t1);}
        });
      }
    else{
      ts=L(inner.get(st.st()),(c,sti)->{
        if (!(sti instanceof T)){return;}
        T ti=(T)sti;
        if(!ti.p().isNCs()){return;}
        P.NCs pi=ti.p().toNCs();
        Optional<MWT> mwti=p.ofCore(pi).mwts().stream()
          .filter(m->m.key().equals(st.s()))
          .reduce(toOneOr(()->bug()));
        if(mwti.isEmpty()){return;}
        assert st.i()!=0;
        //T t1=p.from(mwti.mh().parsWithThis().get(st.i()),pi);//if assertion above is false
        T t1=p.from(mwti.get().mh().pars().get(st.i()-1),pi);
        c.add(t1);
        });
    }
    if(ts.isEmpty()){return st;}
    assert ts.stream().distinct().count()==1;
    return ts.get(0);
    }
  ST minimize(Program p,ST.STOp st){ return st;}//TODO:
  /*
* p.minimize(CTz; OP STz1 ... STzn) = p(P)(s).T[from P; p]
    Ti in CTz(STzi)
    {P.s.i} = p.opOptions(OP, Ti..Tn)
* p.minimize(CTz; OP STz1 ... STzn) = OP p.minimize(CTz; STz1) ... p.minimize(CTz; STzn)
    otherwise
  
  */
    /*public static class Psi{P p; S s; int i;}
  public List<Psi> opOptions(Op op, List<T>ts){
    return L(c->{for(int i:range(ts)){
      P pi=ts.get(i).p();
      //this.methods(pi)
      }});
    }*/
  }
