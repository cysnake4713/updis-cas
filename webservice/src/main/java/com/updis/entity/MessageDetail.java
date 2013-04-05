package com.updis.entity;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 4/5/13
 * Time: 9:35 AM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class MessageDetail extends Message {
    private String fbbm;
    private Integer readCount;
    private List<Comment> comments;

    public String getFbbm() {
        return fbbm;
    }

    public void setFbbm(String fbbm) {
        this.fbbm = fbbm;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
