import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

/**
 *  class with helpful static methods for working with URLs
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class URLUtils

//http://public.africa.local.cmu.edu/cbishop/pfun/.phase2/omutuyinZ1d/
{
    public static String getBaseURL(String url)
    {
        int lastIndexOfBaseUrll;
        lastIndexOfBaseUrll = url.lastIndexOf("/");
        
        return url.substring(0,lastIndexOfBaseUrll);
    }
    public static LinkedList<HTMLLink> getAllLinksInLine(String line)
    {
        LinkedList<HTMLLink> l = new LinkedList<HTMLLink>();
        HTMLLink htmlLink;
        String link, label;
        int nextStart;
        int nextEnd;
        
        int nextHref = line.indexOf("href=");
        while(nextHref != -1)
        {
            //get one link at a time
            link = line.substring(nextHref + 5, line.indexOf(">", nextHref));
            link = link.replaceAll("\"", "");
            
            //get the previous link's label
            nextStart = line.indexOf(">",nextHref);
            nextEnd = line.indexOf("<",nextHref);
            if(nextStart != -1 && nextEnd != -1)
                label = line.substring(nextStart + 1 ,nextEnd);
            else
                label = "";
            label = label.replaceAll("(<.*>)", "");
            label = label.trim();
            //put them in an HTML link
            htmlLink = new HTMLLink(label, link);
            //add to linked list
            l.add(htmlLink);
            nextHref = line.indexOf("href=", nextHref+5);
        }
        return l;
    }
    public static String sanitize(String link)
    {
        String temp = link;
        
        if(temp.contains("#"))
            temp = temp.substring(0,temp.indexOf("#"));
        
        if(temp.endsWith("/"))
            temp += "index.html";
        if(!temp.endsWith(".html")) 
            return "";
        return temp;
    }
}

