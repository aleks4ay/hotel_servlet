<c:set var="man" value="${empty sessionScope.get('user') ? 'guest' :
                        sessionScope.get('user').role.title == 'ADMIN' ? 'admin' :
                        sessionScope.get('user').role.title == 'MANAGER' ? 'manager' :
                        'user'}"/>