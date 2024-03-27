package org.joker.json.exception;

public class JsonFormatErrorException extends RuntimeException {

    public JsonFormatErrorException() {
        super();
    }

    public JsonFormatErrorException(String message) {
        super(message);
    }

    public JsonFormatErrorException(String json, Exception e) {
        super("JsonFormatErrorException in parseJsonString: \n" + json + "\nAdditional Exception Info:" + e.getMessage());
    }

    public JsonFormatErrorException(String json, int startIndex) {
        this(formatError(json, startIndex));
    }

    private static String formatError(String json, int startIndex) {
        StringBuilder sb = new StringBuilder();
        sb.append("JsonFormatErrorException in parseJsonString: startIndex is ");
        sb.append(startIndex);
        sb.append("\n");

        StringBuilder beforeSpace = new StringBuilder();
        for (int i = 0; i < json.length(); i++) {
            if (startIndex == i) {
                beforeSpace.append("^").append("\n");
                boolean hasNextLineFeed = false;
                for (int j = i; j < json.length(); j++) {
                    sb.append(json.charAt(j));
                    if (json.charAt(j) == '\n') {
                        sb.append(beforeSpace);
                        hasNextLineFeed = true;
                    }
                }
                if (!hasNextLineFeed) {
                    sb.append("\n").append(beforeSpace);
                }
                break;
            } else {
                beforeSpace.append(" ");
            }
            sb.append(json.charAt(i));
            if (json.charAt(i) == '\n') {
                beforeSpace.delete(0, beforeSpace.length());
            }
        }
        return sb.toString();
    }
}
