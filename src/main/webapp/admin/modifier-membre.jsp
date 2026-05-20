<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4">

        <h2 class="mb-4">Modifier membre</h2>

        <form action="${pageContext.request.contextPath}/admin/membres/modifier" method="post">

            <input type="hidden" name="id" value="${membre.id}">

            <div class="mb-3">
                <label class="form-label">Numéro</label>
                <input type="text" name="numero" class="form-control" value="${membre.numero}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Prénom</label>
                <input type="text" name="prenom" class="form-control" value="${membre.prenom}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Nom</label>
                <input type="text" name="nom" class="form-control" value="${membre.nom}" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Statut</label>
                <select name="statut" class="form-select">
                    <option value="ACTIF" ${membre.statut == 'ACTIF' ? 'selected' : ''}>ACTIF</option>
                    <option value="INACTIF" ${membre.statut == 'INACTIF' ? 'selected' : ''}>INACTIF</option>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">Mettre à jour</button>

            <a href="${pageContext.request.contextPath}/admin/membres"
               class="btn btn-secondary">
                Annuler
            </a>

        </form>

    </div>
</div>

</body>
</html>