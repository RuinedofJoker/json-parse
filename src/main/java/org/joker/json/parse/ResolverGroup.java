package org.joker.json.parse;

import org.joker.json.exception.JsonFormatErrorException;
import org.joker.json.exception.common.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResolverGroup implements IJsonResolver {

    public static final ResolverGroup INSTANCE = new ResolverGroup();

    private static final List<IJsonResolver> jsonResolvers;

    static {
        List<IJsonResolver> jsonResolverList = new ArrayList<>();
        jsonResolverList.add(new JsonObjectResolver());
        jsonResolverList.add(new JsonArrayResolver());
        jsonResolverList.add(new StringResolver());
        jsonResolverList.add(new NumberResolver());
        jsonResolverList.add(new NullResolver());
        jsonResolvers = Collections.unmodifiableList(jsonResolverList);
    }

    @Override
    public boolean supportParse(String json, int startIndex) {
        return IJsonResolver.super.supportParse(json, startIndex);
    }

    @Override
    public ResolverParseResult parse(String json, int startIndex) {
        if (json == null || json.length() == 0) {
            return new ResolverParseResult(startIndex, startIndex, null);
        }
        int currentIndex = startIndex;
        while (currentIndex < json.length() && (json.charAt(currentIndex) == ' ' || json.charAt(currentIndex) == '\r'|| json.charAt(currentIndex) == '\n')) {
            currentIndex++;
        }
        ResolverParseResult result = null;
        for (IJsonResolver jsonResolver : jsonResolvers) {
            if (jsonResolver.supportParse(json, currentIndex)) {
                result = jsonResolver.parse(json, currentIndex);
                if (!Objects.equals(result.getResult(), Constants.ILLEGAL_FLAG)) {
                    result.setStartIndex(startIndex);
                    return result;
                }
            }
        }
        throw new JsonFormatErrorException(json, currentIndex);
    }
}
