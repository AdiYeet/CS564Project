import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Genre {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/ProjTest";
  static final String user = "root";
  static final String password = "";

  static Integer genre_id = null; // Integer wrapper class instead of int so we can have null values
  static String genre_name = null;

  public static boolean getGenreID(String genreName) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (genreName.trim() == "" || genreName == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getGenreIDQuery = "SELECT genre_id FROM ProjTest.genre "
          + "WHERE genre_id = " + "\"" + genreName + "\"";
      result = statement.executeQuery(getGenreIDQuery);

      if (result.next()) {
        genre_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to integer
      }

      if (genre_id == null) {
        System.out.println("Genre not found!");
        return false;
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
    System.out.println("The genre_id associated with genre_name = " + genreName + " is " + genre_id);
    return true;
  }
  
  public static boolean getGenreName(Integer genreID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (genreID == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getGenreNameQuery = "SELECT genre_name FROM ProjTest.genre "
          + "WHERE genre_id = " + "\"" + genreID.intValue() + "\"";
      result = statement.executeQuery(getGenreNameQuery);

      if (result.next()) {
        genre_name = result.getString(1); // convert from resultset to string
      }

      if (genre_name == null) {
        System.out.println("Genre not found!");
        return false;
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
    System.out.println("The genre_name associated with genre_id = " + genreID + " is " + genre_name);
    return true;
  }

  public static void main(String[] args) {
    getGenreID("Sports");
    getGenreID("Platform");
    getGenreName(1);
    getGenreName(3);
  }

}
