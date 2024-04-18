package ogv.api.repository.admin;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import lombok.extern.log4j.Log4j2;
import ogv.api.dto.AdminDto;
import ogv.api.entity.QAdmin;
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
	public AdminDto getAuth(String id) {	
		QAdmin qAdmin = QAdmin.admin;
		
		return queryFactory
			.select(Projections.fields(AdminDto.class, qAdmin.seq, qAdmin.id, qAdmin.password, qAdmin.name, qAdmin.role, qAdmin.loginedAt))
			.from(qAdmin)
			.where(qAdmin.id.eq(id))
			.fetchOne();
	}

	@Transactional
	@Override
	public void updateLoginedAt(String id) {
		QAdmin qAdmin = QAdmin.admin;
		
		queryFactory
			.update(qAdmin)
			.set(qAdmin.updatedAt, Util.createdAt())
			.set(qAdmin.loginedAt, Util.createdAt())
			.where(qAdmin.id.eq(id))
			.execute();
	}

}
