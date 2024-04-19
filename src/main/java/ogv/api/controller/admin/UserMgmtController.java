package ogv.api.controller.admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.ResultDto;
import ogv.api.dto.UserMgmtDto;
import ogv.api.repository.admin.UserMgmtRepository;
import ogv.api.util.ResponseCode;
import ogv.api.util.Util;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/user")
@Log4j2
public class UserMgmtController {

	private final UserMgmtRepository userMgmtRepository;

	@PostMapping("")
	public ResultDto<UserMgmtDto> user(@RequestBody UserMgmtDto userMgmtDto) {
		Boolean check = userMgmtRepository.checkUserId(userMgmtDto.getId());

		try {
			// 중복 아이디 확인
			if (!check) {
				return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
			} else {
				userMgmtRepository.saveUser(userMgmtDto);
				
				return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
			}
		} catch (Exception e) {
			log.error(Util.getPrintStackTrace(e));
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}

	}
}
