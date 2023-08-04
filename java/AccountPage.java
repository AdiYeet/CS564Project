import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * My Account Page
 */
public class AccountPage extends JFrame {

  // field declarations
  private static JLabel userLabel;
  private static String username;
  private static JButton deleteAcc = new JButton("Delete User");
  private static JButton homeBtn = new JButton("Back to Home");
  private static JButton logOut = new JButton("Log Out");
  private static JList<String> gamesList;
  private static JList<String> genresList;

  void switchLoginPage() {
    
    // method to switch to Login Page
    
    LoginPage loginPage = new LoginPage();
    loginPage.show();
    this.dispose();
    
  }

  void switchHomePage() {
    
    // method to switch to Home Page
    
    HomePage homePage = new HomePage(username);
    homePage.show();
    this.dispose();
  }
  
  public AccountPage(String username) {

    this.username = username; // set current username

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
    JPanel central = new JPanel(new GridLayout(0,3));
    
    // show liked games
    JLabel gameLabel = new JLabel("    Liked Games");
    gameLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
    central.add(gameLabel);
    ArrayList<Integer> gameids = GameThoughts.getLikedGames(username);
    String[] likedGames = new String[gameids.size()];
    int index = 0;
    for (Integer gameID : gameids) {
      likedGames[index] = VideoGames.returnAllData(gameID)[0];
      index++;
    }
    gamesList = new JList<String>(likedGames);
    JScrollPane games = new JScrollPane(gamesList);
    games.setPreferredSize(new Dimension(300, 400));
    central.add(games);
    central.add(new JLabel(""));

    // show liked genres
    JLabel genreLabel = new JLabel("    Liked Genres");
    genreLabel.setFont(new Font("Calibri", Font.PLAIN, 20));
    central.add(genreLabel);
    ArrayList<Integer> genreids = GenreThoughts.getLikedGenres(username);
    String[] likedGenres = new String[genreids.size()];
    int index2 = 0;
    for (Integer genreid : genreids) {
      likedGenres[index2] = Genre.getGenreName(genreid);
      index++;
    }
    genresList = new JList<String>(likedGenres);
    JScrollPane genres = new JScrollPane(genresList);
    genres.setPreferredSize(new Dimension(300, 400));
    central.add(genres);  
    central.add(new JLabel(""));

    // delete user, back to home and log out buttons
    JPanel deletePanel = new JPanel(new FlowLayout());
    homeBtn.setPreferredSize(new Dimension(180, 25));
    deletePanel.add(homeBtn);
    homeBtn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
       switchHomePage();
      }
      
    });
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
    
    logOut.setPreferredSize(new Dimension(100, 25));
    deletePanel.add(logOut);
    logOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switchLoginPage();
      }
    });


    // add everything to the frame
    add(userPanel, BorderLayout.NORTH);
    add(deletePanel, BorderLayout.SOUTH);
    add(central, BorderLayout.CENTER);

    setVisible(true);

  }

}
