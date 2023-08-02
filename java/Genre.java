import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Genre {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  // static Integer genre_id = null; // Integer wrapper class instead of int so we can have null
  // values
  // static String genre_name = null;

  public static Integer getGenreID(String genreName) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    Integer genre_id = null;

    if (genreName.trim() == "" || genreName == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getGenreIDQuery =
          "SELECT genre_id FROM project.genre " + "WHERE genre_name = " + "\"" + genreName + "\"";
      result = statement.executeQuery(getGenreIDQuery);

      if (result.next()) {
        genre_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to
                                                         // integer
      }

      if (genre_id == null) {
        System.out.println("Genre " + genreName + " not found!");
        return null;
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
    System.out
        .println("The genre_id associated with genre_name = " + genreName + " is " + genre_id);
    return genre_id;
  }

  public static String getGenreName(Integer genreID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    String genre_name = null;

    if (genreID == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getGenreNameQuery = "SELECT genre_name FROM project.genre " + "WHERE genre_id = "
          + "\"" + genreID.intValue() + "\"";
      result = statement.executeQuery(getGenreNameQuery);

      if (result.next()) {
        genre_name = result.getString(1); // convert from resultset to string
      }

      if (genre_name == null) {
        System.out.println("Genre ID " + genreID + " not found!");
        return null;
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
    System.out
        .println("The genre_name associated with genre_id = " + genreID + " is " + genre_name);
    return genre_name;
  }

  public static ArrayList<Integer> searchByGenre(String genreName) {
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
      String searchbyGenreQuery = "SELECT vg.game_id FROM video_games vg "
          + "JOIN genre g ON vg.genre_id = g.genre_id " 
          + "WHERE g.genre_name = " + "\"" + genreName + "\";";
      result = statement.executeQuery(searchbyGenreQuery);
      
      while (result.next()) {
        resultArray.add(result.getInt(1));
      }
      
//      testing code
//      for (int i=0; i<10;i++) { // testing if all the correct items got added
//        System.out.println(resultArray.get(i));
//      }
       
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
    //System.out.println("Successfully added " + resultArray.size() + " game_ids to Arraylist");
    return resultArray;
  }

  public static void main(String[] args) {
    getGenreID("Sports");
    getGenreID("Platform");
    getGenreID("doesntexist"); // shouldnt work
    getGenreName(13910);
    getGenreName(13908);
    getGenreName(3); // shouldnt work
    searchByGenre("Sports"); // should be 2306 
    searchByGenre("Action"); // should be 3297
  }

}
