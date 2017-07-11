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


        <title>Login</title>
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
                            <span class="glyphicon glyphicon-user white" aria-hidden="true"></span>
                        </button>
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#search">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span>
                        </button>
                    </div>
                    <div class="col-lg-10 col-md-10 col-sm-10">
                        <div class="container-fluid">
                            <div class="row">    
                                <div class="collapse navbar-collapse" id="menu">
                                    <div class="col-lg-3 col-md-3 col-sm-3">
                                        <form action="firstpage.jsp">
                                            <button type="submit" class="btn-lg glyphicon glyphicon-home pull-left btnTransparent btnHome"></button>
                                        </form>
                                    </div>
                                    <div class="col-lg-1 col-md-1 col-sm-1">
                                    </div>
                                    <div class="col-lg-8 col-md-8 col-sm-8">

                                        <!--search bar-->
                                        <!-- <form class="navbar-form   hidden-xs" role="search" action="ActionServlet" method="GET">
                                             <div id="nav-centrata" class="input-group" >
                                             <input id="auto0" name="search" type="text" class="form-control center-block height-40" placeholder="search..." onkeydown="arrowEnable(event)" onkeyup="autocompl(this.value, event);" 
                                                   autocomplete="off">
                                             <input name="op"  type="text" value="getList" hidden>
                                             <span class="input-group-btn">
                                             <button class="btn btn-success height-40" type="submit">Search</button>
                                             </span>
                                             <div>
                                             </form>-->







                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="hidden-xs col-lg-1 col-md-1 col-sm-1 ">

                    </div> 

                    <!-- /.navbar-collapse #menu -->
                    <!-- barra di ricerca per dispositivi mobile -->
                    <div class="collapse navbar-collapse hidden-lg" id="search">

                    </div><!-- /.container -->
                </div>
            </div>
        </nav> 

        <div class="container">
            <div class="row">
                <br>
                <div class="col-lg-3 col-md-2 col-sm-2">

                </div>
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

