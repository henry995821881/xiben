package com.xiben.pm.md.mapper;

import com.xiben.pm.md.bean.MdCompanyEx;
import com.xiben.pm.md.pojo.MdCompany;
import com.xiben.pm.md.pojo.MdCompanyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface MdCompanyMapper {
	
	
	/**
	 * @author henry
	 * @see  超级管理员 公司管理员、公司下属部门的授权人、公司下属部门的成员
	 * @return
	 */
	 List<MdCompanyEx>  queryComanpy(Integer userid);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int countByExample(MdCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int deleteByExample(MdCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int deleteByPrimaryKey(Integer companyid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int insert(MdCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int insertSelective(MdCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    List<MdCompany> selectByExample(MdCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    MdCompany selectByPrimaryKey(Integer companyid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int updateByExampleSelective(@Param("record") MdCompany record, @Param("example") MdCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int updateByExample(@Param("record") MdCompany record, @Param("example") MdCompanyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int updateByPrimaryKeySelective(MdCompany record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table md_company
     *
     * @mbggenerated Mon Nov 13 10:25:03 CST 2017
     */
    int updateByPrimaryKey(MdCompany record);

    /**
     * @author henry
     * @param fullName
     * @return
     */
	MdCompany queryByName(String fullName);

	MdCompanyEx getCompanyDetail(@Param("userid")Integer userid, @Param("companyid")Integer companyid);

	@Update("update md_company set logo=#{logourl,jdbcType=VARCHAR},updateby=#{userid,jdbcType=INTEGER} , updatedate= NOW() where companyid= #{companyid,jdbcType=INTEGER}")
	int updateLogo(@Param("userid")Integer userid, @Param("companyid")Integer companyid, @Param("logourl")String logourl);

}