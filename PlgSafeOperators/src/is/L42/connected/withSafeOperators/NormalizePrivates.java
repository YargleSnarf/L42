package is.L42.connected.withSafeOperators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import coreVisitors.CloneVisitor;
import coreVisitors.CloneWithPath;
import coreVisitors.Exists;
import tools.Assertions;
import tools.Map;
import ast.ExpCore;
import ast.ExpCore.ClassB.Member;
import ast.ExpCore.ClassB.MethodImplemented;
import ast.ExpCore.ClassB.MethodWithType;
import ast.Util;
import ast.ExpCore.*;
import ast.Util.*;
import auxiliaryGrammar.Locator;
import auxiliaryGrammar.Program;
import auxiliaryGrammar.Locator.Kind;
import ast.Ast.MethodSelector;
import ast.Ast.Path;
//this file may be moved in L42_Main
public class NormalizePrivates {
  public static int countPrivates;
  public static int countFamilies;
  public static String doubleUnderscoreReplacement;
  static{reset();}
  public static void reset(){
    countPrivates=0;
    countFamilies=0;
    doubleUnderscoreReplacement="$%";
    }
  public static void updateDoubleUnderscoreReplacement(String name){
    if(!name.contains(doubleUnderscoreReplacement)){return;}
    doubleUnderscoreReplacement+="$%";
    updateDoubleUnderscoreReplacement(name);
  }
  public static String freshName(String name){
   return freshName(name,"__"+countPrivates++ +"_"+countFamilies);
  }
  public static String freshName(String name,String newPedex){
    assert !name.contains("__");//should have been removed before
    //if(name.contains("__")){
    //  name=name.replace("__", "_"+NormalizePrivates.doubleUnderscoreReplacement);      }
    //just removing __ would be wrong, multiple methods would get different names, but multiple getters would be merged together
    
    return name+newPedex;
  }
  public static CollectedLocatorsMap collectPrivates(ClassB cb){
    CollectedLocatorsMap result=new CollectedLocatorsMap();
    cb.accept(new CloneWithPath(){
      @Override public ClassB.NestedClass visit(ClassB.NestedClass nc){
        String name=nc.getName();
        boolean hasValidUniquePexed=processNameAndReturnHasUnseenPedex(result.pedexes,name);
        if(!nc.getDoc().isPrivate()){return super.visit(nc);}
        if(!hasValidUniquePexed){result.normalized=false;}
        Locator nl=this.getLocator().copy();
        nl.pushMember(nc);
        result.nesteds.add(nl);
        return super.visit(nc);
        }
      @Override public ClassB.MethodWithType visit(ClassB.MethodWithType mwt){
        String name=mwt.getMs().getName();
        boolean hasValidUniquePexed=processNameAndReturnHasUnseenPedex(result.pedexes,name);
        if(!mwt.getDoc().isPrivate()){return super.visit(mwt);}
        if(!hasValidUniquePexed){result.normalized=false;}
        Locator ml=this.getLocator().copy();
        ml.pushMember(mwt);
        result.selectors.add(ml);
        return super.visit(mwt);
        }
      });
    return result;
    }
  
  public static boolean processNameAndReturnHasUnseenPedex(Set<String> collected,String name){
    int index=name.indexOf("__");
    if(index==-1){return false;}
    updateDoubleUnderscoreReplacement(name);
    
    String pedex=name.substring(index+2,name.length());
    boolean wasAdded=collected.add(pedex);
    return wasAdded;
  }
  private static String replace__(String s){
    String ss=s.replace("__", "_"+NormalizePrivates.doubleUnderscoreReplacement);
    if(s.equals(ss)){return ss;}
    return replace__(ss);
  }
  public static ClassB normalize(ClassB cb){
    CollectedLocatorsMap result = NormalizePrivates.collectPrivates(cb);
    if (result.normalized && result.pedexes.isEmpty()){return cb;}
    cb=replace__ifPresent(cb, result);
    if(!result.pedexes.isEmpty()){
      result=NormalizePrivates.collectPrivates(cb);//could be made faster, but not important here
      }
    result.computeNewNames();
    cb=NormalizePrivates.normalize(result, cb);
    return cb;
  
    }
  private static ClassB replace__ifPresent(ClassB cb, CollectedLocatorsMap result) {
    if(result.pedexes.isEmpty()){return cb;}
    return (ClassB)cb.accept(new CloneVisitor(){
        public ExpCore visit(Path s){
          if(s.isPrimitive()){return s;}
          List<String> cs=s.getCBar();
          List<String>newCs=new ArrayList<>();
          for(String si:cs){newCs.add(replace__(si));}
          if(newCs.equals(cs)){return s;}
          return Path.outer(s.outerNumber(),newCs);
        }
        public ast.Ast.MethodSelector liftMs(ast.Ast.MethodSelector ms){
          return super.liftMs(ms.withName(replace__(ms.getName())).withNames(
              Map.of(ni->replace__(ni),ms.getNames())));
        }
        public ClassB.NestedClass visit(ClassB.NestedClass nc){
          return super.visit(nc.withName(replace__(nc.getName())));
        }
      }) ;
  }
   
  static class RenameAlsoDefinition extends RenameUsage{
    public RenameAlsoDefinition(ClassB visitStart,CollectedLocatorsMap maps) { super(visitStart,maps);}

    public List<Member> liftMembers(List<Member> s) {
      List<Member>result=super.liftMembers(s);
      //remove clashes here
      return result;
      }
    //it may clash against something
    public MethodWithType visit(MethodWithType mwt){
      MethodWithType result=super.visit(mwt);
      Locator current=this.getLocator().copy();
      current.pushMember(mwt);
      for(Locator l:this.maps.selectors){
        if(!l.equals(current)){continue;}
        return result.withMs((MethodSelector) l.getAnnotation());
      }
      return result;
    }
    public ClassB.MethodImplemented visit(ClassB.MethodImplemented mi){
      return potentiallyRenameMethodImplementedHeader(super.visit(mi));
    }
    private MethodImplemented potentiallyRenameMethodImplementedHeader(MethodImplemented mi) {
      ClassB currentCb=this.getLocator().getLastCb();
      Program ep=Program.getExtendedProgram(Program.empty(), this.getLocator().getCbs());
      //List<Path> supers = Program.getAllSupertypes(ep, currentCb);
      InfoAboutMs info = Program.getMT(ep, mi.getS(),currentCb);
      assert !info.getAllSuper().isEmpty();
      Locator original=this.getLocator().copy();
      boolean isOut=original.moveInPath(info.getOriginal());
      if(isOut){return mi;}
      for(Locator pMx :maps.selectors){
        assert pMx.kind()==Kind.Method;
        MethodSelector s=pMx.getLastMember().match(
            nc->{throw Assertions.codeNotReachable();},
            mimpl->mimpl.getS(),
            mt->mt.getMs());
        if(!mi.getS().equals(s)){continue;}
        Locator renamed =pMx.copy();
        renamed.toFormerNodeLocator();
        if(!original.equals(renamed)){return mi;}
        MethodSelector ms2=(MethodSelector) pMx.getAnnotation();
        return mi.withS(ms2);
        }
      return mi;
    }
    
    public ClassB.NestedClass visit(ClassB.NestedClass nc){
      Locator current=this.getLocator().copy();
      current.pushMember(nc);
      for(Locator nl:maps.nesteds){
        if(!nl.equals(current)){continue;}
        assert nl.getAnnotation() instanceof String;
        return super.visit(nc.withName((String)nl.getAnnotation()));
      }
      return super.visit(nc);
    }
  }
  public static ClassB normalize(CollectedLocatorsMap privates,ClassB cb){
    cb=(ClassB)new RenameAlsoDefinition(cb,privates).visit(cb);
    return cb;
    //renameMethod still use old introspection
    //write a rename usage from data of collected privates for both paths and methods.
    //then write a simple rename declarations.
    //the rename declarations for methods, for renameMethod have to keep method sum in account.
    
    }
  public static ClassB importWithReuseNormalizedClass(int round,ClassB cb){return cb;}
}
