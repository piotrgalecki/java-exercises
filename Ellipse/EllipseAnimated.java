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
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;

//notice that EllipseAnimated doesn’t implement the Runnable interface
//it uses the technique of hiding //run() in an anonymous inner class.
//Feel free to implement the Runnable interface, if you like.
public class EllipseAnimated extends JComponent {
  //array of images to animate
  private Image[] frameList;

  //the number of milliseconds that each frame should be shown before flipping to the next one
  private long msPerFrame;

  //the current index into the frameList
  private int currFrame;

  //The thread running in this component is referenced by internalThread
  private Thread internalThread;

  //The flag indicating whether or not a stop has been requested.
  private boolean stopRequested;

  //the constructor takes several parameters.
  //The component’s width in pixels is passes through “width”.
  //Its height is passed though “height”.
  //The number of milliseconds it should take for the ellipse
  //to be squashed down from the full height is specified through “msPerCycle”.
  //The number of frames to be flipped through per second is “framesPerSec”.
  //The color with which the ellipse should be drawn is specified by “fgColor”.
  public EllipseAnimated(int width, int height, long msPerCycle,
                         int framesPerSec, Color fgColor) {
    super();

    //Set the preferred size of the component based on the passed width and height.
    Dimension frameSize = new Dimension(width, height);
    setMinimumSize(frameSize);
    setPreferredSize(frameSize);
    setMaximumSize(frameSize);
    setSize(frameSize);

    //Calculate the number of images to generate for a full cycle
    //from tall to flat based on the passed parameters.
    int framesPerCycle = (int) ( ( framesPerSec * msPerCycle ) / 1000);

    //The images to use are program generated and in frameList,
    //which is returned after call to buildImages(width, height, fgColor, framesPerCycle)
    frameList = buildImages(width, height, fgColor, framesPerCycle);

    //Calculate the time the frame should be shown
    msPerFrame = 1000L / framesPerSec;

    //Initialize currFrame and stopRequested
    currFrame = 0;
    stopRequested = false;

    //Use anonymous inner class to create a Runnable object.
    //In the inner class run() is implemented.
    //The private method runWork() is called from within a try/catch block.
    //Any exception that slips all the way up through runWork() is caught
    //has its stack printed.

    Runnable r = new Runnable() {
       public void run() {
         try {
           runWork();
         } catch ( Exception x ) {
           // in case ANY exception slips through
           x.printStackTrace();
         }
       }
    };

    //The Runnable that is created is passed as a parameter
    //to the Thread constructor which creates internalThread.
    //Then internalThread is started.
    internalThread = new Thread(r, "InternalFramesAnimation");
    internalThread.start();
  }

  //This method creates all the frames to be flipped through during animation.
  private Image[] buildImages(int width,
                              int height,
                              Color color,
                              int count) {

    BufferedImage[] im = new BufferedImage[count];

    for ( int i = 0; i < count; i++ ) {
      im[i] = new BufferedImage(
          width, height, BufferedImage.TYPE_INT_ARGB);

      double xShape = 0.0;
      double yShape = ( (double) ( i * height ) ) / (double) count;

      double wShape = width;
      double hShape = 2.0 * ( height - yShape );
      Ellipse2D shape = new Ellipse2D.Double(xShape, yShape, wShape, hShape);

      Graphics2D g2 = im[i].createGraphics();
      g2.setColor(color);
      g2.fill(shape);
      g2.dispose();
    }

    return im;
  }

  //This method controls which frame to display.
  private void runWork() {
    //Each time through the “while” loop
    //a check is done to see if a stop has been requested.
    while ( stopRequested == false) {
       try {
         //If not, the frame number is incremented and wrapped around
         //back to 0 if the last frame has been shown.
         currFrame = ( currFrame + 1 ) % frameList.length;

         //After the frame number is increased,
         //a repaint request is submitted to the event queue
         //so that the new frame is drawn as soon as possible.
         //System.out.println(this + ":repainting frame " + currFrame);
         repaint();

          //Before looping again, the internal thread sleeps for the interframe interval.
          Thread.sleep(msPerFrame);

          //If this sleep is interrupted (probably by the stopRequest()),
          //the InterruptedException is caught.
          //Inside the catch block the current thread is reinterrupted
          //in case any other interruptible, blocking statements are encountered
          //before the thread gets chance to die.
       } catch ( InterruptedException x ) {
          Thread.currentThread().interrupt();
       }
    }
  }

  //This method sets the noStopRequested to the appropriate value
  //and interrupts the internal thread in case it is blocked
  //or an interruptible statement.
  //In this program, the thread spends most of the time sleeping
  //and the interrupt will wake up it early take notice of the stop request.
  public void stopRequest() {
    stopRequested = true;
    internalThread.interrupt();
  }

  //This method is invoked by the event handling thread
  //whenever there is a request to repaint the component.
  //It simply draws the image indicated by currFrame onto the component.
  public void paint(Graphics g) {
    //System.out.println("drawing frame" + currFrame);
    g.drawImage(frameList[currFrame], 0, 0, this);
  }
}
