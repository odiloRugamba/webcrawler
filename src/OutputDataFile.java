import java.io.*;

/**
 *  OutputDataFile is an class that represents a datafile for output.
 *
 *  @author Cathy Bishop
 */

public class OutputDataFile
{

private String      filename;
private PrintWriter outWriter = null;
private boolean     append = false;

public OutputDataFile(String fname)
{
    filename = fname;
}

public OutputDataFile(String fname, boolean isAppend)
{
    this(fname);
    append = isAppend;
}

public String getName()
{
    return(filename);
}

public PrintWriter getWriter()
{
    return(outWriter);
}

public String toString()
{
    String str = "Filename: " + filename;
    if (isOpen())
    {
        str += " is open,";
        str += " appending? " + append;
    }
    else
    {
        str += " is NOT open.";
    }

    return(str);
}

public boolean isAppend()
{
    return(append);
}

public boolean isOpen()
{
    if (outWriter == null)
        return(false);
    else
        return(true);
}

public boolean open()
{
    if (filename == null)
        return(false);

    try
    {
        if (append)
            outWriter = new PrintWriter(new FileOutputStream(filename, true));
        else
            outWriter = new PrintWriter(new FileOutputStream(filename));
    }
    catch    (IOException e)
    {
        outWriter = null;
        return(false);
    }

    return(true);
}

public void println(String s)
{
        outWriter.println(s);
}

public void println(double d)
{
        outWriter.println(d);
}

public void println(int i)
{
        outWriter.println(i);
}

public void close()
{
    outWriter.close();
    outWriter = null;
}
}