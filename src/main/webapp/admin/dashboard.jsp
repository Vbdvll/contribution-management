<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Tableau de bord administrateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
    <main class="dashboard-main">
        <header class="dashboard-header">
            <div><h1>Tableau de bord</h1><p>Situation générale de l'association.</p></div>
            <div class="header-actions">
                <a class="icon-link" href="${pageContext.request.contextPath}/notifications" title="Notifications">
                    <i class="bi bi-bell"></i>
                    <c:if test="${notificationsNonLues > 0}"><span class="notification-count">${notificationsNonLues}</span></c:if>
                </a>
            </div>
        </header>
        <section class="stats-grid">
            <div class="stat-box"><div class="stat-label">Membres</div><div class="stat-value">${totalMembres}</div><div class="stat-note">Membres enregistrés</div></div>
            <div class="stat-box green"><div class="stat-label">Cotisations encaissées</div><div class="stat-value">${totalCotisations}</div><div class="stat-note">FCFA reçus</div></div>
            <div class="stat-box yellow"><div class="stat-label">Paiements en attente</div><div class="stat-value">${paiementsEnAttente}</div><div class="stat-note">À valider</div></div>
            <div class="stat-box red"><div class="stat-label">Amendes non payées</div><div class="stat-value">${totalAmendesNonPayees}</div><div class="stat-note">FCFA à recouvrer</div></div>
        </section>
        <section class="dashboard-grid">
            <div class="panel">
                <div class="panel-header"><h2>Actions rapides</h2><span>${campagnesActives} campagne(s) active(s)</span></div>
                <div class="quick-actions">
                    <a class="quick-action" href="${pageContext.request.contextPath}/admin/membres/ajouter"><i class="bi bi-person-plus"></i><strong>Ajouter un membre</strong><span>Créer son compte et sa fiche.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/admin/campagnes/ajouter"><i class="bi bi-calendar-plus"></i><strong>Créer une campagne</strong><span>Définir montant et fréquence.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/admin/cotisations"><i class="bi bi-receipt-cutoff"></i><strong>Suivre les paiements</strong><span>Consulter les transactions.</span></a>
                    <a class="quick-action" href="${pageContext.request.contextPath}/admin/amendes"><i class="bi bi-receipt"></i><strong>Suivre les amendes</strong><span>Voir les montants non réglés.</span></a>
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
