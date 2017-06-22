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

        <style>#centra{margin-top: 50pt;margin-left: 40%;}</style>

        <title>Login</title>
    </head>
    <body>
        <div class="row">
            <div class="col-lg-10 col-md-10 col-sm-10">

                <form class="form-horizontal" action='ActionServlet' method="post" id="centra">
                    <fieldset>
                        <div id="legend">
                            <legend class="">Login</legend>
                        </div>
                        <div class="control-group">
                            <!-- Username -->
                            <label class="control-label"  for="username">Username</label>
                            <div class="controls">
                                <input type="text" id="username" name="username" placeholder="" class="input-xlarge">
                            </div>
                        </div>
                        <div class="control-group">
                            <!-- Password-->
                            <label class="control-label" for="password">Password</label>
                            <div class="controls">
                                <input type="password" id="password" name="password" placeholder="" class="input-xlarge">
                                <input name="op" value="login" hidden="hidden">
                            </div>
                            <span class="error" style="color:red;text-align: center;"><b>${error}</b><br></span>
                        </div>


                        <br>
                        <div class="control-group">
                            <!-- Button -->
                            <div class="controls">
                                <button class="btn btn-success">Accedi</button>
                            </div>
                        </div>
                        <div class="bottom text-center">
                            Nuovo utente? <a href="registrazione.jsp"><b> Registrati!</b></a>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </body>

</html>

