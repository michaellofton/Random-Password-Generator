
/**
 * Write a description here.
 * 
 * @author Michael Lofton 
 * @version Version 
 * This class acts as the model of the project.
 * This class generates a password randomly with help from
 * a pseudo random number generaetor (PRNG) from the
 * SecureRandom class found in java.security
 */
import com.sun.xml.internal.fastinfoset.util.StringArray;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class passwordGenerator
{
    private SecureRandom randomNumGen;
    private int passwordLength;
    //private String[] password;
    private String[] lowAlpha = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private String[] highAlpha = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private String[] specialChars = new String[] {"~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "+", "=", "[", "{", "]", "}", "|", "\\", "'", "\"", ";", ":", "?", "/", ".", ">", "<", ","};
    private String[] numbers = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    //private String[][] characterSet;
    private ArrayList<String[]> characterSet;
    //private HashMap<Integer, String[]> characterSet;

    public passwordGenerator()
    {

        characterSet = new ArrayList<>();
        //characterSet = new HashMap<>();
    }

    public void setCharacterSet(boolean lower, boolean upper,
                                boolean special, boolean numbs)
                                throws NullPointerException
    {
        if (lower && !characterSet.contains(lowAlpha))//!characterSet.containsValue(lowAlpha))
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

    
    public void setLength(int size)
    {
        passwordLength = size;
    }
    
    public int getLength()
    {
        return passwordLength;
    }
    
    public String makePassword(int size)
    {
        //password = new String[size];
        passwordLength = size;
        String password = "";

        /* POSSIBILITIES:
            ranArrayChooser =   0, 1, 2, 3
                                each number (0-3) could be any one of the following string lists:
                                lowercase, uppercase, special characters, numbers

            ranNumberIndex =    0-25 if lower/uppercase
                                0-31 if special characters
                                0-9 if numbers
         */

        for (int k = 0; k < passwordLength; ++k)
        {
            /* randomly chose an integer (starting from 0, going up to the size of the
            character set (which at max is 4) -1 (since we're choosing an index, 1-4 is
            0-3). Allows it to randomly choose an array depending on how many were added
            to the characterset, making it flexible. */

            randomNumGen = new SecureRandom();

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
            //System.out.println("CharacterSet.size() - 1: " + (characterSet.size()-1));
            //ranArrayChooser = randomNumGen.nextInt(0); //must be 1 or larger
            //int ranArrayChooser = randomNumGen.nextInt(characterSet.size()-1);

            //randomly create an index to access the character at that randomly create index
            // from a randomly chosen array
            int randomLetterIndex = randomNumGen.nextInt(characterSet.get(ranArrayChooser).length -1);

            //append the randomly chosen character into the password
            password += characterSet.get(ranArrayChooser)[randomLetterIndex];
        }
        return password;
    }

    /* Helpful when using the
    * program without having a GUI
    * (when the UI is the terminal)*/
    public void clearScreen()
    {
        System.out.print("\u000C");
    }
}
