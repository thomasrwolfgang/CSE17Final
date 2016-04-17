import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

// -------------------------------------------------------------------------
/**
 * Holds an ArrayList whose index's are ascii values and the values represent
 * the counts of that ascii values occurence
 *
 * @author Thomas Wolfgang trw218
 * @version 2016.04.14
 */
public class CharCounter
    implements ICharCounter
{
    /**
     * Table to hold the counts for each value read by a stream
     */
    ArrayList<Integer> table = new ArrayList<Integer>(256);


    // ----------------------------------------------------------
    /**
     * Create a new CharCounter object.
     */
    public CharCounter()
    {
        for (int i = 0; i <= 256; i++)
        {
            table.set(i, 0);
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the count associated with a specific character's ascii value
     *
     * @return count of specified chunk
     * @param ch
     *            is the chunk/character's ascii value for which count is
     *            requested
     */
    public int getCount(int ch)
    {
        return table.get(ch);
    }


    // ----------------------------------------------------------
    /**
     * Initialize state by counting bits/chunks in a stream
     *
     * @param stream
     *            is source of data
     * @return count of all chucks/read
     * @throws IOException
     */
    public int countAll(InputStream stream)
        throws IOException
    {
        int bits;
        int count = 0;
        while ((bits = stream.read()) != -1)
        {
            table.set(bits, table.get(bits) + 1);
        }
        return count;
    }


    // ----------------------------------------------------------
    /**
     * Updates state to record one occurence of specified ascii chunk value
     *
     * @param i
     *            ascii value of character of whose count is being updated
     */
    public void add(int i)
    {
        table.set(i, table.get(i) + 1);
    }


    // ----------------------------------------------------------
    /**
     * Sets the count of a spefic ascii value to a specific count
     *
     * @param i
     *            the ascii value of the character's whose count is being
     *            changed
     * @param value
     *            the count that the ascii's value count will be updated to
     */
    public void set(int i, int value)
    {
        table.set(i, value);
    }


    // ----------------------------------------------------------
    /**
     * Sets all counts for all ascii values to 0
     */
    public void clear()
    {
        for (int i = 0; i <= 256; i++)
        {
            table.set(i, 0);
        }
    }
}
