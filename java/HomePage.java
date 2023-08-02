import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HomePage extends JFrame {

  private static JButton myAcc;
  private static final JLabel video_games = new JLabel("Video Games");
  private static final String[] types = {"Game Name", "Genre", "Platform", "Publisher"};
  private static JComboBox searchType;
  private static JTextField searchField;
  private static JButton search;
  private static String username;

  
  void myAccPage() {
    AccountPage accountPage = new AccountPage(username);
    accountPage.show();
    this.dispose();
  }
  
  void search() {

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

      // TODO: add to table

    } else if (searchFor.equals("Genre")) {

      ArrayList<Integer> resultGames = Genre.searchByGenre(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      // TODO: add to table


    } else if (searchFor.equals("Platform")) {

      ArrayList<Integer> resultGames = Platform.searchByPlatform(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      // TODO: add to table

    } else {
      // Publisher is selected
      
      ArrayList<Integer> resultGames = Publisher.searchByPublisher(searchVal);

      String[] gameNames = new String[resultGames.size()];
      int index = 0;
      for (Integer gameID : resultGames) {
        gameNames[index] = VideoGames.returnAllData(gameID)[0];
        index++;
      }

      // TODO: add to table

    }
  }

  public HomePage(String username) {
    this.username = username;
    JPanel panel = new JPanel();
    setSize(1200, 800);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    add(panel);

    // add label for our project
    video_games.setBounds(20, 20, 200, 25);
    video_games.setFont(new Font("Calibri", Font.BOLD, 25));
    panel.add(video_games);

    // add type of search dropdown

    searchType = new JComboBox(types);
    searchType.setBounds(300, 22, 150, 25);
    panel.add(searchType);

    // add search field and button
    searchField = new JTextField();
    searchField.setBounds(450, 10, 400, 40);
    panel.add(searchField);

    search = new JButton("Search");
    search.setBounds(850, 10, 100, 40);
    panel.add(search);

    search.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        search(); // call backend methods to search
      }
    });


    // add my account button
    myAcc = new JButton(username);
    myAcc.setBounds(1100, 20, 80, 25);
    panel.add(myAcc);
    
    myAcc.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        myAccPage(); // call method to switch pages
      }
      
    });

    // results table

    panel.setLayout(null);

    setVisible(true);
  }
}