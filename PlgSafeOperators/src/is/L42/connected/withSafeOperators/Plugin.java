package is.L42.connected.withSafeOperators;

import static auxiliaryGrammar.EncodingHelper.ensureExtractClassB;
import static auxiliaryGrammar.EncodingHelper.ensureExtractStringU;
import static auxiliaryGrammar.EncodingHelper.extractInt32;
import static auxiliaryGrammar.EncodingHelper.ensureExtractPathFromJava;
import static auxiliaryGrammar.EncodingHelper.ensureExtractDoc;

import java.util.List;

import static auxiliaryGrammar.EncodingHelper.ensureExtractInt32;
import static auxiliaryGrammar.EncodingHelper.ensureExtractInternalPath;

import ast.Ast;
import ast.Ast.Doc;
import ast.ExpCore;
import ast.Ast.MethodSelector;
import ast.Ast.Path;
import ast.ExpCore.ClassB;
import auxiliaryGrammar.Norm;
import auxiliaryGrammar.Program;
import coreVisitors.CloneVisitorWithProgram;
import platformSpecific.fakeInternet.ActionType;
import platformSpecific.fakeInternet.PluginType;
import platformSpecific.javaTranslation.Resources;
import platformSpecific.javaTranslation.Resources.Revertable;
import sugarVisitors.ToFormattedText;

//empty scheleton
public class Plugin implements PluginType{

    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object Mcompose�xleft�xright(Object _left,Object _right){
      ClassB left=ensureExtractClassB(_left);
      ClassB right=ensureExtractClassB(_right);
      return Sum.sum(Resources.getP(),left,right);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MrenameClass�xthat�xsrc�xdest(Object _that,Object _src,Object _dest){
      ClassB that=ensureExtractClassB(_that);
      List<String> src=Path.parseValidCs(ensureExtractStringU(_src));
      List<String> dest=Path.parseValidCs(ensureExtractStringU(_dest));
      return Rename.renameClass(Resources.getP(),that,src,dest);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MrenameMethod�xthat�xpath�xsrc�xdest(Object _that,Object _path,Object _src,Object _dest){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
       MethodSelector src = MethodSelector.parse(ensureExtractStringU(_src));
       MethodSelector dest = MethodSelector.parse(ensureExtractStringU(_dest));
      return Rename.renameMethod(Resources.getP(),that,path,src,dest);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.TypeAny})
    public Object Mredirect�xthat�xsrc�xdest(Object _that,Object _src,Object _dest){
      ClassB that=ensureExtractClassB(_that);
      List<String> src=Path.parseValidCs(ensureExtractStringU(_src));
      Path dest=ensureExtractPathFromJava(_dest);
      assert dest.isCore() || dest.isPrimitive();
      if(dest.isCore()){dest=dest.setNewOuter(dest.outerNumber()+1);}//TODO: see if extractPath should be changed
      return Redirect.redirect(Resources.getP(),that,Path.outer(0,src),dest);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MremoveImplementation�xthat�xpath(Object _that,Object _path){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      return Abstract.toAbstract(that,path);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MremoveImplementation�xthat�xpath�xselector(Object _that,Object _path,Object _sel){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      MethodSelector sel = MethodSelector.parse(ensureExtractStringU(_sel));
      return Abstract.toAbstract(that,path,sel);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MaddDocumentation�xthat�xpath�xdoc(Object _that,Object _path,Object _doc){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      Doc doc=ensureExtractDoc(_doc);
      return AddDocumentation.addDocumentationOnNestedClass(that,path,doc);
      }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MaddDocumentation�xthat�xpath�xselector�xdoc(Object _that,Object _path,Object _sel,Object _doc){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      MethodSelector sel = MethodSelector.parse(ensureExtractStringU(_sel));
      Doc doc=ensureExtractDoc(_doc);
      return AddDocumentation.addDocumentationOnMethod(that,path,sel,doc);
      }
    
    @ActionType({ActionType.Type.Library,ActionType.Type.Library})
    public Object MremoveUnreachableCode�xthat(Object _that){
      ClassB that=ensureExtractClassB(_that);
      return RemoveCode.removeUnreachableCode(that);
    }
    @ActionType({ActionType.Type.Void,ActionType.Type.Library})
    public  Resources.Void MifInvalidDo�xselector(Object _selector){
      String s=ensureExtractStringU(_selector);
     try{Ast.MethodSelector.parse(s);}
     catch(Resources.Error err){return Resources.Void.instance;}
      throw Resources.notAct;
    }
    @ActionType({ActionType.Type.Void,ActionType.Type.Library})
    public  Resources.Void MifInvalidDo�xpath(Object _path){
      String s=ensureExtractStringU(_path);
     try{Ast.Path.parseValidCs(s);}
     catch(Resources.Error err){return Resources.Void.instance;}
      throw Resources.notAct;
    }
    
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------    
    //-----5 +5 introspections //lib
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectLibraryReport�xthat�xpath(Object _that,Object _path){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      return Introspection.giveInfo(that, path);
    }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectLibraryReportMember�xthat�xpath�xmemberN(Object _that,Object _path,Object _memberN){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int memberN=ensureExtractInt32(_memberN);
      return Introspection.giveInfoMember(that, path,memberN);
    }
    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectLibraryReportType�xthat�xpath�xmemberN�xtypeN(Object _that,Object _path,Object _memberN,Object _typeN){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int memberN=ensureExtractInt32(_memberN);
      int typeN=ensureExtractInt32(_typeN);
      return Introspection.giveInfoType(Resources.getP(), that, path, memberN, typeN);
    }

    @ActionType({ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectLibraryDocAsString�xthat�xpath�xannotationN(Object _that,Object _path,Object _annotationN){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int annotationN=ensureExtractInt32(_annotationN);
      return Introspection.extractDocAsString(that, path, annotationN);
    }
    @ActionType({ActionType.Type.TypeAny,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectLibraryDocPath�xthat�xpath�xannotationN(Object _that,Object _path,Object _annotationN){
      ClassB that=ensureExtractClassB(_that);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int annotationN=ensureExtractInt32(_annotationN);
      return Introspection.extractDocPath(that, path, annotationN);
    }
    
    //-----5 +5 introspections //type
    @ActionType({ActionType.Type.Library,ActionType.Type.TypeAny,ActionType.Type.Library})
    public Object MintrospectTypeReport�xthat�xpath(Object _that,Object _path){
      Path iPath=(_that instanceof Path)?(Path)_that:(Path)((Revertable)_that).revert();
      ClassB that=Resources.getP().extractCb(iPath);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      return Introspection.giveInfo(that, path);
    }
    @ActionType({ActionType.Type.Library,ActionType.Type.TypeAny,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectTypeReportMember�xthat�xpath�xmemberN(Object _that,Object _path,Object _memberN){
      Path iPath=(_that instanceof Path)?(Path)_that:(Path)((Revertable)_that).revert();
      ClassB that=Resources.getP().extractCb(iPath);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int memberN=ensureExtractInt32(_memberN);
      return Introspection.giveInfoMember(that, path,memberN);
    }
    @ActionType({ActionType.Type.Library,ActionType.Type.TypeAny,ActionType.Type.Library,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectTypeReportType�xthat�xpath�xmemberN�xtypeN(Object _that,Object _path,Object _memberN,Object _typeN){
      Path iPath=(_that instanceof Path)?(Path)_that:(Path)((Revertable)_that).revert();
      ClassB that=Resources.getP().extractCb(iPath);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int memberN=ensureExtractInt32(_memberN);
      int typeN=ensureExtractInt32(_typeN);
      return Introspection.giveInfoType(Resources.getP(), that, path, memberN, typeN);
    }

    @ActionType({ActionType.Type.Library,ActionType.Type.TypeAny,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectTypeDocAsString�xthat�xpath�xannotationN(Object _that,Object _path,Object _annotationN){
      Path iPath=(_that instanceof Path)?(Path)_that:(Path)((Revertable)_that).revert();
      ClassB that=Resources.getP().extractCb(iPath);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int annotationN=ensureExtractInt32(_annotationN);
      return Introspection.extractDocAsString(that, path, annotationN);
    }
    @ActionType({ActionType.Type.TypeAny,ActionType.Type.TypeAny,ActionType.Type.Library,ActionType.Type.Library})
    public Object MintrospectTypeDocPath�xthat�xpath�xannotationN(Object _that,Object _path,Object _annotationN){
      Path iPath=(_that instanceof Path)?(Path)_that:(Path)((Revertable)_that).revert();
      ClassB that=Resources.getP().extractCb(iPath);
      List<String> path=Path.parseValidCs(ensureExtractStringU(_path));
      int annotationN=ensureExtractInt32(_annotationN);
      return Introspection.extractDocPath(that, path, annotationN);
    }
}
