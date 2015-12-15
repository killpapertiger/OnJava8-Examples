// ui/MonitoredLongRunningCallable.java
// (c)2016 MindView LLC: see Copyright.txt
// We make no guarantees that this code is fit for any purpose.
// Visit http://mindviewinc.com/Books/OnJava/ for more book information.
// Displaying task progress with ProgressMonitors.
// {IgnoreOutput}
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;
import onjava.*;
import static onjava.SwingConsole.*;

class MonitoredCallable implements Callable<String> {
  private static int counter = 0;
  private final int id = counter++;
  private final ProgressMonitor monitor;
  private final static int MAX = 8;
  public MonitoredCallable(ProgressMonitor monitor) {
    this.monitor = monitor;
    monitor.setNote(toString());
    monitor.setMaximum(MAX - 1);
    monitor.setMillisToPopup(500);
  }
  @Override
  public String call() {
    System.out.println(this + " started");
    try {
      for(int i = 0; i < MAX; i++) {
        TimeUnit.MILLISECONDS.sleep(500);
        if(monitor.isCanceled())
          Thread.currentThread().interrupt();
        final int progress = i;
        SwingUtilities.invokeLater(() ->
          monitor.setProgress(progress));
      }
    } catch(InterruptedException e) {
      monitor.close();
      System.out.println(this + " interrupted");
      return "Result: " + this + " interrupted";
    }
    System.out.println(this + " completed");
    return "Result: " + this + " completed";
  }
  @Override
  public String toString() { return "Task " + id; }
};

public class MonitoredLongRunningCallable extends JFrame {
  private JButton
    b1 = new JButton("Start Long Running Task"),
    b2 = new JButton("End Long Running Task"),
    b3 = new JButton("Get results");
  private TaskManager<String,MonitoredCallable> manager =
    new TaskManager<>();
  public MonitoredLongRunningCallable() {
    b1.addActionListener(e -> {
      MonitoredCallable task = new MonitoredCallable(
              new ProgressMonitor(
                      MonitoredLongRunningCallable.this,
                      "Long-Running Task", "", 0, 0)
      );
      manager.add(task);
      System.out.println(task + " added to the queue");
    });
    b2.addActionListener(e -> {
      for(String result : manager.purge())
        System.out.println(result);
    });
    b3.addActionListener(e -> {
      for(String result : manager.getResults())
        System.out.println(result);
    });
    setLayout(new FlowLayout());
    add(b1);
    add(b2);
    add(b3);
  }
  public static void main(String[] args) {
    run(new MonitoredLongRunningCallable(), 200, 500);
  }
}
