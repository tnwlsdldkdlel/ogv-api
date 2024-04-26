package ogv.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogv.api.dto.UserMgmtDto;
import ogv.api.util.Util;
import ogv.api.util.Values.USER_ROLE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false, unique = true)
	private String id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private USER_ROLE role;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String branch = null;
	
	@Column(nullable = false)
	private int createdAt = Util.createdAt();

	@Column(nullable = false)
	private int updatedAt = Util.createdAt();

	@Column(nullable = true)
	private int loginedAt;

	@Builder
	public User(Long seq, String id, String password, USER_ROLE role, String name, String branch, int createdAt,
			int updatedAt, int loginedAt) {
		this.seq = seq;
		this.id = id;
		this.password = password;
		this.role = role;
		this.name = name;
		this.branch = Util.checkNull(branch);
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.loginedAt = Util.checkNull(loginedAt);
	}
	
	public void update(UserMgmtDto userMgmtDto) {
		this.id = userMgmtDto.getId();
		this.role = userMgmtDto.getRole();
		this.name = userMgmtDto.getName();
		this.branch = userMgmtDto.getBranch();
		this.updatedAt = Util.createdAt();
	}
	
	public void resetPW(String password) {
		this.password = password;
		this.updatedAt = Util.createdAt();
	}

}
