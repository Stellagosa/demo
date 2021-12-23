package com.stellagosa.demo.activiti.full.bean.entity;

import java.util.List;

/**
 * @Author: Stellagosa
 * @Date: 2021/12/23 14:51
 * @Description:
 */
public class Content {

    private String body;
    private boolean approved;
    private List<String> tags;

    public Content(String body, boolean approved, List<String> tags) {
        this.body = body;
        this.approved = approved;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Content{" +
                "body='" + body + '\'' +
                ", approved=" + approved +
                ", tags=" + tags +
                '}';
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
