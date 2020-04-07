import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
/**
 * Link
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class LinkListener implements HyperlinkListener
{
    private JEditorPane editor;
    public LinkListener(JEditorPane e)
    {
        editor = e;
    }
    @Override
    public void hyperlinkUpdate(HyperlinkEvent event)
    {
        try
        {
           if(event.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
               editor.setPage(event.getURL());
        } 
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
