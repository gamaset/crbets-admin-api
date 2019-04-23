package com.gamaset.crbetadmin.infra.configuration.security;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gamaset.crbetadmin.infra.configuration.security.jwt.JwtProvider;
import com.gamaset.crbetadmin.infra.exception.NotFoundException;
import com.gamaset.crbetadmin.infra.exception.UserAlreadyTakenException;
import com.gamaset.crbetadmin.repository.RoleRepository;
import com.gamaset.crbetadmin.repository.UserRepository;
import com.gamaset.crbetadmin.repository.entity.Role;
import com.gamaset.crbetadmin.repository.entity.RoleName;
import com.gamaset.crbetadmin.repository.entity.UserModel;
import com.gamaset.crbetadmin.schema.request.SignUpRequest;
import com.gamaset.crbetadmin.schema.response.SignInResponse;

@Service
public class AuthService {

	AuthenticationManager authenticationManager;
	UserRepository userRepository;
	RoleRepository roleRepository;
	PasswordEncoder encoder;
	JwtProvider jwtProvider;

	@Autowired
	public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder encoder, JwtProvider jwtProvider) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
		this.jwtProvider = jwtProvider;
	}

	public SignInResponse authenticateUser(LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return new SignInResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities());
	}

	public UserModel signUp(SignUpRequest signUpRequest) {
		verifyIfEmailAlreadyUse(signUpRequest.getEmail());

		UserModel user = new UserModel(signUpRequest.getName(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), signUpRequest.getTaxId());

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = verifyRoles(strRoles);

		user.setRoles(roles);
		return userRepository.save(user);

	}

	private Set<Role> verifyRoles(Set<String> strRoles) {
		Set<Role> roles = new HashSet<>();
		if (Objects.isNull(strRoles)) {
			Role userRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
					.orElseThrow(() -> new NotFoundException("Fail! -> Cause: User Role not find."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ADMIN":
					Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
							.orElseThrow(() -> new NotFoundException("Fail! -> Cause: User Role not find."));
					roles.add(adminRole);

					break;
				case "AGENT":
					Role agentRole = roleRepository.findByName(RoleName.ROLE_AGENT)
							.orElseThrow(() -> new NotFoundException("Fail! -> Cause: User Role not find."));
					roles.add(agentRole);

					break;
				case "MANAGER":
					Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
							.orElseThrow(() -> new NotFoundException("Fail! -> Cause: User Role not find."));
					roles.add(managerRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleName.ROLE_CUSTOMER)
							.orElseThrow(() -> new NotFoundException("Fail! -> Cause: User Role not find."));
					roles.add(userRole);
				}
			});
		}

		return roles;
	}

	private void verifyIfEmailAlreadyUse(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new UserAlreadyTakenException("Fail -> Email is already in use!");
		}
	}
}
