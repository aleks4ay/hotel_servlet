<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value=
        "${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}"
       scope="session" />
<%--<c:set var="language" scope="session"
       value="${not empty sessionScope.get('userLocale') ? sessionScope.get('userLocale') : 'en'}"/>--%>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="i18n/hotel" />

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>

<html lang="${language}">
