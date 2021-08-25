<div>
    <h2> <fmt:message key="adm_act_2"/>  </h2>

    <c:if test="${action=='room'}">
        <%@include file="../fragments/sorting.jsp"%>
    </c:if>

    <c:if test="${empty rooms and action=='manageProposal'}">
        <h4 class="text-danger"> <fmt:message key="errorSearch"/> </h4>
    </c:if>

    <c:if test="${not empty rooms}">
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
            <c:forEach var="room" items="${rooms}" varStatus="position" >
                <tr>
                    <td> ${itemOnPage * (pg - 1) + position.count} </td>
                    <td> ${room.number} </td>
                    <td> ${room.category.title} </td>
                    <td> ${room.guests} </td>
                    <td> ${room.description} </td>
                    <td> ${room.price} </td>
                    <td> <img width="200px" src="/static/img/${room.imgName}"/> </td>

                    <c:if test="${(not empty ordId) and isNew}">
                        <td> <a href="/manager?action=newOrder&roomId=${room.id}&ordId=${ordId}">
                            <fmt:message key="act_2"/> </a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${action == 'room'}">
        <%@include file="../fragments/pagination.jsp"%>
    </c:if>

</div>
