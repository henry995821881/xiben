package com.xiben.pm.tk.mapper;

import com.xiben.pm.tk.bean.TkTaskMonthScoreEx;
import com.xiben.pm.tk.pojo.TkTaskMonthScore;
import com.xiben.pm.tk.pojo.TkTaskMonthScoreExample;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TkTaskMonthScoreMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int countByExample(TkTaskMonthScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int deleteByExample(TkTaskMonthScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int insert(TkTaskMonthScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int insertSelective(TkTaskMonthScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    List<TkTaskMonthScore> selectByExample(TkTaskMonthScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    TkTaskMonthScore selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") TkTaskMonthScore record, @Param("example") TkTaskMonthScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int updateByExample(@Param("record") TkTaskMonthScore record, @Param("example") TkTaskMonthScoreExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int updateByPrimaryKeySelective(TkTaskMonthScore record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tk_task_monthscore
     *
     * @mbggenerated Thu Nov 23 08:59:43 CST 2017
     */
    int updateByPrimaryKey(TkTaskMonthScore record);

    
	List<TkTaskMonthScoreEx> getBydeptid(@Param("deptid")Integer deptid, @Param("year")Integer year);

	@Select("select  t.* from tk_task_monthscore  t order by t.id desc limit 0,1")
	TkTaskMonthScore getLastData();
    @Delete("delete from tk_task_monthscore")
	void deleteAll();
    
	void insertAll();
}