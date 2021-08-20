<%--@elvariable id="userType" type="java.lang.String"--%>
<%--@elvariable id="action" type="java.lang.String"--%>
<%--@elvariable id="pg" type="java.lang.Integer"--%>
<c:set var="command" value="${userType == 'guest' ? 'user' : userType}"/>
<ul class="pagination">
    <li class="page-item ${pg==1 ? 'disabled' : ''}">
        <a class="page-link" href="/${command}?action=${action}&pg=${pg>1?pg-1:1}"> < </a>
    </li>
    <li class="page-item active">
        <p class="page-link"> ${pg} </p>
    </li>
    <li class="page-item">
        <a class="page-link" href="/${command}?action=${action}&pg=${pg+1}"> > </a>
    </li>
</ul>