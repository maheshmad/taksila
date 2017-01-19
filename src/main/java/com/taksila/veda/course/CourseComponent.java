package com.taksila.veda.course;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.ChapterDAO;
import com.taksila.veda.db.dao.CoursesDAO;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.course.v1_0.Course;
import com.taksila.veda.model.api.course.v1_0.CreateNewCourseRequest;
import com.taksila.veda.model.api.course.v1_0.CreateNewCourseResponse;
import com.taksila.veda.model.api.course.v1_0.DeleteCourseRequest;
import com.taksila.veda.model.api.course.v1_0.DeleteCourseResponse;
import com.taksila.veda.model.api.course.v1_0.GetCourseInfoRequest;
import com.taksila.veda.model.api.course.v1_0.GetCourseInfoResponse;
import com.taksila.veda.model.api.course.v1_0.SearchCourseRequest;
import com.taksila.veda.model.api.course.v1_0.SearchCourseResponse;
import com.taksila.veda.model.api.course.v1_0.UpdateCourseRequest;
import com.taksila.veda.model.api.course.v1_0.UpdateCourseResponse;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
public class CourseComponent 
{	
	@Autowired
	ApplicationContext applicationContext;

	static Logger logger = LogManager.getLogger(CourseComponent.class.getName());
	private String tenantId;
	
	public CourseComponent(@Value("tenantId") String tenantId) 
	{
		this.tenantId = tenantId;	
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchCourseResponse searchCourses(SearchCourseRequest req)
	{
		CoursesDAO coursesDAO = applicationContext.getBean(CoursesDAO.class,tenantId);	
		
		SearchCourseResponse resp = new SearchCourseResponse();
		try 
		{
			List<Course> courseSearchHits = coursesDAO.searchCoursesByTitle(req.getQuery());
			
			for(Course course: courseSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(course.getId()));
				rec.setRecordTitle(course.getTitle());
				rec.setRecordSubtitle(course.getSubTitle());				
				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("COURSE");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(courseSearchHits.size());

		} 
		catch (Exception e) 
		{			
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public GetCourseInfoResponse getCourse(GetCourseInfoRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	
		CoursesDAO coursesDAO = applicationContext.getBean(CoursesDAO.class,tenantId);	
		
		GetCourseInfoResponse resp = new GetCourseInfoResponse();
		try 
		{
			Course course = coursesDAO.getCoursesById(req.getId());
			
			if (course == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				/*
				 * get chapters for the course
				 */
				course.getChapters().addAll(chapterDAO.searchChaptersByCourseId(req.getId()));
				resp.setCourse(course);
			}					

		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public CreateNewCourseResponse createNewCourse(CreateNewCourseRequest req)
	{
		CoursesDAO coursesDAO = applicationContext.getBean(CoursesDAO.class,tenantId);	
		
		CreateNewCourseResponse resp = new CreateNewCourseResponse();
		try 
		{
			//TODO validation
			
			Course course = coursesDAO.insertCourse(req.getNewCourse());
			resp.setCourse(course);
			resp.setStatus(StatusType.SUCCESS);
			resp.setMsg("Successfully added a new course id = "+course.getId());
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public UpdateCourseResponse updateCourse(UpdateCourseRequest req)
	{
		CoursesDAO coursesDAO = applicationContext.getBean(CoursesDAO.class,tenantId);	
		
		UpdateCourseResponse resp = new UpdateCourseResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = coursesDAO.updateCourse(req.getCourse());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setCourse(req.getCourse());
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
			}
			
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public DeleteCourseResponse deleteCourse(DeleteCourseRequest req)
	{
		CoursesDAO coursesDAO = applicationContext.getBean(CoursesDAO.class,tenantId);	
		
		DeleteCourseResponse resp = new DeleteCourseResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = coursesDAO.deleteCourse(req.getId());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
			}
			else
			{
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Database was not updated! Please check your input"));
			}
			
		} 
		catch (Exception e) 
		{
			CommonUtils.handleExceptionForResponse(resp, e);
		}
		return resp;
		
	}
	
}
