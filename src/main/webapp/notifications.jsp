<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="notifications" scope="request"/>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Notifications</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/dashboard.css" rel="stylesheet">
</head>
<body>
<div class="dashboard-layout">
    <c:choose>
        <c:when test="${utilisateur.role == 'ADMIN'}">
            <%@ include file="/WEB-INF/fragments/admin-sidebar.jspf" %>
        </c:when>
        <c:otherwise>
            <%@ include file="/WEB-INF/fragments/membre-sidebar.jspf" %>
        </c:otherwise>
    </c:choose>
    <main class="dashboard-main">
        <header class="dashboard-header">
            <div><h1>Notifications</h1><p>Consulter les dernières informations reçues.</p></div>
        </header>
        <section class="content-panel">
            <c:if test="${empty notifications}"><p class="empty-state">Aucune notification pour le moment.</p></c:if>
            <c:forEach var="notification" items="${notifications}">
                <article class="notification-card ${notification.lu ? '' : 'unread'}">
                    <strong><c:out value="${notification.titre}"/></strong>
                    <p><c:out value="${notification.message}"/></p>
                    <small>${notification.dateCreation}</small>
                </article>
            </c:forEach>
        </section>
    </main>
</div>
</body>
</html>
