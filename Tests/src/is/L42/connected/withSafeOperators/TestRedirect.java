package is.L42.connected.withSafeOperators;

import static helpers.TestHelper.getClassB;
import helpers.TestHelper.ErrorCarry;
import static org.junit.Assert.fail;
import helpers.TestHelper;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;

import platformSpecific.javaTranslation.Resources;
import ast.Ast.MethodSelector;
import ast.Ast.Path;
import ast.ExpCore.ClassB;
import auxiliaryGrammar.Program;

public class TestRedirect{
@RunWith(Parameterized.class)
public static class TestRedirect1 {//add more test for error cases
  @Parameter(0) public String[] _p;
  @Parameter(1) public String _cb1;
  @Parameter(2) public String _path1;
  @Parameter(3) public String _path2;
  @Parameter(4) public String _expected;
  @Parameter(5) public boolean isError;
  @Parameterized.Parameters
  public static List<Object[]> createData() {
    ErrorCarry ec = new ErrorCarry();

    return Arrays.asList(new Object[][] {
    {new String[]{"{A:{}}"},
      "{InnerA:{} B:{ method InnerA m(InnerA a) a}}","Outer0::InnerA","Outer1::A","{B:{ method Outer2::A m(Outer2::A a) a}}",false
    },{new String[]{"{A:{}}"},
        "{InnerA:{}  method InnerA m(InnerA a) a}","Outer0::InnerA","Outer1::A","{ method Outer1::A m(Outer1::A a) a}",false
    },{new String[]{"{A2:{  }}","{A1:{  }}"}, // redirecting into one of multiple outer scopes
        "{InnerA:{}  method InnerA m(InnerA a) a}","Outer0::InnerA","Outer1::A1","{ method Outer1::A1 m(Outer1::A1 a) a}",false
    },{new String[]{"{A2:{  }}","{A1:{  }}"}, // redirecting into one of multiple outer scopes
        "{InnerA:{}  method InnerA m(InnerA a) a}","Outer0::InnerA","Outer2::A2","{ method Outer2::A2 m(Outer2::A2 a) a}",false
    },{new String[]{"{A2:{ A2n:{}  }}","{A1:{ A1n:{}  }}"}, // redirecting into nested classes
        "{InnerA:{}  method InnerA m(InnerA a) a}","Outer0::InnerA","Outer1::A1::A1n","{ method Outer1::A1::A1n m(Outer1::A1::A1n a) a}",false
    },{new String[]{"{A2:{ A2n:{}  }}","{A1:{ A1n:{}  }}"}, // redirecting into nested classes
        "{InnerA:{}  method InnerA m(InnerA a) a}","Outer0::InnerA","Outer2::A2::A2n","{ method Outer2::A2::A2n m(Outer2::A2::A2n a) a}",false

    // the errors have variable portions.
	// try to explore the cardinality space of the variable portions
	//   for each error.
    // the cardinality, or option space, of each parameter is listed in parentheses.

    // SourceUnfit: Path(1), PrivatePath(t/f), SrcKind(enum(9)), DestKind(enum(9)), 
    //   UnexpectedMethods(0..), UnexpectedImplementedInterfaces(0..)
    },{new String[]{"{A:{ }}"},  // from module with an unexpected function
        "{InnerA:{type method Void fun()} }","Outer0::InnerA","Outer1::A",
        "{"+"Kind:{'@stringU\n'SourceUnfit\n}"
           +"SrcPath:{'@stringU\n'Outer0::InnerA\n}"
           +"DestExternalPath:{'@Outer1::A\n}"
           +"PrivatePath:{'@stringU\n'false\n}"
           +"SrcKind:{'@stringU\n'TemplateModule\n}"
           +"DestKind:{'@stringU\n'Box_TemplateModule\n}"
           +"UnexpectedMethods:{'@stringU\n'[fun()]\n}"
           +"UnexpectedImplementednterfaces:{'@stringU\n'[]\n}"
        + "}",true
    },{new String[]{"{A:{ }}"},  // same test, but with an argument, using new mechanism
        "{InnerA:{type method Void fun(Void that)} }","Outer0::InnerA","Outer1::A",
        ec.load("SourceUnfit",
                "SrcPath", "Outer0::InnerA",
                "DestExternalPath", "'@Outer1::A",
                "PrivatePath", "false",
                "SrcKind", "TemplateModule",
                "DestKind", "Box_TemplateModule",
                "UnexpectedMethods", "[fun(that)]",
                "UnexpectedImplementedInterfaces", "[]"
                )
          .str(), true
    },{new String[]{  // with some matching methods
        "{A:{type method Void fun(Void that)  method Void mostFun(Void that, Library other) }}"
        },
        "{InnerA:{type method Void fun(Void that) type method Void moreFun(Void that)"
        + "method Void mostFun(Void that, Library other) method Void notSoFun() } }",
        "Outer0::InnerA","Outer1::A",
        ec
          .rename("UnexpectedImplementedInterfaces", // TODO: remove this when the test before passes
                  "UnexpectedImplementednterfaces")
          .set("SrcKind", "Template", "DestKind", "Template",
               "UnexpectedMethods", "[moreFun(that), notSoFun()]")
          .str(), true
    },{new String[]{  // with a mismatch on parameter names in the method selector
        "{InnerA:{type method Void fun(Void that) type method Void moreFun()"
        + "method Void mostFun(Void that, Library mineAllMine) method Void notSoFun() } }",
        },
        "{InnerA:{type method Void fun(Void that) type method Void moreFun(Void that)"
        + "method Void mostFun(Void that, Library other) method Void notSoFun() } }",
        "Outer0::InnerA","Outer1::A",
        ec
          .set("UnexpectedMethods", "[moreFun(that), mostFun(that,other)]")
          .str(), true
/* TODO: this test, when I get to method clashes
    },{new String[]{ // mismatches in type vs instance method
        "{A:{type method Void fun(Void that) method Void moreFun(Void that)"
        + "type method Void mostFun(Void that, Library other) method Void notSoFun() } }",
        },
        "{InnerA:{type method Void fun(Void that) type method Void moreFun(Void that)"
        + "method Void mostFun(Void that, Library other) method Void notSoFun() } }",
        "Outer0::InnerA","Outer1::A",
        ec
          .set("UnexpectedMethods", "[moreFun(that), mostFun()]").str(), true
          */
    
    }
});}

//},{"Outer2::D::C","Outer1::C",new String[]{"{A:{}}","{C:{}}","{D:##walkBy}"}


@Test  public void test() {
  TestHelper.configureForTest();
  Program p=TestHelper.getProgram(_p);
  ClassB cb1=getClassB(_cb1);
  Path path1=Path.parse(_path1);
  Path path2=Path.parse(_path2);
  ClassB expected=getClassB(_expected);
  if(!isError){
    ClassB res=Redirect.redirect(p, cb1,path1,path2);
    TestHelper.assertEqualExp(expected,res);
    }
  else{
    try{Redirect.redirect(p, cb1,path1,path2);fail("error expected");}
    catch(Resources.Error err){
      ClassB res=(ClassB)err.unbox;
      TestHelper.assertEqualExp(expected,res);
    }
  }
}
}


}