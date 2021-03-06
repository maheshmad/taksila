package com.taksila.veda.course.slides;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.taksila.veda.course.slides.Pptx2Image.Pptx2ImageOptions;
import com.taksila.veda.db.dao.SlidesDAO;
import com.taksila.veda.model.api.base.v1_0.BaseResponse;
import com.taksila.veda.model.api.base.v1_0.SearchHitRecord;
import com.taksila.veda.model.api.base.v1_0.StatusType;
import com.taksila.veda.model.api.course.v1_0.CreateSlideRequest;
import com.taksila.veda.model.api.course.v1_0.CreateSlideResponse;
import com.taksila.veda.model.api.course.v1_0.DeleteSlideRequest;
import com.taksila.veda.model.api.course.v1_0.DeleteSlideResponse;
import com.taksila.veda.model.api.course.v1_0.GetSlideRequest;
import com.taksila.veda.model.api.course.v1_0.GetSlideResponse;
import com.taksila.veda.model.api.course.v1_0.SearchSlidesRequest;
import com.taksila.veda.model.api.course.v1_0.SearchSlidesResponse;
import com.taksila.veda.model.api.course.v1_0.Slide;
import com.taksila.veda.model.api.course.v1_0.UpdateSlideRequest;
import com.taksila.veda.model.api.course.v1_0.UpdateSlideResponse;
import com.taksila.veda.utils.CommonUtils;



@Component
@Scope(value="prototype")
public class SlideComponent 
{	
	@Autowired
	ApplicationContext applicationContext;
		
	static Logger logger = LogManager.getLogger(SlideComponent.class.getName());	
	private String tenantId;
	
	@Autowired
	public SlideComponent(@Value("tenantId") String tenantId) 
	{
		logger.trace(">>>>>>>>>>>>>>>> Creating SlideComponent bean for tenant "+tenantId);		
		this.tenantId = tenantId;		
	}
	
	public BaseResponse generateImagesFromPptx(String topicid, String uploadedfileid)
	{    						
		logger.trace("inside pptx to image converter");
		Pptx2Image pptx2Image = applicationContext.getBean(Pptx2Image.class,this.tenantId);

		BaseResponse bResp = new BaseResponse(); 
		try 
		{
			Pptx2ImageOptions options = new Pptx2ImageOptions(); 
			options.filename = uploadedfileid;
			options.topicid = topicid;
			options.format= "png";
			options.scale = 1.5;
			pptx2Image.convertToImage(options);
			
			logger.trace("---------------generating thumbs ------------------");
			options.scale = 0.25;
			pptx2Image.convertToImage(options);
			
			bResp.setSuccess(true);
		} 
		catch (Exception e) 
		{		
			e.printStackTrace();
			CommonUtils.handleExceptionForResponse(bResp, e);
		}
		
		return bResp;
		
	}
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public SearchSlidesResponse searchSlide(SearchSlidesRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);

		SearchSlidesResponse resp = new SearchSlidesResponse();
		try 
		{
			List<Slide> courseSearchHits = slideDAO.searchSlidesByTitle(req.getQuery());
			
			for(Slide course: courseSearchHits)
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
			
			resp.setRecordType("SLIDE");
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
	public SearchSlidesResponse getSlidesByTopicId(SearchSlidesRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		SearchSlidesResponse resp = new SearchSlidesResponse();
		try 
		{
			List<Slide> courseSearchHits = new ArrayList<Slide>();
			
			if (req != null && req.getSearchParam() != null)
				courseSearchHits = slideDAO.searchSlidesByTopicId(req.getSearchParam().getTopicid());			
			
			for(Slide course: courseSearchHits)
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
			
			resp.setRecordType("SLIDE");
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
	public GetSlideResponse getSlide(GetSlideRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		GetSlideResponse resp = new GetSlideResponse();
		try 
		{
			Slide slide = slideDAO.getSlideById(req.getId());
			
			if (slide == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				resp.setSlide(slide);
			}
			
			logger.trace("++++++++  exiting getSlide component ");

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
	public GetSlideResponse getSlideByName(GetSlideRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		GetSlideResponse resp = new GetSlideResponse();
		try 
		{
			Slide slide = slideDAO.getSlideByName(req.getName());
			
			if (slide == null)
			{	
				resp.setMsg("Did not find any records with id = "+req.getId());
			}
			else
			{
				resp.setSlide(slide);
			}
			
			logger.trace("++++++++  exiting getSlide component ");

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
	public CreateSlideResponse createNewSlide(CreateSlideRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		CreateSlideResponse resp = new CreateSlideResponse();
		try 
		{
			//TODO validation
			
			Slide course = slideDAO.insertSlide(req.getSlide());
			resp.setSlide(course);
			resp.setSuccess(true);
			logger.trace("********  exiting slide component createNewSlide ");
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
	public UpdateSlideResponse updateSlide(UpdateSlideRequest req)
	{		
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		UpdateSlideResponse resp = new UpdateSlideResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = slideDAO.updateSlide(req.getSlide());
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);
				resp.setSlide(req.getSlide());
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
	public UpdateSlideResponse updateSlideImage(String slideId,InputStream slideContentImageIs, String imageType, double scale)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		logger.trace("++++++++  ABOUT to insert image into Slides id= "+slideId);
		UpdateSlideResponse resp = new UpdateSlideResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = slideDAO.updateSlideImage(slideId, slideContentImageIs, imageType,scale);
			if (updateSucceded)
			{
				resp.setStatus(StatusType.SUCCESS);	
				resp.setSuccess(true);
			}
			else
			{
				resp.setSuccess(false);
				resp.setStatus(StatusType.FAILED);
				resp.setErrorInfo(CommonUtils.buildErrorInfo("FAILED", "Image content was not updated! Please check your data"));				
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
	 * @throws Exception 
	 */
	public ByteArrayOutputStream getSlideImage(int slideId,double scale) throws Exception
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		logger.trace("++++++++  ABOUT to getSlideImage image = "+slideId);				
			//TODO validation			
		return slideDAO.readSlideImage(slideId, scale);					
		
	}
	
	
	/**
	 * 
	 * @param req
	 * @return
	 */
	public DeleteSlideResponse deleteSlide(DeleteSlideRequest req)
	{
		SlidesDAO slideDAO = applicationContext.getBean(SlidesDAO.class,tenantId);
		DeleteSlideResponse resp = new DeleteSlideResponse();
		try 
		{
			//TODO validation
			
			boolean updateSucceded = slideDAO.deleteSlide(req.getId());
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
