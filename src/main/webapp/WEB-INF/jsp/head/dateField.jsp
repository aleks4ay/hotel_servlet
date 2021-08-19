<form method="post" action="/user?action=room" >
    <div class="mb-3">
        <label for="dateStart" class="form-label"> <fmt:message key="arrival"/>: </label>
        <input type="date" class="form-control" id="dateStart"  name="arrival" value="${arrival}" />
    </div>
    <div class="mb-3">
        <label for="dateEnd" class="form-label"> <fmt:message key="departure"/>: </label>
        <input type="date" class="form-control" id="dateEnd" name="departure" value="${departure}" />
    </div>
    <div style="color: rgba(19, 23, 186, 0.9); font-size: 1.2em">
        <button type="submit" class="btn btn-outline-success" >  <fmt:message key="dateEnter"/>  </button>
    </div>
</form>
