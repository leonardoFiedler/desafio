package br.com.leonardofiedler.challenge.filter;

import br.com.leonardofiedler.challenge.model.Url;
import br.com.leonardofiedler.challenge.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class UrlFilter implements Filter {

    @Autowired
    private UrlRepository urlRepository;

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
            throw new ServletException("URI muito curta ou muito longa");
        }

        String path = uri[1];
        //If not in a known route
        if (!path.equalsIgnoreCase("tiny-url")) {
            Url url = urlRepository.findByNewUrl(path);

            //Check if route is valid
            if (url == null) {
                throw new ServletException("Rota inválida");
            }

            //TODO: Check if route is expired
            resp.sendRedirect(url.getLink());
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
