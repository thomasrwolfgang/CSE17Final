import java.io.IOException;

public class TEST
{
    public static void main(String[] args)
    {
        BitInputStream stream = new BitInputStream("C:/Users/trwol/Desktop/CSE17TEST.txt");
        CharCounter counter = new CharCounter();
        try
        {
            counter.countAll(stream);
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HuffModel model = new HuffModel();
        model.initialize(stream);
        model.showCodings();
    }
}


