<%@include file="head/headerPrefixAndLanguage.jsp"%>

<%@include file="head/head.jsp"%>

<body>

<%@include file="head/navbar.jsp"%>

    <div class="center1">
        <form class="inner_logo" method="post" action="/registration">
            <div class="mb-3">
                <label for="login1" class="form-label"> Login </label>
                <input type="text" class="form-control" id="login1"  name="loginNew"
                       value="${oldLogin != null ? oldLogin : ''}">
                <small class="text-danger">
                    <c:if test="${not empty warnLogin}"> <fmt:message key="userExists"/> </c:if>
                    <c:if test="${not empty warnLogin2}"> <fmt:message key="emptyField"/> </c:if>
                </small>
            </div>
            <div class="mb-3">
                <label for="name1" class="form-label">
                    <fmt:message key="firstName"/>
                </label>
                <input type="text" class="form-control" id="name1"  name="firstName"
                       value="${oldFirstName != null ? oldFirstName : ''}">
                <small class="text-danger">
                    <c:if test="${not empty warnFirstName}"> <fmt:message key="emptyField"/> </c:if>
                </small>
            </div>
            <div class="mb-3">
                <label for="name2" class="form-label">
                    <fmt:message key="lastName"/>
                </label>
                <input type="text" class="form-control" id="name2"  name="lastName"
                       value="${oldLastName != null ? oldLastName : ''}">
                <small class="text-danger">
                    <c:if test="${not empty warnLastName}"> <fmt:message key="emptyField"/> </c:if>
                </small>
            </div>
    <%--        <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" >
            </div>--%>
            <div class="mb-3">
                <label for="pass" class="form-label">
                    <fmt:message key="password"/>
                </label>
                <input type="password" class="form-control" id="pass" name="log_pass">
                <small class="text-danger">
                    <c:if test="${not empty warnPass}"> <fmt:message key="emptyField"/> </c:if>
                </small>
            </div>
<%--            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="exampleCheck1" name="checkLogin">
                <label class="form-check-label" for="exampleCheck1">
                    <fmt:message key="loginNow"/>
                </label>
            </div>--%>
            <button type="submit" class="btn btn-outline-success">
                <fmt:message key="register"/>
            </button>
            <button type="button" class="btn btn-outline-danger" onclick="window.location='${not empty backUrl ? backUrl : '/home'}'" >
                <fmt:message key="cancel"/>
            </button>
            <div style="margin-top: 30px; font-size: 1.3em">
                <a href='<c:url value="/login" />'> <fmt:message key="toLogin"/> </a>
            </div>
        </form>
    </div>
</body>
</html>