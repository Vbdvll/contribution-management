<%@ page import="com.association.model.Utilisateur" %>
<%
    Utilisateur utilisateur =
            (Utilisateur) session.getAttribute("utilisateurConnecte");

    if (utilisateur == null) {
        response.sendRedirect(request.getContextPath() + "/login");
    } else if (utilisateur.getRole() == Utilisateur.Role.ADMIN) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    } else {
        response.sendRedirect(request.getContextPath() + "/membre/dashboard");
    }
%>
