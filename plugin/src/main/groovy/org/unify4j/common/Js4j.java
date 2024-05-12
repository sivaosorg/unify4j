package org.unify4j.common;


import java.io.*;
import java.nio.charset.StandardCharsets;

public class Js4j {

    private static final int EOF = -1;

    private PushbackInputStream pushbackInputStream;
    private OutputStream outputStream;
    private int currentCharacters;
    private int nextCharacters;
    private int line;
    private int column;

    public Js4j() {
        this.pushbackInputStream = null;
        this.outputStream = null;
    }

    /**
     * <p>
     * Takes the input JSON string and deletes the characters which are
     * insignificant to JavaScript. Comments will be removed, tabs will be
     * replaced with spaces, carriage returns will be replaced with line feeds,
     * and most spaces and line feeds will be removed. The result will be
     * returned.
     *
     * @param json The JSON string for which to minify
     * @return A minified, yet functionally identical, version of the input JSON
     * string
     */
    public String minify(String json) {
        if (String4j.isEmpty(json)) {
            return json;
        }
        InputStream input = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try {
            minify(input, output);
        } catch (Exception e) {
            return null;
        }
        return String4j.trimWhitespace(output.toString());
    }

    /**
     * Takes an input stream to a JSON string and outputs minified JSON to the
     * output stream.
     * <p>
     * Takes the input JSON via the input stream and deletes the characters
     * which are insignificant to JavaScript. Comments will be removed, tabs
     * will be replaced with spaces, carriage returns will be replaced with
     * line feeds, and most spaces and line feeds will be removed. The result is
     * streamed to the output stream.
     *
     * @param input  The <code>InputStream</code> from which to get the un-minified
     *               JSON
     * @param output The <code>OutputStream</code> where the resulting minified
     *               JSON will be streamed to
     */
    public void minify(InputStream input, OutputStream output) throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException {
        // Initialize
        this.pushbackInputStream = new PushbackInputStream(input);
        this.outputStream = output;
        this.line = 0;
        this.column = 0;
        currentCharacters = '\n';
        action(Action.DELETE_NEXT);

        // Process input
        while (currentCharacters != EOF) {
            switch (currentCharacters) {

                case ' ':
                    if (isAlphabetsAscii(nextCharacters)) {
                        action(Action.OUTPUT_CURR);
                    } else {
                        action(Action.DELETE_CURR);
                    }
                    break;

                case '\n':
                    switch (nextCharacters) {
                        case '{':
                        case '[':
                        case '(':
                        case '+':
                        case '-':
                            action(Action.OUTPUT_CURR);
                            break;
                        case ' ':
                            action(Action.DELETE_NEXT);
                            break;
                        default:
                            if (isAlphabetsAscii(nextCharacters)) {
                                action(Action.OUTPUT_CURR);
                            } else {
                                action(Action.DELETE_CURR);
                            }
                    }
                    break;

                default:
                    switch (nextCharacters) {
                        case ' ':
                            if (isAlphabetsAscii(currentCharacters)) {
                                action(Action.OUTPUT_CURR);
                                break;
                            }
                            action(Action.DELETE_NEXT);
                            break;
                        case '\n':
                            switch (currentCharacters) {
                                case '}':
                                case ']':
                                case ')':
                                case '+':
                                case '-':
                                case '"':
                                case '\'':
                                    action(Action.OUTPUT_CURR);
                                    break;
                                default:
                                    if (isAlphabetsAscii(currentCharacters)) {
                                        action(Action.OUTPUT_CURR);
                                    } else {
                                        action(Action.DELETE_NEXT);
                                    }
                            }
                            break;
                        default:
                            action(Action.OUTPUT_CURR);
                            break;
                    }
            }
        }
        output.flush();
    }

    /**
     * Process the current character with an appropriate action.
     * <p>
     * The action that occurs is determined by the current character. The
     * options are:
     * <p>
     * 1. Output currChar: output currChar, copy nextChar to currChar, get the
     * next character and save it to nextChar 2. Delete currChar: copy nextChar
     * to currChar, get the next character and save it to nextChar 3. Delete
     * nextChar: get the next character and save it to nextChar
     * <p>
     * This method essentially treats a string as a single character. Also
     * recognizes regular expressions if they are preceded by '(', ',', or '='.
     *
     * @param action The action to perform
     */
    private void action(Action action) throws IOException, UnterminatedRegExpLiteralException, UnterminatedCommentException, UnterminatedStringLiteralException {
        switch (action) {
            case OUTPUT_CURR:
                outputStream.write(currentCharacters);
            case DELETE_CURR:
                currentCharacters = nextCharacters;

                if (currentCharacters == '\'' || currentCharacters == '"') {
                    for (; ; ) {
                        outputStream.write(currentCharacters);
                        currentCharacters = get();
                        if (currentCharacters == nextCharacters) {
                            break;
                        }
                        if (currentCharacters <= '\n') {
                            throw new UnterminatedStringLiteralException(line, column);
                        }
                        if (currentCharacters == '\\') {
                            outputStream.write(currentCharacters);
                            currentCharacters = get();
                        }
                    }
                }
            case DELETE_NEXT:
                nextCharacters = next();
                if (nextCharacters == '/' && (currentCharacters == '(' || currentCharacters == ',' || currentCharacters == '=' || currentCharacters == ':')) {
                    outputStream.write(currentCharacters);
                    outputStream.write(nextCharacters);
                    for (; ; ) {
                        currentCharacters = get();
                        if (currentCharacters == '/') {
                            break;
                        } else if (currentCharacters == '\\') {
                            outputStream.write(currentCharacters);
                            currentCharacters = get();
                        } else if (currentCharacters <= '\n') {
                            throw new UnterminatedRegExpLiteralException(line, column);
                        }
                        outputStream.write(currentCharacters);
                    }
                    nextCharacters = next();
                }
        }
    }

    /**
     * Determines whether a given character is a letter, digit, underscore,
     * dollar sign, or non-ASCII character.
     *
     * @param characters The character to compare
     * @return True if the character is a letter, digit, underscore, dollar
     * sign, or non-ASCII character. False otherwise.
     */
    private boolean isAlphabetsAscii(int characters) {
        return ((characters >= 'a' && characters <= 'z') || (characters >= '0' && characters <= '9') || (characters >= 'A' && characters <= 'Z') || characters == '_' || characters == '$' || characters == '\\' || characters > 126);
    }

    /**
     * Returns the next character from the input stream.
     * <p>
     * Will pop the next character from the input stack. If the character is a
     * control character, translate it to a space or line feed.
     *
     * @return The next character from the input stream
     */
    private int get() throws IOException {
        int c = pushbackInputStream.read();

        if (c == '\n') {
            line++;
            column = 0;
        } else {
            column++;
        }

        if (c >= ' ' || c == '\n' || c == EOF) {
            return c;
        }

        if (c == '\r') {
            column = 0;
            return '\n';
        }

        return ' ';
    }

    /**
     * Returns the next character from the input stream without popping it from
     * the stack.
     *
     * @return The next character from the input stream
     */
    private int peek() throws IOException {
        int lookaheadCharacter = pushbackInputStream.read();
        pushbackInputStream.unread(lookaheadCharacter);
        return lookaheadCharacter;
    }

    /**
     * Get the next character from the input stream, excluding comments.
     * <p>
     * Will read from the input stream via the <code>get()</code> method. Will
     * exclude characters that are part of comments.  <code>peek()</code> is used
     * to se if a '/' is followed by a '/' or a '*' for the purpose of
     * identifying comments.
     *
     * @return The next character from the input stream, excluding characters
     * from comments
     */
    private int next() throws IOException, UnterminatedCommentException {
        int characters = get();

        if (characters == '/') {
            switch (peek()) {

                case '/':
                    for (; ; ) {
                        characters = get();
                        if (characters <= '\n') {
                            return characters;
                        }
                    }

                case '*':
                    get();
                    for (; ; ) {
                        switch (get()) {
                            case '*':
                                if (peek() == '/') {
                                    get();
                                    return ' ';
                                }
                                break;
                            case EOF:
                                throw new UnterminatedCommentException(line, column);
                        }
                    }

                default:
                    return characters;
            }

        }
        return characters;
    }

    public enum Action {
        OUTPUT_CURR, DELETE_CURR, DELETE_NEXT
    }

    public static class UnterminatedCommentException extends Exception {
        public UnterminatedCommentException(int line, int column) {
            super("Unterminated comment at line " + line + " and column " + column);
        }
    }

    public static class UnterminatedStringLiteralException extends Exception {
        public UnterminatedStringLiteralException(int line, int column) {
            super("Unterminated string literal at line " + line + " and column " + column);
        }
    }

    public static class UnterminatedRegExpLiteralException extends Exception {
        public UnterminatedRegExpLiteralException(int line, int column) {
            super("Unterminated regular expression at line " + line + " and column " + column);
        }
    }
}
