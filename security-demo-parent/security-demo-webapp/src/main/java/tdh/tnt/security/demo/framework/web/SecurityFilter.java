package tdh.tnt.security.demo.framework.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tdh.thunder.common.utils.ConfigProperties;
import tdh.tnt.security.demo.framework.Constants;
import tdh.tnt.security.demo.framework.model.Subject;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by eric on 2017/10/10.
 */
public class SecurityFilter implements Filter{

    private static final Logger LOG = LoggerFactory.getLogger(SecurityFilter.class);
    public static final String LOGIN_URI = "/login.jsp";
    public static final String HTTP403_URI = "/403.jsp";
    public static final String LOGIN_ACTION_URI = "/login.do";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOG.info("SecurityFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String uri = req.getRequestURI();

        LOG.info("User is requesting uri:{}", uri);

        if (!isLogin(uri)) {

            // check if user has already logged in
            HttpSession session = req.getSession(true);

            Subject subject = (Subject) session.getAttribute(Constants.SESSION_KEY_SUBJECT);

            if (null == subject) {
                gotoLogin(req, res);

                return;
            }

            boolean authorized = hasAuthorized(uri, subject);

            if (authorized) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                res.sendError(403);
            }

        }else{
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }

    private boolean hasAuthorized(String uri, Subject subject) {
        String userId = subject.getUserId();

        String[] roles = findUserRoles(userId);

        for (String role : roles) {

            String[] uris = findRoleUri(role);

            for (String roleUri : uris) {

                if (uri.equals(roleUri)) {
                   return true;
                }
            }

        }

        return false;
    }

    private String[] findUserRoles(String userId) {

        String rolesStr = ConfigProperties.getInstance(Constants.CONFIG_NAME).getPropertyAsString("subject.roles." + userId, "");

        return rolesStr.split(",");
    }

    private String[] findRoleUri(String role){
        String uriStr = ConfigProperties.getInstance(Constants.CONFIG_NAME).getPropertyAsString("roles.config."+ role +".uri" , "");

        return uriStr.split(",");
    }

    private void goto403(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect(HTTP403_URI);
    }

    private void gotoLogin(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect(LOGIN_URI);
    }

    private boolean isLogin(String uri) {
        return LOGIN_URI.equals(uri) || LOGIN_ACTION_URI.equals(uri);
    }

    @Override
    public void destroy() {
        LOG.info("SecurityFilter destroyed.");
    }
}
