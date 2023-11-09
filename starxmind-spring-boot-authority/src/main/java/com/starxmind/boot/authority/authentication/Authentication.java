package com.starxmind.boot.authority.authentication;


import com.starxmind.boot.authority.user.AuthorizedUser;
import com.starxmind.boot.authority.user.UserContextHolder;

import javax.servlet.http.HttpServletRequest;

/**
 * Authentication
 *
 * @author Xpizza
 * @since starxmind1.0
 */
public interface Authentication<U extends AuthorizedUser> {
    /**
     * authentication name
     *
     * @return
     */
    String authenticationName();

    /**
     * parse user from HttpServletRequest
     *
     * @param request
     * @return
     */
    U parseUser(HttpServletRequest request);

    /**
     * cache user
     *
     * @param user authorized user
     */
    void cacheUser(U user);

    default U getUser() {
        return (U) UserContextHolder.get();
    }
}
