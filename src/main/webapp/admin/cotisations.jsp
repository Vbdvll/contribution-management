<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="cotisations" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des cotisations</title>
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
                <h1>Gestion des cotisations</h1>
                <p>Suivre et valider les paiements des membres.</p>
            </div>
            <div class="page-actions">
                <a class="btn-app" href="${pageContext.request.contextPath}/admin/cotisations/ajouter">
                    <i class="bi bi-plus-circle"></i> Enregistrer un paiement
                </a>
            </div>
        </header>

        <form id="filterForm" method="get" action="${pageContext.request.contextPath}/admin/cotisations"
              class="filter-panel">
            <label class="form-label" for="campagneId">Campagne</label>
            <select id="campagneId" name="campagneId" class="form-select">
                <option value="">Toutes les campagnes</option>
                <c:forEach var="campagne" items="${campagnes}">
                    <option value="${campagne.id}" ${campagneId == campagne.id ? 'selected' : ''}>
                        <c:out value="${campagne.titre}"/> - ${campagne.montant} FCFA
                    </option>
                </c:forEach>
            </select>
        </form>

        <c:if test="${not empty param.success}"><div class="alert alert-success"><c:out value="${param.success}"/></div></c:if>
        <c:if test="${not empty param.erreur}"><div class="alert alert-danger"><c:out value="${param.erreur}"/></div></c:if>

        <div class="content-stack">
            <section class="content-panel">
                <h2>Paiements enregistrés</h2>
                <div class="table-responsive">
                    <table class="table app-table">
                        <thead>
                        <tr>
                            <th>ID</th><th>Référence</th><th>Membre</th><th>Campagne</th><th>Montant</th>
                            <th>Date</th><th>Mode</th><th>Statut</th><th>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="cotisation" items="${cotisations}">
                            <tr>
                                <td>${cotisation.id}</td>
                                <td><c:out value="${cotisation.referenceTransaction}"/></td>
                                <td><c:out value="${cotisation.membre.prenom}"/> <c:out value="${cotisation.membre.nom}"/></td>
                                <td><c:out value="${cotisation.campagne.titre}"/></td>
                                <td>${cotisation.montant} FCFA</td>
                                <td>${cotisation.datePaiement}</td>
                                <td><c:out value="${cotisation.modePaiement}"/></td>
                                <td>
                                    <span class="status-badge ${cotisation.statut == 'PAYEE' ? 'status-success' : 'status-warning'}">
                                        ${cotisation.statut}
                                    </span>
                                </td>
                                <td>
                                    <c:if test="${cotisation.statut == 'EN_ATTENTE'}">
                                        <form method="post" action="${pageContext.request.contextPath}/admin/cotisations/valider"
                                              onsubmit="return confirm('Valider ce paiement ?');">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <input type="hidden" name="id" value="${cotisation.id}">
                                            <input type="hidden" name="campagneId" value="${campagneId}">
                                            <button class="btn-app btn-small" type="submit">Valider</button>
                                        </form>
                                    </c:if>
                                    <c:if test="${cotisation.statut == 'PAYEE'}">
                                        <a class="btn-secondary-app btn-small"
                                           href="${pageContext.request.contextPath}/admin/cotisations/recu?id=${cotisation.id}">
                                            <i class="bi bi-file-earmark-pdf"></i> Reçu
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </section>

            <c:if test="${not empty campagneId}">
                <section class="content-panel">
                    <div class="panel-header">
                        <div>
                            <h2>${campagneEnRetard ? 'Membres en retard' : 'Membres sans paiement'}</h2>
                            <span>Amende automatique : 10 % du montant de la campagne.</span>
                        </div>
                        <c:if test="${campagneEnRetard}">
                            <form method="post"
                                  action="${pageContext.request.contextPath}/admin/amendes/generer-automatiquement"
                                  onsubmit="return confirm('Générer les amendes pour tous les membres en retard ?');">
                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                <input type="hidden" name="campagneId" value="${campagneId}">
                                <button type="submit" class="btn-warning-app">
                                    <i class="bi bi-lightning"></i> Générer automatiquement
                                </button>
                            </form>
                        </c:if>
                    </div>
                    <div class="table-responsive">
                        <table class="table app-table">
                            <thead>
                            <tr><th>Numéro</th><th>Prénom</th><th>Nom</th><th>Email</th><th>Action</th></tr>
                            </thead>
                            <tbody>
                            <c:forEach var="membre" items="${membresSansPaiement}">
                                <tr>
                                    <td><c:out value="${membre.numero}"/></td>
                                    <td><c:out value="${membre.prenom}"/></td>
                                    <td><c:out value="${membre.nom}"/></td>
                                    <td><c:out value="${membre.utilisateur.email}"/></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${campagneEnRetard}">
                                                <a class="btn-warning-app btn-small"
                                                   href="${pageContext.request.contextPath}/admin/amendes/generer?membreId=${membre.id}&campagneId=${campagneId}">
                                                    Générer une amende
                                                </a>
                                            </c:when>
                                            <c:otherwise><span class="text-muted">Pas encore en retard</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </section>
            </c:if>
        </div>
    </main>
</div>
<script>
    document.getElementById("campagneId").addEventListener("change", function () {
        document.getElementById("filterForm").submit();
    });
</script>
</body>
</html>
