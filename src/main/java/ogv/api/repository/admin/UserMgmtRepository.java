package ogv.api.repository.admin;

import ogv.api.dto.UserMgmtDto;

public interface UserMgmtRepository {
	
	public Boolean checkUserId(String id);

	public void saveUser(UserMgmtDto userMgmtDto);
}
