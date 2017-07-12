var votoSel;// e' il voto selezionato dall'utente

function apriModalVoto() {

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
    $(".dialogo").removeClass("hidden");
    $(".confermaVoto").removeClass("hidden");

}
function selezione(nome_modal) {

    apriModalVoto();

    var votiNum = {"0.0": "POOR", "0.125": "MARGINAL", "0.25": "FAIR", "0.375": "SATISFACTORY", "0.5": "GOOD", "0.625": "VERY GOOD",
        "0.75": "EXCELLENT", "0.875": "OUTSTANDING", "1.0": "EXCEPTIONAL"};
    // nome_modal Ã¨ l'id del dataService


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
    $(".confermaButton").addClass("disabled");
    $(".btnIndietro").removeClass("hidden");
    $(".modalConfermaVoto").removeClass("hidden");
    $(".confermaButton").removeClass("hidden");
    $(".msgModal").removeClass("hidden");
    // nascondo pagina1 modal
    $(".dialogo").addClass("hidden");
    $(".confermaVoto").addClass("hidden");
}


function confermaVoto(id_s, msg1, msg2) {

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
        var $boxes = $('li[name=inputCk].active');

        $boxes.each(function () {
            $str = $(this).text(); // prende i titoli

            json.push({nome: $str});
        });
        json2.aggregazioni = json;

        jsonAgg = JSON.stringify(json2);  //stringa
        //window.alert(jsonAgg);
    }

    $.post("ActionServlet", {"op": "addVoto", "votaS": id_s, "voto": votoSel, "aggregazioni": jsonAgg},
            function (response) {
                var verifica = false;
                //alert('sono in response: ' + response.toString());
                if (response.toString().trim() === "true") {
                    $(".feedbackVoto").html(" <h3>" + msg1 + "</h3>");
                    verifica = true;
                }

                if (response.toString().trim() === "okAggr") {
                    $(".feedbackVoto").html(" <h3>" + msg1 + "</h3>");
                    verifica = true;
                }
                if (verifica === false) {
                    $(".feedbackVoto").html(" <h3>" + msg2 + "</h3>");
                }

            }, "text");

    $(".btnIndietro").addClass("hidden");
    $(".modalConfermaVoto").addClass("hidden");

    setTimeout(function () {
        $(".feedbackVoto").removeClass("hidden");
    }, 1000);

    $(".confermaButton").addClass("disabled");

    $(".listaAggregazioni").addClass("hidden");

}

function abilitaConfermaVoto() {
    $(".confermaButton").removeClass("disabled");
}


function insertRadioButton(name) {
    $(".listaAggregazioni").append('<li class="list-group-item checkListModal" name=inputCk>' + name + '</li>');
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
    checkboxList();
    $(".listaAggregazioni").removeClass("hidden");

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
        // poi come oggetto ActiveX dal piï¿½ al meno recente
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


function noA(id_s) {
    $(".noBtn").addClass("disabled");
    $(".yesBtn").removeClass("disabled");
    $(".listaAggregazioni").addClass("hidden");
    $(".listaAggregazioni").empty();
    abilitaConfermaVoto();

}


function si(id_s) {

    $(".yesBtn").addClass("disabled");

    $(".noBtn").removeClass("disabled");
    if ($(".listaAggregazioni").html().trim() === '') {
        visAggr(id_s);
        abilitaVotaAggr();
    }
}


function abilitaVotaAggr(){
    if (cercaCheckedLi()) {
            abilitaConfermaVoto();
        } else {
            $(".confermaButton").addClass("disabled");
        }
}

function cercaCheckedLi() {
    check = false;
    $('li[name=inputCk]').each(function () {
        if ($(this).hasClass('active')) {
            check = true;
        }
    });

    return check;

}



function indietro() {
    // nascondo pagina2 modal
    $(".btnIndietro").addClass("hidden");
    $(".modalConfermaVoto").addClass("hidden");
    $(".confermaButton").addClass("hidden");
    $(".msgModal").addClass("hidden");
    $(".listaAggregazioni").html('');
    $(".noBtn").removeClass("disabled");
    $(".yesBtn").removeClass("disabled");
    // mostro pagina1 modal
    $(".dialogo").removeClass("hidden");
    $(".confermaVoto").removeClass("hidden");

}

function checkboxList() {
    $('.list-group.checked-list-box .list-group-item').each(function () {

        // Settings
        var $widget = $(this),
                $checkbox = $('<input type="checkbox" class="hidden" />'),
                color = ($widget.data('color') ? $widget.data('color') : "#428bca"),
                style = ($widget.data('style') === "button" ? "btn-" : "list-group-item-"),
                settings = {
                    on: {
                        icon: 'glyphicon glyphicon-check'
                    },
                    off: {
                        icon: 'glyphicon glyphicon-unchecked'
                    }
                };

        $widget.css('cursor', 'pointer');
        $widget.append($checkbox);

        // Event Handlers
        $widget.on('click', function () {
            $checkbox.prop('checked', !$checkbox.is(':checked'));
            $checkbox.triggerHandler('change');
            updateDisplay();
        });
        $checkbox.on('change', function () {
            updateDisplay();
        });


        // Actions
        function updateDisplay() {
            var isChecked = $checkbox.is(':checked');

            // Set the button's state
            $widget.data('state', (isChecked) ? "on" : "off");

            // Set the button's icon
            $widget.find('.state-icon')
                    .removeClass()
                    .addClass('state-icon ' + settings[$widget.data('state')].icon);

            // Update the button's color
            if (isChecked) {
                $widget.addClass(style + color + ' active');
            } else {
                $widget.removeClass(style + color + ' active');
            }
        }

        // Initialization
        function init() {

            if ($widget.data('checked') === true) {
                $checkbox.prop('checked', !$checkbox.is(':checked'));
            }

            updateDisplay();

            // Inject the icon if applicable
            if ($widget.find('.state-icon').length === 0) {
                $widget.prepend('<span class="state-icon ' + settings[$widget.data('state')].icon + '"></span>');
            }
        }
        init();
    });

    $('#get-checked-data').on('click', function (event) {
        event.preventDefault();
        var checkedItems = {}, counter = 0;
        $(".check-list-box li.active").each(function (idx, li) {
            checkedItems[counter] = $(li).text();
            counter++;
        });
        $('#display-json').html(JSON.stringify(checkedItems, null, '\t'));
    });
}
;

