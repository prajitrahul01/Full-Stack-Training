package org.example.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Service.TokenService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Configuration // among the other beans this is the first priority
public class JwtFilter extends GenericFilterBean {
    private TokenService tokenService;

    // Constructor injection of TokenService for dependency injection
    public JwtFilter(TokenService tokenService){ this.tokenService = tokenService; }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
        String token = httpServletRequest.getHeader("Authorization");

        System.out.println("Token: "+ token);
        // Handling pre-flight requests (OPTIONS)
        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
            // Pre-flight requests are allowed, so set the status to OK and proceed
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req, res);
            return;
        }

        // Check if the request can proceed without a token
        if (AllowRequestWithoutToken(httpServletRequest)) {
            // Requests to specific paths are allowed without a token, so set the status to OK and proceed
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(req, res);
            return;
        }

        // Check if the Authorization header is present and starts with "Bearer "
        if (token == null || !token.startsWith("Bearer ")) {
            // No valid token found and request path is not allowed without a token
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bearer token missing or invalid");
            return;
        }

        // Extract the token without "Bearer " prefix
        String jwtToken = token.substring(7);

        // Validate and extract user ID from the token
        String id = tokenService.getUserIdToken(jwtToken);
        if (id == null) {
            httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        // Set user ID as a request attribute
        httpServletRequest.setAttribute("userId", id.toString());

        // Proceed with the filter chain after setting the user ID attribute
        filterChain.doFilter(req, res);
    }



    //    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws ServletException, IOException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest)  req;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
//        String authorizationHeader = httpServletRequest.getHeader("Authorization");
//
//        // Handling pre-flight requests (OPTIONS)
//        if ("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())) {
//            // Pre-flight requests are allowed, so set the status to OK and proceed
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(req, res);
//        } else {
//            // Extract token from the Authorization header
//            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//                // If token is missing or not of Bearer type, return unauthorized
//                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                return;
//            }
//            String token = authorizationHeader.substring(7); // Extract token excluding "Bearer "
//
//            // Allowing requests without token for specific paths
//            if (AllowRequestWithoutToken(httpServletRequest)) {
//                // Requests to specific paths are allowed without a token, so set the status to OK and proceed
//                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//                filterChain.doFilter(req, res);
//            } else {
//                try {
//                    // Extract user ID from the token and set it as a request attribute
//                    Integer id = new Integer(tokenService.getUserIdToken(token));
//                    httpServletRequest.setAttribute("userId", id);
//                    // Proceed with the filter chain after setting the user ID attribute
//                    filterChain.doFilter(req, res);
//                } catch (Exception e) {
//                    // If any exception occurs, return unauthorized
//                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                }
//            }
//        }
//    }
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws ServletException, IOException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest)  req;
//        HttpServletResponse httpServletResponse = (HttpServletResponse) res;
//        String token = httpServletRequest.getHeader("Authorization");
//        // Handling pre-flight requests (OPTIONS)
//        if("OPTIONS".equalsIgnoreCase(httpServletRequest.getMethod())){
//            // Pre-flight requests are allowed, so set the status to OK and proceed
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(req, res);
//        }
//        // Allowing requests without token for specific paths
//        if(AllowRequestWithoutToken(httpServletRequest)){
//            // Requests to specific paths are allowed without a token, so set the status to OK and proceed
//            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
//            filterChain.doFilter(req, res);
//        }
//        else{
//            // Extract user ID from the token and set it as a request attribute
//            Integer id = new Integer(tokenService.getUserIdToken(token));
//            httpServletRequest.setAttribute("userId","id");
//            // Proceed with the filter chain after setting the user ID attribute
//            filterChain.doFilter(req, res);
//        }
//    }
    // Method to determine whether the request should be allowed without a token
    public boolean AllowRequestWithoutToken(HttpServletRequest httpServletRequest){
        System.out.println(httpServletRequest.getRequestURI());
        // Allow requests without a token for paths containing "/user"
        if(httpServletRequest.getRequestURI().contains("/"))
//        if(httpServletRequest.getRequestURI().contains("/api/user/login") || httpServletRequest.getRequestURI().contains("/api/user/registration") || httpServletRequest.getRequestURI().contains("/api/user/getAll2"))
            return true;
        else
            return false;
    }
}

