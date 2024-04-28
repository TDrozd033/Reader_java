
package ReaderPackage;
import java.io.IOException;

/**
 * Class contains metdhods to read characters, int-s, double-s and hexadecimal values
 * All functions reads characters and calculates the proper values
 * @author MSI
 *
 */


public class Reader
{
   private static char Char = 0;

//--- PUBLICZNE i nie trzeba obiektu klasy aby je wywolac
    /**
     * Reads one character from the input stream System.in
     * @return  returns read character
     * or @return one previous read char
     * only one char could be ungetted
     * in calse of IOException returns character with code 0
     */



    public static char  getChar()
    {
        // jesli odlozylismy wczesniej litere to zwracamy ja
        if( Char != 0 )
        {
            char res = Char;
            Char = 0;
            return res;
        }
        try
        {
            // odczytujemy nowa litere ze strumienia wejsciowego
            int c = System.in.read();
            return (char) c;
        }
        catch (IOException e)
        {
            return 0;
        }
    }


    /**
     * Returns one character to the input for reloading. Only one char could be returned!
     * @param c - zwracany znak do ponownego odczytu
     */

    public static void ungetChar(char c) // nic nie zwraca, do odlozenia znaku zeby pozniej odczytac
    {
        Char = c;

    }

    /**
     * Reads the integer value written in char case
     * @return the value of integer  number
     */


   public static int  readInt()
   {
      boolean n = getSign();
       int number = readNum();
       return n ? -number : number;
   }

    /**
     * Reads the double value written in char case
     * @return the value of double  num
     */

    public static double readDouble()
    {
        // najpierw liczba calkowita ze znakiem, pozniej kropka, pozniej wartosc po kropce
        boolean n = getSign();
        double number = readNum();
        char c;
        if( (c=getChar() ) == '.' )
        {
            double temp = 0.1;
            while (isDecDigit(c = getChar()))
            {
                number += temp * charDecDigit2Int(c);
                temp *= 0.1;
            }
        }
        ungetChar(c);

        return n ? -number : number;
    }


    /**
     * Reads the hexadecimal value written in char case.
     * @throws IOException in case of wrong hexadecimal number prefix 0X, 0x
     *         IOException( "Wrong hexadecimal prefix! Shoud be 0x or 0X" );
     * @return value of proper hexadecimal value
     */


    public static int  readHex()  throws IOException
    {
        skipSpaces();
        char c = getChar();
        // sprawdzamy czy odpowiedni format
        if( c != '0')
        {
            ungetChar( c );
            throw new IOException("Wrong hexadecimal prefix! Should be 0x or 0X");
        }
        c = getChar();
        if( c != 'x' && c != 'X')
        {
            ungetChar( c );
            throw new IOException("Wrong hexadecimal prefix! Should be 0x or 0X");
        }

        int hex = 0;

        while(true)
        {
            c = getChar();
            if(isHexDigit( c ) )
            {
                hex = hex * 16 + charHexDigit2Int(c);
            }
            else
            {
                ungetChar( c );
                break;
            }
        }
        return hex;
    }



    //================ P R I V A T E INTERFACE ==========================================


    private static int readNum() // czyta liczbe calkowita (pomija biale znaki przed zznakiem)
    {
        skipSpaces(); // pomijamy biale znaki

        char c = getChar();
        int number = 0;

        while (true)
        {
            if (isDecDigit(c))
            {
                number = number * 10 + charDecDigit2Int(c);
            }
            else
            {
                ungetChar(c);
                break;
            }
            c = getChar();
        }
        return number;

    }



    private static boolean getSign()  // return true or false; true - in case '-' (pomija biale znaki przed)
    {
        skipSpaces(); // pomiajmy biale znaki

        char c = getChar();
        if ( c == '-' ) //minus przed
        {
            return true;
        }
        else
        {
            ungetChar(c);
        }
        return false;
    }



    private static void skipSpaces() // nic nie zwraca   /// pomija biale znaki (wszystkie: spacje tabulatory, nowe linie - oba zanki)
    {
        char c = getChar();
        while( c == 32 || c == 9 || c == 10 || c == 13 )
        {
            c = getChar();
        };
        ungetChar( c );
    }

    private static boolean isDecDigit(char c)// logiczna - jedna linijka kodu
    {
        return( c >= '0' && c <= '9' );
    }

    private static boolean isHexDigit(char c)// logiczna, jedna linijka kodu
    {
        return isDecDigit( c ) || isHexLetter( c );
    }

    private static char upperCase(char c) // zamienia male na duze ale tylko dla a..f,  jedna linijka kodu
    {
        return( c >= 'a' && c <= 'f') ? (char)( c - 32 ) : c;
    }


    private static boolean isHexLetter(char c)// logiczna ,jedna linijka kodu
    {
        return ( c >= 'a' && c <= 'f' ) || ( c >= 'A' && c <= 'F');
    }

    private static int charDecDigit2Int(char c)// zwraca wartosc cyfry dzisietnej, jedna linijka kodu
    {
        return c - '0';
    }

    private static int charHexDigit2Int(char c)// zwraca wartosc cyfry szesnastkowej, jedna linijka kodu
    {

        return isDecDigit( c = upperCase(c) ) ? charDecDigit2Int(c) : c-55;
    }

}























