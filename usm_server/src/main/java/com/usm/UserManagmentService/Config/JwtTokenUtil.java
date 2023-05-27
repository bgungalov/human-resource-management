package com.usm.UserManagmentService.Config;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;

import com.usm.UserManagmentService.Entity.AuthenticatedUser;
import com.usm.UserManagmentService.Entity.User;
import com.usm.UserManagmentService.Entity.UserRoles;
import com.usm.UserManagmentService.Service.UserRolesService;
import com.usm.UserManagmentService.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.usm.UserManagmentService.Constants.Constants.ROLE_BASIC_USER;

@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRolesService userRolesService;

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 7200000; // 2 hours

    @Value("${jwt.secret}")
    private String secret = Base64.getEncoder().encodeToString("secret".getBytes(StandardCharsets.UTF_8));

    /**
     * Retrieve username from JWT Token.
     *
     * @param token current token to retrieve username from.
     * @return retrieved username.
     */
    public String getUsernameFromToken(String token) {

        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Retrieve exoiration date from JWT Token.
     *
     * @param token current token to retrieve exoiration date from.
     * @return retrieved exoiration date.
     */
    public Date getExpirationDateFromToken(String token) {

        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * "Given a token, get all the claims from it, and then apply the claimsResolver function to the claims."
     * <p>
     * The claimsResolver function is a lambda expression that takes a Claims object and returns a generic type T
     *
     * @param token          The JWT token
     * @param claimsResolver A function that takes in a Claims object and returns a value of type T.
     * @return A generic type T is being returned.
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);

        return claimsResolver.apply(claims);
    }

    /**
     * Retrieve any information from the token by providing the secret key.
     *
     * @param token current token to retrieve information from.
     * @return needed information.
     */
    private Claims getAllClaimsFromToken(String token) {

        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * Check if the token has expired.
     * If the expiration date of the token is before the current date, then the token is expired.
     *
     * @param token The JWT token.
     * @return A boolean value.
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        return expiration.before(new Date());
    }

    /**
     * Generate token for user.
     * We are creating a map of claims, which is a key-value pair of data that we want to store in the token. In this case,
     * we are storing the user's email and the user's roles. We are then creating a new AuthenticatedUser object, which is
     * a custom object that we created to store the user's email, roles, and name. We are then adding the AuthenticatedUser
     * object to the claims map. Finally, we are calling the doGenerateToken function, which is a private function that we
     * created to generate the token.
     *
     * @param userDetails This is the user object that is passed to the generateToken method.
     * @return A JWT token.
     */

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        User user = this.userService.getUserByEmail(userDetails.getUsername());

        List<UserRoles> activeRoles = userRolesService.extractActiveRolesFromUser(userRolesService.getUserRoles(user).stream().toList());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        activeRoles.forEach(userRole -> grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));

        if (activeRoles.isEmpty()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_BASIC_USER));
        }

        AuthenticatedUser authenticatedUser = new AuthenticatedUser(user.getId(), userDetails.getUsername(), grantedAuthorities,
                user.getFirstName(), user.getMiddleName(), user.getLastName());
        claims.put("user", authenticatedUser);

        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * While generating the JWT Token:
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(<a href="https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1">...</a>)
     * compaction of the JWT to a URL-safe string.
     *
     * @param claims  current claims.
     * @param subject current subject.
     * @return JWT string.
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * If the username in the token matches the username in the userDetails object, and the token is not expired, then
     * return true.
     *
     * @param token       The JWT token to validate.
     * @param userDetails The user details object that contains the username and password of the user.
     * @return A boolean value.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}