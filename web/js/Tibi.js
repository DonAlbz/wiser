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
    }
    else if (id.valueOf() == "auto2") {
        $("#auto0").val($("#auto2").text());
    }
    else if (id.valueOf() == "auto3") {
        $("#auto0").val($("#auto3").text());
    }
    else if (id.valueOf() == "auto4") {
        $("#auto0").val($("#auto4").text());
    }
}

function modalAggregazioniPag2() {
    $(".modalScegliDS").removeClass("hidden");
    $(".formAggr").addClass("hidden");
}



var xhr;
function creaAggregazione(param)
{
    var nameAgg = ($("#nameAgg").val());
    var nomeDesc = ($("#descrizioneAgg").val());
    $.get("ActionServlet", {"op": "meshup", "nameAgg": nameAgg, "descrizioneAgg": nomeDesc},
    function (response) {
        console.log(response);
        if (param === "si") {
            window.alert(response.toString());
            $(".aggr").removeClass("hidden");
            $("#btnCreaMeshup").attr("disabled", true);
            $("#confirmMeshup").removeClass("hidden");

            $("#confirmMeshup").click(function () {
                aggiungiArrayDS(response.toString());
            })
            //$("#form-user").addClass("has-success");
            //$("#user-text").html("Username accettable");
        }
        else {
            $("#confirmMeshup").click(function () {
                aggiungiArrayDS(response.toString());
                alert("Hai creato un mesh-up vuoto");
            })
        }
    }, "text");
}


function myGetXmlHttpRequest()
{
    var XmlHttpReq = false;
    var activeXopt = new Array("Microsoft.XmlHttp", "MSXML4.XmlHttp", "MSXML3.XmlHttp", "MSXML2.XmlHttp", "MSXML.XmlHttp");
    // prima come oggetto nativo
    try
    {
        XmlHttpReq = new XMLHttpRequest();
    }
    catch (e)
    {
        // poi come oggetto ActiveX dal pi? al meno recente
        var created = false;
        for (var i = 0; i < activeXopt.length && !created; i++)
        {
            try
            {
                XmlHttpReq = new ActiveXObject(activeXopt[i]);
                created = true;
            }
            catch (eActXobj)
            {
                alert("Il tuo browser non supporta AJAX!");
            }
        }
    }
    return XmlHttpReq;
}


function paginaConferma() {
    if ($("#nameAgg").val() === "") {
        alert("nome mancante");
        $("#inputAgg").addClass("has-error");
    } else {

        //$("#descrizioneAgg")
        $(".mostra").toggleClass("hidden");
    }
}


function apriWizard() {
    div = $("#creazioneAggr");
    if (div.hasClass("hidden")) {
        $(".mostra").toggleClass("hidden");
    }
    $("#nameAgg").val("");
    $("#descrizioneAgg").val("");
    $("#meshup-modal").modal("show");
}

var dataService = [];
var idAggregazione;

function aggiungiDS(idDataService) {
    $("#delAggr" + idDataService).removeClass("hidden");
    $("#addAggr" + idDataService).addClass("hidden");
    dataService.push(idDataService);
}

function aggiungiArrayDS(idAggregazione) {
    alert(dataService.toString());
    $("#btnCreaMeshup").attr("enabled", true);
    if (dataService.length === 0) {
        alert("Non hai inserito alcun data service");
    }
    else {
        var url = "ActionServlet?op=addDStoMeshUp&idDataService=" + dataService + "&idAggr=" + idAggregazione;
        window.location.replace(url);
    }



    //$.get("ActionServlet", {"op": "addDStoMeshUp", "idDataService": dataService, "idAggr" : idAggregazione},
    /*   function (response) {
     if (response.toString()!=""){
     window.alert(response.toString());
     }
     else{
     window.alert("niente");
     }
     console.log(response);
     }, "text");
     }*/
}

function rimuoviDS(idDataService) {
    $("#delAggr" + idDataService).addClass("hidden");
    $("#addAggr" + idDataService).removeClass("hidden");
    dataService.pop(idDataService);
}