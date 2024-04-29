package ogv.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import ogv.api.util.Values.BRANCH_STATE;

@Entity
public class Branch {

	@Id
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private int createdAt;

	@Column(nullable = false)
	private int updatedAt;

	@Column(nullable = false)
	private BRANCH_STATE state;
	
	@OneToOne(mappedBy = "branch")
	private Cinema cinema;
	
	@OneToOne(mappedBy = "branch")
	private User user;

	@Builder
	public Branch(String code, String name, int createdAt, int updatedAt, BRANCH_STATE state, Cinema cinema,
			User user) {
		this.code = code;
		this.name = name;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.state = state;
		this.cinema = cinema;
		this.user = user;
	}

}
