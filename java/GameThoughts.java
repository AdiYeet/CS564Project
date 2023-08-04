import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameThoughts {

  public static int user_id = 0; // initialize to avoid mistakes

  /**
   * sets current user id from current username in HomePage.java
   * @return 
   */
  public static boolean setCurrUserID() {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String query = "SELECT user_id FROM project.users WHERE username = " + "\""
          + HomePage.getUsername() + "\";";
      result = statement.executeQuery(query);

      if (result.next()) {
        user_id = result.getInt("user_id");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // close statement idk why yet but you have to
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return false;
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Set game rating for a specific game and specific user
   * If username already exists in the game_thoughts table then just update rating, otherwise create new entry
   * @param gameID
   * @param rating
   * @return
   */
  public static boolean setGameRating(int gameID, int rating) {
    setCurrUserID();

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet result = null;
    ResultSet checkUsernameSet = null;



    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);
      statement = connection.createStatement();

      String checkUsername = "SELECT EXISTS(SELECT * FROM project.game_thoughts WHERE user_id = "
          + "\"" + user_id + "\") AS 'check';";
      checkUsernameSet = statement.executeQuery(checkUsername);
      
      if (checkUsernameSet.next()) {
        int count = checkUsernameSet.getInt("check");
        if (count == 0) { // user_id does not already exist in the game_thoughts table
          String insertRating =
              "INSERT INTO project.game_thoughts (game_id, user_id, rating) VALUES (?, ?, ?)";
          preparedStatement = connection.prepareStatement(insertRating);


          preparedStatement.setInt(1, gameID);
          preparedStatement.setInt(2, user_id);
          preparedStatement.setInt(3, rating);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("Rating added successfully!");
          } else {
            System.out.println("Failed to add the rating.");
          }
          
          
        } else { // user_id exists in the game_thoughts table
          
          String updateRating = "UPDATE project.game_thoughts "
              + "SET rating = " + "\"" + rating + "\" "
              + "WHERE user_id = " + "\"" + user_id + "\" " 
              + "AND game_id = " + "\"" + gameID + "\";";
          statement.executeUpdate(updateRating);
        }
      }



    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // close statement idk why yet but you have to
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return false;
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * Set game preference for a specific game and specific user
   * 1 - likes game, 0 - unselected / does not "like" game
   * @param gameID
   * @param likes
   * @return
   */
  public static boolean setGamePref(int gameID, boolean likes) {
    setCurrUserID();

    int val = (likes) ? 1 : 0;
    
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet result = null;
    ResultSet checkUsernameSet = null;



    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");
      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);
      statement = connection.createStatement();

      String checkUsername = "SELECT EXISTS(SELECT * FROM project.game_thoughts WHERE user_id = "
          + "\"" + user_id + "\") AS 'check';";
      checkUsernameSet = statement.executeQuery(checkUsername);
      
      if (checkUsernameSet.next()) {
        int count = checkUsernameSet.getInt("check");
        if (count == 0) { // user_id does not already exist in the game_thoughts table
          String insertRating =
              "INSERT INTO project.game_thoughts (game_id, user_id, likes) VALUES (?, ?, ?)";
          preparedStatement = connection.prepareStatement(insertRating);


          preparedStatement.setInt(1, gameID);
          preparedStatement.setInt(2, user_id);
          preparedStatement.setInt(3, val);
          //preparedStatement.setNull(4, java.sql.Types.NULL);

          int rowsAffected = preparedStatement.executeUpdate();
          if (rowsAffected > 0) {
            System.out.println("Preference added successfully!");
          } else {
            System.out.println("Failed to add the preference.");
          }
          
          
        } else { // user_id exists in the game_thoughts table
          
          String updateRating = "UPDATE project.game_thoughts "
              + "SET likes = " + "\"" + val + "\" "
              + "WHERE user_id = " + "\"" + user_id + "\" " 
              + "AND game_id = " + "\"" + gameID + "\";";
          statement.executeUpdate(updateRating);
        }
      }



    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      // close statement idk why yet but you have to
      if (preparedStatement != null) {
        try {
          preparedStatement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
          return false;
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * gets the rating for a specific user for a specific game, if the user has set one
   * have not handled case for when a user tries to call this on a rating which they have not set yet, dont do it
   * @param gameID
   * @return
   */
  public static Integer getRating(int gameID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    
    Integer rating = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getRating = "SELECT rating FROM project.game_thoughts WHERE user_id = " + "\""
          + user_id + "\" AND game_id = " + "\"" + gameID + "\";";
      result = statement.executeQuery(getRating);

      if (result.next()) {
        rating = result.getInt("rating");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close statement idk why yet but you have to
      if (statement != null) {
        try {
          statement.close();
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
    //System.out.println(rating); // test
    return rating;
  }
  
  /**
   * gets the likes/ does not like preferenece for a game for a user if they have set one
   * does not matter if user tries to call on pref they have not set, since default is always "does not like"
   * @param gameID
   * @return
   */
  public static Integer getPref(int gameID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    
    Integer pref = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPref = "SELECT likes FROM project.game_thoughts WHERE user_id = " + "\""
          + user_id + "\" AND game_id = " + "\"" + gameID + "\";";
      result = statement.executeQuery(getPref);

      if (result.next()) {
        pref = result.getInt("likes");
      }

    } catch (Exception e) {
      e.printStackTrace();
      return null;
    } finally {
      // close statement idk why yet but you have to
      if (statement != null) {
        try {
          statement.close();
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
    //System.out.println(pref); // test
    return pref;
  }

  public static void main(String[] args) {
    //setGameRating(2, 4);
    setGameRating(2, 5);
    //setGamePref(2, true);
    setGamePref(2, false);
    
    getRating(2);
    getPref(2);
  }
}
