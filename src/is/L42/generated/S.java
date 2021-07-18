// Generated by delombok at Sun Jun 13 15:12:41 NZST 2021
package is.L42.generated;

import java.util.List;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.CollectorVisitor;
import is.L42.visitors.Visitable;
import is.L42.common.Constants;

public final class S implements LDom, Visitable<S> {
  @Override
  public S accept(CloneVisitor cv) {
    return cv.visitS(this);
  }

  @Override
  public void accept(CollectorVisitor cv) {
    cv.visitS(this);
  }

  @Override
  public String toString() {
    return Constants.toS.apply(this);
  }

  @Override
  public boolean wf() {
    return Constants.wf.test(this);
  }

  private final String m;
  private final List<X> xs;
  private final int uniqueNum;//-1 for no unique num

  public static S parse(String str) {
    var res = is.L42.common.Parse.ctxPathSelX(Constants.dummy, str);
    assert !res.hasErr();
    Full.PathSel ps = new is.L42.visitors.AuxVisitor(null).visitPathSelX(res.res.pathSelX());
    assert ps != null;
    S s = ps._s();
    assert s != null;
    return s;
  }
  public S toCore() {
    if(!this.m.isEmpty()) {return this;} 
    return this.withM("#apply");
  }

  @java.lang.SuppressWarnings("all")
  public S(final String m, final List<X> xs, final int uniqueNum) {
    this.m = m;
    this.xs = xs;
    this.uniqueNum = uniqueNum;
  }

  @java.lang.SuppressWarnings("all")
  public String m() {
    return this.m;
  }

  @java.lang.SuppressWarnings("all")
  public List<X> xs() {
    return this.xs;
  }

  @java.lang.SuppressWarnings("all")
  public int uniqueNum() {
    return this.uniqueNum;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof S)) return false;
    final S other = (S) o;
    if (this.uniqueNum() != other.uniqueNum()) return false;
    final java.lang.Object this$m = this.m();
    final java.lang.Object other$m = other.m();
    if (this$m == null ? other$m != null : !this$m.equals(other$m)) return false;
    final java.lang.Object this$xs = this.xs();
    final java.lang.Object other$xs = other.xs();
    if (this$xs == null ? other$xs != null : !this$xs.equals(other$xs)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.uniqueNum();
    final java.lang.Object $m = this.m();
    result = result * PRIME + ($m == null ? 43 : $m.hashCode());
    final java.lang.Object $xs = this.xs();
    result = result * PRIME + ($xs == null ? 43 : $xs.hashCode());
    return result;
  }

  @java.lang.SuppressWarnings("all")
  public S withM(final String m) {
    return this.m == m ? this : new S(m, this.xs, this.uniqueNum);
  }

  @java.lang.SuppressWarnings("all")
  public S withXs(final List<X> xs) {
    return this.xs == xs ? this : new S(this.m, xs, this.uniqueNum);
  }

  @java.lang.SuppressWarnings("all")
  public S withUniqueNum(final int uniqueNum) {
    return this.uniqueNum == uniqueNum ? this : new S(this.m, this.xs, uniqueNum);
  }
}
