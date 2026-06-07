<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="historique" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Historique des paiements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/membre-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header">
            <div><h1>Historique de mes paiements</h1><p>Retrouver toutes vos déclarations de paiement.</p></div>
        </header>
        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead><tr><th>Référence</th><th>Campagne</th><th>Échéance</th><th>Montant</th><th>Date</th><th>Mode</th><th>Statut</th><th>Reçu</th></tr></thead>
                    <tbody>
                    <c:forEach var="paiement" items="${paiements}">
                        <tr>
                            <td><c:out value="${paiement.referenceTransaction}"/></td>
                            <td><c:out value="${paiement.campagne.titre}"/></td><td>${paiement.dateEcheance}</td>
                            <td>${paiement.montant} FCFA</td><td>${paiement.datePaiement}</td><td><c:out value="${paiement.modePaiement}"/></td>
                            <td>
                                <span class="status-badge ${paiement.statut == 'PAYEE' ? 'status-success' : (paiement.statut == 'EN_ATTENTE' ? 'status-warning' : 'status-danger')}">
                                    ${paiement.statut}
                                </span>
                            </td>
                            <td>
                                <c:if test="${paiement.statut == 'PAYEE'}">
                                    <a class="btn-secondary-app btn-small"
                                       href="${pageContext.request.contextPath}/membre/cotisations/recu?id=${paiement.id}">
                                        <i class="bi bi-file-earmark-pdf"></i> PDF
                                    </a>
                                </c:if>
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
