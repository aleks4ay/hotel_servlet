<form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/admin?action=newRoom"
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

    <div class="custom-file">
        <label class="custom-file-label" for="customFile" ></label>
        <input type="file" name="imgName" class="custom-file-input" id="customFile" value="${imgName}"/>
    </div>

    <div >
        <button type="submit" class="btn btn-outline-success" >  <fmt:message key="save"/>  </button>
    </div>

</form>