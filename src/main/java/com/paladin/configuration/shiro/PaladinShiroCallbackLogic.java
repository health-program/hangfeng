package com.paladin.configuration.shiro;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.exception.HttpAction;
import org.pac4j.core.http.adapter.HttpActionAdapter;

import io.buji.pac4j.engine.ShiroCallbackLogic;

public class PaladinShiroCallbackLogic<R, C extends J2EContext> extends ShiroCallbackLogic<R, C> {

	private String loginTypeField;
	private String errorUrl;
	private String casServerLoginUrl;

	public PaladinShiroCallbackLogic(ShiroCasProperties shiroCasProperties) {
		super();
		this.loginTypeField = shiroCasProperties.getLoginTypeField();
		this.errorUrl = shiroCasProperties.getCasErrorUrl();
		this.casServerLoginUrl = shiroCasProperties.getCasServerLoginUrl();

		if (errorUrl == null || errorUrl.length() == 0) {
			errorUrl = casServerLoginUrl;
		} else {
			if (errorUrl.indexOf("?") > -1) {
				errorUrl += "&casServerLoginUrl=" + casServerLoginUrl;
			} else {
				errorUrl += "?casServerLoginUrl=" + casServerLoginUrl;
			}
		}
	}

	protected HttpAction redirectToOriginallyRequestedUrl(final C context, final String defaultUrl) {
		HttpAction action = super.redirectToOriginallyRequestedUrl(context, defaultUrl);
		context.getRequest().getSession().setAttribute(loginTypeField, PaladinConstants.LOGIN_TYPE_CAS);
		return action;
	}

	protected R handleException(final Exception e, final HttpActionAdapter<R, C> httpActionAdapter, final C context) {
		if (httpActionAdapter == null || context == null) {
			throw runtimeException(e);
		} else if (e instanceof HttpAction) {
			final HttpAction action = (HttpAction) e;
			logger.debug("extra HTTP action required in security: {}", action.getCode());
			return httpActionAdapter.adapt(action.getCode(), context);
		} else {
			final HttpAction action = HttpAction.redirect(context, errorUrl);
			return httpActionAdapter.adapt(action.getCode(), context);
		}
	}

}
