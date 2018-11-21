// Generated by delombok at Wed Nov 21 12:57:41 NZDT 2018
package ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;
import ast.Ast.Atom;
import ast.Ast.C;
import ast.Ast.Doc;
import ast.Ast.Mdf;
import ast.Ast.Type;
import ast.Ast.Path;
import tools.ListFormatter;
import platformSpecific.javaTranslation.Resources;
import tools.Assertions;
import tools.Map;

public class PathAux {
  public static Path Void() {
    return PathPrimitive._Void;
  }

  public static Path Any() {
    return PathPrimitive._Any;
  }

  public static Path Library() {
    return PathPrimitive._Library;
  }

  public static boolean isValidOuter(String name) {
    // thus invalid as pathName
    if (name.equals("This")) {
      return true;
    }
    if (name.equals("This0")) {
      return true;
    }
    if (!name.startsWith("This")) {
      return false;
    }
    int firstN = "This".length();
    char c = name.charAt(firstN);
    // first is 1--9 and all rest is 0-9
    if ("123456789".indexOf(c) == -1) {
      return false;
    }
    for (int i = firstN + 1; i < name.length(); i++) {
      if ("0123456789".indexOf(name.charAt(i)) == -1) {
        return false;
      }
    }
    return true;
  }

  public static List<C> sToC(List<String> names) {
    return immCs(Map.of(s -> C.of(s), names));
  }

  public static List<C> immCs(List<C> cs) {
    assert (cs = Collections.unmodifiableList(cs)) != null;
    return cs;
  }

  public static List<C> parseValidCs(String cs) {
    if (cs.equals("This0") || cs.equals("This")) {
      return Collections.emptyList();
    }
    List<String> rowData = Arrays.asList(cs.split("\\."));
    return PathAux.sToC(rowData);
  }

  public static String as42Path(List<ast.Ast.C> path) {
    if (path.isEmpty()) {
      return "This";
    }
    String res = path.get(0).toString();
    for (int i = 1; i < path.size(); i++) {
      res += "." + path.get(i);
    }
    return res;
  }

  public static <T> String asSet(Collection<T> s) {
    if (s.size() == 1) {
      return s.iterator().next().toString();
    } else {
      return new ListFormatter().header("{").seperator(", ").footer("}").append(s).toString();
    }
  }

  public static boolean isValidPrimitiveName(String name) {
    Path p = PathPrimitive._parsePrimitive(name);
    return p != null;
  }

  public static boolean isValidClassName(String name) {
    if (name.isEmpty()) {
      return false;
    }
    if (isValidOuter(name)) {
      return false;
    }
    if (isValidPrimitiveName(name)) {
      return false;
    }
    if (!isValidPathStart(name.charAt(0))) {
      return false;
    }
    for (int i = 1; i < name.length(); i++) {
      if (!isValidPathChar(name.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  public static boolean isValidPathStart(char c) {
    if (c == '%') {
      return true;
    }
    if (c == '$') {
      return true;
    }
    return Character.isUpperCase(c);
  }

  public static boolean isValidPathChar(char c) {
    if (c == '%') {
      return true;
    }
    if (c == '$') {
      return true;
    }
    if (c == '_') {
      return true;
    }
    assert c != '\t' : c;
    return Character.isUpperCase(c) || Character.isLowerCase(c) || Character.isDigit(c);
  }

  public static List<Ast.C> _subPath(List<Ast.C> cs, List<Ast.C> csLong) {
    int shortSize = cs.size();
    int longSize = csLong.size();
    if (shortSize > longSize) {
      return null;
    }
    if (!cs.equals(csLong.subList(0, shortSize))) {
      return null;
    }
    return csLong.subList(shortSize, longSize);
  }

  public static int getThisn(String that) {
    that = that.substring("This".length());
    if (that.isEmpty()) {
      return 0;
    }
    return Integer.parseInt(that);
  }
}

final class PathCore extends Path {
  public String toString() {
    return sugarVisitors.ToFormattedText.of(this);
  }//otherwise lombock overrides it

  public PathCore(int outerN, List<C> cBar) {
    assert outerN >= 0;
    this.outerN = outerN;
    this.cBar = cBar;
  }

  public static Path instance(int n, List<C> cs) {
    if (n == 0 && cs.isEmpty()) {
      return _This0;
    }
    return new PathCore(n, PathAux.immCs(cs));
  }

  public static Path _parsePathCode(List<String> names) {
    if (names.isEmpty()) {
      return null;
    }
    String first = names.get(0);
    if (!PathAux.isValidOuter(first)) {
      return null;
    }
    int n = PathAux.getThisn(first);
    List<String> tail = names.subList(1, names.size());
    return instance(n, PathAux.sToC(tail));
  }

  private static final PathCore _This0 = new PathCore(0, Collections.emptyList());
  private final int outerN;
  private final List<C> cBar;

  public boolean isCore() {
    return true;
  }

  public Path popC() {
    return new PathCore(outerN, cBar.subList(0, cBar.size() - 1));
  }

  public Path pushC(C c) {
    List<C> newCBar = new ArrayList<>(cBar);
    newCBar.add(c);
    return new PathCore(outerN, PathAux.immCs(newCBar));
  }

  public List<C> getCBar() {
    return cBar;
  }

  public Path setNewOuter(int n) {
    return new PathCore(n, cBar);
  }

  public int outerNumber() {
    return outerN;
  }

  @java.lang.SuppressWarnings("all")
  public int getOuterN() {
    return this.outerN;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PathCore)) return false;
    final PathCore other = (PathCore) o;
    if (!other.canEqual((java.lang.Object) this)) return false;
    if (this.getOuterN() != other.getOuterN()) return false;
    final java.lang.Object this$cBar = this.getCBar();
    final java.lang.Object other$cBar = other.getCBar();
    if (this$cBar == null ? other$cBar != null : !this$cBar.equals(other$cBar)) return false;
    return true;
  }

  @java.lang.SuppressWarnings("all")
  protected boolean canEqual(final java.lang.Object other) {
    return other instanceof PathCore;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOuterN();
    final java.lang.Object $cBar = this.getCBar();
    result = result * PRIME + ($cBar == null ? 43 : $cBar.hashCode());
    return result;
  }
}

final class PathPrimitive extends Path {
  public String toString() {
    return sugarVisitors.ToFormattedText.of(this);
  }//otherwise lombock overrides it

  private PathPrimitive(String name) {
    this.name = name;
  }

  private final String name;

  public boolean isPrimitive() {
    return true;
  }

  public static Path _parsePrimitive(List<String> names) {
    if (names.size() != 1) {
      return null;
    }
    String name = names.get(0);
    return _parsePrimitive(name);
  }

  public static Path _parsePrimitive(String name) {
    if (name.equals(_Void.name)) {
      return _Void;
    }
    if (name.equals(_Any.name)) {
      return _Any;
    }
    if (name.equals(_Library.name)) {
      return _Library;
    }
    return null;
  }

  public static final PathPrimitive _Void = new PathPrimitive("Void");
  public static final PathPrimitive _Any = new PathPrimitive("Any");
  public static final PathPrimitive _Library = new PathPrimitive("Library");

  @java.lang.SuppressWarnings("all")
  public String getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PathPrimitive)) return false;
    final PathPrimitive other = (PathPrimitive) o;
    if (!other.canEqual((java.lang.Object) this)) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.SuppressWarnings("all")
  protected boolean canEqual(final java.lang.Object other) {
    return other instanceof PathPrimitive;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }
}

final class PathSugar extends Path {
  public String toString() {
    return sugarVisitors.ToFormattedText.of(this);
  }//otherwise lombock overrides it

  public static Path _instance(List<String> names) {
    for (String s : names) {
      if (!PathAux.isValidClassName(s)) {
        return null;
      }
    }
    assert (names = Collections.unmodifiableList(names)) != null;//ok for testing efficiency
    return new PathSugar(PathAux.sToC(names));
  }

  private PathSugar(List<C> names) {
    this.names = names;
  }

  private final List<C> names;

  public List<C> sugarNames() {
    return names;
  }

  @java.lang.SuppressWarnings("all")
  public List<C> getNames() {
    return this.names;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PathSugar)) return false;
    final PathSugar other = (PathSugar) o;
    if (!other.canEqual((java.lang.Object) this)) return false;
    final java.lang.Object this$names = this.getNames();
    final java.lang.Object other$names = other.getNames();
    if (this$names == null ? other$names != null : !this$names.equals(other$names)) return false;
    return true;
  }

  @java.lang.SuppressWarnings("all")
  protected boolean canEqual(final java.lang.Object other) {
    return other instanceof PathSugar;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $names = this.getNames();
    result = result * PRIME + ($names == null ? 43 : $names.hashCode());
    return result;
  }
}
