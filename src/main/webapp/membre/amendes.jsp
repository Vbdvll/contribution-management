<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes amendes</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2>Mes amendes</h2>
                <p class="text-muted mb-0">
                    Bonjour ${membre.prenom}, voici vos amendes.
                </p>
            </div>

            <a href="${pageContext.request.contextPath}/membre/dashboard"
               class="btn btn-secondary">
                Retour
            </a>
        </div>

        <table class="table table-bordered table-hover">

            <thead class="table-dark">
            <tr>
                <th>Campagne concernée</th>
                <th>Montant</th>
                <th>Date génération</th>
                <th>Statut paiement</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="amende" items="${amendes}">
                <tr>
                    <td>${amende.campagne.titre}</td>
                    <td>${amende.montant} FCFA</td>
                    <td>${amende.dateGeneration}</td>
                    <td>
                        <c:choose>
                            <c:when test="${amende.statutPaiement == 'PAYEE'}">
                                <span class="badge bg-success">PAYÉE</span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-danger">NON PAYÉE</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>

            </tbody>

        </table>

    </div>

</div>

</body>
</html>