import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Platform {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/ProjTest";
  static final String user = "root";
  static final String password = "sqlPass#7";

  static Integer platform_id = null; // Integer wrapper class instead of int so we can have null values
  static String platform_name = null;

  public static boolean getPlatformID(String platformName) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (platformName.trim() == "" || platformName == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPlatformIDQuery = "SELECT platform_id FROM ProjTest.platform "
          + "WHERE platform_id = " + "\"" + platformName + "\"";
      result = statement.executeQuery(getPlatformIDQuery);

      if (result.next()) {
        platform_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to integer
      }

      if (platform_id == null) {
        System.out.println("Platform Name not found!");
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
    System.out.println("The platform_id associated with platform_name = " + platformName + " is " + platform_id);
    return true;
  }
  
  public static boolean getPlatformName(Integer platformID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (platformID == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Platform.url, Platform.user, Platform.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPlatformNameQuery = "SELECT platform_name FROM ProjTest.platform "
          + "WHERE platform_id = " + "\"" + platformID.intValue() + "\"";
      result = statement.executeQuery(getPlatformNameQuery);

      if (result.next()) {
        platform_name = result.getString(1); // convert from resultset to string
      }

      if (platform_name == null) {
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
    System.out.println("The genre_name associated with genre_id = " + platformID + " is " + platform_name);
    return true;
  }

  public static void main(String[] args) {
    getPlatformID("Wii");
    getPlatformID("X360");
    getPlatformName(1);
    getPlatformName(3);
  }

}

