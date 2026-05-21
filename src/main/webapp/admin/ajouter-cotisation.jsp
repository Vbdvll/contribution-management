<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Enregistrer un paiement</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <h2 class="mb-4">Enregistrer le paiement d’une cotisation</h2>

        <c:if test="${not empty erreur}">
            <div class="alert alert-danger">
                    ${erreur}
            </div>
        </c:if>

        <form action="${pageContext.request.contextPath}/admin/cotisations/ajouter"
              method="post">

            <div class="mb-3">
                <label class="form-label">Membre</label>
                <select name="membreId" class="form-select" required>
                    <c:forEach var="membre" items="${membres}">
                        <option value="${membre.id}">
                                ${membre.numero} - ${membre.prenom} ${membre.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Campagne de cotisation</label>
                <select name="campagneId" class="form-select" required>
                    <c:forEach var="campagne" items="${campagnes}">
                        <option value="${campagne.id}">
                                ${campagne.titre} - ${campagne.montant} FCFA - ${campagne.frequence}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Mode de paiement</label>
                <select name="modePaiement" class="form-select" required>
                    <option value="Espèces">Espèces</option>
                    <option value="Virement">Virement</option>
                    <option value="Chèque">Chèque</option>
                    <option value="Mobile Money">Mobile Money</option>
                </select>
            </div>
            <div class="mb-3">
                <label class="form-label">Date échéance</label>
                <input type="date"
                       name="dateEcheance"
                       class="form-control"
                       required>
            </div>

            <button type="submit" class="btn btn-primary">
                Enregistrer paiement
            </button>

            <a href="${pageContext.request.contextPath}/admin/cotisations"
               class="btn btn-secondary">
                Annuler
            </a>

        </form>

    </div>

</div>

</body>
</html>