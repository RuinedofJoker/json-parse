package org.joker.json.parse;

import org.joker.json.exception.common.Constants;

public class StringResolver implements IJsonResolver {

    @Override
    public boolean supportParse(String json, int startIndex) {
        return json.charAt(startIndex) == '"' || ((startIndex + 1) < json.length() && json.charAt(startIndex) == '\\' && json.charAt(startIndex + 1) == '"');
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        int currentIndex = startIndex + (json.charAt(startIndex) == '"' ? 1 : 2);
        boolean currentParseEnd = false;
        ResolverParseResult result = new ResolverParseResult(startIndex);
        StringBuilder str = new StringBuilder();

        while (currentIndex < json.length()) {
            if (json.charAt(currentIndex) != '"' || (json.charAt(currentIndex) == '"' && (currentIndex - 1) > startIndex && json.charAt(currentIndex - 1) == '\\')) {
                str.append(json.charAt(currentIndex));
            } else {
                currentParseEnd = true;
                result.setResult(str.toString());
                result.setEndIndex(currentIndex);
                break;
            }
            currentIndex++;
        }

        if (!currentParseEnd) {
            result.setResult(Constants.ILLEGAL_FLAG);
        }

        return result;
    }
}
