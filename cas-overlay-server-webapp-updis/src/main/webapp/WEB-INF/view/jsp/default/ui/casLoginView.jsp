<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page session="true" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<spring:theme code="mobile.custom.css.file" var="mobileCss" text=""/>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>UpdisERP</title>
    <link rel="shortcut icon" href="/cas/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="/cas/css/full.css"/>
    <link rel="stylesheet" href="/cas/css/detail.css"/>
</head>
<body style="background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAYAAAAGCAYAAADgzO9IAAAAKUlEQVQIHWO8e/fufwYsgAUkJigoiCIF5DMyoYggcUiXgNnBiGQKmAkARpcEQeriln4AAAAASUVORK5CYII=);">
<!--[if lte IE 8]>
<script src="//ajax.googleapis.com/ajax/libs/chrome-frame/1/CFInstall.min.js"></script>
<script>CFInstall.check({mode: "overlay"});</script>
<![endif]-->

<div class="openerp openerp_webclient_container">
    <table class="oe_webclient">
        <tbody>
        <tr>
            <td class="oe_application">
                <div>
                    <div class="oe_login">
                        <div class="oe_login_bottom"></div>
                        <div class="oe_login_error_message"></div>
                        <div class="oe_login_pane">
                            <div class="oe_login_logo"><img src="/cas/img/logo.png"></div>
                            <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
                                <form:errors path="*" id="msg" cssClass="errors" element="div"/>
                                <ul>
                                    <li data-modes="default" style="">用户名</li>
                                    <li>
                                        <c:if test="${not empty sessionScope.openIdLocalId}">
                                            <strong>${sessionScope.openIdLocalId}</strong>
                                            <input type="hidden" id="username" name="username"
                                                   value="${sessionScope.openIdLocalId}"/>
                                        </c:if>
                                        <c:if test="${empty sessionScope.openIdLocalId}">
                                            <spring:message code="screen.welcome.label.netid.accesskey"
                                                            var="userNameAccessKey"/>
                                            <form:input cssClass="required" cssErrorClass="error" id="username"
                                                        size="25" tabindex="1" accesskey="${userNameAccessKey}"
                                                        path="username" autocomplete="false" htmlEscape="true"/>
                                        </c:if>
                                    </li>
                                    <li>密码</li>
                                    <li>
                                        <form:password cssClass="required" cssErrorClass="error" id="password" size="25"
                                                       tabindex="2" path="password" accesskey="${passwordAccessKey}"
                                                       htmlEscape="true" autocomplete="off"/>
                                    </li>
                                    <input type="hidden" name="lt" value="${loginTicket}"/>
                                    <input type="hidden" name="execution" value="${flowExecutionKey}"/>
                                    <input type="hidden" name="_eventId" value="submit"/>
                                    <li>
                                        <button name="submit" accesskey="l" tabindex="4" type="submit">登录</button>
                                    </li>

                                </ul>
                            </form:form>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <div class="oe_notification ui-notify" style=""></div>
    <div class="oe_loading" style="display: none;">Loading</div>
</div>
</body>
</html>

