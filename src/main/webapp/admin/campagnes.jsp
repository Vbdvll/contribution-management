<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des campagnes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body { background: #eeece6; font-family: Arial, sans-serif; }
        .app { min-height: 100vh; padding: 30px; }

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

        .main { flex: 1; margin-left: 25px; }

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

        table {
            border-collapse: separate;
            border-spacing: 0 12px;
        }

        thead th {
            border: none;
            background: transparent;
            color: #777;
            font-size: 14px;
        }

        tbody tr {
            background: white;
            box-shadow: 0 6px 18px rgba(0,0,0,.05);
        }

        tbody td {
            border: none;
            padding: 18px;
            vertical-align: middle;
        }

        tbody tr td:first-child {
            border-radius: 16px 0 0 16px;
        }

        tbody tr td:last-child {
            border-radius: 0 16px 16px 0;
        }

        .badge-active {
            background: #d4edda;
            color: #1b5e20;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }

        .badge-terminee {
            background: #e9ecef;
            color: #555;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }
    </style>
</head>

<body>

<div class="app d-flex">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard">
            <i class="bi bi-grid-fill"></i>
        </a>

        <a href="${pageContext.request.contextPath}/admin/membres">
            <i class="bi bi-people"></i>
        </a>

        <a href="${pageContext.request.contextPath}/admin/campagnes" class="active">
            <i class="bi bi-calendar-check"></i>
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
                    <h2 class="fw-bold">Gestion des campagnes</h2>
                    <p class="text-muted">
                        Créez et suivez les cotisations décidées par l’association.
                    </p>
                </div>

                <a href="${pageContext.request.contextPath}/admin/campagnes/ajouter"
                   class="btn btn-dark-green rounded-pill px-4 py-2">
                    <i class="bi bi-plus-circle me-2"></i>
                    Créer campagne
                </a>
            </div>

            <div class="content-card">

                <table class="table">

                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Titre</th>
                        <th>Montant</th>
                        <th>Fréquence</th>
                        <th>Date début</th>
                        <th>Date fin</th>
                        <th>Statut</th>
                        <th>Suivi</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="campagne" items="${campagnes}">
                        <tr>
                            <td>${campagne.id}</td>
                            <td>${campagne.titre}</td>
                            <td>${campagne.montant} FCFA</td>
                            <td>${campagne.frequence}</td>
                            <td>${campagne.dateDebut}</td>
                            <td>${campagne.dateFin}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${campagne.statut == 'ACTIVE'}">
                                        <span class="badge-active">ACTIVE</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge-terminee">TERMINÉE</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/cotisations?campagneId=${campagne.id}"
                                   class="btn btn-sm btn-outline-dark rounded-pill px-3">
                                    Voir paiements
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

</body>
</html>