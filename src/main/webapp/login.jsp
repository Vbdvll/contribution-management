<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Connexion - Gestion Cotisations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/auth.css" rel="stylesheet">
</head>
<body>
<main class="auth-layout">
    <section class="auth-intro">
        <div class="auth-brand">
            <i class="bi bi-people-fill"></i>
            <span>Gestion Cotisations</span>
        </div>
        <div>
            <h1>Votre association, simplement suivie.</h1>
            <p>Campagnes, paiements et notifications dans un espace commun.</p>
        </div>
        <span class="auth-note">Espace membres et administration</span>
    </section>

    <section class="auth-form-area">
        <div class="auth-form">
            <div class="auth-heading">
                <span class="auth-mobile-brand">Gestion Cotisations</span>
                <h2>Connexion</h2>
                <p>Accédez à votre espace personnel.</p>
            </div>

            <c:if test="${param.inscription == 'success'}">
                <div class="alert alert-success">Compte créé. Vous pouvez maintenant vous connecter.</div>
            </c:if>
            <c:if test="${not empty erreur}">
                <div class="alert alert-danger"><c:out value="${erreur}"/></div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <div class="mb-3">
                    <label class="form-label" for="email">Email</label>
                    <input id="email" type="email" name="email" class="form-control"
                           maxlength="100" autocomplete="email" required>
                </div>
                <div class="mb-4">
                    <label class="form-label" for="motDePasse">Mot de passe</label>
                    <input id="motDePasse" type="password" name="motDePasse"
                           class="form-control" maxlength="72"
                           autocomplete="current-password" required>
                </div>
                <button type="submit" class="auth-button">
                    Se connecter <i class="bi bi-arrow-right"></i>
                </button>
            </form>

            <p class="auth-switch">
                Pas encore membre ?
                <a href="${pageContext.request.contextPath}/inscription">Créer un compte</a>
            </p>
        </div>
    </section>
</main>
</body>
</html>
