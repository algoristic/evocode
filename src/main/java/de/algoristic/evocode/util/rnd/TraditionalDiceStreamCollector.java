package de.algoristic.evocode.util.rnd;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

final class TraditionalDiceStreamCollector<T>
    implements Collector<T, TraditionalDice<T>, Stream<T>> {

  @Override
  public Supplier<TraditionalDice<T>> supplier() {
    return TraditionalDice::new;
  }

  @Override
  public BiConsumer<TraditionalDice<T>, T> accumulator() {
    return (dice, value) -> dice.add(value);
  }

  @Override
  public BinaryOperator<TraditionalDice<T>> combiner() {
    return (left, right) -> left.addAll(right);
  }

  @Override
  public Function<TraditionalDice<T>, Stream<T>> finisher() {
    return (dice) -> dice.stream();
  }

  @Override
  public Set<Characteristics> characteristics() {
    return Collections.singleton(Characteristics.UNORDERED);
  }
}
