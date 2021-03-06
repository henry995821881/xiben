package com.xiben.pm.md.mapper;

import com.xiben.pm.md.bean.MdNoticeEx;
import com.xiben.pm.md.pojo.MdNotice;
import com.xiben.pm.md.pojo.MdNoticeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface MdNoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int countByExample(MdNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int deleteByExample(MdNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int deleteByPrimaryKey(Integer noticeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int insert(MdNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int insertSelective(MdNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    List<MdNotice> selectByExample(MdNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    MdNotice selectByPrimaryKey(Integer noticeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int updateByExampleSelective(@Param("record") MdNotice record, @Param("example") MdNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int updateByExample(@Param("record") MdNotice record, @Param("example") MdNoticeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int updateByPrimaryKeySelective(MdNotice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_notice
     *
     * @mbggenerated Fri Nov 17 15:51:44 CST 2017
     */
    int updateByPrimaryKey(MdNotice record);

	List<MdNoticeEx> getNoticeList(@Param("userid")Integer userid);
}