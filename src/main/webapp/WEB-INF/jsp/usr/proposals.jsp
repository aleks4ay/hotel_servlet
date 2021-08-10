<div>
    <h2> <fmt:message key="proposal"/>  </h2>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="firstName"/></th>
            <th scope="col"><fmt:message key="lastName"/></th>

            <th scope="col"><fmt:message key="arrival"/></th>
            <th scope="col"><fmt:message key="departure"/></th>
            <th scope="col"><fmt:message key="guests"/></th>
            <th scope="col"><fmt:message key="category"/></th>
            <th scope="col"><fmt:message key="data_reg"/></th>
            <th scope="col"><fmt:message key="statusProp"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="proposal" items="${proposals}" varStatus="position">
            <tr>
                <td scope="row">
                    <c:out value="${itemOnPage * (pg - 1) + position.count}" />
                </td>
                <td><c:out value="${proposal.user.name}" /></td>
                <td><c:out value="${proposal.user.surname}" /></td>

                <td><c:out value="${proposal.arrival}" /></td>
                <td><c:out value="${proposal.departure}" /></td>
                <td><c:out value="${proposal.guests}" /></td>
                <td><c:out value="${proposal.category.title}" /></td>
                <td><c:out value="${proposal.registered}" /></td>
                <td><c:out value="${proposal.status.title}" /></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%--<%@include file="../head/pagination.jsp"%>--%>

</div>
