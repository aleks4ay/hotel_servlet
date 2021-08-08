<c:set var="man" value="${empty sessionScope.get('user') ? 'guest' :
                        sessionScope.get('user').role.title == 'ADMIN' ? 'admin' :
                        sessionScope.get('user').role.title == 'MANAGER' ? 'manager' :
                        'user'}"/>
<ul class="pagination">
    <li class="page-item ${pg == 1 ? 'active' : ''}">
        <a class="page-link" href="/${man}?action=${action}&pg=1">1</a>
    </li>
    <li class="page-item ${pg == 2 ? 'active' : ''}">
        <a class="page-link" href="/${man}?action=${action}&pg=2">2</a>
    </li>
    <li class="page-item ${pg == 3 ? 'active' : ''}">
        <a class="page-link" href="/${man}?action=${action}&pg=3">3</a>
    </li>
</ul>