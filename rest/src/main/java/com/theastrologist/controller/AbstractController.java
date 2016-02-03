package com.theastrologist.controller;

import com.theastrologist.util.ControllerUtil;

/**
 * Created by Samy on 31/01/2016.
 */
public class AbstractController {
	protected ControllerUtil controllerUtil = new ControllerUtil();

	public void setControllerUtil(ControllerUtil controllerUtil) {
		this.controllerUtil = controllerUtil;
	}
}
