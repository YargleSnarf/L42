package is.L42.connected.withSafeOperators.location;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ast.Ast;
import ast.Ast.MethodType;
import ast.Ast.NormType;
import ast.Ast.Path;
import ast.ExpCore.ClassB;
import ast.ExpCore.ClassB.MethodWithType;
import is.L42.connected.withSafeOperators.pluginWrapper.RefactorErrors.NotAvailable;

public class Method extends Location.LocationImpl<ClassB.MethodWithType, Lib>{
  public Method(MethodWithType inner, Lib location) {
    super(inner, location);
    }
  boolean isAbstract(){return !inner.get_inner().isPresent();}
  boolean isRefine(){return inner.getMt().isRefine();}
  String  selector(){return inner.getMs().toString();}
  @Override public String toS() {return sugarVisitors.ToFormattedText.of(this.inner);}
  @Override public Doc doc() {return new Doc(inner.getDoc(),this);}

  Type.Return returnType(){return new Type.Return(inner.getMt().getReturnType().getNT(),inner,this);}
    
  Cacher<List<Type.Parameter>> parametersC=new Cacher<List<Type.Parameter>>(){public List<Type.Parameter> cache(){
    MethodType mt = inner.getMt();
    Ast.NormType thisT=new NormType(mt.getMdf(),Path.outer(0),Ast.Doc.empty());
    List<Type.Parameter> res=new ArrayList<>();
    res.add(new Type.Parameter(0,thisT,inner,Method.this));
    {int i=0; for(Ast.Type ti:mt.getTs()){i+=1; //starts from 1
      res.add(new Type.Parameter(i,ti.getNT(),inner,Method.this));
    }}
    return res;
    }};
 
  public int parameterTypesSize(){return parametersC.get().size();}
  public Type.Parameter parameterType(int that) throws NotAvailable{return Location.listAccess(parametersC.get(), that);}
  

  Cacher<List<Type.Exception>> exceptionsC=new Cacher<List<Type.Exception>>(){public List<Type.Exception> cache(){
  MethodType mt = inner.getMt();
  List<Type.Exception> res=new ArrayList<>();
  {int i=-1; for(Ast.Type ti:mt.getTs()){i+=1; //starts from 0
    res.add(new Type.Exception(i,ti.getNT(),inner,Method.this));
  }}
  return res;
  }};
  public int exceptionsSize(){return parametersC.get().size();}
  public Type.Exception exception(int that) throws NotAvailable{return Location.listAccess(exceptionsC.get(), that);}
  
  }
