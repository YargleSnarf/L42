package is.L42.connected.withSafeOperators.refactor;

import java.util.*;
import java.util.Map;
import java.util.function.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ast.Ast;
import ast.Ast.*;
import ast.ErrorMessage;
import ast.ExpCore.ClassB.*;
import ast.Util.CsPz;
import ast.Util.CsPath;

import ast.ExpCore.ClassB;
import ast.PathAux;
import auxiliaryGrammar.*;
import coreVisitors.From;
import facade.PData;
import is.L42.connected.withSafeOperators.pluginWrapper.*;
import is.L42.connected.withSafeOperators.pluginWrapper.RefactorErrors.RedirectError;
import is.L42.connected.withSafeOperators.pluginWrapper.RefactorErrors.RedirectError.*;
import is.L42.connected.withSafeOperators.refactor.FromedL;
import static is.L42.connected.withSafeOperators.refactor.FromedL.*;

import programReduction.*;
import tools.*;

/*Note the type system prevents to return a class T with T without methods.
This is needed to avoid the redirect from violating metalevel soundness:

 TT:Resource<><{T:{interface} method class T id(class T that){return that}}

 if we was to allow TT to be well typed, the result of

 C:Redirect[\"T" into Any]<><TT()

 would not be well typed: we can not return class Any (is ill typed). Otherwise we may cast
  a class Any obtained thanks to the distinction between tryTyped and tryCohereny
  back to its original Path (that may not be coherent) and call class methods on it.

 */

public class Redirect {
  public static ClassB redirectS(PData pData,ClassB that,String src,ast.Ast.Path dest) throws RefactorErrors.ClassUnfit, RefactorErrors.IncoherentMapping, RefactorErrors.MethodClash, RefactorErrors.PathUnfit{
    return redirectJ(pData,that,PathAux.parseValidCs(src),dest);
    }
  public static ClassB redirectJ(PData pData,ClassB that,List<C> src,ast.Ast.Path dest) throws RefactorErrors.ClassUnfit, RefactorErrors.IncoherentMapping, RefactorErrors.MethodClash, RefactorErrors.PathUnfit{
    assert dest.isCore() || dest.isPrimitive():
      dest;
    return new is.L42.connected.withSafeOperators.refactor.RedirectObj(that).redirect(pData.p,src, dest);
    //return redirect(pData.p, that, new PathMap(List.of(new CsPath(src, dest))));
    }


  static public ClassB applyRedirect(Program p, PathMap map) {
    //TODO: use the new renaming?
    ClassB L = map.apply(p.top());
    L = PathMap.remove(L, map.dom());
    return L;
  }

  enum ClassKind { Final, Interface, Class }

  // To group error handling functions into one place...
  final Error error;
  class Error {
    final boolean detailed;
    Error(boolean detailed) { this.detailed = detailed; }

    void detailed(ListFormatter message) throws DetailedError {
      if (message != null && message.count() > 0) { throw new DetailedError(message.toString()); }}
    ListFormatter formatter(Function<ListFormatter, ListFormatter> lf) { return lf.apply(new ListFormatter()); }

    void accumulate(ListFormatter formatter, Object o) { if (formatter != null) formatter.append(o.toString()); }

    void pathUnfit(ListFormatter formatter, List<C> Cs, String reason) throws PathUnfit {
      if (this.detailed) { formatter.append("Cannot redirect " + PathAux.as42Path(Cs) + ": " + reason); }
      else { throw new PathUnfit(Cs, reason, Redirect.this); }}

    void unredirectable(ListFormatter formatter, List<C> Cs, String reason) throws ClassUnfit {
      if (this.detailed) { formatter.append(reason); }
      else { throw new ClassUnfit(Cs, reason, Redirect.this); }}

    void deductionFailure(ListFormatter message, List<C> Cs) throws DeductionFailure {
      if (!subtypeConstraints.contains(Cs)) {
        if (error.detailed) { message.append("Found no constraints for " + PathAux.as42Path(Cs) + "."); }
        else { throw new DeductionFailure(Cs, "We were unable to collect any constraints on it.", Redirect.this); }
      } else if (!supertypeConstraints.contains(Cs)) {
        var con = printConstraint(Cs);
        if (error.detailed) { message.append("The only constraints we found for " + PathAux.as42Path(Cs) + ", were subtype constraints: " + con + "."); }
        else { throw new DeductionFailure(Cs, "We only found the subtype constraints: " + con + ".", Redirect.this); }}
      else {
        var cons = printConstraint(Cs);
        if (error.detailed) { message.append("Cannot find a most specific solution for " + cons + "."); }
        else {
          var P2 = problem.R._get(Cs);

          if (P2 != null) { // The mapping was given by the user
            // Remove!
            subtypeConstraints.remove(Cs, P2); // Remove the users constraints
            supertypeConstraints.remove(Cs, P2); // Remove the users constraints
            cons = printConstraint(Cs);
            throw new DeductionFailure(Cs, "The given target, " + P2 +", does not satisfy " + cons + ".", Redirect.this); }
          else {
            throw new DeductionFailure(Cs, "Cannot find a most-specific solution to the constraints "
              + cons + ".", Redirect.this); }}}}

    void invalidRedirection(boolean inputCheck, ListFormatter formatter, List<C> Cs, Path P, String reason)
        throws DeductionFailure, InvalidMapping {
      if (this.detailed) { formatter.append(reason); }
      else {
        if (inputCheck) { throw new InvalidMapping(Cs, P, reason, Redirect.this); } // Was the users fault!
        else {throw new DeductionFailure(Cs, "our best guess, " + P + ", failed: " + reason, Redirect.this); }}}

    //<T> T detailed(Supplier<T> f) { return this.detailed ? f.get() : null; }
  }
  static final boolean earlyErrorDetection = false;

  Program p; // The current program we are look at, this may be different from problem.p
  FromedL FL_; // The top of p, in 'frommed' form (i.e. FL.get(Cs) corresponds to p.top()(Cs)[from This0.Cs])
  public final Problem problem; // Everything you need to know about what we are trying to do
  public static class Problem {
    final Program p; // The program we are redirecting in terms of, with the argument to the redirect at the top
    final PathMap R; // The user-provided map, in terms of the above p
    Problem(Program p, PathMap R) { this.p = p; this.R = R; }}

  void debugPrint(String m) { if (!this.error.detailed) System.out.println(m); }

  public Redirect(Problem problem, boolean detailed) {
    this.problem = problem; this.p = problem.p; this.error = new Error(detailed); }

  // Redirect(p; L; Cs1, P1, ..., Csn, Pn) = L'
  static ClassB redirect(Program p0, ClassB L, PathMap R) throws RedirectError {
    var p = p0.evilPush(L);

    // R = Cs1 -> p.minimize(P1[from This1]), ..., Csn -> p.minimize(Pn[from This1])
    R = new PathMap(R.stream().map(
      CsP -> new CsPath(CsP.getCs(), p.minimize(From.fromP(CsP.getPath(), Path.outer(1))))
    ).collect(Collectors.toList()));

    var r = new Redirect(new Problem(p, R), false);

    // Csz = RedirectSet(L; Cs1, ..., Csn)
    // Redirectable(L; Csz)
    // L' = CollectRedirection(p; R)(L[remove Csz])
    return r.redirectTop(); }

  CsPzMap subtypeConstraints, supertypeConstraints;
  Map<List<C>, ClassKind> redirectSet = new HashMap<>();
  public ClassB redirectTop() throws RedirectError {
    // precompute p[Cs] = p(This0.Cs)[from This0.Cs] since we will be using it a lot
    this.FL_ = new FromedL(p, Path.outer(0));

    // foral Cs in dom(R): Cs not empty, p.top()[Cs] is defined
    if (earlyErrorDetection) { checkCsValid(this.problem.R.dom()); }

    // this.redirectSet = RedirectSet(L; dom(problem.R))
    // Redirectable(L; this.redirectSet)
    computeRedirectSet(); // Pre-compute the redirect set, mustClass and mustInterface

    if (earlyErrorDetection) { validRedirection(this.problem.R); }

    // R = CollectRedirection(p; problem.R)
    var R = collectRedirection();
    //assert LambdaExceptionUtil.catchThrow(() -> validRedirection(R)) == null;

    //  R(L[remove this.redirectSet])
    assert this.redirectSet.keySet().equals(R.dom());
    return R.apply(PathMap.remove(this.p.top(), this.redirectSet.keySet())); }

  // CollectRedirection(this.p; this.problem.R)
  PathMap collectRedirection() throws DetailedError, DeductionFailure, InvalidMapping {
    // CollectConstraints(this.p; this.problem.R) = CCz
    this.subtypeConstraints = new CsPzMap(this.problem.R.toList()) {
      @Override String format(String Cs, String Pz) { return Cs + " <= " + Pz; }};
    this.supertypeConstraints = new CsPzMap(this.subtypeConstraints) {
      @Override String format(String Cs, String Pz) { return Pz + " <= " + Cs; }};
    collectAll(); // CCz = CollectAll()

    // R = CollectSolution(p; CCz)
    var Rz = collectAllSolutions().collect(Collectors.toList());
    for (var R : Rz) { validRedirection(R); }

    var R = collectSolution();
    debugPrint("Chosen R was: " + R);
    return R; }

  void checkCsValid(Collection<List<C>> Csz) throws DetailedError, PathUnfit {
    var errors = error.formatter(l -> l.header("Input to redirect is invalid:\n").prefix("  ").suffix("\n"));
    for (var Cs : Csz) {
      if (Cs.isEmpty()) {
        error.pathUnfit(errors, Cs, "it is empty."); continue; }
      if (Cs.stream().anyMatch(C::isUnique)) {
        error.pathUnfit(errors, Cs, "it is private."); continue; }

      if (this._get(Cs) == null) { error.pathUnfit(errors, Cs, "it does not exist."); }}

    error.detailed(errors); }

  // Redirectable(L; Csz)
  void redirectable(ListFormatter message, List<List<C>> Csz) throws RedirectError.ClassUnfit {
    // forall Cs in Csz
    for (var Cs : Csz) { //forall Cs in Csz
      // L[Cs] is defined
      if (this.redirectSet.get(Cs) == null) {
        error.unredirectable(message, Cs, "Class does not exist."); continue; }

      // Cs not empty
      // forall C in Cs: not Private(Cs)
      // forall mwt in L.mwtz: mwt.e = empty && not Private(mwt.m)
      redirectableBody(message, Cs, this.get(Cs));
      redirectableNesteds(message, Cs, this.get(Cs)); }} // forall C in dom(L): Cs.C in Csz
  // forall C in dom(L): Cs.C in Csz
  void redirectableNesteds(ListFormatter message, List<C> Cs, FromedL L) throws ClassUnfit {
    // C in dom(L[Cs]): Cs.C in Csz
    var errors = error.formatter(l -> l.header("Cannot completely redirect " + PathAux.as42Path(Cs) + ":\n").prefix("    ").suffix("\n"));
    for (var C : L.cDom()) {
      if (!this.redirectSet.containsKey(withAdd(Cs, C))) {
        error.unredirectable(errors, Cs, "It contains a nested class " + C + ", which is not in the redirect set."); }}
    error.accumulate(message, errors); }

  // redirectableBody(Cs; L)
  void redirectableBody(ListFormatter message, List<C> Cs, FromedL L) throws ClassUnfit {
    var errors = error.formatter(l -> l.header("Cannot redirect " + PathAux.as42Path(Cs) + ":\n").prefix("    ").suffix("\n"));

    if (Cs.isEmpty()) { // Cs not empty
      error.unredirectable(errors, Cs, "It refers to the whole library literal!"); }

    if (Cs.stream().anyMatch(C::isUnique)) { // forall C in Cs: not Private(Cs)
      error.unredirectable(errors, Cs, "It is private."); }

    for (var mwt : L.mwtz()) { // forall mwt in L[Cs].mwtz:
      var ms = mwt.getMs();
      if (ms.isUnique()) { // not Private(mwt.m)
        error.unredirectable(errors, Cs, "It contains a private method: " + ms + "."); }
      if (mwt.get_inner() != null) { // mwt.e = empty
        error.unredirectable(errors, Cs, "It contains a non abstract method: " + ms + "."); }}

    error.accumulate(message, errors); }

  // this.redirectSet = RedirectSet(L; dom(problem.R))
  // Redirectable(L; this.redirectSet)
  void computeRedirectSet() throws DetailedError, ClassUnfit {
    var message = error.formatter(l -> l.header("Requested redirect is invalid:\n").prefix("  ").suffix("\n"));
    var todo = new ArrayList<>(this.problem.R.dom()); // Csz subseteq RedirectSet(L; Csz)
    var done = new ArrayList<List<C>>(); // Just to ensure determinism of error messages

    while (!todo.isEmpty()) {
      var Cs = todo.get(0);
      todo.remove(0);
      done.add(Cs);
      var L = earlyErrorDetection ? this.get(Cs) : this._get(Cs);
      if (L == null) { redirectSet.put(Cs, null); continue; }

      // MustInterface(p; Cs) = p(Cs).interface? = interface'
      // MustClass(p; Cs) = p(Cs).interface? = empty and class in p(Cs).mwtz.mdfZ
      ClassKind kind = L.isInterface() ? ClassKind.Interface
        : L.mwtz().stream().anyMatch(mwt -> mwt.getMdf().isClass()) ? ClassKind.Final
        : ClassKind.Class;

      redirectSet.put(Cs, kind);

      // Cs not empty
      // forall C in Cs: not Private(Cs)
      // forall mwt in L.mwtz: mwt.e = empty && not Private(mwt.m)
      redirectableBody(message, Cs, L); // Redirectable(L; Cs)

      // Internals(Reachables(L[Cs])) subseteq RedirectSet(L; Csz)
      //  Cs in RedirectSet(L; Csz)
      internals(L.reachables()).forEach(Cs1 -> { if (!redirectSet.containsKey(Cs1)) todo.add(Cs1); });
    }

    if (earlyErrorDetection) {
      // forall Cs in Csz: forall C in dom(L[Cs]): Cs.C in Csz
      for (var Cs : done) { redirectableNesteds(message, Cs, this.get(Cs)); }}
    else { redirectable(message, done); }

    error.detailed(message); }

  boolean addConstraint(CsPzMap map, List<C> key, Path value, String message) {
    var res = map.add(key, value);
    if (res) this.debugPrint(message + " ==> " + map.format(PathAux.as42Path(key), value.toString()));
    return res; }
  boolean addSupertype(List<C> key, Path value, String message) { return addConstraint(this.supertypeConstraints, key, value, message); }
  boolean addSubtype(List<C> key, Path value, String message) { return addConstraint(this.subtypeConstraints, key, value, message); }
  void collectAll() {
    debugPrint("\nStart: " + this.printConstraints());
    boolean progress; // Have we done something?
    do {
      progress = false;
      //1: Collect(p; Cs <= P) = P <= Cs
      //   p[P].interface? = empty
      for (var CsP : subtypeConstraints) { // Cs <= P
        if (!this.get(CsP.getPath()).isInterface()) { // p[P].interface? = empty
          progress |= addSupertype(CsP.getCs(), CsP.getPath(), "Rule 1: " + asSubtype(CsP)); }} // P <= Cs

      //3: Collect(p; P <= Cs) = MostSpecific(p; Pz) <= Cs', Cs' <= MostGeneral(p; Pz)
      //   This0.Cs' in p[Cs].Pz
      //   Pz = {P' in SuperClasses(p; P) | msdom(p[P') = msdom(p[Cs'])}
      for (var CsP : supertypeConstraints) { // P <= Cs
        for (var PCs2 : this.get(CsP.getCs()).Pz()) { // P in p[Cs].Pz
          var Cs2 = internal(PCs2); // P = This0.Cs'
          if (Cs2 == null) { continue; }

          var Pz = superClasses(CsP.getPath()).stream().filter(Pi -> possibleTarget(Cs2, Pi))
            .collect(Collectors.toSet()); // px = {P' in SuperClasses(p; P) | msdom(p[P']) = msz}
          var P1 = _mostSpecific(Pz); // P1 = MostSpecific(p; Pz)
          var P2 = _mostGeneral(Pz); // P2 = MostGeneral(p; Pz)

          if (P1 != null) { // P1 <= Cs'
            progress |= addSupertype(Cs2, P1, "Rule 3a: " + asSupertype(CsP)); } // TODO?
          if (P2 != null) { // Cs' <= P2
            progress |= addSubtype(Cs2, P2, "Rule 3b: " + asSupertype(CsP)); }

          progress |= addSupertype(Cs2, CsP.getPath(), "Rule 3d: " + asSupertype(CsP));

          /*for (var P3 : subtypeConstraints.get(Cs2)) { // Cs' <= P" // TODO: Proove this is neccessary
            var CsP3 = new CsPath(Cs2, P3); //Cs <= P", P <= Cs'
            progress |= addSubtype(CsP.getCs(), P3, "Rule 3c: " + asSupertype(CsP) + ", " + asSubtype(CsP3)); }*/}}

      //4: Collect(p; P <= Cs) = p[P.ms].P <= Cs'
      //   p[Cs.ms].P = This0.Cs'
      for (var CsP : supertypeConstraints) { // P <= Cs
        progress |= collectReturns(CsP, (Cs, P, ms) -> addSupertype(Cs, P,
          "Rule 4: " + asSupertype(CsP) + " [" + ms + "]"));} // p[P.ms].P <= p[Cs.ms].P.Cs

      //5: Collect(p; CC) = Cs' <= p[P.ms].Pi
      //   CC = P <= Cs or  CC = Cs <= P
      //   p[Cs.ms].Pi = This0.Cs'
      for (var CsP : seqIterate(supertypeConstraints, subtypeConstraints)) { // P <= Cs or Cs <= P
        // p[Cs.ms].Pi.Cs <= p[P.ms].Pi
        progress |= collectParams(CsP, (Cs, P, ms, i) -> addSubtype(Cs, P,
            "Rule 5: " + asReltype(CsP) + " [" + ms + "." + i + "]"));}

      //6: Collect(p; Cs <= P) = Cs' <= p[P.ms].P:
      //   MustInterface(p; Cs)
      //   p[Cs.ms].P = This0.Cs'
      for (var CsP : subtypeConstraints) { // Cs <= P
        if (mustInterface(CsP.getCs())) { // MustInterface(CsP.Cs)
          // p[Cs.ms].P.Cs <= p[P.ms].P
          progress |= collectReturns(CsP, (Cs, P, ms) -> addSubtype(Cs, P,
            "Rule 6: " + asSubtype(CsP) + " [" + ms + "]"));}}

      //7: Collect(p; Cs <= P) = p[P.ms].Pi <= Cs':
      //   MustInterface(p; Cs)
      //   p[Cs.ms].Pi = This0.Cs'
      for (var CsP : subtypeConstraints) { // Cs <= P
        if (mustInterface(CsP.getCs())) { // MustInterface(CsP.Cs)
          progress |= collectParams(CsP, (Cs, P, ms, i) -> addSupertype(Cs, P,
              "Rule 7: " + asSubtype(CsP) + " [" + ms + "." + i + "]"));}} // p[P.ms].Pi <= p[Cs.ms].Pi.Cs

      //8/8': Collect(p; Cs <= P, CCz) = CCz'
      for (var CsP : subtypeConstraints) { // Cs <= P
        TriPredicate<List<C>, Path, String> body = (Cs2, P2, message) -> {
          boolean progress2 = false;

          // 8a/8'a
          if (!get(P2).isInterface()) { // p[P'].interface?=empty and
            progress2 |= addSupertype(Cs2, P2, message); } // CC = P' <= Cs'

          // 8d/8'd
          for (var ms : this.get(Cs2).msDom()) { // or ms' in dom(p[Cs'])
            var P3 = _origin(ms, P2); // P'' = Origin(p; sel'; P')
            if (P3 != null) {
              progress2 |= addSubtype(Cs2, P3, message + " [" + ms + "]");}}  // CC = Cs' <= P''

          return progress2;};

        // 8: This0.Cs' = p[Cs.sel].P
        //    P' = p[P.sel].P
        progress |= collectReturns(CsP, (Cs, P, ms) -> body.test(Cs, P, "Rule 8: " + asSubtype(CsP) + " [" + ms + "]"));

        // TODO: Proove this is neccessary?
        // 8': This0.Cs' in p[Cs].Pz
        //     P' in SuperClass(p; P)
        /*for (var P2 : iterate(this.fromPz(toP(CsP.getCs())))) { // P' in p[Cs].Pz
          if (P2.tryOuterNumber() == 0) { // P' = This0.Cs'
            for (var P3 : superClasses(CsP.getPath())) { // P'' in SuperClass(p; P)
              progress |= body.test(P2.getCBar(), P3, "Rule 8': " + asSubtype(CsP)); }}}*/}

      //11a and 11b
      // TODO: Proove this is neccessary?
      for (var Cs : supertypeConstraints.dom()) { // RChoices is only defined for things in superTypeConstraints
        var Pz = _collectTargets(Cs);
        // TODO: If Pz is empty, we can produce an error now!
        if (Pz.size() == 1) { // 11a: RChoices(CCz) = Cs -> {P} ==> Cs <= P
          progress |= addSubtype(Cs, Pz.iterator().next(), "Rule 11a: " + printConstraint(Cs)); }
        // 11b: RChoices(CCz) = Cs -> Pz ==> MostSpecific(p; Pz) <= Cs
        var P = _mostSpecific(Pz);
        if (P != null) {
          progress |= addSupertype(Cs, P, "Rule 11b: " + printConstraint(Cs)); }}}

    // Keep going until we stop doing anything
    while (progress);
    debugPrint("End: " + this.printConstraints() + "\n"); }

  // collectTargets(p; P1 <= Cs, ..., Pn <= Cs, Cs <= P'1, ..., Cs <= P'k, CCz; Cs) = Pz''
  Set<Path> _collectTargets(List<C> Cs) {
    var Ps1 = this.supertypeConstraints.get(Cs); // Ps1 = P1, ..., Pn
    var Ps2 = this.subtypeConstraints.get(Cs); // Ps2 = P'1, ..., P'k
    var Pz = _superClasses(Ps1); // Pz = SuperClasses(p; P1, ..., Pn)
    if (Pz == null) { return null; } // SuperClasses was undefined

    //Pz' = {P in Pz | {P'1, ..., P'k} subseteq SuperClasses(p; P)}
    var Pz2 = Pz.stream().filter(P -> superClasses(P).containsAll(Ps2)).collect(Collectors.toSet());

    // Pz'' = {P in Pz' | PossibleTarget(p; Cs; P)}
    var res = Pz2.stream().filter(P -> possibleTarget(Cs, P)).collect(Collectors.toSet());
    return res; }
//Doc doc1, boolean isInterface, List<Type> supertypes, List<Member> ms, Position p, Phase phase, int uniqueId
  // Creates new things and adds the to the program, in case targets fails,
  Set<Path> createTargets(List<C> Cs) throws DeductionFailure {
    var res = this._collectTargets(Cs);
    if (res != null) {return res; }

    // we need to construct an aribtrary L such that:
    //    it satisfies the constraints for Cs (this.subtypeConstraints)
    //    it satisfies PossibleTarget for Cs
    // In particular, we are not allowed to look at the constraints of anything other than Cs!

    var supers = new ArrayList<>(this.subtypeConstraints.get(Cs));
    supers.addAll(this.get(Cs).Pz().stream().filter(P -> !isInternal(P)).collect(Collectors.toList()));

    //var L = this.p.top().getClassB(Cs);
    /*L.withMs(L.mwts().stream().map(


    ));*/

    // simple really?
    // If their are no bounds, it means that it is ineachable
    // We will then modify it accordingly,

    /*

    )*/


    // Create the thing, it needs at least the supertypes specified by the constraints, and those needed by possibleTarget
    var L = new ClassB(Doc.factory(false, "GENERATED BULLSHIT!"),
      this.redirectSet.get(Cs).equals(ClassKind.Interface),
      supers.stream().map(Type::of).collect(toList()), List.of(), Position.noInfo, Phase.None, -2);
    L = new Norm().norm(p.updateTop(L)); // Hopefully this is the right function to do a normalisation
    // Now add any extra methods and changes types, as needed by updateTop
    // (note: if it's an allready existing method we are only allowed to refine it,
    // if we can't refine it, this means that their is no-possible solution!)

    var mwtz = new ArrayList<Member>(L.mwts().stream().filter(mwt -> !this.get(Cs).msDom().contains(mwt.getMs()))
      .collect(toList()));

    for (var mwt : this.get(Cs).mwtz()) {
      var mwt2 = L._getMwt(mwt.getMs());
      if (mwt2 == null) {
        assert internals(reachables(mwt)).isEmpty();
        mwtz.add(mwt); // leave it untouched, it should trivially satisfy PossibleTargets
      } else {
        // The method came from an interface, as such we have to refine it, and can't just make a new one
        for (int i = 0; i < mwt.getPaths().size(); i++) {
          // Due to the inability to refine paramater types on interfaces, we have to fail here
          // (Note: this means that no such L can exist, weras the assertions above mean that we can't construct an L arbitrarily)
          var T1 = mwt.getMt().getTs().get(i);
          var T2 = mwt2.getMt().getTs().get(i);
          if (!partialSubtype(T1, T2)) { throw new DeductionFailure(Cs, "impossible paramater types", this); }
        }

        // Technically any common subtype between P1 and P2 will do
        // However, that may neccesasitate creating a new type in the program,
        // Since I'm too lazy
        var T1 = mwt.getReturnType();
        var T2 = mwt2.getReturnType();
        var mdf = commonSubMdf(T1.getMdf(), T2.getMdf());
        T1 = T1.withMdf(mdf);
        T2 = T2.withMdf(mdf);

        Type T3 = null; // the new type!
        // T1 is a class, so we have to return it, we have no choice

        if (partialSubtype(T1, T2)) { // T1 is allready ok!
          T3 = T1; }

        else if (partialSubtype(T2, T1)) { // T2 is allready ok!
          T3 = T1; }

        // We have to create a new type that implements T1 and T3
        else {
          T3 = Type.of(addL(new Norm().norm(p.updateTop(new ClassB(Doc.factory(false, "GENERATED SUBTYPE!"), false,
            List.of(T1.withMdf(Mdf.Immutable), T2.withMdf(Mdf.Immutable)), List.of(), Position.noInfo, Phase.None, -2)))));}
        mwtz.add(mwt2.withMt(mwt2.getMt().withReturnType(T3)));
        assert mwt.getExceptions().isEmpty() : "UNIMPLEMENTED"; }
    }

    // Ok, now it should be a PossibleTarget!
    return Set.of(addL(L.withMs(mwtz))); }

  // Adds L and returns a path referencing it!
  static int n = 0;

  // This function has way too many assumptions:
  //  "Fresh" + n is a fresh name
  //  L does not contain This0.Cs
  //  L has no implemented methods
  //  L has no nested classes
  //  p is equiavlent to p.pop().evilPush(p.top())
  Path addL(ClassB L) { // let's hope that p was evil pushed, I don't know how to check though...
    var L2 = p.pop().top(); // This1
    var newC = C.of("Fresh" + n++);
    var Mz2 = new ArrayList<>(L2.getMs());
    Mz2.add(new NestedClass(Doc.empty(), newC,
      new FromedL(L, Path.outer(0, List.of(C.of("EVIL_PUSHED")))).asClassB(), Position.noInfo));
    // replace This1 with our now thing
    p = p.pop().updateTop(L2.withMs(Mz2)).evilPush(p.top());
    return Path.outer(1, List.of(newC)); }

  Mdf commonSubMdf(Mdf a, Mdf b) {
    if (a.equals(b)) { return a; }
    assert false : "NOT IMPLEMENTED";
    return null; }

  Stream<PathMap> collectAllSolutions(PathMap R, List<List<C>> Csz) throws DeductionFailure {
    if (Csz.isEmpty()) { return Stream.of(R); }
    var Cs = Csz.get(0);
    var Csz2 = Csz.subList(1, Csz.size());
    var Pz = createTargets(Cs);
    return Pz.stream().flatMap(LambdaExceptionUtil.rethrowFunction(P -> {
      var R2 = new PathMap(R);
      R2.add(Cs, P);
      return collectAllSolutions(R2, Csz2);
    }));}

  Stream<PathMap> collectAllSolutions() throws DeductionFailure {
    return collectAllSolutions(new PathMap(), List.copyOf(this.redirectSet.keySet())); }

  // collectSolution(p; CCz)
  PathMap collectSolution() throws DetailedError, DeductionFailure {
    var message = error.formatter(l -> l.header("Failed to choose mapping:\n").prefix("  ").suffix("\n"));
    PathMap res = new PathMap(/*this.problem.R*/);

    // assert dom(CCz) <= redirectSet
    assert this.redirectSet.keySet().containsAll(subtypeConstraints.dom());
    assert this.redirectSet.keySet().containsAll(supertypeConstraints.dom());

    // Cs in redirectSet(p.top(), dom(R))
    for (var Cs : this.redirectSet.keySet()) {
      var Pz = _collectTargets(Cs); // Pz = CollectTargets(p; CCz; Cs)
      if (Pz == null) { error.deductionFailure(message, Cs); continue; }

      var P = _mostSpecific(Pz); // Pi = MostSpecific(p; Pz)
      if (P == null) { error.deductionFailure(message, Cs); continue; }
      res.add(Cs, P);
    }

    error.detailed(message);
    return res; }

  // Otherwise, throw an AlgorithmError
  void validRedirection(PathMap R) throws DetailedError, DeductionFailure, InvalidMapping {
    var message = error.formatter(l -> l.header("Chosen redirect is invalid:\n").prefix("  ").suffix("\n"));
    // Check structural subtyping (including NC consistency)
    var inputCheck = R == this.problem.R;

    for (var CsP : R) {
      var P = CsP.getPath();
      var Cs = CsP.getCs();
      ClassKind ck = redirectSet.get(Cs);

      var errors = error.formatter(l -> l.header("Cannot redirect " + PathAux.as42Path(Cs) + " to " + P + ":\n")
        .prefix("    ").seperator("\n"));

      assert p.minimize(P).equals(P);
      assert P.tryOuterNumber() != 0;

      var L1 = R.apply(this.get(Cs).asClassB());
      var L2 = this.get(P);

      // No need to check for well-typedness of L2, as 42 gurantees this for any 'class Any' given as input is (transitivley) well-typed

      // p|- P; L2 <= Cs; L1
      var Pz1 = p.minimizeSet(L1.getSuperPaths().stream().filter(x -> !isInternal(x)));
      var Pz2 = superClasses(P);
      if (!Pz2.containsAll(Pz1)) { //Pz subseteq_p SuperClasses(p; P)
        var Pz11 = new HashSet<>(Pz1);
        Pz11.removeAll(Pz2);
        error.invalidRedirection(inputCheck, errors, Cs, P, "Target is not a subclass of " + PathAux.asSet(Pz11)); }

      // Check class compatability
      if (ck.equals(ClassKind.Final) && L2.isInterface()) {
        error.invalidRedirection(inputCheck, errors, Cs, P, "Cannot redirect a final class, with class methods, to an interface."); }
      if (ck.equals(ClassKind.Interface) && !L2.isInterface()) {
        error.invalidRedirection(inputCheck, errors, Cs, P, "Cannot redirect an interface to a final class"); }

      //forall MS in dom(mwtz): p |- mwtz'(MS).mt <= mwt(MS).mt
      for (var mwt1 : L1.mwts()) {
        var mwt2 = L2._get(mwt1.getMs());
        if (mwt2 == null) {
          error.invalidRedirection(inputCheck, errors, Cs, P, "Target does not contain method " + mwt1.getMs() + ".");
          continue; }
        if (!partialMethSubType(mwt2.getMt(), mwt1.getMt())) {
          error.invalidRedirection(inputCheck, errors, Cs, P, "The method type for " + mwt1.getMs() + " of the target (" + mwt2.getMt() +
          ") is not a subtype of the source (" + mwt1.getMt() + ")."); }}

      // mwtz[with refine?s=empty] = mwtz'[with refine?s=empty]
      // (implied by the above check together with this one)
      if (ck.equals(ClassKind.Interface)) {
        for (var mwt2 : L2.mwtz()) {
          var mwt1 = L1._getMwt(mwt2.getMs());
          if (mwt1 == null) {
            error.invalidRedirection(inputCheck, errors, Cs, P, "Source does not contain method " + mwt2.getMs());
            continue; }
          if (!partialMethSubType(mwt1.getMt(), mwt2.getMt())) {
            error.invalidRedirection(inputCheck, errors, Cs, P, "The method type for " + mwt2.getMs() + " of the target (" + mwt2.getMt() +
            ") isn't a supertype of the source (" + mwt1.getMt() + ")."); }}}

    error.accumulate(message, errors); }
    error.detailed(message); }

  boolean possibleTarget(List<C> Cs, Path P) {
    var kind = redirectSet.get(Cs);
    assert kind != null;
    assert p.minimize(P).equals(P);

    var L1 = this.get(Cs);
    var L2 = this.get(P);
    var ck = this.redirectSet.get(Cs);

    if (ck.equals(ClassKind.Final) && L2.isInterface()) { return false; }
    if (ck.equals(ClassKind.Interface) && !L2.isInterface()) { return false; }

    if (!L2.msDom().containsAll(L1.msDom())) { return false; }
    if (ck.equals(ClassKind.Interface) && !L1.msDom().containsAll(L2.msDom())) { return false; }

    for (var mwt : L1.mwtz()) {
      // We know this exists, thanks to the above code
      var mwt2 = L2._get(mwt.getMs());
      if (!partialMethSubType(mwt2.getMt(), mwt.getMt())) { return false; }
      if (ck.equals(ClassKind.Interface) && !partialMethSubType(mwt.getMt(), mwt2.getMt())) { return false; }}
    var res = superClasses(P).containsAll(p.minimizeSet(L1.Pz().stream().filter(x -> !isInternal(x))));
      return res; }

  Path _mostSpecific(Set<Path> Pz) {
    Pz = p.minimizeSet(Pz.stream());
    // TODO: This is horribly inefficient...
    for (Path P : Pz) {
      if (superClasses(P).containsAll(Pz)) { return P; }}

    return null;}

  Path _mostGeneral(Set<Path> Pz) {
    Pz = p.minimizeSet(Pz.stream());
    // TODO: This is horribly inefficient...
    if (!Pz.isEmpty()) {
      var res = superClasses(Pz);
      res.retainAll(Pz);
      if (res.size() == 1) { return res.iterator().next(); } }

    return null;}


  boolean partialSubtype(Type sub, Type sup) {
    if (sub.getPath().tryOuterNumber() != 0 && sup.getPath().tryOuterNumber() != 0) { return p.subtypeEq(sub, sup); }
    else { return Functions.isSubtype(sup.getMdf(), sub.getMdf()); }}

  boolean partialMethSubType(MethodType sub, MethodType sup) {
    assert sub.getTs().size() == sup.getTs().size();

    return Functions.isSubtype(sup.getMdf(), sub.getMdf())
      && partialSubtype(sub.getReturnType(), sup.getReturnType())
      && IntStream.range(0, sub.getTs().size()).allMatch(i -> partialSubtype(sup.getTs().get(i), sub.getTs().get(i)))
      && sub.getExceptions().stream().allMatch(T1 -> sup.getExceptions().stream().anyMatch(T2 -> partialSubtype(T1, T2)));}

  boolean mustInterface(List<Ast.C> Cs) { return this.redirectSet.get(Cs) == ClassKind.Interface; }

  // collectReturns(Cs P, f) computes f(Cs', P'), such that
  //    This0.Cs' = p[Cs](ms).P
  //    P' = p[P](ms).P
  // and returns true if any of the calls to f did
  boolean collectReturns(CsPath CsP, TriPredicate<List<C>, Path, MethodSelector> f) {
    boolean progress = false;
    for (var mwt : this.get(CsP.getCs()).mwtz()) {
      var P2 = mwt.getReturnPath(); // P2 = mwt.P
      var mwt2 = this.get(CsP.getPath())._get(mwt.getMs()); // mwt2? = p[P](mwt.ms)
      if (P2.tryOuterNumber() == 0 && mwt2 != null) { // P2 = This0.Cs', mwt2? != empty
        progress |= f.test(P2.getCBar(), mwt2.getReturnPath(), mwt.getMs()); }} //f(P2.Cs, mwt2.P)
    return progress; }

  // collectParams(Cs P, f) same as collectReturns, but for each paramater type instead
  boolean collectParams(CsPath CsP, QuadPredicate<List<C>, Path, MethodSelector, Integer> f) {
    boolean progress = false;
    for (var mwt : this.get(CsP.getCs()).mwtz()) {
      for (int i = 0; i < mwt.getSize(); i++) { // i in 0..#(mwt.ms.xs)
        var P2 = mwt.getPaths().get(i); // P2 = mwt.Pi
        var mwt2 = this.get(CsP.getPath())._get(mwt.getMs()); // mwt2? = p[P](mwt.ms)
        if (P2.tryOuterNumber() == 0 && mwt2 != null) { // P2 = This0.Cs', mwt2? != empty
          progress |= f.test(P2.getCBar(), mwt2.getPaths().get(i), mwt.getMs(), i); }}} //f(P2.Cs, mwt2.Pi)
    return progress; }

  // Utilities, not directly related to redirect

  // iterates over the first, and then the second collection
  static <T> Iterable<T> seqIterate(Iterable<T> first, Iterable<T> second) {
    return () -> new Iterator<T>() {
        Iterator<T> inner = first.iterator();
        boolean firstHalf = true;

        void update() { inner = second.iterator(); firstHalf = false; }
        @Override public boolean hasNext() {
          var res = this.inner.hasNext();
          if (!res && firstHalf) {
            this.update();
            res = this.inner.hasNext();
          }
          return res; }
        @Override public T next() {
          if (firstHalf) {
            try { return inner.next(); }
            catch (NoSuchElementException e) { update(); } }
          return inner.next(); }};}

  static <T> Set<T> intersect(Stream<Collection<T>> s) {
      Set<T> res = null;
      for (var set : iterate(s)) {
        if (res == null) { res = new HashSet<>(set); }
        else { res.retainAll(set); }}
      return res; }

  static <T> Iterable<T> iterate(Stream<T> s) { return s::iterator; }

  FromedL _get(List<C> Cs) { return this.FL_._get(Cs); }
  FromedL get(List<C> Cs) { return this.FL_.get(Cs); }
  FromedL get(Path P) {
    var Cs = internal(P);
    return Cs != null ? get(Cs) : new FromedL(p, P); }
  //ClassB get(Path P)
  // Makes a Path from a Cs
  static Path toP(List<Ast.C> Cs) { return Path.outer(0, Cs); }

  /*Origin(p; sel; P) = P'
    forall P'' in Supertypes(p; P) where sel in dom [P'']
       p |- P'' <= P'*/
  Path _origin(MethodSelector ms, Path P) {
    var L = this.get(P);
    var mwt = L._get(ms);
    if (mwt == null) { return null; }
    if (!mwt.getMt().isRefine()) { return L.P; }

    for (var P2 : L.Pz()) {
      var mwt2 = get(P2)._get(ms);
      if (mwt2 != null && !mwt2.getMt().isRefine()) {
        return P2; }}

    throw Assertions.codeNotReachable(); }
  Set<Path> superClasses(List<C> Cs) { return superClasses(toP(Cs)); }
  Set<Path> superClasses(Path P) { return p.minimizeSet(Stream.concat(get(P).Pz().stream(), Stream.of(P, Path.Any()))); }

  //SuperClasses(p; Pz) = intersect {p.minimize(p[P].Pz U {P, Any}) | P in Pz}
  Set<Path> superClasses(Collection<Path> Pz) { return Objects.requireNonNull(_superClasses(Pz)); }
  Set<Path> _superClasses(Collection<Path> Pz) { return intersect(Pz.stream().map(this::superClasses)); }

  // For debuging
  String asSubtype(CsPath csp) { return PathAux.as42Path(csp.getCs()) + " <= " + csp.getPath(); }
  String asSupertype(CsPath csp) { return csp.getPath() + " <= " + PathAux.as42Path(csp.getCs()); }
  String asReltype(CsPath csp)  { return PathAux.as42Path(csp.getCs()) + " <=> " + csp.getPath(); }
  String asSametype(CsPath csp)  { return PathAux.as42Path(csp.getCs()) + " == " + csp.getPath(); }
  String printConstraints() {
    return new ListFormatter().seperator(", ").append(this.redirectSet.keySet().stream().map(this::printConstraint))
        .toString();
  }
  String printConstraint(List<C> Cs) {
    var sub = supertypeConstraints.get(Cs);
    var sup = subtypeConstraints.get(Cs);
    if (sub.isEmpty() && sup.isEmpty()) {
      return ""; }
    else {
      if (sub.isEmpty()) {
        return PathAux.as42Path(Cs) + " <= " + PathAux.asSet(sup); }
      else {
        if (sup.isEmpty()) {
          return PathAux.asSet(sub) + " <= " + PathAux.as42Path(Cs); }
        else {
          return PathAux.asSet(sub) + " <= " + PathAux.as42Path(Cs) + " <= " + PathAux.asSet(sup);}}}}}

class CsPzMap implements Iterable<CsPath> {
  // Cs |-> Pz
  // (Cs, P)z
  private Map<List<C>, Set<Path>> map = new HashMap<>();

  @Override public String toString() {
    return this.mappings().stream().map(
        CsPz -> this.format(PathAux.as42Path(CsPz.getCs()), PathAux.asSet(CsPz.getPathsSet()))

    ).collect(Collectors.joining(", ")); }

  String format(String Cs, String Pz) { return Cs + "->" + Pz; }
  CsPzMap() { }
  CsPzMap(CsPzMap other) {this.map = new HashMap<>(other.map); }
  CsPzMap(List<CsPath> CsPz) { this(); for (var CsP : CsPz) { this.add(CsP); } }
  // Returns true iff the key, value pair wasn't allready present
  boolean add(CsPath keyValue) { return this.add(keyValue.getCs(), keyValue.getPath()); }

  boolean remove(List<C> key, Path value) {
    return this.update(key, () -> null, Pz -> Pz.contains(value), Pz -> Pz.remove(value)); }

  // Tries to update the value corresponding to key:
  //    If their was no value, and def() returns one, add that to the map
  //    Otherwise if condition holds on the current value, perform op
  //    If op failed, perform op on a copy of the original value
  // Returns true if the map was modified (under the assumption that 'op' will modify it's argument)
  private boolean update(List<C> key, Supplier<Set<Path>> def, Predicate<Set<Path>> condition, Consumer<Set<Path>> op) {
    var set = this.map.get(key);
    if (set == null) {
      var res = def.get();
      if (res == null || res.isEmpty()) { return false; }
      this.map.put(key, res);
      return true; }
    if (!condition.test(set)) { return false; }
    try { op.accept(set); }
    catch (UnsupportedOperationException __) {
      var res = new HashSet<>(set); op.accept(res); this.map.put(key, res); }
    return true; }

  public boolean add(List<C> key, Path value) {
    assert value.tryOuterNumber() != 0;
    return this.update(key, () -> Collections.singleton(value), Pz -> !Pz.contains(value), Pz -> Pz.add(value)); }

  public boolean contains(List<C> key, Path value) { return this.get(key).contains(value); }
  public boolean contains(List<C> key) { return !this.get(key).isEmpty(); }

  public Set<CsPz> mappings() {
    return this.map.entrySet().stream().map(e -> {
      assert !e.getValue().isEmpty();
      return new CsPz(e.getKey(), e.getValue());}
    ).collect(Collectors.toSet()); }

  public Set<Path> get(List<C> key) { return this.map.getOrDefault(key, Collections.emptySet()); }
  public Set<List<C>> dom() { return this.mappings().stream().map(CsPz::getCs).collect(Collectors.toSet()); }
  public Stream<CsPath> stream() {
    return this.mappings().stream().flatMap(e -> e.getPathsSet().stream().map(P -> new CsPath(e.getCs(), P))); }

  public Set<CsPath> values() { return this.stream().collect(Collectors.toSet()); }
  @Override public Iterator<CsPath> iterator() {
    // Who cares about performance anyway? What's more important is that I want to be able to iterate over something while modifying it!
    return this.values().iterator(); } }

@FunctionalInterface interface TriPredicate<T, U, V> { boolean test(T t, U u, V V); }
@FunctionalInterface interface QuadPredicate<T, U, V, W> { boolean test(T t, U u, V v, W w); }