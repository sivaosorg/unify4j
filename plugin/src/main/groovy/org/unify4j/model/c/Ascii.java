package org.unify4j.model.c;

/**
 * The Ascii class provides constants for various ASCII characters, grouped into different categories
 * such as control characters, punctuation, symbols, digits, uppercase letters, and lowercase letters.
 */
public class Ascii {

    /**
     * The Control class provides constants for ASCII control characters.
     */
    public static final class Control {
        /**
         * Null character (NUL)
         */
        public static final String NULL = Character.toString((char) 0);
        /**
         * Start of Heading (SOH)
         */
        public static final String START_OF_HEADING = Character.toString((char) 1);
        /**
         * Start of Text (STX)
         */
        public static final String START_OF_TEXT = Character.toString((char) 2);
        /**
         * End of Text (ETX)
         */
        public static final String END_OF_TEXT = Character.toString((char) 3);
        /**
         * End of Transmission (EOT)
         */
        public static final String END_OF_TRANSMISSION = Character.toString((char) 4);
        /**
         * Enquiry (ENQ)
         */
        public static final String ENQUIRY = Character.toString((char) 5);
        /**
         * Acknowledge (ACK)
         */
        public static final String ACKNOWLEDGE = Character.toString((char) 6);
        /**
         * Bell (BEL)
         */
        public static final String BEL = Character.toString((char) 7);
        /**
         * Backspace (BS)
         */
        public static final String BACKSPACE = Character.toString((char) 8);
        /**
         * Horizontal Tab (HT)
         */
        public static final String CHARACTER_TABULATION = Character.toString((char) 9);
        /**
         * Line Feed (LF)
         */
        public static final String LINE_FEED_LF = Character.toString((char) 10);
        /**
         * Vertical Tab (VT)
         */
        public static final String LINE_TABULATION = Character.toString((char) 11);
        /**
         * Form Feed (FF)
         */
        public static final String FORM_FEED_FF = Character.toString((char) 12);
        /**
         * Carriage Return (CR)
         */
        public static final String CARRIAGE_RETURN_CR = Character.toString((char) 13);
        /**
         * Shift Out (SO)
         */
        public static final String SHIFT_OUT = Character.toString((char) 14);
        /**
         * Shift In (SI)
         */
        public static final String SHIFT_IN = Character.toString((char) 15);
        /**
         * Data Link Escape (DLE)
         */
        public static final String DATA_LINK_ESCAPE = Character.toString((char) 16);
        /**
         * Device Control 1 (DC1)
         */
        public static final String DEVICE_CONTROL_ONE = Character.toString((char) 17);
        /**
         * Device Control 2 (DC2)
         */
        public static final String DEVICE_CONTROL_TWO = Character.toString((char) 18);
        /**
         * Device Control 3 (DC3)
         */
        public static final String DEVICE_CONTROL_THREE = Character.toString((char) 19);
        /**
         * Device Control 4 (DC4)
         */
        public static final String DEVICE_CONTROL_FOUR = Character.toString((char) 20);
        /**
         * Negative Acknowledge (NAK)
         */
        public static final String NEGATIVE_ACKNOWLEDGE = Character.toString((char) 21);
        /**
         * Synchronous Idle (SYN)
         */
        public static final String SYNCHRONOUS_IDLE = Character.toString((char) 22);
        /**
         * End of Transmission Block (ETB)
         */
        public static final String END_OF_TRANSMISSION_BLOCK = Character.toString((char) 23);
        /**
         * Cancel (CAN)
         */
        public static final String CANCEL = Character.toString((char) 24);
        /**
         * End of Medium (EM)
         */
        public static final String END_OF_MEDIUM = Character.toString((char) 25);
        /**
         * Substitute (SUB)
         */
        public static final String SUBSTITUTE = Character.toString((char) 26);
        /**
         * Escape (ESC)
         */
        public static final String ESCAPE = Character.toString((char) 27);
        /**
         * File Separator (FS)
         */
        public static final String INFORMATION_SEPARATOR_FOUR = Character.toString((char) 28);
        /**
         * Group Separator (GS)
         */
        public static final String INFORMATION_SEPARATOR_THREE = Character.toString((char) 29);
        /**
         * Record Separator (RS)
         */
        public static final String INFORMATION_SEPARATOR_TWO = Character.toString((char) 30);
        /**
         * Unit Separator (US)
         */
        public static final String INFORMATION_SEPARATOR_ONE = Character.toString((char) 31);
        /**
         * Delete (DEL)
         */
        public static final String DELETE = Character.toString((char) 127);
    }

    /**
     * The Punctuation class provides constants for ASCII punctuation characters.
     */
    public static final class Punctuation {
        /**
         * Space character (SP)
         */
        public static final String SPACE = Character.toString((char) 32);
        /**
         * Exclamation mark (!)
         */
        public static final String EXCLAMATION_MARK = Character.toString((char) 33);
        /**
         * Quotation mark (")
         */
        public static final String QUOTATION_MARK = Character.toString((char) 34);
        /**
         * Number sign (#)
         */
        public static final String NUMBER_SIGN = Character.toString((char) 35);
        /**
         * Percent sign (%)
         */
        public static final String PERCENT_SIGN = Character.toString((char) 37);
        /**
         * Ampersand (&)
         */
        public static final String AMPERSAND = Character.toString((char) 38);
        /**
         * Apostrophe (')
         */
        public static final String APOSTROPHE = Character.toString((char) 39);
        /**
         * Left parenthesis (()
         */
        public static final String LEFT_PARENTHESIS = Character.toString((char) 40);
        /**
         * Right parenthesis ())
         */
        public static final String RIGHT_PARENTHESIS = Character.toString((char) 41);
        /**
         * Asterisk (*)
         */
        public static final String ASTERISK = Character.toString((char) 42);
        /**
         * Comma (,)
         */
        public static final String COMMA = Character.toString((char) 44);
        /**
         * Hyphen-minus (-)
         */
        public static final String HYPHEN_MINUS = Character.toString((char) 45);
        /**
         * Full stop (.)
         */
        public static final String FULL_STOP = Character.toString((char) 46);
        /**
         * Solidus (/)
         */
        public static final String SOLIDUS = Character.toString((char) 47);
        /**
         * Colon (:)
         */
        public static final String COLON = Character.toString((char) 58);
        /**
         * Semicolon (;)
         */
        public static final String SEMICOLON = Character.toString((char) 59);
        /**
         * Question mark (?)
         */
        public static final String QUESTION_MARK = Character.toString((char) 63);
        /**
         * Commercial at (@)
         */
        public static final String COMMERCIAL_AT = Character.toString((char) 64);
        /**
         * Left square bracket ([)
         */
        public static final String LEFT_SQUARE_BRACKET = Character.toString((char) 91);
        /**
         * Reverse solidus (\)
         */
        public static final String REVERSE_SOLIDUS = Character.toString((char) 92);
        /**
         * Right square bracket (])
         */
        public static final String RIGHT_SQUARE_BRACKET = Character.toString((char) 93);
        /**
         * Low line (_)
         */
        public static final String LOW_LINE = Character.toString((char) 95);
        /**
         * Left curly bracket ({)
         */
        public static final String LEFT_CURLY_BRACKET = Character.toString((char) 123);
        /**
         * Right curly bracket (})
         */
        public static final String RIGHT_CURLY_BRACKET = Character.toString((char) 125);
    }

    /**
     * The Symbol class provides constants for ASCII symbol characters.
     */
    public static final class Symbol {
        /**
         * Dollar sign ($)
         */
        public static final String DOLLAR_SIGN = Character.toString((char) 36);
        /**
         * Plus sign (+)
         */
        public static final String PLUS_SIGN = Character.toString((char) 43);
        /**
         * Less-than sign (<)
         */
        public static final String LESS_THAN_SIGN = Character.toString((char) 60);
        /**
         * Equals sign (=)
         */
        public static final String EQUALS_SIGN = Character.toString((char) 61);
        /**
         * Greater-than sign (>)
         */
        public static final String GREATER_THAN_SIGN = Character.toString((char) 62);
        /**
         * Circumflex accent (^)
         */
        public static final String CIRCUMFLEX_ACCENT = Character.toString((char) 94);
        /**
         * Grave accent (`)
         */
        public static final String GRAVE_ACCENT = Character.toString((char) 96);
        /**
         * Vertical line (|)
         */
        public static final String VERTICAL_LINE = Character.toString((char) 124);
        /**
         * Tilde (~)
         */
        public static final String TILDE = Character.toString((char) 126);
    }

    /**
     * The Digit class provides constants for ASCII digit characters.
     */
    public static final class Digit {
        /**
         * Digit zero (0)
         */
        public static final String DIGIT_ZERO = Character.toString((char) 48);
        /**
         * Digit one (1)
         */
        public static final String DIGIT_ONE = Character.toString((char) 49);
        /**
         * Digit two (2)
         */
        public static final String DIGIT_TWO = Character.toString((char) 50);
        /**
         * Digit three (3)
         */
        public static final String DIGIT_THREE = Character.toString((char) 51);
        /**
         * Digit four (4)
         */
        public static final String DIGIT_FOUR = Character.toString((char) 52);
        /**
         * Digit five (5)
         */
        public static final String DIGIT_FIVE = Character.toString((char) 53);
        /**
         * Digit six (6)
         */
        public static final String DIGIT_SIX = Character.toString((char) 54);
        /**
         * Digit seven (7)
         */
        public static final String DIGIT_SEVEN = Character.toString((char) 55);
        /**
         * Digit eight (8)
         */
        public static final String DIGIT_EIGHT = Character.toString((char) 56);
        /**
         * Digit nine (9)
         */
        public static final String DIGIT_NINE = Character.toString((char) 57);
    }

    /**
     * The Uppercase class provides constants for ASCII uppercase letters.
     */
    public static final class Uppercase {
        /**
         * Uppercase letter A
         */
        public static final String LATIN_CAPITAL_LETTER_A = Character.toString((char) 65);
        /**
         * Uppercase letter B
         */
        public static final String LATIN_CAPITAL_LETTER_B = Character.toString((char) 66);
        /**
         * Uppercase letter C
         */
        public static final String LATIN_CAPITAL_LETTER_C = Character.toString((char) 67);
        /**
         * Uppercase letter D
         */
        public static final String LATIN_CAPITAL_LETTER_D = Character.toString((char) 68);
        /**
         * Uppercase letter E
         */
        public static final String LATIN_CAPITAL_LETTER_E = Character.toString((char) 69);
        /**
         * Uppercase letter F
         */
        public static final String LATIN_CAPITAL_LETTER_F = Character.toString((char) 70);
        /**
         * Uppercase letter G
         */
        public static final String LATIN_CAPITAL_LETTER_G = Character.toString((char) 71);
        /**
         * Uppercase letter H
         */
        public static final String LATIN_CAPITAL_LETTER_H = Character.toString((char) 72);
        /**
         * Uppercase letter I
         */
        public static final String LATIN_CAPITAL_LETTER_I = Character.toString((char) 73);
        /**
         * Uppercase letter J
         */
        public static final String LATIN_CAPITAL_LETTER_J = Character.toString((char) 74);
        /**
         * Uppercase letter K
         */
        public static final String LATIN_CAPITAL_LETTER_K = Character.toString((char) 75);
        /**
         * Uppercase letter L
         */
        public static final String LATIN_CAPITAL_LETTER_L = Character.toString((char) 76);
        /**
         * Uppercase letter M
         */
        public static final String LATIN_CAPITAL_LETTER_M = Character.toString((char) 77);
        /**
         * Uppercase letter N
         */
        public static final String LATIN_CAPITAL_LETTER_N = Character.toString((char) 78);
        /**
         * Uppercase letter O
         */
        public static final String LATIN_CAPITAL_LETTER_O = Character.toString((char) 79);
        /**
         * Uppercase letter P
         */
        public static final String LATIN_CAPITAL_LETTER_P = Character.toString((char) 80);
        /**
         * Uppercase letter Q
         */
        public static final String LATIN_CAPITAL_LETTER_Q = Character.toString((char) 81);
        /**
         * Uppercase letter R
         */
        public static final String LATIN_CAPITAL_LETTER_R = Character.toString((char) 82);
        /**
         * Uppercase letter S
         */
        public static final String LATIN_CAPITAL_LETTER_S = Character.toString((char) 83);
        /**
         * Uppercase letter T
         */
        public static final String LATIN_CAPITAL_LETTER_T = Character.toString((char) 84);
        /**
         * Uppercase letter U
         */
        public static final String LATIN_CAPITAL_LETTER_U = Character.toString((char) 85);
        /**
         * Uppercase letter V
         */
        public static final String LATIN_CAPITAL_LETTER_V = Character.toString((char) 86);
        /**
         * Uppercase letter W
         */
        public static final String LATIN_CAPITAL_LETTER_W = Character.toString((char) 87);
        /**
         * Uppercase letter X
         */
        public static final String LATIN_CAPITAL_LETTER_X = Character.toString((char) 88);
        /**
         * Uppercase letter Y
         */
        public static final String LATIN_CAPITAL_LETTER_Y = Character.toString((char) 89);
        /**
         * Uppercase letter Z
         */
        public static final String LATIN_CAPITAL_LETTER_Z = Character.toString((char) 90);
    }

    /**
     * The Lowercase class provides constants for ASCII lowercase letters.
     */
    public static final class Lowercase {
        /**
         * Lowercase letter a
         */
        public static final String LATIN_SMALL_LETTER_A = Character.toString((char) 97);
        /**
         * Lowercase letter b
         */
        public static final String LATIN_SMALL_LETTER_B = Character.toString((char) 98);
        /**
         * Lowercase letter c
         */
        public static final String LATIN_SMALL_LETTER_C = Character.toString((char) 99);
        /**
         * Lowercase letter d
         */
        public static final String LATIN_SMALL_LETTER_D = Character.toString((char) 100);
        /**
         * Lowercase letter e
         */
        public static final String LATIN_SMALL_LETTER_E = Character.toString((char) 101);
        /**
         * Lowercase letter f
         */
        public static final String LATIN_SMALL_LETTER_F = Character.toString((char) 102);
        /**
         * Lowercase letter g
         */
        public static final String LATIN_SMALL_LETTER_G = Character.toString((char) 103);
        /**
         * Lowercase letter h
         */
        public static final String LATIN_SMALL_LETTER_H = Character.toString((char) 104);
        /**
         * Lowercase letter i
         */
        public static final String LATIN_SMALL_LETTER_I = Character.toString((char) 105);
        /**
         * Lowercase letter j
         */
        public static final String LATIN_SMALL_LETTER_J = Character.toString((char) 106);
        /**
         * Lowercase letter k
         */
        public static final String LATIN_SMALL_LETTER_K = Character.toString((char) 107);
        /**
         * Lowercase letter l
         */
        public static final String LATIN_SMALL_LETTER_L = Character.toString((char) 108);
        /**
         * Lowercase letter m
         */
        public static final String LATIN_SMALL_LETTER_M = Character.toString((char) 109);
        /**
         * Lowercase letter n
         */
        public static final String LATIN_SMALL_LETTER_N = Character.toString((char) 110);
        /**
         * Lowercase letter o
         */
        public static final String LATIN_SMALL_LETTER_O = Character.toString((char) 111);
        /**
         * Lowercase letter p
         */
        public static final String LATIN_SMALL_LETTER_P = Character.toString((char) 112);
        /**
         * Lowercase letter q
         */
        public static final String LATIN_SMALL_LETTER_Q = Character.toString((char) 113);
        /**
         * Lowercase letter r
         */
        public static final String LATIN_SMALL_LETTER_R = Character.toString((char) 114);
        /**
         * Lowercase letter s
         */
        public static final String LATIN_SMALL_LETTER_S = Character.toString((char) 115);
        /**
         * Lowercase letter t
         */
        public static final String LATIN_SMALL_LETTER_T = Character.toString((char) 116);
        /**
         * Lowercase letter u
         */
        public static final String LATIN_SMALL_LETTER_U = Character.toString((char) 117);
        /**
         * Lowercase letter v
         */
        public static final String LATIN_SMALL_LETTER_V = Character.toString((char) 118);
        /**
         * Lowercase letter w
         */
        public static final String LATIN_SMALL_LETTER_W = Character.toString((char) 119);
        /**
         * Lowercase letter x
         */
        public static final String LATIN_SMALL_LETTER_X = Character.toString((char) 120);
        /**
         * Lowercase letter y
         */
        public static final String LATIN_SMALL_LETTER_Y = Character.toString((char) 121);
        /**
         * Lowercase letter z
         */
        public static final String LATIN_SMALL_LETTER_Z = Character.toString((char) 122);
    }
}
