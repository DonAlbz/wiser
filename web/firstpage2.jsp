<% 
    String nome_utente = (String)request.getAttribute("nome");
    
    String redirectURL = "ActionServlet?op=getList2&nomeU="+nome_utente;
   
    response.sendRedirect(redirectURL);
%>