package com.xiben.pm.wf.mapper;

import com.xiben.pm.wf.pojo.WfInstance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WfInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int deleteByPrimaryKey(Integer insid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insert(WfInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insertSelective(WfInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    WfInstance selectByPrimaryKey(Integer insid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKeySelective(WfInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKey(WfInstance record);

    int queryCountWithStatus(@Param("templateid") Integer templateid);

    List<WfInstance> queryBy(@Param("filed") String filed,@Param("val") String value);
    List<WfInstance> queryBy1(@Param("filed") String filed,@Param("val") String value,
                              @Param("filed1") String filed1,@Param("val1") String value1);
    List<WfInstance> queryBy2(@Param("filed") String filed,@Param("val") String value,
                              @Param("filed") String filed1,@Param("val1") String value1,
                              @Param("filed") String filed2,@Param("val2") String value2);

}