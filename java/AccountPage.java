import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccountPage extends JFrame {

  // field declarations
  private static JLabel userLabel;
  private static String username;
  private static JButton deleteAcc = new JButton("Delete User");

  void switchLoginPage() {
    
    // method to switch to Login Page
    
    LoginPage loginPage = new LoginPage();
    loginPage.show();
    this.dispose();
    
  }

  public AccountPage(String username) {

    this.username = username;

    // frame setup
    setTitle("My Account");
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // show current user
    JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

    userLabel = new JLabel("Username: " + username);
    userLabel.setFont(new Font("Calibri", Font.BOLD, 30));
    userLabel.setPreferredSize(new Dimension(400, 80));
    userPanel.add(userLabel);

    // create central panel
    JPanel central = new JPanel(new FlowLayout());
    
    // show liked games

    // show liked genres

    // delete user option
    JPanel deletePanel = new JPanel(new FlowLayout());
    deleteAcc.setPreferredSize(new Dimension(100, 25));
    deletePanel.add(deleteAcc);
    deleteAcc.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          Users.deleteUser(username);
          switchLoginPage();
        } catch (SQLException e1) {
          e1.printStackTrace();
        }

      }

    });


    add(userPanel, BorderLayout.NORTH);
    add(deletePanel, BorderLayout.SOUTH);

    setVisible(true);

  }

}
