package com.updis.pushNotification;

import com.updis.entity.MessageDetail;
import com.updis.service.object.MessageDetailERPObjectService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.codehaus.jackson.map.ObjectMapper;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.*;

/**
 * User: Comcuter
 * Date: 4/30/13
 * Time: 20:12
 */

// 如果 PushJob 不是 public 的,Quartz 就不能正常运行,只好和 PushListener 分开.
public class PushJob implements Job {

    private Logger logger = LoggerFactory.getLogger(PushJob.class);
    private MessageDetailERPObjectService messageDetailService;
    private PushJobConfig pushJobConfig;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        ServletContext servletContext = (ServletContext)dataMap.get("servletContext");
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        messageDetailService = context.getBean(MessageDetailERPObjectService.class);
        pushJobConfig = context.getBean(PushJobConfig.class);

        Date fromDate = new Date(new Date().getTime() - PushListener.PULL_INTERVAL_MINUTE * 60 * 1000);
        Date toDate = new Date();
        List<MessageDetail> messageDetails = new ArrayList<MessageDetail>();
        for (int i = 0; i <= 3; i++) {
            try {
                messageDetails = messageDetailService.getMesagesBetweenDate(fromDate, toDate);
                pushMessages(messageDetails);
                break;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public void pushMessages(List<MessageDetail> messageDetails) {
         if (messageDetails.size() != 0 ) {
            for (MessageDetail message : messageDetails) {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                try {
                    HttpPost post = new HttpPost("http://api.jpush.cn:8800/sendmsg/v2/sendmsg");
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("sendno", message.getContentId() + ""));
                    params.add(new BasicNameValuePair("app_key", pushJobConfig.getAppKey()));
                    params.add(new BasicNameValuePair("receiver_type", pushJobConfig.getReceiveType()));
                    params.add(new BasicNameValuePair("receiver_value", pushJobConfig.getReceiveValue()));
                    params.add(new BasicNameValuePair("msg_type", pushJobConfig.getMessageType()));
                    params.add(new BasicNameValuePair("msg_content", buildMessageContent(message)));
                    params.add(new BasicNameValuePair("platform", pushJobConfig.getPlatform()));
                    params.add(new BasicNameValuePair("verification_code", buildVerifyCode(message.getContentId() + "")));
                    post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

                    String responseBody = httpClient.execute(post, new BasicResponseHandler());
                    logger.debug(responseBody);
                } catch (Exception e) {
                  logger.error(e.getMessage(), e);
                } finally {
                    httpClient.getConnectionManager().shutdown();
                }
            }
         }
    }

    // 发送消息的验证码用来验证是否有权限发送推送消息.
    private String buildVerifyCode(String messageId) {
        String input = messageId + pushJobConfig.getReceiveType() + pushJobConfig.getReceiveValue() + pushJobConfig.getMasterSecret();
        return DigestUtils.md5Hex(input).toUpperCase();
    }

    // 发送的消息内容是Json格式的.
    private String buildMessageContent(MessageDetail message) {
        String returnValue = "";

        Map<String, Object> messageContent = new HashMap<String, Object>();
        Map<String, String> extras = new HashMap<String, String>();
        messageContent.put("n_content", message.getTitle());
        extras.put("messageId", message.getContentId() + "");
        messageContent.put("n_extras", extras);
        try {
            returnValue = new ObjectMapper().writeValueAsString(messageContent);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return returnValue;
    }
}

@Component
class PushJobConfig {
    private String appKey = "f1b5f4b4b93d29300ec3ae1d";
    private String receiveType = "2";
    private String receiveValue = "default";
    private String messageType = "1";
    private String platform = "android";
    private String masterSecret = "732c0c785e57e0c10cb9f05a";

    String getAppKey() {
        return appKey;
    }

    void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    String getReceiveType() {
        return receiveType;
    }

    void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    String getReceiveValue() {
        return receiveValue;
    }

    void setReceiveValue(String receiveValue) {
        this.receiveValue = receiveValue;
    }

    String getMessageType() {
        return messageType;
    }

    void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    String getPlatform() {
        return platform;
    }

    void setPlatform(String platform) {
        this.platform = platform;
    }

    String getMasterSecret() {
        return masterSecret;
    }

    void setMasterSecret(String masterSecret) {
        this.masterSecret = masterSecret;
    }
}