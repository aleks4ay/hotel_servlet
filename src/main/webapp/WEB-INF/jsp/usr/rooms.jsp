<div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="number"/></th>
            <th scope="col"><fmt:message key="category"/></th>
            <%--<th scope="col"><fmt:message key="roomStatus"/></th>--%>
            <th scope="col"><fmt:message key="guests"/></th>
            <th scope="col"><fmt:message key="description"/></th>
            <th scope="col"><fmt:message key="price"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${rooms}" varStatus="position" >
            <tr>
                <td scope="row">
                    <c:out value="${itemOnPage * (pg - 1) + position.count}" />
                </td>
                <td><c:out value="${room.number}" /></td>
                <td><c:out value="${room.category.title}" /></td>
                <td><c:out value="${room.guests}" /></td>
                <td><c:out value="${room.description}" /></td>
                <td><c:out value="${room.price}" /></td>
                <c:if test="${not empty sessionScope.get('user')}">
                    <td><a href='<c:url value="/user?action=booking&id=${room.number}&pg=${pg}" />'><fmt:message key="act_2"/></a></td>
                </c:if>
                <c:if test="${empty sessionScope.get('user')}">
                    <td> <fmt:message key="act_2"/> </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <c:if test="${empty sessionScope.get('user')}">  <h3> <fmt:message key="book_info1"/> </h3>  </c:if>

    <ul class="pagination">
        <li class="page-item ${pg == 1 ? 'active' : ''}">
            <a class="page-link" href="/user?action=${action}&pg=1">1</a>
        </li>
        <li class="page-item ${pg == 2 ? 'active' : ''}">
            <a class="page-link" href="/user?action=${action}&pg=2">2</a>
        </li>
        <li class="page-item ${pg == 3 ? 'active' : ''}">
            <a class="page-link" href="/user?action=${action}&pg=3">3</a>
        </li>
    </ul>

    <%--<ul class="pagination">
        <li class="page-item ${pg == 1 ? 'active' : ''}"><a class="page-link" href="/booking?pg=1">1</a></li>
        <li class="page-item ${pg == 2 ? 'active' : ''}"><a class="page-link" href="/booking?pg=2">2</a></li>
        <li class="page-item ${pg == 3 ? 'active' : ''}"><a class="page-link" href="/booking?pg=3">3</a></li>
    </ul>--%>
</div>
