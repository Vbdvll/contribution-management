<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Admin</title>

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

        .top-card {
            background: #f7f6f2;
            border-radius: 30px;
            padding: 35px;
        }

        .stat-card {
            background: white;
            border-radius: 22px;
            padding: 25px;
            min-height: 140px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
        }

        .stat-card h6 {
            color: #777;
            font-size: 14px;
        }

        .stat-card h3 {
            font-weight: 700;
            margin-top: 10px;
        }

        .green-card {
            background: #6f8f7b;
            color: white;
        }

        .action-card {
            background: white;
            border-radius: 25px;
            padding: 30px;
            height: 100%;
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
    </style>
</head>

<body>

<div class="app d-flex">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/admin/dashboard" class="active">
            <i class="bi bi-grid-fill"></i>
        </a>

        <a href="${pageContext.request.contextPath}/admin/membres">
            <i class="bi bi-people"></i>
        </a>

        <a href="${pageContext.request.contextPath}/admin/campagnes">
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

        <div class="top-card mb-4">

            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <h2 class="fw-bold">Bonjour, Administrateur</h2>
                    <p class="text-muted mb-0">
                        Vue globale de la gestion des cotisations de l’association
                    </p>
                </div>

                <a href="${pageContext.request.contextPath}/logout"
                   class="btn btn-outline-dark rounded-pill px-4">
                    Déconnexion
                </a>
            </div>

            <div class="row g-4">

                <div class="col-md-3">
                    <div class="stat-card">
                        <h6>Membres</h6>
                        <h3>${totalMembres}</h3>
                        <p class="text-muted mb-0">Total enregistrés</p>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="stat-card">
                        <h6>Cotisations</h6>
                        <h3>${totalCotisations} FCFA</h3>
                        <p class="text-muted mb-0">Montant encaissé</p>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="stat-card">
                        <h6>Amendes payées</h6>
                        <h3>${totalAmendesPayees} FCFA</h3>
                        <p class="text-muted mb-0">Recouvrées</p>
                    </div>
                </div>

                <div class="col-md-3">
                    <div class="stat-card green-card">
                        <h6 class="text-white-50">Amendes non payées</h6>
                        <h3>${totalAmendesNonPayees} FCFA</h3>
                        <p class="mb-0">À suivre</p>
                    </div>
                </div>

            </div>

        </div>

        <div class="row g-4">

            <div class="col-md-3">
                <div class="action-card">
                    <h4>Membres</h4>
                    <p class="text-muted">
                        Ajouter, modifier, supprimer et consulter les membres.
                    </p>
                    <a href="${pageContext.request.contextPath}/admin/membres"
                       class="btn btn-dark-green rounded-pill px-4">
                        Gérer
                    </a>
                </div>
            </div>

            <div class="col-md-3">
                <div class="action-card">
                    <h4>Campagnes</h4>
                    <p class="text-muted">
                        Créer les cotisations décidées par l’association.
                    </p>
                    <a href="${pageContext.request.contextPath}/admin/campagnes"
                       class="btn btn-dark-green rounded-pill px-4">
                        Gérer
                    </a>
                </div>
            </div>

            <div class="col-md-3">
                <div class="action-card">
                    <h4>Cotisations</h4>
                    <p class="text-muted">
                        Suivre les paiements effectués par les membres.
                    </p>
                    <a href="${pageContext.request.contextPath}/admin/cotisations"
                       class="btn btn-dark-green rounded-pill px-4">
                        Gérer
                    </a>
                </div>
            </div>

            <div class="col-md-3">
                <div class="action-card">
                    <h4>Amendes</h4>
                    <p class="text-muted">
                        Générer, consulter et marquer les amendes comme payées.
                    </p>
                    <a href="${pageContext.request.contextPath}/admin/amendes"
                       class="btn btn-dark-green rounded-pill px-4">
                        Gérer
                    </a>
                </div>
            </div>

        </div>

    </main>

</div>

</body>
</html>