import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JList;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

/**
 * AccountPage class that contains the constructor for Account Page, its specifications and related
 * methods
 * 
 * @author avakharia, kjhunjhunwa2, tmjohnson32
 *
 */
@SuppressWarnings({"serial", "unused"})
public class AccountPage extends JFrame {

  // field declarations
  private JPanel contentPane;
  private static JLabel userLabel;
  private static String username;
  private static JButton deleteAcc = new JButton("Delete User");
  private static JButton homeBtn = new JButton("Back to Home");
  private static JButton logOut = new JButton("Log Out");
  @SuppressWarnings("rawtypes")
  private static JList gamesList;
  @SuppressWarnings("rawtypes")
  private static JList genresList;

  /**
   * Method to switch the view to the Login Page
   */
  void switchLoginPage() {
    Main.main(null);
    this.dispose();
  }

  /**
   * Method to switch the view to Home Page
   */
  void switchHomePage() {
    HomePage homePage = new HomePage(username);
    this.dispose();
    homePage.setVisible(true);
  }

  /**
   * Constructor for the Account Page with its specifications
   * 
   * @param username - username of the user currently logged in
   */
  @SuppressWarnings({"static-access", "unchecked", "rawtypes"})
  public AccountPage(String username) {
    this.username = username;

    // create frame and panel
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 1000, 700);
    contentPane = new JPanel();
    contentPane.setBackground(Color.WHITE);
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

    setContentPane(contentPane);
    contentPane.setLayout(null);

    // add my account label
    JLabel myAccountLabel = new JLabel("My Account");
    myAccountLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
    myAccountLabel.setBounds(41, 30, 162, 27);
    contentPane.add(myAccountLabel);

    // frontend design element
    JSeparator separator = new JSeparator();
    separator.setForeground(Color.BLACK);
    separator.setBounds(41, 62, 918, 12);
    contentPane.add(separator);

    // create hello label
    JLabel helloLabel = new JLabel("Hello,");
    helloLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
    helloLabel.setBounds(51, 95, 61, 16);
    contentPane.add(helloLabel);

    // create user label
    userLabel = new JLabel(username);
    userLabel.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
    userLabel.setBounds(109, 86, 199, 34);
    contentPane.add(userLabel);

    // create liked games label
    JLabel gameLabel = new JLabel("Liked Games");
    gameLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
    gameLabel.setBounds(214, 168, 101, 16);
    contentPane.add(gameLabel);

    // create liked genres label
    JLabel genreLabel = new JLabel("Liked Genres");
    genreLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));
    genreLabel.setBounds(679, 168, 101, 16);
    contentPane.add(genreLabel);

    // get liked games and display on scrollpane
    ArrayList<Integer> gameids = GameThoughts.getLikedGames(username);
    String[] likedGames = new String[gameids.size()];
    int index1 = 0;
    for (Integer gameID : gameids) {
      likedGames[index1] = VideoGames.returnAllData(gameID)[0];
      index1++;
    }

    JScrollPane gamesScrollPane = new JScrollPane();
    gamesScrollPane.setViewportBorder(null);
    gamesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    gamesScrollPane.setBounds(51, 200, 420, 332);
    contentPane.add(gamesScrollPane);
    gamesList = new JList(likedGames);
    gamesScrollPane.setViewportView(gamesList);
    gamesList.setBackground(SystemColor.window);
    DefaultListCellRenderer renderer = (DefaultListCellRenderer) gamesList.getCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);

    // get liked genres and show on scrollpane
    ArrayList<Integer> genreids = GenreThoughts.getLikedGenres(username);
    String[] likedGenres = new String[genreids.size()];
    int index2 = 0;
    for (Integer genreid : genreids) {
      likedGenres[index2] = Genre.getGenreName(genreid);
      index2++;
    }
    JScrollPane genreScrollPane = new JScrollPane();
    genreScrollPane.setViewportBorder(null);
    genreScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    genreScrollPane.setBounds(523, 200, 420, 332);
    contentPane.add(genreScrollPane);
    genresList = new JList(likedGenres);
    genreScrollPane.setViewportView(genresList);
    genresList.setBackground(SystemColor.window);
    renderer = (DefaultListCellRenderer) genresList.getCellRenderer();
    renderer.setHorizontalAlignment(SwingConstants.CENTER);

    // add home button
    homeBtn = new JButton("Back to Home");
    homeBtn.setBounds(41, 577, 147, 41);
    contentPane.add(homeBtn);
    homeBtn.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        switchHomePage();
      }

    });

    // add logout button
    logOut = new JButton("Log Out");
    logOut.setBounds(426, 577, 147, 41);
    contentPane.add(logOut);
    logOut.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switchLoginPage();
      }
    });

    // add delete account button
    deleteAcc = new JButton("Delete User");
    deleteAcc.setBounds(807, 577, 147, 41);
    contentPane.add(deleteAcc);
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

    contentPane.setVisible(true);
  }
}
