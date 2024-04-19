package ogv.api.repository.admin;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import ogv.api.dto.UserMgmtDto;
import ogv.api.entity.Admin;
import ogv.api.entity.QAdmin;
import ogv.api.util.Util;
import ogv.api.util.Values.ADMIN_ROLE;
import ogv.api.util.Values.USER_TYPE;

@Repository
@Transactional(readOnly = true)
public class UserMgmtRepositoryImpl implements UserMgmtRepository {

	private final EntityManager em;
	private final JPAQueryFactory queryFactory;
	private final PasswordEncoder passwordEncoder;

	public UserMgmtRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory, PasswordEncoder passwordEncoder) {
		this.em = em;
		this.queryFactory = new JPAQueryFactory(em);
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Boolean checkUserId(String id) {
		QAdmin qAdmin = QAdmin.admin;
		
		Long count = queryFactory.select(qAdmin.id)
			.from(qAdmin)
			.where(qAdmin.id.eq(id))
			.fetchCount();
		
		return count > 0 ? false : true;
	}

	@Transactional
	@Override
	public void saveUser(UserMgmtDto userMgmtDto) {
		// 1. 본사 관리자를 등록했을 경우
		if (userMgmtDto.getType().equals(USER_TYPE.ADMIN)) {
			Admin admin = Admin.builder()
					.id(userMgmtDto.getId())
					.name(userMgmtDto.getName())
					.role(userMgmtDto.getRole())
					.createdAt(Util.createdAt())
					.updatedAt(Util.createdAt())
					.password(passwordEncoder.encode(userMgmtDto.getId()))
					.build();
			
			if(userMgmtDto.getRole().equals(ADMIN_ROLE.BRANCH)) {
				admin = Admin.builder()
						.id(userMgmtDto.getId())
						.name(userMgmtDto.getName())
						.role(userMgmtDto.getRole())
						.branch(userMgmtDto.getBranch())
						.createdAt(Util.createdAt())
						.updatedAt(Util.createdAt())
						.password(passwordEncoder.encode(userMgmtDto.getId()))
						.build();
			}
		
			em.persist(admin);
		} 
	}

}
