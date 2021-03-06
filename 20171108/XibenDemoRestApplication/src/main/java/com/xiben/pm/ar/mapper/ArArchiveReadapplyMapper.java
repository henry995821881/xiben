package com.xiben.pm.ar.mapper;

import com.xiben.pm.ar.pojo.ArArchiveReadapply;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ArArchiveReadapplyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int deleteByPrimaryKey(Integer appid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int insert(ArArchiveReadapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int insertSelective(ArArchiveReadapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    ArArchiveReadapply selectByPrimaryKey(Integer appid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int updateByPrimaryKeySelective(ArArchiveReadapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ar_archive_readapply
     *
     * @mbggenerated Tue Nov 14 14:06:28 CST 2017
     */
    int updateByPrimaryKey(ArArchiveReadapply record);


    @Select("SELECT * FROM ar_archive_readapply WHERE compid=#{compid,jdbcType=INTEGER} AND appuserid=#{appuserid,jdbcType=INTEGER} AND archiveid=#{archiveid,jdbcType=INTEGER}")
    List<ArArchiveReadapply> getBySomeId(@Param("compid") Integer compid,@Param("appuserid") Integer appuserid,@Param("archiveid") Integer arid);

    @Select("SELECT * FROM ar_archive_readapply WHERE archiveid=#{arid,jdbcType=INTEGER}")
    List<ArArchiveReadapply> getByArid(@Param("arid") Integer arid);


}