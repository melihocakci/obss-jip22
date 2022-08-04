package tr.com.obss.jip.bookportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.obss.jip.bookportal.RoleType;
import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.service.RoleService;
import tr.com.obss.jip.bookportal.service.UserService;

import java.util.Arrays;
import java.util.List;

@Configuration
public class AppInitConfig {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            final List<RoleType> allRoles = roleService.getAllRoles().stream().map(Role::getName).toList();

            Arrays.stream(RoleType.values()).filter(roleType -> !allRoles.contains(roleType)).forEach(roleType -> {
                Role role = new Role();
                role.setName(roleType);
                roleService.createNewRole(role);
            });

            User adminUser = userService.findByUsername("admin");

            if (adminUser != null) {
                return;
            }

            adminUser = new User();

            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setRole(roleService.findByName(RoleType.ROLE_ADMIN));
            userService.saveUser(adminUser);

        };
    }
}
