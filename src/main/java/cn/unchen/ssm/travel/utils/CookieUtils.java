package cn.unchen.ssm.travel.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 * @author chenjun
 * @version 1.0
 */
public final class CookieUtils {
    private CookieUtils() {
    }

    /**
     * 查找Cookie
     * @param cookies
     * @param cookieName
     * @return cookie or null
     */
    public static Cookie findCookie(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }

        return null;
    }

    /**
     * 查询cookie的值
     * @param cookies
     * @param cookieName
     * @return cookie value
     */
    public static String findCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    /**
     * 创建cookie
     * @param cookieName cookie名称
     * @param cookieValue  cookie值
     * @param maxAge    最大持久化时间
     * @param response  客户端相应对象
     */
    public static void addCookie(String cookieName, String cookieValue, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, cookieValue);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void deleteCookie(String username, String cookieValue, int i, HttpServletResponse response) {
        addCookie(username,cookieValue,i,response);
    }
}
