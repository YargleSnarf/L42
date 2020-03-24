// Generated by delombok at Tue Mar 24 20:42:48 NZDT 2020
package is.L42.generated;

import java.util.List;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.CollectorVisitor;
import is.L42.visitors.Visitable;
import is.L42.common.Constants;
import is.L42.common.Parse;
import static is.L42.tools.General.*;

enum PrimitiveP implements P {
  Void, Library, Any;

  public String toString() {
    return Constants.toS.apply(this);
  }
}

public interface P extends Visitable<P> {
  default P accept(CloneVisitor cv) {
    return cv.visitP(this);
  }

  default void accept(CollectorVisitor cv) {
    cv.visitP(this);
  }

  default boolean wf() {
    return Constants.wf.test(this);
  }

  P pAny = PrimitiveP.Any;
  P pVoid = PrimitiveP.Void;
  P pLibrary = PrimitiveP.Library;
  Core.T coreAny = new Core.T(Mdf.Immutable, L(), P.pAny);
  Core.T coreLibrary = new Core.T(Mdf.Immutable, L(), P.pLibrary);
  Core.T coreVoid = new Core.T(Mdf.Immutable, L(), P.pVoid);
  List<ST> stzCoreVoid = L(new Core.T(Mdf.Immutable, L(), P.pVoid));
  P.NCs pThis0 = P.of(0, L());
  P.NCs pThis1 = P.of(1, L());
  Core.T coreThis0 = new Core.T(Mdf.Immutable, L(), pThis0);
  Core.T coreThis1 = new Core.T(Mdf.Immutable, L(), pThis0);
  Core.T coreClassAny = new Core.T(Mdf.Class, L(), P.pAny);
  Full.T fullThis0 = new Full.T(Mdf.Immutable, L(), L(), coreThis0.p());
  Full.T fullClassAny = new Full.T(Mdf.Class, L(), L(), P.pAny);
  Full.T fullVoid = new Full.T(Mdf.Immutable, L(), L(), P.pVoid);

  static P.NCs of(int n, List<C> cs) {
    assert n >= 0;
    return new NCs(n, cs);
  }

  static P parse(String s) {
    var csP = Parse.csP(Constants.dummy, s);
    assert !csP.hasErr();
    assert csP.res._p() != null;
    return csP.res._p();
  }

  default boolean isNCs() {
    return false;
  }

  default NCs toNCs() {
    throw bug();
  }

  default boolean hasUniqueNum() {
    if (!isNCs()) {
      return false;
    }
    var p = toNCs();
    return p.cs.stream().anyMatch(c -> c.hasUniqueNum());
  }


  final class NCs implements P {
    private final int n;
    private final List<C> cs;

    @Override
    public NCs toNCs() {
      return this;
    }

    @Override
    public boolean isNCs() {
      return true;
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    public boolean hasUniqueNum() {
      for (C ci : cs) {
        if (ci.hasUniqueNum()) {
          return true;
        }
      }
      return false;
    }

    @java.lang.SuppressWarnings("all")
    public NCs(final int n, final List<C> cs) {
      this.n = n;
      this.cs = cs;
    }

    @java.lang.SuppressWarnings("all")
    public int n() {
      return this.n;
    }

    @java.lang.SuppressWarnings("all")
    public List<C> cs() {
      return this.cs;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof P.NCs)) return false;
      final P.NCs other = (P.NCs) o;
      if (this.n() != other.n()) return false;
      final java.lang.Object this$cs = this.cs();
      final java.lang.Object other$cs = other.cs();
      if (this$cs == null ? other$cs != null : !this$cs.equals(other$cs)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      result = result * PRIME + this.n();
      final java.lang.Object $cs = this.cs();
      result = result * PRIME + ($cs == null ? 43 : $cs.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public NCs withN(final int n) {
      return this.n == n ? this : new NCs(n, this.cs);
    }

    @java.lang.SuppressWarnings("all")
    public NCs withCs(final List<C> cs) {
      return this.cs == cs ? this : new NCs(this.n, cs);
    }
  }
}
