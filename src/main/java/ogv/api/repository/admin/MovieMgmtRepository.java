package ogv.api.repository.admin;

import java.util.List;

import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;

public interface MovieMgmtRepository {
	
	public List<GenreDto> getGenre();
	
	public void save(MovieMgmtDto movieMgmtDto);

}
