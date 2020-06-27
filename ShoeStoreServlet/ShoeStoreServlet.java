package edu.brandeis.PiotrGalecki.Homework1.ShoeStoreServlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class ShoeStoreServlet extends HttpServlet {
  Connection con = null;

  /**
   * Creates a persistent connection to a database.
   * init() is called when the servlet is first loaded. Use this
   * method to initialize resources.
   *
   * This method reads (from a properties file) JDBC driver info
   *  and initializes the JDBC variables.
   */
  public void init() throws ServletException {
    String jdbcDriver = null;
    String dbURL = null;
    String user = "", password = "";

    Properties prop = new Properties(); //create new Properties object

    try {
      //create a file object pointing to the properties file
       String propFileFullPath = getInitParameter("propertyFile");
       File file = new File(propFileFullPath);
      //determine if the properties file exists
      if (file.exists())
      {
        //get an input stream to the properties file
        FileInputStream fileIn = new FileInputStream(file);
        prop.load(fileIn); //load the properties object from file

        //initialize JDBC propeties
        jdbcDriver = prop.getProperty("driver");
        dbURL = prop.getProperty("url");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        System.out.println("*** PIOTR jdbcDriver = " + jdbcDriver + " dbURL = " + dbURL +
            " user = " + user + " password = " + password);
      }
    }
    catch (FileNotFoundException e) {
      System.out.println("File not found " + e.getMessage());
      e.printStackTrace();
     } catch (IOException e){
      System.out.println("IOException: " + e.getMessage());
      e.printStackTrace();
    }

   try {
      //Load the driver
      Class.forName(jdbcDriver);
      con = DriverManager.getConnection(dbURL, user, password);
    } catch (ClassNotFoundException e) {
      System.err.println("Message:  " + e.getMessage());
    }catch (SQLException ex) {
       System.err.println("-----SQLException-----");
       System.err.println("SQLState:  " + ex.getSQLState());
       System.err.println("Message:  " + ex.getMessage());
       System.err.println("Vendor:  " + ex.getErrorCode());
    }
  }

  protected void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    Statement stmt = null;
    ResultSet rs = null;
    HtmlResultSet hrs = null;

    //Set a content type
    res.setContentType("text/html");

    //Get a handle to output stream
    PrintWriter out = res.getWriter();

    try {
    //Sync is needed for Tomcat
     synchronized (this) {

       // Create a Statement object
       stmt = con.createStatement();

       // Execute an SQL query on total shoes sale greater than 80 pairs
       rs = stmt.executeQuery("select MODEL, PRICE, WEIGHT, FOOT_TYPE, " +
                              "SALES, TOTAL from SPORT_SHOES " +
                              "where (TOTAL > 80)");

       //Display the result as an HTML table
       hrs = new HtmlResultSet(rs);

       // Display the result set as a first table
       out.write("<H1>Shoes with total sales greater than 80 pairs</H1>");
       String outStr = hrs.toString();
       out.write(outStr);

       // Update the table by reducing the price by $10 for those shoes,
       // which have total sale less than or equal to 80 pairs.
       int rows = stmt.executeUpdate("update SPORT_SHOES set PRICE = PRICE - 10 " +
                                     "where (TOTAL <= 80)");
       // Get a ResultSet
       rs = stmt.executeQuery("select MODEL, PRICE, WEIGHT, FOOT_TYPE, " +
                              "SALES, TOTAL from SPORT_SHOES " +
                              "where (TOTAL <= 80)");

       //Display the result as an HTML table
       hrs = new HtmlResultSet(rs);

       // Display the result set as a second table
       out.write("<BR/><H1>Shoes with total sales <= than 80 pairs<BR/>" +
                 "after reduction of price by $10</H1>");
       outStr = hrs.toString();
       out.write(outStr);
     }
   }
   catch(SQLException e) {
     out.println("SQLException caught: " + e.getMessage());
   }
 }

 public void doDelete(HttpServletRequest req, HttpServletResponse res)
     throws ServletException, IOException {
   // Always close the database connection.
   try {
     if (con != null)
       con.close();
   }
   catch (SQLException e) {
     PrintWriter out = res.getWriter();
     out.println("SQLException caught while closing connection: " + e.getMessage());
   }
 }
}
