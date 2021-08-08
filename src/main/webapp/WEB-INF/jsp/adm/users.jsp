<div>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"> <fmt:message key="n"/> </th>
            <th scope="col"> Login </th>
            <th scope="col"> <fmt:message key="firstName"/> </th>
            <th scope="col"><fmt:message key="lastName"/></th>
            <th scope="col"> Active </th>
            <th scope="col"><fmt:message key="data_reg"/></th>
            <th scope="col"> Role </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}" varStatus="position" >
            <tr>
                <td scope="row">
                    <c:out value="${itemOnPage * (pg - 1) + position.count}" />
                </td>
                <td><c:out value="${user.login}" /></td>
                <td><c:out value="${user.name}" /></td>
                <td><c:out value="${user.surname}" /></td>
                <td><c:out value="${user.active}" /></td>
                <td><c:out value="${user.registered}" /></td>
                <td><c:out value="${user.role.title}" /></td>
                <%--<td><a href='<c:url value="/order?action=select&id=${room.number}&pg=${pg}" />'><fmt:message key="act_1"/></a></td>--%>
                <%--<td><a href='<c:url value="/order?action=booking&id=${room.number}&pg=${pg}" />'><fmt:message key="act_2"/></a></td>--%>
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
