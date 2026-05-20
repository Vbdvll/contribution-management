<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="fr">
<head>

    <meta charset="UTF-8">
    <title>Dashboard Administrateur</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css"
          rel="stylesheet">

    <style>

        body{
            background:#eeece6;
            font-family:Arial,sans-serif;
        }

        .app{
            min-height:100vh;
            padding:30px;
        }

        .sidebar{
            width:80px;
            background:white;
            border-radius:30px;
            padding:25px 0;
            min-height:90vh;
            box-shadow:0 10px 30px rgba(0,0,0,.08);
        }

        .sidebar a{
            width:45px;
            height:45px;
            margin:12px auto;
            border-radius:50%;
            display:flex;
            align-items:center;
            justify-content:center;
            text-decoration:none;
            font-size:20px;
            color:#333;
        }

        .sidebar a.active,
        .sidebar a:hover{
            background:#1f1f1f;
            color:white;
        }

        .main{
            flex:1;
            margin-left:25px;
        }

        .page-card{
            background:#f7f6f2;
            border-radius:30px;
            padding:35px;
            min-height:90vh;
        }

        .stat-card{
            background:white;
            border-radius:25px;
            padding:25px;
            box-shadow:0 8px 25px rgba(0,0,0,.06);
            height:100%;
        }

        .green{
            background:#6f8f7b;
            color:white;
        }

    </style>

</head>

<body>

<div class="app d-flex">

    <div class="sidebar">

        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="active">
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

        <div class="page-card">

            <h2 class="fw-bold mb-2">
                Bonjour, Administrateur
            </h2>

            <p class="text-muted mb-5">
                Vue globale de la gestion des cotisations de l'association
            </p>


            <div class="row g-4 mb-5">

                <div class="col-md-3">

                    <div class="stat-card">

                        Membres

                        <h1>

                            ${totalMembres}

                        </h1>

                        Total enregistrés

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="stat-card">

                        Cotisations

                        <h1>

                            ${totalCotisations}
                            FCFA

                        </h1>

                        Montant encaissé

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="stat-card">

                        Amendes payées

                        <h1>

                            ${totalAmendesPayees}
                            FCFA

                        </h1>

                        Recouvrées

                    </div>

                </div>

                <div class="col-md-3">

                    <div class="stat-card green">

                        Amendes non payées

                        <h1>

                            ${totalAmendesNonPayees}
                            FCFA

                        </h1>

                        À suivre

                    </div>

                </div>

            </div>


            <div class="row g-4">

                <div class="col-md-4">

                    <div class="stat-card">

                        <h3>Membres</h3>

                        <p>
                            Ajouter, modifier, supprimer et consulter les membres.
                        </p>

                        <a href="${pageContext.request.contextPath}/admin/membres"
                           class="btn btn-success rounded-pill">

                            Gérer les membres

                        </a>

                    </div>

                </div>


                <div class="col-md-4">

                    <div class="stat-card">

                        <h3>Cotisations</h3>

                        <p>
                            Enregistrer les paiements et suivre les cotisations.
                        </p>

                        <a href="${pageContext.request.contextPath}/admin/cotisations"
                           class="btn btn-success rounded-pill">

                            Gérer les cotisations

                        </a>

                    </div>

                </div>


                <div class="col-md-4">

                    <div class="stat-card">

                        <h3>Amendes</h3>

                        <p>
                            Gérer les amendes et les paiements.
                        </p>

                        <a href="${pageContext.request.contextPath}/admin/amendes"
                           class="btn btn-success rounded-pill">

                            Gérer les amendes

                        </a>

                    </div>

                </div>

            </div>

        </div>

    </main>

</div>

</body>
</html>