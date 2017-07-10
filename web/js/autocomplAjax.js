var xhr;

function autocompl(stringa, e)
{
    if ((e.keyCode !== 38) && (e.keyCode !== 40)) {
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
                $("#auto" + i).text("");
            }
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

function inserisciSuggerimento() {
    if (xhr.readyState === 4)
    {
        var ajaxResp = xhr.responseText;
        var jo = JSON.parse(ajaxResp);
        console.log(ajaxResp);
        for (var i = 1; i < 5; i++)
        {
            $("#autocompl").css("display", "");
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
                $("#auto" + i).text("");
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

function arrowEnable(e)
{
    if (e.keyCode == 40)
    {
        if ($(".autoc-selected")[0])
        {
            var id = $(".autoc-selected").first().attr("id");
            var int = id.replace("auto", "");
            if ((int > 0) && (int < 4))
            {
                $("#auto" + int).toggleClass("autoc-selected");
                int = parseInt(int) + 1;
                if (($("#auto" + int)).text().toString() !== "")
                {
                    $("#auto" + int).toggleClass("autoc-selected");
                    $("#auto0").val($("#auto" + int).text());
                }
                else
                {
                    $("#auto1").toggleClass("autoc-selected");
                    $("#auto0").val($("#auto1").text());
                }
            }
            else
            {
                $("#auto" + int).toggleClass("autoc-selected");
                $("#auto0").val($("#auto" + int).text());
            }
        }
        else
        {
            $("#auto1").toggleClass("autoc-selected");
            $("#auto0").val($("#auto1").text());
        }
    }
    else if (e.keyCode == 38)
    {
        if ($(".autoc-selected")[0])
        {
            var id = $(".autoc-selected").first().attr("id");
            var int = id.replace("auto", "");
            if ((int > 1) && (int < 5))
            {
                $("#auto" + int).toggleClass("autoc-selected");
                int = parseInt(int) - 1;
                $("#auto" + int).toggleClass("autoc-selected");
                $("#auto0").val($("#auto" + int).text());
            }
            else
            {
                $("#auto" + int).toggleClass("autoc-selected");
                $("#auto0").val($("#auto" + int).text());
            }
        }
        else
        {
            $("#auto4").toggleClass("autoc-selected");
            $("#auto0").val($("#auto4").text());

        }
    }
    else if (e.keyCode == 13) {
        if ($(".autoc-selected")[0])
        {
            var id = $(".autoc-selected").first().attr("id");
            var daInviare = ($("#" + id)).text().toString();
            $("#auto0").val(daInviare);
        }
        parseSend();
    }
}

function parseSend()
{
    var key =$("#auto0").val();
    var send = false;
    if((key != null)&&(key != "")&&(key != "null"))
    {
        var parser = /[\s]+/;
        var array = key.split(parser);
        var index = 1;
        while(index > -1)
        {
            index = array.indexOf("");
            if(index != -1)
            {
              array.splice(index, 1);  
            }
        }
        if(array.length >0)
        {
            var joString = createJSONString(array);
            console.log(joString);
            send = true;
            var userMode = $("#userModeSearch").val();
            console.log(userMode);
            if((userMode != null)||(userMode == "true"))
            {
                var nomeUt = $("#userSearch").val();
                window.location.replace("ActionServlet?op=getList2&search="+joString+"&nomeU="+nomeUt);
            }
            else
            {
                 window.location.replace("ActionServlet?op=getList&search="+joString);
            }
           
        }
    }
    if(!send)
    {
        $("#auto0").val("");
    }
}

function createJSONString(array)
{
    var jo = [];
    for(var i=0; i<array.length; i++)
    {
        jo.push({
           nome: array[i]
        });
    }
    var toRet = {};
    toRet.keys = jo;
    return JSON.stringify(toRet);
}




