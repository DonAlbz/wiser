<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/main.css">
        <link rel="stylesheet" href="css/sara_css.css">


        <title>Login</title>
    </head>
    <body onload="controllo()">
        <fmt:setLocale value="en"/>
        <fmt:setBundle basename="indexBundle"/>   
        <nav class="navbar navbar-inverse">
            <div class="container-fluid" id="container-nav">
                <div class="row">      
                    <div class="navbar-header col-xs-12 col-xs-offset-4 col-sm-offset-0 col-lg-2 col-md-2 col-sm-2">
                        <a class="navbar-brand" href="ActionServlet?op=getList"><fmt:message key="titleNavbar"/></a> 

                    </div>

                    <div class="navbar-header col-xs-12 col-sm-8 col-sm-offset-1  col-lg-8 col-md-8">



                    </div>


                </div>

            </div>

        </div>
    </div>
</nav> 
<div class="container">
    <div class="row">
        <br>
        <div class="col-lg-3 col-md-2 col-sm-2">

        </div>
        <div class="col-lg-6 col-md-8 col-sm-8">

            <form class="form-horizontal form-login" action='ActionServlet' method="post" id="centra">
                <fieldset>
                    <div id="legend"  class="title text-center">
                        <h2 class=""><fmt:message key="loginTitle"/></h2>
                    </div>
                    <div class="form-group has-feedback">
                        <!-- Username -->
                        <label class="control-label"  for="username"><fmt:message key="usernameInput"/></label>
                        <div class="controls" id="usernameDiv">
                            <input type="text" id="username" name="username" placeholder="" class="form-control" value="${nomeInserito}">
                            <p class="help-block erroreUsr"></p>
                        </div>
                    </div>

                    <div class="form-group">
                        <!-- Password-->
                        <label class="control-label" for="password"><fmt:message key="passwordInput"/></label>
                        <div class="controls" id="passwordDiv">
                            <input type="password" id="password" name="password" placeholder="" class="form-control" value="${pwdInserita}">
                            <p class="help-block errorePwd"></p>
                            <p class="help-block erroreLogin hidden">${error}</p>
                            <input name="op" value="login" hidden="hidden">
                        </div>

                        <!--<span class="error" style="color:red;text-align: center;"><b></b><br></span>-!
                    </div>


                    <br>
                    <div class="text-center">
                        <!-- Button -->
                        <div class="controls">
                            <br><br>
                            <button class="btn btn-success center-block"><fmt:message key="loginButton"/></button>
                        </div>
                    </div>

                    <div class="clearfix"></div>
                    <br>
                    <br>
                    <div class="bottom text-center">
                        <div id="divAccount">
                            <span>
                                <fmt:message key="accountTitle"/> <!--Padding is optional-->
                            </span>
                        </div>
                        <br>

                        <a class="text-success" href="registrazione.jsp"><h4><fmt:message key="accountLink"/></h4></a>
                    </div>

                </fieldset>
            </form>


        </div>
    </div>
</div>
<script type="text/javascript">

    function controllo() {
        $("#username").focus();
        var msgerror = $(".erroreLogin").html();
        if (msgerror !== "") {
            switch (msgerror) {
                case "noUsr":
                    $("#usernameDiv").addClass("has-error");
                    $(".erroreUsr").html("<fmt:message key="enterUsrMessage"/>");
                    $("#username").focus();
                    break;
                case "noPwd":
                    $("#passwordDiv").addClass("has-error");
                    $(".errorePwd").html("<fmt:message key="enterPwdMessage"/>");
                    $("#password").focus();
                    break;
                case "noUsrPwd":
                    $(".form-login").addClass("has-error");
                    $(".erroreUsr").html("<fmt:message key="enterUsrMessage"/>");
                    $(".errorePwd").html("<fmt:message key="enterPwdMessage"/>");
                    break;
                case "errorUsrPwd":
                    $(".form-login").addClass("has-error");
                    $(".errorePwd").html("<fmt:message key="loginFailedMessage"/>");
                    break;
                case "noAccount":
                    $(".errorePwd").html("<fmt:message key="createAccounMessage"/>");
                    $(".form-login").addClass("has-error");
                    break;
            }

        }

    }
</script>
<script type="text/javascript" src="./js/jquery.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<script type="text/javascript" src="./js/Alb.js"></script> 
<script type="text/javascript" src="./js/main.js"></script>
</body>

</html>

