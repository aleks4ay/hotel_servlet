<div>
    <h2> <fmt:message key="bill"/>  </h2>
    <form  class="sticky rounded-lg shadow p-4 mb-4" method="post" action="/user?action=changeBill"
           style="background-color: rgba(96, 162, 218, 0.2);">
        <h4> <fmt:message key="bill"/>: ${sessionScope.get('user').bill} <fmt:message key="currency"/> </h4>
        <div class="mb-3">
            <label for="bill1" class="form-label"> <fmt:message key="sum"/>: </label>
            <input type="number" class="form-control" id="bill1"  name="addBill" style="width: 40%"/>
        </div>
        <c:if test="${not empty errMess}">
            <h4 class="text-danger"> <fmt:message key="negativeMoney"/> </h4>
        </c:if>

        <div >
            <button type="submit" class="btn btn-outline-success" >  <fmt:message key="addBill"/>  </button>
        </div>
    </form>
</div>
