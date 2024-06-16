package com.orcnaydn.ecommerce;

import com.orcnaydn.ecommerce.entity.Role;
import com.orcnaydn.ecommerce.entity.User;
import com.orcnaydn.ecommerce.repository.RoleRepository;
import com.orcnaydn.ecommerce.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


//	@Bean
//	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
//		return args -> {
//				Role userRole = new Role();
//				userRole.setName("ROLE_USER");
//				roleRepository.save(userRole);
//
//				Role adminRole = new Role();
//				adminRole.setName("ROLE_ADMIN");
//				var role = roleRepository.save(adminRole);
//
//				Set<Role> roles = new HashSet<>();
//				roles.add(role);
//
//
//
//			User user = new User();
//			user.setEmail("admin@admin.com");
//			user.setPassword(passwordEncoder.encode("admin"));
//			userRepository.save(user);
//
//
//			var u = userRepository.findByEmail("admin@admin.com").get();
//			u.setRoles(roles);
//			userRepository.save(u);
//		};
//	}
}
