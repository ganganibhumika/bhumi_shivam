package com.techhive.shivamweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techhive.shivamweb.exception.ResourceNotFoundException;
import com.techhive.shivamweb.master.model.User;
import com.techhive.shivamweb.repository.UserRepository;
import com.techhive.shivamweb.utils.ShivamWebMethodUtils;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// Let people login with either username or email
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(String id) {
		User user = userRepository.findByIdAndIsDeletedAndIsApproved(id, false, true)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

		return UserPrincipal.create(user);
	}
	// @Transactional
	// public UserDetails loadUserById(String id) {
	// User user;
	// user = userRepository.findByIdAndIsDeletedAndIsApproved(id, false,
	// true).get();
	// if (ShivamWebMethodUtils.isObjectisNullOrEmpty(user)) {
	// user = userRepository.findByIdAndIsDeletedAndIsApproved(id, false,
	// false).get();
	// }
	// // .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	//
	// return UserPrincipal.create(user);
	// }
}