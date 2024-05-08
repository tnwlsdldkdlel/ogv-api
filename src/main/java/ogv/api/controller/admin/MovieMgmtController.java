package ogv.api.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.dto.ResultDto;
import ogv.api.repository.admin.MovieMgmtRepository;
import ogv.api.util.ResponseCode;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/movies")
@Log4j2
public class MovieMgmtController {
	
	private final MovieMgmtRepository movieMgmtRepository;
	
	@GetMapping("/genres")
	public ResultDto<List<GenreDto>> getGenre() {
		try {
			List<GenreDto> list =  movieMgmtRepository.getGenre();
			return ResultDto.success(list, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping("")
	public ResultDto<MovieMgmtDto> save(MovieMgmtDto movieMgmtDto) {
		try {
			log.info("movieMgmtDto => " + movieMgmtDto.toString());
			movieMgmtRepository.save(movieMgmtDto);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}
}
