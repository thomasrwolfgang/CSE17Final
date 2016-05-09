import java.io.File;
import java.io.IOException;

public class TEST
{
    public static void main(String[] args) throws IOException
    {
        BitInputStream stream =
            new BitInputStream("C:/Users/trwol/Desktop/CSE17TEST.txt");
        HuffModel model = new HuffModel();
        model.initialize(stream);
        model.showCounts();
        model.showCodings();
        File file = new File("C:/Users/trwol/Desktop/OUTPUTHUFF.huff");
        model.write(stream, "C:/Users/trwol/Desktop/OUTPUTHUFF.huff", true);
        BitInputStream huff = new BitInputStream("C:/Users/trwol/Desktop/OUTPUTHUFF.huff");
        BitOutputStream unhuff = new BitOutputStream("C:/Users/trwol/Desktop/OUTPUT.txt");
        model.uncompress(huff, unhuff);
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