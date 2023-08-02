import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Account {
  
  private static JLabel userLabel;
  
  public void setup() {
    
    JPanel panel = new JPanel();
    JFrame frame = new JFrame();
    frame.setSize(800, 500);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.add(panel);
    
    panel.setLayout(null);
    
    userLabel = new JLabel()
  }

}
