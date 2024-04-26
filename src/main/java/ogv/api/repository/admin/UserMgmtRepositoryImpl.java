package ogv.api.repository.admin;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import ogv.api.dto.PageDto;
import ogv.api.dto.UserMgmtDto;
import ogv.api.entity.QUser;
import ogv.api.entity.User;
import ogv.api.util.Util;

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
		QUser qAdmin = QUser.user;
		
		Long count = queryFactory.select(qAdmin.id)
			.from(qAdmin)
			.where(qAdmin.id.eq(id))
			.fetchCount();
		
		return count > 0 ? false : true;
	}

	@Transactional
	@Override
	public void saveUser(UserMgmtDto userMgmtDto) {
		User admin = User.builder()
				.id(userMgmtDto.getId())
				.name(userMgmtDto.getName())
				.role(userMgmtDto.getRole())
				.branch(userMgmtDto.getBranch())
				.createdAt(Util.createdAt())
				.updatedAt(Util.createdAt())
				.password(passwordEncoder.encode(userMgmtDto.getId()))
				.build();
		
		em.persist(admin);
	}

	@Override
	public List<UserMgmtDto> getUser(PageDto pageDto) {
		QUser qUser = QUser.user;
		
		// 검색
		BooleanExpression search = null;
		if (!pageDto.getSearch().isEmpty()) {
			switch (pageDto.getSearchTarget()) {
			case ID:
				search = qUser.id.contains(pageDto.getSearch());
				break;
				
			case NAME:
				search = qUser.name.contains(pageDto.getSearch());
				break;
				
			case ALL:
				search = qUser.name.contains(pageDto.getSearch()).or(qUser.id.contains(pageDto.getSearch()));
				break;
			}
		}
		
		JPAQuery<UserMgmtDto> adminDtos =  queryFactory
			.select(Projections.fields(UserMgmtDto.class, qUser.seq , qUser.name, qUser.id, qUser.role, qUser.createdAt, qUser.branch))
			.from(qUser)
			.where(search)
			.orderBy(qUser.seq.desc())
			.offset((pageDto.getPage()-1) * pageDto.getSize())
			.limit(pageDto.getSize());
		
		pageDto.setAmount(adminDtos.fetchCount());
		
		return adminDtos.fetch();
	}

	@Transactional
	@Override
	public void deleteUser(Map<String, Long[]> map) {
		QUser qUser = QUser.user;
	 	Long[] seq = map.get("seq");
	 	
	 	for(int i = 0 ; i < seq.length; i++) {
	 		queryFactory
	 			.delete(qUser)
	 			.where(qUser.seq.eq(seq[i]))
	 			.execute();
	 	}
	}

	@Override
	public UserMgmtDto getUserInfo(Long seq) {
		User user = em.find(User.class, seq);
		
		return UserMgmtDto.builder()
				.seq(user.getSeq())
				.role(user.getRole())
				.id(user.getId())
				.name(user.getName())
				.branch(user.getBranch())
				.createdAt(user.getCreatedAt())
				.build();
				
	}

	@Transactional
	@Override
	public void updateUser(UserMgmtDto userMgmtDto) {
		User user = em.find(User.class, userMgmtDto.getSeq());
		user.update(userMgmtDto);
	}

	@Transactional
	@Override
	public void resetPW(Long seq) {
		User user = em.find(User.class, seq);
		user.resetPW(passwordEncoder.encode(user.getId()));
	}

}
