package com.test.schedule.dao.impl;

import com.junyi.cms.schedule.dao.ScheduleJobDao;
import com.junyi.cms.schedule.model.ScheduleJob;
import com.test.schedule.model.ScheduleJob;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shuisheng on 2018-02-11.
 */
@Repository
public class ScheduleJobDaoImpl implements ScheduleJobDao {


    private static String scheduleJobNameSpace = "com.junyi.cms.schedule.model.ScheduleJobMapper.";

    @Autowired
    SqlSession sqlSession;


    /**
     * 添加
     * @param job
     * @return
     */
    @Override
    public int insertScheduleJob(ScheduleJob job) {

        //这里还要检查长度等

        return sqlSession.insert(scheduleJobNameSpace + "insertScheduleJob", job);
    }

    /**
     * 查询
     * @param params
     * @return
     */
    @Override
    public List<ScheduleJob> getScheduleJob(Map<String, Object> params) {
        return sqlSession.selectList(scheduleJobNameSpace + "getScheduleJob", params);
    }


    @Override
    public ScheduleJob getScheduleJobByJobId(long jobId) {
        if (jobId > 0) {
            Map<String, Object> params = new HashMap<>();
            params.put("pJobId", jobId);
            return sqlSession.selectOne(scheduleJobNameSpace + "getScheduleJobByJobId", params);
        }

        return null;
    }

    /**
     * 更新
     * @param params
     * @return
     */
    @Override
    public int updateScheduleJobByJobId(Map<String, Object> params) {
        return sqlSession.update(scheduleJobNameSpace + "updateScheduleJobByJobId", params);
    }

	@Override
	public int getScheduleJobCnt(Map<String, Object> param) {
		List<Integer> list = sqlSession.selectList(scheduleJobNameSpace + "getScheduleJobCnt", param);
		return list.get(0);
	}

	@Override
	public List<ScheduleJob> getScheduleJobByMap(Map<String, Object> param) {
		return sqlSession.selectList(scheduleJobNameSpace + "getScheduleJobByMap", param);
	}

}
