package com.proj.commenter.commenteer;

public class Comment {
    private String code;

    private String firstPart;

    public Comment() {}


    public Comment(String code, String firstPart) {
        this.code = code;
        this.firstPart = firstPart;
    }

    public String getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(String firstPart) {
        this.firstPart = firstPart;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
