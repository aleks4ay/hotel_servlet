<%--@elvariable id="order" type="org.aleks4ay.hotel.model.Order"--%>
<%@include file="../fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="../fragments/head.jsp"%>

<body>

<%@include file="../fragments/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">

        <div class="col-sm-3">
            <img style="width: 100%" src="/static/img/${orderDto.imgName}"/> </td>
        </div>

        <div class="col-sm-6">
            <form method="post" action="/user?action=newOrder&id=${roomId}">
                <h2> <fmt:message key="blank"/>: </h2>
                <c:if test="${roomOccupiedMessage != null}">
                    <h3 class="text-danger"> <fmt:message key="roomOccupiedMessage"/> </h3>
                    <button type="button" class="btn btn-outline-success" onclick="window.location='/user?action=room'">
                        <h5> <fmt:message key="anotherRoom"/> </h5>
                    </button>
                </c:if>

                <div>
                    <div class="mb-3">
                        <label for="dateStart" class="form-label"> <fmt:message key="arrival"/>: </label>
                        <input type="date" class="form-control" id="dateStart"  name="arrival" value="${orderDto.arrival}"
                               onchange="getCost()"/>
                    </div>
                    <div class="mb-3">
                        <label for="dateEnd" class="form-label"> <fmt:message key="departure"/>: </label>
                        <input type="date" class="form-control" id="dateEnd" name="departure" value="${orderDto.departure}"
                               onchange="getCost()"/>
                        <input type="hidden" id="price1" value="${orderDto.correctPrice}">
                    </div>
                    <input type="hidden" value="${orderDto.number}" name="number">

                    <p><fmt:message key="roomNumber"/>: ${orderDto.number}</p>
                    <p><fmt:message key="category"/>: ${orderDto.category}</p>
                    <p><fmt:message key="guestsQuantity"/>: ${orderDto.guests}</p>
                    <p><fmt:message key="description"/>: ${orderDto.description}</p>
                    <h3> <fmt:message key="cost"/>: <span id="cost1">${orderDto.cost}</span>  <fmt:message key="currency"/> </h3>
                </div>

                <div class="btn-group" style="width: 100%">
                    <input type="submit" class="btn btn-primary btn-my-r" value="<fmt:message key="act_3"/>"> </input>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
<script>
    var getCost = function () {
        var parts1 = dateStart.value.split('-');
        var price = price1.value;
        var start = new Date(parts1[0], parts1[1] - 1, parts1[2]);
        var parts2 = dateEnd.value.split('-');
        var end = new Date(parts2[0], parts2[1] - 1, parts2[2]);
        cost1.innerHTML = dateStart.value == '' | dateEnd.value == '' ? '' : (end - start) / 86400000 * price + '.0';
    };
</script>
</html>


