<div>
    <h2> <fmt:message key="adm_act_1"/>  </h2>
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
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%@include file="../fragments/pagination.jsp"%>

</div>
