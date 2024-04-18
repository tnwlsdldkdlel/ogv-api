package ogv.api.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.ResultDto;
import ogv.api.util.JWTUtil;
import ogv.api.util.ResponseCode;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// authentication는 loadUserByUsername에서 return된 값인 UserDetails
		log.info("=================================");
		log.info(authentication);
		log.info("=================================");

		CustomUser customUser = (CustomUser) authentication.getPrincipal();
		Map<String, Object> claims = customUser.getClaims();

		String accessToken = JWTUtil.generateToken(claims, 10); // 10분.
		String refreshToken = JWTUtil.generateToken(claims, 68 * 24);

		claims.put("accessToken", accessToken);
		claims.put("refreshToken", refreshToken);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(ResultDto.success(claims, ResponseCode.READ_SUCCESS.getMessage()));

		// 한글이 들어간 경우도 있으므로 UTF-8 확인.
		response.setContentType("application/json; charset=UTF-8");
		
		log.info("로그인 결과값 => " + jsonStr);

		PrintWriter printWriter = response.getWriter();
		printWriter.println(jsonStr);
		printWriter.close();
	}

}
