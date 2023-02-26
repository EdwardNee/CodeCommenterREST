package com.proj.commenter.model;

public record CommentResponse(String generatedComment, CommentError error) {
}
