var index;


function selezione(nome_modal) {
    // nome_modal è l'id del dataService
    $(".confermaVoto").attr("disabled", true);
    $("ol").selectable({
        stop: function () {
            $(".confermaVoto").attr("disabled", true);
            var result = $("#select-result" + nome_modal).empty();
            $(".ui-selected", this).each(function () {
                // index = $("ol" + " li").index(this);
                index = $(".ui-selected").children().first().html();
                result.append(index);
                if (index !== "") {
                    $(".confermaVoto").attr("disabled", false);
                }
            });
        }
    });
}
;
function ciao() {
    window.alert("ciao");
}
;
function modal2() {
    // nome_modal + l'id del DataService
    $(".modalConfermaVoto").removeClass("hidden");
    $(".confermaButton").removeClass("hidden");
    $(".dialogo").addClass("hidden");





}
;

function confermaVoto(id_s) {
    var indexVoto = index;
    var url = "ActionServlet?votaS=" + id_s + "&Si=" + indexVoto;
    $("#no").attr("href", url);
}

function abilitaConfermaVoto() {
    $(".confermaButton").removeClass("disabled");

}

function no(id_s) {
    abilitaConfermaVoto(id_s);
}

function si(id_s) {
    visAggr(id_s);
    abilitaConfermaVoto();
}

var xhr;

function visAggr(id_s) {
    var url = "ActionServlet?op=aggregationByDS&idDS=" + id_s;
    xhr = myGetXmlHttpRequest();
    xhr.open("GET", url, true);
    xhr.onreadystatechange = insertRB;
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


function insertRB() {
    
    if (xhr.readyState === 4)
    {
        var ajaxResp = xhr.responseText;
        var jo = JSON.parse(ajaxResp);
        console.log(jo);

        for (var i=0; i<jo.aggregazioni.length;i++)
        {
            insertRadioButton(jo.aggregazioni[i].nome);
        } 



    }
}


/*function visualizzaAggregazioni(id_s) {
    $.post("ActionServlet", {"op": "aggregationByDS", "idDS": id_s},
            function (response) {
                if (response == '')
                    alert("vuoto");
                console.log(response);
                var JSONresponse = JSON.parse(response);
                for (i = 1; i <= JSONresponse.aggregazioni.length; i++) {
                    insertRadioButton(JSONresponse.aggregazioni[i].nome);
                }
            }, "json");
}*/
function insertRadioButton(name) {
   // var button= $.html('<div class="radio"><label><input type="radio" name="optradio">' + name + '</label></div>');
    $(".listaAggregazioni").append('<div class="checkbox"> <label><input type="checkbox" value="">'+ name +'</label></div>');
}


$(".closeButton").click(function () {

    $(".modalConfermaVoto").addClass("hidden");
    $(".confermaButton").addClass("hidden").addClass("disabled");
    setTimeout(function () {
        $(".dialogo").removeClass("hidden");
    }, 1000);
});
//$(".modalToClose").on('hide.bs.modal', function () {
//    alert('The modal is about to be hidden.');
//});

//$(".modalConfermaVoto").addClass("hidden");
