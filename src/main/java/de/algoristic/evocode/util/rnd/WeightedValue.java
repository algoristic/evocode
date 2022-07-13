package de.algoristic.evocode.util.rnd;

public interface WeightedValue<T> {

  public double getWeight();

  public T getValue();

  public static <T> WeightedValue<T> of(final double weight, final T value) {
    return new DefaultWeightedValue<>(weight, value);
  }
}
