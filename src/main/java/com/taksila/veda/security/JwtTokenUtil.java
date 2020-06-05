package com.taksila.veda.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.taksila.veda.model.db.base.v1_0.UserRole;
import com.taksila.veda.model.db.security.v1_0.UserSession;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil implements Serializable 
{

    private static final long serialVersionUID = -3301605591108950415L;

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_AUDIENCE = "audience";
    static final String CLAIM_KEY_CREATED = "created";
    static final String CLAIM_KEY_ROLES = "roles";
    static final String CLAIM_KEY_SESSION_ID = "sessid";

    private static final String AUDIENCE_UNKNOWN = "unknown";
    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getUsernameFromToken(String token) 
    {
        String username;
        try 
        {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } 
        catch (Exception e) 
        {
            username = null;
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public String getSessionIdFromToken(String token) {
        String sessionid;
        try {
            final Claims claims = getClaimsFromToken(token);
            sessionid = (String) claims.get(CLAIM_KEY_SESSION_ID);
        } catch (Exception e) {
            sessionid = null;
        }
        return sessionid;
    }


    public List<UserRole> getRolesFromToken(String token) 
    {
        List<UserRole> userRoles = new ArrayList<UserRole>();
        try 
        {
            final Claims claims = getClaimsFromToken(token);
            List<String> rolesClaim = new ArrayList<String>();
            if (claims.get(CLAIM_KEY_ROLES) != null)
            	rolesClaim = (List<String>) claims.get(CLAIM_KEY_ROLES);
            
//            String[] roles = rolestr.split(",");
//            
            for(String r : rolesClaim)
            {            	
            	try
            	{
            		userRoles.add(UserRole.fromValue(r));
            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            	}
            }
        } catch (Exception e) 
        {
        	e.printStackTrace();
        }
        return userRoles;
    }
    
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private String generateAudience(JwtEndClientDevice device) {
            	         		
        return device.platForm.name();
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = getAudienceFromToken(token);
        return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
    }

    public String generateToken(UserSession userSession, JwtEndClientDevice device) throws Exception {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userSession.getUserId());
        claims.put(CLAIM_KEY_AUDIENCE, generateAudience(device));
        claims.put(CLAIM_KEY_SESSION_ID, userSession.getId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        String[] roles = {"ADMIN","TEACHER"};
        claims.put(CLAIM_KEY_ROLES, roles);
        return generateToken(claims);
    }

    String generateToken(Map<String, Object> claims) throws Exception
    {
        if (this.secret == null)
        {           
           throw new Exception("  MISSING JWT TOKEN SECRET  ");
        }

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getCreatedDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public String refreshToken(String token) 
    {
        String refreshedToken;
        try 
        {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } 
        catch (Exception e) 
        {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, UserSession userSession) 
    {             
        final String username = getUsernameFromToken(token);        
        //final Date expiration = getExpirationDateFromToken(token);
        return (username.equals(userSession.getUserId())  && !isTokenExpired(token));
    }
}