package testAux;

import static helpers.TestHelper.getClassB;
import helpers.TestHelper.ErrorCarry;
import static org.junit.Assert.fail;
import helpers.TestHelper;

import static helpers.TestHelper.lineNumber;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import platformSpecific.javaTranslation.Resources;
import ast.Ast.MethodSelector;
import ast.Ast.Path;
import ast.ExpCore.ClassB;
import auxiliaryGrammar.Program;
import coreVisitors.PathAnnotateClass;

@RunWith(Parameterized.class)
public class TestCloneWithPath {
  @Parameter(0) public int _lineNumber;
  @Parameter(1) public String _cb1;
  @Parameter(2) public String _expected;
  @Parameters(name = "{index}: line {0}")
  public static List<Object[]> createData() {return Arrays.asList(new Object[][] {{
    lineNumber(),"{}","{'Outer0.\n}"
  },{
    lineNumber(),"{method Void foo()}","{'Outer0.\nmethod 'Outer0.\nVoid foo()}"
  },{
    lineNumber(),"{ A:{method Void foo()  method Void bar() }}",
    "{'Outer0.\n A:'Outer0.\n{'Outer0.A[0]\nmethod 'Outer0.A[1]\nVoid foo()  method 'Outer0.A[1]\nVoid bar()   }}"
  },{
    lineNumber(),"{ A:{B:{method Void foo()  method Void bar() }}}",
    "{'Outer0.\n"
    + "A:'Outer0.\n"
    + "{'Outer0.A[0]\n"
    + "B:'Outer0.A[1]\n"
    + "{'Outer0.A[1]B[0]\n"
    + "method 'Outer0.A[1]B[1]\n"
    + "Void foo() \n"
    + "method 'Outer0.A[1]B[1]\n"
    + "Void bar() }}}"
  },{
    lineNumber(),"{ A:{method Void foo() this.foo({<:A},x:{<:B}) }}",
    "{'Outer0.\n"
    + "A:'Outer0.\n"
    + "{'Outer0.A[0]\n"
    + "method 'Outer0.A[1]\n"
    + "Void foo() this.foo(that:{'Outer0.A[1]foo()[0]\n"
    + "<:Outer2.A}, x:{'Outer0.A[1]foo()[1]\n"
    + "<:Outer2.B})}}"
  }});}


@Test  public void test() {
  TestHelper.configureForTest();
  ClassB cb1=getClassB("cb1", _cb1);
  ClassB expected=getClassB("expected", _expected);
  ClassB result = (ClassB) cb1.accept(new PathAnnotateClass());
  TestHelper.assertEqualExp(expected, result);
  }
}