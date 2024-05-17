package ogv.api.repository.admin;

import java.util.List;
import java.util.Map;

import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.dto.PageDto;

public interface MovieMgmtRepository {
	
	public List<GenreDto> getGenre();
	
	public void save(MovieMgmtDto movieMgmtDto);
	
	public List<MovieMgmtDto> getMovieList(PageDto pageDto);
	
	public void remove(Map<String, Long[]> map);
	
	public MovieMgmtDto info(Long seq);
	
	public void update(MovieMgmtDto movieMgmtDto);

}
