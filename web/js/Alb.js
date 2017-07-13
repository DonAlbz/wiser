/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var errorUser;
var errorPass;
var passOk;
function loadVars(msg1, msg2, msg3) {
    errorUser = msg1;
    errorPass = msg2;
    passOk = msg3;
}


function alb() {



    $("#username").blur(function () {

        patt1 = /^[A-Za-z]/ig;
        patt2 = /[^A-Za-z0-9]/gi;
        if (patt1.test($(this).val()) && !patt2.test($(this).val()) && ($(this).val().length < 16)) {
            controlloUsername($(this).val());
        }
        else {
            $("#form-user").removeClass("has-success");
            $("#form-user").addClass("has-error");
            $("#user-text").html(errorUser);
        }

        $("#password").blur(
                function () {
                    pwd1 = $(this).val();
                    if (pwd1.length > 3) {
                        $("#pwd-text").html(passOk);
                        $("#form-pwd").removeClass("has-error");
                        $("#form-pwd").addClass("has-success");
                    }
                    else {
                        $("#pwd-text").html(errorPass);
                        if (pwd1 !== "") {
                            $("#form-pwd").addClass("has-error");
                        }
                    }
                });

        $("#password_confirm").blur(
                function () {
                    pwd1 = $("#password").val();
                    pwd2 = $(this).val();
                    if (pwd1 !== pwd2) {
                        $("#form-pwd-confirm").addClass("has-error");

                    }
                });
        // $(this).css("background-color","#0A5B68");
    });




}



function controlloUsername(username) {

    $.post("ActionServlet", {"op": "controlloUsername", "username": username},
    function (response) {

        if (response.trim() === "false") {
            $("#form-user").removeClass("has-error");
            $("#form-user").addClass("has-success");
            $("#user-text").html("Username accettable");
        }
        else {
            $("#form-user").removeClass("has-success");
            $("#form-user").addClass("has-error");
            $("#user-text").html("Username already taken, please choose another one!");

        }
        confrontaPassword();
    }, "text");

}
