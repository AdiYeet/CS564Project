import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.regex.Pattern;

public class Users {

  // create strings to store mysql login
  static final String url = "jdbc:mysql://localhost:3306/project";
  static final String user = "root";
  static final String password = "sqlPass#7";

  /**
   * Method to add user
   * 
   * @param username
   * @param password
   * @throws SQLException
   */
  public static boolean addUser(String username, String password) throws SQLException {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    // Check that username and password are not null
    if (username == null || username.trim() == "" || password == null || password.trim() == "") {
      System.out.println("Username and Password cannot be empty!");
      return false;
    }

    // Check if password is atleast 8 characters long and has atleast 1 digit
    if (password.length() < 8) {
      System.out.println(
          "Password for username: " + username + " needs to contain atleast 8 characters!");
      return false;
    }
    if (!Pattern.compile("[0-9]").matcher(password).find()) {
      System.out.println("Password for username: " + username + " needs atleast 1 digit (0-9)");
      return false;
    }

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Users.url, Users.user, Users.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create query to add users
      String userAddQuery = "INSERT INTO project.users" + "(username,password)" + "VALUES(" + "\""
          + username + "\"" + "," + "\"" + password + "\"" + ")";

      // execute
      statement.executeUpdate(userAddQuery);

    } catch (SQLIntegrityConstraintViolationException e) {
      System.out.println(username + " already exists! Please try again with a different username");
      return false;
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
    System.out.println("User " + username + " successfully added!");
    return true;
  }

  /**
   * Method to delete user
   * 
   * @param username
   * @throws SQLException
   */
  public static boolean deleteUser(String username) throws SQLException {
    Connection connection = null;
    Statement statement = null;
    ResultSet checkUsernameSet = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Users.url, Users.user, Users.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create query to delete users
      String userDeleteQuery =
          "DELETE FROM project.users " + "WHERE username = " + "'" + username + "'";

      // Create query to check if username exists
      String checkUsername = "SELECT EXISTS(SELECT * FROM project.users WHERE username = " + "\""
          + username + "\") AS 'check';";
      checkUsernameSet = statement.executeQuery(checkUsername);
      if (checkUsernameSet.next()) {
        int count = checkUsernameSet.getInt("check");
        if (count == 0) {
          System.out.println("Username " + username + " does not exist!");
          return false;
        }
      }

      // execute
      statement.executeUpdate(userDeleteQuery);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // close statement idk why yet but you have to
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
        }
      }
    }
    System.out.println("User " + username + " successfully deleted!");
    return true;
  }

  public static boolean verifyUser(String username, String password) {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;
    ResultSet checkUsernameSet = null;
    ResultSet checkPasswordSet = null;

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Users.url, Users.user, Users.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Create query to add users
      String checkUsernameQuery = "SELECT EXISTS(SELECT * FROM project.users WHERE username = "
          + "\"" + username + "\") AS 'check';";
      checkUsernameSet = statement.executeQuery(checkUsernameQuery);
      if (checkUsernameSet.next()) {
        int count = checkUsernameSet.getInt("check");
        if (count == 0) {
          System.out.println("Username " + username + " does not exist!");
          return false;
        } else {
          String checkPasswordQuery =
              "SELECT password FROM project.users WHERE username = \"" + username + "\";";
          checkPasswordSet = statement.executeQuery(checkPasswordQuery);
          if (checkPasswordSet.next()) {
            String testPassword = checkPasswordSet.getString(1);
            if (testPassword.equals(password)) {
              System.out.println("Username and Password verified!");
              return true;
            } else {
              System.out.println("Password does not match!");
              return false;
            }
          }
        }
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

    return false;
  }

  /**
   * Prints the users table out as a string to verify changes
   * 
   * @return
   */
  public static String printUsersTable() {
    Connection connection = null;
    Statement statement = null;
    ResultSet result = null;

    String usersString = "";
    System.out.println("Table Output:");
    System.out.println("user_id | username | password");

    try {
      // Step 1: Create mysql connector class
      Class.forName("com.mysql.cj.jdbc.Driver");

      // Step 2: Initialize connection object
      connection = DriverManager.getConnection(Users.url, Users.user, Users.password);

      // Step 3: Initialize statement object
      statement = connection.createStatement();

      // Step 4: Initialize result set
      result = statement.executeQuery("SELECT * FROM project.users");

      while (result.next()) {
        usersString += result.getInt("user_id") + "       " + result.getString("username") + " "
            + result.getString("password") + "\n";
      }

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      // close statement idk why yet but you have to
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException sqlE1) {
          sqlE1.printStackTrace();
        }
      }

      // close connection same idk why yet but you have to
      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException sqlE2) {
          sqlE2.printStackTrace();
        }
      }
    }

    if (usersString == null)
      return "Table is Empty!"; // if no entries
    else
      return usersString; // if there are entries
  }

  public static void main(String[] args) throws SQLException {
    // test addUser
    /*
    addUser("  ", "  "); // should not work because empty fields
    System.out.println();

    addUser("avakharia", "goat"); // should not work because password not long enough
    System.out.println();

    addUser("kshjhun", "testpassword"); // should not work because no digits
    System.out.println();

    addUser("tjohnson", "working1"); // should work
    System.out.println();

    addUser("tjohnson", "somepassword1"); // should not work because duplicate username
    System.out.println();

    addUser("keatonlep", "somepassword2"); // should work
    System.out.println();

    addUser("nilspalumbo", "somepassword3"); // should work
    System.out.println();

    System.out.println(printUsersTable()); // should print all users without deleteUser statements

    // test delete user
    deleteUser("keatonlep"); // should remove keatonlep from database
    deleteUser("doesntexist"); // THROWS ERROR FINALLY!
    System.out.println();

    // print table
    System.out.println(printUsersTable());
    */
    
    verifyUser("tjohnson", "working1"); // should work
    verifyUser("doesntexist", "doesntexist"); // username does not exist
    verifyUser("tjohnson", "doesnt match"); // password does not match
  }

}
