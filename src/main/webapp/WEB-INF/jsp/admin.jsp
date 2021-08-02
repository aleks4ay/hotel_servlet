<%@include file="head/headerPrefixAndLanguage.jsp"%>
<%@include file="head/head.jsp"%>

<body>

<%@include file="head/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">
        <div class="col-sm-2">
            <form class="sticky rounded-lg shadow p-4 mb-4"
            style="height:700px; height: 350px; background-color: rgba(96, 162, 218, 0.2);">
                <p><fmt:message key="filters"/></p>
                <c:if test="${not empty sessionScope.get('user')}">
                    <h2> admin: ${sessionScope.get('user').name} </h2>
                </c:if>
            </form>
        </div>

        <div class="col-sm-8">
            <div class="btn-group" style="width: 100%">
                <button type="button" class="btn btn-primary btn-my-r selected" onclick="window.location='/admin?action=user'">
                    <h5> <fmt:message key="adm_act_1"/> </h5>
                </button>
                <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/admin?action=room'">
                    <h5> <fmt:message key="adm_act_2"/> </h5>
                </button>
                <button type="button" class="btn btn-primary btn-my-r" onclick="window.location='/admin?action=order'">
                    <h5> <fmt:message key="adm_act_3"/> </h5>
                </button>
            </div>


            <c:if test="${action == 'user'}">  <%@include file="adm/users.jsp"%> </c:if>

            <c:if test="${action == 'room'}">  <%@include file="adm/rooms.jsp"%> </c:if>


        </div>


        <div class="col-sm-2">
            <form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/admin/newRoom"
                            style="background-color: rgba(96, 162, 218, 0.2);">
                <h3><fmt:message key="new_room"/></h3>
                <div class="mb-3">
                    <label for="field1" class="form-label"> <fmt:message key="number"/>:</label>
                    <input type="text" class="form-control" id="field1"  name="number" required/>
                </div>
                <div class="mb-3">
                    <label for="field2" class="form-label"> Room category </label>
                    <select id="field2" name="category" class="col-md-12 form-control" required>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category}"> ${category} </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label for="field3" class="form-label"> <fmt:message key="guests"/> </label>
                    <select id="field3" class="col-md-12 form-control" name="guests" >
                        <option value="1"> 1 </option>
                        <option value="2"> 2 </option>
                        <option value="3"> 3 </option>
                        <option value="4"> 4 </option>
                    </select>
                </div>

                <div class="mb-3">
                    <label for="field4" class="form-label"> <fmt:message key="description"/>: </label>
                    <input type="text" class="form-control" id="field4" name="description" required/>
                </div>
                <div class="mb-3">
                    <label for="field5" class="form-label"> <fmt:message key="price"/>: </label>
                    <input type="number" class="form-control" id="field5" name="price" required/>
                </div>


                <div >
                <c:if test="${sessionScope.get('user').admin}">
                    <button type="submit" class="btn btn-outline-success" >  <fmt:message key="save"/>  </button>
                </c:if>
<%--                <c:if test="${empty sessionScope.get('user')}">
                    <button type="submit" class="btn btn-outline-success" disabled >  <fmt:message key="cart_2"/>  </button>
                </c:if>--%>
                </div>

            </form>
        </div>
    </div>
</div>

<%--<div class="jumbotron text-center" style="margin-bottom:0">
  <p>Footer</p>
</div>--%>

</body>
</html>