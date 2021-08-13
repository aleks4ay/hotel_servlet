<%--@elvariable id="order" type="org.aleks4ay.hotel.model.Order"--%>

<div class="col-sm-6">
    <form method="post" action="/user?action=newOrder&id=${order.room.id}">
        <h2> <fmt:message key="blank"/>: </h2>
        <input type="hidden" name="id" value="${order.room.id}"/>
        <div>
            <div class="mb-3">
                <label for="date3" class="form-label"> <fmt:message key="arrival"/>: </label>
                <input type="date" class="form-control" id="date3"  name="date_arrival" value="${arrival}"/>
            </div>
            <div class="mb-3">
                <label for="date4" class="form-label"> <fmt:message key="departure"/>: </label>
                <input type="date" class="form-control" id="date4" name="date_departure"  value="${departure}"/>
            </div>
            <p> <fmt:message key="name"/>: ${order.user.name} </p>
            <input type="hidden" value="${order.user.name}">

            <p> <fmt:message key="surname"/>: ${order.user.surname} </p>
            <input type="hidden" value="${order.user.surname}">

            <p> <fmt:message key="roomNumber"/>: ${order.room.number} </p>
            <input type="hidden" value="${order.room.number}">

            <p> <fmt:message key="category"/>: ${order.room.category} </p>
            <input type="hidden" value="${order.room.category}">

            <p> <fmt:message key="guestsQuantity"/>: ${order.room.guests} </p>
            <input type="hidden" value="${order.room.guests}">

            <p> <fmt:message key="description"/>: ${order.room.description} </p>
            <input type="hidden" value="${order.room.description}">

            <input type="hidden" value="${order.room.price}">
            <p> <h3> <fmt:message key="cost"/>: ${order.room.price} &nbsp; <fmt:message key="currency"/> </h3> </p>
        </div>
        <div class="btn-group" style="width: 100%">
            <input type="submit" class="btn btn-primary btn-my-r" value="<fmt:message key="act_3"/>"> </input>
        </div>
    </form>
</div>
