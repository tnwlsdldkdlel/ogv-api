package ogv.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import ogv.api.util.Util;
import ogv.api.util.Values.USER_ROLE;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMgmtDto {

	private Long seq;
	private String id; // id
	private String name; // name
	private USER_ROLE role; // 구분
	private String branch = null; // 구분
	private int createdAt; // 등록일
	private String createdAtStr;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public USER_ROLE getRole() {
		return role;
	}

	public void setRole(USER_ROLE role) {
		this.role = role;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(int createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedAtStr() {
		return Util.getIntToStringDate(createdAt);
	}

	public void setCreatedAtStr(String createdAtStr) {
		this.createdAtStr = createdAtStr;
	}

}
