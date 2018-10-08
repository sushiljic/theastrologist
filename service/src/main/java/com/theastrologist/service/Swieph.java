package com.theastrologist.service;

import swisseph.SwissEph;

/**
 * Created by Samy on 12/05/2017.
 */
public class Swieph {

	private final SwissEph sw;

	/**
	 * Constructeur privé
	 */
	private Swieph() {
		sw = new SwissEph();
	}

	/**
	 * Holder
	 */
	private static class SwiephHolder {
		/**
		 * Instance unique non préinitialisée
		 */
		private final static Swieph instance = new Swieph();
	}

	/**
	 * Point d'accès pour l'instance unique du singleton
	 */
	public static Swieph getInstance() {
		return SwiephHolder.instance;
	}

	public SwissEph value() {
		return sw;
	}
}
