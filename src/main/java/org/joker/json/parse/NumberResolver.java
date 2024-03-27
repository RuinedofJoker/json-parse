package org.joker.json.parse;

public class NumberResolver implements IJsonResolver {

    @Override
    public boolean supportParse(String json, int startIndex) {
        return json.charAt(startIndex) >= '0' && json.charAt(startIndex) <= '9';
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        boolean currentParseEnd = false;
        boolean findPoint = false;
        int currentIndex = startIndex;
        StringBuilder num = new StringBuilder();
        ResolverParseResult result = new ResolverParseResult(startIndex);

        while (currentIndex < json.length()) {
            char currentChar = json.charAt(currentIndex);
            if (currentChar >= '0' && currentChar <= '9') {
                num.append(currentChar);
            } else if (currentChar == '.') {
                if (!findPoint) {
                    num.append(currentChar);
                    findPoint = true;
                } else {
                    result.setResult(IllegalFlag.ILLEGAL_FLAG);
                    return result;
                }
            } else {
                currentParseEnd = true;
                result.setEndIndex(currentIndex - 1);
                break;
            }
            currentIndex++;
        }

        if (!currentParseEnd) {
            result.setResult(IllegalFlag.ILLEGAL_FLAG);
            return result;
        }

        if (findPoint) {
            result.setResult(Double.parseDouble(num.toString()));
        } else {
            result.setResult(Integer.parseInt(num.toString()));
        }
        return result;
    }

}
