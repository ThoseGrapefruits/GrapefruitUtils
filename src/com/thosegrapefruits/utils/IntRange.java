package com.thosegrapefruits.utils;

import java.util.Arrays;
import java.util.Objects;

public class IntRange implements Range<Integer> {

  /**
   * Inclusivity of the lower bound.
   */
  private final boolean lowerInclusive;

  /**
   * Lower bound.
   */
  private final Integer lowerBound;

  /**
   * Inclusivity of the upper bound.
   */
  private final boolean upperInclusive;

  /**
   * Upper bound.
   */
  private final Integer upperBound;


  /**
   * @param lowerBound lower bound of {@link IntRange}, inclusive
   * @param upperBound upper bound of {@link IntRange}, exclusive
   */
  public IntRange(Integer lowerBound, Integer upperBound) {
    this(true, lowerBound, false, upperBound);
  }

  /**
   * @param lowerInclusive inclusivity of lower bound
   * @param lowerBound     lower bound of {@link IntRange}
   * @param upperInclusive inclusivity of upper bound
   * @param upperBound     upper bound of {@link IntRange}, exclusive
   */
  public IntRange(boolean lowerInclusive, int lowerBound, boolean upperInclusive, int upperBound) {
    this.lowerInclusive = lowerInclusive;
    this.lowerBound = lowerBound;
    this.upperInclusive = upperInclusive;
    this.upperBound = upperBound;
  }

  /**
   * Construct a new {@link IntRange} based on the common string representation.
   *
   * String must start with {@value "("} (exclusive) or {@value "["} (inclusive), end with {@value
   * ")"} (exclusive) or {@value "]"} (inclusive), and have 2 comma-separated integers in-between.
   *
   * @param range {@link String} representation of the {@link IntRange}.
   */
  public IntRange(String range) throws NumberFormatException {
    Objects.requireNonNull(range);
    if (range.length() < 5) {
      throw new IllegalArgumentException("range string is impossibly short");
    }

    // Determine lower inclusivity
    switch (range.charAt(0)) {
      case '(':
        lowerInclusive = false;
        break;
      case '[':
        lowerInclusive = true;
        break;
      default:
        throw new IllegalArgumentException("range must start with ( or [");
    }

    // Determine upper inclusivity
    switch (range.charAt(range.length() - 1)) {
      case ')':
        upperInclusive = false;
        break;
      case ']':
        upperInclusive = true;
        break;
      default:
        throw new IllegalArgumentException("range must end with ) or ]");
    }

    String[] input = range.substring(1, range.length() - 1).split("\\s*,\\s*");
    Arrays.deepToString(input);

    if (input.length != 2) {
      throw new IllegalArgumentException("range can only have 2 values: upper & lower bound");
    }

    lowerBound = Integer.parseInt(input[0]);
    upperBound = Integer.parseInt(input[1]);
  }

  @Override
  public boolean isLowerInclusive() {
    return lowerInclusive;
  }

  @Override
  public Integer getLowerBound() {
    return lowerBound;
  }

  @Override
  public boolean isUpperInclusive() {
    return upperInclusive;
  }

  @Override
  public Integer getUpperBound() {
    return upperBound;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    IntRange range = (IntRange) o;
    return isLowerInclusive() == range.isLowerInclusive() &&
           getLowerBound().equals(range.getLowerBound()) &&
           isUpperInclusive() == range.isUpperInclusive() &&
           getUpperBound().equals(range.getUpperBound());
  }

  @Override
  public int hashCode() {
    return Objects.hash(isLowerInclusive(), getLowerBound(), isUpperInclusive(), getUpperBound());
  }

  @Override
  public String toString() {
    return String.format("%c%d, %d%c%n",
                         lowerInclusive ? '[' : '(', lowerBound,
                         upperBound, upperInclusive ? ']' : ')');
  }
}
