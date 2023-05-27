package com.usm.UserManagmentService.Service;

import java.util.ArrayList;
import java.util.List;

import com.usm.UserManagmentService.Entity.UserCredentials;
import com.usm.UserManagmentService.Entity.UserRoles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    UserRolesService userRolesService;

    @Autowired
    UserCredentialsService userCredentialsService;

    @Autowired
    UserService userService;

    /**
     * It takes a username, finds the user in the database, extracts the roles from the user, and returns a UserDetails
     * object with the username, password, and roles.
     *
     * @param username The username of the user you want to load.
     * @return User object.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.usm.UserManagmentService.Entity.User user = userService.getUserByEmail(username);

        UserCredentials userCredentials = this.userCredentialsService.loadUserByUsername(username);

        if (username.equals(userCredentials.getUsername())) {
            return new User(userCredentials.getUsername(), userCredentials.getPassword(),
                    getGrantedAuthority(userRolesService.extractActiveRolesFromUser(user.getRoles())));
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    /**
     * It takes a list of user roles and returns a list of granted authorities.
     *
     * @param userRoles This is the list of roles that the user has.
     * @return A list of GrantedAuthority objects.
     */
    private List<GrantedAuthority> getGrantedAuthority(List<UserRoles> userRoles) {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        userRoles.forEach(userRole -> grantedAuthorities.add(new SimpleGrantedAuthority(userRole.getRoleName())));

        return grantedAuthorities;
    }
}