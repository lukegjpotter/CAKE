//Written by: Martin Farrell

package demo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;

public class Chat_CloseButton extends JPanel {
   
    public Chat_CloseButton(String ChatID) {
       
    	JLabel label = new JLabel(ChatID); 
    	
        add(label);

        JButton button = new TabButton();
        add(button);
     
    }


private class TabButton extends JButton implements ActionListener {
    public TabButton() {
        int size = 20;
        
        setPreferredSize(new Dimension(size, size));//sets the size of the button
        setToolTipText("close this tab");//sets the hover comment
        //Make the button looks the same for all Laf's
        setUI(new BasicButtonUI());//without this buttons don't work
        //Make it transparent
        setContentAreaFilled(false);//makes the background of opaque 
        //No need to be focusable
        setFocusable(false);
        setBorder(BorderFactory.createRaisedBevelBorder());
        setBorderPainted(false);//sets the border to always painted leave at false
        //Making nice rollover effect
        //use the same listener for all buttons
        //addMouseListener(buttonMouseListener);
        setRolloverEnabled(true);
        //Close the proper tab by clicking the button
        addActionListener(this);
        	
	}

	public void actionPerformed(ActionEvent e) 
    {
		int i = Chat_tabbedPane.mypane.indexOfTabComponent(Chat_CloseButton.this);
		Chat_tabbedPane.rmvtaby(i);
    }

    //paint the cross
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        //shift the image for pressed buttons
        if (getModel().isPressed()) {
            g2.translate(1, 1);//moves the x
        }
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        if (getModel().isRollover()) {
            g2.setColor(Color.RED);
        }
        int delta = 6;
        g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
        g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
        g2.dispose();
    }


private final  MouseListener buttonMouseListener = new MouseAdapter() 
{
	
    public void mouseEntered(MouseEvent e) 
    {
        Component component = e.getComponent();
        if (component instanceof AbstractButton) 
        {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(true);//paints a box around the x
        }
    }

    public void mouseExited(MouseEvent e) 
    {
        Component component = e.getComponent();
        if (component instanceof AbstractButton) 
        {
            AbstractButton button = (AbstractButton) component;
            button.setBorderPainted(false);//takes the box off from around the x 
        }
    }
};

}
}
