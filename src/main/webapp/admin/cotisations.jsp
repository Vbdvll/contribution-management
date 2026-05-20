<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des cotisations</title>

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
            margin-bottom: 30px;
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

        .filter-control {
            border-radius: 18px;
            border: none;
            padding: 13px 16px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.05);
        }

        .badge-payee {
            background: #d4edda;
            color: #1b5e20;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }

        .badge-attente {
            background: #fff3cd;
            color: #856404;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }

        table {
            border-collapse: separate;
            border-spacing: 0 12px;
        }

        thead th {
            border: none;
            background: transparent;
            color: #777;
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

        <a href="${pageContext.request.contextPath}/admin/campagnes">
            <i class="bi bi-calendar-check"></i>
        </a>

        <a href="${pageContext.request.contextPath}/admin/cotisations" class="active">
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
                    <h2 class="fw-bold">Gestion des cotisations</h2>
                    <p class="text-muted">
                        Suivi des paiements, validations et retards.
                    </p>
                </div>

                <a href="${pageContext.request.contextPath}/admin/cotisations/ajouter"
                   class="btn btn-dark-green rounded-pill px-4 py-2">
                    Enregistrer paiement
                </a>
            </div>

            <form id="filterForm"
                  method="get"
                  action="${pageContext.request.contextPath}/admin/cotisations">

                <div class="mb-4">
                    <label class="form-label">Campagne</label>

                    <select name="campagneId"
                            class="form-select filter-control auto-filter">

                        <option value="">Toutes les campagnes</option>

                        <c:forEach var="campagne" items="${campagnes}">
                            <option value="${campagne.id}"
                                ${campagneId == campagne.id ? 'selected' : ''}>
                                    ${campagne.titre} - ${campagne.montant} FCFA
                            </option>
                        </c:forEach>

                    </select>
                </div>

            </form>

            <div class="content-card">

                <h4 class="mb-4">Paiements enregistrés</h4>

                <table class="table">

                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Membre</th>
                        <th>Campagne</th>
                        <th>Montant</th>
                        <th>Date</th>
                        <th>Mode</th>
                        <th>Statut</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="cotisation" items="${cotisations}">
                        <tr>
                            <td>${cotisation.id}</td>

                            <td>
                                    ${cotisation.membre.prenom}
                                    ${cotisation.membre.nom}
                            </td>

                            <td>${cotisation.campagne.titre}</td>

                            <td>${cotisation.montant} FCFA</td>

                            <td>${cotisation.datePaiement}</td>

                            <td>${cotisation.modePaiement}</td>

                            <td>
                                <c:choose>
                                    <c:when test="${cotisation.statut == 'PAYEE'}">
                                        <span class="badge-payee">PAYÉE</span>
                                    </c:when>

                                    <c:otherwise>
                                        <span class="badge-attente">EN ATTENTE</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>

                            <td>
                                <c:if test="${cotisation.statut == 'EN_ATTENTE'}">
                                    <a href="${pageContext.request.contextPath}/admin/cotisations/valider?id=${cotisation.id}&campagneId=${campagneId}"
                                       class="btn btn-success btn-sm rounded-pill"
                                       onclick="return confirm('Valider ce paiement ?')">
                                        Valider
                                    </a>
                                </c:if>

                                <c:if test="${cotisation.statut == 'PAYEE'}">
                                    <span class="text-muted">Validé</span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>

                </table>

            </div>

            <c:if test="${not empty campagneId}">

                <div class="content-card">

                    <h4 class="mb-4">
                        <c:choose>
                            <c:when test="${campagneEnRetard}">
                                Membres en retard pour cette campagne
                            </c:when>
                            <c:otherwise>
                                Membres n’ayant pas encore payé cette campagne
                            </c:otherwise>
                        </c:choose>
                    </h4>

                    <table class="table">

                        <thead>
                        <tr>
                            <th>Numéro</th>
                            <th>Prénom</th>
                            <th>Nom</th>
                            <th>Email</th>
                            <th>Action</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach var="membre" items="${membresSansPaiement}">
                            <tr>
                                <td>${membre.numero}</td>
                                <td>${membre.prenom}</td>
                                <td>${membre.nom}</td>
                                <td>${membre.utilisateur.email}</td>

                                <td>
                                    <a href="${pageContext.request.contextPath}/admin/amendes/generer?membreId=${membre.id}&campagneId=${campagneId}"
                                       class="btn btn-warning btn-sm rounded-pill ${campagneEnRetard ? '' : 'disabled'}">
                                        <c:choose>
                                            <c:when test="${campagneEnRetard}">
                                                Générer amende
                                            </c:when>
                                            <c:otherwise>
                                                Pas encore en retard
                                            </c:otherwise>
                                        </c:choose>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>

                </div>

            </c:if>

        </div>

    </main>

</div>

<script>
    const filterForm = document.getElementById("filterForm");
    const fields = document.querySelectorAll(".auto-filter");

    fields.forEach(function (field) {
        field.addEventListener("change", function () {
            filterForm.submit();
        });
    });
</script>

</body>
</html>