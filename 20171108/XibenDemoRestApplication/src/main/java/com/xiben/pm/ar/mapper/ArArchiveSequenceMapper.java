package com.xiben.pm.ar.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xiben.pm.ar.pojo.ArArchiveSequence;
import com.xiben.pm.md.pojo.MdAttchment;

public interface ArArchiveSequenceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int insert(ArArchiveSequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int insertSelective(ArArchiveSequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    ArArchiveSequence selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int updateByPrimaryKeySelective(ArArchiveSequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_sequence
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int updateByPrimaryKey(ArArchiveSequence record);
    
    @Select("select * from ar_archive_sequence a where  a.compid=#{compid,jdbcType=INTEGER} and a.groupno = #{groupno,jdbcType=VARCHAR}")
    ArArchiveSequence getArArchiveSequenceByCompidAndGroupno(@Param("compid")Integer compid, @Param("groupno")String groupno);
}