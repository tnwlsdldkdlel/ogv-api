package ogv.api.repository.admin;

import ogv.api.dto.AdminDto;

public interface AdminRepository {
	
	public AdminDto getAuth(String id);
	
	public void updateLoginedAt(String id);
}
