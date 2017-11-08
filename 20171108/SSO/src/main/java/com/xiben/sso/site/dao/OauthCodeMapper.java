package com.xiben.sso.site.dao;

import com.xiben.sso.site.bean.OauthCode;
import com.xiben.sso.site.bean.OauthCodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface OauthCodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int countByExample(OauthCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int deleteByExample(OauthCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int deleteByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int insert(OauthCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int insertSelective(OauthCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    List<OauthCode> selectByExample(OauthCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    OauthCode selectByPrimaryKey(Integer userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int updateByExampleSelective(@Param("record") OauthCode record, @Param("example") OauthCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int updateByExample(@Param("record") OauthCode record, @Param("example") OauthCodeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int updateByPrimaryKeySelective(OauthCode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table oauth_code
     *
     * @mbggenerated Mon Nov 06 23:58:26 CST 2017
     */
    int updateByPrimaryKey(OauthCode record);
}