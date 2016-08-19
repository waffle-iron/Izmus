package com.izmus.data.messages;

import java.io.Serializable;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSource extends ReloadableResourceBundleMessageSource implements Serializable{
	/*----------------------------------------------------------------------------------------------------*/
	private static final long serialVersionUID = 1L;
	private static final String baseName = "/WEB-INF/language/messages";
	private static final int CACHE_SECONDS = 5;
	/*----------------------------------------------------------------------------------------------------*/
	public MessageSource(){
		this.setBasenames(baseName);
		this.setCacheSeconds(CACHE_SECONDS);
	}
}
