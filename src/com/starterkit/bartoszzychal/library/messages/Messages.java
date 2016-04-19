package com.starterkit.bartoszzychal.library.messages;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class Messages {
	private final String RES_PATH = "/res/";
	private final Logger LOG = Logger.getLogger(Messages.class.getSimpleName());
	private ResourceBundle bundle;

	public Messages(String bundlePath, String clazz) {
		bundlePath = bundlePath.replace(".", "/");
		bundlePath = RES_PATH + bundlePath +"/"+ clazz;
		bundle = ResourceBundle.getBundle(bundlePath);

		LOG.info("Bundle path: "+bundlePath);
	}

	public String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
