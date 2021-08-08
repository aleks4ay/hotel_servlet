<div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="firstName"/></th>
            <th scope="col"><fmt:message key="lastName"/></th>
            <th scope="col"><fmt:message key="number"/></th>
            <th scope="col"><fmt:message key="category"/></th>
            <th scope="col"><fmt:message key="arrival"/></th>
            <th scope="col"><fmt:message key="departure"/></th>
            <th scope="col"><fmt:message key="guests"/></th>
            <th scope="col"><fmt:message key="description"/></th>
            <th scope="col"><fmt:message key="data_reg"/></th>
            <th scope="col"><fmt:message key="price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}" varStatus="position">
            <tr>
                <td scope="row">
                    <c:out value="${itemOnPage * (pg - 1) + position.count}" />
                </td>
                <td><c:out value="${order.user.name}" /></td>
                <td><c:out value="${order.user.surname}" /></td>
                <td><c:out value="${order.room.number}" /></td>
                <td><c:out value="${order.room.category.title}" /></td>
                <td><c:out value="${order.arrival}" /></td>
                <td><c:out value="${order.departure}" /></td>
                <td><c:out value="${order.room.guests}" /></td>
                <td><c:out value="${order.room.description}" /></td>
                <td><c:out value="${order.registered}" /></td>
                <td><c:out value="${order.correctPrice}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <ul class="pagination">
        <li class="page-item ${pg == 1 ? 'active' : ''}">
            <a class="page-link" href="/admin?action=${action}&pg=1">1</a>
        </li>
        <li class="page-item ${pg == 2 ? 'active' : ''}">
            <a class="page-link" href="/admin?action=${action}&pg=2">2</a>
        </li>
        <li class="page-item ${pg == 3 ? 'active' : ''}">
            <a class="page-link" href="/admin?action=${action}&pg=3">3</a>
        </li>
    </ul>
</div>
