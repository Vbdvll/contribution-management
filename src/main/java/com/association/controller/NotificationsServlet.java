package com.association.controller;

import com.association.model.Notification;
import com.association.model.Utilisateur;
import com.association.service.NotificationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/notifications")
public class NotificationsServlet extends HttpServlet {

    private final NotificationService notificationService =
            new NotificationService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("utilisateurConnecte") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login"
            );
            return;
        }

        Utilisateur utilisateur =
                (Utilisateur) session.getAttribute(
                        "utilisateurConnecte"
                );

        List<Notification> notifications =
                notificationService.listerNotificationsUtilisateur(
                        utilisateur.getId()
                );

        notificationService.marquerToutesCommeLues(
                utilisateur.getId()
        );

        request.setAttribute("notifications", notifications);
        request.setAttribute("utilisateur", utilisateur);

        request.getRequestDispatcher("/notifications.jsp")
                .forward(request, response);
    }
}