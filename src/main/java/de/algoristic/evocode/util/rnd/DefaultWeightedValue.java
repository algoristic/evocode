package de.algoristic.evocode.util.rnd;

final class DefaultWeightedValue<T> implements WeightedValue<T> {

  final double weight;
  final T value;

  public DefaultWeightedValue(final double weight, final T value) {
    this.weight = weight;
    this.value = value;
  }

  @Override
  public double getWeight() {
    return weight;
  }

  @Override
  public T getValue() {
    return value;
  }
}
