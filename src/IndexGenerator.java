import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

/**
 * helper class for generating the index file(s)
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class IndexGenerator
{
    private String url, wordsIndexFile, examplesIndexFile;
    
    WebCrawler crawler;
    
    public IndexGenerator(String urlP, String wordsIndexFileP, String examplesIndexFileP)
    {
        url = urlP;
        wordsIndexFile = wordsIndexFileP;
        examplesIndexFile = examplesIndexFileP;
        crawler = new WebCrawler(url); 
        crawler.crawlForLinks();
        crawler.crawlForWords();
        crawler.saveExampleFiles(examplesIndexFileP);
        crawler.saveWordsFiles(wordsIndexFileP);
    }
    
    public String getUrl()
    {
        return url;
    }


    public void setUrl(String urlP)
    {
        url = urlP;
    }


    public String getWordsIndexFile()
    {
        return wordsIndexFile;
    }


    public void setWordsIndexFile(String wordsIndexFileP)
    {
        wordsIndexFile = wordsIndexFileP;
    }


    public String getExamplesIndexFile()
    {
        return examplesIndexFile;
    }


    public void setExamplesIndexFile(String examplesIndexFileP)
    {
        examplesIndexFile = examplesIndexFileP;
    }
    

    public String toString()
    {
        return "IndexGenerator: URL: "+ url +" wordsIndexFile: "+ wordsIndexFile +" examplesIndexFile:" +examplesIndexFile;
    }
    
    
}
