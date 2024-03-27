package org.joker.json.parse;

import org.joker.json.exception.JsonFormatErrorException;

public interface IJsonResolver {

    default void indexLegal(String json, int startIndex) {
        if (startIndex < 0 || startIndex >= json.length()) {
            throw new JsonFormatErrorException(this.getClass().getName() + " Not override indexLegal method ");
        }
    }

    default boolean supportParse(String json, int startIndex) {
        indexLegal(json, startIndex);
        return false;
    }

    default ResolverParseResult parse(String json, int startIndex) {
        throw new JsonFormatErrorException(this.getClass().getName() + " Not override parse method ");
    }
}
