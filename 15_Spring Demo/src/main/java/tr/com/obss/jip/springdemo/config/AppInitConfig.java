package tr.com.obss.jip.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.obss.jip.springdemo.model.Role;
import tr.com.obss.jip.springdemo.model.RoleType;
import tr.com.obss.jip.springdemo.model.User;
import tr.com.obss.jip.springdemo.service.RoleService;
import tr.com.obss.jip.springdemo.service.UserService;

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

            adminUser.setName("System");
            adminUser.setAge(22);
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setRoles(roleService.getAllRoles());
            userService.createNewUser(adminUser);

            try {

            } catch (Exception e) {

            }

        };
    }
}
