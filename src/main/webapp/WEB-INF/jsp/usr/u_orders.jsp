<div>
    <h2> <fmt:message key="adm_act_3"/>  </h2>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="roomNumber"/></th>
            <th scope="col"><fmt:message key="arrival"/></th>
            <th scope="col"><fmt:message key="departure"/></th>
            <th scope="col"><fmt:message key="statusOrd"/></th>
            <th scope="col"><fmt:message key="cost_2"/></th>
            <th scope="col"><fmt:message key="photo"/></th>
            <th scope="col"><fmt:message key="detail"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}" varStatus="position">
            <tr style="${order.status.toString()=='CANCEL' ? 'color: #d54d38' : ''}">
                <td scope="row">
                    <input type="hidden" value="${order.id}"/>
                    ${order.id}
                </td>
                <td> <c:if test="${not empty order.room}"> ${order.room.number} </c:if></td>
                <td> ${order.arrival} </td>
                <td> ${order.departure} </td>
                <td> <b style="${order.status.toString()=='CANCEL' ? 'color: #d54d38' : 'color: #3b6696'}"> ${order.status} </b> </td>
                <td> ${order.cost} </td>
                <td>
                    <c:if test="${not empty order.room}">
                        <img width="100px" src="/static/img/${order.room.imgName}"/>
                    </c:if>
                </td>
                <td> <a  href="/user?action=account&ap=oneOrder&id=${order.id}"> <fmt:message key="detail"/> </a> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
