<div>
    <h2> <fmt:message key="adm_act_2"/>  </h2>
    <table class="table">
        <thead class="thead-light">
        <tr>
            <th scope="col"><fmt:message key="n"/></th>
            <th scope="col"><fmt:message key="number"/></th>
            <th scope="col"><fmt:message key="category"/></th>
            <th scope="col"><fmt:message key="guests"/></th>
            <th scope="col"><fmt:message key="description"/></th>
            <th scope="col"><fmt:message key="price"/></th>
            <th scope="col"><fmt:message key="photo"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="room" items="${rooms}" varStatus="position">
            <tr>
                <td> ${itemOnPage * (pg - 1) + position.count} </td>
                <td> ${room.number} </td>
                <td> ${room.category.title} </td>
                <td> ${room.guests} </td>
                <td> ${room.description} </td>
                <td> ${room.price}" /></td>
                <td> <img width="200px" src="/static/img/${room.imgName}"/> </td>

                <td>
                    <a href="/admin?action=changeRoom&id=${room.id}&pg=${pg}"> <fmt:message key="change"/> </a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <%@include file="../fragments/pagination.jsp"%>

</div>
