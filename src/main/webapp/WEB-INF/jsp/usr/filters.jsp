<%--@elvariable id="guests" type="java.lang.Integer"--%>
<%--@elvariable id="order" type="org.aleks4ay.hotel.model.Order"--%>
<form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/user?action=filter"
       style="background-color: rgba(96, 162, 218, 0.2);">

    <h3><fmt:message key="filters"/></h3>
    <%--<div class="mb-3">
        <label for="date3" class="form-label"> <fmt:message key="arrival"/>: </label>
        <input type="date" class="form-control" id="date3"  name="filter_arrival" value="${arrival}"/>
    </div>
    <div class="mb-3">
        <label for="date4" class="form-label"> <fmt:message key="departure"/>: </label>
        <input type="date" class="form-control" id="date4" name="filter_departure" value="${departure}"/>
    </div>--%>
    <div class="mb-3">
        <label for="field1" class="form-label"> <fmt:message key="guests"/> </label>

        <select id="field1" name="filter_guests" class="col-md-12 form-control">
            <option value="1" ${guests == 1 ? 'selected' : ''}> 1 </option>
            <option value="2" ${guests == 2 ? 'selected' : ''}> 2 </option>
            <option value="3" ${guests == 3 ? 'selected' : ''}> 3 </option>
            <option value="4" ${guests == 4 ? 'selected' : ''}> 4 </option>
        </select>
    </div>
    <div class="mb-3">
        <label for="field2" class="form-label"> <fmt:message key="category"/> </label>
        <select id="field2" name="filter_category" class="col-md-12 form-control">
            <c:forEach items="${categories}" var="item">
                <option value="${item}" ${category == item ? 'selected' : ''}> ${item} </option>
            </c:forEach>
        </select>
    </div>

    <div style="color: rgba(19, 23, 186, 0.9); font-size: 1.2em">
        <c:if test="${not empty category}" > <p> <fmt:message key="category"/>: ${category} </p> </c:if>
        <c:if test="${not empty guests}" > <p> <fmt:message key="guests"/>: ${guests} </p> </c:if>

        <button type="submit" class="btn btn-outline-success" name="filter" value="applyFilter">
            <fmt:message key="filterEnter"/>
        </button>
        <hr/>
        <button type="submit" class="btn btn-outline-success" name="filter" value="filterCansel">
            <fmt:message key="filterCansel"/>
        </button>
    </div>
<%--    <div>
        <input type="submit" name="Search2" value="${'Search_2'}"/>
        <input  type="submit" name="newProposal2" value="${'newProposal_2'}" />
    </div>--%>
</form>


