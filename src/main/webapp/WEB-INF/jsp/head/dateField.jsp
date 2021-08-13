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

<%--

<p><input type="text" id="setArrival" name="setArrival" value="get Arrival: "/> </p>
<p><input type="text" id="setDeparture" name="setArrival" value="get Arrival: "/> </p>

<span id="output"></span>


<input type="button" onclick="tictactoe(this.id)" value="Button 1" id="buttonId1"/>
<input type="button" onclick="tictactoe('setArrival')" value="Button 2" id="buttonId2"/>
<input type="button" onclick="tictactoe('setDeparture')" value="Button 3" id="buttonId3"/>


<script type="text/javascript">
    tictactoe = function(id) {
        var box = document.getElementById(id);


        var from = document.getElementById('dateStart');
        box.value = from;
//        box.value = "X";
    }
</script>

<input type='text' name="rt" id='test' value='Hello'/>
<input type='text' name="rt" id='test2' value='Hello'/>
<input type='text' name="rt" id='test3' value='Hello'/>
<script type="text/javascript">
    getDate = function(id) {
        document.getElementById('test').value='Good By';

        var from = document.getElementById('dateStart');
        var text = document.getElementById('dateStart');
        document.getElementById('test2').value=from;
        document.getElementById('test3').value=text.getMonth();
    }
</script>

--%>
