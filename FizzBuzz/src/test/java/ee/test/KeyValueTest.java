package ee.test;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KeyValueTest {
    @Test
    public void createKeyValueForAGivenSetOfValues() throws Exception {
        // Given
        String key = "abc";
        int value = 5;

        // When
        KeyValue keyValue = new KeyValue(key, value);

        // Then
        assertThat(keyValue.getKey()).isEqualTo(key);
        assertThat(keyValue.getValue()).isEqualTo(value);
    }

    @Test
    public void createKeyIntegerWhenKeyIsNumber() throws Exception {
        // Given
        String key = "10";
        int value = 5;

        // When
        KeyValue keyValue = new KeyValue(key, value);

        // Then
        assertThat(keyValue.getKey()).isEqualTo("integer");
        assertThat(keyValue.getValue()).isEqualTo(value);
    }
}