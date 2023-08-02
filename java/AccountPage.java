import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccountPage extends JFrame{
  
  private static JLabel userLabel;
  private static String username;
  
  public AccountPage(String username) {
    
    this.username = username;
    JPanel panel = new JPanel();
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel);
    
    panel.setLayout(null);
    
    setVisible(true);
    
  }

}
