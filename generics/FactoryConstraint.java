// generics/FactoryConstraint.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.

interface FactoryI<T> {
  T create();
}

class Foo2<T> {
  private T x;
  public <F extends FactoryI<T>> Foo2(F factory) {
    x = factory.create();
  }
  // ...
}

class IntegerFactory implements FactoryI<Integer> {
  @Override
  public Integer create() {
    return 0;
  }
}

class Widget {
  public static class Factory implements FactoryI<Widget> {
    @Override
    public Widget create() {
      return new Widget();
    }
  }
}

public class FactoryConstraint {
  public static void main(String[] args) {
    new Foo2<>(new IntegerFactory());
    new Foo2<>(new Widget.Factory());
  }
}
