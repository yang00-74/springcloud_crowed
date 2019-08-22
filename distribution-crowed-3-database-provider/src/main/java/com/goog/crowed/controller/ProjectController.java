package com.goog.crowed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.goog.crowed.service.api.ProjectService;

@RestController
public class ProjectController {

	@Autowired
	private ProjectService projectService;
}
