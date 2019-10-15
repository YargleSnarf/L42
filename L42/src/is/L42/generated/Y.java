// Generated by delombok at Tue Oct 15 15:51:09 NZDT 2019
package is.L42.generated;

import java.util.List;
import is.L42.common.Program;
import is.L42.common.GX;

public final class Y {
  private final Program p;
  private final GX g;
  private final List<ST> onSlash;
  private final Half.XP _onSlashX;
  private final List<ST> _expectedT;

  @java.lang.SuppressWarnings("all")
  public Y(final Program p, final GX g, final List<ST> onSlash, final Half.XP _onSlashX, final List<ST> _expectedT) {
    this.p = p;
    this.g = g;
    this.onSlash = onSlash;
    this._onSlashX = _onSlashX;
    this._expectedT = _expectedT;
  }

  @java.lang.SuppressWarnings("all")
  public Program p() {
    return this.p;
  }

  @java.lang.SuppressWarnings("all")
  public GX g() {
    return this.g;
  }

  @java.lang.SuppressWarnings("all")
  public List<ST> onSlash() {
    return this.onSlash;
  }

  @java.lang.SuppressWarnings("all")
  public Half.XP _onSlashX() {
    return this._onSlashX;
  }

  @java.lang.SuppressWarnings("all")
  public List<ST> _expectedT() {
    return this._expectedT;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Y)) return false;
    final Y other = (Y) o;
    final java.lang.Object this$p = this.p();
    final java.lang.Object other$p = other.p();
    if (this$p == null ? other$p != null : !this$p.equals(other$p)) return false;
    final java.lang.Object this$g = this.g();
    final java.lang.Object other$g = other.g();
    if (this$g == null ? other$g != null : !this$g.equals(other$g)) return false;
    final java.lang.Object this$onSlash = this.onSlash();
    final java.lang.Object other$onSlash = other.onSlash();
    if (this$onSlash == null ? other$onSlash != null : !this$onSlash.equals(other$onSlash)) return false;
    final java.lang.Object this$_onSlashX = this._onSlashX();
    final java.lang.Object other$_onSlashX = other._onSlashX();
    if (this$_onSlashX == null ? other$_onSlashX != null : !this$_onSlashX.equals(other$_onSlashX)) return false;
    final java.lang.Object this$_expectedT = this._expectedT();
    final java.lang.Object other$_expectedT = other._expectedT();
    if (this$_expectedT == null ? other$_expectedT != null : !this$_expectedT.equals(other$_expectedT)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $p = this.p();
    result = result * PRIME + ($p == null ? 43 : $p.hashCode());
    final java.lang.Object $g = this.g();
    result = result * PRIME + ($g == null ? 43 : $g.hashCode());
    final java.lang.Object $onSlash = this.onSlash();
    result = result * PRIME + ($onSlash == null ? 43 : $onSlash.hashCode());
    final java.lang.Object $_onSlashX = this._onSlashX();
    result = result * PRIME + ($_onSlashX == null ? 43 : $_onSlashX.hashCode());
    final java.lang.Object $_expectedT = this._expectedT();
    result = result * PRIME + ($_expectedT == null ? 43 : $_expectedT.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Y(p=" + this.p() + ", g=" + this.g() + ", onSlash=" + this.onSlash() + ", _onSlashX=" + this._onSlashX() + ", _expectedT=" + this._expectedT() + ")";
  }

  @java.lang.SuppressWarnings("all")
  public Y withP(final Program p) {
    return this.p == p ? this : new Y(p, this.g, this.onSlash, this._onSlashX, this._expectedT);
  }

  @java.lang.SuppressWarnings("all")
  public Y withG(final GX g) {
    return this.g == g ? this : new Y(this.p, g, this.onSlash, this._onSlashX, this._expectedT);
  }

  @java.lang.SuppressWarnings("all")
  public Y withOnSlash(final List<ST> onSlash) {
    return this.onSlash == onSlash ? this : new Y(this.p, this.g, onSlash, this._onSlashX, this._expectedT);
  }

  @java.lang.SuppressWarnings("all")
  public Y with_onSlashX(final Half.XP _onSlashX) {
    return this._onSlashX == _onSlashX ? this : new Y(this.p, this.g, this.onSlash, _onSlashX, this._expectedT);
  }

  @java.lang.SuppressWarnings("all")
  public Y with_expectedT(final List<ST> _expectedT) {
    return this._expectedT == _expectedT ? this : new Y(this.p, this.g, this.onSlash, this._onSlashX, _expectedT);
  }
}
