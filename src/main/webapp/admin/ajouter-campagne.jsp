<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="campagnes" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Créer une campagne</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header">
            <div>
                <h1>Créer une campagne</h1>
                <p>Définir les règles et le mode de participation.</p>
            </div>
        </header>
        <section class="content-panel form-panel">
            <c:if test="${not empty erreur}">
                <div class="alert alert-danger"><c:out value="${erreur}"/></div>
            </c:if>
            <form action="${pageContext.request.contextPath}/admin/campagnes/ajouter" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <div class="mb-3">
                    <label class="form-label">Titre</label>
                    <input type="text" name="titre" class="form-control" maxlength="150"
                           placeholder="Ex : Cotisation mensuelle Mai" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Montant à payer</label>
                    <input type="number" name="montant" class="form-control"
                           min="0.01" step="0.01" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Fréquence</label>
                    <select name="frequence" class="form-select" required>
                        <option value="JOURNALIER">Journalier</option>
                        <option value="HEBDOMADAIRE">Hebdomadaire</option>
                        <option value="MENSUEL">Mensuel</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Participation</label>
                    <select name="typeParticipation" class="form-select" required>
                        <option value="OBLIGATOIRE">Obligatoire pour tous les membres actifs</option>
                        <option value="SUR_INSCRIPTION">Sur inscription avec un code</option>
                    </select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Date début</label>
                    <input type="date" name="dateDebut" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Date fin</label>
                    <input type="date" name="dateFin" class="form-control">
                </div>
                <div class="form-check mb-4">
                    <input class="form-check-input" type="checkbox" name="retardTolere"
                           id="retardTolere" checked>
                    <label class="form-check-label" for="retardTolere">
                        Autoriser les paiements en retard
                    </label>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn-app">Créer la campagne</button>
                    <a href="${pageContext.request.contextPath}/admin/campagnes"
                       class="btn-secondary-app">Annuler</a>
                </div>
            </form>
        </section>
    </main>
</div>
</body>
</html>
