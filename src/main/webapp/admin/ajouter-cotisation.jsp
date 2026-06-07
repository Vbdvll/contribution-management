<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="cotisations" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Enregistrer un paiement</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header"><div><h1>Enregistrer un paiement</h1><p>Ajouter une cotisation reçue pour un membre.</p></div></header>
        <section class="content-panel form-panel">
            <c:if test="${not empty erreur}"><div class="alert alert-danger"><c:out value="${erreur}"/></div></c:if>
            <form action="${pageContext.request.contextPath}/admin/cotisations/ajouter" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <div class="mb-3">
                    <label class="form-label">Membre</label>
                    <select name="membreId" class="form-select" required>
                        <c:forEach var="membre" items="${membres}">
                            <option value="${membre.id}"><c:out value="${membre.numero}"/> - <c:out value="${membre.prenom}"/> <c:out value="${membre.nom}"/></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Campagne</label>
                    <select name="campagneId" class="form-select" required>
                        <c:forEach var="campagne" items="${campagnes}">
                            <option value="${campagne.id}"><c:out value="${campagne.titre}"/> - ${campagne.montant} FCFA - ${campagne.frequence}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Mode de paiement</label>
                    <select name="modePaiement" class="form-select" required>
                        <option value="Espèces">Espèces</option><option value="Virement">Virement</option>
                        <option value="Chèque">Chèque</option><option value="Mobile Money">Mobile Money</option>
                    </select>
                </div>
                <div class="mb-3"><label class="form-label">Date échéance</label><input type="date" name="dateEcheance" class="form-control" required></div>
                <div class="form-actions">
                    <button type="submit" class="btn-app">Enregistrer</button>
                    <a href="${pageContext.request.contextPath}/admin/cotisations" class="btn-secondary-app">Annuler</a>
                </div>
            </form>
        </section>
    </main>
</div>
</body>
</html>
