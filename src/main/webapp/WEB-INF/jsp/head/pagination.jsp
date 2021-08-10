<%--@elvariable id="userType" type="java.lang.String"--%>
<%--@elvariable id="action" type="java.lang.String"--%>
<%--@elvariable id="pg" type="java.lang.Integer"--%>
<ul class="pagination">
    <li class="page-item ${pg == 1 ? 'active' : ''}">
        <a class="page-link" href="/${userType}?action=${action}&pg=1">1</a>
    </li>
    <li class="page-item ${pg == 2 ? 'active' : ''}">
        <a class="page-link" href="/${userType}?action=${action}&pg=2">2</a>
    </li>
    <li class="page-item ${pg == 3 ? 'active' : ''}">
        <a class="page-link" href="/${userType}?action=${action}&pg=3">3</a>
    </li>
</ul>