<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Campagnes de cotisation</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Campagnes de cotisation</h2>

            <a href="${pageContext.request.contextPath}/admin/campagnes/ajouter"
               class="btn btn-success">
                Créer une campagne
            </a>
        </div>

        <table class="table table-bordered table-hover">

            <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Titre</th>
                <th>Montant</th>
                <th>Fréquence</th>
                <th>Date début</th>
                <th>Date fin</th>
                <th>Statut</th>
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
                    <td>${campagne.statut}</td>
                </tr>
            </c:forEach>
            </tbody>

        </table>

        <a href="${pageContext.request.contextPath}/admin/dashboard"
           class="btn btn-secondary">
            Retour dashboard
        </a>

    </div>

</div>

</body>
</html>