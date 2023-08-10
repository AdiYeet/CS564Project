import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * VideoGames class that defines all the methods necessary for the video_games table
 * 
 * @author adivakharia, kjhunjhunwa2, tmjohnson32
 *
 */
public class VideoGames {

  /**
   * returns the game_id associated with a given game_name
   * 
   * @param gameName
   * @return
   */
  public static Integer getGameID(String gameName) {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet result = null;
    Integer gameID = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      String findID = "SELECT game_id FROM project.video_games WHERE game_name = ?";
      preparedStatement = connection.prepareStatement(findID);
      preparedStatement.setString(1, gameName);

      result = preparedStatement.executeQuery();
      if (result.next()) {
        gameID = result.getInt("game_id");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close statement idk why yet but you have to
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return null;
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return null;
        }
      }
    }

    return gameID;
  }

  /**
   * Method that returns all games that are exact matches of the keyword or contain the keyword in
   * part
   * 
   * @param keyword - word that we search for
   * @return - Arraylist of all the game ids that were a match
   */
  public static ArrayList<Integer> searchByName(String keyword) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    ArrayList<Integer> resultArray = new ArrayList<>();

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String searchbyPublisherQuery = "SELECT game_id FROM video_games " + "WHERE game_name = "
          + "\"" + keyword + "\"" + " OR game_name LIKE " + "\"%" + keyword + "%\"";

      result = statement.executeQuery(searchbyPublisherQuery);

      while (result.next()) {
        resultArray.add(result.getInt(1));
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close resources
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return null;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return null;
        }
      }
    }
    return resultArray;
  }

  /**
   * Finds the game name, platform name, genre, publisher name, global sales as well as year
   * released and stores them in an array
   * 
   * @param gameID
   * @return
   */
  public static String[] returnAllData(int gameID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    String resultArray[] = new String[6];

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String returnAllQuery =
          "SELECT vg.game_name, vg.global_sales, vg.year, p.platform_name, g.genre_name, pub.publisher_name "
              + "FROM video_games vg " + "JOIN platform p ON vg.platform_id = p.platform_id "
              + "JOIN genre g ON vg.genre_id = g.genre_id "
              + "JOIN publisher pub ON vg.publisher_id = pub.publisher_id " + "WHERE vg.game_id = "
              + "\"" + gameID + "\";";

      result = statement.executeQuery(returnAllQuery);

      if (result.next()) {
        resultArray[0] = result.getString("game_name");
        resultArray[1] = result.getString("platform_name");
        resultArray[2] = result.getString("year");
        resultArray[3] = result.getString("genre_name");
        resultArray[4] = result.getString("publisher_name");
        resultArray[5] = result.getString("global_sales");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close resources
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return null;
        }
      }

      // close connection
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return null;
        }
      }
    }
    return resultArray;
  }

  /**
   * Takes in the arraylist of game ids and calls the returnAllData() method on them
   * 
   * @param gameIDList
   * @return
   */
  public static ArrayList<String[]> listToAllData(ArrayList<Integer> gameIDList) {
    ArrayList<String[]> listOfGameArrays = new ArrayList<>();
    for (int i = 0; i < gameIDList.size(); i++) {
      listOfGameArrays.add(returnAllData(gameIDList.get(i)));
    }
    return listOfGameArrays;
  }
}
