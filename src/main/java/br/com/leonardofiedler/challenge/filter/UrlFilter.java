package br.com.leonardofiedler.challenge.filter;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

@Component
@Order(1)
public class UrlFilter implements Filter {

    @Autowired
    private UrlService urlService;

    //The HTTPS here was defined due to problems to redirect
    private static final String URL_SCHEME_DEFAULT = "https://";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri[] = req.getRequestURI().split("/");

        //Check the URI length
        if (uri.length > 3 || uri.length <= 0) {
            resp.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "URI muito curta ou muito longa.");
            return;
        }

        String path = uri[1];
        //If not in a known route or if path not contains . character (this character could be the difference)
        //from an internal and external route verification.
        if (!path.equalsIgnoreCase("tiny-url") && !path.contains(URL_SCHEME_DEFAULT)) {
            Url url = urlService.findByNewUrl(path);

            //Check if route is valid
            if (url == null) {
                resp.sendError(HttpStatus.NOT_FOUND.value(), "URL encurtada não encontrada no servidor.");
                return;
            }

            Calendar currTime = Calendar.getInstance();
            currTime.setTime(new Date());

            //Check if the current time is higher than the url expires at.
            if (currTime.getTimeInMillis() > url.getExpiresAt()) {
                resp.sendError(HttpStatus.GONE.value(), "URL já expirada.");
                return;
            }

            //Redirect to URL
            resp.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
            resp.sendRedirect(URL_SCHEME_DEFAULT.concat(url.getLink()));
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
