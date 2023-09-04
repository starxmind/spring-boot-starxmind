package com.starxmind.boot.authority.user;

/**
 * User context holder
 *
 * @author Xpizza
 * @since starxmind1.0
 */
public class UserContextHolder {
    private final static ThreadLocal<AuthorizedUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(AuthorizedUser userVo) {
        USER_THREAD_LOCAL.set(userVo);
    }

    public static AuthorizedUser get() {
        return USER_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
    }
}
