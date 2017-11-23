package com.xiben.pm.wf.mapper;

import com.xiben.pm.wf.pojo.WfInstanceNodeApproveUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WfInstanceNodeApproveUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insert(WfInstanceNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insertSelective(WfInstanceNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    WfInstanceNodeApproveUser selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKeySelective(WfInstanceNodeApproveUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_instance_node_approve_user
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKey(WfInstanceNodeApproveUser record);
    
    //inspartid,userid
    WfInstanceNodeApproveUser selectInstanceNodeApproveUserByUserId(Map<String,Integer> map);
    
    List<WfInstanceNodeApproveUser> selectInstanceNodeApproveUserByInsPartId(Integer inspartId);
    
    List<WfInstanceNodeApproveUser> selectInstanceNodeNoApproveUserByInsPartId(Integer inspartId);
    
    List<Integer> selectInstanceStartNodeApprovedUser(int insid);

    List<WfInstanceNodeApproveUser> queryBy(@Param("field") String field, @Param("val") String value);

    List<WfInstanceNodeApproveUser> queryBy1(@Param("field") String field,@Param("val") String value,
                              @Param("field1") String field1,@Param("val1") String value1);


    List<WfInstanceNodeApproveUser> queryBy2(@Param("field") String field,@Param("val") String value,
                              @Param("field1") String field1,@Param("val1") String value1,
                              @Param("field2") String field2,@Param("val2") String value2);

    List<WfInstanceNodeApproveUser> queryInsNodeUserMapperWithType(@Param("compid") Integer compid ,@Param("type") Integer type);
    
    List<WfInstanceNodeApproveUser> selectInstanceNodeApproveUserList(Integer insid);
    
    List<WfInstanceNodeApproveUser> selectInstanceNodeApproveUserListByInsNodeid(Integer insnodeid);
    
    
}