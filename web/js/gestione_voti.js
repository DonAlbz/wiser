var votoSel;// e' il voto selezionato dall'utente


function selezione(nome_modal) {
    var votiNum = {"0.0": "POOR", "0.125": "MARGINAL", "0.25": "FAIR", "0.375": "SATISFACTORY", "0.5": "GOOD", "0.625": "VERY GOOD",
        "0.75": "EXCELLENT", "0.875": "OUTSTANDING", "1.0": "EXCEPTIONAL"};
    // nome_modal è l'id del dataService

    $(".confermaVoto").attr("disabled", true);
    $("ol").selectable({
        selected: function (event, ui) {
            $(ui.selected).addClass('ui-selected').siblings().removeClass('ui-selected');
        },
        stop: function () {
            $(".confermaVoto").attr("disabled", true);
            $(".ui-selected", this).each(function () {
                votoSel = $(".ui-selected").children().first().html();
                $(".votoScelto").html(votiNum[votoSel]);
                if (votoSel !== "") {
                    $(".confermaVoto").attr("disabled", false);
                }
            });
        }
    });
}
;

function modal2() {
    // nome_modal + l'id del DataService
    // mostro pagina2 modal
    $(".btnIndietro").removeClass("hidden");
    $(".modalConfermaVoto").removeClass("hidden");
    $(".confermaButton").removeClass("hidden");
    $(".msgModal").removeClass("hidden");
    // nascondo pagina1 modal
    $(".dialogo").addClass("hidden");
    $(".confermaVoto").addClass("hidden");
}
;

function confermaVoto(id_s) {

    var jsonAgg;

    no = false;
    $(".noBtn").each(function (index) {
        if ($(this).hasClass("disabled")) {
            jsonAgg = "";
            no = true;//e' stato selezionato
        }
    });

    var json = [];
    var json2 = {};

    if (!no) {//si' e' stato selezionato

        var $str;
        var $boxes = $('input[name=inputCk]:checked');

        $boxes.each(function () {
            $str = $(this).next("label").html().trim(); // prende i titoli
            json.push({nome: $str});
        });
        json2.aggregazioni = json;

        jsonAgg = JSON.stringify(json2);  //stringa
        //window.alert(jsonAgg);
    }

    $.post("ActionServlet", {"op": "addVoto", "votaS": id_s, "voto": votoSel, "aggregazioni": jsonAgg},
            function (response) {
                //alert('sono in response: ' + response.toString());
                if (response.toString() === "true") {
                    $(".feedbackVoto").html(" <h3> Il tuo voto &egrave; stato registrato con successo!</h3>");
                }
                if (response.toString() === "aggrOk") {
                    $(".feedbackVoto").html(" <h3> Il tuo voto &egrave; stato registrato con successo!</h3>");
                } else {
                    $(".feedbackVoto").html("<h3>Errore: riprovare pi&ugrave; tardi!</h3>");
                }

            }, "text");

    $(".modalConfermaVoto").addClass("hidden");
    $(".feedbackVoto").removeClass("hidden");
    $(".confermaButton").addClass("disabled");
    $(".listaAggregazioni").addClass("hidden");


}

function abilitaConfermaVoto() {
    $(".confermaButton").removeClass("disabled");

}

function noA(id_s) {
    $(".noBtn").addClass("disabled");
    $(".yesBtn").removeClass("disabled");
    $(".listaAggregazioni").addClass("hidden");
    $(".listaAggregazioni").empty();
    abilitaConfermaVoto();
    $(".btnIndietro").addClass("hidden");
}


function si(id_s) {
    $(".yesBtn").addClass("disabled");
    $(".noBtn").removeClass("disabled");
    if ($(".listaAggregazioni").html().trim() === '') {
        visAggr(id_s);
        abilitaConfermaVoto();
    }
    $(".listaAggregazioni").removeClass("hidden");
    $(".btnIndietro").addClass("hidden");
}

var xhr;

function visAggr(id_s) {
    var url = "ActionServlet?op=aggregationByDS&idDS=" + id_s;
    xhr = myGetXmlHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = insertCheck;
    xhr.send(null);
    console.log(xhr);

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
        // poi come oggetto ActiveX dal pi� al meno recente
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


function insertCheck() {

    if (xhr.readyState === 4)
    {
        var ajaxResp = xhr.responseText;
        var jo = JSON.parse(ajaxResp);
        console.log(jo);

        for (var i = 0; i < jo.aggregazioni.length; i++)
        {
            insertRadioButton(jo.aggregazioni[i].nome);
        }
    }
}

function insertRadioButton(name) {
    $(".listaAggregazioni").append('<li class="checkbox  checkbox-primary ui-widget-content"><input name="inputCk" class="checkInput" type="checkbox"> <label>' + name + '</label></div>');
    //$(".listaAggregazioni").append('<label for="inputCk" class="ui-checkboxradio-label ui-corner-all\n\
 //ui-button ui-widget"><span class="ui-checkboxradio-icon ui-corner-all ui-icon ui-icon-background ui-icon-blank ui-checkboxradio-checked ui-state-active>\n\
//</span><span class="ui-checkboxradio-icon-space"> </span>' + name +
  //          '</label><input name="inputCk" id="inputCk" class="checkInput ui-checkboxradio ui-helper-hidden-accessible" type="checkbox"> <br>');

}

//$(function () {
//    $(".checkInput").checkboxradio();
//});

$(".closeButton").click(chiudiModal);

$(".closeButtonX").click(chiudiModal);

function chiudiModal() {
    $(".btnIndietro").addClass("hidden");
    $(".ui-widget-content").removeClass("ui-selected");
    $(".listaAggregazioni").html('');
    $(".modalConfermaVoto").addClass("hidden");
    $(".confermaButton").addClass("hidden").addClass("disabled");
    $(".listaAggregazioni").addClass("hidden");
    $(".msgModal").addClass("hidden");
    $(".noBtn").removeClass("disabled");
    $(".yesBtn").removeClass("disabled");
    $(".feedbackVoto").addClass("hidden");
    setTimeout(function () {
        $(".dialogo").removeClass("hidden");
        $(".confermaVoto").removeClass("hidden");
    }, 1000);
}
;

function indietro() {
    // nascondo pagina2 modal
    $(".btnIndietro").addClass("hidden");
    $(".modalConfermaVoto").addClass("hidden");
    $(".confermaButton").addClass("hidden");
    $(".msgModal").addClass("hidden");
    // mostro pagina1 modal
    $(".dialogo").removeClass("hidden");
    $(".confermaVoto").removeClass("hidden");
}