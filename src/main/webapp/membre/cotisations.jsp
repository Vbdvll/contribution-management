<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.time.LocalDate" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="cotisations" scope="request"/>
<c:set var="today" value="<%= LocalDate.now() %>"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mes cotisations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/membre-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header"><div><h1>Mes cotisations</h1><p>Consulter vos échéances et effectuer vos paiements.</p></div></header>
        <c:if test="${not empty param.success}"><div class="alert alert-success"><c:out value="${param.success}"/></div></c:if>
        <c:if test="${not empty param.erreur}"><div class="alert alert-danger"><c:out value="${param.erreur}"/></div></c:if>
        <section class="content-panel campaign-code-panel">
            <div>
                <h2>Rejoindre une campagne</h2>
                <p class="text-muted mb-0">Saisissez le code transmis par l'administrateur.</p>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/membre/campagnes/rejoindre">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <label class="visually-hidden" for="code">Code de campagne</label>
                <input id="code" type="text" name="code" class="form-control"
                       maxlength="12" placeholder="COT-XXXXXXXX" required>
                <button type="submit" class="btn-app">
                    <i class="bi bi-box-arrow-in-right"></i> Rejoindre
                </button>
            </form>
        </section>
        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead><tr><th>Campagne</th><th>Montant</th><th>Fréquence</th><th>Échéance</th><th>Statut</th><th>Action</th></tr></thead>
                    <tbody>
                    <c:if test="${empty lignes}">
                        <tr>
                            <td colspan="6" class="text-muted text-center py-4">
                                Aucune cotisation à afficher.
                            </td>
                        </tr>
                    </c:if>
                    <c:forEach var="ligne" items="${lignes}">
                        <tr>
                            <td><c:out value="${ligne.campagne.titre}"/></td><td>${ligne.campagne.montant} FCFA</td>
                            <td>${ligne.campagne.frequence}</td><td>${ligne.dateEcheance}</td>
                            <td>
                                <span class="status-badge ${ligne.statut == 'PAYEE' ? 'status-success' : (ligne.statut == 'EN_ATTENTE' ? 'status-warning' : (ligne.statut == 'EN_RETARD' ? 'status-danger' : 'status-neutral'))}">
                                    ${ligne.statut}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${ligne.statut == 'PAYEE'}"><span class="text-muted">Déjà payée</span></c:when>
                                    <c:when test="${ligne.statut == 'EN_ATTENTE'}"><span class="text-muted">Ancien paiement en attente</span></c:when>
                                    <c:when test="${ligne.dateEcheance > today}"><span class="text-muted">Pas encore disponible</span></c:when>
                                    <c:when test="${ligne.statut == 'EN_RETARD' and !ligne.campagne.retardTolere}"><span class="text-muted">Retard non autorisé</span></c:when>
                                    <c:otherwise>
                                        <form method="post" action="${pageContext.request.contextPath}/membre/cotisations/payer"
                                              onsubmit="return confirm('Confirmer ce paiement simulé ?');">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <input type="hidden" name="campagneId" value="${ligne.campagne.id}">
                                            <input type="hidden" name="dateEcheance" value="${ligne.dateEcheance}">
                                            <button type="submit" class="${ligne.statut == 'EN_RETARD' ? 'btn-danger-app' : 'btn-app'} btn-small">
                                                ${ligne.statut == 'EN_RETARD' ? 'Payer avec retard' : 'Payer maintenant'}
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </section>
    </main>
</div>
</body>
</html>
