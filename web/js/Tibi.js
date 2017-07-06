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


var nomeAggregazCreata;
var xhr;
function creaAggregazione()
{
    if ($("#nameAgg").val() === "") {
        alert("nome mancante");
        $("#inputAgg").addClass("has-error");
    } else {
        var nameAgg = ($("#nameAgg").val());
        var nomeDesc = ($("#descrizioneAgg").val());
       // var url = "ActionServlet?op=meshup&nameAgg=" + nameAgg + "&descrizioneAgg=" + nomeDesc; //+ "&param=" + param;
       // window.location.replace(url);
       // nomeAggregazCreata = response.toString();
         $.post("ActionServlet", {"op":"meshup", "nameAgg": nameAgg, "descrizioneAgg": nomeDesc},
        function(response){
           nomeAggregazCreata = response.toString();
           location.reload();
        }, "text");
        
        
        
        /* $.get("ActionServlet", {"op": "meshup", "nameAgg": nameAgg, "descrizioneAgg": nomeDesc},
         function (response) {
         console.log(response);
         if (param === "si") {
         window.alert(response.toString());
         $(".aggr").removeClass("hidden");
         $("#btnCreaMeshup").attr("disabled", true);
         /*    $("#confirmMeshup").removeClass("hidden");
         $("#confirmMeshup").click(function () {
         aggiungiArrayDS(response.toString());
         });
         }
         else {
         $("#confirmMeshup").click(function () {
         aggiungiArrayDS(response.toString());
         alert("Hai creato un mesh-up vuoto");
         });   */
    }
}
//}, "text");
//}


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


function apriWizard() {
    div = $("#creazioneAggr");
    if (div.hasClass("hidden")) {
        $(".mostra").toggleClass("hidden");
    }
    $("#nameAgg").val("");
    $("#descrizioneAgg").val("");
    $("#meshup-modal").modal("show");
}


function aggiungiArrayDS(idDataService, idAggregazione) {
    $("#delAggr" + idDataService).removeClass("hidden");
    $("#addAggr" + idDataService).addClass("hidden");
    $("#btnCreaMeshup").attr("enabled", true);
    var url = "ActionServlet?op=addDStoMeshUp&idDataService=" + idDataService + "&idAggr=" + idAggregazione;
    window.location.replace(url);
}

function rimuoviArrayDS(idDataService, idAggregazione) {
    $("#delAggr" + idDataService).addClass("hidden");
    $("#addAggr" + idDataService).removeClass("hidden");
    $("#btnCreaMeshup").attr("enabled", true);
    var url = "ActionServlet?op=deleteDStoMeshUp&idDataService=" + idDataService + "&idAggr=" + idAggregazione;
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
 }
 }
 */

/*
 function rimuoviArrayDS(idDataService, idAggregazione) {
 $("#delAggr" + idDataService).addClass("hidden");
 $("#addAggr" + idDataService).removeClass("hidden");
 dataService.pop(idDataService);
 }*/


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



function modificaMashUp(nomeMU) {
    var figli = $("#"+nomeMU+"list").children();
    $(".aggr").removeClass("hidden");
    $(".confermaMash").addClass("hidden");
    $(".modificaMash").removeClass("hidden");
    $("#"+nomeMU+"conferma").removeClass("hidden");
    $("#"+nomeMU+"modifica").addClass("hidden");
    figli.each(function() {
        var id = $(this).attr("id");
        id = id.replace(nomeMU+"+","");
        $("#DIV"+id).find(".delAggr").removeClass("hidden");
        $("#DIV"+id).find(".aggr").addClass("hidden");
        
    });
}

function confermaMashUp(nomeMU) {
    $("#"+nomeMU+"conferma").addClass("hidden");
    $("#"+nomeMU+"modifica").removeClass("hidden");
    $(".delAggr").addClass("hidden");
    $(".aggr").addClass("hidden");
}

function eliminaAggregazione(nomeMU) {

}

