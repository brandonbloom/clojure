package clojure.lang;

import java.lang.ThreadLocal;

public class TransientEdit {

  static final int N = 8;

  private static class State {
    public Object[] objects;
    public int i;
    public int n;
  }

  private static ThreadLocal local = new ThreadLocal() {
    protected Object initialValue() {
      return new State();
    }
  };

  public static boolean isEditable(Object object) {
    State state = (State)local.get();
    for (int i = state.i; i > state.i - state.n; i--) {
      if (state.objects[(i + N) % N] == object) {
        return true;
      }
    }
    return false;
  }

  public static void add(Object o) {
    State state = (State)local.get();
    if (state.n != N) {
      state.n++;
    }
    state.objects[state.i] = o;
    state.i = state.i + 1 % N;
  }

  public static void clear() {
    State state = (State)local.get();
    state.i = 0;
    state.n = 0;
  }

}
