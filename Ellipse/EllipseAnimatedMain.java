package edu.brandeis.students.PiotrGalecki.Homework2.Ellipse;

/**
 * <p>Title: Ellipse</p>
 * <p>Description: Animated Ellipse</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Piotr Galecki
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//import edu.brandeis.util.WindowCloser;

public class EllipseAnimatedMain extends JPanel {
  static EllipseAnimated blueEllipse;
  static EllipseAnimated redEllipse;

  public EllipseAnimatedMain() {
    //create a blue and a red EllipseAnimated object
    blueEllipse = new EllipseAnimated(150, 150, 3000L, 100, Color.blue);
    redEllipse = new EllipseAnimated(150, 200, 2000L, 100, Color.red);

    //Set the appropriate layout for the  EllipseAnimatedMain panel
    setLayout(new GridBagLayout());

    //and add both blueEllipse and redEllipse to this panel
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.anchor = GridBagConstraints.SOUTH;

    add(blueEllipse, gbc);
    add(redEllipse, gbc);
  }

  public static void main(String[] args) {
    //Create a frame to host the GUI part of the application.
    //The frame will host a panel with the EllipseAnimatedMain object and a panel with two buttons.
    //Each button will allow the user to stop internal thread corresponding to the blue or red ellipse.
    //Look at the screenShots below and select the appropriate layout manager.
    JFrame f = new JFrame("Animated Ellipse");
    f.getContentPane().setLayout(new BorderLayout(0, 4));
    f.getContentPane().setBackground(Color.black);

    //ellipse panel
    EllipseAnimatedMain mainPanel = new EllipseAnimatedMain();

    //button panel
    JPanel p = new JPanel(true);
    JButton blueButton = new JButton("Stop Blue Ellipse");
    p.add(blueButton);
    JButton redButton = new JButton("Stop Red Ellipse");
    p.add(redButton);

    //add panels to the frame
    f.getContentPane().add(mainPanel, BorderLayout.CENTER);
    f.getContentPane().add(p, BorderLayout.SOUTH);

    //Add action listener to each button as anonymous inner class
    blueButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        blueEllipse.stopRequest();
      }
    });
    redButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        redEllipse.stopRequest();
      }
    });

    //Pack the frame, set its size, and make visible.
    f.pack();
    f.setResizable(false);
    f.setVisible(true);

    //Make sure the frame can be closed by the user.
    //f.addWindowListener(new WindowCloser());

    f.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        blueEllipse.stopRequest();
        redEllipse.stopRequest();
        Window w = e.getWindow();
        w.setVisible(false);
        w.dispose();
        System.exit(0);
      }
    });
  }
}
