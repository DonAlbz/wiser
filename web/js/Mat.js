function selezionaCategoria(filtro){
    var $filter=filtro.trim();
    var $ciao;
    $(".categorie").each(function(){
        $ciao= $(this).text().trim();
        if( $ciao === $filter){
            
            $(this).css("background","#286090");
            $(this).css("color","white");
            
        }

    });      
};

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
    var nome= $("#nomeMash").val();
    var id = $("#idMash").val();
    href = href +"&idMash="+id+"&nomeMash="+nome;
    var toRet = href.toString().replace(/\|\!/gi, "\'");
    console.log(toRet);
    window.location.replace(toRet);
}



