<%-- 
    Document   : index
    Created on : 19-giu-2017, 15.03.52
    Author     : Sara
--%>

<%@page import="javaFunctions.Functions"%>
<%@page import="java.util.*"%>
<%@page import="wiser.dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title></title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/bootstrap.min.css">

        <link rel="stylesheet" href="css/bootstrap-theme.min.css">
        <link rel="stylesheet" href="css/main.css">


    </head>
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
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
                                 <!--       <ul class="nav navbar-nav">
                                            <li><a href="#">Link <span class="sr-only">Link at: </span></a></li>
                                            <li><a href="#">Link</a></li>
                                            <li class="dropdown">
                                                <a href="#" class="dropdown-toggle pulsante-dropdown" data-toggle="dropdown" role="button" >Menu <span class="caret"></span></a>
                                                <ul class="dropdown-menu" role="menu">
                                                    <li><a href="#">Link1</a></li>
                                                    <li><a href="#">Link2</a></li>
                                                    <li><a href="#">Link3</a></li>
                                                    <li class="divider"></li>
                                                    <li><a href="#">Link4</a></li>
                                                    <li class="divider"></li>
                                                    <li><a href="#">Link5</a></li>
                                                </ul>
                                            </li>
                                        </ul>  -->
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

                                        <%
                                            String searchBar = (String) request.getAttribute("ricerca");
                                            if (searchBar == null) {
                                                searchBar = "";
                                            }
                                        %>
                                        <form class="navbar-form   hidden-xs" role="search">
                                            <div id="nav-centrata" class="input-group" >
                                                <input id="auto0" name="search" type="text" value='<%=searchBar%>' class="form-control center-block height-40" placeholder=<fmt:message key="searchPlaceHolder"/> onkeydown="arrowEnable(event)" onkeyup="autocompl(this.value, event);" 
                                                       autocomplete="off">



                                                <input id="op"  type="text" value="getList" hidden>

                                                <span class="input-group-btn">
                                                    <button class="btn btn-success height-40" type="button" onclick="parseSend()"><fmt:message key="searchButton"/></button>
                                                </span>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="hidden-xs col-lg-1 col-md-1 col-sm-1 ">
                        <ul class="nav navbar-nav navbar-right">
                            <li>
                                <a href="login.jsp"><span class="glyphicon glyphicon-log-in"></span>  <fmt:message key="loginButton"/></a>
          
                            </li>
                        </ul>

                    </div> 

                    <!-- /.navbar-collapse #menu -->
                    <!-- barra di ricerca per dispositivi mobile -->
                    <div class="collapse navbar-collapse hidden-lg" id="search">
                        <form class="mobile_search hidden-sm hidden-md hidden-lg" role="search" action="/" method="GET">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Search..."/>
                                <span class="input-group-btn">
                                    <a href="ActionServlet?op=getList" class="btn btn-success" type="button">Search</a>
                                </span>
                            </div><!-- /.navbar-collapse #search -->
                        </form>

                    </div><!-- /.container -->
                </div>
            </div>
        </nav> 

        <div id="autocompl" style="display:none">
            <div id="auto1" class="autoc" style="display: none" onmouseover="riempiSearch(id.valueOf())" onclick="ricerca(id.valueOf())"></div>
            <div id="auto2" class="autoc" onmouseover="riempiSearch(id.valueOf())" onclick="ricerca(id.valueOf())"></div>
            <div id="auto3" class="autoc" onmouseover="riempiSearch(id.valueOf())" onclick="ricerca(id.valueOf())"></div>
            <div id="auto4" class="autoc" onmouseover="riempiSearch(id.valueOf())" onclick="ricerca(id.valueOf())"></div>
        </div>

        <!-- Sidebar -->
        <!-- il div wrapper serve per comprimere la navbar e comprende tutto il contenuto della pagina-->
        <div id="wrapper"> 
            <div id="sidebar-wrapper" style="margin-top: 30px"> 
                <div class="sidebar-nav">
                    <h3><fmt:message key="titleCategories"/></h3>
                    <div class="list-group">
                        <%                            int servicesDim = (Integer) request.getAttribute("servicesDim");
                            int numPages = Functions.numberOfPages(servicesDim);
                            String filter = (String) request.getAttribute("filtro");
                            String key = (String) request.getAttribute("search");
                            if (key != null) {
                                key = key.replace("\"", "\'");
                            }
                            String tag = (String) request.getAttribute("tag");
                            String ordinamento = (String) request.getAttribute("orderBy");
                            ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("cats");
                            Iterator<Category> iterCat = categories.iterator();
                            while (iterCat.hasNext()) {
                                Category selCat = (Category) iterCat.next();
                        %>        
                        <a href="ActionServlet?op=getList&filtro=<%=selCat.getNome()%>&search=<%=key%>&tag=<%=tag%>" class="list-group-item"> <%=selCat.getNome()%></a>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <!-- Pagina centrale -->
            <div>
                <!-- Tasto per comprimere sidebar -->
                <a href="#menu-toggle" class="btn hidden-sm btn-default hidden-lg hidden-md" id="menu-toggle">Toggle Menu</a>

                <!--Paginazione schermo piccolo-->
                <ul class="pagination  hidden-sm hidden-lg hidden-md" style="float:right; margin: 0px;" >
                    <%
                        if (numPages > 1) {
                            for (int i = 0; i < numPages; i++) {

                    %>

                    <li><a href="ActionServlet?op=getList&start=<%=i * 8%>&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=<%=ordinamento%>"><%=i + 1%></a></li>
                        <%
                                }
                            }
                        %>
                </ul>
            </div>

            <!-- Page Content -->
            <div class="container-fluid" style="margin: 0 auto"> 
                <div id="page-content-wrapper">
                    <div class="container-fluid">
                        <div class=" btn-group" id="ordinaPer">  
                            <button type="button" class="btn btn-success dropdown-toggle pos" data-toggle="dropdown">
                                <fmt:message key="orderButton"/> <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu" role="menu">
                                <li><a id="1" class="mouseOver" href="ActionServlet?op=getList&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=nome"><fmt:message key="orderModeName"/></a></li>
                                <li class="dropdown-submenu">
                                    <a class="test mouseOver" id="2" tabindex="-1" id="2"><fmt:message key="orderModeUse"/><span class="caret"></span></a>
                                    <ul class="dropdown-menu" id="menu1">
                                        <li><a tabindex="-1" class="mouseOver" id="3" href="ActionServlet?op=getList&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=utilizziMax"><fmt:message key="orderMostUsed"/></a></li>
                                        <li><a tabindex="-1" class="mouseOver" id="4" href="ActionServlet?op=getList&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=utilizziMin"><fmt:message key="orderLessUsed"/></a></li>
                                    </ul>
                                </li>
                                <li class="dropdown-submenu">
                                    <a class="test1 mouseOver" id="5" tabindex="-1"><fmt:message key="orderModeVote"/><span class="caret"></span></a>
                                    <ul class="dropdown-menu" id="menu2">
                                        <li><a tabindex="-1" class="mouseOver" id="6" href="ActionServlet?op=getList&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=votoMax"><fmt:message key="orderMostVoted"/></a></li>
                                        <li><a tabindex="-1" class="mouseOver" id="7" href="ActionServlet?op=getList&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=votoMin"><fmt:message key="orderLessVoted"/></a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div><br>
                        <div id="divOrd" class="col-lg-12">
                            <div id="ord" class="label label-success" role="alert">${ordinamento}</div>
                        </div>

                        <%
                            ArrayList<DataService> services = (ArrayList<DataService>) request.getAttribute("list");
                            Iterator<DataService> iterServ = services.iterator();
                            while (iterServ.hasNext()) {
                                DataService service = (DataService) iterServ.next();
                                Set<Tag> taglist = service.getTag();
                                Iterator iterTag = taglist.iterator();
                        %>

                        <div class="col-lg-12 well" id="DIV<%= service.getId()%>">
                            <div class="row">
                                <div class="hidden-xs hidden-sm col-md-2 col-lg-2">
                                    <img class="img-circle img-responsive center-block" src="data:image/png;base64,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" alt="" width="150" height="150">
                                </div>
                                <div class="col-sm-12 col-md-6 col-lg-8">
                                    <h4 class="title"> <%=service.getNome()%> </h4>
                                    <h6 class="title"> <%=service.getNome()%> </h6>
                                    <h5 class="description"> 
                                        <%=service.getDescrizione()%>
                                    </h5>
                                    <p>
                                        <%
                                            while (iterTag.hasNext()) {
                                                Tag selTag = (Tag) iterTag.next();
                                        %>

                                        <a href="ActionServlet?op=getList&tag=<%=selTag.getNome()%>"> <%= selTag.getNome()%> </a>

                                        <%
                                            }
                                        %>
                                    </p>
                                </div>


                                <div class="col-sm-12 col-md-2 col-lg-2 margine-top-50">
                                    <div class="ratings">

                                        <%  int parteIntera = service.getParteInteraVoti(service.mediaVoti());
                                            double parteDecimale = service.getParteDecimaleVoti(service.mediaVoti());
                                            int count = 0;
                                            int k;
                                            int i;   %>

                                        <%      for (k = 0; k < parteIntera; k++) {                                        %>           
                                        <span class="glyphicon glyphicon-star"></span>
                                        <%          count++;
                                            }                   %>     


                                        <%      if ((parteDecimale < 0.5)&&(count<5)) {       %>          
                                        <span class="glyphicon glyphicon-star-empty"></span> 
                                        <%          count++;

                                        } else {if(count<5) {                 %>
                                        <span class="glyphicon glyphicon-star half"></span> 
                                        <%              count++;
                                        }
                                            }
                                            for (i = count; i < 5; i++) {                 %>                                                                                                                                                

                                        <span class="glyphicon glyphicon-star-empty"></span>
                                        <%      }%>

                                        <p> <%=service.getNumeroUtilizzi()%> reviews</p>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <%
                            }
                        %>
                    </div>
                </div>
            </div>

            <ul class="pagination hidden-xs">
                <%
                    if (numPages > 1) {
                        for (int i = 0; i < numPages; i++) {

                %>

                <li><a href="ActionServlet?op=getList&start=<%=i * 8%>&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=<%=ordinamento%>"><%=i + 1%></a></li>

                <%
                        }
                    }
                %>
            </ul>
        </div>

        <footer class="container-fluid text-right" style="color: black; padding: 15px;">
            <p>&copy; <fmt:message key="firme"/></p>
        </footer>
        <script type="text/javascript" src="./js/jquery.js"></script>
        <script type="text/javascript" src="./js/bootstrap.js"></script>
        <script type="text/javascript" src="./js/autocomplAjax.js"></script>
        <script type="text/javascript" src="./js/main.js"></script>
        <script type="text/javascript" src="./js/Tibi.js"></script>




        <!-- script per comprimere sidebar -->
        <script>
                $("#menu-toggle").click(function (e) {
                    e.preventDefault();
                    $("#wrapper").toggleClass("toggled");
                });
        </script>
    </body>
</html>