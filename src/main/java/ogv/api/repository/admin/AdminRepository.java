package ogv.api.repository.admin;

import ogv.api.dto.UserDto;

public interface AdminRepository {
	
	public UserDto getAuth(String id);
	
	public void updateLoginedAt(String id);
}
