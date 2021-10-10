// Generated by delombok at Sun Jun 13 15:12:41 NZST 2021
package is.L42.flyweight;

import static is.L42.tools.General.L;

import java.io.ObjectStreamException;
import java.util.List;
import java.util.Map;

import com.google.common.cache.CacheBuilder;

import is.L42.common.Constants;
import is.L42.perftests.PerfCounters;
import is.L42.visitors.CloneVisitor;
import is.L42.visitors.CollectorVisitor;
import is.L42.visitors.Visitable;

public final class X implements Visitable<X> {
  private static final long serialVersionUID = 2948092384L;
  @Override public X accept(CloneVisitor cv) { return cv.visitX(this); }
  @Override public void accept(CollectorVisitor cv) { cv.visitX(this); }
  @Override public String toString() { return Constants.toS.apply(this); }
  @Override public boolean wf() { return Constants.wf.test(this); }
  private final String inner;
  private static final Map<String, X> created =CacheBuilder.newBuilder().weakValues().<String, X>build().asMap();
  private static void perfCountXOf(String s) {
    PerfCounters.inc("invoke.X.init.total");
    if(!created.containsKey(s)) {
      PerfCounters.inc("invoke.X.init.total.unique");
      }
    }
  public static X of(String s) {
    if(PerfCounters.isEnabled()) { perfCountXOf(s); }
    return created.computeIfAbsent(s, s2->new X(s2));
    }
  Object readResolve() throws ObjectStreamException {
    return of(this.inner);
    }
  private X(final String inner) { this.inner = inner; }
  public static final X thisX = of("this");
  public static final X thatX = of("that");
  public static final List<X> thatXs = L(of("that"));
  public String inner() { return this.inner; }
  public X withInner(final String inner) { 
	return this.inner == inner ? this : of(inner);
    }
}
