package is.L42.nativeCode;

import static is.L42.tools.General.bug;

import is.L42.common.EndError;
import is.L42.common.ErrMsg;
import is.L42.generated.Core;
import is.L42.generated.Core.L.MWT;
import is.L42.generated.Core.MH;
import is.L42.generated.Mdf;
import is.L42.generated.S;
import is.L42.translationToJava.J;

public class CacheLazyGenerator implements Generator{
  void typeCache(MWT mwt, J j){
    var p=mwt._e().poss();
    MH mh=mwt.mh();
    var url=mwt.nativeUrl();
    if(mh.key().m().startsWith("#$")){
      throw new EndError.TypeError(p,ErrMsg.nativeKindInvalidSelector(url,mh));
      }
    if(!mh.mdf().isIn(Mdf.Immutable,Mdf.Readable,Mdf.Class)){
      throw new EndError.TypeError(p,ErrMsg.nativeParameterInvalidKind(!url.isEmpty(),url,mh,
        "imm, class or read methods",mh.mdf(),"imm, class or read"));
      }
    if(!mh.pars().isEmpty()){
      throw new EndError.TypeError(p,ErrMsg.nativeParameterCountInvalid(url,mh,0));
      }
    var t=mwt.mh().t();
    if(!t.mdf().isIn(Mdf.Immutable,Mdf.Class,Mdf.Readable)){
      throw new EndError.TypeError(p,ErrMsg.nativeParameterInvalidKind(!url.isEmpty(),url,mh,
        "imm, class or read return type",t.mdf(),"imm, class or read"));
      }
    }
  public static String nameFromS(S s) {
    assert s.xs().isEmpty();
    String name="£k"+s.m().replace("#", "£h");
    //other used letters: x _ f c n h E
    if(s.hasUniqueNum()){name+="£n"+s.uniqueNum();}
    return name;
    }
  @Override public void of(boolean type, MWT mwt, J j) {
    if(type){typeCache(mwt,j); return;}
    assert mwt.key().xs().isEmpty();
    String retT=j.typeNameStr(mwt.mh().t().p());
    String thisT;
    if(mwt.mh().mdf().isClass()){thisT=J.classNameStr(j.p());}
    else{thisT=j.typeNameStr(j.p());}
    String name=nameFromS(mwt.key());
    if(mwt.mh().mdf().isClass()){classCache(j,name);}
    if(mwt.mh().mdf().isImm()){immCache(j,name);}
    if(mwt.mh().mdf().isRead()){readCache(j,name);}
    fieldAndAuxMethod(mwt.mh().mdf().isClass(),j,name,retT,thisT,mwt._e());
    if(!mwt.mh().mdf().isIn(Mdf.Readable,Mdf.Immutable,Mdf.Class)){throw bug();}
    }
  void immCache(J j,String name){
    if(j.fields.xs.isEmpty()){readCache(j,name);return;}
    j.c("if(£xthis.norm==null){L42CacheMap.normalizeCachable(£xthis);}");j.nl();
    j.c("return £xthis.norm."+name+".join();");j.nl();j.deIndent();    
    j.c("}");j.nl();
    }
  void readCache(J j,String name){
    j.c("return £xthis."+name+".join();");j.nl();j.deIndent();    
    j.c("}");j.nl();
    }
  void classCache(J j,String name){
    j.c("return "+name+".join();");j.nl();j.deIndent();    
    j.c("}");j.nl();
    }
  void fieldAndAuxMethod(boolean isStatic,J j,String name,String retT,String thisT,Core.E e){
    String theThis="pathInstance";
    if(isStatic) {j.c("static ");}
    else {theThis=thisT+".this";}
    j.c("CachedRes<"+J.boxed(retT)+"> "+name+"=new CachedRes<"+J.boxed(retT)+">(){public "
      +J.boxed(retT)+" op(){return "+name+"("+theThis+");"+"}};");j.nl();
      //TODO: not for the imm only on the normalized one
    j.c("private static "+retT+" "+name+"("+thisT+" £xthis){");j.indent();j.nl();
    j.c("return ");
    j.visitE(e);
    j.c(";");    
    }
  }