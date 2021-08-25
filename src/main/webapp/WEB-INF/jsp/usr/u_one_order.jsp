<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="../fragments/head.jsp"%>

<body>

<%@include file="../fragments/navbar.jsp"%>

<div class="container-fluid mt-3">
    <div class="row">

        <div class="col-sm-4">
            <%--@elvariable id="order" type="org.aleks4ay.hotel.model.Order"--%>
            <c:if test="${not empty order.room}">
                <img width="100%" src="/static/img/${order.room.imgName}"/>
            </c:if>
        </div>

        <div class="col-sm-4">

            <form method="post" action="/user?action=changeOrder">
                <h2> <fmt:message key="blank2"/>${order.id} </h2>

                <div>
                    <input type="hidden" name="id" value="${order.id}"/>

                    <h4><fmt:message key="statusOrd"/>: ${order.status}</h4>
                    <p><fmt:message key="data_reg"/>: ${order.registeredStr}</p>
                    <p><fmt:message key="arrival"/>: ${order.arrival}</p>
                    <p><fmt:message key="departure"/>: ${order.departure}</p>
                    <c:if test="${not empty order.room}">
                        <h5> <fmt:message key="roomNumber"/>: ${order.room.number} </h5>
                    </c:if>
                    <p><fmt:message key="category"/>: ${order.category.title}</p>
                    <p><fmt:message key="guestsQuantity"/>: ${order.guests}</p>
                    <c:if test="${not empty order.room}">
                        <p> <fmt:message key="description"/>: ${order.room.description} </p>
                        <p> <fmt:message key="price"/>: ${order.room.price} </p>
                    </c:if>
                    <h3> <fmt:message key="cost"/>: ${order.cost} <fmt:message key="currency"/> </h3>
                </div>

                <c:if test="${order.status.toString()=='BOOKED'}">
                    <div class="btn-group w-100">
                        <button type="submit" class="btn btn-primary btn-my-r" name="changeStatus" value="confirm">
                            <fmt:message key="act_5"/>
                        </button>
                    </div>
                </c:if>
            </form>
            <hr>

            <button type="button" class="btn btn-primary btn-my-r w-100"
                    onclick="window.location='/user?action=account&ap=order'">
                <fmt:message key="returnToOrder"/>
            </button>
        </div>


        <div class="col-sm-4">
            <div class="rounded-lg shadow p-4 mb-4">
                <h3> <fmt:message key="bill2"/>: ${bill} <fmt:message key="currency"/> </h3>
            </div>
            <c:if test="${not empty order.invoice}">
                <form method="post" class="rounded-lg shadow p-4 mb-4" action="/user?action=changeOrder">
                    <h2> <fmt:message key="invoice"/> <fmt:message key="n"/>${order.invoice.id} </h2>
                    <c:if test="${not empty noMoneyMessage}">
                        <h3 class="text-danger"> <fmt:message key="noMoneyMessage"/> </h3>
                    </c:if>
                    <div>
                        <input type="hidden" name="id" value="${order.id}"/>

                        <h5><fmt:message key="data_reg2"/>: ${order.invoice.registeredStr}</h5>
                        <c:if test="${order.invoice.status.toString()=='NEW'}">
                            <h5 style="color: #3b6696;">
                                <fmt:message key="data_reg_end"/>: ${order.invoice.endDateStr}
                            </h5>
                        </c:if>
                        <c:if test="${order.invoice.status.toString()=='PAID'}">
                            <div class="rotate1 paid">
                                <h3 class="paid-item" ><fmt:message key="paid"/></h3>
                            </div>
                        </c:if>
                        <c:if test="${order.invoice.status.toString()=='CANCEL'}">
                            <div class="rotate1 oldPaid">
                                <h3 class="paid-item" ><fmt:message key="oldPaid"/></h3>
                            </div>
                        </c:if>
                        <h3><fmt:message key="cost"/>: ${order.invoice.cost} <fmt:message key="currency"/> </h3>
                    </div>

                    <c:if test="${empty noMoneyMessage and order.status.toString()=='CONFIRMED'}">
                        <button type="submit" class="btn btn-primary btn-my-r w-100" name="changeStatus" value="pay">
                            <fmt:message key="act_6"/>
                        </button>
                    </c:if>
                    <c:if test="${not empty noMoneyMessage}">
                        <button type="button" class="btn btn-primary btn-my-r w-100"
                                onclick="window.location='/user?action=account&ap=bill'">
                            <fmt:message key="addBill"/>
                        </button>
                    </c:if>
                </form>
            </c:if>
        </div>
    </div>
</div>

</body>
</html>



