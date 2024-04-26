package ogv.api.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.PageDto;
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
	public ResultDto<UserMgmtDto> save(@RequestBody UserMgmtDto userMgmtDto) {
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

	@GetMapping("")
	public ResultDto<List<UserMgmtDto>> list(PageDto pageDto) {
		List<UserMgmtDto> list = userMgmtRepository.getUser(pageDto);
		return ResultDto.success(list, ResponseCode.READ_SUCCESS.getMessage(), pageDto);
	}

	@DeleteMapping("")
	public ResultDto<UserMgmtDto> delete(@RequestBody Map<String, Long[]> map) {
		try {
			userMgmtRepository.deleteUser(map);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GetMapping("/{seq}")
	public ResultDto<UserMgmtDto> getOne(@PathVariable(name = "seq") Long seq) {
		try {
			UserMgmtDto userMgmtDto = userMgmtRepository.getUserInfo(seq);
			return ResultDto.success(userMgmtDto, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PutMapping("")
	public ResultDto<UserMgmtDto> update(@RequestBody UserMgmtDto userMgmtDto) {
		try {
			userMgmtRepository.updateUser(userMgmtDto);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}

	}

	@PutMapping("/pw/{seq}")
	public ResultDto<UserMgmtDto> resetPW(@PathVariable(name = "seq") long seq) {
		try {
			userMgmtRepository.resetPW(seq);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

}
