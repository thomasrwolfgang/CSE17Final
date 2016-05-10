import java.io.IOException;

public class TEST
{
    public static void main(String[] args)
        throws IOException
    {
        BitInputStream stream =
            new BitInputStream("C:/Users/trwol/Desktop/CSE17TEST.txt");
        HuffModel model = new HuffModel();
        model.initialize(stream);
        model.showCounts();
        model.showCodings();
        stream.close();
        BitInputStream stream2 =
            new BitInputStream("C:/Users/trwol/Desktop/CSE17TEST.txt");
        //model.write(stream2, "C:/Users/trwol/Desktop/OUTPUTHUFF.huff", true);
        stream.close();
        stream2.close();
        // System.out.println(IHuffModel.PSEUDO_EOF);
        // System.out.println(IHuffModel.MAGIC_NUMBER);
        BitInputStream huff =
            new BitInputStream("C:/Users/trwol/Desktop/OUTPUTHUFF.huff");
        BitOutputStream unhuff =
            new BitOutputStream("C:/Users/trwol/Desktop/OUTPUTUNHUFF.txt");

        // System.out.println(huff.read((24*4)));
        model.uncompress(huff, unhuff);
         huff.close();
         unhuff.close();
    }




    public static String encodeThisBitch(HuffBaseNode root)
    {

        if (root == null)
        {
            return null;
        }
        if (root.isLeaf())
        {
            return " "
                + (Integer.toBinaryString(((HuffLeafNode)root).element()))
                + " ";
        }
        else
        {
            String left = "0";
            String right = "0";
            if (((HuffInternalNode)root).left() != null)
            {
                left = "0";
            }
            if (((HuffInternalNode)root).right() != null)
            {
                right = "1";
            }
            return " "
                + (Integer.toBinaryString(((HuffInternalNode)root).weight()))
                + " " + left + " "
                + encodeThisBitch(((HuffInternalNode)root).left()) + " " + right
                + " " + encodeThisBitch(((HuffInternalNode)root).right()) + " ";

        }

    }
}
