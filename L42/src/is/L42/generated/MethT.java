// Generated by delombok at Tue Nov 19 18:07:57 NZDT 2019
package is.L42.generated;

import java.util.*;
import static is.L42.tools.General.*;
import is.L42.generated.Core.MH;

public final class MethT {
  private final List<Mdf> mdfs;
  private final Mdf mdf;

  public MH mh() {
    var mdfs = this.mdfs.subList(1, this.mdfs.size());
    List<Core.T> ts = L(mdfs, (c, mi) -> c.add(P.coreAny.withMdf(mi)));
    Core.T t = P.coreAny.withMdf(mdf);
    S s = new S("a", L(range(mdfs), (c, i) -> c.add(new X("x" + i))), -1);
    return new MH(this.mdfs.get(0), L(), t, s, ts, L());
  }

  public boolean wf() {
    try {
      mh().wf();
      return true;
    } catch (is.L42.common.EndError ee) {
      return false;
    }
  }

  @java.lang.SuppressWarnings("all")
  public MethT(final List<Mdf> mdfs, final Mdf mdf) {
    this.mdfs = mdfs;
    this.mdf = mdf;
  }

  @java.lang.SuppressWarnings("all")
  public List<Mdf> mdfs() {
    return this.mdfs;
  }

  @java.lang.SuppressWarnings("all")
  public Mdf mdf() {
    return this.mdf;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof MethT)) return false;
    final MethT other = (MethT) o;
    final java.lang.Object this$mdfs = this.mdfs();
    final java.lang.Object other$mdfs = other.mdfs();
    if (this$mdfs == null ? other$mdfs != null : !this$mdfs.equals(other$mdfs)) return false;
    final java.lang.Object this$mdf = this.mdf();
    final java.lang.Object other$mdf = other.mdf();
    if (this$mdf == null ? other$mdf != null : !this$mdf.equals(other$mdf)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $mdfs = this.mdfs();
    result = result * PRIME + ($mdfs == null ? 43 : $mdfs.hashCode());
    final java.lang.Object $mdf = this.mdf();
    result = result * PRIME + ($mdf == null ? 43 : $mdf.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "MethT(mdfs=" + this.mdfs() + ", mdf=" + this.mdf() + ")";
  }

  @java.lang.SuppressWarnings("all")
  public MethT withMdfs(final List<Mdf> mdfs) {
    return this.mdfs == mdfs ? this : new MethT(mdfs, this.mdf);
  }

  @java.lang.SuppressWarnings("all")
  public MethT withMdf(final Mdf mdf) {
    return this.mdf == mdf ? this : new MethT(this.mdfs, mdf);
  }
}
