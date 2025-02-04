package is.L42.introspection;

import is.L42.cache.L42Cache;
import is.L42.common.Program;
import is.L42.flyweight.P;
import is.L42.generated.Core.T;
import is.L42.meta.L42£Meta;
import is.L42.meta.L42£Name;
import is.L42.nativeCode.TrustedKind;
import is.L42.platformSpecific.javaTranslation.L42Any;
import is.L42.platformSpecific.javaTranslation.L42NoFields;
import is.L42.platformSpecific.javaTranslation.Resources;

public class L42£Type extends L42NoFields.Eq<L42£Type>{
  static public L42£Type fromClass(String mdf,L42£Doc doc,L42Any clazz){
    return fromClass(mdf,doc,L42£Meta.unwrapPath(clazz));
    }
  static public L42£Type fromClass(String mdf,L42£Doc doc,P clazz){
    var refTo=L42£Nested.fromClass(clazz);
    return new L42£Type(mdf,doc,refTo);
    }
  static public L42£Type fromType(T t,L42£Nested root, L42£Name nameFromRoot){
    var resDoc=L42£Doc.fromDoc(root,nameFromRoot,L42£Doc.normalize(t.docs()));
    if(!t.p().isNCs()){return L42£Type.fromClass(t.mdf().inner,resDoc, t.p());}
    P.NCs pi=t.p().toNCs();
    if(root.isBinded()){
      if(!root._classAny.isNCs()){return L42£Type.fromClass(t.mdf().inner,resDoc, t.p());}
      pi=Resources.currentP.from(pi,root._classAny.toNCs());
      return L42£Type.fromClass(t.mdf().inner,resDoc, pi);
      }
    P.NCs pj=Program.emptyP.from(pi,nameFromRoot.cs);
    if(pj.n()!=0){
      pj=pj.withN(pj.n()-1);
      return L42£Type.fromClass(t.mdf().inner,resDoc, pj);
      }
    return L42£Type.fromLibrary(t.mdf().inner,resDoc,L42£Name.fromCs(pj.cs()));
    }
  static public L42£Type fromLibrary(String mdf,L42£Doc doc,L42£Name name){
    return new L42£Type(mdf,doc,doc.root.nestedByName(name)).myNorm();
    }
  public String mdf(){return mdf;}
  public L42£Doc doc(){return doc;}
  public L42£Nested nested(){return nested;}
  final String mdf;
  final L42£Doc doc;//also contains rootL and nameFromRoot
  final L42£Nested nested;
  private L42£Type(String mdf,L42£Doc doc,L42£Nested nested){
    this.mdf=mdf;
    this.doc=doc;
    this.nested=nested;
    } 
  @Override public String toString(){
    var cs="";
    if(!nested.isBinded()){cs=""+nested.nameFromRoot;}
    else {
      var c=nested._classAny;
      if(!c.isNCs()){cs=""+nested._classAny;}
      else{cs=""+c.toNCs().withN(c.toNCs().n()+1);}
      }
    var docS=doc.toString();
    if(!docS.isEmpty()) {docS+=" ";}
    return mdf+" "+docS+cs;
    }
  @Override public boolean eq(L42£Type that){
    if(!mdf.equals(that.mdf)){return false;}
    if(doc.eq(that.doc)){return false;}
    return nested.eq(that.nested);
    }
  public static final Class<L42£Nested> _class=L42£Nested.class;
  public static final L42Cache<L42£Type> myCache=new EqCache<>(TrustedKind.Type);
  @Override public L42Cache<L42£Type> myCache(){return myCache;}
  static public L42£Type instance=new L42£Type("imm",L42£Doc.instance,L42£Nested.instanceVoid).myNorm();
  @Override public L42£Type newInstance(){return instance;}
  }