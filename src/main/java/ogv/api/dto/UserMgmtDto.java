package ogv.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ogv.api.util.Values.ADMIN_ROLE;
import ogv.api.util.Values.USER_TYPE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserMgmtDto {
	
	private USER_TYPE type;
	private String id;
	private String name;
	private ADMIN_ROLE role;
	private String branch = null;
	
}
