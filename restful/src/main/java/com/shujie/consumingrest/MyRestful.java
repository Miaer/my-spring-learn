package com.shujie.consumingrest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(ignoreUnknown = true)
public class MyRestful {

    private Long id;
    private String content;

    public MyRestful() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "MyRestful{" +
                "id=" + id +
                ", content='" + content + ''' +
                '}';
    }
}
