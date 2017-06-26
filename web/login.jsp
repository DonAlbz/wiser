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


        <title>Login</title>
    </head>
    <body>
        
        <div class="container">
            <div class="row">
                <br>
                <div class="col-lg-3 col-md-2 col-sm-2"></div>
                <div class="col-lg-6 col-md-8 col-sm-8">

                    <form class="form-horizontal" action='ActionServlet' method="post" id="centra">
                        <fieldset>
                            <div id="legend"  class="title text-center">
                                <h2 class="">Login</h2>
                            </div>
                            <div class="form-group has-feedback">
                                <!-- Username -->
                                <label class="control-label"  for="username">Username</label>
                                <div class="controls">
                                    <input type="text" id="username" name="username" placeholder="" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <!-- Password-->
                                <label class="control-label" for="password">Password</label>
                                <div class="controls">
                                    <input type="password" id="password" name="password" placeholder="" class="form-control">
                                    <input name="op" value="login" hidden="hidden">
                                </div>
                                <span class="error" style="color:red;text-align: center;"><b>${error}</b><br></span>
                            </div>


                            <br>
                            <div class="text-center">
                                <!-- Button -->
                                <div class="controls">
                                    <button class="btn btn-success">Accedi</button>
                                </div>
                            </div>
                            
                            <div class="clearfix"></div>
                                <br>
                                <br>
                                  <div class="bottom text-center">
                                    Nuovo utente? <a class="text-success" href="registrazione.jsp"><b> Registrati!</b></a>
                                    </div>
                              
                        </fieldset>
                    </form>
                </div>
            </div>
        </div>
        <script type="text/javascript" src="./js/jquery.js"></script>
        <script type="text/javascript" src="./js/bootstrap.js"></script>
        <script type="text/javascript" src="./js/Alb.js"></script> 
        <script type="text/javascript" src="./js/main.js"></script>
    </body>

</html>

