import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class HuffModel
    implements IHuffModel
{
    CharCounter counter   = new CharCounter();
    HuffTree    tree;
    MinHeap     Hheap;
    String[]    encodings = new String[256];


    // ----------------------------------------------------------
    public void initialize(InputStream stream)
    {
        try
        {
            counter.countAll(stream);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void showCounts()
    {
        for (int i = 0; i <= 127; i++)
        {
            if (counter.getCount(i) > 0)
                System.out.println((char)i + " count: " + counter.getCount(i));
        }
    }


    public void showCodings()
    {
        tree = buildTree(counter);
        this.findEncodings(tree.root(), "");
        // printTree(tree.root());
        for (int i = 0; i < 255; i++)
        {
            if (counter.getCount(i) > 0)
            {
                System.out
                    .println("Encoding for " + (char)i + ": " + encodings[i]);
            }
        }
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param node
     *            starting node
     * @param path
     *            encoding path
     */
    public void findEncodings(HuffBaseNode node, String path)
    {
        if (node.isLeaf())
        {
            encodings[((HuffLeafNode)node).element()] = path;
        }
        else
        {
            findEncodings(((HuffInternalNode)node).left(), path + '0');
            findEncodings(((HuffInternalNode)node).right(), path + '1');
        }
    }


    // ----------------------------------------------------------
    /**
     * Place a description of your method here.
     *
     * @param counter
     * @return
     */
    public HuffTree buildTree(CharCounter countObj)
    {
        HuffBaseNode[] table1 = new HuffBaseNode[128];
        ArrayList<HuffTree> tempList = new ArrayList<HuffTree>();

        for (int i = 0; i <= 127; i++)
        {
            if (countObj.table[i] > 0)
            {
                tempList.add(new HuffTree((char)i, counter.table[i]));
                // table1[i] = new HuffLeafNode((char)i, counter.table[i]);
            }
        }

        HuffTree[] trees = new HuffTree[tempList.size()];
        for (int i = 0; i < tempList.size(); i++)
        {
            trees[i] = tempList.get(i);
        }

        Hheap = new MinHeap(trees, tempList.size(), 128);
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


    public void write(InputStream stream, String file, boolean force)
    {
        // BitInputStream stream2 = new BitInputStream(stream);
        // initialize(stream2);
        // tree = buildTree(counter);
        // findEncodings(tree.root(), "");
        BitOutputStream output = new BitOutputStream(file);
        output.write(IHuffModel.BITS_PER_INT, IHuffModel.MAGIC_NUMBER);
        writeTree(tree.root(), output);
        output.write(1, 1);
        output.write(9, IHuffModel.PSEUDO_EOF);
        int inbits;
        try
        {
            while ((inbits =
                ((BitInputStream)stream).read(IHuffModel.BITS_PER_WORD)) != -1)
            {
                char[] encodes = encodings[inbits].toCharArray();
                for (int i = 0; i < encodes.length; i++)
                {
                    if (encodes[i] == '0')
                        output.write(1, 0);
                    else
                        output.write(1, 1);
                }
            }
            output.write(9, IHuffModel.PSEUDO_EOF);
            output.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();

        }

    }


    public void writeTree(HuffBaseNode node, BitOutputStream output)
    {
        if (!node.isLeaf())
        {
            output.write(1, 0);
            writeTree(((HuffInternalNode)node).left(), output);
            writeTree(((HuffInternalNode)node).right(), output);
        }
        else
        {
            output.write(1, 1);
            output.write(9, ((HuffLeafNode)node).element());

        }

    }


    // public void setViewer(HuffViewer viewer)
    {
        // TODO Auto-generated method stub

    }


    public void uncompress(InputStream input1, OutputStream output1)
    {
        BitInputStream input = (BitInputStream)input1;
        BitOutputStream output = (BitOutputStream)output1;
        try
        {
            int magic = input.read(BITS_PER_INT);

            if (magic != MAGIC_NUMBER)
            {
                throw new IOException("magic number not right");
            }
            HuffTree cTree = new HuffTree(rebuild(input));
            int bits;
            while (true)
            {
                HuffBaseNode pointer = cTree.root();
                bits = input.read(1);
                if (bits == -1)
                {
                    System.err
                        .println("should not happen! trouble reading bits1");
                    break;
                }
                else
                {
                    // use the zero/one value of the bit read
                    // to traverse Huffman coding tree
                    // if a leaf is reached, decode the character and print
                    // UNLESS
                    // the character is pseudo-EOF, then decompression done

                    if ((bits & 1) == 0)
                    {
                        pointer = ((HuffInternalNode)pointer).left();
                    }
                    if ((bits & 1) == 1)
                    {
                        pointer = ((HuffInternalNode)pointer).right();
                    }
                    if (pointer.isLeaf())
                    {
                        if (((HuffLeafNode)pointer)
                            .element() == (char)IHuffModel.PSEUDO_EOF)
                            break; // out of loop
                        else
                            output.write(
                                BITS_PER_WORD,
                                ((HuffLeafNode)pointer).element());
                        pointer = cTree.root();
                    }

                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        input.close();
        output.close();

    }


    HuffBaseNode rebuild(InputStream input1)
    {
        BitInputStream input = (BitInputStream)input1;
        HuffBaseNode temp = null;
        int bits;
        while (true)
        {
            try
            {
                if ((bits = input.read(1)) == -1)
                {
                    System.err
                        .println("should not happen! trouble reading bits2");
                    break;
                }
                else
                {
                    if (bits == 0)
                    {
                        temp = new HuffInternalNode(
                            rebuild(input),
                            rebuild(input),
                            0);
                    }
                    else
                    {
                        int charV = input.read(9);
                        if (charV == IHuffModel.PSEUDO_EOF)
                            break; // out of loop
                        else
                            return new HuffLeafNode((char)charV, 0);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return temp;
    }
}
