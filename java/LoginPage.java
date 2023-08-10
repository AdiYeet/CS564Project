import java.awt.EventQueue;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JLabel;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

/**
 * LoginPage class that contains the constructor for the LoginPage object, its specifications and
 * related methods
 * 
 * @author avakharia, kjhunjhunwa2, tmjohnson32
 *
 */
@SuppressWarnings({"unused", "serial"})
public class LoginPage extends JFrame {

  // field declarations
  private JFrame frame;
  private static JLabel userLabel;
  private static JTextField userText;
  private static JLabel passwordLabel;
  private static JPasswordField passwordText;
  private static JButton loginButton;
  private static JLabel status;
  private static JButton signUpButton;
  private static JLabel signInLabel;

  /**
   * Method to call backend methods to query database and verify user
   */
  @SuppressWarnings("deprecation")
  void userVerification() {

    String user = userText.getText(); // get username
    String password = passwordText.getText(); // get password

    if (Users.verifyUser(user, password)) {
      // verifies user and sets status text
      status.setText("Login successful!");
      HomePage home = new HomePage(user);
      frame.dispose(); // dispose of the current frame
      home.setVisible(true); // view the homepage
    } else {
      status.setText("Login failed!"); // username/password did not match
    }
  }

  /**
   * Method that calls specific backend methods to query database and add user
   */
  @SuppressWarnings("deprecation")
  void addUser() {
    String user = userText.getText(); // get username
    String password = passwordText.getText(); // get password

    try {

      boolean returned = Users.addUser(user, password);

      if (returned) {
        // adds user and sets status text
        status.setText("Sign up successful!");
        HomePage home = new HomePage(user);
        frame.dispose(); // dispose of the current frame
        home.setVisible(true); // view the home page
      } else {
        status.setText("Sign up unsuccessful! Please check console for error.");
      }

    } catch (SQLException e2) {
      e2.printStackTrace();
    }

  }

  /**
   * Constructor for the Login page with its specifications
   */
  public LoginPage() {
    // set the frame
    setTitle("Error Page");
    frame = new JFrame();
    frame.getContentPane().setBackground(Color.WHITE);
    frame.getContentPane().setLayout(null);

    // add username label and text field
    userLabel = new JLabel("Username");
    userLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
    userLabel.setBounds(224, 89, 68, 16);
    frame.getContentPane().add(userLabel);

    userText = new JTextField();
    userText.setForeground(Color.BLACK);
    userText.setBackground(Color.WHITE);
    userText.setBounds(217, 104, 365, 29);
    frame.getContentPane().add(userText);
    userText.setColumns(10);

    // add password label and text field
    passwordLabel = new JLabel("Password");
    passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
    passwordLabel.setBounds(224, 143, 61, 16);
    frame.getContentPane().add(passwordLabel);

    passwordText = new JPasswordField();
    passwordText.setBounds(217, 171, 365, 26);
    frame.getContentPane().add(passwordText);

    // add login button
    loginButton = new JButton("Login");
    loginButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
    loginButton.setForeground(Color.BLACK);
    loginButton.setBackground(Color.WHITE);
    loginButton.setBounds(217, 229, 186, 29);
    frame.getContentPane().add(loginButton);

    // add signup button
    signUpButton = new JButton("Sign Up");
    signUpButton.setFont(new Font("SansSerif", Font.PLAIN, 13));
    signUpButton.setForeground(Color.BLACK);
    signUpButton.setBackground(Color.WHITE);
    signUpButton.setBounds(407, 229, 175, 29);
    frame.getContentPane().add(signUpButton);

    // add status text label
    status = new JLabel("");
    status.setFont(new Font("SansSerif", Font.PLAIN, 13));
    status.setBounds(217, 270, 365, 16);
    status.setHorizontalAlignment(SwingConstants.CENTER);
    frame.getContentPane().add(status);

    loginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        userVerification();
      }
    }); // call backend methods to verify user

    signUpButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        addUser();
      }
    }); // call backend methods to add user

    // add sign in label
    signInLabel = new JLabel("Sign In or Create Account");
    signInLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
    signInLabel.setBounds(278, 23, 244, 36);

    frame.getContentPane().add(signInLabel);
    frame.setBounds(100, 100, 800, 500);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    this.frame.setVisible(true);
  }
}
