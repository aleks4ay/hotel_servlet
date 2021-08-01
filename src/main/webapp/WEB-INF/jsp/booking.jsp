<%@include file="head/headerPrefixAndLanguage.jsp"%>
<%@include file="head/head.jsp"%>

<body>

<%--<%@include file="head/headerImg.jsp"%>--%>
<%@include file="head/navbar.jsp"%>


<div class="container-fluid" style="margin-top:10px">
    <div class="row">
        <div class="col-sm-3">

        </div>

        <div class="col-sm-6">
            <h2> <fmt:message key="blank"/>: </h2>
            <div>
                <p> <fmt:message key="name"/>: ${order.user.name} </p>
                <p> <fmt:message key="surname"/>: ${order.user.surname} </p>
                <p> <fmt:message key="arrival"/>: ${order.arrival} </p>
                <p> <fmt:message key="departure"/>: ${order.departure} </p>
                <p> <fmt:message key="period"/>: ${order.period} </p>
                <p> <fmt:message key="roomNumber"/>: ${order.room.number} </p>
                <p> <fmt:message key="category"/>: ${order.room.category} </p>
                <p> <fmt:message key="guestsQuantity"/>: ${order.room.guests} </p>
                <p> <fmt:message key="description"/>: ${order.room.description} </p>
                <p> <h3> <fmt:message key="cost"/>: ${order.cost} &nbsp; <fmt:message key="currency"/> </h3> </p>
            </div>
            <div class="btn-group" style="width: 100%">
                <button type="button" class="btn btn-primary btn-my-r" onclick="'#'"><h5><fmt:message key="act_3"/></h5></button>
            </div>
        </div>

<%--        <div class="col-sm-3">
            <form class="sticky rounded-lg shadow p-4 mb-4" style="height: 250px; background-color: rgba(96, 162, 218, 0.2);">
                <p><fmt:message key="cart_1"/></p>
                <p><c:out value="${not empty id ? id : '-'}"></c:out>

            </form>
        </div>--%>
    </div>
</div>

</body>
</html>
