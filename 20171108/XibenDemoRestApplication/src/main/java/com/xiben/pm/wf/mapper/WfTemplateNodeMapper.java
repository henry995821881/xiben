package com.xiben.pm.wf.mapper;

import com.xiben.pm.wf.pojo.WfTemplateNode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WfTemplateNodeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int deleteByPrimaryKey(Integer templatenodeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insert(WfTemplateNode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int insertSelective(WfTemplateNode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    WfTemplateNode selectByPrimaryKey(Integer templatenodeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKeySelective(WfTemplateNode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wf_template_node
     *
     * @mbggenerated Tue Nov 14 14:06:07 CST 2017
     */
    int updateByPrimaryKey(WfTemplateNode record);

    List<WfTemplateNode> queryAll();
    List<WfTemplateNode> queryBy(@Param("field") String filed, @Param("val") String val);
    void deleteAllWithTempId(Integer templateid);
    
    WfTemplateNode getStartNodeByTemplateid(Integer templateid);
    
    WfTemplateNode getNextNode(WfTemplateNode templateNode);

    WfTemplateNode getPreNode(WfTemplateNode templateNode);
    

    List<WfTemplateNode> queryByTCN(@Param("templateid") Integer templateid,@Param("compid") Integer compid);

    List<WfTemplateNode> queryByTNCType(@Param("templateid") Integer templateid,@Param("compid") Integer compid,@Param("notetype") Integer notetype);


}