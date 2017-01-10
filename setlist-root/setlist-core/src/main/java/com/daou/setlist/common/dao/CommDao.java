package com.daou.setlist.common.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 공통 DAO (myBatis)
 */
@Repository("commDao")
public class CommDao {

	private static final Logger log = LoggerFactory.getLogger(CommDao.class);

	@Autowired
	private SqlSession sqlSession;

	public int insert(String queryId, Object model) {
		log.info("commDao::insert() ==> {}, {}", queryId, model);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("insert parameter query id is empty");
		}
		if (model == null) {
			throw new IllegalArgumentException("insert parameter model is empty");
		}

		return sqlSession.insert(queryId, model);
	}

	public int update(String queryId, Object model) {
		log.info("commDao::update() ==> {}, {}", queryId, model);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("update parameter query id is empty");
		}

		if (model == null) {
			throw new IllegalArgumentException("update parameter model is empty");
		}
		
		int cnt = sqlSession.update(queryId, model);
		log.info("commDao::update() <== {} 건", cnt);

		return cnt;
	}

	public int delete(String queryId, Object model) {
		log.info("commDao::delete() ==> {}, {}", queryId, model);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("delete parameter query id is empty");
		}

		if (model == null) {
			throw new IllegalArgumentException("delete parameter model is empty");
		}

		return sqlSession.delete(queryId, model);
	}

	public <T> T selectOne(String queryId) {
		log.info("commDao::selectOne() ==> {}", queryId);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectOne parameter query id is empty");
		}
		
		T result = sqlSession.selectOne(queryId);
		
		oneResultLog(result);

		return result;
	}

	public <T> T selectOne(String queryId, Object model) {
		log.info("commDao::selectOne() ==> {}, {}", queryId, model);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectOne parameter query id is empty");
		}
		
		T result = sqlSession.selectOne(queryId, model);
		
		oneResultLog(result);

		return result;
	}

	public <T> List<T> selectList(String queryId) {
		log.info("commDao::selectList() ==> {}", queryId);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectList parameter query id is empty");
		}

		List<T> result = sqlSession.selectList(queryId);
		
		this.listResultLog(result);

		return result;
	}

	public <T> List<T> selectList(String queryId, Object model) {
		log.info("commDao::selectList() ==> {}, {}", queryId, model);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectList parameter query id is empty");
		}

		List<T> result = sqlSession.selectList(queryId, model);
		
		this.listResultLog(result);

		return result;
	}

	@Deprecated
	public <T> List<T> selectList(String queryId, Object model, RowBounds rowBounds) {
		log.info("commDao::selectList() ==> {}, {}, {}", queryId, model, rowBounds.toString());

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectList parameter query id is empty");
		}

		List<T> result = sqlSession.selectList(queryId, model, rowBounds);
		
		this.listResultLog(result);

		return result;
	}

	@Deprecated
	public <T> List<T> selectList(String queryId, Object model, int offset, int limit) {
		log.info("commDao::selectList() ==> {}, {}, {}, {}", queryId, model, offset, limit);

		if (StringUtils.isBlank(queryId)) {
			throw new IllegalArgumentException("selectList parameter query id is empty");
		}
		
		List<T> result = sqlSession.selectList(queryId, model, new RowBounds(offset, limit));
		
		this.listResultLog(result);
		
		return result;
	}

	private <T> void oneResultLog(T result) {
		if (result == null) {
			log.info("commDao::selectOne() <== null");
		} else {
			log.info("commDao::selectOne() <== {}, {}", result, result.getClass().getName());
		}
	}
	
	private <T> void listResultLog(List<T> result) {
		log.info("commDao::selectList() <== {} 건", result != null ? result.size() : 0);
		
		if (log.isDebugEnabled() && result != null) {
			int cnt = result.size() < 10 ? result.size() : 10;
			for (int i = 0; i < cnt ; i++) {
				log.debug("result [{}] {}", i, result.get(i));
			}
		}
	}
}