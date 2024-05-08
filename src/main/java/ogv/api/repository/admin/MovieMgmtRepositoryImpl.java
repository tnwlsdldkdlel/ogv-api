package ogv.api.repository.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.GenreDto;
import ogv.api.dto.MovieMgmtDto;
import ogv.api.entity.Movie;
import ogv.api.entity.Qgenre;
import ogv.api.util.CustomFileUtil;

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
		Qgenre qgenre = Qgenre.genre;
		
		return queryFactory.select(Projections.fields(GenreDto.class, qgenre.code, qgenre.name))
			.from(qgenre)
			.orderBy(qgenre.name.asc())
			.fetch();
	}

	@Transactional
	@Override
	public void save(MovieMgmtDto movieMgmtDto) {
		// 이미지 로컬 저장
		List<MultipartFile> files = new ArrayList<>();
		files.add(movieMgmtDto.getUploadThumbnail());
		
		List<String> uploadFileNames = customFileUtil.saveFiles(files);
		movieMgmtDto.setUploadFileNames(uploadFileNames);
		
		Map<String, String> period = new HashMap<>();
		period.put("start", movieMgmtDto.getStart());
		period.put("end", movieMgmtDto.getEnd());
		
		// db 저장
		Movie movie = Movie.builder()
				.name(movieMgmtDto.getName())
				.director(movieMgmtDto.getDirector())
				.actor(movieMgmtDto.getActor().toString())
				.intro(movieMgmtDto.getIntro())
				.period(period)
				.thumbnail(movieMgmtDto.getUploadFileNames().get(0))
				.age(movieMgmtDto.getAge())
				.genres(movieMgmtDto.getGenre().toString())
				.build();
		
		em.persist(movie);
	}

}
