<div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="number"/></th>
            <th scope="col"><fmt:message key="category"/></th>
            <th scope="col"><fmt:message key="guests"/></th>
            <th scope="col"><fmt:message key="description"/></th>
            <th scope="col"><fmt:message key="price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${rooms}" varStatus="position" begin="${10 * (pg - 1)}" end="${10 * (pg - 1) + 9}">
            <tr>
                <td scope="row">
                    <c:out value="${10 * (pg - 1) + position.count}" />
                </td>
                <td><c:out value="${room.number}" /></td>
                <td><c:out value="${room.category.title}" /></td>
                    <%--<td><c:out value="${room.roomStatus}" /></td>--%>
                <td><c:out value="${room.guests}" /></td>
                <td><c:out value="${room.description}" /></td>
                <td><c:out value="${room.price}" /></td>
                    <%--<td><c:out value="${room.dateReceiving}" /></td>--%>
                <td><a href='<c:url value="/order?action=select&id=${room.number}&pg=${pg}" />'><fmt:message key="act_1"/></a></td>
                <td><a href='<c:url value="/order?action=booking&id=${room.number}&pg=${pg}" />'><fmt:message key="act_2"/></a></td>
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
