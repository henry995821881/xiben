package com.xiben.pm.wf.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.xiben.pm.wf.pojo.WfTemplateNodeApproveUser;

public interface WfTemplateNodeApproveUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insert(WfTemplateNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insertSelective(WfTemplateNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    WfTemplateNodeApproveUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKeySelective(WfTemplateNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKey(WfTemplateNodeApproveUser record);

    List<WfTemplateNodeApproveUser> selectTemplatePartUserListByTempPartId(Integer templatepartid);


    int selectTemplateNodeUserCountForUserStartRight(Map<String,Integer> map);



    List<WfTemplateNodeApproveUser> queryWithDUCT(@Param("compid") Integer compid,@Param("userid") Integer userid,@Param("templateid") Integer templateid,@Param("deptid") Integer deptid);


    void deleteAllWithTempid(@Param("templateid") Integer templateid);

    List<WfTemplateNodeApproveUser> queryBy(@Param("field") String filed,@Param("val") String value);


    List<WfTemplateNodeApproveUser> queryBy1(@Param("field") String filed,@Param("val") String value,@Param("field1") String filed1,@Param("val1") String value1);


    List<WfTemplateNodeApproveUser> queryBy2(@Param("field") String filed,@Param("val") String value,
                                             @Param("field1") String filed1, @Param("val1") String value1,
                                             @Param("field2") String filed2,@Param("val2") String value2);


    void deleteShouquanren(@Param("deptid") Integer depid,@Param("userid") Integer userid,@Param("partid") Integer partid);

    @Select("SELECT distinct templateid FROM pm.wf_template_node_approve_user where userid=#{userid,jdbcType=INTEGER} and userrole=1 and deptid=#{deptid,jdbcType=INTEGER}")
    List<Integer> selectTemplateIdListHasMgrRight(@Param("deptid")int deptid,@Param("userid")int userid);
    
    


}