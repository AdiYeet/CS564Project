import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Publisher {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/ProjTest";
  static final String user = "root";
  static final String password = "sqlPass#7";

  static Integer publisher_id = null; // Integer wrapper class instead of int so we can have null values
  static String publisher_name = null;

  public static boolean getPublisherID(String publisherName) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (publisherName.trim() == "" || publisherName == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Publisher.url, Publisher.user, Publisher.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPublisherIDQuery = "SELECT publisher_id FROM ProjTest.publisher "
          + "WHERE publisher_id = " + "\"" + publisherName + "\"";
      result = statement.executeQuery(getPublisherIDQuery);

      if (result.next()) {
        publisher_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to integer
      }

      if (publisher_id == null) {
        System.out.println("Publisher Name not found!");
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
    System.out.println("The publisher_id associated with publisher_name = " + publisherName + " is " + publisher_id);
    return true;
  }
  
  public static boolean getPublisherName(Integer publisherID) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    if (publisherID == null)
      return false;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Publisher.url, Publisher.user, Publisher.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create result set and query to retrieve data from
      String getPublisherNameQuery = "SELECT publisher_name FROM ProjTest.publisher "
          + "WHERE publisher_id = " + "\"" + publisherID.intValue() + "\"";
      result = statement.executeQuery(getPublisherNameQuery);

      if (result.next()) {
        publisher_name = result.getString(1); // convert from resultset to string
      }

      if (publisher_name == null) {
        System.out.println("Publisher ID not found!");
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
    System.out.println("The publisher_name associated with publisher_id = " + publisherID + " is " + publisher_name);
    return true;
  }

  public static void main(String[] args) {
    getPublisherID("Nintendo");
    getPublisherID("Microsoft Game Studios");
    getPublisherName(1);
    getPublisherName(3);
  }

}

