package org.joker.json;

import org.joker.json.exception.JsonFormatErrorException;
import org.joker.json.parse.ResolverGroup;

public class JsonFacade {

    public static JsonObject parseObject(String json) {
        try {
            return (JsonObject) ResolverGroup.INSTANCE.parse(json, 0).getResult();
        } catch (JsonFormatErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonFormatErrorException(json, e);
        }
    }

    public static JsonArray parseArray(String json) {
        try {
            return (JsonArray) ResolverGroup.INSTANCE.parse(json, 0).getResult();
        } catch (JsonFormatErrorException e) {
            throw e;
        } catch (Exception e) {
            throw new JsonFormatErrorException(json, e);
        }
    }
}
