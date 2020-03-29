package com.audioapp.cms.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.audioapp.cms.dto.UserDTO;
import com.audioapp.cms.service.RedisService;
import org.springframework.util.StringUtils;

/**
 * 登录过滤器
 * @author Administrator
 *
 */
@Component
@WebFilter(urlPatterns="/cms/*", filterName="LoginFilter")
public class LoginFilter implements Filter {
	
	@Autowired
	private RedisService redisService;

	private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/main/excludefilter", "/login","/updateUserPassword", "/goLogin", "/register")));

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String url = req.getRequestURI();
		if(url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".jpg") || url.endsWith(".ico") ||
				url.endsWith(".gif") || url.endsWith(".php") || url.endsWith(".ttf") || url.endsWith(".less") ||
				url.endsWith(".woff") || url.endsWith("login.html") || url.endsWith(".woff2")) {
			chain.doFilter(request, response);
			return;
		}
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				if("CMS_TOKEN".equals(cookie.getName())){
					String tokenKey = cookie.getValue();
					String tokenValue = redisService.get(tokenKey);
					if(!StringUtils.isEmpty(tokenValue) && !"null".equals(tokenValue)){
						redisService.expireS(tokenKey, 1800L);//刷新超时时间0.5小时
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}
		
		String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
	    boolean allowedPath = ALLOWED_PATHS.contains(path);
	    if (allowedPath) {
	    	chain.doFilter(request, response);
	    	return;
	    }
	    
	    HttpServletResponse res = (HttpServletResponse) response;
	    //如果有cookie 要清理cookie
	    if(cookies != null && cookies.length > 0){
			for(Cookie cookie : cookies){
				if("CMS_TOKEN".equals(cookie.getName())){
					cookie.setValue(null);  
                    cookie.setMaxAge(0);// 立即销毁cookie  
                    cookie.setPath("/");  
                    System.out.println("被删除的cookie名字为:"+cookie.getName());  
                    res.addCookie(cookie);
				}
			}
		}
	    res.sendRedirect("goLogin");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
