<%@include file="fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="fragments/head.jsp"%>

<body>

<%@include file="fragments/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">
        <div class="col-sm-3">
            <h2> <fmt:message key="head_text_2"/> </h2>
            <h3> <fmt:message key="head_text_3"/> </h3>
        </div>

        <div class="col-sm-8">

            <img style="width: 100%" src="<c:url value="/static/img/background.jpg"/>" alt="background"/>

        </div>

    </div>
</div>

</body>
</html>