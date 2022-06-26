package utils;

public interface IPreconditions {

    public static void checkArgument(boolean expression, String errorMessage){
        if (!expression){
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static String checkLength(String value, int lengh, String errorMessage){
        String valueClean = value.strip();
        if (value == null | valueClean.length() != lengh){
            throw new IllegalArgumentException(errorMessage);
        }
        return valueClean;
    }
    public static String checkLengthIsGreaterThan(String value, int length, String errorMessage) {
        String valueClean = value.strip();
        if(value == null | valueClean.length() <= length) {
            throw new IllegalArgumentException(errorMessage);
        }
        return valueClean;
    }

    public static <T> T checkNotNull(T value, String errorMessage) {
        if(value == null) {
            throw new IllegalArgumentException(errorMessage);
        }
        return value;
    }

    public static <T> T requireNonNullElse(T value, T orElse) {
        return value != null ? value : orElse;
    }
}
