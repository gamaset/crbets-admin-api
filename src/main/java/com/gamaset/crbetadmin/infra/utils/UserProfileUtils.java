package com.gamaset.crbetadmin.infra.utils;

import org.springframework.security.core.GrantedAuthority;

import com.gamaset.crbetadmin.infra.configuration.security.UserPrinciple;
import com.gamaset.crbetadmin.repository.entity.RoleName;

public class UserProfileUtils {
	
	public static boolean isAdmin(UserPrinciple principle) {
		for (GrantedAuthority authority : principle.getAuthorities()) {
			if(authority.getAuthority().equals(RoleName.ROLE_ADMIN.name())) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isAdminOrManager(UserPrinciple principle) {
		for (GrantedAuthority authority : principle.getAuthorities()) {
			if(authority.getAuthority().equals(RoleName.ROLE_ADMIN.name()) || authority.getAuthority().equals(RoleName.ROLE_MANAGER.name())) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isAdminOrAgent(UserPrinciple principle) {
		for (GrantedAuthority authority : principle.getAuthorities()) {
			if(authority.getAuthority().equals(RoleName.ROLE_ADMIN.name()) || authority.getAuthority().equals(RoleName.ROLE_AGENT.name())) {
				return true;
			}
		}
		
		return false;
	}

	public static boolean isAgent(UserPrinciple principle) {
		for (GrantedAuthority authority : principle.getAuthorities()) {
			if(authority.getAuthority().equals(RoleName.ROLE_AGENT.name())) {
				return true;
			}
		}
		
		return false;
	}

}
