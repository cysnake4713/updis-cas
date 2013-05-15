package com.updis.common;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Comcuter
 * Date: 5/10/13
 * Time: 11:20
 */
public class SendSMSJob {
    private static final Logger logger = LoggerFactory.getLogger(SendSMSJob.class);

    private static String corpId = "118775";
    private static String loginName = "72620872";
    private static String password = "047243";

    public static boolean sendSMS(String mobileNumber, String message) {
        boolean result = false;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://web.mobset.com/SDK/Sms_Send.asp");
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("CorpID", corpId));
            params.add(new BasicNameValuePair("LoginName", loginName));
            params.add(new BasicNameValuePair("Passwd", password));
            params.add(new BasicNameValuePair("send_no", mobileNumber));
            params.add(new BasicNameValuePair("msg", message));
            post.setEntity(new UrlEncodedFormEntity(params, "GBK"));

            String responseBody = httpClient.execute(post, new BasicResponseHandler());
            // 返回形式是 1,41569137. 前面是发送条数,后面是发送的序列号.目前只发一条,所以返回为 1, 或者 0,
            logger.debug(responseBody);
            if (responseBody.startsWith("1,")) {
                result = true;
                logger.debug("responseBody" + responseBody + "发送短信成功");
            } else {
                result = false;
                logger.debug("responseBody" + responseBody +"发送短信失败");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            post.releaseConnection();
        }
        return result;
    }
}
