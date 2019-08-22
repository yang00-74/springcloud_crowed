package com.goog.crowed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.goog.crowed.api.DataBaseOperationRemoteService;
import com.goog.crowed.api.RedisOperationRemoteService;

@RestController
public class ProjectController {

	@Autowired
	private RedisOperationRemoteService redisRemoteService;
	@Autowired
	private DataBaseOperationRemoteService dataBaseRemoteService;
}
