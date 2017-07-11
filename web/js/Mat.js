
function confrontaPassword()
{
    var send = true;
    if (($("#username").val() == null) || ($("#username").val() == "") || ($("#username").val() == "null"))
    {
        console.log("userVuoto");
        send = false;
    }
    if (($("#password").val() == null) || ($("#password").val() == "") || ($("#password").val() == "null"))
    {
        console.log("pwdVuota");
        send = false;
    }
    if (($("#password_confirm").val() == null) || ($("#password_confirm").val() == "") || ($("#password_confirm").val() == "null"))
    {
        console.log("pwdCvuota");
        send = false;
    }
    if(send == true)
    {
        if($("#form-user").hasClass("has-error"))
        {
           console.log("username");
            send = false;
        }
    }
     if(send == true)
    {
        if($("#password").val() !== $("#password_confirm").val())
        {
            console.log("pwdDiverse");
            send = false;
        }
    }
    
    if (send == true)
    {
        $("#daAbilitare").removeClass("disabled");
    }
    else
    {
        $("#daAbilitare").addClass("disabled");
    }
}


$('#centra').on('keyup keypress', function(e) {
  var keyCode = e.keyCode || e.which;
  if (keyCode === 13) { 
    e.preventDefault();
    return false;
  }
});

function selezionaCategoria(filtro) {
    var $filter = filtro.trim();
    var $ciao;
    $(".categorie").each(function () {
        $ciao = $(this).text().trim();
        if ($ciao === $filter) {

            $(this).css("background", "#286090");
            $(this).css("color", "white");

        }

    });
}
;

function selectMash(nomeMU, idMU)
{
    console.log(nomeMU, idMU);
    if ((nomeMU != null) && (nomeMU != "") && (nomeMU != "null"))
    {
        console.log("if");
        modificaMashUp(nomeMU, idMU);
    }
    else
    {
        console.log("else");
        $("#idMash").val(-1);
        $("#nomeMash").val("null");
        console.log($("#idMash").val());
    }
}

function doHref(href)
{
    var nome = $("#nomeMash").val();
    var id = $("#idMash").val();
    href = href + "&idMash=" + id + "&nomeMash=" + nome;
    var toRet = href.toString().replace(/\|\!/gi, "\'");
    console.log(toRet);
    window.location.replace(toRet);
}



