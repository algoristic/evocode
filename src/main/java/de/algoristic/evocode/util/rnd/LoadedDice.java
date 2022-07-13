package de.algoristic.evocode.util.rnd;

import java.util.List;
import java.util.Random;

public final class LoadedDice<T> extends AbstractDice<T> {

  public LoadedDice() {
    super();
  }

  public LoadedDice(Random rnd) {
    super(rnd);
  }

  @Override
  public LoadedDice<T> add(final double weight, final T value) {
    return (LoadedDice<T>) super.add(weight, value);
  }

  @Override
  public LoadedDice<T> add(final WeightedValue<T> value) {
    return (LoadedDice<T>) super.add(value);
  }
  
  public LoadedDice<T> add(final List<WeightedValue<T>> values) {
	  values.forEach(this::add);
	  return this;
  }

  public LoadedDice<T> addAll(final LoadedDice<T> other) {
    return (LoadedDice<T>) super.addAll(other);
  }
}
