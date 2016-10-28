package ee.test;

public class KeyValue {
    private final String key;
    private final int value;

    public KeyValue(String key, int value) {
        if (isNumber(key)) {
           key = "integer";
        }

        this.key = key;
        this.value = value;
    }

    private boolean isNumber(String key) {
        try {
            Integer.parseInt(key);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    public String getKey() {
        return key;
    }

    public int getValue() {
        return value;
    }
}