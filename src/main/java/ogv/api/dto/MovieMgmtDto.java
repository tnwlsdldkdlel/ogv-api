package ogv.api.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ogv.api.entity.Movie;
import ogv.api.util.Util;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MovieMgmtDto {

	private Long seq;
	private String name;
	private String director;
	private List<String> actor;
	private String intro;
	private String start;
	private String end;
	private List<Integer> genres;
	private String genre;
	private int age;
	private MultipartFile uploadThumbnail;
	private List<String> uploadFileNames = new ArrayList<>();
	private List<String> thumbnail = new ArrayList<>();
	private int createdAt;
	private int updatedAt;
	private String createdAtStr;


	public MovieMgmtDto(Long seq, String name, String director, int createdAt, int start, int end, String genres) {
		super();
		this.seq = seq;
		this.name = name;
		this.director = director;
		this.genres = Util.parseGenresInteger(genres);
		this.start = Util.getIntToStringDateNotTime(start);
		this.end = Util.getIntToStringDateNotTime(end);
		this.createdAtStr = Util.getIntToStringDateNotTime(createdAt);
	}
	
	public MovieMgmtDto(Movie movie) {
		super();
		this.seq = movie.getSeq();
		this.name = movie.getName();
		this.director = movie.getDirector();
		this.genres = Util.parseGenresInteger(movie.getGenres());
		this.start = Util.getIntToStringDateNotTime(movie.getStart());
		this.end = Util.getIntToStringDateNotTime(movie.getEnd());
		this.createdAtStr = Util.getIntToStringDateNotTime(movie.getCreatedAt());
		this.actor = Util.parseGenresString(movie.getActor());
		this.intro = movie.getIntro();
		this.age = movie.getAge();
		this.thumbnail.add(movie.getThumbnail());
	}

}
