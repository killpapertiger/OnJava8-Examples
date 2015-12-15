// exceptions/MainException.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
import java.util.*;
import java.nio.file.*;

public class MainException {
  // Pass exceptions to the console:
  public static void main(String[] args) throws Exception {
    // Open the file:
    List<String> lines = Files.readAllLines(
      Paths.get("MainException.java"));
    // Use the file ...
  }
}
