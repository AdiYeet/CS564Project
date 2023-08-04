import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * LoginPage
 */
public class LoginPage extends JFrame {

  // field declarations
  private static JLabel userLabel;
  private static JTextField userText;
  private static JLabel passwordLabel;
  private static JPasswordField passwordText;
  private static JButton loginButton;
  private static JLabel status;
  private static JButton signUpButton;

  void userVerification() {

    // method calls specific backend methods to query database and verify user

    String user = userText.getText(); // get username
    String password = passwordText.getText(); // get password

    if (Users.verifyUser(user, password)) {
      // verifies user and sets status text
      status.setText("Login successful!");
      HomePage home = new HomePage(user);
      home.show();
      this.dispose();
    } else {
      status.setText("Login failed!");
    }
  }

  void addUser() {
    
    // method calls specific backend methods to query database and add user

    String user = userText.getText(); // get username
    String password = passwordText.getText(); // get password

    try {

      boolean returned = Users.addUser(user, password);
      
      if (returned) {
        // adds user and sets status text
        status.setText("Sign up successful!");
        HomePage home = new HomePage(user);
        home.show();
        this.dispose();

      } else {
        status.setText("Sign up unsuccessful! Please check console for error.");
      }

    } catch (SQLException e2) {
      e2.printStackTrace();
    }

  }


  public LoginPage() {
    
    setTitle("Login Page"); // set title of app

    // setup final frame
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());
    
    // set central panel 
    JPanel central = new JPanel(new FlowLayout(FlowLayout.LEFT));
    central.setPreferredSize(new Dimension(270, 200));

    // setup username and password fields
    userLabel = new JLabel("Username");
    userLabel.setPreferredSize(new Dimension(80,25));
    central.add(userLabel);

    userText = new JTextField();
    userText.setPreferredSize(new Dimension(165, 25));
    central.add(userText);

    passwordLabel = new JLabel("Password");
    passwordLabel.setPreferredSize(new Dimension(80, 25));
    central.add(passwordLabel);

    passwordText = new JPasswordField();
    passwordText.setPreferredSize(new Dimension(165, 25));
    central.add(passwordText);

    // setup login and signup buttons
    loginButton = new JButton("Login");
    loginButton.setPreferredSize(new Dimension(80, 25));
    central.add(loginButton);

    signUpButton = new JButton("Sign Up");
    signUpButton.setPreferredSize(new Dimension(80, 25));
    central.add(signUpButton);

    // setup text for login/sign up status
    status = new JLabel("");
    status.setPreferredSize(new Dimension(500, 25));
    central.add(status);
    
    loginButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        userVerification();
      }}); // call backend methods to verify user
    
    signUpButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        addUser();
      }}); // call backend methods to add user
    
    add(central, BorderLayout.CENTER);

    setVisible(true);

  }

}
