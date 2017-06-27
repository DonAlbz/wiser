
var xhr;

function autocompl(stringa)
{
    if ((stringa.trim() !== null) && (stringa.trim() !== ""))
    {
        var url = "ActionServlet?op=autoc&s=" + stringa;
        xhr = myGetXmlHttpRequest();
        xhr.open("GET", url, true);
        xhr.onreadystatechange = inserisciSuggerimento;
        xhr.send(null);
    }
    else
    {
        $("#autocompl").css("display", "none");
        for (i = 1; i < 5; i++)
        {
            /*document.getElementById("auto" + i).setAttribute("hidden", "hidden");*/
            $("#auto" + i).css("display", "none");
           
        }
    }
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
        // poi come oggetto ActiveX dal pi� al meno recente
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

function inserisciSuggerimento() {
    if (xhr.readyState === 4)
    {
        var ajaxResp = xhr.responseText;
        var jo = JSON.parse(ajaxResp);
        for (var i = 1; i < 5; i++)
        {
            $("#autocompl").css("display","");
            if (i <= jo.suggerimenti.length)
            {
                /*document.getElementById("auto" + i).value = jo.suggerimenti[i - 1].nome;
                 document.getElementById("auto" + i).removeAttribute("hidden");*/
                getPosizione();
                $("#auto" + i).css("display", "");
                $("#auto" + i).text(jo.suggerimenti[i - 1].nome);
            }
            else
            {
                /*document.getElementById("auto" + i).setAttribute("hidden", "hidden");*/
                $("#auto" + i).css("display", "none");
            }
        }


    }
}

function getPosizione()
{
    var pos = $("#auto0").offset();
    var width = $("#auto0").width();
    var height = $("#auto0").height();
    $(".autoc").css("heigth", height);
    $("#autocompl").css("top", pos.top + height + 12);
    $("#autocompl").css("left", pos.left);
    $("#autocompl").css("width", width + 26);
}

