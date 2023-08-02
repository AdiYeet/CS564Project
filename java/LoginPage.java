import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

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

    // method checks which button is clicked and calls specific backend methods to query database
    // and figure out whether user can be added/verified

    // status.setText("");
    String user = userText.getText(); // get username
    String password = passwordText.getText(); // get password
    // System.out.println(user + "," + password);

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

    // setup panel and frame
    JPanel panel = new JPanel();
    setSize(800, 500);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel);

    panel.setLayout(null);

    // setup username and password fields
    userLabel = new JLabel("Username");
    userLabel.setBounds(10, 20, 80, 25);
    panel.add(userLabel);

    userText = new JTextField();
    userText.setBounds(100, 20, 165, 25);
    panel.add(userText);

    passwordLabel = new JLabel("Password");
    passwordLabel.setBounds(10, 50, 80, 25);
    panel.add(passwordLabel);

    passwordText = new JPasswordField();
    passwordText.setBounds(100, 50, 165, 25);
    panel.add(passwordText);

    // setup login and signup buttons
    loginButton = new JButton("Login");
    loginButton.setBounds(10, 80, 80, 25);
    panel.add(loginButton);

    signUpButton = new JButton("Sign Up");
    signUpButton.setBounds(90, 80, 80, 25);
    panel.add(signUpButton);

    // setup text for login/sign up status
    status = new JLabel("");
    status.setBounds(10, 110, 500, 25);
    
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
    
    panel.add(status);

    setVisible(true);

  }

}
