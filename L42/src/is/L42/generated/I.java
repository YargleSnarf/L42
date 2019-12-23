// Generated by delombok at Mon Dec 23 14:35:13 NZDT 2019
package is.L42.generated;

import java.util.List;
import is.L42.common.Program;
import is.L42.common.G;

public final class I {
  private final C _c;
  private final Program p;
  private final G g;

  @java.lang.SuppressWarnings("all")
  public I(final C _c, final Program p, final G g) {
    this._c = _c;
    this.p = p;
    this.g = g;
  }

  @java.lang.SuppressWarnings("all")
  public C _c() {
    return this._c;
  }

  @java.lang.SuppressWarnings("all")
  public Program p() {
    return this.p;
  }

  @java.lang.SuppressWarnings("all")
  public G g() {
    return this.g;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof I)) return false;
    final I other = (I) o;
    final java.lang.Object this$_c = this._c();
    final java.lang.Object other$_c = other._c();
    if (this$_c == null ? other$_c != null : !this$_c.equals(other$_c)) return false;
    final java.lang.Object this$p = this.p();
    final java.lang.Object other$p = other.p();
    if (this$p == null ? other$p != null : !this$p.equals(other$p)) return false;
    final java.lang.Object this$g = this.g();
    final java.lang.Object other$g = other.g();
    if (this$g == null ? other$g != null : !this$g.equals(other$g)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $_c = this._c();
    result = result * PRIME + ($_c == null ? 43 : $_c.hashCode());
    final java.lang.Object $p = this.p();
    result = result * PRIME + ($p == null ? 43 : $p.hashCode());
    final java.lang.Object $g = this.g();
    result = result * PRIME + ($g == null ? 43 : $g.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "I(_c=" + this._c() + ", p=" + this.p() + ", g=" + this.g() + ")";
  }

  @java.lang.SuppressWarnings("all")
  public I with_c(final C _c) {
    return this._c == _c ? this : new I(_c, this.p, this.g);
  }

  @java.lang.SuppressWarnings("all")
  public I withP(final Program p) {
    return this.p == p ? this : new I(this._c, p, this.g);
  }

  @java.lang.SuppressWarnings("all")
  public I withG(final G g) {
    return this.g == g ? this : new I(this._c, this.p, g);
  }
}
