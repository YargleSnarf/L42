package is.L42.nativeCode;

import static is.L42.tools.General.bug;
import static is.L42.tools.General.range;
import static is.L42.tools.General.todo;

import java.util.List;

import is.L42.common.EndError;
import is.L42.common.Err;
import is.L42.common.Program;
import is.L42.generated.Core.L.MWT;
import is.L42.generated.Core.MH;
import is.L42.generated.Mdf;
import is.L42.generated.P;
import is.L42.generated.Pos;
import is.L42.tools.General;
import is.L42.translationToJava.J;

public enum TrustedKind implements TrustedT{
  AnyKind(""){public String factory(J j,MWT mwt){throw bug();}
    @Override public boolean typePluginK(Program p,MH mh){throw bug();}
    public String defaultVal(){throw bug();}
    },
  AnyNativeKind(""){public String factory(J j,MWT mwt){throw bug();}
    @Override public boolean typePluginK(Program p,MH mh){throw bug();}
    public String defaultVal(){throw bug();}
    },
  Bool("boolean"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return false;";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    public String defaultVal(){return "false";}
    },
  Int("int"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return 0;";
    }
    public String defaultVal(){return "0";}
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  String("String"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return \"\";";
    }
    @Override public int genExceptionNumber(){return 1;}
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  StringBuilder("StringBuilder"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return new StringBuilder();";
    }
    @Override public boolean typePluginK(Program p,MH mh){return mutTypePluginK(p,mh);}
    },
  TrustedIO("L42£TrustedIO"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£TrustedIO.instance;";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  BigRational("L42£BigRational"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£BigRational.ZERO;";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Meta("L42£Meta"){
    public String factory(J j,MWT mwt){
      assert mwt.key().xs().isEmpty();
      return "return new L42£Meta();";
      }
    @Override public int genExceptionNumber(){return 5;}
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  LazyMessage("L42£LazyMsg"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return new L42£LazyMsg();";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  NonDeterministicError("L42£NonDeterministicError"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return new L42£NonDeterministicError();";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Vector("ArrayList"){@Override public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    assert j.p().topCore().info().nativePar().size()==4;
    return OpUtils.makeVector(j,"0");      
    }
    @Override public int genericNumber(){return 1;}
    @Override public int genExceptionNumber(){return 3;}
    @Override public boolean typePluginK(Program p,MH mh){return mutTypePluginK(p,mh);}
    },
  Opt("Opt"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    assert j.p().topCore().info().nativePar().size()==2;
    return "return null;";
    }
    @Override public boolean typePluginK(Program p,MH mh){return mutTypePluginK(p,mh);}
    @Override public int genericNumber(){return 1;}
    @Override public int genExceptionNumber(){return 1;}
    @Override public String typeNameStr(Program p,J j){
      var info=p.topCore().info();
      P gen1=info.nativePar().get(0);
      if(!gen1.isNCs()){return J.primitivePToString(gen1);}
      return j.typeNameStr(p.navigate(gen1.toNCs()));
      }
    },
  Name("L42£Name"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£Name.parse(\"This\");";
    }
    @Override public int genExceptionNumber(){return 1;}
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Nested("L42£Nested"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£Nested.instanceVoid;";
    }
    @Override public int genExceptionNumber(){return 3;}//InvalidName, OutOfBound,OptNotPresent
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);} 
    },
  Type("L42£Type"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£Type.instance;";
    }
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Doc("L42£Doc"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£Doc.instance;";
    }
    @Override public int genExceptionNumber(){return 2;}//OutOfBound,OptNotPresent
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Method("L42£Method"){public String factory(J j,MWT mwt){
    assert mwt.key().xs().isEmpty();
    return "return L42£Method.instance;";
    }
    @Override public int genExceptionNumber(){return 1;}//OutOfBound
    @Override public boolean typePluginK(Program p,MH mh){return immTypePluginK(p,mh);}
    },
  Limit("Void"){public String factory(J j,MWT mwt){
    assert false;
    throw bug();
    }
    @Override public boolean typePluginK(Program p,MH mh){throw bug();}
    };
  public final String inner;
  TrustedKind(String inner){this.inner = inner;}
  public int genericNumber(){return 0;}
  public int genExceptionNumber(){return 0;}
  public abstract String factory(J j,MWT mwt);
  public abstract boolean typePluginK(Program p,MH mh);
  public String defaultVal(){return "null";}
  public static TrustedKind _fromString(String s) {
    if(s.isEmpty()){return AnyKind;}
    for (TrustedKind t : TrustedKind.values()) {
      if (t.name().equals(s))return t;
      }
    return null;
    }
  public String typeNameStr(Program p,J j){
    String res=inner;
    var info=p.topCore().info();
    if(this.genericNumber()==0){return res;}
    res+="<";
    for(var i:range(this.genericNumber())){
      P pi=info.nativePar().get(i);
      if(!pi.isNCs()){res+=J.primitivePToString(pi);}
      else{res+=J.boxed(j.typeNameStr(p.navigate(pi.toNCs())));}
      res+=", ";
      }
    res=res.substring(0,res.length()-2)+">";
    return res;
    }
  private static boolean immTypePluginK(Program p,MH mh){return mdfTypePluginK(p,mh,Mdf.Immutable);}
  private static boolean mutTypePluginK(Program p,MH mh){return mdfTypePluginK(p,mh,Mdf.Mutable);}
  private static boolean mdfTypePluginK(Program p,MH mh,Mdf mdf){
    return mh.key().xs().isEmpty() && mdf==mh.t().mdf();
    /*if(!mh.key().xs().isEmpty()){
      throw new EndError.TypeError(poss,
        Err.nativeParameterCountInvalid(p.topCore().info().nativeKind(),mh,0));
      }
    if(mh.t().mdf()==mdf){return;}
    throw new EndError.TypeError(poss,
      Err.nativeParameterInvalidKind(p.topCore().info().nativeKind(),mh,mdf,mh.t(),mdf));
    */}
  }