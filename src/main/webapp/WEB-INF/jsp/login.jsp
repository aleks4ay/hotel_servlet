<%@include file="head/headerPrefixAndLanguage.jsp"%>

<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/logo.css">
    <%--<link rel="stylesheet" type="text/css" href="/static/css/navbar.css">--%>
    <style>
        .hello_1 {
            height: 1200px;
            /*background-image: url(/static/img/background_1.jpg);*/
            /*background-size: cover;*/
            background-size: 100%;
            background-position: center;
            background-repeat: no-repeat;
        }
    </style>
</head>
<body class="hello_1">
<div class="center1">
    <form class="inner_logo" method="post" action="/login">
        <div class="mb-3">
            <label for="login1" class="form-label"> Login </label>
            <input type="text" class="form-control" id="login1" name="login" value="${oldLogin != null ? oldLogin :''}">
            <small class="text-danger">
                <c:if test="${not empty wrongLogin}"> <fmt:message key="wrongLogin"/> </c:if>
            </small>
        </div>
        <div class="mb-3">
            <label for="passwordField" class="form-label">  <fmt:message key="password"/>  </label>
            <input type="password" class="form-control" id="passwordField" name="log_pass">
            <small class="text-danger">
                <c:if test="${not empty wrongPass}"> <fmt:message key="wrongPass"/> </c:if>
            </small>
        </div>
        <button type="submit" class="btn btn-outline-success">  <fmt:message key="loginNow"/>  </button>
        <button type="button" class="btn btn-outline-danger" onclick="window.location='${not empty backUrl ? backUrl : '/home'}'" >
            <fmt:message key="cancel"/>
        </button>
        <%--<button type="button" class="btn btn-outline-danger" onclick="window.history.back()"> <fmt:message key="cancel"/> </button>--%>
<%--        <div style="margin-top: 30px; font-size: 1.3em">
            <a href='<c:url value="/registration" />'> <fmt:message key="registration"/> </a>
        </div>--%>
    </form>
</div>
</body>
</html>