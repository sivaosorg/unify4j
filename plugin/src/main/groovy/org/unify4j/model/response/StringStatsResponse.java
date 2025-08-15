package org.unify4j.model.response;

import java.io.Serializable;

@SuppressWarnings({"all"})
public class StringStatsResponse implements Serializable {
    private final int totalLength;
    private final int wordCount;
    private final int lineCount;
    private final int characterCount;
    private final int digitCount;
    private final int letterCount;
    private final int uppercaseCount;
    private final int lowercaseCount;
    private final int specialCharCount;

    public StringStatsResponse() {
        this(0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public StringStatsResponse(int totalLength, int wordCount, int lineCount, int characterCount, int digitCount, int letterCount, int uppercaseCount, int lowercaseCount, int specialCharCount) {
        this.totalLength = totalLength;
        this.wordCount = wordCount;
        this.lineCount = lineCount;
        this.characterCount = characterCount;
        this.digitCount = digitCount;
        this.letterCount = letterCount;
        this.uppercaseCount = uppercaseCount;
        this.lowercaseCount = lowercaseCount;
        this.specialCharCount = specialCharCount;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public int getWordCount() {
        return wordCount;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getCharacterCount() {
        return characterCount;
    }

    public int getDigitCount() {
        return digitCount;
    }

    public int getLetterCount() {
        return letterCount;
    }

    public int getUppercaseCount() {
        return uppercaseCount;
    }

    public int getLowercaseCount() {
        return lowercaseCount;
    }

    public int getSpecialCharCount() {
        return specialCharCount;
    }

    public double getAverageWordLength() {
        return wordCount > 0 ? (double) characterCount / wordCount : 0.0;
    }
}
