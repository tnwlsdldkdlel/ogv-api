package ogv.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ogv.api.util.Values.ADMIN_ROLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {

	private Long seq;
	private String id;
	private String password;
	private ADMIN_ROLE role;
	private String name;
	private String branch;
	private int createdAt;
	private int updatedAt;
	private int loginedAt;

}
