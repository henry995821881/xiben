package com.xiben.pm.md.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xiben.pm.md.pojo.MdAttchment;

public interface MdAttchmentMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    int deleteByPrimaryKey(Integer attachid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    int insert(MdAttchment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    int insertSelective(MdAttchment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    MdAttchment selectByPrimaryKey(Integer attachid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    int updateByPrimaryKeySelective(MdAttchment record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_attchments
     *
     * @mbggenerated Fri Nov 17 14:34:09 CST 2017
     */
    int updateByPrimaryKey(MdAttchment record);
    
    @Select("select * from md_attchments a where  a.biztype=#{biztype,jdbcType=INTEGER} and a.bizid = #{bizid,jdbcType=INTEGER}")
	List<MdAttchment> query(@Param("bizid")Integer bizid, @Param("biztype")Integer biztype);


    @Select("SELECT * FROM md_attchments WHERE archiveid=#{aid,jdbcType=INTEGER}")
    List<MdAttchment> queryWithAid(@Param("aid") Integer aid);

    @Update("update md_attchments t set `status`=2 where bizid =#{dutyid,jdbcType=INTEGER} and biztype=5")
	void deleteByDuty(@Param("dutyid")Integer dutyid);


}