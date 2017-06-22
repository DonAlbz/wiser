/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function alb(){

    
    $("#username").blur(function(){
       patt1=/^[A-Za-z]/ig;
       patt2=/[^A-Za-z0-9]/gi;
       if(patt1.test($(this).val())&& !patt2.test($(this).val())){
            controlloUsername($(this).val());
        }
        else{
             $("#form-user").removeClass("has-success");
             $("#form-user").addClass("has-error");
        }
     // $(this).css("background-color","#0A5B68");
    });
    

}

function controlloUsername(username){
    $.post("ActionServlet", {"op":"controlloUsername", "username": username},
        function(response){
           
            if(response.trim()==="false"){                
                $("#form-user").removeClass("has-error");
                $("#form-user").addClass("has-success");
            }
            else{
                
                $("#form-user").removeClass("has-success");
                $("#form-user").addClass("has-error");
            }
        }, "text");
    }
