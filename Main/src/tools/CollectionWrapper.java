// Generated by delombok at Mon Nov 26 09:57:26 NZDT 2018
package tools;

import java.util.Collection;

public abstract class CollectionWrapper<T> implements Collection<T> {
  protected abstract Collection collection();

  @java.lang.SuppressWarnings("all")
  public int size() {
    return this.collection().size();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEmpty() {
    return this.collection().isEmpty();
  }

  @java.lang.SuppressWarnings("all")
  public boolean contains(final java.lang.Object arg0) {
    return this.collection().contains(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public java.util.Iterator iterator() {
    return this.collection().iterator();
  }

  @java.lang.SuppressWarnings("all")
  public java.lang.Object[] toArray() {
    return this.collection().toArray();
  }

  @java.lang.SuppressWarnings("all")
  public java.lang.Object[] toArray(final java.lang.Object[] arg0) {
    return this.collection().toArray(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public java.lang.Object[] toArray(final java.util.function.IntFunction generator) {
    return this.collection().toArray(generator);
  }

  @java.lang.SuppressWarnings("all")
  public boolean add(final java.lang.Object arg0) {
    return this.collection().add(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public boolean remove(final java.lang.Object arg0) {
    return this.collection().remove(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public boolean containsAll(final java.util.Collection arg0) {
    return this.collection().containsAll(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public boolean addAll(final java.util.Collection arg0) {
    return this.collection().addAll(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public boolean removeAll(final java.util.Collection arg0) {
    return this.collection().removeAll(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public boolean removeIf(final java.util.function.Predicate filter) {
    return this.collection().removeIf(filter);
  }

  @java.lang.SuppressWarnings("all")
  public boolean retainAll(final java.util.Collection arg0) {
    return this.collection().retainAll(arg0);
  }

  @java.lang.SuppressWarnings("all")
  public void clear() {
    this.collection().clear();
  }

  @java.lang.SuppressWarnings("all")
  public java.util.Spliterator spliterator() {
    return this.collection().spliterator();
  }

  @java.lang.SuppressWarnings("all")
  public java.util.stream.Stream stream() {
    return this.collection().stream();
  }

  @java.lang.SuppressWarnings("all")
  public java.util.stream.Stream parallelStream() {
    return this.collection().parallelStream();
  }
}
