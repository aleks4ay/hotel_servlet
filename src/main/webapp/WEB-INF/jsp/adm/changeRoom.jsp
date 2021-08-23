<form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/admin?action=saveChangedRoom"
       style="background-color: rgba(96, 162, 218, 0.2);">
    <h3><fmt:message key="new_room"/></h3>
    <div class="mb-3">
        <input type="hidden" name="id" value="${room.id}"/>
        <input type="hidden" name="pg" value="${pg}"/>
        <input type="hidden" name="imgName" value="${room.imgName}"/>
        <label for="field1" class="form-label"> <fmt:message key="number"/>:</label>
        <input type="number" name="number" value="${room.number}" class="form-control" id="field1" />



        <label for="field2" class="form-label">  <fmt:message key="category"/> </label>
        <select id="field2" name="category" class="col-md-12 form-control" required>
            <c:forEach items="${categories}" var="category">
                <option value="${category}" ${category == room.category ? 'selected' : ''}> ${category} </option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label for="field3" class="form-label"> <fmt:message key="guests"/> </label>
        <select id="field3" class="col-md-12 form-control" name="guests" >
            <option value="1" ${1 == room.guests ? 'selected' : ''}> 1 </option>
            <option value="2" ${2 == room.guests ? 'selected' : ''}> 2 </option>
            <option value="3" ${3 == room.guests ? 'selected' : ''}> 3 </option>
            <option value="4" ${4 == room.guests ? 'selected' : ''}> 4 </option>
        </select>
    </div>

    <div class="mb-3">
        <label for="field4" class="form-label"> <fmt:message key="description"/>: </label>
        <input type="text" class="form-control" id="field4" name="description" required value="${room.description}"/>
    </div>
    <div class="mb-3">
        <label for="field5" class="form-label"> <fmt:message key="price"/>: </label>
        <input type="number" class="form-control" id="field5" name="price" required value="${room.price}"/>
    </div>

    <div class="custom-file">
        <label class="custom-file-label" for="customFile" ></label>
        <input type="file" name="imgName2" class="custom-file-input" id="customFile" value="${imgName}"/>
    </div>

    <div >
        <button type="submit" class="btn btn-outline-success" >  <fmt:message key="save"/>  </button>
    </div>
    <button type="button" class="btn btn-outline-success" onClick="window.location='/admin?action=room&pg=${pg}'">
        <h5> <fmt:message key="cancel"/> </h5>
    </button>

</form>