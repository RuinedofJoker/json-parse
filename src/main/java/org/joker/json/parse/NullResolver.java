package org.joker.json.parse;

public class NullResolver implements IJsonResolver {
    @Override
    public boolean supportParse(String json, int startIndex) {
        return json != null && json.length() != 0 && startIndex >= 0 && startIndex < json.length() && (startIndex + 4) <= json.length() && json.startsWith("null", startIndex);
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        return new ResolverParseResult(startIndex, startIndex + 3, null);
    }
}
