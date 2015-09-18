package com.hantsylabs.example.spring.model;

public class SecurityUtils {
	public static User user;

	public static User getCurrentUser() {
		// Authentication authentication =
		// SecurityContextHolder.getContext().getAuthentication();
		//
		// if (authentication == null || !authentication.isAuthenticated()) {
		// return null;
		// }
		//
		// return ((MyUserDetails) authentication.getPrincipal()).getUser();
		return user;
	}

}
