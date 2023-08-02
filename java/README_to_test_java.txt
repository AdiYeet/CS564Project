I'm uploading java code that uses hard coded "users" with an username and password with auto-generating user_id.
BEFORE YOU TRY TO RUN THE FILE on your IDE make ALL OF THE following changes to the code (do not skip any steps before trying to run otherwise you will run into errors):

1) Create a new Java project and after you add the Users.java file to your src folder/ default package, download the jar (click on the button that says jar 2.4 mb next to the Files section in the tables at the top and the download should start) from the following link: https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.1.0
JDBC WILL NOT WORK without this jar file so make sure you add it to your Java Project Build Path

2) Make sure the schema where your "users" table is located on your mysql is specifically called "project" since that is what I named mine and trying to change that will mean changing a bunch of unnecessary lines of code

3) In the first line after "public class Users {" you will see a section where I am initializing strings for "mysql login". In there the url and user section should be fine unchanged but just check them out once. Change the password string to exactly whatever your mysql root password is.

4) Make sure your users table is setup to be where there are 3 columns (user_id, username, password) where all the following are ticked:
user_id -> INT, Primary Key, Not Null, Unique, and Auto Increment
username -> VARCHAR(45) (im not specifically checking for the username to be less than 45 characters yet but can implement that), Not Null, and Unique
password -> VARCHAR(32) (same here, not checking for 32 but checking for it being atleast 8 characters long with atleast 1 digit), and Not Null

5) Im using JDK 17.0.4.1 dont know how much of an effect other java versions will have but this is mine

Im not sure if I forgot any steps but just try running the code after you do all these steps and if you run into any errors just lmk.
