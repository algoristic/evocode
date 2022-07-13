package de.algoristic.evocode.util.rnd;

import java.util.List;
import java.util.Random;

public final class TraditionalDice<T> extends AbstractDice<T> {

  public TraditionalDice() {
    super();
  }

  public TraditionalDice(Random rnd) {
    super(rnd);
  }

  public TraditionalDice<T> add(final T value) {
    return (TraditionalDice<T>) super.add(1, value);
  }

  public TraditionalDice<T> add(final List<T> value) {
    value.forEach(this::add);
    return this;
  }

  public TraditionalDice<T> addAll(final TraditionalDice<T> other) {
    return (TraditionalDice<T>) super.addAll(other);
  }
}
