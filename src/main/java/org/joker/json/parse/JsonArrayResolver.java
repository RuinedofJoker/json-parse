package org.joker.json.parse;

import org.joker.json.JsonArray;


public class JsonArrayResolver implements IJsonResolver {

    @Override
    public boolean supportParse(String json, int startIndex) {
        indexLegal(json, startIndex);
        return json.charAt(startIndex) == '[';
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        if (json.charAt(startIndex) != '[') {
            return null;
        }
        JsonArray jsonArray = new JsonArray();
        ResolverParseResult result = new ResolverParseResult(startIndex, jsonArray);

        boolean currentParseEnd = false;
        int currentIndex = startIndex + 1;
        boolean initState = true;
        boolean findSeparator = false;

        while (currentIndex < json.length()) {
            char currentChar = json.charAt(currentIndex);
            if (currentChar == ']') {
                if (findSeparator) {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                } else {
                    result.setEndIndex(currentIndex);
                    currentParseEnd = true;
                    break;
                }
            } else if (currentChar == ' ' || currentChar == '\r'|| currentChar == '\n') {
                currentIndex++;
                continue;
            } else if (currentChar == ',') {
                if (initState || findSeparator) {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                } else {
                    findSeparator = true;
                    currentIndex++;
                    continue;
                }
            }

            ResolverParseResult resolverParseResult = ResolverGroup.INSTANCE.parse(json, currentIndex);
            jsonArray.add(resolverParseResult.getResult());
            findSeparator = false;
            initState = false;
            currentIndex = resolverParseResult.getEndIndex() + 1;
        }

        if (!currentParseEnd) {
            result.setResult(IllegalFlag.ILLEGAL_FLAG);
        }
        return result;
    }
}
