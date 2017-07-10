<%-- 
    Document   : index
    Created on : 19-giu-2017, 15.03.52
    Author     : Sara
--%>

<%@page import="javaFunctions.Functions"%>
<%@page import="java.util.*"%>
<%@page import="wiser.dao.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

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

        <!--  <script type="text/javascript" src="./js/bootstrap.js"></script>  -->

        <link rel="stylesheet" href="jquery-ui/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

        <!--<script src="https://code.jquery.com/jquery-1.12.4.js"></script>-->
        <script src="jquery-ui/jquery-ui.js"></script>
        <link rel="stylesheet" href="css/sara_css.css">

        <link rel="apple-touch-icon" href="apple-touch-icon.png">
        <link rel="stylesheet" href="css/sidebar.css">
        <link rel="stylesheet" href="css/main.css">


        <script type="text/javascript" src="./js/gestione_voti.js"></script>
        <link rel="stylesheet" href="css/sara_css.css">
    </head>> 
    <body>
        <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
        <nav class="navbar navbar-default">
            <div class="container-fluid" id="container-nav">
                <div class="row">      
                    <div class="navbar-header col-lg-7 col-md-7 col-sm-7">
                        <a class="navbar-brand" href="#">Wiser</a>
                        <div class="collapse navbar-collapse col-lg-6 col-md-6 col-sm-6" id="menu">
                            <!--    <div class="col-lg-5 col-md-5 col-sm-5">   -->
                            <ul class="nav navbar-nav">
                                <li><a href="#"><b>My MashUp</b></a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle pulsante-dropdown" data-toggle="dropdown" role="button"><b>Order by</b><span class="caret"></span></a>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a href="#">Top Scored</a></li>
                                        <li><a href="#">Most Used</a></li>
                                        <li><a href="#">Most Recent</a></li>
                                    </ul>
                                </li>
                            </ul>
                        </div>
                        <div class="col-lg-6 col-md-6 col-sm-6">
                            <%
                                String nome_utente = (String) request.getAttribute("nomeU");
                                session = request.getSession();
                                String nomeSes = session.getAttribute("name").toString();
                                String searchBar = (String) request.getAttribute("ricerca");
                                if (searchBar == null) {
                                    searchBar = "";
                                }
                            %>
                            <form class="navbar-form   hidden-xs" role="search">
                                <div id="nav-centrata" class="input-group" >
                                    <input id="auto0" name="search" type="text" value='<%=searchBar%>' class="form-control center-block height-40" placeholder="search..." onkeydown="arrowEnable(event)" onkeyup="autocompl(this.value, event);" 
                                           autocomplete="off">



                                    <input id="op"  type="text" value="getList2" hidden>

                                    <span class="input-group-btn">
                                        <button class="btn btn-success height-40" type="button" onclick="parseSend()">Search</button>
                                    </span>
                                    <input id="userModeSearch" value="true" class="hidden">
                                    <input id="userSearch" value="<%=nome_utente%>" class="hidden">
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="navbar-header col-lg-5 col-md-5 col-sm-5">

                        <ul class="nav navbar-nav navbar-right">
                            <!-- Trigger the modal with a button -->
                            <li><a href="#"><button id="btnCreaMeshup" type="button" class="btn btn-warning margine" onclick="apriWizard()">New Mash-Up! </button></a></li>
                            <!--         <li><a href="#"><button id="confirmMeshup" type="button" class="btn btn-warning margine hidden">Conferma</button></a></li>  -->

                            <!-- Modal -->
                            <div id="meshup-modal" class="modal fade" role="dialog">
                                <div class="modal-dialog">

                                    <!-- Modal content-->
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                            <h4 class="modal-title">Create Mash-Up</h4>
                                        </div>
                                        <div class="modal-body">

                                            <div id="creazioneAggr" class="creazioneAggr">
                                                <div id="inputAgg" class="form-group has-feedback"> 

                                                    <label class="control-label" for="nameAgg">Name: </label>
                                                    <div class="controls">
                                                        <input id="nameAgg" type="text" placeholder="insert name" class="form-control">
                                                        <p id="mashup-text" class="help-block">A mash-up can contain any letters (without accent) or numbers, without spaces and can't start with a number</p>
                                                        <p id="mashup-err" class="help-block hidden">This mash-up already exists!</p>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="form-group has-feedback"> 
                                                    <label class="control-label" for="descrizioneAgg">Description: </label>
                                                    <textarea class="form-control" id="descrizioneAgg" placeholder="insert description"></textarea>                                           
                                                </div>
                                            </div>
                                            <div id="msg-conf-aggr" class="creazioneAggr hidden">
                                                <div  class="jumbotron feedbackMashup">
                                                    <h3>
                                                        Mash-up created!
                                                    </h3>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="modal-footer">
                                            <button id="btn-crea-aggregazione" type="button" onclick="creaAggregazione()" class="btn btn-primary" >Confirm</button>                                                                                    
                                            <button  type="button" class="btn btn-danger" data-dismiss="modal" onclick="chiudiModalAggregazione();">Close</button>
                                        </div>                     
                                    </div>
                                </div>
                            </div>

                            <li><a href="#"><span class="glyphicon glyphicon-user"></span><%=nomeSes%></a></li>
                            <li><a href="ActionServlet?op=logout"><span class="glyphicon glyphicon-log-out"></span>Logout</a></li>
                        </ul>

                    </div> 

                    <!-- /.navbar-collapse #menu -->
                    <!-- barra di ricerca per dispositivi mobile -->
                    <div class="collapse navbar-collapse hidden-lg" id="search">
                        <form class="mobile_search hidden-sm hidden-md hidden-lg" role="search" action="/" method="GET">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="looking for something?"/>
                                <span class="input-group-btn">
                                    <a href="#" class="btn btn-success" type="button">Search</a>
                                </span>

                            </div><!-- /.navbar-collapse #search -->
                        </form>
                    </div><!-- /.container -->
                </div>
            </div> 
        </nav> 

        <div id="autocompl" style="display:none">
            <div id="auto1" class="autoc" style="display: none" onmouseover="riempiSearch(id.valueOf())"></div>
            <div id="auto2" class="autoc" onmouseover="riempiSearch(id.valueOf())"></div>
            <div id="auto3" class="autoc" onmouseover="riempiSearch(id.valueOf())"></div>
            <div id="auto4" class="autoc" onmouseover="riempiSearch(id.valueOf())"></div>
        </div>

        <!-- Sidebar -->
        <!-- il div wrapper serve per comprimere la navbar e comprende tutto il contenuto della pagina-->
        <div id="wrapper"> 
            <div id="sidebar-wrapper" style="margin-top: 30px"> 
                <div class="sidebar-nav">
                    <h3> Filter by Categories </h3>
                    <div class="list-group">
                        <%
                            int servicesDim = (Integer) request.getAttribute("servicesDim");
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
                        <a href="ActionServlet?op=getList2&filtro=<%=selCat.getNome()%>&search=<%=key%>&tag=<%=tag%>&nomeU=<%=nome_utente%>" class=" categorie list-group-item"> <%=selCat.getNome()%></a>
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

                    <li><a href="ActionServlet?op=getList2&start=<%=i * 8%>&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=<%=ordinamento%>&nomeU=<%=nome_utente%>"><%=i + 1%></a></li>
                        <%
                                }
                            }
                        %>
                </ul>
            </div>

            <!-- Page Content -->
            <div class="container" style="margin: 0 auto"> 
                <div id="page-content-wrapper">
                    <div class="row">
                        <div class="col-lg-9">
                            <div class="container-fluid">
                                <div class=" btn-group" id="ordinaPer">  
                                    <button type="button" class="btn btn-success dropdown-toggle pos" data-toggle="dropdown">
                                        Ordina per <span class="caret"></span>
                                    </button>
                                    <ul class="dropdown-menu" role="menu">
                                        <li><a id="1" class="mouseOver" href="ActionServlet?op=getList2&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=nome&nomeU=<%=nome_utente%>">Nome</a></li>
                                        <li class="dropdown-submenu">
                                            <a class="test mouseOver" id="2" tabindex="-1" id="2">Utilizzi<span class="caret"></span></a>
                                            <ul class="dropdown-menu" id="menu1">
                                                <li><a tabindex="-1" class="mouseOver" id="3" href="ActionServlet?op=getList2&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=utilizziMax&nomeU=<%=nome_utente%>">Dai più ai meno utilizzati</a></li>
                                                <li><a tabindex="-1" class="mouseOver" id="4" href="ActionServlet?op=getList2&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=utilizziMin&nomeU=<%=nome_utente%>">Dai meno ai più utilizzati</a></li>
                                            </ul>
                                        </li>
                                        <li class="dropdown-submenu">
                                            <a class="test1 mouseOver" id="5" tabindex="-1">Voti<span class="caret"></span></a>
                                            <ul class="dropdown-menu" id="menu2">
                                                <li><a tabindex="-1" class="mouseOver" id="6" href="ActionServlet?op=getList2&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=votoMax&nomeU=<%=nome_utente%>">Dai più ai meno votati</a></li>
                                                <li><a tabindex="-1" class="mouseOver" id="7" href="ActionServlet?op=getList2&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=votoMin&nomeU=<%=nome_utente%>">Dai meno ai più votati</a></li>
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
                                    Aggregazione aggregazione = (Aggregazione) request.getAttribute("newAggregazione");
                                    while (iterServ.hasNext()) {
                                        DataService service = (DataService) iterServ.next();
                                        Set<Tag> taglist = service.getTag();
                                        Iterator iterTag = taglist.iterator();
                                %>
                                <div class="row">
                                    <div class="col-lg-12 well" id="DIV'<%= service.getId()%>'">

                                        <div class="hidden-xs hidden-sm col-md-2 col-lg-2">
                                            <img class="img-circle img-responsive center-block" src="data:image/png;base64,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" alt="" width="150" height="150">
                                        </div>
                                        <div class="col-sm-12 col-md-5 col-lg-7">
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

                                                <a href="ActionServlet?op=getList2&tag=<%=selTag.getNome()%>&nomeU=<%=nome_utente%>"> <%= selTag.getNome()%> </a>

                                                <%
                                                    }
                                                %>
                                            </p>
                                        </div>


                                        <div class="col-sm-12 col-md-2 col-lg-2" style="margin-top: 30px">
                                            <button onclick="selezione('<%=service.getId()%>')" type="button" data-toggle="modal" class="dimension" data-target="#myModal<%=service.getId()%>"><span class="glyphicon glyphicon-pencil"></span>   Vota</button>
                                            <br>
                                            <br>
                                            <button type="button" data-toggle="modal" class="dimension" data-target="#myModalTag<%=service.getId()%>"> <span class="glyphicon glyphicon-tag"></span>   Tag</button>

                                            <!-- Modal -->
                                            <div id="myModalTag<%=service.getId()%>" class="modal fade" role="dialog">
                                                <div class="modal-dialog">

                                                    <!-- Modal content-->
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Add Tag</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div id="creazioneAggr">
                                                                <div id="inputAgg" class="form-group has-feedback"> 
                                                                    <div class="controls">
                                                                        <label class="control-label" for="nameTag">Tag name: </label><input id="nameAgg" type="text" placeholder="insert name" class="form-control">
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" onclick="aggiungiTag('<%=service.getId()%>')" class="btn btn-primary mostra" data-dismiss="modal"> Confirm</button> 
                                                                <button type="button" class="btn btn-danger mostra" data-dismiss="modal"> Annulla</button> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>



                                            <!-- Modal -->

                                            <div class="modal fade modalToClose" id="myModal<%=service.getId()%>" role="dialog" >
                                                <div class="modal-dialog">

                                                    <!-- Modal content-->
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close closeButtonX" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Vota <%=service.getNome()%></h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div class="dialogo" nome="<%=service.getId()%>" title="Vota ">
                                                                <ol>
                                                                    <li class="ui-widget-content">POOR (scarcely adopted within the mashup)<span class='label right'>0.0</span></li>
                                                                    <li class="ui-widget-content">MARGINAL (several problems during execution within the mashup)<span class='label right'>0.125</span></li>
                                                                    <li class="ui-widget-content">FAIR (slow and cumbersome)<span class='label right'>0.25</span></li>
                                                                    <li class="ui-widget-content">SATISFACTORY (small performance penalty)<span class='label right'>0.375</span></li>
                                                                    <li class="ui-widget-content">GOOD (minimum mashup requirements are satisfied)<span class='label right'>0.5</span></li>
                                                                    <li class="ui-widget-content">VERY GOOD (good performances and minimum mashup requirements are satisfied)<span class='label'>0.625</span></li>
                                                                    <li class="ui-widget-content">EXCELLENT (discreet performances and satisfying functionalities within the mashup)<span class='label right'>0.75</span></li>
                                                                    <li class="ui-widget-content">OUTSTANDING (very good performances and functionalities within the mashup)<span class='label right'>0.875</span></li>
                                                                    <li class="ui-widget-content">EXCEPTIONAL (very good performances, functionalities and easy to use in mashup)<span class='label right'>1.0</span></li>
                                                                </ol>
                                                                <br>

                                                            </div>
                                                            <div class="hidden modalConfermaVoto text-center" >
                                                                <p class="textConfermaVoto">Vuoi assegnare il voto <b class="votoScelto"></b> a <b><%=service.getNome()%></b> in relazione a qualche aggregazione?</p>
                                                                <br>
                                                                <div class="row">

                                                                    <div class="center-block">
                                                                        <button type="button" class="btn btn-success yesBtn" onclick="si('<%=service.getId()%>');"><a>yes</a></button>
                                                                        <button type="button" class="btn btn-success noBtn" onclick="noA('<%=service.getId()%>');"><a>no</a></button>
                                                                    </div>
                                                                </div>
                                                                <br>
                                                            </div>
                                                            <div class="msgModal hidden">
                                                                <ul class="listaAggregazioni hidden list-group checked-list-box">
                                                                </ul>
                                                                <div class="jumbotron feedbackVoto hidden">                                                           
                                                                </div>
                                                            </div>

                                                            <div class="modal-footer">
                                                                <button class="btn btnIndietro glyphicon glyphicon-chevron-left pull-left btn-default hidden" onclick="indietro()"></button>
                                                                <button type="button" class="btn btn-primary hidden disabled confermaButton" onclick="confermaVoto('<%=service.getId()%>')"><a>Confirm</a></button>                                                               
                                                                <button class="btn btn-primary confermaVoto" disabled="true" onclick="modal2()">Confirm</button>
                                                                <button  type="button" class="btn btn-danger closeButton" data-dismiss="modal">Close</button>
                                                            </div>

                                                        </div>
                                                    </div>

                                                </div>
                                            </div>
                                        </div>
                                        <div class="hidden-xs hidden-sm col-md-1 col-lg-1">
                                            <a type="button" id="addAggr<%=service.getId()%>" onclick="aggiungiDataService('<%=service.getId()%>')" class="aggr hidden btn btn-warning">+</a>                   
                                        </div>   
                                    </div>
                                </div>

                                <%
                                    }
                                %>
                            </div>
                        </div>
                        <div class="col-lg-3 list-group">
                            <p class="center-block">MY MASH-UP</p>
                            <%
                                ArrayList<Aggregazione> mashup = (ArrayList<Aggregazione>) session.getAttribute("mashup");
                                if (mashup.size() == 0) {
                            %>
                            <div>No mash-up</div>
                            <%
                            } else {
                                Iterator iterMash = mashup.iterator();
                                while (iterMash.hasNext()) {
                                    Aggregazione aggr = (Aggregazione) iterMash.next();
                                    String nome = aggr.getNome();
                                    HashSet<DataService> dataServiceByAggr = (HashSet<DataService>) aggr.getElencoDS();
                            %>
                            <div class="panel-group">
                                <div class="panel panel-default">
                                    <div class="panel-heading">
                                        <h4 class="panel-title">
                                            <a data-toggle="collapse" href="#collapse<%=nome%>"><%=nome%></a>
                                            <button id="<%=nome%>elimina" class="glyphicon glyphicon-trash pull-right" data-toggle="modal" data-target="#deleteMashup<%=nome%>"></button>
                                            <button id="<%=nome%>conferma" onclick="confermaMashUp('<%=nome%>')" class="hidden confermaMash glyphicon glyphicon-ok pull-right"></button>
                                            <button id="<%=nome%>modifica" onclick="modificaMashUp('<%=nome%>', '<%=aggr.getId()%>')" class="glyphicon modificaMash glyphicon glyphicon-edit pull-right"></button> 


                                            <div id="deleteMashup<%=nome%>" class="modal fade" role="dialog">
                                                <div class="modal-dialog">

                                                    <!-- Modal content-->
                                                    <div class="modal-content">
                                                        <div class="modal-header">
                                                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                            <h4 class="modal-title">Delete Mash-Up</h4>
                                                        </div>
                                                        <div class="modal-body">
                                                            <div id="creazioneAggr">
                                                                <div id="inputAgg" class="form-group has-feedback"> 
                                                                    <div class="controls">
                                                                        Sei sicuro di voler eliminare <b><%=nome%></b> ?
                                                                        <br>
                                                                        Attenzione, una volta cancellato non può più essere recuperato!
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" onclick="eliminaAggregazione('<%=nome%>')" class="btn btn-primary mostra" data-dismiss="modal"> Cancella</button> 
                                                                <button type="button" class="btn btn-danger mostra" data-dismiss="modal"> Annulla</button> 
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </h4>
                                    </div>
                                    <div id="collapse<%=nome%>" class="panel-collapse collapse">
                                        <ul id="<%=nome%>list" class="list-group">
                                            <%
                                                if (dataServiceByAggr.size() == 0) {
                                            %>
                                            <li class="list-group-item" id="no<%=nome%>" > No Data Services </li> 
                                                <% } else {
                                                %>
                                            <li class="list-group-item hidden" id="no<%=nome%>" > No Data Services </li>
                                                <%
                                                    Iterator iteratoreDS = dataServiceByAggr.iterator();
                                                    while (iteratoreDS.hasNext()) {
                                                        DataService ds = (DataService) iteratoreDS.next();
                                                        String nomeDS = ds.getNome();
                                                %>
                                            <li id="<%=nome%>_<%=ds.getId()%>" class="list-group-item"><%=nomeDS%> <button type="button" id="delAggr<%=ds.getId()%>" onclick="rimuoviDataService('<%=ds.getId()%>')" class="delAggr pull-right glyphicon glyphicon-remove glyphicon-minus btn-xs btn-danger hidden"></button></li>   
                                                <%
                                                        }
                                                    }
                                                %>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>    
                    </div>
                </div>
            </div>

            <ul class="pagination hidden-xs">
                <%
                    if (numPages > 1) {
                        for (int i = 0; i < numPages; i++) {

                %>

                <li><a href="ActionServlet?op=getList2&start=<%=i * 8%>&filtro=<%=filter%>&search=<%=key%>&tag=<%=tag%>&orderBy=<%=ordinamento%>&nomeU=<%=nome_utente%>"><%=i + 1%></a></li>

                <%
                        }
                    }
                %>
            </ul>
        </div>


        <footer class="container-fluid text-right" style="color: black; padding: 15px;">
            <p>&copy; Beschi Chiari Tiberti Vivenzi</p>
        </footer>
        <script type="text/javascript" src="./js/autocomplAjax.js"></script>
        <script type="text/javascript" src="./js/Tibi.js"></script>
        <script type="text/javascript" src="./js/Mat.js"></script>




        <!-- script per comprimere sidebar -->
        <script>
                                                $("#menu-toggle").click(function (e) {
                                                    e.preventDefault();
                                                    $("#wrapper").toggleClass("toggled");
                                                });
        </script>
        <script>
            $(document).ready(selezionaCategoria('<%=filter%>'));
        </script>
        <script src="js/gestione_voti.js"></script>
    </body>
</html>