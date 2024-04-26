package ogv.api.repository.admin;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.UserDto;
import ogv.api.entity.QUser;
import ogv.api.util.Util;

@Repository
@Transactional(readOnly = true)
@Log4j2
public class AdminRepositoryImpl implements AdminRepository {
	
	private final EntityManager em;
	private final JPAQueryFactory queryFactory;

	public AdminRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
	}

	// 쿠키에 넣을 데이터 - seq, name, role, login time
	@Override
	public UserDto getAuth(String id) {	
		QUser qUser = QUser.user;
		
		return queryFactory
			.select(Projections.fields(UserDto.class, qUser.seq, qUser.id, qUser.password, qUser.name, qUser.role, qUser.loginedAt))
			.from(qUser)
			.where(qUser.id.eq(id))
			.fetchOne();
	}

	@Transactional
	@Override
	public void updateLoginedAt(String id) {
		QUser qUser = QUser.user;
		
		queryFactory
			.update(qUser)
			.set(qUser.updatedAt, Util.createdAt())
			.set(qUser.loginedAt, Util.createdAt())
			.where(qUser.id.eq(id))
			.execute();
	}

}
