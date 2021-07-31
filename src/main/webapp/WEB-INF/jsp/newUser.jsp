<%@include file="head/headerPrefixAndLanguage.jsp"%>

<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/static/css/logo.css">
    <style>
        .hello_1 {
            height: 1200px;
            /*background-image: url(/static/img/background_1.jpg);*/
            /*background-size: cover;*/
            width: 720px;
            background-size: 100%;
            background-position: center;
            background-repeat: no-repeat;
        }
        .center2 {
            width: 720px;
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            margin-right: -50%;
            transform: translate(-50%, -50%)
        }
    </style>
</head>
<body>
    <div class="center1">
        <form class="inner_logo" method="post" action="/registration">
            <div class="mb-3">
                <label for="login1" class="form-label"> Login </label>
                <input type="text" class="form-control" id="login1"  name="loginNew">
            </div>
            <div class="mb-3">
                <label for="name1" class="form-label">
                    <fmt:message key="firstName"/>
                </label>
                <input type="text" class="form-control" id="name1"  name="firstName">
            </div>
            <div class="mb-3">
                <label for="name2" class="form-label">
                    <fmt:message key="lastName"/>
                </label>
                <input type="text" class="form-control" id="name2"  name="lastName">
            </div>
    <%--        <div class="mb-3">
                <label for="exampleInputEmail1" class="form-label">Email address</label>
                <input type="email" class="form-control" id="exampleInputEmail1" >
            </div>--%>
            <div class="mb-3">
                <label for="pass" class="form-label">
                    <fmt:message key="password"/>
                </label>
                <input type="password" class="form-control" id="pass" name="log_pass">
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="exampleCheck1" name="checkLogin">
                <label class="form-check-label" for="exampleCheck1">
                    <fmt:message key="loginNow"/>
                </label>
            </div>
            <button type="submit" class="btn btn-outline-success">
                <fmt:message key="register"/>
            </button>
            <button type="button" class="btn btn-outline-danger" onclick="window.history.back()">
                <fmt:message key="cancel"/>
            </button>
        </form>
    </div>
</body>
</html>