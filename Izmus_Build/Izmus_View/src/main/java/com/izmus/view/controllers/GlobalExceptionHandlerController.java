package com.izmus.view.controllers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.izmus.data.domain.users.User;

/**
 * Global exception handler for controllers. The handler lives in the
 * Spring context and gets executed if exceptions are not handled
 * by individual controllers already.
 */
@ControllerAdvice
public class GlobalExceptionHandlerController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);
	protected static final String ERROR_VIEW = "error";
	protected static final String ACCESS_DENIED_VIEW = "accessDenied";
	protected static final String LANDING_PAGE_VIEW = "landingPage";
	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Catch Exception and redirect to the error page.
	 */
	@ExceptionHandler(Throwable.class)
	public ModelAndView handlException(final Throwable t) {
		User user;
		try {
			user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			user = null;
		}
		if (user == null){
			LOGGER.error("An Uncought Exception Occured", t);
			return new ModelAndView(LANDING_PAGE_VIEW);
		}
		if (t instanceof AccessDeniedException){
			LOGGER.error("No Access To User");
			return new ModelAndView(ACCESS_DENIED_VIEW);
		}
		LOGGER.error("An Uncought Exception Occured", t);
		ModelAndView errorView = new ModelAndView(ERROR_VIEW);
		String errorTrace = getErrorTrace(t);
		errorView.addObject("errorMessage", errorTrace);
		return errorView;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private String getErrorTrace(final Throwable t) {
		final StringWriter stringWriter = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(stringWriter);
		t.printStackTrace(printWriter);
		return stringWriter.toString();
	}
}