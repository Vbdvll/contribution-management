<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Membre</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <h2>Bonjour ${membre.prenom} ${membre.nom}</h2>

        <p class="text-muted">
            Bienvenue dans votre espace membre.
        </p>

        <div class="row g-3 mt-4">

            <div class="col-md-4">
                <div class="card p-3 h-100">
                    <h5>Mes cotisations</h5>
                    <p>Voir les cotisations à payer et mon historique.</p>
                    <a href="${pageContext.request.contextPath}/membre/cotisations"
                       class="btn btn-primary">
                        Consulter
                    </a>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card p-3 h-100">
                    <h5>Mes amendes</h5>
                    <p>Consulter mes amendes payées ou non payées.</p>
                    <a href="#"
                       class="btn btn-warning disabled">
                        Bientôt
                    </a>
                </div>
            </div>

            <div class="col-md-4">
                <div class="card p-3 h-100">
                    <h5>Déconnexion</h5>
                    <p>Quitter votre espace membre.</p>
                    <a href="${pageContext.request.contextPath}/logout"
                       class="btn btn-danger">
                        Déconnexion
                    </a>
                </div>
            </div>

        </div>

    </div>

</div>

</body>
</html>