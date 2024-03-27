package org.joker.json.parse;

import org.joker.json.exception.common.Constants;

public class ResolverParseResult {

    private final Object[] resultRecord;

    public ResolverParseResult() {
        resultRecord = new Object[3];
        setResult(Constants.ILLEGAL_FLAG);
    }

    public ResolverParseResult(Integer startIndex) {
        this();
        setStartIndex(startIndex);
    }

    public ResolverParseResult(Integer startIndex, Integer endIndex) {
        this();
        setStartIndex(startIndex);
        setEndIndex(endIndex);
    }

    public ResolverParseResult(Integer startIndex, Object result) {
        this();
        setStartIndex(startIndex);
        setResult(result);
    }

    public ResolverParseResult(Integer startIndex, Integer endIndex, Object result) {
        this();
        setStartIndex(startIndex);
        setEndIndex(endIndex);
        setResult(result);
    }

    public Object getResult() {
        return resultRecord[0];
    }

    public Integer getStartIndex() {
        return (Integer) resultRecord[1];
    }

    public Integer getEndIndex() {
        return (Integer) resultRecord[2];
    }

    public void setResult(Object result) {
        this.resultRecord[0] = result;
    }

    public void setStartIndex(Integer startIndex) {
        this.resultRecord[1] = startIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.resultRecord[2] = endIndex;
    }
}
