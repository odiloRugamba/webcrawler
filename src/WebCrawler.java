import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

/**
 * helper class for processing ("crawling") the website.
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class WebCrawler
{
    TreeMap<String, String> unprocessedFiles = new TreeMap<String, String>();
    String tempLink;
    TreeMap<String, String> processedFiles = new TreeMap<String, String>();
    TreeMap<String, HTMLLink> htmlFileList = new TreeMap<String, HTMLLink>();
    TreeMap<String, HTMLLink> exampleFileList = new TreeMap<String, HTMLLink>();
    TreeMap<String, TreeMap<String, String>> wordsList;
    ArrayList<String> excludedWords = new ArrayList<String>();
    LinkedList<HTMLLink> linksInLine;
    URL url;
    String nextLink;
    String baseURL;
    static int iu = 1;

    public WebCrawler(String link)
    {
        link = URLUtils.sanitize(link);
        htmlFileList.put(link, new HTMLLink("home", link));
        wordsList = new TreeMap<String, TreeMap<String, String>>();
        baseURL = URLUtils.getBaseURL(link);
        updateExcludedWords();
        nextLink = link;

    }

    private void updateExcludedWords()
    {
        File f = new File("excludeWords.txt");
        String line;
        try
        {
            BufferedReader ex = new BufferedReader(new FileReader(f));
            line = ex.readLine();
            while (line != null)
            {
                excludedWords.add(line);
                line = ex.readLine();
            }
        } 
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // for links
    public void crawlForLinks()
    {

        try
        {
            BufferedReader webPage = null;
            String line = null;

            URL url = new URL(nextLink);
            webPage = new BufferedReader(new InputStreamReader(url.openStream()));

            line = webPage.readLine();

            while (line != null)
            {
                linksInLine = URLUtils.getAllLinksInLine(line);

                for (HTMLLink l : linksInLine)
                {
                    if (l.getLink().startsWith("../"))
                    {
                        // do nothing
                    }
                    // if this link is relative but does not starts with base URL
                    else if (!l.getLink().contains("http") && !l.getLink().startsWith(baseURL))
                    {
                        l.setLink(baseURL + "/" + l.getLink());
                        addLink(l);
                    }
                    // if it starts with our base URL
                    else if (l.getLink().startsWith(baseURL))
                    {
                        addLink(l);
                    }
                }
                line = webPage.readLine();

            }
            webPage.close();
            processLinks();
        } 
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }

    private void processLinks()
    {
        while (!unprocessedFiles.isEmpty())
        {
            tempLink = unprocessedFiles.pollLastEntry().getKey();
            nextLink = URLUtils.sanitize(tempLink);
            
            processedFiles.put(tempLink, tempLink);
            crawlForLinks();
        }
    }

    private void addLink(HTMLLink l)
    {
        String link = l.getLink();
        String label = l.getLabel();

        if (link.isEmpty())
            return;

        if (link.endsWith(".cpp") || link.endsWith(".h") || link.endsWith(".java"))
        {
            if (!processedFiles.containsKey(link))
            {
                processedFiles.put(link, label);
                exampleFileList.put(link, new HTMLLink(label, link));
            }
        } 
        else
        {
            link = URLUtils.sanitize(link);
            if (!processedFiles.containsKey(link) && !unprocessedFiles.containsKey(link))
            {
                if (link.isEmpty())
                    return;
                unprocessedFiles.put(link, label);
                htmlFileList.put(link, new HTMLLink(label, link));

            } 
            else
            {
                // System.out.println(link + "<--");

            }
        }

    }

    public void saveExampleFiles(String fileName)
    {

        OutputDataFile file = new OutputDataFile(fileName);
        file.open();

        for (String key : exampleFileList.keySet())
        {

            file.println("<li>" + exampleFileList.get(key).formatLink() + "</li>");

        }
        file.close();

    }

    // for words
    public void crawlForWords()
    {
        HTMLLink link;
        Set<String> keys = htmlFileList.keySet();
        for (String key : keys)
        {
            link = htmlFileList.get(key);
            crawlLink(link);
        }
    }

    private void crawlLink(HTMLLink l)
    {
        String link = URLUtils.sanitize(l.getLink());
        if (link.isEmpty())
            return;
        String label = l.getLabel();

        String line = null, wordsInLine[];
        LinkedList<HTMLLink> l1 = null;
        BufferedReader webPage = null;
        try
        {
            URL url = new URL(link);
            webPage = new BufferedReader(new InputStreamReader(url.openStream()));
            line = webPage.readLine();

            while (line != null)
            {
                wordsInLine = splitter(line);
                for (String s : wordsInLine)
                {
                    if (!excludedWords.contains(s))
                    {
                        if (wordsList.containsKey(s))
                        {
                            wordsList.get(s).put(link, label);
                        } 
                        else
                        {
                            TreeMap<String, String> n = new TreeMap<String, String>();
                            n.put(link, label);
                            wordsList.put(s, n);
                        }
                    }
                }

                line = webPage.readLine();

            }
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private String[] splitter(String str)
    {
        str = str.replaceAll("(<.*>)", "");
        str = str.replaceAll("\\p{Punct}", "");
        str = str.replaceAll("\\p{Space}", " ");
        str = str.toLowerCase();
        return str.split(" ");
    }

    public void saveWordsFiles(String fileName)
    {
        TreeMap<String, String> temp = null;
        String word = "";
        OutputDataFile file = new OutputDataFile(fileName);
        file.open();

        for (String key : wordsList.keySet())
        {
            temp = wordsList.get(key);
            word += "<p><b>" + key + " has " + temp.size() + " references<b>:<br>";
            for (String k : temp.keySet())
            {
                word += "<a href=\"" + k + "\">" + temp.get(k) + "</a><br>";
            }
            word += "</p>";

            file.println(word);
            word = "";

        }
        file.close();

    }

    public TreeMap<String, HTMLLink> getHtmlFileList()
    {

        return htmlFileList;
    }

    public TreeMap<String, HTMLLink> getexampleFileList()
    {
        return exampleFileList;
    }

    public TreeMap<String, TreeMap<String, String>> getWordsList()
    {
        return wordsList;
    }

}
