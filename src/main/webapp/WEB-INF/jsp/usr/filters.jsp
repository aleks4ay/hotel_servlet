<%--@elvariable id="guests" type="java.lang.Integer"--%>
<form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/${man}?action=filter"
       style="background-color: rgba(96, 162, 218, 0.2);">

    <h3><fmt:message key="filters"/></h3>
    <div class="mb-3">
        <label for="date3" class="form-label"> <fmt:message key="arrival"/>: </label>
        <input type="date" class="form-control" id="date3"  name="filter_arrival" />
    </div>
    <div class="mb-3">
        <label for="date4" class="form-label"> <fmt:message key="departure"/>: </label>
        <input type="date" class="form-control" id="date4" name="filter_departure" />
    </div>
    <div class="mb-3">
        <label for="field1" class="form-label"> <fmt:message key="guests"/> </label>

        <select id="field1" name="filter_guests" class="col-md-12 form-control">
            <option value="1" ${empty guests or guests == 1 ? 'selected' : ''}> 1 </option>
            <option value="2" ${guests == 2 ? 'selected' : ''} > 2 </option>
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
    <div >
        category = ${category}
        guests = ${guests}
        <button type="submit" class="btn btn-outline-success" >  <fmt:message key="filterEnter"/>  </button>
    </div>
<%--    <div>
        <input type="submit" name="Search2" value="${'Search_2'}"/>
        <input  type="submit" name="newProposal2" value="${'newProposal_2'}" />
    </div>--%>
</form>


