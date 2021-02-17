package smart.hub.helpers;

import java.util.Base64;

public class Conversions {

    public static String encodeStringToBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64ToString(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

}
