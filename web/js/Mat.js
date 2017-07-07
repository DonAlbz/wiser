function selezionaCategoria(filtro){
    var $filter=filtro.trim();
    var $ciao;
    $(".categorie").each(function(){
        $ciao= $(this).text().trim();
        if( $ciao === $filter){
            
            $(this).css("background","#2d2f2d");
            $(this).css("color","white");
            
        }

    });      
};


