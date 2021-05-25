package com.rs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FindWordTest {

    @Test
    public void doTest() {
        FindWord findWord = new FindWord();

        assertThat(findWord.find(new String[]{"P>E","E>R","R>U"})).isEqualTo("PERU");
        assertThat(findWord.find(new String[]{"I>N","A>I","P>A","S>P"})).isEqualTo("SPAIN");
    }
}
