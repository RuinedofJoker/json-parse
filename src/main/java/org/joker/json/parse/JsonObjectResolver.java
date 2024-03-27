package org.joker.json.parse;

import org.joker.json.JsonObject;

public class JsonObjectResolver implements IJsonResolver {

    @Override
    public boolean supportParse(String json, int startIndex) {
        indexLegal(json, startIndex);
        return json.charAt(startIndex) == '{';
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        if (json.charAt(startIndex) != '{') {
            return null;
        }
        JsonObject jsonObject = new JsonObject();
        ResolverParseResult result = new ResolverParseResult(startIndex, jsonObject);

        boolean currentParseEnd = false;
        int currentIndex = startIndex + 1;

        String currentKey = null;
        int kvFlag = 0;
        boolean initState = true;
        boolean findSeparator = false;

        while (currentIndex < json.length()) {
            char currentChar = json.charAt(currentIndex);
            if (currentChar == '}') {
                result.setEndIndex(currentIndex);
                currentParseEnd = true;
                break;
            } else if (currentChar == ' ' || currentChar == '\r'|| currentChar == '\n') {
                currentIndex++;
                continue;
            } else if (currentChar == ',') {
                if (initState || kvFlag % 2 == 1 || findSeparator) {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                } else {
                    currentIndex++;
                    findSeparator = true;
                    continue;
                }
            } else if (currentChar == ':') {
                if (kvFlag % 2 == 0) {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                } else {
                    currentIndex++;
                    findSeparator = true;
                    continue;
                }
            }

            initState = false;
            findSeparator = false;
            ResolverParseResult resolverParseResult = ResolverGroup.INSTANCE.parse(json, currentIndex);
            if (kvFlag % 2 == 0) {
                if (!(resolverParseResult.getResult() instanceof String)) {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                }
                currentKey = (String) resolverParseResult.getResult();
            } else {
                jsonObject.put(currentKey, resolverParseResult.getResult());
                currentKey = null;
            }
            kvFlag++;

            currentIndex = resolverParseResult.getEndIndex() + 1;
        }

        if (!currentParseEnd) {
            result.setResult(IllegalFlag.ILLEGAL_FLAG);
        }
        return result;
    }
}
