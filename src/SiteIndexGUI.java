import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * GUI class
 *
 * @author odilo M. Rugamba
 *
 * Andrew ID: omutuyin
 *
 * On my honor, as a Carnegie-Mellon Africa student, I have neither given nor received unauthorized assistance on this work.
 *
 */
public class SiteIndexGUI extends LayoutGUI implements StringHandler
{

    private JTextField enteredURL = new JTextField(30);
    private JButton generateIndex = new JButton("Generate Index");
    private JButton returnToWordsIndex = new JButton("Return to words index");
    private JButton returnToExamplesIndex = new JButton("Return to examples index");
    private JEditorPane words = new JEditorPane();
    private JEditorPane examples = new JEditorPane();
    private JScrollPane wordsScroll = new JScrollPane(words);
    private JScrollPane examplesScroll = new JScrollPane(examples);
    private Container c;
    File wordsIndexFileP, examplesIndexFileP;
    String baseURL, wordsFile, examplesFile;

    public void process(String text)
    {
        try
        {
            if (text.equals("Generate Index"))
            {
                generateIndex();

            }
            if (text.equals("Return to words index"))
            {
                words.setPage(wordsIndexFileP.toURI().toURL());

            }
            if (text.equals("Return to examples index"))
            {

                examples.setPage(examplesIndexFileP.toURI().toURL());

            }
        } 
        catch (Exception e)
        {
            // System.out.println();
        }

    }

    private void generateIndex()
    {

        String link = enteredURL.getText();
        baseURL = URLUtils.getBaseURL(link);
        wordsFile = getWordsFileName();
        examplesFile = getExamplesFileName();

        IndexGenerator generator = new IndexGenerator(link, wordsFile, examplesFile);

        wordsIndexFileP = new File(wordsFile);
        examplesIndexFileP = new File(examplesFile);

        try
        {
            words.setPage(wordsIndexFileP.toURI().toURL());
            examples.setPage(examplesIndexFileP.toURI().toURL());
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private String getWordsFileName()
    {
        String label;
        label = baseURL.substring(baseURL.lastIndexOf("/") + 1);
        return label + "Words.html";
    }

    private String getExamplesFileName()
    {
        String label;
        label = baseURL.substring(baseURL.lastIndexOf("/") + 1);
        return label + "Examples.html";
    }

    @Override
    public void addComponents(JFrame theFrame)
    {
        c = theFrame.getContentPane();

        c.setLayout(new FlowLayout());
        c.add(enteredURL);
        c.add(generateIndex);
        c.add(returnToWordsIndex);
        c.add(returnToExamplesIndex);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 2));

        words.setEditable(false);
        wordsScroll.setPreferredSize(new Dimension(800, 800));
        examples.setEditable(false);
        examplesScroll.setPreferredSize(new Dimension(800, 800));

        c.add(p1);
        p1.add(wordsScroll);
        p1.add(examplesScroll);

        generateIndex.addActionListener(new Generator(this));
        returnToWordsIndex.addActionListener(new Generator(this));
        returnToExamplesIndex.addActionListener(new Generator(this));
        words.addHyperlinkListener(new LinkListener(words));
        examples.addHyperlinkListener(new LinkListener(examples));
    }

}
