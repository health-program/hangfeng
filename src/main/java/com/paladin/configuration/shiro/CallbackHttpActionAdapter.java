package com.paladin.configuration.shiro;

import org.pac4j.core.context.J2EContext;
import org.pac4j.core.http.adapter.HttpActionAdapter;

public class CallbackHttpActionAdapter implements HttpActionAdapter<Object, J2EContext>{
	
	private String loginTypeField;
	
	public CallbackHttpActionAdapter(ShiroCasProperties shiroCasProperties) {
		this.loginTypeField = shiroCasProperties.getLoginTypeField();
	}
	
	@Override
	public Object adapt(int code, J2EContext context) {
		if(code == 302) {
			context.getRequest().getSession().setAttribute(loginTypeField, PaladinConstants.LOGIN_TYPE_CAS);
		}
		return null;
	}

}
