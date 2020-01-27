package com.techhive.shivamweb.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;

public interface PushNotificationService {
	CompletableFuture<String> send(HttpEntity<String> request);
}
