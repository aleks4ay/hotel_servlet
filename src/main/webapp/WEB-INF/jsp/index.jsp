<%@include file="fragments/headerPrefixAndLanguage.jsp"%>
<%@include file="fragments/head.jsp"%>

<body>

<%@include file="fragments/navbar.jsp"%>

<div class="container-fluid" style="margin-top:10px">
    <div class="row">
        <div class="col-sm-3">
            <h2> <fmt:message key="head_text_2"/> </h2>
            <h3> <fmt:message key="head_text_3"/> </h3>
        </div>

        <div class="col-sm-8">

            <img style="width: 100%" src="static/img/background.jpg" alt="background"/>

            <form  class="sticky rounded-lg shadow p-4 mb-2" method="post" action="/user?action=saveImage"
                   enctype="multipart/form-data"  style="background-color: rgba(96, 162, 218, 0.2);">
                <div class="custom-file">
                    <label class="custom-file-label" for="inputFile04" aria-describedby="inputAddon" id="lab1"></label>
                    <input type="file" name="image" class="custom-file-input" id="inputFile04" onchange="changeImage()"/> <%--value="${imgName}"--%>
                </div>

                <div >
                    <button type="submit" class="btn btn-outline-success" id="inputAddon">  <fmt:message key="save"/>  </button>
                </div>
            </form>

        </div>

    </div>
</div>

</body>

<script>
    var changeImage = function () { lab1.innerHTML = inputFile04.value.split('fakepath\\')[1]; };
</script>
</html>