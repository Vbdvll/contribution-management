<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ page import="java.time.LocalDate" %>
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

        <div class="d-flex justify-content-between mb-4">

            <div>
                <h2>Mes cotisations</h2>
                <p class="text-muted">
                    Bonjour ${membre.prenom}, voici vos échéances.
                </p>
            </div>

            <a href="${pageContext.request.contextPath}/membre/dashboard"
               class="btn btn-secondary">
                Retour
            </a>

        </div>

        <c:set var="today" value="<%= LocalDate.now() %>" />

        <c:if test="${not empty param.success}">
            <div class="alert alert-success">
                    ${param.success}
            </div>
        </c:if>

        <c:if test="${not empty param.erreur}">
            <div class="alert alert-danger">
                    ${param.erreur}
            </div>
        </c:if>

        <table class="table table-bordered">

            <thead class="table-dark">

            <tr>
                <th>Campagne</th>
                <th>Montant</th>
                <th>Fréquence</th>
                <th>Échéance</th>
                <th>Statut</th>
                <th>Action</th>
            </tr>

            </thead>

            <tbody>

            <c:forEach var="ligne" items="${lignes}">

                <tr>

                    <td>${ligne.campagne.titre}</td>

                    <td>${ligne.campagne.montant} FCFA</td>

                    <td>${ligne.campagne.frequence}</td>

                    <td>${ligne.dateEcheance}</td>

                    <td>

                        <c:choose>

                            <c:when test="${ligne.statut=='PAYEE'}">
<span class="badge bg-success">
PAYÉE
</span>
                            </c:when>

                            <c:when test="${ligne.statut=='EN_ATTENTE'}">
<span class="badge bg-warning text-dark">
EN ATTENTE
</span>
                            </c:when>

                            <c:when test="${ligne.statut=='EN_RETARD'}">
<span class="badge bg-danger">
EN RETARD
</span>
                            </c:when>

                            <c:otherwise>
<span class="badge bg-secondary">
NON PAYÉE
</span>
                            </c:otherwise>

                        </c:choose>

                    </td>

                    <td>

                        <c:choose>

                            <c:when test="${ligne.statut=='PAYEE'}">

                                <button class="btn btn-success btn-sm" disabled>
                                    Déjà payée
                                </button>

                            </c:when>

                            <c:when test="${ligne.statut=='EN_ATTENTE'}">

                                <button class="btn btn-warning btn-sm" disabled>
                                    En attente validation
                                </button>

                            </c:when>

                            <c:when test="${ligne.dateEcheance > today}">

                                <button class="btn btn-secondary btn-sm" disabled>
                                    Pas encore disponible
                                </button>

                            </c:when>

                            <c:when test="${ligne.statut=='EN_RETARD' and !ligne.campagne.retardTolere}">

                                <button class="btn btn-dark btn-sm" disabled>
                                    Retard non autorisé
                                </button>

                            </c:when>

                            <c:when test="${ligne.statut=='EN_RETARD'}">

                                <a href="${pageContext.request.contextPath}/membre/cotisations/payer?campagneId=${ligne.campagne.id}&dateEcheance=${ligne.dateEcheance}"
                                   class="btn btn-danger btn-sm">

                                    Paiement en retard

                                </a>

                            </c:when>

                            <c:otherwise>

                                <a href="${pageContext.request.contextPath}/membre/cotisations/payer?campagneId=${ligne.campagne.id}&dateEcheance=${ligne.dateEcheance}"
                                   class="btn btn-primary btn-sm">

                                    Déclarer paiement

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