package tdh.tnt.security.demo.webapp;

import org.apache.commons.lang.StringUtils;
import tdh.thunder.common.utils.ConfigProperties;
import tdh.tnt.security.demo.framework.Constants;
import tdh.tnt.security.demo.framework.model.Subject;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static tdh.tnt.security.demo.framework.Constants.CONFIG_NAME;

/**
 * Created by eric on 2017/10/10.
 */
public class LoginServlet extends GenericServlet{
    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String userId = req.getParameter("userId");
        String password = req.getParameter("password");

        String userPassword = ConfigProperties.getInstance(CONFIG_NAME).getPropertyAsString("subject.password." + userId);

        if (StringUtils.isBlank(userPassword)) {
            req.setAttribute("LOGIN_ERROR", "用户不存在");
            req.getRequestDispatcher("/login.jsp").forward(req, res);

            return;
        }


        if (userPassword.equals(password)) {

            Subject subject = new Subject();

            subject.setUserId(userId);

            req.getSession(true).setAttribute(Constants.SESSION_KEY_SUBJECT, subject);

            res.sendRedirect("/index.jsp");

        }else{
            req.setAttribute("LOGIN_ERROR", "用户名密码不正确");
            req.getRequestDispatcher("/login.jsp").forward(req, res);
        }

    }
}
