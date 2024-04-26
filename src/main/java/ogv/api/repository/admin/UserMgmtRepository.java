package ogv.api.repository.admin;

import java.util.List;
import java.util.Map;

import ogv.api.dto.PageDto;
import ogv.api.dto.UserMgmtDto;

public interface UserMgmtRepository {
	
	public Boolean checkUserId(String id);

	public void saveUser(UserMgmtDto userMgmtDto);
	
	public List<UserMgmtDto> getUser(PageDto pageDto);
	
	public void deleteUser(Map<String, Long[]> map);
	
	public UserMgmtDto getUserInfo(Long seq);
	
	public void updateUser(UserMgmtDto userMgmtDto);
	
	public void resetPW(Long seq);
}
