import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Publisher {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  public static Integer getPublisherID(String publisherName) {

    // method to get publisher ID

    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    Integer publisher_id = null;

    if (publisherName.trim() == "" || publisherName == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Publisher.url, Publisher.user, Publisher.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPublisherIDQuery = "SELECT publisher_id FROM project.publisher "
          + "WHERE publisher_name = " + "\"" + publisherName + "\"";
      result = statement.executeQuery(getPublisherIDQuery);

      if (result.next()) {
        publisher_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to
                                                             // integer
      }

      if (publisher_id == null) {
        System.out.println("Publisher Name not found!");
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
    return publisher_id;
  }

  public static String getPublisherName(Integer publisherID) {

    // method to get publisher name

    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    String publisher_name = null;

    if (publisherID == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Publisher.url, Publisher.user, Publisher.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPublisherNameQuery = "SELECT publisher_name FROM project.publisher "
          + "WHERE publisher_id = " + "\"" + publisherID.intValue() + "\"";
      result = statement.executeQuery(getPublisherNameQuery);

      if (result.next()) {
        publisher_name = result.getString(1); // convert from resultset to string
      }

      if (publisher_name == null) {
        System.out.println("Publisher ID not found!");
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
    return publisher_name;
  }

  public static ArrayList<Integer> searchByPublisher(String publisherName) {

    // method to search for all games by publisher

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
      String searchbyPublisherQuery = "SELECT vg.game_id FROM video_games vg "
          + "JOIN publisher pb ON vg.publisher_id = pb.publisher_id " + "WHERE pb.publisher_name = "
          + "\"" + publisherName + "\";";

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

  public static ArrayList<String[]> topByPublisher() {

    // method to get all publishers sorted by aggregate sales

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
      String topByGenre = "SELECT p.publisher_name, SUM(vg.global_sales) AS total_sales "
          + "FROM video_games vg " + "JOIN publisher p ON vg.publisher_id = p.publisher_id "
          + "GROUP BY p.publisher_name " + "ORDER BY total_sales DESC";

      result = statement.executeQuery(topByGenre);

      while (result.next()) {
        String publisherName = result.getString("publisher_name");
        double totalSales = result.getDouble("total_sales");

        String[] publisherSales = {publisherName, String.valueOf(totalSales)};
        returnArray.add(publisherSales);
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

