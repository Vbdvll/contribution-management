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

        tbody tr td:first-child { border-radius: 16px 0 0 16px; }
        tbody tr td:last-child { border-radius: 0 16px 16px 0; }

        .badge-soft-green {
            background: #e5efe8;
            color: #456b55;
            padding: 8px 14px;
            border-radius: 20px;
            font-weight: 600;
        }

        .badge-soft-warning {
            background: #fff3cd;
            color: #8a6500;
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
                    <h2 class="fw-bold mb-1">Gestion des cotisations</h2>
                    <p class="text-muted mb-0">
                        Enregistrer les paiements et suivre les membres n’ayant pas encore payé
                    </p>
                </div>

                <a href="${pageContext.request.contextPath}/admin/cotisations/ajouter"
                   class="btn btn-dark-green rounded-pill px-4 py-2">
                    <i class="bi bi-plus-circle me-2"></i>
                    Ajouter cotisation
                </a>
            </div>

            <form id="filterForm"
                  method="get"
                  action="${pageContext.request.contextPath}/admin/cotisations"
                  class="row g-3 mb-4">

                <div class="col-md-4">
                    <label class="form-label">Mois</label>
                    <select name="mois" class="form-select filter-control auto-filter">
                        <option value="1" ${mois == 1 ? 'selected' : ''}>Janvier</option>
                        <option value="2" ${mois == 2 ? 'selected' : ''}>Février</option>
                        <option value="3" ${mois == 3 ? 'selected' : ''}>Mars</option>
                        <option value="4" ${mois == 4 ? 'selected' : ''}>Avril</option>
                        <option value="5" ${mois == 5 ? 'selected' : ''}>Mai</option>
                        <option value="6" ${mois == 6 ? 'selected' : ''}>Juin</option>
                        <option value="7" ${mois == 7 ? 'selected' : ''}>Juillet</option>
                        <option value="8" ${mois == 8 ? 'selected' : ''}>Août</option>
                        <option value="9" ${mois == 9 ? 'selected' : ''}>Septembre</option>
                        <option value="10" ${mois == 10 ? 'selected' : ''}>Octobre</option>
                        <option value="11" ${mois == 11 ? 'selected' : ''}>Novembre</option>
                        <option value="12" ${mois == 12 ? 'selected' : ''}>Décembre</option>
                    </select>
                </div>

                <div class="col-md-4">
                    <label class="form-label">Année</label>
                    <input type="number"
                           name="annee"
                           class="form-control filter-control auto-filter"
                           value="${annee}">
                </div>

                <div class="col-md-4">
                    <label class="form-label">Statut</label>
                    <select name="statut" class="form-select filter-control auto-filter">
                        <option value="" ${empty statut ? 'selected' : ''}>Tous</option>
                        <option value="PAYEE" ${statut == 'PAYEE' ? 'selected' : ''}>Payée</option>
                        <option value="EN_RETARD" ${statut == 'EN_RETARD' ? 'selected' : ''}>En retard</option>
                    </select>
                </div>

            </form>

            <div class="content-card mb-4">

                <h4 class="fw-bold mb-3">Cotisations enregistrées</h4>

                <table class="table align-middle mb-0">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Membre</th>
                        <th>Montant</th>
                        <th>Mois</th>
                        <th>Année</th>
                        <th>Date paiement</th>
                        <th>Mode</th>
                        <th>Statut</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach var="cotisation" items="${cotisations}">
                        <tr>
                            <td>${cotisation.id}</td>
                            <td>${cotisation.membre.prenom} ${cotisation.membre.nom}</td>
                            <td>${cotisation.montant} FCFA</td>
                            <td>${cotisation.mois}</td>
                            <td>${cotisation.annee}</td>
                            <td>${cotisation.datePaiement}</td>
                            <td>${cotisation.modePaiement}</td>
                            <td>
                                <span class="badge-soft-green">
                                        ${cotisation.statut}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

            </div>

            <div class="content-card">

                <h4 class="fw-bold mb-3">
                    Membres n’ayant pas encore payé pour ${mois}/${annee}
                </h4>

                <table class="table align-middle mb-0">
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
                    <c:forEach var="membre" items="${membresEnRetard}">
                        <tr>
                            <td>${membre.numero}</td>
                            <td>${membre.prenom}</td>
                            <td>${membre.nom}</td>
                            <td>${membre.utilisateur.email}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/admin/amendes/generer?membreId=${membre.id}"
                                   class="btn btn-sm btn-warning rounded-pill px-3">
                                    Générer amende
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
    const filterForm = document.getElementById("filterForm");
    const filterFields = document.querySelectorAll(".auto-filter");

    filterFields.forEach(function (field) {
        field.addEventListener("change", function () {
            filterForm.submit();
        });
    });
</script>

</body>
</html>