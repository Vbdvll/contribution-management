<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des membres</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background: #eeece6;
            font-family: Arial, sans-serif;
        }

        .app {
            min-height: 100vh;
            padding: 30px;
        }

        .sidebar {
            width: 80px;
            background: white;
            border-radius: 30px;
            padding: 25px 0;
            min-height: 90vh;
            box-shadow: 0 10px 30px rgba(0,0,0,0.08);
        }

        .sidebar a {
            width: 45px;
            height: 45px;
            margin: 12px auto;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #333;
            text-decoration: none;
            font-size: 20px;
        }

        .sidebar a.active,
        .sidebar a:hover {
            background: #1f1f1f;
            color: white;
        }

        .main {
            flex: 1;
            margin-left: 25px;
        }

        .page-card {
            background: #f7f6f2;
            border-radius: 30px;
            padding: 35px;
            min-height: 90vh;
        }

        .content-card {
            background: white;
            border-radius: 25px;
            padding: 25px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
        }

        .btn-dark-green {
            background: #456b55;
            color: white;
            border: none;
        }

        .btn-dark-green:hover {
            background: #365543;
            color: white;
        }

        .search-box {
            border-radius: 20px;
            border: none;
            padding: 14px 20px;
            background: white;
            box-shadow: 0 8px 25px rgba(0,0,0,0.05);
        }

        table {
            border-collapse: separate;
            border-spacing: 0 12px;
        }

        thead th {
            border: none;
            color: #777;
            font-size: 14px;
            font-weight: 600;
            background: transparent;
        }

        tbody tr {
            background: #fff;
            box-shadow: 0 6px 18px rgba(0,0,0,0.05);
        }

        tbody td {
            border: none;
            padding: 18px 14px;
            vertical-align: middle;
        }

        tbody tr td:first-child {
            border-radius: 16px 0 0 16px;
        }

        tbody tr td:last-child {
            border-radius: 0 16px 16px 0;
        }

        .badge-soft {
            background: #e5efe8;
            color: #456b55;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }

        .action-btn {
            border-radius: 18px;
            padding: 7px 14px;
        }
    </style>
</head>

<body>

<div class="app d-flex">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">
            <i class="bi bi-grid-fill"></i>
        </a>
        <a href="${pageContext.request.contextPath}/admin/membres" class="active">
            <i class="bi bi-people"></i>
        </a>
        <a href="${pageContext.request.contextPath}/admin/cotisations">
            <i class="bi bi-cash-stack"></i>
        </a>
        <a href="${pageContext.request.contextPath}/admin/amendes">
            <i class="bi bi-exclamation-triangle"></i>
        </a>
        <a href="${pageContext.request.contextPath}/logout">
            <i class="bi bi-box-arrow-right"></i>
        </a>
    </div>

    <main class="main">

        <div class="page-card">

            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="fw-bold mb-1">Gestion des membres</h2>
                    <p class="text-muted mb-0">
                        Ajouter, rechercher, modifier et supprimer les membres de l’association
                    </p>
                </div>

                <a href="${pageContext.request.contextPath}/admin/membres/ajouter"
                   class="btn btn-dark-green rounded-pill px-4 py-2">
                    <i class="bi bi-plus-circle me-2"></i>
                    Ajouter membre
                </a>
            </div>

            <form method="get"
                  action="${pageContext.request.contextPath}/admin/membres"
                  class="mb-4">

                <input type="text"
                       id="searchInput"
                       name="recherche"
                       class="form-control search-box"
                       placeholder="Rechercher par nom, prénom, numéro ou email..."
                       value="${recherche}">
            </form>

            <div class="content-card">

                <table class="table align-middle mb-0">

                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Numéro</th>
                        <th>Prénom</th>
                        <th>Nom</th>
                        <th>Email</th>
                        <th>Statut</th>
                        <th>Actions</th>
                    </tr>
                    </thead>

                    <tbody id="membresTable">

                    <c:forEach var="membre" items="${membres}">
                        <tr>
                            <td>${membre.id}</td>
                            <td>${membre.numero}</td>
                            <td>${membre.prenom}</td>
                            <td>${membre.nom}</td>
                            <td>${membre.utilisateur.email}</td>
                            <td>
                                <span class="badge-soft">
                                        ${membre.statut}
                                </span>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/membres/modifier?id=${membre.id}"
                                   class="btn btn-sm btn-outline-dark action-btn">
                                    <i class="bi bi-pencil"></i>
                                </a>

                                <a href="${pageContext.request.contextPath}/admin/membres/supprimer?id=${membre.id}"
                                   class="btn btn-sm btn-outline-danger action-btn"
                                   onclick="return confirm('Voulez-vous vraiment supprimer ce membre ?');">
                                    <i class="bi bi-trash"></i>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>

                </table>

            </div>

        </div>

    </main>

</div>

<script>
    document.getElementById("searchInput").addEventListener("keyup", function () {
        let filter = this.value.toLowerCase();
        let rows = document.querySelectorAll("#membresTable tr");

        rows.forEach(function (row) {
            let text = row.textContent.toLowerCase();
            row.style.display = text.includes(filter) ? "" : "none";
        });
    });
</script>

</body>
</html>