//
//package com.updis.web;
//
//import org.jasig.cas.authentication.principal.SimpleWebApplicationServiceImpl;
//import org.jasig.cas.services.RegisteredService;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.AbstractController;
//import org.springframework.web.servlet.view.RedirectView;
//import org.jasig.cas.web.logoutController;
///**
// * Controller to delete ticket granting ticket cookie in order to log out of
// * single sign on. This controller implements the idea of the ESUP Portail's
// * Logout patch to allow for redirecting to a url on logout. It also exposes a
// * log out link to the view via the WebConstants.LOGOUT constant.
// *
// * @author Scott Battaglia
// * @since 3.0
// */
//public final class LogoutController extends logoutController {
//
//
//
//    @Override
//    protected ModelAndView handleRequestInternal(
//        final HttpServletRequest request, final HttpServletResponse response)
//        throws Exception {
//        final String ticketGrantingTicketId = this.ticketGrantingTicketCookieGenerator.retrieveCookieValue(request);
//        final String service = request.getParameter("service");
//
//        if (ticketGrantingTicketId != null) {
//            this.centralAuthenticationService
//                .destroyTicketGrantingTicket(ticketGrantingTicketId);
//
//            this.ticketGrantingTicketCookieGenerator.removeCookie(response);
//            this.warnCookieGenerator.removeCookie(response);
//        }
//
//        if (this.followServiceRedirects && service != null) {
//            final RegisteredService rService = this.servicesManager.findServiceBy(new SimpleWebApplicationServiceImpl(service));
//
//            if (rService != null && rService.isEnabled()) {
//                return new ModelAndView(new RedirectView(service));
//            }
//        }
//
//        return new ModelAndView(this.logoutView);
//    }
//}
