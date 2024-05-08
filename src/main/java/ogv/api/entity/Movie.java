package ogv.api.entity;

import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ogv.api.util.JsonMapConverter;

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

	@Convert(converter = JsonMapConverter.class) 
    @Column(nullable = false)
	private Map<String, String> period;

	@Column(nullable = false)
	private String thumbnail;

	@Column(nullable = false)
	private int age;

	@Column(nullable = false)
	private String genres;
	
	@Builder
	public Movie(Long seq, String name, String director, String actor, String intro, Map<String, String> period,
			String thumbnail, int age, String genres) {
		super();
		this.seq = seq;
		this.name = name;
		this.director = director;
		this.actor = actor;
		this.intro = intro;
		this.period = period;
		this.thumbnail = thumbnail;
		this.age = age;
		this.genres = genres;
	}

}
