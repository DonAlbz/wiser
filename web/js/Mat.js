function selectOrdinamento(stringa)
{
    if(stringa == "nome")
    {
        $("#ordNome").removeClass("hidden");
    }
    if(stringa == "utilizziMax")
    {
        $("#ordUsiMax").removeClass("hidden");
    }
    if(stringa == "utilizziMin")
    {
        $("#ordUsiMin").removeClass("hidden");
    }
    if(stringa == "votoMax")
    {
        $("#ordRatMax").removeClass("hidden");
    }
    if(stringa == "votoMin")
    {
        $("#ordRatMin").removeClass("hidden");
    }
}
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
    if (send == true)
    {
        if ($("#form-user").hasClass("has-error"))
        {

            send = false;
        }
    }
    if (send == true)
    {
        if ($("#password").val() !== $("#password_confirm").val())
        {
            $("#form-pwd").addClass("has-error");
            $("#form-pwd-confirm").addClass("has-error");
            $("#pwd-confirm-text").html("This is not the same password");
            send = false;
        }
        else
        {
            $("#pwd-confirm-text").html("Password confirmed");
            $("#form-pwd").addClass("has-success");
            $("#form-pwd-confirm").addClass("has-success");
            $("#form-pwd").removeClass("has-error");
            $("#form-pwd-confirm").removeClass("has-error");
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

$('#centra').on('keyup keypress', function (e) {
    var keyCode = e.keyCode || e.which;
    if (keyCode === 13) {
        e.preventDefault();
        return false;
    }
});

    function selezionaCategoriaUt(filtro){
    var $filter=filtro.trim();
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



function selezionaCategoria(filtro){
    var $filter=filtro.trim();
    var $ciao;
    $(".categorie").each(function(){
        $ciao= $(this).text().trim();
        if( $ciao === $filter){
            
            $(this).css("background","black");
            $(this).css("color","white");
            
        }

    });      
};
