package ogv.api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.AdminDto;
import ogv.api.repository.admin.AdminRepository;

@RequiredArgsConstructor
@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {

	private final AdminRepository adminRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("========================= CustomUserDetailService =========================");
		
		AdminDto adminDto = adminRepository.getAuth(username);
		
		return new CustomUser(adminDto.getSeq(), adminDto.getId(), adminDto.getPassword(), adminDto.getName(), adminDto.getLoginedAt(), adminDto.getRole());
	}

}
