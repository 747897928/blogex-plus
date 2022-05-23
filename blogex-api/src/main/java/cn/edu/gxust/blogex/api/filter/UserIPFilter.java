package cn.edu.gxust.blogex.api.filter;

import cn.edu.gxust.blogex.api.utils.NetworkUtils;
import cn.edu.gxust.blogex.common.Constants;
import cn.edu.gxust.blogex.common.enums.ErrorCode;
import cn.edu.gxust.blogex.common.response.Result;
import cn.edu.gxust.blogex.common.utils.JSONUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author zhaoyijie
 * @since 2022/5/23 20:03
 */
@Order(0)
@Component
@WebFilter(filterName = "userIPFilter", urlPatterns = {"/**"})
public class UserIPFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
        //获取用户ip，写入到请求头里
        String ipAddress = NetworkUtils.getIPAddress(requestWrapper);
        requestWrapper.addHeader(Constants.X_USER_IP, ipAddress);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
