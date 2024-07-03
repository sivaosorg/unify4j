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
        public static final String NULL = Character.toString(0);
        /**
         * Start of Heading (SOH)
         */
        public static final String START_OF_HEADING = Character.toString(1);
        /**
         * Start of Text (STX)
         */
        public static final String START_OF_TEXT = Character.toString(2);
        /**
         * End of Text (ETX)
         */
        public static final String END_OF_TEXT = Character.toString(3);
        /**
         * End of Transmission (EOT)
         */
        public static final String END_OF_TRANSMISSION = Character.toString(4);
        /**
         * Enquiry (ENQ)
         */
        public static final String ENQUIRY = Character.toString(5);
        /**
         * Acknowledge (ACK)
         */
        public static final String ACKNOWLEDGE = Character.toString(6);
        /**
         * Bell (BEL)
         */
        public static final String BEL = Character.toString(7);
        /**
         * Backspace (BS)
         */
        public static final String BACKSPACE = Character.toString(8);
        /**
         * Horizontal Tab (HT)
         */
        public static final String CHARACTER_TABULATION = Character.toString(9);
        /**
         * Line Feed (LF)
         */
        public static final String LINE_FEED_LF = Character.toString(10);
        /**
         * Vertical Tab (VT)
         */
        public static final String LINE_TABULATION = Character.toString(11);
        /**
         * Form Feed (FF)
         */
        public static final String FORM_FEED_FF = Character.toString(12);
        /**
         * Carriage Return (CR)
         */
        public static final String CARRIAGE_RETURN_CR = Character.toString(13);
        /**
         * Shift Out (SO)
         */
        public static final String SHIFT_OUT = Character.toString(14);
        /**
         * Shift In (SI)
         */
        public static final String SHIFT_IN = Character.toString(15);
        /**
         * Data Link Escape (DLE)
         */
        public static final String DATA_LINK_ESCAPE = Character.toString(16);
        /**
         * Device Control 1 (DC1)
         */
        public static final String DEVICE_CONTROL_ONE = Character.toString(17);
        /**
         * Device Control 2 (DC2)
         */
        public static final String DEVICE_CONTROL_TWO = Character.toString(18);
        /**
         * Device Control 3 (DC3)
         */
        public static final String DEVICE_CONTROL_THREE = Character.toString(19);
        /**
         * Device Control 4 (DC4)
         */
        public static final String DEVICE_CONTROL_FOUR = Character.toString(20);
        /**
         * Negative Acknowledge (NAK)
         */
        public static final String NEGATIVE_ACKNOWLEDGE = Character.toString(21);
        /**
         * Synchronous Idle (SYN)
         */
        public static final String SYNCHRONOUS_IDLE = Character.toString(22);
        /**
         * End of Transmission Block (ETB)
         */
        public static final String END_OF_TRANSMISSION_BLOCK = Character.toString(23);
        /**
         * Cancel (CAN)
         */
        public static final String CANCEL = Character.toString(24);
        /**
         * End of Medium (EM)
         */
        public static final String END_OF_MEDIUM = Character.toString(25);
        /**
         * Substitute (SUB)
         */
        public static final String SUBSTITUTE = Character.toString(26);
        /**
         * Escape (ESC)
         */
        public static final String ESCAPE = Character.toString(27);
        /**
         * File Separator (FS)
         */
        public static final String INFORMATION_SEPARATOR_FOUR = Character.toString(28);
        /**
         * Group Separator (GS)
         */
        public static final String INFORMATION_SEPARATOR_THREE = Character.toString(29);
        /**
         * Record Separator (RS)
         */
        public static final String INFORMATION_SEPARATOR_TWO = Character.toString(30);
        /**
         * Unit Separator (US)
         */
        public static final String INFORMATION_SEPARATOR_ONE = Character.toString(31);
        /**
         * Delete (DEL)
         */
        public static final String DELETE = Character.toString(127);
    }

    /**
     * The Punctuation class provides constants for ASCII punctuation characters.
     */
    public static final class Punctuation {
        /**
         * Space character (SP)
         */
        public static final String SPACE = Character.toString(32);
        /**
         * Exclamation mark (!)
         */
        public static final String EXCLAMATION_MARK = Character.toString(33);
        /**
         * Quotation mark (")
         */
        public static final String QUOTATION_MARK = Character.toString(34);
        /**
         * Number sign (#)
         */
        public static final String NUMBER_SIGN = Character.toString(35);
        /**
         * Percent sign (%)
         */
        public static final String PERCENT_SIGN = Character.toString(37);
        /**
         * Ampersand (&)
         */
        public static final String AMPERSAND = Character.toString(38);
        /**
         * Apostrophe (')
         */
        public static final String APOSTROPHE = Character.toString(39);
        /**
         * Left parenthesis (()
         */
        public static final String LEFT_PARENTHESIS = Character.toString(40);
        /**
         * Right parenthesis ())
         */
        public static final String RIGHT_PARENTHESIS = Character.toString(41);
        /**
         * Asterisk (*)
         */
        public static final String ASTERISK = Character.toString(42);
        /**
         * Comma (,)
         */
        public static final String COMMA = Character.toString(44);
        /**
         * Hyphen-minus (-)
         */
        public static final String HYPHEN_MINUS = Character.toString(45);
        /**
         * Full stop (.)
         */
        public static final String FULL_STOP = Character.toString(46);
        /**
         * Solidus (/)
         */
        public static final String SOLIDUS = Character.toString(47);
        /**
         * Colon (:)
         */
        public static final String COLON = Character.toString(58);
        /**
         * Semicolon (;)
         */
        public static final String SEMICOLON = Character.toString(59);
        /**
         * Question mark (?)
         */
        public static final String QUESTION_MARK = Character.toString(63);
        /**
         * Commercial at (@)
         */
        public static final String COMMERCIAL_AT = Character.toString(64);
        /**
         * Left square bracket ([)
         */
        public static final String LEFT_SQUARE_BRACKET = Character.toString(91);
        /**
         * Reverse solidus (\)
         */
        public static final String REVERSE_SOLIDUS = Character.toString(92);
        /**
         * Right square bracket (])
         */
        public static final String RIGHT_SQUARE_BRACKET = Character.toString(93);
        /**
         * Low line (_)
         */
        public static final String LOW_LINE = Character.toString(95);
        /**
         * Left curly bracket ({)
         */
        public static final String LEFT_CURLY_BRACKET = Character.toString(123);
        /**
         * Right curly bracket (})
         */
        public static final String RIGHT_CURLY_BRACKET = Character.toString(125);
    }

    /**
     * The Symbol class provides constants for ASCII symbol characters.
     */
    public static final class Symbol {
        /**
         * Dollar sign ($)
         */
        public static final String DOLLAR_SIGN = Character.toString(36);
        /**
         * Plus sign (+)
         */
        public static final String PLUS_SIGN = Character.toString(43);
        /**
         * Less-than sign (<)
         */
        public static final String LESS_THAN_SIGN = Character.toString(60);
        /**
         * Equals sign (=)
         */
        public static final String EQUALS_SIGN = Character.toString(61);
        /**
         * Greater-than sign (>)
         */
        public static final String GREATER_THAN_SIGN = Character.toString(62);
        /**
         * Circumflex accent (^)
         */
        public static final String CIRCUMFLEX_ACCENT = Character.toString(94);
        /**
         * Grave accent (`)
         */
        public static final String GRAVE_ACCENT = Character.toString(96);
        /**
         * Vertical line (|)
         */
        public static final String VERTICAL_LINE = Character.toString(124);
        /**
         * Tilde (~)
         */
        public static final String TILDE = Character.toString(126);
    }

    /**
     * The Digit class provides constants for ASCII digit characters.
     */
    public static final class Digit {
        /**
         * Digit zero (0)
         */
        public static final String DIGIT_ZERO = Character.toString(48);
        /**
         * Digit one (1)
         */
        public static final String DIGIT_ONE = Character.toString(49);
        /**
         * Digit two (2)
         */
        public static final String DIGIT_TWO = Character.toString(50);
        /**
         * Digit three (3)
         */
        public static final String DIGIT_THREE = Character.toString(51);
        /**
         * Digit four (4)
         */
        public static final String DIGIT_FOUR = Character.toString(52);
        /**
         * Digit five (5)
         */
        public static final String DIGIT_FIVE = Character.toString(53);
        /**
         * Digit six (6)
         */
        public static final String DIGIT_SIX = Character.toString(54);
        /**
         * Digit seven (7)
         */
        public static final String DIGIT_SEVEN = Character.toString(55);
        /**
         * Digit eight (8)
         */
        public static final String DIGIT_EIGHT = Character.toString(56);
        /**
         * Digit nine (9)
         */
        public static final String DIGIT_NINE = Character.toString(57);
    }

    /**
     * The Uppercase class provides constants for ASCII uppercase letters.
     */
    public static final class Uppercase {
        /**
         * Uppercase letter A
         */
        public static final String LATIN_CAPITAL_LETTER_A = Character.toString(65);
        /**
         * Uppercase letter B
         */
        public static final String LATIN_CAPITAL_LETTER_B = Character.toString(66);
        /**
         * Uppercase letter C
         */
        public static final String LATIN_CAPITAL_LETTER_C = Character.toString(67);
        /**
         * Uppercase letter D
         */
        public static final String LATIN_CAPITAL_LETTER_D = Character.toString(68);
        /**
         * Uppercase letter E
         */
        public static final String LATIN_CAPITAL_LETTER_E = Character.toString(69);
        /**
         * Uppercase letter F
         */
        public static final String LATIN_CAPITAL_LETTER_F = Character.toString(70);
        /**
         * Uppercase letter G
         */
        public static final String LATIN_CAPITAL_LETTER_G = Character.toString(71);
        /**
         * Uppercase letter H
         */
        public static final String LATIN_CAPITAL_LETTER_H = Character.toString(72);
        /**
         * Uppercase letter I
         */
        public static final String LATIN_CAPITAL_LETTER_I = Character.toString(73);
        /**
         * Uppercase letter J
         */
        public static final String LATIN_CAPITAL_LETTER_J = Character.toString(74);
        /**
         * Uppercase letter K
         */
        public static final String LATIN_CAPITAL_LETTER_K = Character.toString(75);
        /**
         * Uppercase letter L
         */
        public static final String LATIN_CAPITAL_LETTER_L = Character.toString(76);
        /**
         * Uppercase letter M
         */
        public static final String LATIN_CAPITAL_LETTER_M = Character.toString(77);
        /**
         * Uppercase letter N
         */
        public static final String LATIN_CAPITAL_LETTER_N = Character.toString(78);
        /**
         * Uppercase letter O
         */
        public static final String LATIN_CAPITAL_LETTER_O = Character.toString(79);
        /**
         * Uppercase letter P
         */
        public static final String LATIN_CAPITAL_LETTER_P = Character.toString(80);
        /**
         * Uppercase letter Q
         */
        public static final String LATIN_CAPITAL_LETTER_Q = Character.toString(81);
        /**
         * Uppercase letter R
         */
        public static final String LATIN_CAPITAL_LETTER_R = Character.toString(82);
        /**
         * Uppercase letter S
         */
        public static final String LATIN_CAPITAL_LETTER_S = Character.toString(83);
        /**
         * Uppercase letter T
         */
        public static final String LATIN_CAPITAL_LETTER_T = Character.toString(84);
        /**
         * Uppercase letter U
         */
        public static final String LATIN_CAPITAL_LETTER_U = Character.toString(85);
        /**
         * Uppercase letter V
         */
        public static final String LATIN_CAPITAL_LETTER_V = Character.toString(86);
        /**
         * Uppercase letter W
         */
        public static final String LATIN_CAPITAL_LETTER_W = Character.toString(87);
        /**
         * Uppercase letter X
         */
        public static final String LATIN_CAPITAL_LETTER_X = Character.toString(88);
        /**
         * Uppercase letter Y
         */
        public static final String LATIN_CAPITAL_LETTER_Y = Character.toString(89);
        /**
         * Uppercase letter Z
         */
        public static final String LATIN_CAPITAL_LETTER_Z = Character.toString(90);
    }

    /**
     * The Lowercase class provides constants for ASCII lowercase letters.
     */
    public static final class Lowercase {
        /**
         * Lowercase letter a
         */
        public static final String LATIN_SMALL_LETTER_A = Character.toString(97);
        /**
         * Lowercase letter b
         */
        public static final String LATIN_SMALL_LETTER_B = Character.toString(98);
        /**
         * Lowercase letter c
         */
        public static final String LATIN_SMALL_LETTER_C = Character.toString(99);
        /**
         * Lowercase letter d
         */
        public static final String LATIN_SMALL_LETTER_D = Character.toString(100);
        /**
         * Lowercase letter e
         */
        public static final String LATIN_SMALL_LETTER_E = Character.toString(101);
        /**
         * Lowercase letter f
         */
        public static final String LATIN_SMALL_LETTER_F = Character.toString(102);
        /**
         * Lowercase letter g
         */
        public static final String LATIN_SMALL_LETTER_G = Character.toString(103);
        /**
         * Lowercase letter h
         */
        public static final String LATIN_SMALL_LETTER_H = Character.toString(104);
        /**
         * Lowercase letter i
         */
        public static final String LATIN_SMALL_LETTER_I = Character.toString(105);
        /**
         * Lowercase letter j
         */
        public static final String LATIN_SMALL_LETTER_J = Character.toString(106);
        /**
         * Lowercase letter k
         */
        public static final String LATIN_SMALL_LETTER_K = Character.toString(107);
        /**
         * Lowercase letter l
         */
        public static final String LATIN_SMALL_LETTER_L = Character.toString(108);
        /**
         * Lowercase letter m
         */
        public static final String LATIN_SMALL_LETTER_M = Character.toString(109);
        /**
         * Lowercase letter n
         */
        public static final String LATIN_SMALL_LETTER_N = Character.toString(110);
        /**
         * Lowercase letter o
         */
        public static final String LATIN_SMALL_LETTER_O = Character.toString(111);
        /**
         * Lowercase letter p
         */
        public static final String LATIN_SMALL_LETTER_P = Character.toString(112);
        /**
         * Lowercase letter q
         */
        public static final String LATIN_SMALL_LETTER_Q = Character.toString(113);
        /**
         * Lowercase letter r
         */
        public static final String LATIN_SMALL_LETTER_R = Character.toString(114);
        /**
         * Lowercase letter s
         */
        public static final String LATIN_SMALL_LETTER_S = Character.toString(115);
        /**
         * Lowercase letter t
         */
        public static final String LATIN_SMALL_LETTER_T = Character.toString(116);
        /**
         * Lowercase letter u
         */
        public static final String LATIN_SMALL_LETTER_U = Character.toString(117);
        /**
         * Lowercase letter v
         */
        public static final String LATIN_SMALL_LETTER_V = Character.toString(118);
        /**
         * Lowercase letter w
         */
        public static final String LATIN_SMALL_LETTER_W = Character.toString(119);
        /**
         * Lowercase letter x
         */
        public static final String LATIN_SMALL_LETTER_X = Character.toString(120);
        /**
         * Lowercase letter y
         */
        public static final String LATIN_SMALL_LETTER_Y = Character.toString(121);
        /**
         * Lowercase letter z
         */
        public static final String LATIN_SMALL_LETTER_Z = Character.toString(122);
    }
}
