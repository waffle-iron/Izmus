package com.izmus.view.locale;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * Locale change interceptor, modelled after
 * <tt>org.springframework.web.servlet.i18n.LocaleChangeInterceptor</tt> but
 * additionally supporting a default locale and a set of accepted locales.
 * 
 * @see <a
 *      href="http://docs.spring.io/spring/docs/3.0.x/javadoc-api/org/springframework/web/servlet/i18n/LocaleChangeInterceptor.html">org.springframework.web.servlet.i18n.LocaleChangeInterceptor</a>
 */
public class LocaleChangeInterceptor extends HandlerInterceptorAdapter {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LocaleChangeInterceptor.class);
	private String paramName = "language";
	private String defaultLocale = "en";
	private Set<String> acceptedLocales = Collections.singleton("en");

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Sets the name of the parameter that contains a locale specification in a
	 * locale change request.
	 * 
	 * @param paramName
	 *            the name of the parameter to use, must not be
	 *            <code>null</code>
	 */
	public void setParamName(final String paramName) {
		this.paramName = paramName;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Returns the name of the parameter that contains a locale specification in
	 * a locale change request.
	 * 
	 * @return the name of the parameter that contains a locale specification
	 */
	public String getParamName() {
		return this.paramName;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Returns the locale string to use in case a change to an unsupported
	 * locale is requested.
	 * 
	 * @return the default locale
	 */
	public String getDefaultLocale() {
		return defaultLocale;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Sets the locale string to use in case a change to an unsupported locale
	 * is requested.
	 * 
	 * @param defaultLocale
	 */
	public void setDefaultLocale(final String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Returns a set of accepted locales.
	 * 
	 * @return a set of accepted locales.
	 */
	public Set<String> getAcceptedLocales() {
		return acceptedLocales;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * Sets the set of accepted locales.
	 * 
	 * @param acceptedLocales
	 *            the set of accepted locales, must not be <code>null</code>.
	 */
	public void setAcceptedLocales(final Set<String> acceptedLocales) {
		this.acceptedLocales = acceptedLocales;
	}

	/*----------------------------------------------------------------------------------------------------*/
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean preHandle(final HttpServletRequest request,
			final HttpServletResponse response, final Object handler)
			throws ServletException {
		String newLocale = request.getParameter(this.paramName);
		if (newLocale != null) {
			final LocaleResolver localeResolver = RequestContextUtils
					.getLocaleResolver(request);
			if (localeResolver == null) {
				throw new IllegalStateException(
						"No LocaleResolver found: not in a DispatcherServlet request?");
			}
			if (!acceptedLocales.contains(newLocale)) {
				newLocale = getDefaultLocale();
			}
			if (!request.getLocale().getLanguage().equals(new Locale(newLocale).getLanguage())) LOGGER.info("Switching to new Locale: " + newLocale);
			localeResolver.setLocale(request, response,
					StringUtils.parseLocaleString(newLocale));
		}
		// Proceed in any case.
		return true;
	}
}
