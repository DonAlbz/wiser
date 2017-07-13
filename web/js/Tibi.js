/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    $('.dropdown-submenu a.test').on("click", function (e) {
        $(this).next('ul').toggle();
        $("#menu2").css("display", "none");
        e.stopPropagation();
        e.preventDefault();
    });
});

$(document).ready(function () {
    $('.dropdown-submenu a.test1').on("click", function (e) {
        $(this).next('ul').toggle();
        $("#menu1").css("display", "none");
        e.stopPropagation();
        e.preventDefault();
    });
});


function riempiSearch(id) {
    if (id.valueOf() == "auto1") {
        $("#auto0").val($("#auto1").text());
    } else if (id.valueOf() == "auto2") {
        $("#auto0").val($("#auto2").text());
    } else if (id.valueOf() == "auto3") {
        $("#auto0").val($("#auto3").text());
    } else if (id.valueOf() == "auto4") {
        $("#auto0").val($("#auto4").text());
    }
}

/*
 function ricerca(id) {
 
 }*/


function modalAggregazioniPag2() {
    $(".modalScegliDS").removeClass("hidden");
    $(".formAggr").addClass("hidden");
}


var nomeAggregazCreata;
var xhr;
function creaAggregazione()
{
    var nameAgg = ($("#nameAgg").val());
    var nomeDesc = ($("#descrizioneAgg").val());
    patt1 = /^[A-Za-z]/ig;
    patt2 = /[^A-Za-z0-9]/gi;
    if (patt1.test(nameAgg) && !patt2.test(nameAgg)&&(nameAgg.length <16)) {
        $.post("ActionServlet", {"op": "meshup", "nameAgg": nameAgg, "descrizioneAgg": nomeDesc},
        function (response) {
            nomeAggregazCreata = response.trim().toString().trim();
            if (nomeAggregazCreata === "false") {
                $("#inputAgg").addClass("has-error");
                $("#mashup-err").toggleClass("hidden");
                $("#mashup-text").toggleClass("hidden");
            } else {
                $("#inputAgg").removeClass("has-error");
                $("#creazioneAggr").toggleClass("hidden");
                $("#msg-conf-aggr").toggleClass("hidden");
                $("#btn-crea-aggregazione").toggleClass("hidden");
            }
            //window.alert(nomeAggregazCreata);

        }, "text");
    }
    else {
        $("#inputAgg").addClass("has-error");
        if ($("#mashup-text").hasClass("hidden")) {
            $("#mashup-err").toggleClass("hidden");
            $("#mashup-text").toggleClass("hidden");
        }
    }
}


function chiudiModalAggregazione() {
    setTimeout(function () {
        location.reload();
    }, 300);
}


function myGetXmlHttpRequest()
{
    var XmlHttpReq = false;
    var activeXopt = new Array("Microsoft.XmlHttp", "MSXML4.XmlHttp", "MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp");
    // prima come oggetto nativo
    try
    {
        XmlHttpReq = new XMLHttpRequest();
    } catch (e)
    {
// poi come oggetto ActiveX dal pi? al meno recente
        var created = false;
        for (var i = 0; i < activeXopt.length && !created; i++)
        {
            try
            {
                XmlHttpReq = new ActiveXObject(activeXopt[i]);
                created = true;
            } catch (eActXobj)
            {
                alert("Il tuo browser non supporta AJAX!");
            }
        }
    }
    return XmlHttpReq;
}


function apriWizard() {
    div = $("#creazioneAggr");
    if (div.hasClass("hidden")) {
        $(".mostra").toggleClass("hidden");
    }
    $("#nameAgg").val("");
    $("#descrizioneAgg").val("");
    $("#inputAgg").removeClass("has-error");
    $("#mashup-text").removeClass("hidden");
    $("#mashup-err").addClass("hidden");
    $("#meshup-modal").modal("show");
}



/*
 function selezionaMashUp(nome) {
 var MashDaSel = [];
 MashDaSel = $(".mashDaSelezionare");
 MashDaSel.each(function () {
 if (($(this).text()) === nome.trim()) {
 $(this).addClass("active");
 }
 });
 }
 $(document).ready(selezionaMashUp(nomeAggregazCreata));
 */

var mashSel;
var nomeMashSel;
function modificaMashUp(nomeMU, idMU) {
    mashSel = idMU;
    //$(".mashCollapse").collapse("hide");
    $("#idMash").val(idMU);
    $("#nomeMash").val(nomeMU);
    $(".delAggr").addClass("hidden");
    $("#collapse" + nomeMU).collapse("show");
    $("#" + nomeMU.trim() + "list").find(".delAggr").removeClass("hidden");
    var figli = $("#" + nomeMU + "list").children();
    $(".aggr").attr("disabled", false).removeClass("btn-default").addClass("btn-primary");
    $(".aggr").removeClass("hidden");
    $(".confermaMash").addClass("hidden");
    $(".modificaMash").removeClass("hidden");
    $("#" + nomeMU + "conferma").removeClass("hidden");
    $("#" + nomeMU + "modifica").addClass("hidden");
    figli.each(function () {
        var id = $(this).attr("id");
        id = id.replace(nomeMU + "_", "");
        $("#DIV" + id).find(".aggr").attr("disabled", true).removeClass("btn-primary").addClass("btn-default");
    });
}

function confermaMashUp(nomeMU) {
    $("#idMash").val(-1);
    $("#nomeMash").val("");
    $("#collapse" + nomeMU).collapse("hide");
    $("#" + nomeMU + "conferma").addClass("hidden");
    $("#" + nomeMU + "modifica").removeClass("hidden");
    $(".delAggr").addClass("hidden");
    $(".aggr").addClass("hidden");
}




/*
 function eliminaAggregazione(nomeMU) {
 var nameAgg = ($("#nameAgg").val());
 var nomeDesc = ($("#descrizioneAgg").val());
 // var url = "ActionServlet?op=meshup&nameAgg=" + nameAgg + "&descrizioneAgg=" + nomeDesc; //+ "&param=" + param;
 // window.location.replace(url);
 // nomeAggregazCreata = response.toString();
 $.post("ActionServlet", {"op": "meshup", "nameAgg": nameAgg, "descrizioneAgg": nomeDesc},
 function (response) {
 nomeAggregazCreata = response.toString();
 location.reload();
 }, "text");
 }*/


function aggiungiDataService(idDataService) {
    idAggregazione = mashSel;
    //$("#delAggr" + idDataService).removeClass("hidden");
    $("#addAggr" + idDataService).attr("disabled", true).removeClass("btn-primary").addClass("btn-default");
    $.post("ActionServlet", {"op": "addDStoMeshUp", "idDataService": idDataService, "idAggr": idAggregazione},
    function (response) {
        vett = [];
        vett = response.toString().split(" ");
        tag = "<li id=" + vett[2].trim() + "_" + vett[1].trim() + " class=list-group-item>" + vett[0].trim() + "</li>";
        $("#" + vett[2].trim() + "list").append(tag);
        html = "<button type='button' id='delAggr" + idDataService + "' onclick='rimuoviDataService(" + idDataService + ")' class='delAggr glyphicon glyphicon-remove pull-right btnTransparent'></button>";
        $("#" + vett[2].trim() + "_" + vett[1].trim()).append(html);
        $("#no" + vett[2].trim()).addClass("hidden");
    }, "text");
}


function rimuoviDataService(idDataService) {
    idAggregazione = mashSel;
    $("#addAggr" + idDataService).attr("disabled", false).removeClass("btn-default").addClass("btn-primary");
    $.post("ActionServlet", {"op": "deleteDStoMeshUp", "idDataService": idDataService, "idAggr": idAggregazione},
    function (response) {
        vett = [];
        vett = response.toString().split(" ");
        // window.alert("#"+vett[1].trim()+"+"+vett[0].trim());     
        $("#" + vett[1].trim() + "_" + vett[0].trim()).remove();
        if (vett[2].trim() === "empty") {
            $("#no" + vett[1].trim()).removeClass("hidden");
        }
    }, "text");
}


function confermaRegistrazione(Stringa) {
    var pass = $("#password").val();
    var usern = $("#username").val();
    var passconf = $("#password_confirm").val();
    $("#welcDiv").text(Stringa + " " + usern.toUpperCase());
    $("#modalRegistration").modal("show");
    setTimeout(function () {
        $.post("ActionServlet", {"op": "registrazione", "username": usern, "password": pass, "password_confirm": passconf},
        function (response) {
            window.location.replace("login.jsp")
        }, "text");
    }, 3000);
}



/*
 function aggiungiTag(iddataservice) {
 $.post("ActionServlet", {"op": "addTag", "idDataService": iddataservice},
 function (response) {
 }
 }, "text");
 }
 */

