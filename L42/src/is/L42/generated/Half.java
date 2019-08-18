// Generated by delombok at Mon Aug 19 09:09:33 PETT 2019
package is.L42.generated;

import java.util.List;
import java.util.stream.Collectors;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.CollectorVisitor;
import is.L42.visitors.Visitable;
import is.L42.common.Constants;

public class Half {

  public static interface E extends HasPos, HasWf, HasVisitable {
    Visitable<? extends E> visitable();
  }


  public static interface Leaf extends E {
  }


  public static interface Wrapper extends E {
    E e();
  }


  public static interface XP extends E {
    Visitable<? extends XP> visitable();
  }


  public static final class PCastT implements Leaf, XP, Visitable<PCastT> {
    @Override
    public Visitable<PCastT> visitable() {
      return this;
    }

    @Override
    public PCastT accept(CloneVisitor cv) {
      return cv.visitPCastT(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitPCastT(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final P p;
    private final T t;

    @java.lang.SuppressWarnings("all")
    public PCastT(final Pos pos, final P p, final T t) {
      this.pos = pos;
      this.p = p;
      this.t = t;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public P p() {
      return this.p;
    }

    @java.lang.SuppressWarnings("all")
    public T t() {
      return this.t;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.PCastT)) return false;
      final Half.PCastT other = (Half.PCastT) o;
      final java.lang.Object this$p = this.p();
      final java.lang.Object other$p = other.p();
      if (this$p == null ? other$p != null : !this$p.equals(other$p)) return false;
      final java.lang.Object this$t = this.t();
      final java.lang.Object other$t = other.t();
      if (this$t == null ? other$t != null : !this$t.equals(other$t)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $p = this.p();
      result = result * PRIME + ($p == null ? 43 : $p.hashCode());
      final java.lang.Object $t = this.t();
      result = result * PRIME + ($t == null ? 43 : $t.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public PCastT withPos(final Pos pos) {
      return this.pos == pos ? this : new PCastT(pos, this.p, this.t);
    }

    @java.lang.SuppressWarnings("all")
    public PCastT withP(final P p) {
      return this.p == p ? this : new PCastT(this.pos, p, this.t);
    }

    @java.lang.SuppressWarnings("all")
    public PCastT withT(final T t) {
      return this.t == t ? this : new PCastT(this.pos, this.p, t);
    }
  }


  public static final class Slash implements Leaf, XP, Visitable<Slash> {
    @Override
    public Visitable<Slash> visitable() {
      return this;
    }

    @Override
    public Slash accept(CloneVisitor cv) {
      return cv.visitSlash(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitSlash(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final List<ST> stz;

    @java.lang.SuppressWarnings("all")
    public Slash(final Pos pos, final List<ST> stz) {
      this.pos = pos;
      this.stz = stz;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public List<ST> stz() {
      return this.stz;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.Slash)) return false;
      final Half.Slash other = (Half.Slash) o;
      final java.lang.Object this$stz = this.stz();
      final java.lang.Object other$stz = other.stz();
      if (this$stz == null ? other$stz != null : !this$stz.equals(other$stz)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $stz = this.stz();
      result = result * PRIME + ($stz == null ? 43 : $stz.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public Slash withPos(final Pos pos) {
      return this.pos == pos ? this : new Slash(pos, this.stz);
    }

    @java.lang.SuppressWarnings("all")
    public Slash withStz(final List<ST> stz) {
      return this.stz == stz ? this : new Slash(this.pos, stz);
    }
  }


  public static final class BinOp implements E, Visitable<BinOp> {
    @Override
    public Visitable<BinOp> visitable() {
      return this;
    }

    @Override
    public BinOp accept(CloneVisitor cv) {
      return cv.visitBinOp(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitBinOp(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final Op op;
    private final List<XP> es;

    @java.lang.SuppressWarnings("all")
    public BinOp(final Pos pos, final Op op, final List<XP> es) {
      this.pos = pos;
      this.op = op;
      this.es = es;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public Op op() {
      return this.op;
    }

    @java.lang.SuppressWarnings("all")
    public List<XP> es() {
      return this.es;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.BinOp)) return false;
      final Half.BinOp other = (Half.BinOp) o;
      final java.lang.Object this$op = this.op();
      final java.lang.Object other$op = other.op();
      if (this$op == null ? other$op != null : !this$op.equals(other$op)) return false;
      final java.lang.Object this$es = this.es();
      final java.lang.Object other$es = other.es();
      if (this$es == null ? other$es != null : !this$es.equals(other$es)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $op = this.op();
      result = result * PRIME + ($op == null ? 43 : $op.hashCode());
      final java.lang.Object $es = this.es();
      result = result * PRIME + ($es == null ? 43 : $es.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public BinOp withPos(final Pos pos) {
      return this.pos == pos ? this : new BinOp(pos, this.op, this.es);
    }

    @java.lang.SuppressWarnings("all")
    public BinOp withOp(final Op op) {
      return this.op == op ? this : new BinOp(this.pos, op, this.es);
    }

    @java.lang.SuppressWarnings("all")
    public BinOp withEs(final List<XP> es) {
      return this.es == es ? this : new BinOp(this.pos, this.op, es);
    }
  }


  public static final class MCall implements E, Visitable<MCall> {
    @Override
    public Visitable<MCall> visitable() {
      return this;
    }

    @Override
    public MCall accept(CloneVisitor cv) {
      return cv.visitMCall(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitMCall(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final XP xP;
    private final S s;
    private final List<E> es;

    @java.lang.SuppressWarnings("all")
    public MCall(final Pos pos, final XP xP, final S s, final List<E> es) {
      this.pos = pos;
      this.xP = xP;
      this.s = s;
      this.es = es;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public XP xP() {
      return this.xP;
    }

    @java.lang.SuppressWarnings("all")
    public S s() {
      return this.s;
    }

    @java.lang.SuppressWarnings("all")
    public List<E> es() {
      return this.es;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.MCall)) return false;
      final Half.MCall other = (Half.MCall) o;
      final java.lang.Object this$xP = this.xP();
      final java.lang.Object other$xP = other.xP();
      if (this$xP == null ? other$xP != null : !this$xP.equals(other$xP)) return false;
      final java.lang.Object this$s = this.s();
      final java.lang.Object other$s = other.s();
      if (this$s == null ? other$s != null : !this$s.equals(other$s)) return false;
      final java.lang.Object this$es = this.es();
      final java.lang.Object other$es = other.es();
      if (this$es == null ? other$es != null : !this$es.equals(other$es)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $xP = this.xP();
      result = result * PRIME + ($xP == null ? 43 : $xP.hashCode());
      final java.lang.Object $s = this.s();
      result = result * PRIME + ($s == null ? 43 : $s.hashCode());
      final java.lang.Object $es = this.es();
      result = result * PRIME + ($es == null ? 43 : $es.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public MCall withPos(final Pos pos) {
      return this.pos == pos ? this : new MCall(pos, this.xP, this.s, this.es);
    }

    @java.lang.SuppressWarnings("all")
    public MCall withXP(final XP xP) {
      return this.xP == xP ? this : new MCall(this.pos, xP, this.s, this.es);
    }

    @java.lang.SuppressWarnings("all")
    public MCall withS(final S s) {
      return this.s == s ? this : new MCall(this.pos, this.xP, s, this.es);
    }

    @java.lang.SuppressWarnings("all")
    public MCall withEs(final List<E> es) {
      return this.es == es ? this : new MCall(this.pos, this.xP, this.s, es);
    }
  }


  public static final class Block implements E, Visitable<Block> {
    @Override
    public Visitable<Block> visitable() {
      return this;
    }

    @Override
    public Block accept(CloneVisitor cv) {
      return cv.visitBlock(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitBlock(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final List<D> ds;
    private final List<K> ks;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public Block(final Pos pos, final List<D> ds, final List<K> ks, final E e) {
      this.pos = pos;
      this.ds = ds;
      this.ks = ks;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public List<D> ds() {
      return this.ds;
    }

    @java.lang.SuppressWarnings("all")
    public List<K> ks() {
      return this.ks;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.Block)) return false;
      final Half.Block other = (Half.Block) o;
      final java.lang.Object this$ds = this.ds();
      final java.lang.Object other$ds = other.ds();
      if (this$ds == null ? other$ds != null : !this$ds.equals(other$ds)) return false;
      final java.lang.Object this$ks = this.ks();
      final java.lang.Object other$ks = other.ks();
      if (this$ks == null ? other$ks != null : !this$ks.equals(other$ks)) return false;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $ds = this.ds();
      result = result * PRIME + ($ds == null ? 43 : $ds.hashCode());
      final java.lang.Object $ks = this.ks();
      result = result * PRIME + ($ks == null ? 43 : $ks.hashCode());
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public Block withPos(final Pos pos) {
      return this.pos == pos ? this : new Block(pos, this.ds, this.ks, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Block withDs(final List<D> ds) {
      return this.ds == ds ? this : new Block(this.pos, ds, this.ks, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Block withKs(final List<K> ks) {
      return this.ks == ks ? this : new Block(this.pos, this.ds, ks, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Block withE(final E e) {
      return this.e == e ? this : new Block(this.pos, this.ds, this.ks, e);
    }
  }


  public static final class Loop implements Wrapper, Visitable<Loop> {
    @Override
    public Visitable<Loop> visitable() {
      return this;
    }

    @Override
    public Loop accept(CloneVisitor cv) {
      return cv.visitLoop(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitLoop(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public Loop(final Pos pos, final E e) {
      this.pos = pos;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.Loop)) return false;
      final Half.Loop other = (Half.Loop) o;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public Loop withPos(final Pos pos) {
      return this.pos == pos ? this : new Loop(pos, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Loop withE(final E e) {
      return this.e == e ? this : new Loop(this.pos, e);
    }
  }


  public static final class Throw implements Wrapper, Visitable<Throw> {
    @Override
    public Visitable<Throw> visitable() {
      return this;
    }

    @Override
    public Throw accept(CloneVisitor cv) {
      return cv.visitThrow(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitThrow(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final Throw thr;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public Throw(final Pos pos, final Throw thr, final E e) {
      this.pos = pos;
      this.thr = thr;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public Throw thr() {
      return this.thr;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.Throw)) return false;
      final Half.Throw other = (Half.Throw) o;
      final java.lang.Object this$thr = this.thr();
      final java.lang.Object other$thr = other.thr();
      if (this$thr == null ? other$thr != null : !this$thr.equals(other$thr)) return false;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $thr = this.thr();
      result = result * PRIME + ($thr == null ? 43 : $thr.hashCode());
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public Throw withPos(final Pos pos) {
      return this.pos == pos ? this : new Throw(pos, this.thr, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Throw withThr(final Throw thr) {
      return this.thr == thr ? this : new Throw(this.pos, thr, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public Throw withE(final E e) {
      return this.e == e ? this : new Throw(this.pos, this.thr, e);
    }
  }


  public static final class OpUpdate implements Wrapper, Visitable<OpUpdate> {
    @Override
    public Visitable<OpUpdate> visitable() {
      return this;
    }

    @Override
    public OpUpdate accept(CloneVisitor cv) {
      return cv.visitOpUpdate(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitOpUpdate(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Pos pos;
    private final X x;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public OpUpdate(final Pos pos, final X x, final E e) {
      this.pos = pos;
      this.x = x;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public Pos pos() {
      return this.pos;
    }

    @java.lang.SuppressWarnings("all")
    public X x() {
      return this.x;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.OpUpdate)) return false;
      final Half.OpUpdate other = (Half.OpUpdate) o;
      final java.lang.Object this$x = this.x();
      final java.lang.Object other$x = other.x();
      if (this$x == null ? other$x != null : !this$x.equals(other$x)) return false;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $x = this.x();
      result = result * PRIME + ($x == null ? 43 : $x.hashCode());
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public OpUpdate withPos(final Pos pos) {
      return this.pos == pos ? this : new OpUpdate(pos, this.x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public OpUpdate withX(final X x) {
      return this.x == x ? this : new OpUpdate(this.pos, x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public OpUpdate withE(final E e) {
      return this.e == e ? this : new OpUpdate(this.pos, this.x, e);
    }
  }

  //---
  public static final class D implements Visitable<D> {
    @Override
    public D accept(CloneVisitor cv) {
      return cv.visitD(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitD(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final T t;
    private final X x;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public D(final T t, final X x, final E e) {
      this.t = t;
      this.x = x;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public T t() {
      return this.t;
    }

    @java.lang.SuppressWarnings("all")
    public X x() {
      return this.x;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.D)) return false;
      final Half.D other = (Half.D) o;
      final java.lang.Object this$t = this.t();
      final java.lang.Object other$t = other.t();
      if (this$t == null ? other$t != null : !this$t.equals(other$t)) return false;
      final java.lang.Object this$x = this.x();
      final java.lang.Object other$x = other.x();
      if (this$x == null ? other$x != null : !this$x.equals(other$x)) return false;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $t = this.t();
      result = result * PRIME + ($t == null ? 43 : $t.hashCode());
      final java.lang.Object $x = this.x();
      result = result * PRIME + ($x == null ? 43 : $x.hashCode());
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public D withT(final T t) {
      return this.t == t ? this : new D(t, this.x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public D withX(final X x) {
      return this.x == x ? this : new D(this.t, x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public D withE(final E e) {
      return this.e == e ? this : new D(this.t, this.x, e);
    }
  }


  public static final class K implements Visitable<K> {
    @Override
    public K accept(CloneVisitor cv) {
      return cv.visitK(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitK(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Throw thr;
    private final T t;
    private final X x;
    private final E e;

    @java.lang.SuppressWarnings("all")
    public K(final Throw thr, final T t, final X x, final E e) {
      this.thr = thr;
      this.t = t;
      this.x = x;
      this.e = e;
    }

    @java.lang.SuppressWarnings("all")
    public Throw thr() {
      return this.thr;
    }

    @java.lang.SuppressWarnings("all")
    public T t() {
      return this.t;
    }

    @java.lang.SuppressWarnings("all")
    public X x() {
      return this.x;
    }

    @java.lang.SuppressWarnings("all")
    public E e() {
      return this.e;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.K)) return false;
      final Half.K other = (Half.K) o;
      final java.lang.Object this$thr = this.thr();
      final java.lang.Object other$thr = other.thr();
      if (this$thr == null ? other$thr != null : !this$thr.equals(other$thr)) return false;
      final java.lang.Object this$t = this.t();
      final java.lang.Object other$t = other.t();
      if (this$t == null ? other$t != null : !this$t.equals(other$t)) return false;
      final java.lang.Object this$x = this.x();
      final java.lang.Object other$x = other.x();
      if (this$x == null ? other$x != null : !this$x.equals(other$x)) return false;
      final java.lang.Object this$e = this.e();
      final java.lang.Object other$e = other.e();
      if (this$e == null ? other$e != null : !this$e.equals(other$e)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $thr = this.thr();
      result = result * PRIME + ($thr == null ? 43 : $thr.hashCode());
      final java.lang.Object $t = this.t();
      result = result * PRIME + ($t == null ? 43 : $t.hashCode());
      final java.lang.Object $x = this.x();
      result = result * PRIME + ($x == null ? 43 : $x.hashCode());
      final java.lang.Object $e = this.e();
      result = result * PRIME + ($e == null ? 43 : $e.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public K withThr(final Throw thr) {
      return this.thr == thr ? this : new K(thr, this.t, this.x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public K withT(final T t) {
      return this.t == t ? this : new K(this.thr, t, this.x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public K withX(final X x) {
      return this.x == x ? this : new K(this.thr, this.t, x, this.e);
    }

    @java.lang.SuppressWarnings("all")
    public K withE(final E e) {
      return this.e == e ? this : new K(this.thr, this.t, this.x, e);
    }
  }


  public static final class T implements Visitable<T> {
    @Override
    public T accept(CloneVisitor cv) {
      return cv.visitT(this);
    }

    @Override
    public void accept(CollectorVisitor cv) {
      cv.visitT(this);
    }

    @Override
    public String toString() {
      return Constants.toS.apply(this);
    }

    @Override
    public boolean wf() {
      return Constants.wf.test(this);
    }

    private final Mdf _mdf;
    private final List<ST> stz;

    @java.lang.SuppressWarnings("all")
    public T(final Mdf _mdf, final List<ST> stz) {
      this._mdf = _mdf;
      this.stz = stz;
    }

    @java.lang.SuppressWarnings("all")
    public Mdf _mdf() {
      return this._mdf;
    }

    @java.lang.SuppressWarnings("all")
    public List<ST> stz() {
      return this.stz;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public boolean equals(final java.lang.Object o) {
      if (o == this) return true;
      if (!(o instanceof Half.T)) return false;
      final Half.T other = (Half.T) o;
      final java.lang.Object this$_mdf = this._mdf();
      final java.lang.Object other$_mdf = other._mdf();
      if (this$_mdf == null ? other$_mdf != null : !this$_mdf.equals(other$_mdf)) return false;
      final java.lang.Object this$stz = this.stz();
      final java.lang.Object other$stz = other.stz();
      if (this$stz == null ? other$stz != null : !this$stz.equals(other$stz)) return false;
      return true;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public int hashCode() {
      final int PRIME = 59;
      int result = 1;
      final java.lang.Object $_mdf = this._mdf();
      result = result * PRIME + ($_mdf == null ? 43 : $_mdf.hashCode());
      final java.lang.Object $stz = this.stz();
      result = result * PRIME + ($stz == null ? 43 : $stz.hashCode());
      return result;
    }

    @java.lang.SuppressWarnings("all")
    public T with_mdf(final Mdf _mdf) {
      return this._mdf == _mdf ? this : new T(_mdf, this.stz);
    }

    @java.lang.SuppressWarnings("all")
    public T withStz(final List<ST> stz) {
      return this.stz == stz ? this : new T(this._mdf, stz);
    }
  }
}
