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
import ogv.api.util.Util;
import ogv.api.util.Values.ADMIN_ROLE;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false, unique = true)
	private String id;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private ADMIN_ROLE role;

	@Column(nullable = false)
	private String name;

	@Column(nullable = true)
	private String branch; // 본사인 경우 null

	@Column(nullable = false)
	private int createdAt = Util.createdAt();

	@Column(nullable = false)
	private int updatedAt = Util.createdAt();

	@Column(nullable = true)
	private int loginedAt;

	@Builder
	public Admin(Long seq, String id, String password, ADMIN_ROLE role, String name, String branch, int createdAt,
			int updatedAt, int loginedAt) {
		super();
		this.seq = seq;
		this.id = id;
		this.password = password;
		this.role = role;
		this.name = name;
		this.branch = branch;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.loginedAt = loginedAt;
	}

}
