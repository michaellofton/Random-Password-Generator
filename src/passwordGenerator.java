/**
 * Class Description:
 *
 * This class acts as the model of the project. It generates a password randomly with help from
 * a pseudo random number generator (PRNG) from the SecureRandom class found in java.security.
 * This class works by:
 *      1. Determining which character sets to include based on what the user decides,
 *      2. Randomly choosing a character set (an array) to select a character from
 *      3. Randomly choosing a character from that randomly chosen character set
 *      4. Repeating steps 2 and 3 (in that order) for as many times as the password's length
 */

import java.security.SecureRandom;
import java.util.ArrayList;

public class passwordGenerator
{
    private SecureRandom randomNumGen;
    private int passwordLength;
    private ArrayList<String[]> characterSet;
    private String[] lowAlpha = new String[]
            {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
             "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
             "u", "v", "w", "x", "y", "z"};

    private String[] highAlpha = new String[]
            {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
             "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
             "U", "V", "W", "X", "Y", "Z"};

    private String[] specialChars = new String[]
            {"~", "`", "!", "@", "#", "$", "%", "^", "&", "*",
             "(", ")", "-", "_", "+", "=", "[", "{", "]", "}",
             "|", "\\", "'", "\"", ";", ":", "?", "/", ".", ">",
             "<", ","};

    private String[] numbers = new String[]
            {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};


    public passwordGenerator()
    {
        characterSet = new ArrayList<>();
    }


    public void setCharacterSet(boolean lower, boolean upper,
                                boolean special, boolean numbs)
                                throws NullPointerException
    {
    /*----------------------------------------------------------
    * Author(s): Michael Lofton, Other Name, Third Name, etc
    * Date: 12-23-17
    *
    * By using 4 given booleans representing which checkboxes were selected,
    * this function determines which character set to use when
    * randomly generating the password.
    */
        if (lower && !characterSet.contains(lowAlpha))
        {
            characterSet.add(lowAlpha);
        }
        else if (!lower && characterSet.contains(lowAlpha))
        {
            characterSet.remove(lowAlpha);
        }

        if (upper && !characterSet.contains(highAlpha))
        {
            characterSet.add(highAlpha);
        }
        else if (!upper && characterSet.contains(highAlpha))
        {
            characterSet.remove(highAlpha);
        }

        if (special && !characterSet.contains(specialChars))
        {
            characterSet.add(specialChars);
        }
        else if (!special && characterSet.contains(specialChars))
        {
            characterSet.remove(specialChars);
        }

        if (numbs && !characterSet.contains(numbers))
        {
            characterSet.add(numbers);
        }
        else if (!numbs && characterSet.contains(numbers))
        {
            characterSet.remove(numbers);
        }

        if (characterSet.size() == 0)
        {
            throw new NullPointerException();
        }
    }

    
    public String makePassword(int size)
    /*---------------------------------------
    * Author(s): 	Michael Lofton, Other Name, Third Name, etc
    * Date: 12-23-17
    *
    * Randomly chooses a character set (string array), and then randomly chooses
    * a character from that character set to append to the password. This
    * process is done a number of (passwordLength) times.
    */
    {
        passwordLength = size;
        String password = "";
        randomNumGen = new SecureRandom();

        /* POSSIBILITIES:
            ranArrayChooser =   0, 1, 2, 3
                                each number (0-3) could be any one of the following string lists:
                                lowercase, uppercase, special characters, numbers

            ranLetterIndex =    0-25 if lower/uppercase
                                0-31 if special characters
                                0-9 if numbers
         */
        for (int k = 0; k < passwordLength; ++k)
        {
            /* randomly chose an integer (starting from 0, going up to the size of the
            character set (which at max is 4) -1 (since we're choosing an index, 1-4 becomes
            0-3). Allows it to randomly choose an array depending on how many were added
            to the character set, making it flexible. */

            int ranArrayChooser;
            if ((characterSet.size() - 1) == 0)
            {
                ranArrayChooser = 0;
            }
            else
            {
                //randomly chooses array: 0 to size (size is exclusive, so size-1 by nature)
                ranArrayChooser = randomNumGen.nextInt(characterSet.size());
            }

            //create an index randomly to access the character at that index from a randomly chosen array
            int randomLetterIndex = randomNumGen.nextInt(characterSet.get(ranArrayChooser).length -1);

            //append the randomly chosen character into the password
            password += characterSet.get(ranArrayChooser)[randomLetterIndex];
        }
        return password;
    }

    public void setLength(int size)
    {
        passwordLength = size;
    }
    public int getLength()
    {
        return passwordLength;
    }

    /* Helpful when using the program without having a GUI
    * (when the UI is the terminal)*/
    public void clearScreen()
    {
        System.out.print("\u000C");
    }
}
