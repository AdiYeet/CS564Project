import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Platform {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  public static Integer getPlatformID(String platformName) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    Integer platform_id = null;

    if (platformName.trim() == "" || platformName == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPlatformIDQuery = "SELECT platform_id FROM project.platform "
          + "WHERE platform_name = " + "\"" + platformName + "\"";
      result = statement.executeQuery(getPlatformIDQuery);

      if (result.next()) {
        platform_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to
                                                            // integer
      }

      if (platform_id == null) {
        System.out.println("Platform " + platformName + " not found!");
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
    return platform_id;
  }

  public static String getPlatformName(Integer platformID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    String platform_name = null;

    if (platformID == null)
      return null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPlatformNameQuery = "SELECT platform_name FROM project.platform "
          + "WHERE platform_id = " + "\"" + platformID.intValue() + "\"";
      result = statement.executeQuery(getPlatformNameQuery);

      if (result.next()) {
        platform_name = result.getString(1); // convert from resultset to string
      }

      if (platform_name == null) {
        System.out.println("Platform ID " + platformID + " not found!");
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
    return platform_name;
  }

  public static ArrayList<Integer> searchByPlatform(String platformName) {
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
      String searchbyPlatformQuery = "SELECT vg.game_id FROM video_games vg "
          + "JOIN platform p ON vg.platform_id = p.platform_id " + "WHERE p.platform_name = " + "\""
          + platformName + "\";";
      result = statement.executeQuery(searchbyPlatformQuery);

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

  public static ArrayList<String[]> topByPlatform() {
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
      String topByGenre = "SELECT p.platform_name, SUM(vg.global_sales) AS total_sales "
          + "FROM video_games vg " + "JOIN platform p ON vg.platform_id = p.platform_id "
          + "GROUP BY p.platform_name " + "ORDER BY total_sales DESC";

      result = statement.executeQuery(topByGenre);

      while (result.next()) {
        String platformName = result.getString("platform_name");
        double totalSales = result.getDouble("total_sales");

        String[] platformSales = {platformName, String.valueOf(totalSales)};
        returnArray.add(platformSales);
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

