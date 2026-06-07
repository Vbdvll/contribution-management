<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tableau de bord membre</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/membre-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header">
            <div><h1>Bonjour <c:out value="${membre.prenom}"/></h1><p>Voici votre situation financière.</p></div>
            <div class="header-actions">
                <a class="icon-link" href="${pageContext.request.contextPath}/notifications" title="Notifications">
                    <i class="bi bi-bell"></i>
                    <c:if test="${notificationsNonLues > 0}"><span class="notification-count">${notificationsNonLues}</span></c:if>
                </a>
            </div>
        </header>
        <section class="member-summary">
            <div class="member-avatar"><i class="bi bi-person"></i></div>
            <div><strong><c:out value="${membre.prenom}"/> <c:out value="${membre.nom}"/></strong><span>Numéro <c:out value="${membre.numero}"/> · Statut ${membre.statut}</span></div>
        </section>
        <section class="stats-grid">
            <div class="stat-box green"><div class="stat-label">Paiements validés</div><div class="stat-value">${paiementsValides}</div><div class="stat-note">Cotisations confirmées</div></div>
            <div class="stat-box yellow"><div class="stat-label">Anciens paiements</div><div class="stat-value">${paiementsEnAttente}</div><div class="stat-note">Encore en attente</div></div>
            <div class="stat-box red"><div class="stat-label">Amendes non payées</div><div class="stat-value">${amendesNonPayees}</div><div class="stat-note">Règlements en attente</div></div>
            <div class="stat-box"><div class="stat-label">Notifications</div><div class="stat-value">${notificationsNonLues}</div><div class="stat-note">Non lues</div></div>
        </section>
        <section class="dashboard-grid">
            <div class="panel">
                <div class="panel-header"><h2>Accès rapide</h2></div>
                <div class="quick-actions">
                    <a class="quick-action" href="${pageContext.request.contextPath}/membre/cotisations"><i class="bi bi-wallet2"></i><strong>Mes cotisations</strong><span>Voir les échéances et effectuer un paiement.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/membre/historique"><i class="bi bi-clock-history"></i><strong>Mon historique</strong><span>Consulter les paiements enregistrés.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/membre/amendes"><i class="bi bi-exclamation-circle"></i><strong>Mes amendes</strong><span>Vérifier les amendes et leur statut.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/notifications"><i class="bi bi-bell"></i><strong>Notifications</strong><span>Lire les dernières informations reçues.</span></a>
                </div>
            </div>
            <div class="panel">
                <div class="panel-header"><h2>Notifications</h2><a class="btn-dashboard" href="${pageContext.request.contextPath}/notifications">Voir tout</a></div>
                <div class="notification-list">
                    <c:if test="${empty notificationsRecentes}"><p class="empty-state">Aucune notification récente.</p></c:if>
                    <c:forEach var="notification" items="${notificationsRecentes}">
                        <div class="notification-item"><strong><c:out value="${notification.titre}"/></strong><p><c:out value="${notification.message}"/></p><small>${notification.dateCreation.toLocalDate()}</small></div>
                    </c:forEach>
                </div>
            </div>
        </section>
    </main>
</div>
</body>
</html>
