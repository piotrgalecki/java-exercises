package edu.brandeis.students.PiotrGalecki.Homework2.ScratchPad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import edu.brandeis.util.WindowCloser;
import java.awt.event.ActionEvent;

/**
 * <p>Title: ScratchPad</p>
 * <p>Description: Scratch Pad Application</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author Piotr Galecki
 * @version 1.0
 */

public class ScratchPad extends JFrame implements ActionListener {

  private JTextArea t;
  String fontNames[] =
     { "TimesRoman", "Courier", "Helvetica" };
  String colorNames[] =
     { "Black", "Red", "Green", "Blue" };
  Color colorValues[] =
     { Color.black, Color.red, Color.green, Color.blue };

  private JMenuBar bar;
  private JMenu formatMenu, fontSubMenu, colorSubMenu, helpMenu;
  private JCheckBoxMenuItem readOnly;
  private JToolBar toolbar;
  AboutDialog aboutDialog;

  Action timesRomanAction, courierAction, helveticaAction;
  Action blackAction, redAction, greenAction, blueAction;

  public ScratchPad() {
    //setup application frame
    setTitle( "Scratch Pad" );
    addWindowListener(new WindowCloser());
    setSize(550, 300);

    //add text area
    getContentPane().setLayout(new BorderLayout());
    t = new JTextArea("\n\nThis is some initial text to test the Scratch Pad program\n", 10, 30);
    getContentPane().add(t, BorderLayout.CENTER);
    Font f = new Font(fontNames[0], Font.PLAIN, 16);
    t.setFont(f);
    t.setForeground(colorValues[0]);

    //build help about dialog
    aboutDialog = new AboutDialog(this);

    //create actions
    timesRomanAction = new FontAction(fontNames[0],
       new ImageIcon("../../../../HW2/SwingComponents/Times.gif"));
    courierAction = new FontAction(fontNames[1],
       new ImageIcon("../../../../HW2/SwingComponents/Courier.gif"));
    helveticaAction = new FontAction(fontNames[2],
       new ImageIcon("../../../../HW2/SwingComponents/Helvetica.gif"));
    timesRomanAction.setEnabled(false);

    blackAction = new ColorAction(colorNames[0],
       new ImageIcon( "../../../../HW2/SwingComponents/black-ball.gif" ));
    redAction = new ColorAction(colorNames[1],
       new ImageIcon( "../../../../HW2/SwingComponents/red-ball.gif" ));
    greenAction = new ColorAction(colorNames[2],
       new ImageIcon( "../../../../HW2/SwingComponents/green-ball.gif" ));
    blueAction = new ColorAction(colorNames[3],
       new ImageIcon( "../../../../HW2/SwingComponents/blue-ball.gif" ));
    blackAction.setEnabled(false);

    Action aboutAction = (Action)new AbstractAction("About") {
      public void actionPerformed(ActionEvent e) {
        aboutDialog.show();
      }
    };

    //build the font sub-menu
    fontSubMenu = new JMenu( "Font" );
    fontSubMenu.setMnemonic( 'o' );

    //add font sub-menu items
    JMenuItem item;
    item = fontSubMenu.add(timesRomanAction);
    item.setIcon((Icon)timesRomanAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('T');
    item.setActionCommand((String)timesRomanAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.ALT_MASK));

    item = fontSubMenu.add(courierAction);
    item.setIcon((Icon)courierAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('C');
    item.setActionCommand((String)courierAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));

    item = fontSubMenu.add(helveticaAction);
    item.setIcon((Icon)helveticaAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('V');
    item.setActionCommand((String)helveticaAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.ALT_MASK));

    //build the color sub-menu
    colorSubMenu = new JMenu( "Color" );
    colorSubMenu.setMnemonic( 'C' );

    //add color sub-menu items
    item = colorSubMenu.add(blackAction);
    item.setIcon((Icon)blackAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('B');
    item.setActionCommand((String)blackAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.ALT_MASK));

    item = colorSubMenu.add(redAction);
    item.setIcon((Icon)redAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('R');
    item.setActionCommand((String)redAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));

    item = colorSubMenu.add(greenAction);
    item.setIcon((Icon)greenAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('G');
    item.setActionCommand((String)greenAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));

    item = colorSubMenu.add(blueAction);
    item.setIcon((Icon)blueAction.getValue(Action.SMALL_ICON));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('L');
    item.setActionCommand((String)blueAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));

    //add the format menu
    formatMenu = new JMenu( "Format" );
    formatMenu.setMnemonic( 'F' );
    //formatMenu.addMenuListener(this);
    formatMenu.add( fontSubMenu );
    formatMenu.add( colorSubMenu );

    //add read only checkbox
    readOnly = new JCheckBoxMenuItem();
    readOnly.setText( "Read Only" );
    readOnly.setMnemonic( 'O' );
    readOnly.setActionCommand("Read Only");
    readOnly.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.ALT_MASK));
    readOnly.addActionListener( this );

    formatMenu.addSeparator();
    formatMenu.add(readOnly);

    //add the help menu
    helpMenu = new JMenu( "Help" );
    helpMenu.setMnemonic( 'H' );

    item = helpMenu.add(aboutAction);
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.setMnemonic('A');
    item.setActionCommand((String)aboutAction.getValue(Action.NAME));
    item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));

    //add the menu bar
    bar = new JMenuBar();
    setJMenuBar(bar);
    bar.add( formatMenu );
    bar.add( helpMenu );

    //create toolbar
    toolbar = new JToolBar();
    getContentPane().add(toolbar, BorderLayout.NORTH);

    //add font buttons
    JButton button;
    button = toolbar.add(timesRomanAction);
    button.setText(fontNames[0]);
    button.setMnemonic('T');
    button.setActionCommand((String)timesRomanAction.getValue(Action.NAME));
    button.setToolTipText("Set text font to TimesRoman");

    button = toolbar.add(courierAction);
    button.setText(fontNames[1]);
    button.setMnemonic('C');
    button.setActionCommand((String)courierAction.getValue(Action.NAME));
    button.setToolTipText("Set text font to Courier");

    button = toolbar.add(helveticaAction);
    button.setText(fontNames[2]);
    button.setMnemonic('v');
    button.setActionCommand((String)helveticaAction.getValue(Action.NAME));
    button.setToolTipText("Set text font to Helvetica");

    toolbar.addSeparator();

    //add color buttons
    button = toolbar.add(blackAction);
    button.setText(colorNames[0]);
    button.setMnemonic('B');
    button.setActionCommand((String)blackAction.getValue(Action.NAME));
    button.setToolTipText("Set text color to black");

    button = toolbar.add(redAction);
    button.setText(colorNames[1]);
    button.setMnemonic('R');
    button.setActionCommand((String)redAction.getValue(Action.NAME));
    button.setToolTipText("Set text color to red");

    button = toolbar.add(greenAction);
    button.setText(colorNames[2]);
    button.setMnemonic('G');
    button.setActionCommand((String)greenAction.getValue(Action.NAME));
    button.setToolTipText("Set text color to green");

    button = toolbar.add(blueAction);
    button.setText(colorNames[3]);
    button.setMnemonic('l');
    button.setActionCommand((String)blueAction.getValue(Action.NAME));
    button.setToolTipText("Set text color to blue");
  }

  //public void itemStateChanged( ItemEvent e ) { }

  public static void main(String[] args) {
    //create a frame
    ScratchPad scratchPad = new ScratchPad();
    scratchPad.setVisible(true);
  }

  public void actionPerformed(ActionEvent actionEvent) {
    //action event from read only checkbox
    if (readOnly.isSelected())
    {
      //readOnly.setSelected(false);
      t.setEnabled(false);
    }
    else
    {
      //readOnly.setSelected(true);
      t.setEnabled(true);
    }
  }

  class FontAction extends AbstractAction {

     public FontAction(String fontName, Icon icon) {
        super(fontName, icon);
     }

     public void actionPerformed(ActionEvent e) {
       String fontName = e.getActionCommand();

       //set desired font
       Font f = new Font(fontName, Font.PLAIN, 16);
       t.setFont(f);

       //disable selected font
       timesRomanAction.setEnabled(!fontName.equals(fontNames[0]));
       courierAction.setEnabled(!fontName.equals(fontNames[1]));
       helveticaAction.setEnabled(!fontName.equals(fontNames[2]));
     }
   }

   class ColorAction extends AbstractAction {

      public ColorAction(String colorName, Icon icon) {
         super(colorName, icon);
      }

      public void actionPerformed(ActionEvent e) {
        String colorName = e.getActionCommand();
        //set desired clor
        for (int i = 0; i < colorNames.length; i++)
        {
          if (colorName.equals(colorNames[i]))
          {
            t.setForeground(colorValues[i]);
            break;
          }
        }
        //disable selected color
        blackAction.setEnabled(!colorName.equals(colorNames[0]));
        redAction.setEnabled(!colorName.equals(colorNames[1]));
        greenAction.setEnabled(!colorName.equals(colorNames[2]));
        blueAction.setEnabled(!colorName.equals(colorNames[3]));
      }
    }
} //end of class ScratchPad

class AboutDialog extends JDialog {
   public AboutDialog( JFrame parentFrame )
   {
     super(parentFrame, "About Scratch Pad");
     getContentPane().setLayout(new BorderLayout());
     JLabel txt = new JLabel("   Scratch Pad. Version 1.0");
     getContentPane().add(txt, BorderLayout.CENTER);
     setSize(200, 100);
   }
}
