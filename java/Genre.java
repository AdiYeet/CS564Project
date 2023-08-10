import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Genre class that has all the specifications for the genre table with its related methods
 * 
 * @author adivakharia, kjhunjhunwa2, tmjohnson32
 *
 */
public class Genre {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  /**
   * Gets the associated genre_id for a specific genre_name in the database
   * 
   * @param genreName
   * @return
   */
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
    return genre_id;
  }

  /**
   * get the associated genre_name based on a given genre_id in the database
   * 
   * @param genreID
   * @return
   */
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
    return genre_name;
  }

  /**
   * gets a list of all games in the database that have a particular given genre
   * 
   * @param genreName
   * @return
   */
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
      String searchbyGenreQuery =
          "SELECT vg.game_id FROM video_games vg " + "JOIN genre g ON vg.genre_id = g.genre_id "
              + "WHERE g.genre_name = " + "\"" + genreName + "\";";
      result = statement.executeQuery(searchbyGenreQuery);

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
   * Returns the top genres based on total sales across platforms
   * 
   * @return
   */
  public static ArrayList<String[]> topByGenre() {
    ArrayList<String[]> returnArray = new ArrayList<>();

    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Genre.url, Genre.user, Genre.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String topByGenre = "SELECT g.genre_name, SUM(vg.global_sales) AS total_sales "
          + "FROM video_games vg " + "JOIN genre g ON vg.genre_id = g.genre_id "
          + "GROUP BY g.genre_name " + "ORDER BY total_sales DESC";

      result = statement.executeQuery(topByGenre);

      while (result.next()) {
        String genreName = result.getString("genre_name");
        double totalSales = result.getDouble("total_sales");

        String[] genreSales = {genreName, String.valueOf(totalSales)};
        returnArray.add(genreSales);
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

    return returnArray;
  }

}
