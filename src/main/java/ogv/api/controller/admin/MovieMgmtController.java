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
import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.dto.PageDto;
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
			List<GenreDto> list = movieMgmtRepository.getGenre();
			return ResultDto.success(list, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PostMapping("")
	public ResultDto<MovieMgmtDto> save(MovieMgmtDto movieMgmtDto) {
		try {
			movieMgmtRepository.save(movieMgmtDto);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GetMapping("")
	public ResultDto<List<MovieMgmtDto>> getMovieList(PageDto pageDto) {
		try {
			List<MovieMgmtDto> movieMgmtDtos = movieMgmtRepository.getMovieList(pageDto);
			return ResultDto.success(movieMgmtDtos, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@DeleteMapping("")
	public ResultDto<MovieMgmtDto> remove(@RequestBody Map<String, Long[]> map) {
		try {
			movieMgmtRepository.remove(map);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@GetMapping("/{seq}")
	public ResultDto<MovieMgmtDto> info(@PathVariable(name = "seq") Long seq) {
		try {
			MovieMgmtDto movieMgmtDto = movieMgmtRepository.info(seq);
			return ResultDto.success(movieMgmtDto, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

	@PutMapping("/{seq}")
	public ResultDto<MovieMgmtDto> update(@RequestBody MovieMgmtDto movieMgmtDto) {
		try {
			log.info(movieMgmtDto.toString());
			movieMgmtRepository.update(movieMgmtDto);
			return ResultDto.success(null, ResponseCode.READ_SUCCESS.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			return ResultDto.fail(ResponseCode.INTERNAL_SERVER_ERROR, null);
		}
	}

}
