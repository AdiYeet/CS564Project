import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
  private static JList<String> list;
  private static JButton homeBtn = new JButton("Back to Home");

  void switchAccountPage() {
    // method to switch to account page
    AccountPage accountPage = new AccountPage(username);
    accountPage.show();
    this.dispose();
  }

  void switchHomePage() {
    // method to switch to Home Page
    HomePage homePage = new HomePage(username);
    homePage.show();
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
        gameNames[index] = VideoGames.returnAllData(gameID)[0]; // get name of each game
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

    // frame setup
    setTitle("Home Page"); // set title of app
    setSize(1000, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    // set search bar panel
    JPanel searchBar = new JPanel(new FlowLayout());

    // set results panel
    JPanel results = new JPanel();

    // set bototm panel
    JPanel bottom = new JPanel();

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
        list = new JList<String>(gamesFound);
        list.setVisibleRowCount(15);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addMouseListener(new MouseAdapter() {

          public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {

              results.removeAll(); // remove list of games to replace with info about selected game
              results.setLayout(new GridLayout(0, 1));

              String gameChosen = list.getSelectedValue(); // get name of game chosen
              // get info about game
              String[] game = VideoGames.returnAllData(VideoGames.getGameID(gameChosen));

              JLabel gameName = new JLabel("Name: " + game[0]);
              results.add(gameName);

              JLabel platformName = new JLabel("Platform: " + game[1]);
              results.add(platformName);

              JLabel year = new JLabel("Year released: " + game[2]);
              results.add(year);

              JLabel gameGenre = new JLabel("Genre: " + game[3]);
              results.add(gameGenre);

              JLabel publisherName = new JLabel("Publisher: " + game[4]);
              results.add(publisherName);

              JLabel sales = new JLabel("Global Sales (in millions): " + game[5]);
              results.add(sales);

              JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

              JLabel ratingLabel = new JLabel("Rating: ");
              ratingPanel.add(ratingLabel);

              Integer userRating =
                  GameThoughts.getRating(VideoGames.getGameID(gameChosen), username);

              String finalRating = "-";

              if (userRating == null) {
                // do nothing
              } else {
                finalRating = String.valueOf(userRating);
              }

              JTextField rating = new JTextField(finalRating, 1);
              ratingPanel.add(rating);

              JLabel outOf = new JLabel("/5");
              ratingPanel.add(outOf);

              JButton save = new JButton("Save");
              save.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  // save new rating
                  GameThoughts.setGameRating(VideoGames.getGameID(gameChosen),
                      Integer.parseInt(rating.getText()), username);
                  save.setText("Saved!");

                  revalidate();
                  repaint();
                }

              });
              ratingPanel.add(save);

              // add average rating
              Double avgRating = GameThoughts.getAvgRating(VideoGames.getGameID(gameChosen));
              JLabel avgLabel = new JLabel("Average Rating: " + Double.toString(avgRating));
              results.remove(ratingPanel);
              ratingPanel.remove(avgLabel);
              ratingPanel.add(avgLabel);

              results.add(ratingPanel);

              // make likes panel with button and number of likes
              JPanel likesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

              JButton like = new JButton();
              if (GameThoughts.getPref(VideoGames.getGameID(gameChosen), username) == null
                  || GameThoughts.getPref(VideoGames.getGameID(gameChosen), username) == 0) {
                like.setText("Like");
              } else {
                like.setText("Unlike");
              }

              like.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                  if (like.getText().equals("Like")) {
                    GameThoughts.setGamePref(VideoGames.getGameID(gameChosen), true, username);
                    like.setText("Unlike");
                  } else {
                    GameThoughts.setGamePref(VideoGames.getGameID(gameChosen), false, username);
                    like.setText("Like");
                  }
                }

              });

              likesPanel.add(like);

              Integer number = GameThoughts.numLikes(VideoGames.getGameID(gameChosen));

              if (number == null) {
                number = 0;
              }

              JLabel numLikes = new JLabel("Number of Likes: " + Integer.toString(number));

              likesPanel.add(numLikes);

              results.add(likesPanel);

              revalidate();
              repaint();
            }
          }
        });
        results.removeAll(); // remove previous results if any before adding new ones
        results.setLayout(new FlowLayout());
        JScrollPane games = new JScrollPane(list);
        games.setPreferredSize(new Dimension(800, 500));
        results.add(games);

        // switch to home page button add to screen
        homeBtn.setPreferredSize(new Dimension(180, 25));
        bottom.add(homeBtn);
        homeBtn.addActionListener(new ActionListener() {

          @Override
          public void actionPerformed(ActionEvent e) {
            switchHomePage();
          }

        });

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
        switchAccountPage(); // call method to switch pages
      }

    });

    add(searchBar, BorderLayout.NORTH);
    add(results, BorderLayout.CENTER);
    add(bottom, BorderLayout.SOUTH);

    setVisible(true);

  }
}
