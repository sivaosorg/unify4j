package org.unify4j.text;

public class ExpressionText {

    public static final String EMAIL_REGULAR_EXPRESSION = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    public static final String IP_DIGITS_REGULAR_EXPRESSION = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
    public static final String IP_DIGITS_REPEAT_REGULAR_EXPRESSION = IP_DIGITS_REGULAR_EXPRESSION + "\\." + IP_DIGITS_REGULAR_EXPRESSION + "\\." + IP_DIGITS_REGULAR_EXPRESSION + "\\." + IP_DIGITS_REGULAR_EXPRESSION;
    public static final String IPV4_DIGITS_REGULAR_EXPRESSION = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
    public static final String IPV6_STD_DIGITS_REGULAR_EXPRESSION = "^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$";
    public static final String IPV6_HEX_COMPRESSED_DIGITS_REGULAR_EXPRESSION = "^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$";
    /**
     * A password is considered valid if all the following constraints are satisfied:
     * <p>
     * It contains at least 8 characters and at most 20 characters.
     * It contains at least one digit.
     * It contains at least one upper case alphabet.
     * It contains at least one lower case alphabet.
     * It contains at least one special character which includes !@#$%&*()-+=^.
     * It does not contain any white space.
     */
    public static final String PASSWORD_REGULAR_EXPRESSION = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

    /**
     * A username is considered valid if all the following constraints are satisfied:
     * <p>
     * The username consists of 6 to 30 characters inclusive. If the username
     * consists of less than 6 or greater than 30 characters, then it is an invalid username.
     * The username can only contain alphanumeric characters and underscores (_). Alphanumeric characters describe the character set consisting of lowercase characters [a – z], uppercase characters [A – Z], and digits [0 – 9].
     * The first character of the username must be an alphabetic character, i.e., either lowercase character
     * [a – z] or uppercase character [A – Z].
     * <p>
     * Approach: The idea is to use Regular Expressions to validate if the given username is valid or not. The following steps can be followed to compute the answer:
     * <p>
     * Get the string.
     * Form a regular expression to validate the given string. According to the conditions, the regular expression can be formed in the following way:
     * <p>
     * regex = "^[aA-zZ]\\w{5, 29}$"
     * <p>
     */
    public static final String USERNAME_REGULAR_EXPRESS = "^[aA-zZ]\\w{5,29}$";

    /**
     * The valid rules for defining Java identifiers are:
     * <p>
     * It must start with either lower case alphabet[a-z] or upper case alphabet[A-Z] or underscore(_) or a dollar sign($).
     * It should be a single word, the white spaces are not allowed.
     * It should not start with digits
     * <p>
     * Approach: This problem can be solved by using Regular Expression.
     * <p>
     * Get the string.
     * Create a regex to check the valid identifiers.
     * <p>
     * regex = "^([a-zA-Z_$][a-zA-Z\\d_$]*)$";
     * <p>
     * where:
     * ^ represents the starting character of the string.
     * [a-zA-Z_$] represents, the string start with only lower case alphabet or upper case alphabet or underscore(_) or dollar sign($).>/li>
     * [a-zA-Z\\d_$]* represents, the string can be alphanumeric or underscore(_) or dollar sign($) after the first character of the string. It contains one or more time.
     * $ represents the ending of the string.
     * Match the given string with the Regex. In Java, this can be done using Pattern.matcher().
     * Return true if the string matches with the given regex, else return false.
     */
    public static final String IDENTIFIER_REGULAR_EXPRESS = "^([a-zA-Z_$][a-zA-Z\\d_$]*)$";

    /**
     * A valid MAC address must satisfy the following conditions:
     * <p>
     * It must contain 12 hexadecimal digits.
     * One way to represent them is to form six pairs of the characters separated with a hyphen (-) or colon(:). For example, 01-23-45-67-89-AB is a valid MAC address.
     * Another way to represent them is to form three groups of four hexadecimal digits separated by dots(.). For example, 0123.4567.89AB is a valid MAC address.
     */
    public static final String MAC_REGULAR_EXPRESS = "^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})|([0-9a-fA-F]{4}\\.[0-9a-fA-F]{4}\\.[0-9a-fA-F]{4})$";

    /**
     * The valid CVV (Card Verification Value) number must satisfy the following conditions:
     * <p>
     * It should have 3 or 4 digit.
     * It should have digit between 0-9.
     * It should not have any alphabets and special characters.
     */
    public static final String CVV_REGULAR_EXPRESS = "^[0-9]{3,4}$";

    /**
     * The valid time in 12-hour format must satisfy the following conditions:
     * <p>
     * It should start from 1, 2, … 9 or 10, 11, 12.
     * It should be followed by a colon(:).
     * It should be followed by two digits between 00 to 59.
     * It should only allow one white space, although this is optional.
     * It should end with ‘am’, ‘pm’ or ‘AM’, ‘PM’.
     */
    public static final String TIME_12_HOUR_REGULAR_EXPRESS = "(1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
    public static final String TIME_12_HOUR_FULL_REGULAR_EXPRESS = "(1[012]|[1-9]):([0-5][0-9]):([0-5][0-9])(\\s)?(?i)(am|pm)?$";

    /**
     * The valid time in the 24-hour format must satisfy the following conditions.
     * <p>
     * It should start from 0-23 or 00-23.
     * It should be followed by a ‘:'(colon).
     * It should be followed by two digits from 00 to 59.
     * It should not end with ‘am’, ‘pm’ or ‘AM’, ‘PM’.
     */
    public static final String TIME_24_HOUR_REGULAR_EXPRESS = "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String TIME_24_HOUR_FULL_REGULAR_EXPRESS = "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";

    /**
     * Examples of valid dates:
     * <p>
     * 2017-12-31
     * 2020-02-29
     * 2400-02-29
     * <p>
     * Examples of invalid dates:
     * <p>
     * 2017/12/31: incorrect token delimiter
     * 2018-1-1: missing leading zeroes
     * 2018-04-31: wrong days count for April
     * 2100-02-29: this year isn't leap as the value divides by 100, so February is limited to 28 days
     */
    public static final String DATE_REGULAR_EXPRESS = "^((2000|2400|2800|(19|2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$|^(((19|2[0-9])[0-9]{2})-02-(0[1-9]|1[0-9]|2[0-8]))$|^(((19|2[0-9])[0-9]{2})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$|^(((19|2[0-9])[0-9]{2})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$";
    /**
     * The valid image file extension must specify the following conditions:
     * <p>
     * It should start with a string of at least one character.
     * It should not have any white space.
     * It should be followed by a dot(.).
     * It should be end with any one of the following extensions: jpg, jpeg, png, gif, bmp.
     */
    public static final String IMAGES_REGULAR_EXPRESS = "([^\\s]+(\\.(?i)(jpe?g|png|gif|bmp))$)";

    /**
     * The valid domain name must satisfy the following conditions:
     * <p>
     * The domain name should be a-z or A-Z or 0-9 and hyphen (-).
     * The domain name should be between 1 and 63 characters long.
     * The domain name should not start or end with a hyphen(-) (e.g. -abc.org or abc.org-).
     * The last TLD (Top level domain) must be at least two characters, and a maximum of 6 characters.
     * The domain name can be a sub-domain (e.g. contribute.abc.org).
     * <p>
     * Approach: The idea is to use the Regular Expression to solve this problem. The following steps can be followed to compute the answer:
     * <p>
     * Get the String.
     * Create a regular expression to check valid domain name as mentioned below:
     * <p>
     * <p>
     * Where:
     * ^ represents the starting of the string.
     * ( represents the starting of the group.
     * (?!-) represents the string should not start with a hyphen (-).
     * [A-Za-z0-9-]{1, 63} represents the domain name should be a-z or A-Z or 0-9 and hyphen (-) between 1 and 63 characters long.
     * (?<!-) represents the string should not end with a hyphen (-).
     * \\. represents the string followed by a dot.
     * )+ represents the ending of the group, this group must appears atleast 1 time, but allowed multiple times for subdomain.
     * [A-Za-z]{2, 6} represents the TLD must be A-Z or a-z between 2 and 6 characters long.
     * $ represents the ending of the string.
     * Match the given string with the regular expression. In Java, this can be done by using Pattern.matcher().
     * Return true if the string matches with the given regular expression, else return false.
     */
    public static final String DOMAIN_REGULAR_EXPRESS = "^((?!-)[A-Za-z0-9-]{1,63}(?<!-)\\.)+[A-Za-z]{2,6}";

    /**
     * The valid Master Card number must satisfy the following conditions.
     * <p>
     * It should be 16 digits long.
     * It should start with either two digits number may range from 51 to 55 or four digits number may range from 2221 to 2720.
     * In the case of 51 to 55, the next fourteen digits should be any number between 0-9.
     * In the case of 2221 to 2720, the next twelve digits should be any number between 0-9.
     * It should not contains any alphabets and special characters
     * <p>
     * Approach: The idea is to use Regular Expression to solve this problem. The following steps can be followed to compute the answer.
     * <p>
     * Get the String.
     * Create a regular expression to check valid Master Card number as mentioned below:
     * <p>
     * <p>
     * Where:
     * ^ represents the starting of the string.
     * 5[1-5] represents the first two digit number may range from 51 to 55.
     * [0-9]{14} represents the next fourteen digits should be any between 0-9.
     * | represents the or.
     * ( represents the starting of the group.
     * 222[1-9] represents the first four digit number may range from 2221 to 2229.
     * | represents the or.
     * 22[3-9]\\d represents the first four digit number may range from 2230 to 2299.
     * | represents the or.
     * 2[3-6]\\d{2} represents the first four digit number may range from 2300 to 2699.
     * | represents the or.
     * 27[0-1]\\d represents the first four digit number may range from 2700 to 2719.
     * | represents the or.
     * 2720 represents the first four digit may starts with 2720.
     * ) represents the ending of the group.
     * [0-9]{12} represents the next twelve digits should be any between 0-9.
     * $ represents the ending of the string.
     * Match the given string with the Regular Expression. In Java, this can be done by using Pattern.matcher()
     * Return true if the string matches with the given regular expression, else return false.
     */
    public static final String MASTERCARD_REGULAR_EXPRESS = "^5[1-5][0-9]{14}|^(222[1-9]|22[3-9]\\d|2[3-6]\\d{2}|27[0-1]\\d|2720)[0-9]{12}$";

    /**
     * The valid Visa Card number must satisfy the following conditions:
     * <p>
     * It should be 13 or 16 digits long, new cards have 16 digits and old cards have 13 digits.
     * It should be starts with 4.
     * If the cards have 13 digits the next twelve digits should be any number between 0-9.
     * If the cards have 16 digits the next fifteen digits should be any number between 0-9.
     * It should not contains any alphabets and special characters
     * <p>
     * Approach: The idea is to use Regular Expression to solve this problem. The following steps can be followed to compute the answer.
     * <p>
     * Get the String.
     * Create a regular expression to check valid Visa Card number as mentioned below:
     * <p>
     * regex = "^4[0-9]{12}(?:[0-9]{3})?$";
     * <p>
     * Where:
     * <p>
     * <p>
     * ^ represents the starting of the string.
     * 4 represents the string should be starts with 4.
     * [0-9]{12} represents the next twelve digits should be any between 0-9.
     * ( represents the starting of the group.
     * ? represents the 0 or 1 time.
     * [0-9]{3} represents the next three digits should be any between 0-9.
     * ) represents the ending of the group.
     * ? represents the 0 or 1 time.
     * $ represents the ending of the string.
     * Match the given string with the Regular Expression. In Java, this can be done by using Pattern.matcher().
     * Return true if the string matches with the given regular expression, else return false.
     */
    public static final String VISA_CARD_REGULAR_EXPRESS = "^4[0-9]{12}(?:[0-9]{3})?$";

    /**
     * The valid HTML tag must satisfy the following conditions:
     * <p>
     * It should start with an opening tag (<).
     * It should be followed by double quotes string, or single quotes string.
     * It should not allow one double quotes string, one single quotes string or a closing tag (>) without single or double quotes enclosed.
     * It should end with a closing tag (>).
     * <p>
     * Approach: The idea is to use Regular Expression to solve this problem. The following steps can be followed to compute the answer.
     * <p>
     * Get the String.
     * Create a regular expression to check valid HTML tag as mentioned below:
     * <p>
     * regex = "<("[^"]*"|'[^']*'|[^'">])*>";
     * <p>
     * Where:
     * < represents the string should start with an opening tag (<).
     * ( represents the starting of the group.
     * | represents or.
     * ‘[^’]*‘ represents the string should allow single quotes enclosed string.
     * | represents or.
     * ) represents the ending of the group.
     * * represents 0 or more.
     * > represents the string should end with a closing tag (>).
     * Match the given string with the regular expression. In Java, this can be done by using Pattern.matcher().
     * Return true if the string matches with the given regular expression, else return false.
     */
    public static final String HTML_TAG_REGULAR_EXPRESS = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>";

    /**
     * The valid hexadecimal color code must satisfy the following conditions.
     * <p>
     * It should start from ‘#’ symbol.
     * It should be followed by the letters from a-f, A-F and/or digits from 0-9.
     * The length of the hexadecimal color code should be either 6 or 3, excluding ‘#’ symbol.
     */
    public static final String HEXADECIMAL_COLOR_REGULAR_EXPRESS = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

    /**
     * The valid GUID (Globally Unique Identifier) must specify the following conditions:
     * <p>
     * It should be 128 bit number.
     * It should be 36 characters (32 hexadecimal characters and 4 hyphens) long.
     * It should be displayed in five groups separated by hyphens (-).
     * Microsoft GUIDs are sometimes represented with surrounding braces.
     */
    public static final String GUID_REGULAR_EXPRESS = "^[{]?[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}[}]?$";

    /**
     * sign -> + | - | epsilon
     * digit -> 0 | 1 | .... | 9
     * digits -> digit digit*
     * optional_fraction -> . digits | epsilon
     * optional_exponent -> ((E | e) (+ | - | epsilon) digits) | epsilon
     * num -> sign digits optional_fraction optional_exponent
     */
    public static final String NUMBER_REGULAR_EXPRESS = "[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?";

    /**
     * (?s)	DOT ALL flag to treat line terminators (\n or \r) as literals
     * /\*	Start of comment literal, escaping *
     * (.)*	Any character zero or more times.
     * ?	The reluctant quantifier, finds matches in smaller parts
     */
    public static final String COMMENT_REGULAR_EXPRESS = "(?s)/\\*(.)*?\\*/";
    public static final String CURRENCY_MONEY_REGULAR_EXPRESS = "/(\\d)(?=(\\d{3})+(?!\\d))/g";

    /**
     * The expression used in the last section:
     * ^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$
     * Regular expression to allow numbers like +111 123 456 789:
     * ^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$
     * Pattern to allow numbers like +111 123 45 67 89:
     * ^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$
     */
    public static final String PHONE_NUMBER_REGULAR_EXPRESS = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$|^(\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4})";
    public static final String URL_REGULAR_EXPRESS = "(https?://)([^:^/]*)(:\\d*)?(.*)?";
    public static final String NAME_FILE_REGULAR_EXPRESS = "\\.(?=[^\\.]+$)";
    public static final String VERSION_NUMBER_REGULAR_EXPRESS = "^\\d+(\\.\\d+){0,2}$";
}
