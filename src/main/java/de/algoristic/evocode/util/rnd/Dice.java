package de.algoristic.evocode.util.rnd;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface Dice<T> extends Iterable<WeightedValue<T>> {

  T roll() throws EmptyDiceException;

  default Stream<T> stream() {
    return Stream.generate(this.supplyValue());
  }

  default Supplier<T> supplyValue() {
    return () -> this.roll();
  }

  public static <T> Dice<T> getTraditionalDice(Stream<T> items) {
    return items.collect(toTraditionalDice());
  }

  public static <T> Dice<T> getTraditionalDice(Set<T> items) {
    return getTraditionalDice(items.stream());
  }

  public static <T> Dice<T> getTraditionalDice(List<T> items) {
    return getTraditionalDice(items.stream());
  }

  public static <T> Dice<T> getTraditionalDice(T[] items) {
    return getTraditionalDice(Arrays.asList(items));
  }

  public static <T> Collector<T, ?, Dice<T>> toTraditionalDice() {
    return new TraditionalDiceCollector<T>();
  }

  public static <T> Collector<T, ?, Stream<T>> toTraditionalRandomStream() {
    return new TraditionalDiceStreamCollector<>();
  }

  public static <T> Dice<T> getLoadedDice(Stream<WeightedValue<T>> items) {
    return items.collect(toLoadedDice());
  }

  public static <T> Dice<T> getLoadedDice(Set<WeightedValue<T>> items) {
    return getLoadedDice(items.stream());
  }

  public static <T> Dice<T> getLoadedDice(List<WeightedValue<T>> items) {
    return getLoadedDice(items.stream());
  }

  public static <T> Collector<WeightedValue<T>, ?, Dice<T>> toLoadedDice() {
    return new LoadedDiceCollector<T>();
  }

  public static <T> Collector<WeightedValue<T>, ?, Stream<T>> toLoadedRandomStream() {
    return new LoadedDiceStreamCollector<>();
  }
}
