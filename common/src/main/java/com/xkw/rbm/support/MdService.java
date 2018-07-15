/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

package com.xkw.rbm.support;

import com.xkw.mdm.dto.*;

import java.util.Collection;
import java.util.List;

/**
 * 提供主数据API访问服务，包含：
 * <p>用户登录，角色，权限API。这部分API与应用相关，本服务默认使用本系统（qbm）的身份访问主数据API
 * <p>学科，课程，教材（课本），教材目录，知识点等数据
 *
 * @author tecky lee
 * @since 1.0
 */
public interface MdService {

    /**
     * 本系统在mdm系统中的应用ID，硬编码，不要改
     */
    public static final String QBM_APP_ID_IN_MDM = "qbm";

    /**
     * 调用MDM API用户登录
     * <p></p>异常：IncorrectCredentialsException:用户名或密码错误
     *
     * @param username
     * @param pwd
     * @param withAuthorization 是否要返回用户信息（包含权限信息）
     * @return
     */
    UserInfo login(String username, String pwd, boolean withAuthorization);

    /**
     * 调用MDM API获取QBM的用户信息（包含权限信息）。
     * <p>注意：这个用户信息不包含密码（因为我们系统中不存储用户密码，直接调用的用户中心的API）
     * 所以此方法不能用于shiro AuthenticatingRealm#doGetAuthenticationInfo方法中获取用户信息
     *
     * @param username
     * @return
     */
    UserInfo getUserInfo(String username);

    boolean checkUserByName(String userName);

    //region account

    /**
     * 根据多个用户名获取多个用户信息，如果不存在返回空List
     *
     * @param userNames
     * @return
     */
    List<User> getUserByNames(List<String> userNames);

    //endregion

    //region areas
    List<AreaLevel> getAreaLevels();

    /**
     * getAreas
     * 获取所有的行政区列表&lt;br&gt;&lt;br&gt;不包含乡镇（街道）级别。
     *
     * @param level 最深的行政区级别，默认为到区县 (optional, default to COUNTY)
     * @param all   是否包含学科网定制的非行政区域，如全国、全国一。默认为包含。 (optional, default to true)
     * @return List<Area>
     * @ if fails to make API call
     */
    List<Area> getAreas(String level, Boolean all);

    /**
     * 根据id获取地区
     *
     * @param id
     * @return
     */
    Area getAreaById(String id);

    /**
     * 获取某个地区的直接下一级地区
     *
     * @param areaId
     * @return
     */
    List<Area> getAreaChildrenById(String areaId);

    //endregion

    //region grades
    List<GradeDivision> getGradeDivisions();

    List<Grade> getGrades();

    Grade getGradeById(int gradeId);

    List<Grade> getGradesByStageId(String stageId, String gd);
    //endregion

    //region stages
    List<Stage> getStages();
    //endregion

    //region terms
    List<Term> getTerms();

    //endregion

    //region subjects
    List<Subject> getSubjects();

    //endregion

    //region course

    List<Course> getCourses(String stageId, Integer subjectId);

    List<Course> getCourses();

    /**
     * 根据id获取课程
     *
     * @param courseId
     * @return 如果存在就返回course，不存在返回null
     */
    Course getCourseById(int courseId);

    //endregion

    //region textbook versions
    List<TextbookVersion> getTextBookVersionByCourseId(Integer id);

    List<TextbookVersion> getTextbookVersions();
    //endregion

    //region textbooks

    /**
     * getTextbooks
     * 获取教材列表
     *
     * @param stageid     学段id：PRIMARY(小学)，JUNIOR_MIDDLE（初中），SENIOR_MIDDLE（高中）。请参考学段相关的API获取更多信息。 (optional)
     * @param courseid    课程Id (optional)
     * @param versionid   教材版本ID (optional)
     * @param gradeid     年级id (optional)
     * @param namekeyword 教材名称关键字 (optional)
     * @param term        学期id。LAST（上学期），NEXT（下学期） (optional)
     * @return List<Textbook>
     * @ if fails to make API call
     */
    List<Textbook> getTextbooks(String stageid, Integer courseid, Integer versionid, Integer gradeid, String namekeyword, String term);

    /**
     * 根据id获取课本
     *
     * @param id
     * @return
     */
    Textbook getTextbookById(int id);
    //endregion

    //region catalogs

    /**
     * 获取一个课本的章节
     *
     * @param tbId  课本id
     * @param depth 深度 optional
     * @return
     */
    List<CatalogNode> getCatalogNodesByTextbookId(Integer tbId, Integer depth);
    //endregion

    CatalogItem getCatalogNodeById(Integer catalogId);

    List<CatalogItem> getCatalogNodePathById(Integer catalogId);

    List<CatalogItem> getCatalogNodeListByIds(Collection<Integer> catalogIds);

    List<CatalogItem> getChildCatalogNodeListById(Integer parentId);

    boolean isTop100School(int schoolId);

    List<Organization> getOrganizations(String districtId, String keywords, String stage);

    /**
     * 获取指定系统包含的所有角色。本系统的ID为{@link #QBM_APP_ID_IN_MDM}
     *
     * @param type          0：菜单角色，null：不限类型，other：数据角色
     * @param applicationId
     * @return
     */
    List<Role> getRoles(Integer type, String applicationId);

    /**
     * 获取指定角色的用户列表
     *
     * @param roleId        角色id
     * @param applicationId 应用id
     * @return
     */
    List<User> getUsersByRoleId(String roleId, String applicationId);

    User getUserFromXkw(String username);
}
