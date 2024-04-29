package ogv.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Cinema {
	
	@Id
	private Long seq;
	
	@Column(nullable = false)
	private int createdAt;
	
	@Column(nullable = false)
	private int updatedAt;
	
	// 하나의 지점은 하나의 영화관만 관리할 수 있다.
	@OneToOne
	@JoinColumn(name = "code_branch", nullable = false)
	private Branch branch;
}
