<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Membre</title>

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

        .welcome-card {
            background: white;
            border-radius: 28px;
            padding: 35px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
            margin-bottom: 30px;
        }

        .action-card {
            background: white;
            border-radius: 25px;
            padding: 30px;
            height: 100%;
            box-shadow: 0 8px 25px rgba(0,0,0,0.06);
        }

        .action-card i {
            font-size: 32px;
            margin-bottom: 18px;
            color: #456b55;
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

        .member-badge {
            background: #e5efe8;
            color: #456b55;
            border-radius: 20px;
            padding: 8px 16px;
            font-weight: 600;
            display: inline-block;
            margin-top: 10px;
        }
    </style>
</head>

<body>

<div class="app d-flex">

    <div class="sidebar">

        <a href="${pageContext.request.contextPath}/membre/dashboard" class="active">
            <i class="bi bi-grid-fill"></i>
        </a>

        <a href="${pageContext.request.contextPath}/membre/cotisations">
            <i class="bi bi-cash-stack"></i>
        </a>

        <a href="${pageContext.request.contextPath}/membre/historique">
            <i class="bi bi-clock-history"></i>
        </a>

        <a href="${pageContext.request.contextPath}/membre/amendes">
            <i class="bi bi-exclamation-triangle"></i>
        </a>

        <a href="${pageContext.request.contextPath}/logout">
            <i class="bi bi-box-arrow-right"></i>
        </a>

    </div>

    <main class="main">

        <div class="page-card">

            <div class="welcome-card">

                <h2 class="fw-bold mb-2">
                    Bonjour ${membre.prenom} ${membre.nom}
                </h2>

                <p class="text-muted mb-0">
                    Bienvenue dans votre espace membre. Vous pouvez consulter vos cotisations,
                    suivre vos paiements et vérifier vos amendes.
                </p>

                <span class="member-badge">
                    Numéro membre : ${membre.numero}
                </span>

            </div>

            <div class="row g-4">

                <div class="col-md-4">
                    <div class="action-card">

                        <i class="bi bi-cash-stack"></i>

                        <h4>Mes cotisations</h4>

                        <p class="text-muted">
                            Consultez les campagnes de cotisation, déclarez vos paiements
                            et suivez votre statut personnel.
                        </p>

                        <a href="${pageContext.request.contextPath}/membre/cotisations"
                           class="btn btn-dark-green rounded-pill px-4">
                            Consulter
                        </a>

                    </div>
                </div>

                <div class="col-md-4">
                    <div class="action-card">

                        <i class="bi bi-clock-history"></i>

                        <h4>Historique paiements</h4>

                        <p class="text-muted">
                            Retrouvez tous vos paiements déclarés, validés ou en attente
                            de validation par l’administrateur.
                        </p>

                        <a href="${pageContext.request.contextPath}/membre/historique"
                           class="btn btn-dark-green rounded-pill px-4">
                            Voir historique
                        </a>

                    </div>
                </div>

                <div class="col-md-4">
                    <div class="action-card">

                        <i class="bi bi-exclamation-triangle"></i>

                        <h4>Mes amendes</h4>

                        <p class="text-muted">
                            Consultez les amendes liées aux retards de paiement
                            et leur statut de règlement.
                        </p>

                        <a href="${pageContext.request.contextPath}/membre/amendes"
                           class="btn btn-warning rounded-pill px-4">
                            Consulter
                        </a>

                    </div>
                </div>

            </div>

            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/logout"
                   class="btn btn-outline-dark rounded-pill px-4">
                    Déconnexion
                </a>
            </div>

        </div>

    </main>

</div>

</body>
</html>