import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class HuffModel
    implements IHuffModel
{
    CharCounter counter = new CharCounter();


    public void initialize(InputStream stream)
    {
        counter.countAll(stream);
    }


    public void showCodings()
    {

    }


    public void showCounts()
    {
        for (int i = 0; i <= 256; i++)
        {
            System.out.println(i + "\t " + counter.getCount(i));
        }
    }


    public void write(InputStream stream, File file, boolean force)
    {
        // TODO Auto-generated method stub

    }


    public void setViewer(HuffViewer viewer)
    {
        // TODO Auto-generated method stub

    }


    public void uncompress(InputStream in, OutputStream out)
    {
        // TODO Auto-generated method stub

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
}
