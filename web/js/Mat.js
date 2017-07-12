function selezionaCategoriaUt(filtro){
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
