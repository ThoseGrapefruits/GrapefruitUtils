package com.thosegrapefruits.utils;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Lucas Pickering
 * @author Logan Moore
 */
public class DoubleDeltaComparator implements Comparator<Double> {

  private static final double DEFAULT_DELTA = 0.0001d;
  private final double delta;

  /**
   * Constructs a new comparator that compares doubles with a certain delta buffer zone. for two
   * doubles {@code d1} and {@code d2}, if {@code abs(d1 - d2) <= delta}, then they are considered
   * equal. This constructor uses a delta of {@link #DEFAULT_DELTA}.
   */
  public DoubleDeltaComparator() {
    this(DEFAULT_DELTA);
  }

  /**
   * Constructs a new comparator that compares doubles with a certain delta buffer zone. for two
   * doubles {@code d1} and {@code d2}, if {@code abs(d1 - d2) <= delta}, then they are considered
   * equal. This constructor uses the given delta.
   *
   * @param delta the delta to be used in comparisons (non-negative)
   * @throws IllegalArgumentException if {@code delta < 0}
   */
  public DoubleDeltaComparator(double delta) {
    if (delta < 0) {
      throw new IllegalArgumentException("delta cannot be negative");
    }
    this.delta = delta;
  }

  @Override
  public int compare(Double d1, Double d2) {
    Objects.requireNonNull(d1);
    Objects.requireNonNull(d2);

    return Math.abs(d1 - d2) <= delta ? 0 : Double.compare(d1, d2);
  }
}
