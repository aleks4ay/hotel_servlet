<%@include file="fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="fragments/head.jsp"%>

<body>

<%@include file="fragments/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">

        <div class="col-sm-2">
        </div>


        <div class="col-sm-8">

            <c:if test="${fn:contains(action, 'user')}">  <%@include file="adm/users.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'room')}">  <%@include file="adm/rooms.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'order')}">  <%@include file="adm/orders.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'changeRoom')}">  <%@include file="adm/changeRoom.jsp"%> </c:if>

        </div>


        <div class="col-sm-2">
            <c:if test="${fn:contains(action, 'room')}"> <%@include file="adm/newRoom.jsp"%> </c:if>
        </div>
    </div>
</div>

</body>
</html>