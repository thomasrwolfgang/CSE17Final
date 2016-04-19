import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class HuffModel
    implements IHuffModel
{
    CharCounter counter = new CharCounter();


    // ----------------------------------------------------------
    public void initialize(InputStream stream)
    {
        BitInputStream bits = null;
        try
        {
            bits = new BitInputStream(
                new FileInputStream("C:/Users/trwol/Desktop/CSE17TEST.txt"));
        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try
        {
            counter.countAll(bits);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void showCodings()
    {

    }


    public static HuffTree buildTree()
    {
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = Hheap.removemin();
            tmp2 = Hheap.removemin();
            tmp3 = new HuffTree(
                tmp1.root(),
                tmp2.root(),
                tmp1.weight() + tmp2.weight());
            Hheap.insert(tmp3); // Return new tree to heap
        }
        return tmp3; // Return the tree
    }


    public void showCounts()
    {
        for (int i = 0; i <= 256; i++)
        {
            System.out.println((char)i + "\t " + counter.getCount(i));
        }
    }


    public void write(InputStream stream, File file, boolean force)
    {
        // TODO Auto-generated method stub

    }


    // public void setViewer(HuffViewer viewer)
    {
        // TODO Auto-generated method stub

    }


    public void uncompress(InputStream in, OutputStream out)
    {
        // TODO Auto-generated method stub

    }
}
