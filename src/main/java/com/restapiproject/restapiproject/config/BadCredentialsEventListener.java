package com.restapiproject.restapiproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.restapiproject.restapiproject.entities.AppUserDetails;
import com.restapiproject.restapiproject.services.UserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BadCredentialsEventListener {

	@Autowired
	private UserDetailsServiceImpl service;

	@EventListener
	public void onBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event) {
		log.info("Start");
		if(event.getException().getClass().equals(UsernameNotFoundException.class)) {
			log.info("ユーザが存在しません");
			return;
		}

		String userId = event.getAuthentication().getName();
		AppUserDetails user = (AppUserDetails) service.loadUserByUsername(userId);

		int loginMissTime = user.getLoginMissTimes() + 1;

		service.updateUnlock(userId,loginMissTime);
		log.info("End");
	}

}
