<%@include file="head/headerPrefixAndLanguage.jsp"%>
<%@include file="head/head.jsp"%>

<body>

<%@include file="head/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">

        <div class="col-sm-2">
            <c:if test="${(action == 'room') or (action == 'filter')}">  <%@include file="usr/filters.jsp"%> </c:if>

            <c:if test="${(sessionScope.get('user').client) and (action != 'room') and (action != 'filter')}">
                <div>  <h1> <fmt:message key="account"/>  </h1> </div>
            </c:if>

        </div>

        <div class="col-sm-8">

            <c:if test="${(sessionScope.get('user').client) and (action != 'room')}">


                <div class="btn-group" style="width: 100%">
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=account&ap=order'">
                        <h5> <fmt:message key="adm_act_3"/> </h5>
                    </button>
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=account&ap=proposal'">
                        <h5> <fmt:message key="proposal"/> </h5>
                    </button>
                    <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/user?action=account&ap=bill'">
                        <h5> <fmt:message key="bill"/> </h5>
                    </button>
                </div>
            </c:if>

            <c:if test="${(action == 'room') or (action == 'filter')}">  <%@include file="usr/rooms.jsp"%> </c:if>

            <c:if test="${ap == 'order'}">  <%@include file="usr/orders.jsp"%> </c:if>

            <c:if test="${ap == 'proposal'}">  <%@include file="usr/proposals.jsp"%> </c:if>

            <c:if test="${ap == 'bill'}">  <%@include file="usr/bill.jsp"%> </c:if>

            <c:if test="${action == 'booking'}">  <%@include file="usr/bookingBlank.jsp"%> </c:if>

        </div>


        <div class="col-sm-2">

            <c:if test="${(fn:contains(action, 'room')) and (sessionScope.get('user').client)}">
                <%@include file="usr/newProposal.jsp"%>
            </c:if>

        </div>
    </div>
</div>

</body>
</html>