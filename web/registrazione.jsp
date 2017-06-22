<%-- 
    Document   : registrazione
    Created on : 20-giu-2017, 20.16.11
    Author     : Sara
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/bootstrap-theme.min.css">


        <title>Registrazione</title>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col-lg-3 col-md-3 col-sm-3 col-xs-2">                    
                </div>
                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-8">

                    <form class="form-horizontal" action='ActionServlet' method="post" id="centra">
                        <fieldset>
                            <div id="legend" class="title text-center">
                                <h2>Register</h2> 
                            </div>
                            <div id="form-user"class="form-group has-feedback">
                                <!-- Username -->
                                <label class="control-label"  for="username">Username</label>
                                <div class="controls">
                                    <input type="text" id="username" name="username" placeholder="" class="form-control" onkeyup="confrontaPassword()">
                                    <p id="user-text" class="help-block">Username can contain any letters or numbers, without spaces and can't start with a number</p>
                                </div>
                            </div>

                            <div id="form-pwd" class="form-group has-feedback">
                                <!-- Password-->
                                <label class="control-label" for="password">Password</label>
                                <div class="controls">
                                    <input type="password" id="password" name="password" placeholder="" class="form-control" onkeyup="confrontaPassword()">
                                    <p id="pwd-text" class="help-block">Password should be at least 4 characters</p>
                                </div>
                            </div>

                            <div id="form-pwd-confirm" class="form-group has-feedback">
                                <!-- Password -->
                                <label class="control-label"  for="password_confirm">Password (Confirm)</label>
                                <div class="controls">
                                    <input type="password" id="password_confirm" name="password_confirm" onkeyup="confrontaPassword()" placeholder="" class="form-control">
                                    <p id="pwd-confirm-text" class="help-block">Please confirm password</p>
                               </div>                        
                                <span class="error" style="color:red;text-align: center;"><b>${error_a}</b><br></span>
                            </div>
                            <br>



                            <script type="text/javascript">
                                
                                function confrontaPassword(){
                                    var user = document.getElementById("username").value;
                                    var pwd1 = document.getElementById("password").value;
                                    var pwd2 = document.getElementById("password_confirm").value;
                                    
                                    
                                    
                                    if((user !== "") && (pwd1 !== "") && (pwd2 !== "") && (pwd1 === pwd2) && (pwd1.length>3)) {
                                        document.getElementById("daAbilitare").removeAttribute("disabled");
                                        $("#form-pwd-confirm").removeClass("has-error");
                                        $("#form-pwd").removeClass("has-error");
                                        
                                        if(pwd1 === pwd2){
                                            $("#form-pwd-confirm").addClass("has-success");
                                            $("#form-pwd").addClass("has-success");
                                            $("#pwd-confirm-text").html("Password confirmed");
                                        }
                                    }
                                    else
                                    {  
                                        document.getElementById("daAbilitare").setAttribute("disabled", true);
                                        
                                        if(pwd2!=="" && pwd2.length >= pwd1.length && pwd1 !== pwd2){
                                            $("#form-pwd-confirm").addClass("has-error");
                                            $("#form-pwd").addClass("has-error");
                                            $("#pwd-confirm-text").html("This is not the same password!");
                                            
                                        }
                                    }
                                }
                            </script>

                                <div class="control-group">
                                <!-- Button -->
                                <div class="form-group">
                                    <button id="daAbilitare" disabled class="btn btn-success">Register</button>
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
        <script type="text/javascript" src="./js/main.js"></script> <!-- AGGIUNTOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO -->
    </body>
    
    <!--/*/*/*/*/*/*/*/*/ MANCA CONTROLLO UTENTE /*/*/*/*/*/*//*//*/*/* -->

</html>
