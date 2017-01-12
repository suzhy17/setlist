package com.daou.setlist.admin.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

public class ParamHandlerTag extends TagSupport {
	private static final long serialVersionUID = 1355669406465114546L;

	private String skipParams;
	private String requiredParams;
	private String suffix;
	private String var;
	private String scope;

	@Override
	public int doEndTag() throws JspException {
		HttpServletRequest request = (HttpServletRequest) this.pageContext.getRequest();
		Map<String, String[]> paramMap = new HashMap<String, String[]>(request.getParameterMap());

		if (skipParams.length() > 0) {
			String[] skipParamArray = skipParams.split(",");
			for (String skipParamName : skipParamArray) {
				paramMap.remove(skipParamName);
			}
		} else if (requiredParams.length() > 0) {
			Map<String, String[]> requiredParamMap = new HashMap<String, String[]>();
			String[] requiredParamArray = requiredParams.split(",");
			for (String requiredParamName : requiredParamArray) {
				requiredParamMap.put(requiredParamName, paramMap.get(requiredParamName));
			}
			paramMap = requiredParamMap;
		} else {
			return EVAL_PAGE;
		}

		if (paramMap.size() == 0) {
			return EVAL_PAGE;
		}

		StringBuilder queryString = new StringBuilder(suffix);

		for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
			for (String value : entry.getValue()) {
				if (!"_".equals(entry.getKey())) {
					queryString.append(entry.getKey()).append("=").append(value).append("&");
				}
			}
		}

		if (queryString.length() > 0) {
			queryString.deleteCharAt(queryString.length() - 1);
		}

		try {
			if (!StringUtils.isBlank(var)) {
				if (StringUtils.isBlank(scope) || "page".equals(scope)) {
					pageContext.setAttribute(var, queryString.toString());
				} else if ("request".equals(scope)) {
					request.setAttribute(var, queryString.toString());
				} else if ("session".equals(scope)) {
					pageContext.getSession().setAttribute(var, queryString.toString());
				} else if ("application".equals(scope)) {
					pageContext.getServletContext().setAttribute(var, queryString.toString());
				}
			} else {
				pageContext.getOut().write(queryString.toString());
			}

		} catch (IOException e) {
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	public String getSkipParams() {
		return skipParams;
	}

	public void setSkipParams(String skipParams) {
		this.skipParams = skipParams;
	}

	public String getRequiredParams() {
		return requiredParams;
	}

	public void setRequiredParams(String requiredParams) {
		this.requiredParams = requiredParams;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
