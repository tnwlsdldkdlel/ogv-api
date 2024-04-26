package ogv.api.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import ogv.api.util.Util;
import ogv.api.util.Values.USER_ROLE;

public class CustomUser extends User {
	private Long seq;
	private String id;
	private String password;
	private String name;
	private int loginedAt;
	private USER_ROLE role;

	public CustomUser(Long seq, String id, String password, String name, int loginedAt, USER_ROLE role) {
		super(id, password, Util.setAuthRole(new SimpleGrantedAuthority("ROLE_" + role)));
		// spring security는 role_형태로 인식을 하기때문에 변환.
		// spring객체에 부여된 권한의 표현을 저장.
		this.seq = seq;
		this.id = id;
		this.password = password;
		this.name = name;
		this.loginedAt = loginedAt;
		this.role = role;
	}
	
	public Map<String, Object> getClaims() {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("seq", seq);
		dataMap.put("name", name);
		dataMap.put("loginedAt", Util.getIntToStringDate(loginedAt));
		dataMap.put("role", role);

		return dataMap;
	}
}
