<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Créer une campagne</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">

    <div class="card shadow p-4">

        <h2 class="mb-4">Créer une campagne de cotisation</h2>

        <form action="${pageContext.request.contextPath}/admin/campagnes/ajouter"
              method="post">

            <div class="mb-3">
                <label class="form-label">Titre</label>
                <input type="text"
                       name="titre"
                       class="form-control"
                       placeholder="Ex : Cotisation mensuelle Mai"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">Montant à payer</label>
                <input type="number"
                       name="montant"
                       class="form-control"
                       min="0"
                       step="0.01"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">Fréquence</label>
                <select name="frequence" class="form-select" required>
                    <option value="JOURNALIER">Journalier</option>
                    <option value="HEBDOMADAIRE">Hebdomadaire</option>
                    <option value="MENSUEL">Mensuel</option>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Date début</label>
                <input type="date"
                       name="dateDebut"
                       class="form-control"
                       required>
            </div>

            <div class="mb-3">
                <label class="form-label">Date fin</label>
                <input type="date"
                       name="dateFin"
                       class="form-control">
            </div>

            <button type="submit" class="btn btn-primary">
                Créer campagne
            </button>

            <a href="${pageContext.request.contextPath}/admin/campagnes"
               class="btn btn-secondary">
                Annuler
            </a>

        </form>

    </div>

</div>

</body>
</html>