<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="campagnes" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des campagnes</title>
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
                <h1>Gestion des campagnes</h1>
                <p>Créer et suivre les campagnes de cotisation.</p>
            </div>
            <div class="page-actions">
                <a class="btn-app" href="${pageContext.request.contextPath}/admin/campagnes/ajouter">
                    <i class="bi bi-calendar-plus"></i> Créer une campagne
                </a>
            </div>
        </header>
        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead>
                    <tr>
                        <th>ID</th><th>Titre</th><th>Montant</th><th>Fréquence</th>
                        <th>Début</th><th>Fin</th><th>Participation</th><th>Code</th>
                        <th>Participants</th><th>Statut</th><th>Suivi</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="campagne" items="${campagnes}">
                        <tr>
                            <td>${campagne.id}</td>
                            <td><c:out value="${campagne.titre}"/></td>
                            <td>${campagne.montant} FCFA</td>
                            <td>${campagne.frequence}</td>
                            <td>${campagne.dateDebut}</td>
                            <td>${campagne.dateFin}</td>
                            <td>${campagne.typeParticipation == 'OBLIGATOIRE' ? 'Obligatoire' : 'Sur inscription'}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${not empty campagne.codeInscription}">
                                        <strong><c:out value="${campagne.codeInscription}"/></strong>
                                    </c:when>
                                    <c:otherwise><span class="text-muted">-</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <strong>${nombreParticipants[campagne.id]}</strong>
                            </td>
                            <td>
                                <span class="status-badge ${campagne.statut == 'ACTIVE' ? 'status-success' : 'status-neutral'}">
                                    ${campagne.statut}
                                </span>
                            </td>
                            <td>
                                <div class="table-actions">
                                    <a class="btn-secondary-app btn-small"
                                       href="${pageContext.request.contextPath}/admin/campagnes/participants?campagneId=${campagne.id}">
                                        <i class="bi bi-people"></i> Participants
                                    </a>
                                    <a class="btn-secondary-app btn-small"
                                       href="${pageContext.request.contextPath}/admin/cotisations?campagneId=${campagne.id}">
                                        Paiements
                                    </a>
                                </div>
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
