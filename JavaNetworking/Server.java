package edu.brandeis.students.PiotrGalecki.Homework2.Networking;

import java.net.*;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import edu.brandeis.util.WindowCloser;

/**
 * <p>Title: Networking</p>
 * <p>Description: Simple Networking Application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Piotr Galecki
 * @version 1.0
 */

public class Server extends JFrame {
  static final int port = 30000;

  private ServerSocket server;
  private Socket connection;
  private BufferedReader input;
  private BufferedWriter output;
  private JTextArea display;

   //Create a frame. Display the server's output in a JTextArea, Create a server socket.
   public Server(){
     //call JFrame constructor
     super( "Simple File Server" );
     //display the server's output in a JTextArea
     display = new JTextArea();
     getContentPane().add( display, BorderLayout.CENTER );
     addWindowListener(new WindowCloser());
     setSize( 300, 150 );
     setVisible( true );
   }

   //Create a socket to communicate with a client. Create streams to exchange data
   //with the client. Read the file name the client wants to display and display the name
   //in the text area. Read each line of the text file and display it in the text area.
   //Write each line to the output stream and close connection.
   //If the file does not exist, inform the user about it in the text area.
   public void runServer() {

     try {
       //create server socket
       server = new ServerSocket(port);
     }
     catch (IOException e) {
       e.getMessage();
       e.printStackTrace();
       System.exit( -1);
     }

     //keep accepting client connections, one at the time
     while (true) {
       try {
         //wait for a connection from the client, init input/output streams
         connection = server.accept();
         InputStreamReader charReader = new InputStreamReader(connection.
             getInputStream());
         input = new BufferedReader(charReader);
         OutputStreamWriter charWriter = new OutputStreamWriter(connection.
             getOutputStream());
         output = new BufferedWriter(charWriter);

         String fileName = input.readLine();
         display.append("Received request for a file: " + fileName + "\n");

         BufferedReader file;
         try {
           FileInputStream fileStream = new FileInputStream(fileName);
           file = new BufferedReader(new InputStreamReader(fileStream));
         }
         catch (IOException e) {
           display.append("Cannot open the file\n");
           output.write("Cannot open the file " + fileName + "\n");
           output.flush();
           connection.close();
           continue;
         }
         display.append("File content:\n");
         while (true) {
           String s = file.readLine();
           if (s == null)
           {
             //end of file
             output.flush();
             display.append("EOF\n");
             connection.close();
             break;
           }
           output.write(s + "\n");
           display.append(s + "\n");
         }
       }
       catch (IOException e) {
         e.getMessage();
         e.printStackTrace();
         System.exit( -1);
       }
     }
   }

   public static void main( String args[] ) {
     //Create an instance of the server and make it run
     Server s = new Server();
     s.runServer();
  }
}//end of Server
