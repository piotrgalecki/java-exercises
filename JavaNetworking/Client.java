package edu.brandeis.students.PiotrGalecki.Homework2.Networking;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import edu.brandeis.util.WindowCloser;
import java.awt.*;

/**
 * <p>Title: Networking</p>
 * <p>Description: Simple Networking Application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Piotr Galecki
 * @version 1.0
 */

public class Client extends JFrame implements ActionListener {
   private JTextField fileName;
   private JTextArea contents;
   private BufferedReader input;
   private BufferedWriter output;
   private Socket connection;
   private JPanel p;
   private JLabel label;

   //Create this label the text field and add them to the panel.
   //Create a text area to display the content of the file received from the server.
   //Add ActionListener to display the file contents when the user hits Enter key
   //in the test field after supplying a file name.
   //Create a socket to communicate with the server
   //and create input and output streams to perform this communication.
   public Client() {
     //Create a client frame.
     super( "Simple File Server Client" );
     //setSize( 300, 150 );
     addWindowListener(new WindowCloser());
     getContentPane().setLayout(new BorderLayout());

     //Create a panel to host the label “Enter file name to retrieve:"
     //and a text field for the user to enter a text file name.
     p = new JPanel();
     label = new JLabel("Enter file name to retrieve:");
     fileName = new JTextField(50);
     fileName.addActionListener(this);
     p.add(label);
     p.add(fileName);

     //display the client's output in a JTextArea
     contents = new JTextArea(20, 60);

     getContentPane().add(p, BorderLayout.NORTH);
     getContentPane().add(contents, BorderLayout.CENTER);
     pack();
     setVisible( true );
   }

   //Read each line of the text file, append “\n” to each line
   //and display each line in the text area.
   public void actionPerformed( ActionEvent e ) {
     try {
       connection = new Socket(InetAddress.getLocalHost(), Server.port);
       InputStreamReader charReader = new InputStreamReader(connection.getInputStream());
       input = new BufferedReader(charReader);
       OutputStreamWriter charWriter = new OutputStreamWriter(connection.getOutputStream());
       output = new BufferedWriter(charWriter);
     }
     catch ( IOException ioe ) {
       //ioe.printStackTrace();
       contents.append("ERROR: Cannot connect to server");
       return;
     }

     try {
       String s = e.getActionCommand();
       contents.append("Requesting file: " + s + "\n");
       output.write(s + "\n");
       output.flush();

       while (true) {
         s = input.readLine();
         if (s == null)
         {
           //end of file
           contents.append("EOF\n");
           break;
         }
         contents.append(s + "\n");
       }
     }
     catch ( IOException ioe ) {
        contents.append(ioe.getMessage() + "\n" );
        //ioe.printStackTrace();
     }
   }

   public static void main( String args[] )
   {
     Client c = new Client();
   }
 }
