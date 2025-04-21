package com.evandrorenan.web3270datasysdump.domain.model;

public class Blob {
    private final byte[] content;
    private final String id;
    private final String contentType;

    public Blob(byte[] content, String id, String contentType) {
        this.content = content;
        this.id = id;
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getContentType() {
        return contentType;
    }
}
