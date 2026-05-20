<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes cotisations</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <div>
                <h2>Mes cotisations</h2>
                <p class="text-muted mb-0">
                    Bonjour ${membre.prenom}, voici les cotisations actives.
                </p>
                <c:if test="${not empty param.erreur}">
                    <div class="alert alert-danger">
                            ${param.erreur}
                    </div>
                </c:if>
            </div>

            <a href="${pageContext.request.contextPath}/membre/dashboard"
               class="btn btn-secondary">
                Retour
            </a>
        </div>

        <table class="table table-bordered table-hover">

            <thead class="table-dark">
            <tr>
                <th>Titre</th>
                <th>Montant</th>
                <th>Fréquence</th>
                <th>Date début</th>
                <th>Date fin</th>
                <th>Statut paiement</th>
                <th>Action</th>
            </tr>
            </thead>

            <tbody>

            <c:forEach var="campagne" items="${campagnes}">
                <tr>
                    <td>${campagne.titre}</td>
                    <td>${campagne.montant} FCFA</td>
                    <td>${campagne.frequence}</td>
                    <td>${campagne.dateDebut}</td>
                    <td>${campagne.dateFin}</td>

                    <td>
                        <c:choose>
                            <c:when test="${paiements[campagne.id]}">
                                <span class="badge bg-success">
                                    PAYÉE
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge bg-warning text-dark">
                                    NON PAYÉE
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <c:choose>
                            <c:when test="${paiements[campagne.id]}">
                                <button class="btn btn-success btn-sm" disabled>
                                    Déjà payée
                                </button>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/membre/cotisations/payer?campagneId=${campagne.id}"
                                   class="btn btn-primary btn-sm">
                                    Payer
                                </a>
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