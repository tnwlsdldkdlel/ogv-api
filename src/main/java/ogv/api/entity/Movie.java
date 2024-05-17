package ogv.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.util.Util;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seq;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String director;

	@Column(nullable = false)
	private String actor;

	@Column(nullable = false)
	private String intro;

	@Column(nullable = false)
	private int start;
	
	@Column(nullable = false)
	private int end;

	@Column(nullable = false)
	private String thumbnail;

	@Column(nullable = false)
	private int age;

	@Column(nullable = false)
	private String genres;

	@Column(nullable = false)
	private int createdAt;

	@Column(nullable = false)
	private int updatedAt;

	@Builder
	public Movie(Long seq, String name, String director, String actor, String intro, String start, String end,
			String thumbnail, int age, String genres, int createdAt, int updatedAt) {
		super();
		this.seq = seq;
		this.name = name;
		this.director = director;
		this.actor = actor;
		this.intro = intro;
		this.start = Util.getStringToIntDate(start);
		this.end = Util.getStringToIntDate(end);
		this.thumbnail = thumbnail;
		this.age = age;
		this.genres = genres;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public void update(MovieMgmtDto movieMgmtDto) {
		this.name = movieMgmtDto.getName();
		this.director = movieMgmtDto.getDirector();
		this.actor = movieMgmtDto.getActor().toString();
		this.intro = movieMgmtDto.getIntro();
		this.start = Util.getStringToIntDate(movieMgmtDto.getStart());
		this.end = Util.getStringToIntDate(movieMgmtDto.getEnd());
		this.thumbnail = movieMgmtDto.getUploadFileNames().get(0);
		this.age = movieMgmtDto.getAge();
		this.genres = movieMgmtDto.getGenre();
		this.updatedAt = Util.createdAt();
	}

}
