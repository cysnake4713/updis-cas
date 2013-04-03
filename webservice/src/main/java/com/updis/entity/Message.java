package com.updis.entity;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Zhou Guangwen
 * Date: 3/27/13
 * Time: 10:47 PM
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Message {
    private Integer contentId;
    private String subtitle;
    private String author;
    private String title;
    private String content;
    private String iconUrl;
    private Date datetime;

    public Message() {
    }

    public Message(Integer contentId, String subtitle, String author, String title, String content, String iconUrl, Date datetime) {
        this.contentId = contentId;
        this.subtitle = subtitle;
        this.author = author;
        this.title = title;
        this.content = content;
        this.iconUrl = iconUrl;
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
