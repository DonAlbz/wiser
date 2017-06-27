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
        $("#auto0").val($("#auto1").val());
    }
    else if (id.valueOf() == "auto2") {
        $("#auto0").val($("#auto2").val());
    }
    else if (id.valueOf() == "auto3") {
        $("#auto0").val($("#auto3").val());
    }
    else if (id.valueOf() == "auto4") {
        $("#auto0").val($("#auto4").val());
    }
}


