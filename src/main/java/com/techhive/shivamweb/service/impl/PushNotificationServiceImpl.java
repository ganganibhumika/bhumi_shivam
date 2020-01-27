package com.techhive.shivamweb.service.impl;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.techhive.shivamweb.appconfig.HeaderRequestInterceptor;
import com.techhive.shivamweb.service.PushNotificationService;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	private static final String FIREBASE_SERVER_KEY = "AAAAqARg0a4:APA91bHw-f8SVy5rgTcqfpZt_AFFTf-8WOGs7EUfV_jpCJX1YApkrLUq5HS8TGJDVeFmJvQigHBLTfgTP7BbhqSAUA_e2v9xeg_oC2HTfy9PqNL_b4KKJx8kSGtVIgJw6HEvpKR4hNAx";
	// private static final String FIREBASE_SERVER_KEY =
	// "AAAAk8R2Fcw:APA91bEH4VpIykpt21tDd1yDq_W38uAhVq5HaC6zlNsHIUBNxgm7ttcWR9HVZkYY5hlMkrLBd1pcrhDzGdKoY1VopPy_UJiI0hyU6FS_UwqUf14BQGTxHy71cpjoXg5A9kRdCSFTqXjQ";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		/**
		 * https://fcm.googleapis.com/fcm/send Content-Type:application/json
		 * Authorization:key=FIREBASE_SERVER_KEY
		 */

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
