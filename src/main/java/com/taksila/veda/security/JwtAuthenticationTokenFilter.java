//package com.taksila.veda.security;
//
//import java.io.IOException;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.taksila.servlet.utils.ServletUtils;
//import com.taksila.veda.usermgmt.UserComponent;
//
//public class JwtAuthenticationTokenFilter extends OncePerRequestFilter 
//{
//
//    private final Log logger = LogFactory.getLog(this.getClass());
//
//    @Autowired
//	ApplicationContext applicationContext;
//        
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Value("${jwt.header}")
//    private String tokenHeader;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException 
//    {
//        String authToken = request.getHeader(this.tokenHeader);
//        logger.info("recieved user authorization token "+authToken); 
//        // authToken.startsWith("Bearer ")
//        // String authToken = header.substring(7);
//        String username = jwtTokenUtil.getUsernameFromToken(authToken);
//
//        logger.info("checking authentication for user for token " + username);
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) 
//        {
//            /*
//             * It is not compelling necessary to load the use details from the database. You could also store the information
//             *in the token and read it from it. It's up to you ;)
//             */
//        	UserComponent userDetailsService = applicationContext.getBean(UserComponent.class,ServletUtils.getSubDomain(request.getRequestURI()));
//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            /*
//             *  For simple validation it is completely sufficient to just check the token integrity. You don't have to call the database compellingly.
//             */            
//            if (jwtTokenUtil.validateToken(authToken, userDetails)) 
//            {
//            	logger.info("authenticated user " + username + ", setting security context");
//            	UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));                
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}