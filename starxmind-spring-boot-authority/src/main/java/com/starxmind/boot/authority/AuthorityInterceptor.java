package com.starxmind.boot.authority;

import com.starxmind.boot.authority.anno.SkipAuthority;
import com.starxmind.boot.authority.authentication.Authentication;
import com.starxmind.boot.authority.user.AuthorizedUser;
import com.starxmind.boot.authority.user.UserContextHolder;
import com.starxmind.boot.utils.ApplicationContextHolder;
import com.starxmind.boot.utils.ExcludeResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

import static com.starxmind.boot.utils.PathUtils.PATH_MATCHER;

/**
 * Authority interceptor
 *
 * @author Xpizza
 * @since starxmind1.0
 */
@Slf4j
@RequiredArgsConstructor
public class AuthorityInterceptor implements HandlerInterceptor, InitializingBean {
    /**
     * All registered authentications
     */
    private static Collection<Authentication> AUTHENTICATIONS;

    private final ApplicationContextHolder applicationContextHolder;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, Authentication> authenticationMap = applicationContextHolder.getApplicationContext().getBeansOfType(Authentication.class);
        AUTHENTICATIONS = authenticationMap.values();
        log.info("<Starxmind Authority> has found <{}> authentications", authenticationMap.size());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // remove current thread
        UserContextHolder.remove();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        // Pass static pages
        if (isExclude(requestURI)) {
            log.debug("Pass exclude path - {}", requestURI);
            return true;
        }

        // Ignore authority
        if (ignoreAuthority(handler)) {
            log.debug("Pass ignore handler - {}", requestURI);
            return true;
        }

        // Validate user if authorized
        AuthorizedUser authorizedUser = null;
        for (Authentication authentication : AUTHENTICATIONS) {
            // If the authentication match the request, then enable it
            if (!authentication.enableIf(request)) {
                continue;
            }
            // Parse the authorized user
            authorizedUser = authentication.parseUser(request);
            if (authorizedUser != null) {
                // Set the authorized user into current thread
                UserContextHolder.set(authorizedUser);
                // Cache the authorized user
                authentication.cacheUser(authorizedUser);
                log.debug("This authentication succeeds - authentication name:{}", authentication.authenticationName());
                break;
            }
        }

        if (authorizedUser == null) {
            log.error("Unauthorized exception: can not find user...");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    /**
     * Determine whether the handler ignore authority
     *
     * @param handler A handler in the spring controllers
     * @return
     */
    private boolean ignoreAuthority(Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        SkipAuthority skipAuthority = ((HandlerMethod) handler).getMethodAnnotation(SkipAuthority.class);
        return skipAuthority != null;
    }

    private boolean isExclude(String requestURI) {
        return ExcludeResources.match(requestURI) ||
                PATH_MATCHER.match("/**/open/**", requestURI)
                ;
    }
}
