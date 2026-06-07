<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<c:set var="activePage" value="membres" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Gestion des membres</title>
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
                <h1>Gestion des membres</h1>
                <p>Rechercher, ajouter et modifier les membres de l'association.</p>
            </div>
            <div class="page-actions">
                <a class="btn-app" href="${pageContext.request.contextPath}/admin/membres/ajouter">
                    <i class="bi bi-person-plus"></i> Ajouter un membre
                </a>
            </div>
        </header>

        <form method="get" action="${pageContext.request.contextPath}/admin/membres" class="filter-panel">
            <input type="search" id="searchInput" name="recherche" class="form-control"
                   placeholder="Nom, prénom, numéro ou email" value="${fn:escapeXml(recherche)}">
        </form>

        <section class="content-panel">
            <div class="table-responsive">
                <table class="table app-table">
                    <thead>
                    <tr>
                        <th>ID</th><th>Numéro</th><th>Prénom</th><th>Nom</th>
                        <th>Email</th><th>Naissance</th><th>Adhésion</th><th>Statut</th><th>Actions</th>
                    </tr>
                    </thead>
                    <tbody id="membresTable">
                    <c:forEach var="membre" items="${membres}">
                        <tr>
                            <td>${membre.id}</td>
                            <td><c:out value="${membre.numero}"/></td>
                            <td><c:out value="${membre.prenom}"/></td>
                            <td><c:out value="${membre.nom}"/></td>
                            <td><c:out value="${membre.utilisateur.email}"/></td>
                            <td>${empty membre.dateNaissance ? '-' : membre.dateNaissance}</td>
                            <td>${membre.dateAdhesion}</td>
                            <td>
                                <span class="status-badge ${membre.statut == 'ACTIF' ? 'status-success' : 'status-neutral'}">
                                    ${membre.statut}
                                </span>
                            </td>
                            <td>
                                <div class="table-actions">
                                    <a class="btn-secondary-app btn-small"
                                       href="${pageContext.request.contextPath}/admin/membres/modifier?id=${membre.id}"
                                       title="Modifier"><i class="bi bi-pencil"></i></a>
                                    <c:if test="${membre.statut == 'ACTIF'}">
                                        <form method="post" action="${pageContext.request.contextPath}/admin/membres/supprimer"
                                              onsubmit="return confirm('Désactiver ce membre ?');">
                                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                            <input type="hidden" name="id" value="${membre.id}">
                                            <button class="btn-danger-app btn-small" type="submit" title="Désactiver">
                                                <i class="bi bi-person-x"></i>
                                            </button>
                                        </form>
                                    </c:if>
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
<script>
    document.getElementById("searchInput").addEventListener("input", function () {
        const value = this.value.toLowerCase();
        document.querySelectorAll("#membresTable tr").forEach(function (row) {
            row.style.display = row.textContent.toLowerCase().includes(value) ? "" : "none";
        });
    });
</script>
</body>
</html>
