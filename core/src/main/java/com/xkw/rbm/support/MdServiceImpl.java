/*
 * Copyright (C) 2016 the xkw.com authors.
 * http://www.xkw.com
 */

package com.xkw.rbm.support;

import com.xkw.commons.exception.ResourceNotFoundException;
import com.xkw.mdm.api.ApiException;
import com.xkw.mdm.api.DefaultApi;
import com.xkw.mdm.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author tecky lee
 * @since 1.0
 */
@Service
public class MdServiceImpl implements MdService {

    public static final String CACHE_NAME = "MDM_API";
    public static final long CACHE_TTL = 3600;

    Logger logger = LoggerFactory.getLogger(MdServiceImpl.class);

    // todo
    DefaultApi mdsClient;

    private RuntimeException wrapToQbmException(Exception ex) {
        return new RuntimeException();
    }

    @Override
    public UserInfo login(String username, String pwd, boolean withAuthorization) {
        try {
            return mdsClient.login(QBM_APP_ID_IN_MDM, username, pwd, withAuthorization);
        } catch (ApiException e) {
            throw wrapToQbmException(e);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public UserInfo getUserInfo(String username) {
        try {
            return mdsClient.getUserInfo(QBM_APP_ID_IN_MDM, username);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public boolean checkUserByName(String userName) {
        try {
            UserInfo info = mdsClient.getUserInfo("qbm", userName);
            return info != null;
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    /**
     * 根据多个用户名获取多个用户信息，如果不存在返回空List
     *
     * @param userNames
     * @return
     */
    @Override
    public List<User> getUserByNames(List<String> userNames) {
        try {
            return mdsClient.getUserByNames(userNames);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getAreaLevels'")
    @Override
    public List<AreaLevel> getAreaLevels() {
        try {
            return mdsClient.getAreaLevelsUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    /**
     * getAreas
     * 获取所有的行政区列表
     *
     * @param level 最深的行政区级别，默认为到区县 (optional, default to COUNTY)
     * @param all   是否包含学科网定制的非行政区域，如全国、全国一。默认为包含。 (optional, default to true)
     * @return List<Area>
     * @ if fails to make API call
     */
    @Cacheable(value = CACHE_NAME, key = "'getAreas_'+#level+'_'+#all")
    @Override
    public List<Area> getAreas(String level, Boolean all) {
        try {
            return mdsClient.getAreasUsingGET(level, all);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    /**
     * 获取某个id的行政区列表
     *
     * @param id
     * @return
     */
    @Cacheable(value = CACHE_NAME, key = "'getAreaById'+#id")
    @Override
    public Area getAreaById(String id) {
        try {
            return mdsClient.getAreaByIdUsingGET(id);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'getAreaChildren_'+#areaId")
    public List<Area> getAreaChildrenById(String areaId) {
        try {
            return mdsClient.getAreaChildrenByIdUsingGET(areaId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getGradeDivisions'")
    @Override
    public List<GradeDivision> getGradeDivisions() {
        try {
            return mdsClient.getGradeDivisionsUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getGrades'")
    @Override
    public List<Grade> getGrades() {
        try {
            return mdsClient.getGradesUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getGradesByStageId_'+#stageId+'_'+ #gd")
    @Override
    public List<Grade> getGradesByStageId(String stageId, String gd) {
        try {
            return mdsClient.getGradesByStageIdUsingGET(stageId, gd);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getGradeById'+'_'+ #gradeId")
    @Override
    public Grade getGradeById(int gradeId) {
        Optional<Grade> optional =
                this.getGrades().stream().filter(grade -> grade.getId() == gradeId).findFirst();
        if (optional.isPresent())
            return optional.get();

        throw new ResourceNotFoundException("ID为【" + gradeId + "】的年级不存在");
    }

    @Cacheable(value = CACHE_NAME, key = "'getStages'")
    @Override
    public List<Stage> getStages() {
        try {
            return mdsClient.getStagesUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getTerms'")
    @Override
    public List<Term> getTerms() {
        try {
            return mdsClient.getTermsUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getSubjects'")
    @Override
    public List<Subject> getSubjects() {
        try {
            return mdsClient.getSubjectsUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getCourses_'+#stageId+'_'+#subjectId")
    @Override
    public List<Course> getCourses(String stageId, Integer subjectId) {
        try {
            return mdsClient.getCoursesUsingGET(stageId, subjectId);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getCourses'")
    @Override
    public List<Course> getCourses() {
        try {
            return mdsClient.getCoursesUsingGET(null, null);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    /**
     * 根据id获取课程
     *
     * @param courseId
     */
    @Cacheable(value = CACHE_NAME, key = "'getCourseById_'+#courseId")
    @Override
    public Course getCourseById(int courseId) {
        Optional<Course> optional =
                getCourses().stream().filter(c -> c.getId() == courseId).findFirst();
        if (optional.isPresent()) {
            return optional.get();
        }

        throw new ResourceNotFoundException("ID为【" + courseId + "】的课程不存在");
    }

    @Cacheable(value = CACHE_NAME, key = "'getTextBookVersionByCourseId_'+#courseId")
    @Override
    public List<TextbookVersion> getTextBookVersionByCourseId(Integer courseId) {
        try {
            return mdsClient.getTextBookVersionByCourseIdUsingGET(courseId);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getTextbookVersions'")
    @Override
    public List<TextbookVersion> getTextbookVersions() {
        try {
            return mdsClient.getTextbookVersionsUsingGET();
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    /**
     * getTextbooks
     * 获取教材列表
     *
     * @param stageid     学段id。2(小学)，3（初中），4（高中）。请参考学段相关的API获取更多信息。 (optional)
     * @param courseid    课程Id (optional)
     * @param versionid   教材版本ID (optional)
     * @param gradeid     年级id (optional)
     * @param namekeyword 教材名称关键字 (optional)
     * @param term        学期id。LAST（上学期），NEXT（下学期） (optional)
     * @return List<Textbook>
     * @ if fails to make API call
     */
    @Cacheable(value = CACHE_NAME, key = "'getTextbooks_'+#stageid+'_'+#courseid+'_'+#versionid+'_'+#gradeid+'_'+#namekeyword+'_'+#term")
    @Override
    public List<Textbook> getTextbooks(String stageid, Integer courseid, Integer versionid, Integer gradeid, String namekeyword, String term) {
        try {
            return mdsClient.getTextbooksUsingGET(stageid, courseid, versionid, gradeid, namekeyword, term);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    /**
     * 根据id获取课本
     *
     * @param id
     * @return
     */
    @Cacheable(value = CACHE_NAME, key = "'getTextbookById_'+#id")
    @Override
    public Textbook getTextbookById(int id) {
        try {
            return mdsClient.getTextBookByIdUsingGET(id);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Cacheable(value = CACHE_NAME, key = "'getCatalogNodesByTextbookId_'+#tbId+'_'+#depth")
    @Override
    public List<CatalogNode> getCatalogNodesByTextbookId(Integer tbId, Integer depth) {
        try {
            return mdsClient.getCatalogNodesByTextbookIdUsingGET(tbId, depth);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Override
    public CatalogItem getCatalogNodeById(Integer catalogId) {
        try {
            return mdsClient.getCatalogNodeByIdUsingGET(catalogId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public List<CatalogItem> getCatalogNodePathById(Integer catalogId) {
        try {
            return mdsClient.getCatalogNodePathByIdUsingGET(catalogId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public List<CatalogItem> getCatalogNodeListByIds(Collection<Integer> catalogIds) {
        try {
            return mdsClient.getCatalogNodeListByIdsUsingGET(catalogIds);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public List<CatalogItem> getChildCatalogNodeListById(Integer parentId) {
        try {
            return mdsClient.getCatalogNodeChildrenByIdUsingGET(parentId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public boolean isTop100School(int schoolId) {
        if (schoolId <= 0) {
            return false;
        }
        try {
            return mdsClient.isTop100School(schoolId);
        } catch (Exception ex) {
            throw wrapToQbmException(ex);
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'getOrganizations_'+#districtId+'_'+#keywords+'_'+#stage")
    public List<Organization> getOrganizations(String districtId, String keywords, String stage) {
        try {
            return mdsClient.getOrganizations(districtId, keywords, stage);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'Roles_'+#type+'_'+#applicationId")
    public List<Role> getRoles(Integer type, String applicationId) {
        try {
            return mdsClient.getRolesUsingGET(type, applicationId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    @Cacheable(value = CACHE_NAME, key = "'getUsersByRoleId_'+#roleId+'_'+#applicationId")
    public List<User> getUsersByRoleId(String roleId, String applicationId) {
        try {
            return mdsClient.getUsersByRoleIdUsingGET(roleId, applicationId);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

    @Override
    public User getUserFromXkw(String username) {
        try {
            return mdsClient.getUserFromXkw(username);
        } catch (Exception e) {
            throw wrapToQbmException(e);
        }
    }

}

