package tr.com.obss.jip.bookportal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.model.Role;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.service.RoleService;
import tr.com.obss.jip.bookportal.service.UserService;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class AppInitConfig {

    private final UserService userService;
    private final RoleService roleService;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            final List<RoleType> allRoles =
                    roleService.getAllRoles().stream().map(Role::getName).toList();

            Arrays.stream(RoleType.values())
                    .filter(roleType -> !allRoles.contains(roleType))
                    .forEach(
                            roleType -> {
                                Role role = new Role();
                                role.setName(roleType);
                                roleService.createNewRole(role);
                            });

            User adminUser = userService.getUser("admin");

            if (adminUser != null) {
                return;
            }

            CreateUserDto newAdmin = new CreateUserDto("admin", "admin123");
            userService.createUser(newAdmin, RoleType.ROLE_ADMIN);
        };
    }
}
