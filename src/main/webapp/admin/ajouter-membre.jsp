<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="activePage" value="membres" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ajouter un membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header"><div><h1>Ajouter un membre</h1><p>Créer sa fiche et son compte de connexion.</p></div></header>
        <section class="content-panel form-panel">
            <c:if test="${not empty erreur}"><div class="alert alert-danger"><c:out value="${erreur}"/></div></c:if>
            <form action="${pageContext.request.contextPath}/admin/membres/ajouter" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <div class="mb-3"><label class="form-label">Numéro membre</label><input type="text" name="numero" class="form-control" maxlength="50" value="${fn:escapeXml(param.numero)}" required></div>
                <div class="mb-3"><label class="form-label">Prénom</label><input type="text" name="prenom" class="form-control" maxlength="100" value="${fn:escapeXml(param.prenom)}" required></div>
                <div class="mb-3"><label class="form-label">Nom</label><input type="text" name="nom" class="form-control" maxlength="100" value="${fn:escapeXml(param.nom)}" required></div>
                <div class="mb-3"><label class="form-label">Email de connexion</label><input type="email" name="email" class="form-control" maxlength="100" value="${fn:escapeXml(param.email)}" required></div>
                <div class="mb-3"><label class="form-label">Date de naissance</label><input type="date" name="dateNaissance" class="form-control" value="${fn:escapeXml(param.dateNaissance)}" required></div>
                <div class="mb-3"><label class="form-label">Date d'adhésion</label><input type="date" name="dateAdhesion" class="form-control" value="${empty param.dateAdhesion ? localDate : fn:escapeXml(param.dateAdhesion)}" required></div>
                <div class="mb-3"><label class="form-label">Mot de passe temporaire</label><input type="password" name="motDePasse" class="form-control" minlength="8" maxlength="72" required></div>
                <div class="form-actions">
                    <button type="submit" class="btn-app">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/admin/membres" class="btn-secondary-app">Annuler</a>
                </div>
            </form>
        </section>
    </main>
</div>
</body>
</html>
