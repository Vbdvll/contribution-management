<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion - Gestion Cotisations</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container d-flex justify-content-center align-items-center vh-100">
    <div class="card shadow p-4" style="width: 400px;">

        <h3 class="text-center mb-4">Connexion</h3>

        <% if (request.getAttribute("erreur") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("erreur") %>
        </div>
        <% } %>

        <form action="login" method="post">

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Mot de passe</label>
                <input type="password" name="motDePasse" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary w-100">
                Se connecter
            </button>

        </form>
    </div>
</div>

</body>
</html>