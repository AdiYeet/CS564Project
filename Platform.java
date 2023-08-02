import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Platform {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  //static Integer platform_id = null; // Integer wrapper class instead of int so we can have null values
  //static String platform_name = null;

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
        platform_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to integer
      }

      if (platform_id == null) {
        System.out.println("Platform " + platformName + " not found!");
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
    System.out.println("The platform_id associated with platform_name = " + platformName + " is " + platform_id);
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
    System.out.println("The genre_name associated with genre_id = " + platformID + " is " + platform_name);
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
          + "JOIN platform p ON vg.platform_id = p.platform_id " 
          + "WHERE p.platform_name = " + "\"" + platformName + "\";";
      result = statement.executeQuery(searchbyPlatformQuery);
      
      while (result.next()) {
        resultArray.add(result.getInt(1));
      }
      
//      testing code
//      for (int i=0; i<10;i++) { // testing if all the correct items got added
//        System.out.println(resultArray.get(i));
//      }
//      System.out.println(resultArray.size()); // 2306 if genre=sports, 3297 if genre=action
       
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
    System.out.println("Successfully added " + resultArray.size() + " game_ids to Arraylist");
    return resultArray;
  }
  
  public static void main(String[] args) {
    getPlatformID("Wii");
    getPlatformID("X360");
    getPlatformID("doesntexist"); // shouldnt work
    getPlatformName(87);
    getPlatformName(54);
    getPlatformName(99999); // shouldnt work
    searchByPlatform("Wii"); // should be 1285
  }

}

