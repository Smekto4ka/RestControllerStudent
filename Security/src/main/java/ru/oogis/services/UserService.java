package ru.oogis.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.oogis.entity.Role;
import ru.oogis.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private Map<String, User> users = new HashMap<>();

    public UserService() {
        User user = new User("sasa", "sasa");
        user.setRole(new Role("ROLE_ADMIN"));
        users.put(user.getUsername(), user);
        System.out.println(users);
    }

    public User findByUsername(String name) {
        return users.get(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        User user = findByUsername(username);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                , user.getPassword()
                , mapRolesToGrantedAuthority(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToGrantedAuthority(List<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
