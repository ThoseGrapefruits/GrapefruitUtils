package com.thosegrapefruits.utils;

public interface Range<N extends Number> {

  boolean isLowerInclusive();

  N getLowerBound();

  boolean isUpperInclusive();

  N getUpperBound();

  @Override
  boolean equals(Object o);

  @Override
  int hashCode();

  @Override
  String toString();
}
