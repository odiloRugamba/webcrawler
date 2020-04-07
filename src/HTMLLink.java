/**
 *  helper class to represent an HTML link
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class HTMLLink
{
    private String link;
    private String label;
    public HTMLLink(String l)
    {
        link = l;
        label = "";
    }
    public HTMLLink(String lbl, String l)
    {
        link = l;
        label = lbl;
    }
    public String getLink()
    {
        return link;
    }
    public void setLink(String l)
    {
        link = l;
    }
    public String getLabel()
    {
        return label;
    }
    public void setLabel(String l)
    {
        label = l;
    }
    public String formatLink()
    {
        return "<a href='"+ link +"'>" + label + "</a>";
    }
    public String toString()
    {
        return "<a href='"+ link +"'>" + label + "</a>";
    }

}
