<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Générer une amende</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow p-4">

        <h2 class="mb-4">Générer une amende</h2>

        <form action="${pageContext.request.contextPath}/admin/amendes/generer" method="post">

            <div class="mb-3">
                <label class="form-label">Membre</label>
                <select name="membreId" class="form-select" required>
                    <c:forEach var="membre" items="${membres}">
                        <option value="${membre.id}"
                            ${membreId == membre.id ? 'selected' : ''}>
                                ${membre.numero} - ${membre.prenom} ${membre.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Campagne concernée</label>
                <select name="campagneId" class="form-select" required>
                    <c:forEach var="campagne" items="${campagnes}">
                        <option value="${campagne.id}"
                            ${campagneId == campagne.id ? 'selected' : ''}>
                                ${campagne.titre} - ${campagne.montant} FCFA
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Montant de l’amende</label>
                <input type="number"
                       name="montant"
                       class="form-control"
                       value="1000"
                       min="0"
                       step="0.01"
                       required>
            </div>

            <button type="submit" class="btn btn-warning">
                Générer
            </button>

            <a href="${pageContext.request.contextPath}/admin/amendes"
               class="btn btn-secondary">
                Annuler
            </a>

        </form>

    </div>
</div>

</body>
</html>