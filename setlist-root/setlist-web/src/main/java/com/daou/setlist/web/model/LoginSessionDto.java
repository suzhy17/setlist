package com.daou.setlist.web.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.daou.setlist.common.model.User;

/**
 * 사용자 세션 정보를 저장
 * @author suzhy
 */
public class LoginSessionDto extends User implements Serializable {

	private static final long serialVersionUID = -5581194925815482829L;

	private String contentsSession;
	private String companyId;
	private String authKey;

	public String getContentsSession() {
		return contentsSession;
	}

	public void setContentsSession(String contentsSession) {
		this.contentsSession = contentsSession;
	}
	
	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAuthKey() {
		return authKey;
	}

	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

}
