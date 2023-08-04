import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class HomePage extends JFrame {

  // field declarations
  private static JButton myAcc;
  private static final JLabel video_games = new JLabel("Video Games");
  private static final String[] types = {"Game Name", "Genre", "Platform", "Publisher"};
  private static JComboBox searchType;
  private static JTextField searchField;
  private static JButton search;
  private static String username;
  private static JList list;


  void myAccPage() {
    // method to switch to account page
    AccountPage accountPage = new AccountPage(username);
    accountPage.show();
    this.dispose();
  }

  String[] search() {
    // method to search for game and return string array of names
    String searchVal = searchField.getText();
    String searchFor = searchType.getSelectedItem().toString();

    if (searchFor.equals("Game Name")) {

      ArrayList<Integer> resultGames = VideoGames.searchByName(searchVal);
      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      return gameNames;

    } else if (searchFor.equals("Genre")) {

      ArrayList<Integer> resultGames = Genre.searchByGenre(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      return gameNames;


    } else if (searchFor.equals("Platform")) {

      ArrayList<Integer> resultGames = Platform.searchByPlatform(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      return gameNames;


    } else {
      
      // Publisher is selected

      ArrayList<Integer> resultGames = Publisher.searchByPublisher(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      return gameNames;

    }

  }

  public HomePage(String username) {

    this.username = username;

    //frame setup
    setTitle("Video Games App"); // set title of app
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // set search bar panel
    JPanel searchBar = new JPanel(new FlowLayout());

    // set results panel
    JPanel results = new JPanel();

    // add label for our project
    video_games.setFont(new Font("Calibri", Font.BOLD, 25));
    video_games.setPreferredSize(new Dimension(200, 25));
    searchBar.add(video_games);

    // add type of search dropdown
    searchType = new JComboBox(types);
    searchType.setPreferredSize(new Dimension(150, 25));
    searchBar.add(searchType);

    // add search field and button
    searchField = new JTextField();
    searchField.setPreferredSize(new Dimension(400, 40));
    searchBar.add(searchField);

    search = new JButton("Search");
    search.setPreferredSize(new Dimension(100, 40));
    searchBar.add(search);

    search.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        String[] gamesFound = search(); // call backend methods to search
        list = new JList(gamesFound);
        list.setVisibleRowCount(15);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        results.removeAll();
        JScrollPane games = new JScrollPane(list);
        games.setPreferredSize(new Dimension(800, 500));
        results.add(games);
        revalidate(); // refresh the results
        repaint(); // refresh the results
      }
    });


    // add my account button
    myAcc = new JButton(username);
    myAcc.setPreferredSize(new Dimension(80, 25));
    searchBar.add(myAcc);

    myAcc.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        myAccPage(); // call method to switch pages
      }

    });

    add(searchBar, BorderLayout.NORTH);
    add(results, BorderLayout.CENTER);

    setVisible(true);

  }
}