<%@include file="head/headerPrefixAndLanguage.jsp"%>
<%@include file="head/head.jsp"%>

<body>

<%@include file="head/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">
        <%@include file="head/leftPanel.jsp"%>

        <div class="col-sm-8">
            <c:if test="${(sessionScope.get('user').client) and (action != 'room')}">
                <div class="btn-group" style="width: 100%">
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=order'">
                        <h5> <fmt:message key="adm_act_3"/> </h5>
                    </button>
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=proposal'">
                        <h5> <fmt:message key="proposal"/> </h5>
                    </button>
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=bill'">
                        <h5> <fmt:message key="bill"/> </h5>
                    </button>
                </div>
            </c:if>

            <%--@elvariable id="action" type="java.lang.String"--%>
            <c:if test="${fn:contains(action, 'order')}">  <%@include file="usr/orders.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'proposal')}">  <%@include file="usr/proposals.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'bill')}">  <%@include file="usr/bill.jsp"%> </c:if>

            <c:if test="${fn:contains(action, 'room')}">  <%@include file="usr/rooms.jsp"%> </c:if>

        </div>


        <div class="col-sm-2">

            <c:if test="${sessionScope.get('user').client}">  <%@include file="usr/newProposal.jsp"%> </c:if>

        </div>
    </div>
</div>

</body>
</html>