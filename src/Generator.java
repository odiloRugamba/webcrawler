import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**

 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class Generator implements ActionListener
{
    private StringHandler callback;

    public Generator(StringHandler call)
    {
        callback = call;
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        JButton button = (JButton) arg0.getSource();

        try
        {
            callback.process(button.getText());
        } catch (Exception e)
        {
            System.out.println("error on button! please try again" + button.getText());
        }
    }

}
