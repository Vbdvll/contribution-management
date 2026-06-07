<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="amendes" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mes amendes</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/membre-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header"><div><h1>Mes amendes</h1><p>Consulter les pénalités liées à vos cotisations.</p></div></header>
        <c:if test="${not empty param.success}"><div class="alert alert-success"><c:out value="${param.success}"/></div></c:if>
        <c:if test="${not empty param.erreur}"><div class="alert alert-danger"><c:out value="${param.erreur}"/></div></c:if>
        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead><tr><th>Campagne</th><th>Montant</th><th>Date</th><th>Statut</th><th>Action</th></tr></thead>
                    <tbody>
                    <c:forEach var="amende" items="${amendes}">
                        <tr>
                            <td><c:out value="${amende.campagne.titre}"/></td><td>${amende.montant} FCFA</td><td>${amende.dateGeneration}</td>
                            <td>
                                <span class="status-badge ${amende.statutPaiement == 'PAYEE' ? 'status-success' : 'status-danger'}">
                                    ${amende.statutPaiement}
                                </span>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${amende.statutPaiement == 'NON_PAYEE'}">
                                        <form method="post" action="${pageContext.request.contextPath}/membre/amendes/payer"
                                              onsubmit="return confirm('Confirmer le paiement de cette amende ?');">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <input type="hidden" name="id" value="${amende.id}">
                                            <button type="submit" class="btn-app btn-small">Payer</button>
                                        </form>
                                    </c:when>
                                    <c:otherwise><span class="text-muted">Réglée</span></c:otherwise>
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
