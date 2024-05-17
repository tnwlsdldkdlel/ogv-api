package ogv.api.repository.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.dto.PageDto;
import ogv.api.entity.Genre;
import ogv.api.entity.Movie;
import ogv.api.entity.QGenre;
import ogv.api.entity.QMovie;
import ogv.api.util.CustomFileUtil;
import ogv.api.util.Util;

@Repository
@Transactional(readOnly = true)
@Log4j2
public class MovieMgmtRepositoryImpl implements MovieMgmtRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final CustomFileUtil customFileUtil;

	public MovieMgmtRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory, CustomFileUtil customFileUtil) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.customFileUtil = customFileUtil;
	}

	@Override
	public List<GenreDto> getGenre() {
		QGenre qgenre = QGenre.genre;

		return queryFactory
				.select(Projections.fields(GenreDto.class, qgenre.code, qgenre.name))
				.from(qgenre)
				.orderBy(qgenre.name.asc()).fetch();
	}

	@Transactional
	@Override
	public void save(MovieMgmtDto movieMgmtDto) {
		// 이미지 로컬 저장
		List<MultipartFile> files = new ArrayList<>();
		files.add(movieMgmtDto.getUploadThumbnail());

		List<String> uploadFileNames = customFileUtil.saveFiles(files);
		movieMgmtDto.setUploadFileNames(uploadFileNames);

		// db 저장
		Movie movie = Movie.builder()
				.name(movieMgmtDto.getName())
				.director(movieMgmtDto.getDirector())
				.actor(movieMgmtDto.getActor().toString())
				.intro(movieMgmtDto.getIntro())
				.start(movieMgmtDto.getStart())
				.end(movieMgmtDto.getEnd())
				.thumbnail(movieMgmtDto.getUploadFileNames().get(0))
				.age(movieMgmtDto.getAge())
				.genres(movieMgmtDto.getGenres().toString())
				.createdAt(Util.createdAt())
				.updatedAt(Util.createdAt())
				.build();

		em.persist(movie);
	}

	@Override
	public List<MovieMgmtDto> getMovieList(PageDto pageDto) {
		QMovie qMovie = QMovie.movie;
		
		// 검색
		BooleanExpression search = null;
		if (!pageDto.getSearch().isEmpty()) {
			switch (pageDto.getSearchTarget()) {
			case NAME:
				search = qMovie.name.contains(pageDto.getSearch());
				break;

			case ACTOR:
				search = qMovie.actor.contains(pageDto.getSearch());
				break;
				
			case DIRECTOR:
				search = qMovie.director.contains(pageDto.getSearch());
				break;

			case ALL:
				search = qMovie.name.contains(pageDto.getSearch())
							.or(qMovie.actor.contains(pageDto.getSearch())
							.or(qMovie.director.contains(pageDto.getSearch())));
				break;
			}
		}
		
		// 날짜
		BooleanExpression dateSearch = null;
		if(!pageDto.getStart().isEmpty() && !pageDto.getEnd().isEmpty()) {
			dateSearch = qMovie.start.goe(Util.getStringToIntDate(pageDto.getStart()))
					.and(qMovie.end.loe(Util.getStringToIntDate(pageDto.getEnd())));
		}

		JPAQuery<MovieMgmtDto> movieDtos = queryFactory
				.select(Projections.constructor(MovieMgmtDto.class, qMovie.seq, qMovie.name, qMovie.director, qMovie.createdAt, qMovie.start, qMovie.end, qMovie.genres))
				.from(qMovie)
				.where(search, dateSearch)
				.orderBy(qMovie.seq.desc())
				.offset((pageDto.getPage() - 1) * pageDto.getSize())
				.limit(pageDto.getSize());

		pageDto.setAmount(movieDtos.fetchCount());
		
		// 장르
		List<MovieMgmtDto> movieMgmtDtos = movieDtos.fetch();
		StringBuilder genreStr = new StringBuilder();
		
		movieMgmtDtos.forEach(dto -> {
		    List<Integer> genres = dto.getGenres();
		    genres.forEach(genre -> {
		        genreStr.append(em.find(Genre.class, genre).getName()).append(",");
		    });
		    
		    if (genreStr.length() > 0) {
		        genreStr.deleteCharAt(genreStr.length() - 1);
		    }
		    
		    dto.setGenre(genreStr.toString());
		});

		return movieMgmtDtos;
	}

	@Transactional
	@Override
	public void remove(Map<String, Long[]> map) {
		QMovie qMovie = QMovie.movie;
	 	Long[] seq = map.get("seq");
	 	
	 	for(int i = 0 ; i < seq.length; i++) {
	 		queryFactory
	 			.delete(qMovie)
	 			.where(qMovie.seq.eq(seq[i]))
	 			.execute();
	 	}
	}

	@Override
	public MovieMgmtDto info(Long seq) {
		Movie movie = em.find(Movie.class, seq);
		
		return new MovieMgmtDto(movie);
	}

	@Override
	public void update(MovieMgmtDto movieMgmtDto) {
		// 이미지를 변경했을 경우
		if(movieMgmtDto.getUploadThumbnail() != null) {
			// 기존 이미지 삭제
			customFileUtil.deleteFiles(movieMgmtDto.getThumbnail());
			
			// 수정된 이미지 업로드
			List<MultipartFile> files = new ArrayList<>();
			files.add(movieMgmtDto.getUploadThumbnail());

			List<String> uploadFileNames = customFileUtil.saveFiles(files);
			movieMgmtDto.setUploadFileNames(uploadFileNames);
		} else {
			movieMgmtDto.setUploadFileNames(movieMgmtDto.getThumbnail());
		}
		
		Movie movie = em.find(Movie.class, movieMgmtDto.getSeq());
		movie.update(movieMgmtDto);
	}

}
