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
import com.taksila.veda.db.dao.TopicDAO;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.course.v1_0.Chapter;
import com.taksila.veda.model.api.course.v1_0.CreateChapterRequest;
import com.taksila.veda.model.api.course.v1_0.CreateChapterResponse;
import com.taksila.veda.model.api.course.v1_0.DeleteChapterRequest;
import com.taksila.veda.model.api.course.v1_0.DeleteChapterResponse;
import com.taksila.veda.model.api.course.v1_0.GetChapterRequest;
import com.taksila.veda.model.api.course.v1_0.GetChapterResponse;
import com.taksila.veda.model.api.course.v1_0.SearchChaptersRequest;
import com.taksila.veda.model.api.course.v1_0.SearchChaptersResponse;
import com.taksila.veda.model.api.course.v1_0.UpdateChapterRequest;
import com.taksila.veda.model.api.course.v1_0.UpdateChapterResponse;
import com.taksila.veda.utils.CommonUtils;

@Component
@Scope(value="prototype")
public class ChapterComponent 
{	
	@Autowired
	ApplicationContext applicationContext;
	
	private String tenantId;
	static Logger logger = LogManager.getLogger(ChapterComponent.class.getName());
	
	@Autowired
	public ChapterComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating ChapterComponent bean for tenant "+tenantId);		
		this.tenantId = tenantId;	
	}
			
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchChaptersResponse searchChapter(SearchChaptersRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	
		
		SearchChaptersResponse resp = new SearchChaptersResponse();
		try 
		{
			List<Chapter> chapterSearchHits = chapterDAO.searchChaptersByTitle(req.getQuery());
			
			for(Chapter chapter: chapterSearchHits)
			{
				SearchHitRecord rec = new SearchHitRecord();
				/*
				 * map search hits
				 */
				rec.setRecordId(String.valueOf(chapter.getId()));
				rec.setRecordTitle(chapter.getTitle());
				rec.setRecordSubtitle(chapter.getSubTitle());				
				
				resp.getHits().add(rec);
			}
			
			resp.setRecordType("CHAPTER");
			resp.setPage(req.getPage());
			resp.setPageOffset(req.getPageOffset());
			resp.setTotalHits(chapterSearchHits.size());

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
	public GetChapterResponse getChapter(GetChapterRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);	

		
		GetChapterResponse resp = new GetChapterResponse();
		try 
		{
			Chapter chapter = chapterDAO.getChapterById(req.getId());
			
			if (chapter == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				chapter.getTopics().addAll(topicDAO.searchTopicsByChapterid(req.getId()));
				resp.setChapter(chapter);
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
	public CreateChapterResponse createNewChapter(CreateChapterRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	
		
		CreateChapterResponse resp = new CreateChapterResponse();
		try 
		{							
			Chapter course = chapterDAO.insertChapter(req.getChapter());
			resp.setChapter(course);
			resp.setStatus(StatusType.SUCCESS);
			resp.setMsg("Successfully created a new chapter id = "+resp.getChapter().getId());
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
	public UpdateChapterResponse updateChapter(UpdateChapterRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	
		
		UpdateChapterResponse resp = new UpdateChapterResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = chapterDAO.updateChapter(req.getChapter());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setChapter(req.getChapter());
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
	public DeleteChapterResponse deleteChapter(DeleteChapterRequest req)
	{
		ChapterDAO chapterDAO = applicationContext.getBean(ChapterDAO.class,tenantId);	

		DeleteChapterResponse resp = new DeleteChapterResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = chapterDAO.deleteChapter(req.getId());
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
