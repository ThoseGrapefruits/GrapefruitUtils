package com.thosegrapefruits.utils;

import java.util.Date;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.OperationNotSupportedException;

public class VariableRateTimer extends Timer {

  private final Object lock = new Object();
  private Timer delegate = new Timer();
  private TimerTask task;
  private long period = 0;

  public VariableRateTimer() {
    delegate = new Timer();
  }

  public VariableRateTimer(boolean isDaemon) {
    delegate = new Timer(isDaemon);
  }

  public VariableRateTimer(String name) {
    delegate = new Timer(name);
  }

  public VariableRateTimer(String name, boolean isDaemon) {
    delegate = new Timer(name, isDaemon);
  }

  private VariableRateTimer(Timer delegate) {
    this.delegate = delegate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    if (getClass() != o.getClass()) {
      return o.getClass() == delegate.getClass() && delegate.equals(o);
    }
    return Objects.equals(delegate, ((VariableRateTimer) o).delegate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(delegate);
  }

  @Override
  public String toString() {
    return delegate.toString();
  }

  @Override
  public void schedule(TimerTask task, long delay) {
    new OperationNotSupportedException().printStackTrace();
  }

  @Override
  public void schedule(TimerTask task, Date time) {
    new OperationNotSupportedException().printStackTrace();
  }

  @Override
  public void schedule(TimerTask task, long delay, long period) {
    new OperationNotSupportedException().printStackTrace();
  }

  @Override
  public void schedule(TimerTask task, Date firstTime, long period) {
    new OperationNotSupportedException().printStackTrace();
  }

  @Override
  public void scheduleAtFixedRate(TimerTask task, long delay, long period) {
    this.task = task;
    this.period = period;
    delegate.scheduleAtFixedRate(task, delay, period);
  }

  @Override
  public void scheduleAtFixedRate(TimerTask task, Date firstTime, long period) {
    this.task = task;
    this.period = period;
    delegate.scheduleAtFixedRate(task, firstTime, period);
  }

  public void changeRate(long period) {
    synchronized (lock) {
      if (task == null) {
        throw new IllegalStateException("Timer must be running in order to change states");
      }

      clearDelegate();
      delegate = new Timer();
      delegate.schedule(task, 0, period);
    }
  }

  public void pause() {
    synchronized (lock) {
      clearDelegate();
    }
  }

  private void clearDelegate() {
    delegate.cancel();
    delegate.purge();
    delegate = null;
  }

  public void resume() {
    synchronized (lock) {
      if (delegate != null) {
        throw new IllegalStateException("Timer is running");
      }

      delegate = new Timer();
      delegate.schedule(task, 0, period);
    }
  }

  @Override
  public void cancel() {
    new OperationNotSupportedException().printStackTrace();
  }

  @Override
  public int purge() {
    return delegate.purge();
  }
}
