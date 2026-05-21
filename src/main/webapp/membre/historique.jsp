<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Historique paiements</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2>Historique de mes paiements</h2>
                <p class="text-muted mb-0">
                    Bonjour ${membre.prenom}, voici vos paiements déclarés.
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
                <th>Campagne</th>
                <th>Échéance concernée</th>
                <th>Montant</th>
                <th>Date déclaration</th>
                <th>Mode</th>
                <th>Statut</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="paiement" items="${paiements}">
                <tr>
                    <td>${paiement.campagne.titre}</td>
                    <td>${paiement.dateEcheance}</td>
                    <td>${paiement.montant} FCFA</td>
                    <td>${paiement.datePaiement}</td>
                    <td>${paiement.modePaiement}</td>
                    <td>
                        <c:choose>
                            <c:when test="${paiement.statut == 'PAYEE'}">
                                <span class="badge bg-success">PAYÉE</span>
                            </c:when>

                            <c:when test="${paiement.statut == 'EN_ATTENTE'}">
                                <span class="badge bg-warning text-dark">
                                    EN ATTENTE
                                </span>
                            </c:when>

                            <c:otherwise>
                                <span class="badge bg-danger">EN RETARD</span>
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