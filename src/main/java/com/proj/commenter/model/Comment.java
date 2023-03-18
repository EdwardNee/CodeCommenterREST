package com.proj.commenter.model;

import java.util.Objects;

public class Comment {
    private String code;

    private String firstPart;

    public Comment() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return code.equals(comment.code) && firstPart.equals(comment.firstPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, firstPart);
    }

    @Override
    public String toString() {
        return "Comment{"
                + "code='" + code + '\''
                + ", firstPart='" + firstPart + '\''
                + '}';
    }
}
