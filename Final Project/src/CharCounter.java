import java.io.IOException;

// -------------------------------------------------------------------------
/**
 * Holds an ArrayList whose index's are ASCII values and the values represent
 * the counts of that ASCII values occurrence
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
    int[] table = new int[256];
    /**
     * Number of bits per word
     */
    final int BITS_PER_WORD = 8;


    // ----------------------------------------------------------
    /**
     * Create a new CharCounter object.
     */
    public CharCounter()
    {
        for (int i = 0; i <= 255; i++)
        {
            table[i] = 0;
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the count associated with a specific character's ASCII value
     *
     * @return count of specified chunk
     * @param ch
     *            is the chunk/character's ASCII value for which count is
     *            requested
     */
    public int getCount(int ch)
    {
        return table[ch];
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
    public int countAll(BitInputStream stream)
        throws IOException
    {
        int bits;
        int count = 0;
        while ((bits = stream.read(BITS_PER_WORD)) != -1)
        {
            table[bits] = table[bits] + 1;
            System.out.println((char)bits + "\t" + table[bits]);
            count++;
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
        table[i] = table[i] + 1;
    }


    // ----------------------------------------------------------
    /**
     * Sets the count of a specific ASCII value to a specific count
     *
     * @param i
     *            the ASCII value of the character's whose count is being
     *            changed
     * @param value
     *            the count that the ascii's value count will be updated to
     */
    public void set(int i, int value)
    {
        table[i] = value;
    }


    // ----------------------------------------------------------
    /**
     * Sets all counts for all ASCII values to 0
     */
    public void clear()
    {
        for (int i = 0; i <= 255; i++)
        {
            table[i] = 0;
        }
    }
}
