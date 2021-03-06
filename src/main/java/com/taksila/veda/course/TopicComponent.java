package com.taksila.veda.course;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.db.dao.TopicDAO;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.course.v1_0.CreateTopicRequest;
import com.taksila.veda.model.api.course.v1_0.CreateTopicResponse;
import com.taksila.veda.model.api.course.v1_0.DeleteTopicRequest;
import com.taksila.veda.model.api.course.v1_0.DeleteTopicResponse;
import com.taksila.veda.model.api.course.v1_0.GetTopicRequest;
import com.taksila.veda.model.api.course.v1_0.GetTopicResponse;
import com.taksila.veda.model.api.course.v1_0.SearchTopicsRequest;
import com.taksila.veda.model.api.course.v1_0.SearchTopicsResponse;
import com.taksila.veda.model.api.course.v1_0.Topic;
import com.taksila.veda.model.api.course.v1_0.UpdateTopicRequest;
import com.taksila.veda.model.api.course.v1_0.UpdateTopicResponse;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
public class TopicComponent 
{	
	@Autowired
	ApplicationContext applicationContext;
	
	static Logger logger = LogManager.getLogger(TopicComponent.class.getName());
	private String tenantId;
	
	@Autowired
	public TopicComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating TopicComponent bean for tenant "+tenantId);		
		this.tenantId = tenantId;
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchTopicsResponse searchTopic(SearchTopicsRequest req)
	{
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);		

		SearchTopicsResponse resp = new SearchTopicsResponse();
		try 
		{
			List<Topic> courseSearchHits = topicDAO.searchTopicsByTitle(req.getQuery());
			
			for(Topic course: courseSearchHits)
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
			
			resp.setRecordType("CHAPTER");
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
	public GetTopicResponse getTopic(GetTopicRequest req)
	{
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);		
		GetTopicResponse resp = new GetTopicResponse();
		try 
		{
			Topic chapter = topicDAO.getTopicById(req.getId());
			
			if (chapter == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				resp.setTopic(chapter);
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
	public CreateTopicResponse createNewTopic(CreateTopicRequest req)
	{
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);		
		CreateTopicResponse resp = new CreateTopicResponse();
		try 
		{
			//TODO validation
			
			Topic topic = topicDAO.insertTopic(req.getTopic());
			resp.setTopic(topic);
			resp.setStatus(StatusType.SUCCESS);
			resp.setMsg("Successfully added topic id = "+topic.getId());
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
	public UpdateTopicResponse updateTopic(UpdateTopicRequest req)
	{
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);		
		UpdateTopicResponse resp = new UpdateTopicResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = topicDAO.updateTopic(req.getTopic());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);				
				resp.setMsg("Successfully updated topic id = "+resp.getTopic().getId());
				resp.setTopic(req.getTopic());
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
	public DeleteTopicResponse deleteTopic(DeleteTopicRequest req)
	{
		TopicDAO topicDAO = applicationContext.getBean(TopicDAO.class,tenantId);		
		DeleteTopicResponse resp = new DeleteTopicResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = topicDAO.deleteTopic(req.getId());
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
