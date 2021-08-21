<%@include file="fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="fragments/head.jsp"%>

<body>

<%@include file="fragments/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">

        <div class="col-sm-2">
            <c:if test="${(action == 'room') or (action == 'manageProposal')}">
                <%@include file="fragments/filters.jsp"%>
            </c:if>
        </div>

        <div class="col-sm-8">

            <c:if test="${(action == 'room') or (action == 'manageProposal')}">  <%@include file="mngr/m_rooms.jsp"%> </c:if>

            <c:if test="${action == 'order'}">  <%@include file="mngr/m_orders.jsp"%> </c:if>

        </div>


        <div class="col-sm-2">

        </div>
    </div>
</div>

</body>
</html>