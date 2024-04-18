package ogv.api.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j2;
import ogv.api.repository.admin.AdminRepository;

@Component
@Log4j2
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailService customUserDetailService;

	private final AdminRepository adminRepository;

	public CustomAuthenticationProvider(CustomUserDetailService customUserDetailService,
			PasswordEncoder passwordEncoder, AdminRepository adminRepository) {
		this.customUserDetailService = customUserDetailService;
		this.passwordEncoder = passwordEncoder;
		this.adminRepository = adminRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		log.info("========================= CustomAuthenticationProvider =========================");

		// 사용자가 입력한 정보
		String id = authentication.getName();
		String password = authentication.getCredentials().toString();

		log.info("사용자가 입력한 id : " + id);
		log.info("사용자가 입력한 password : " + password);

		// 빈값인 경우
		if (password == null || id == null) {
			throw new UsernameNotFoundException("Invalid");
		}

		UserDetails userDetails = customUserDetailService.loadUserByUsername(id);

		log.info("userDetails : " + userDetails.toString());

		// 등록되지 않은 유저인 경우
		if (userDetails == null) {
			throw new UsernameNotFoundException("Not Found");
		}

		// 비밀번호가 틀린 경우
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UsernameNotFoundException("Invalid");
		}
		
		// 로그인한 시간 업데이트
		adminRepository.updateLoginedAt(userDetails.getUsername());

		Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());

		return newAuth;
	}

	// 위의 authenticate 메소드에서 반환한 객체가 유효한 타입이 맞는지 검사
	// null 값이거나 잘못된 타입을 반환했을 경우 인증 실패로 간주
	@Override
	public boolean supports(Class<?> authentication) {
		// 스프링 Security가 요구하는 UsernamePasswordAuthenticationToken 타입이 맞는지 확인
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
