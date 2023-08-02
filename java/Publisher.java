import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Publisher {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  // static Integer publisher_id = null; // Integer wrapper class instead of int so we can have null values
  // static String publisher_name = null;

  public static Integer getPublisherID(String publisherName) {
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
        publisher_id = Integer.valueOf(result.getString(1)); // convert from resultset to string to integer
      }

      if (publisher_id == null) {
        System.out.println("Publisher Name not found!");
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
    System.out.println("The publisher_id associated with publisher_name = " + publisherName + " is " + publisher_id);
    return publisher_id;
  }
  
  public static String getPublisherName(Integer publisherID) {
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
    System.out.println("The publisher_name associated with publisher_id = " + publisherID + " is " + publisher_name);
    return publisher_name;
  }

  public static ArrayList<Integer> searchByPublisher(String publisherName) {
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
          + "JOIN publisher pb ON vg.publisher_id = pb.publisher_id " 
          + "WHERE pb.publisher_name = " + "\"" + publisherName + "\";";
      
      result = statement.executeQuery(searchbyPublisherQuery);
      
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
    //System.out.println("Successfully added " + resultArray.size() + " game_ids to Arraylist");
    return resultArray;
  }
  
  public static void main(String[] args) {
    getPublisherID("Nintendo");
    getPublisherID("Microsoft Game Studios");
    getPublisherID("doesntexist");
    getPublisherName(30);
    getPublisherName(31);
    getPublisherName(99999);
    searchByPublisher("Microsoft Game Studios"); //should be 191
    searchByPublisher("Nintendo"); // should be 695
  }

}

