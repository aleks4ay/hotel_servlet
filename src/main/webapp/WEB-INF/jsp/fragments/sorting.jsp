<%--@elvariable id="sortMethod" type="java.lang.String"--%>
<c:set var="command1" value="${userType == 'guest' ? 'user' : userType}"/>
<form method="post" action="/${command1}?action=room" >
    <div class="btn-group">
        <label for="field11" class="form-label date-item"> <fmt:message key="sort"/>: </label>
        <select id="field11" name="sortMethod" value="${sortMethod}" class="form-control date-item" style="width: 20em" required>
            <option value="number" ${sortMethod == 'number' ? 'selected' : ''}>     <fmt:message key="byRoomNumber"/> </option>
            <option value="price" ${sortMethod == 'price' ? 'selected' : ''}>       <fmt:message key="byPrice"/> </option>
            <option value="guests" ${sortMethod == 'guests' ? 'selected' : ''}>     <fmt:message key="byGuests"/> </option>
            <option value="category" ${sortMethod == 'category' ? 'selected' : ''}> <fmt:message key="byCategory"/> </option>
        </select>
        <button type="submit" class="btn btn-outline-success date-item"> <fmt:message key="doSort"/> </button>
    </div>
</form>
