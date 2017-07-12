<%-- 
    Document   : registrazione
    Created on : 20-giu-2017, 20.16.11
    Author     : Sara
--%>

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


        <title>Create account</title>
    </head>
    <body>
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
        <div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">                    
        </div>
        <div class="col-lg-6 col-md-6 col-sm-6 col-xs-8">

            <form class="form-horizontal" action='ActionServlet' method="post" id="centra">
                <fieldset>
                    <div id="legend" class="title text-center">
                        <h2><fmt:message key="createAccountTitle"/></h2> 
                    </div>
                    <div id="form-user"class="form-group has-feedback">
                        <!-- Username -->
                        <label class="control-label"  for="username"><fmt:message key="usernameInput"/></label>
                        <div class="controls">
                            <input type="text" id="username" name="username" placeholder="" class="form-control" onkeyup="confrontaPassword()">
                            <p id="user-text" class="help-block"><fmt:message key="inputUsernameMessage"/></p>
                        </div>
                    </div>

                    <div id="form-pwd" class="form-group has-feedback">
                        <!-- Password-->
                        <label class="control-label" for="password"><fmt:message key="passwordInput"/></label>
                        <div class="controls">
                            <input type="password" id="password" name="password" placeholder="" class="form-control" onkeyup="confrontaPassword()">
                            <p id="pwd-text" class="help-block"><fmt:message key="inputPasswordMessage"/></p>
                        </div>
                    </div>

                    <div id="form-pwd-confirm" class="form-group has-feedback">
                        <!-- Password -->
                        <label class="control-label"  for="password_confirm"><fmt:message key="passwordConfirmInput"/></label>
                        <div class="controls">
                            <input type="password" id="password_confirm" name="password_confirm" onkeyup="confrontaPassword()" placeholder="" class="form-control">
                            <p id="pwd-confirm-text" class="help-block"><fmt:message key="confimPasswordMessage"/></p>
                        </div>                        
                        <span class="error" style="color:red;text-align: center;"><b>${error_a}</b><br></span>
                    </div>
                    <br>






                    <div class="control-group">
                        <!-- Button -->
                        <div class="form-group">
                            <button id="daAbilitare" class="btn btn-success disabled center-block"><fmt:message key="accountConfirmButton"/></button>
                            <input name="op" value="registrazione" hidden="hidden">
                        </div>
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" src="./js/jquery.js"></script>
<script type="text/javascript" src="./js/bootstrap.js"></script>
<script type="text/javascript" src="./js/Alb.js"></script> 
<script type="text/javascript" src="./js/Mat.js"></script> 
<script type="text/javascript" src="./js/main.js"></script> 
<script>
                                $(document).ready(function ()
                                {
                                    $("#username").focus();
                                });

</script>
</body>

</html>
