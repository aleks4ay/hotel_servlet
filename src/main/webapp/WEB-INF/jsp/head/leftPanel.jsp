<div class="col-sm-2">
    <form class="sticky rounded-lg shadow p-4 mb-4"
          style="height:700px; height: 350px; background-color: rgba(96, 162, 218, 0.2);">
        <%--<p><fmt:message key="filters"/></p>--%>
        <c:if test="${not empty sessionScope.get('user')}">
            <h4> <fmt:message key="hello1"/> '${sessionScope.get('user').name}' (${sessionScope.get('user').role.title}) </h4>
        </c:if>
        <c:if test="${empty sessionScope.get('user')}">
            <h4> <fmt:message key="notEntered"/> </h4>
        </c:if>
    </form>
</div>
