package org.unify4j.model.request;

import java.io.Serializable;

@SuppressWarnings({"all"})
public class CharacterFreqRequest implements Serializable {
    private final char character;
    private final int frequency;

    public CharacterFreqRequest(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
    }

    public char getCharacter() {
        return character;
    }

    public int getFrequency() {
        return frequency;
    }
}
