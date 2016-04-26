import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class HuffModel
    implements IHuffModel
{
    CharCounter counter = new CharCounter();
    HuffTree    tree;
    MinHeap     Hheap;


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
        tree = buildTree(counter);
        for (int i = 0; i <= 127; i++)
        {
            System.out.println(findEnCode(i, tree.root()));
        }
    }


    public String findEnCode(int ascii, HuffBaseNode node1)
    {
        HuffBaseNode node = node1;
        int freq = counter.table[ascii];
        String enCode = "";
        if (freq < node.weight())
        {
            enCode = enCode + "0";
            findEnCode(ascii, ((HuffInternalNode)node).left());
        }
        else
        {
            if (freq > node.weight())
            {
                node = ((HuffInternalNode)node).right();
                enCode = enCode + "1";
                findEnCode(ascii, ((HuffInternalNode)node).right());
            }
            else
            {
                return enCode;
            }
        }
        return Integer.toString(ascii);
    }


    public HuffTree buildTree(CharCounter counter)
    {
        HuffBaseNode[] table1 = new HuffBaseNode[128];
        ArrayList<HuffBaseNode> tempList = new ArrayList<HuffBaseNode>();


        for (int i = 0; i <= 127; i++)
        {
            if (counter.table[i] > 0)
            {
                tempList.add(new HuffLeafNode((char)i, counter.table[i]));
                //table1[i] = new HuffLeafNode((char)i, counter.table[i]);
            }
        }

        HuffBaseNode[] nodes = new HuffBaseNode[tempList.size()];
        for (int i=0; i<tempList.size(); i++) {
            nodes[i] = tempList.get(i);
        }

        Hheap = new MinHeap(nodes, 128, 128);
        HuffTree tmp1, tmp2, tmp3 = null;

        while (Hheap.heapsize() > 1)
        { // While two items left
            tmp1 = (HuffTree)Hheap.removemin();
            tmp2 = (HuffTree)Hheap.removemin();
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
        for (int i = 0; i <= 127; i++)
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
