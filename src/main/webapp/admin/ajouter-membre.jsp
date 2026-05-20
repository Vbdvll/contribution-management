<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <h2 class="mb-4">Ajouter un membre</h2>

        <% if (request.getAttribute("erreur") != null) { %>
        <div class="alert alert-danger">
            <%= request.getAttribute("erreur") %>
        </div>
        <% } %>

        <form action="<%= request.getContextPath() %>/admin/membres/ajouter" method="post">

            <div class="mb-3">
                <label class="form-label">Numéro membre</label>
                <input type="text" name="numero" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Prénom</label>
                <input type="text" name="prenom" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Nom</label>
                <input type="text" name="nom" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email de connexion</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Mot de passe temporaire</label>
                <input type="password" name="motDePasse" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-primary">
                Enregistrer
            </button>

            <a href="<%= request.getContextPath() %>/admin/dashboard"
               class="btn btn-secondary">
                Annuler
            </a>

        </form>

    </div>

</div>

</body>
</html>