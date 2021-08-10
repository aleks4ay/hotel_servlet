<form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/user?action=newProposal"
       style="background-color: rgba(96, 162, 218, 0.2);">
    <%--<h3><fmt:message key="cart_1"/></h3>--%>
    <div class="mb-3">
        <label for="date3" class="form-label"> <fmt:message key="arrival"/>: </label>
        <input type="date" class="form-control" id="date3"  name="arrival2" />
    </div>
    <div class="mb-3">
        <label for="date4" class="form-label"> <fmt:message key="departure"/>: </label>
        <input type="date" class="form-control" id="date4" name="departure2" />
    </div>
    <div class="mb-3">
        <label for="field1" class="form-label"> <fmt:message key="guests"/> </label>
        <select id="field1" name="field1" class="col-md-12 form-control">
            <option value="1"> 1 </option>
            <option value="2"> 2 </option>
            <option value="3"> 3 </option>
            <option value="4"> 4 </option>
        </select>
    </div>
    <div class="mb-3">
        <label for="field2" class="form-label"> <fmt:message key="category"/> </label>
        <select id="field2" name="field2" class="col-md-12 form-control">
            <c:forEach items="${categories}" var="category">
                <option value="${category}"> ${category} </option>
            </c:forEach>
        </select>
    </div>
    <div >
        <c:if test="${not empty sessionScope.get('user')}">
            <button type="submit" class="btn btn-outline-success" >  <fmt:message key="cart_2"/>  </button>
        </c:if>
        <c:if test="${empty sessionScope.get('user')}">
            <button type="submit" class="btn btn-outline-success" disabled >  <fmt:message key="cart_2"/>  </button>
        </c:if>
    </div>
    <div>
        <input type="submit" name="Search2" value="${'Search_2'}"/>
        <input  type="submit" name="newProposal2" value="${'newProposal_2'}" />
    </div>
</form>
<%--

<form action="/user?action=new" method="post">

    <input type="submit" name="newOrder" value="Order" />
    <input type="submit" name="newProposal" value="Proposal" />

</form>--%>
