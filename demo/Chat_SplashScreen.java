package demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Chat_SplashScreen extends JWindow {
  private int duration;
  
  public Chat_SplashScreen(int d) 
  {
	  duration = d;  
  }


  public void showSplash() {

		    JPanel splash = (JPanel) getContentPane();
		    splash.setBackground(Color.ORANGE);
		    
		
		    // Set the window's bounds, centring the window
		    int width = 580;
		    int height = 440;
		    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		    int x = (screen.width - width) / 2;
		    int y = (screen.height - height) / 2;
		    setBounds(x, y, width, height);
		
		    // Build the splash screen
		    JLabel label = new JLabel(new ImageIcon("cake_logo2.jpg"));
		    JProgressBar pb = new JProgressBar(0,10);
		    pb.setStringPainted(true);
		    pb.setIndeterminate(false);
		    pb.setForeground(Color.ORANGE);
		
		   
		  
		    splash.add(label, BorderLayout.NORTH);
		    setVisible(true);
		    
		    splash.setBorder(BorderFactory.createLineBorder(Color.black, 10));
		    
		
		    for (int prog=1; prog <= 10; prog++)
		    {
			    try {
			    	Thread.sleep(duration);
			    } catch (Exception e) {	
			    }
			    pb.setValue(prog);
		    }

		
		    setVisible(false);
		  }


  public void showSplashAndExit() {
    showSplash();
    dispose();
  }



}
