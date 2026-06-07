<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="activePage" value="membres" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Modifier un membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header"><div><h1>Modifier un membre</h1><p>Mettre à jour ses informations.</p></div></header>
        <section class="content-panel form-panel">
            <form action="${pageContext.request.contextPath}/admin/membres/modifier" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <input type="hidden" name="id" value="${membre.id}">
                <c:if test="${not empty erreur}"><div class="alert alert-danger"><c:out value="${erreur}"/></div></c:if>
                <div class="mb-3"><label class="form-label">Numéro</label><input type="text" name="numero" class="form-control" maxlength="50" value="${fn:escapeXml(membre.numero)}" required></div>
                <div class="mb-3"><label class="form-label">Prénom</label><input type="text" name="prenom" class="form-control" maxlength="100" value="${fn:escapeXml(membre.prenom)}" required></div>
                <div class="mb-3"><label class="form-label">Nom</label><input type="text" name="nom" class="form-control" maxlength="100" value="${fn:escapeXml(membre.nom)}" required></div>
                <div class="mb-3"><label class="form-label">Email</label><input type="email" name="email" class="form-control" maxlength="100" value="${fn:escapeXml(membre.utilisateur.email)}" required></div>
                <div class="mb-3"><label class="form-label">Date de naissance</label><input type="date" name="dateNaissance" class="form-control" value="${membre.dateNaissance}" required></div>
                <div class="mb-3"><label class="form-label">Date d'adhésion</label><input type="date" name="dateAdhesion" class="form-control" value="${membre.dateAdhesion}" required></div>
                <div class="mb-3">
                    <label class="form-label">Statut</label>
                    <select name="statut" class="form-select">
                        <option value="ACTIF" ${membre.statut == 'ACTIF' ? 'selected' : ''}>ACTIF</option>
                        <option value="INACTIF" ${membre.statut == 'INACTIF' ? 'selected' : ''}>INACTIF</option>
                    </select>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-app">Mettre à jour</button>
                    <a href="${pageContext.request.contextPath}/admin/membres" class="btn-secondary-app">Annuler</a>
                </div>
            </form>
        </section>
    </main>
</div>
</body>
</html>
