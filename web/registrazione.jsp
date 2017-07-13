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
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">

        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>


        <link rel="stylesheet" href="jquery-ui/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <!--<script src="https://code.jquery.com/jquery-1.12.4.js"></script>-->
        <script src="jquery-ui/jquery-ui.js"></script>

        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/main.css">



        <script type="text/javascript" src="./js/gestione_voti.js"></script>
        <link rel="stylesheet" href="css/sara_css.css">



 
        <title>Create account</title>

    </head>
    <body>
        <fmt:setLocale value="en"/>
        <fmt:setBundle basename="indexBundle"/>   
        <nav class="navbar navbar-inverse">
            <div class="container-fluid" id="container-nav">
                <div class="row">      
                    <div class="navbar-header col-lg-1 col-md-1 col-sm-1">

                        <a class="navbar-brand" href="ActionServlet?op=getList"><fmt:message key="titleNavbar"/></a>                       
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#">

                            <!--<span class="glyphicon glyphicon-user white" aria-hidden="true"></span>-->
                        </button>
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#search">
                            <!--<span class="glyphicon glyphicon-search" aria-hidden="true"></span>-->
                        </button>
                    </div>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                        <div class="container-fluid">
                            <div class="row">    
                                <div class="collapse navbar-collapse" id="menu">
                                    <div class="col-lg-3 col-md-3 col-sm-3">                                
                                    </div>
                                    <div class="col-lg-1 col-md-1 col-sm-1">
                                    </div>
                                    <div class="col-lg-8 col-md-8 col-sm-8">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="hidden-xs col-lg-1 col-md-1 col-sm-1 ">

                    </div> 

                    <div class="collapse navbar-collapse hidden-lg" id="search">

                    </div>
                </div>
            </div>
        </nav> 
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">                    
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-8">

                    <form class="form-horizontal" id="centra"> 
                        <fieldset>
                            <div id="legend" class="title text-center">
                                <h2><fmt:message key="createAccountTitle"/></h2> 
                            </div>
                            <div id="form-user" class="form-group has-feedback">
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
                                    <p id="pwd-confirm-text" class="help-block"><fmt:message key="confirmPasswordMessage"/></p>
                                </div>                        
                                <span class="error" style="color:red;text-align: center;"><b>${error_a}</b><br></span>
                            </div>
                            <br>
                            <div class="control-group">
                                <!-- Button -->
                                <div class="form-group">

                                    <button type="button" id="daAbilitare" class="btn btn-success disabled center-block" onclick="confermaRegistrazione('<fmt:message key="welcomeRegister"/>')"><fmt:message key="accountConfirmButton"/></button>
                                </div>
                            </div>
                        </fieldset>
                    </form>

                    <!-- Modal -->
                    <div id="modalRegistration" class="modal fade posizionaModal" role="dialog">
                        <div class="modal-dialog">

                            <!-- Modal content-->
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                                    <h4 class="modal-title"><fmt:message key="confirmRegistration"/></h4>
                                </div>
                                <div class="modal-body">
                                    <div>
                                        <div class="form-group has-feedback"> 
                                            <div class="controls">
                                                <label id="welcDiv" class="control-label" for="nameTag"></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript" src="./js/Alb.js"></script>
        <script type="text/javascript" src="./js/Tibi.js"></script> 
        <script type="text/javascript" src="./js/Mat.js"></script> 
        <script type="text/javascript" src="./js/main.js"></script> 
        <script>
                                        $(document).ready(function ()
                                        {
                                            $("#username").focus();
                                            loadVars('<fmt:message key="errorMessageUser"/>', '<fmt:message key="errorPass" />', '<fmt:message key="passOk"/>');
                                        });

        </script>
    </body>

</html>
