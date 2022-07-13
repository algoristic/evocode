package de.algoristic.evocode.util.rnd;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

final class LoadedDiceCollector<T> implements Collector<WeightedValue<T>, LoadedDice<T>, Dice<T>> {

  @Override
  public Supplier<LoadedDice<T>> supplier() {
    return LoadedDice::new;
  }

  @Override
  public BiConsumer<LoadedDice<T>, WeightedValue<T>> accumulator() {
    return (dice, value) -> dice.add(value);
  }

  @Override
  public BinaryOperator<LoadedDice<T>> combiner() {
    return (left, right) -> left.addAll(right);
  }

  @Override
  public Function<LoadedDice<T>, Dice<T>> finisher() {
    return (dice) -> dice;
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.singleton(Characteristics.UNORDERED);
  }
}
