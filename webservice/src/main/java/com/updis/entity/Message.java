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
    private String iconUrl;
    private String datetime;

    public Message() {
    }

    public Message(Integer contentId, String subtitle, String author, String title, String iconUrl, String datetime) {
        this.contentId = contentId;
        this.subtitle = subtitle;
        this.author = author;
        this.title = title;
        this.iconUrl = iconUrl;
        this.datetime = datetime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.getTitle());
        stringBuffer.append(" Published on: ");
        stringBuffer.append(this.getDatetime());
        return stringBuffer.toString();
    }
}
