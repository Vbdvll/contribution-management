<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="amendes" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des amendes</title>
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
                <h1>Gestion des amendes</h1>
                <p>Consulter et suivre le paiement des amendes.</p>
            </div>
            <div class="page-actions">
                <a class="btn-app" href="${pageContext.request.contextPath}/admin/amendes/generer">
                    <i class="bi bi-plus-circle"></i> Générer une amende
                </a>
            </div>
        </header>
        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead>
                    <tr><th>ID</th><th>Membre</th><th>Montant</th><th>Date</th><th>Statut</th><th>Action</th></tr>
                    </thead>
                    <tbody>
                    <c:forEach var="amende" items="${amendes}">
                        <tr>
                            <td>${amende.id}</td>
                            <td><c:out value="${amende.membre.prenom}"/> <c:out value="${amende.membre.nom}"/></td>
                            <td>${amende.montant} FCFA</td>
                            <td>${amende.dateGeneration}</td>
                            <td>
                                <span class="status-badge ${amende.statutPaiement == 'PAYEE' ? 'status-success' : 'status-warning'}">
                                    ${amende.statutPaiement}
                                </span>
                            </td>
                            <td>
                                <c:if test="${amende.statutPaiement == 'NON_PAYEE'}">
                                    <form method="post" action="${pageContext.request.contextPath}/admin/amendes/payer"
                                          onsubmit="return confirm('Marquer cette amende comme payée ?');">
                                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                        <input type="hidden" name="id" value="${amende.id}">
                                        <button class="btn-app btn-small" type="submit">Marquer payée</button>
                                    </form>
                                </c:if>
                                <c:if test="${amende.statutPaiement == 'PAYEE'}">
                                    <span class="text-muted">Déjà payée</span>
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
