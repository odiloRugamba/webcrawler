import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;

import javax.swing.JFrame;

/**
 *  This class runs SiteIndexGUI
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class SiteIndex
{
    public static void main(String args[])
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            LayoutGUI gui = new SiteIndexGUI();
            @Override
            public void run()
            {
                showGUI(gui);
            }
        });
    }
    
    
    public static void showGUI(LayoutGUI gui)
    {
        JFrame frame = new JFrame("Site Index");
        Container c = frame.getContentPane();
        
        gui.addComponents(frame);
        frame.setPreferredSize(new Dimension(3000,1050));
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        c.add(gui);
        frame.pack();
        frame.setVisible(true);
    }
    
    
}
