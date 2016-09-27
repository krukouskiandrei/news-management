package com.epam.newsmanagement.admin.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import com.epam.newsmanagement.common.service.AuthorService;
import com.epam.newsmanagement.common.service.CommentService;
import com.epam.newsmanagement.common.service.NewsService;
import com.epam.newsmanagement.common.service.TagService;

/**
 * controller for working with news
 * @author Andrei_Krukouski
 *
 */

@Controller
@SessionAttributes({"searchParameter", "listNewsInfo", "pageNum", "newsInfo", "numNews"})
public class NewsController {

	private final static Logger logger = LogManager.getLogger(NewsController.class);
	
	@Autowired
	NewsService newsService;
	@Autowired
	AuthorService authorService;
	@Autowired
	TagService tagService;
	@Autowired
	CommentService commentService;
	/**
	 * Main page, showing all news by page
	 * @param model
	 * @return
	 */
    @RequestMapping(value = "news", method = {RequestMethod.GET, RequestMethod.POST})
    public String getAllNews(Model model){
    	List<NewsInfo> listNewsInfo = null;
    	int numberNews = 3;
    	try{
    		listNewsInfo = newsService.paginationNews(1, numberNews);
    	}catch(ServiceException e){
    		logger.error("error from pagination news", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	
    	model.addAttribute("searchParameter", new SearchParameter());
    	model.addAttribute("pageNum", 1);
    	model.addAttribute("listNewsInfo", listNewsInfo);
    	try{
    		setAttributeInModel(model);
    	}catch(ServiceException e){
    		logger.error("error from seting attribute in model", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	return "news";
    }
    /**
     * going by page
     * @param numPage
     * @param model
     * @return
     */
    @RequestMapping(value = "/news/page/{numPage}", method = RequestMethod.GET)
    public String redirectOnPage(@PathVariable int numPage, Model model){
    	List<NewsInfo> listNewsInfo = null;
    	int numberNews = 3;//number news on a page
    	try{
    		listNewsInfo = newsService.paginationNews(numPage == 1 ? 1 :
    			(numPage-1) * numberNews + 1, numPage == 1 ? 
    					numberNews : (numPage-1) * numberNews + numberNews);
    	}catch(ServiceException e){
    		logger.error("error from pagination news", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	model.addAttribute("pageNum", numPage);
    	model.addAttribute("listNewsInfo", listNewsInfo);
    	try{
    		setAttributeInModel(model);
    	}catch(ServiceException e){
    		logger.error("error from seting attribute in model", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	return "news";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
	public String mainPage(Model model) {
		return "redirect:/login";
	}
    /**
     * redirect on addnews.jsp
     * @return
     */
    @RequestMapping(value = "addnews", method = RequestMethod.GET)
    public String getAddNewsPage(Model model){
    	model.addAttribute(new NewsInfo());
    	try{
    		setAttributeInModel(model);
    	}catch(ServiceException e){
    		logger.error("error from seting attribute in model");
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	Date date = new Date();
    	model.addAttribute("currentDate", dateFormat.format(date));
    	return "addnews";
    }
    /**
     * deleting news by newsId
     * @param deleteNewsId contains newsId which need to delete
     * @param model
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public String deleteNews(@RequestParam Long[] deleteNewsId, Model model){
    	for(int i = 0; i < deleteNewsId.length; i++){
    		try{
    			newsService.delete(deleteNewsId[i]);
    		}catch(ServiceException e){
    			logger.error("error with deleting news where id=" + deleteNewsId[i], e);
    			model.addAttribute(e);
    		}
    	}
    	return getAllNews(model);
    }
    /**
     * handler exception when don't choose news which need to delete, but press button delete
     * @param e
     * @param model
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String missingParameterHandler(Exception e, Model model){
    	model.addAttribute("errorTitle", e);
    	return "error";
    }
    /**
     * filtering news 
     * @param searchParameter contains author and list tag by which need to filtering
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "filternews", method = RequestMethod.POST)
    public String filteredNews(@ModelAttribute("searchParameter")SearchParameter searchParameter, 
    		BindingResult result, Model model){
    	if(result.hasErrors()){
    		return "error";
    	}
    	List<NewsInfo> listNewsInfo = null;
    	try{
    		listNewsInfo = newsService.searchNews(searchParameter);
    	}catch(ServiceException e){
    		logger.error("error from searching news", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	try{
    		setAttributeInModel(model);
    	}catch(ServiceException e){
    		logger.error("error from seting attribute in model", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    		model.addAttribute("listNewsInfo", listNewsInfo);
    	return "news";
    }
    
    /**
     * create news 
     * @param newsInfo
     * @param result
     * @param model
     * @return
     */
    @RequestMapping(value = "createNews", method = RequestMethod.POST)
    public String createNews(@Valid NewsInfo newsInfo, 
    		BindingResult result, Model model){
    	if(result.hasErrors()){
    		logger.info("Returning addnews.jsp page");
    		try{
        		setAttributeInModel(model);
        	}catch(ServiceException e){
        		logger.error("error from seting attribute in model");
        		model.addAttribute("errorTitle", e);
        		return "error";
        	}
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        	Date date = new Date();
        	model.addAttribute("currentDate", dateFormat.format(date));
    		return "addnews";
    	}
    	Date currentDate = new Date(); 
    	newsInfo.getNews().setModificationDate(new java.sql.Date(currentDate.getTime()));
    	newsInfo.getNews().setCreationDate(new Timestamp(currentDate.getTime()));
    	try{
    		newsService.createFullNews(newsInfo);    		
    	}catch(ServiceException e){
    		logger.error("Failed to create newsInfo=" + newsInfo + ";", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	return "creatednews";
    }
    /**
     * setting list authors, list tags, list tags in Model
     * @param model
     * @return
     * @throws ServiceException
     */
    private Model setAttributeInModel(Model model) throws ServiceException{
    	List<Author> listAuthor = null;
    	List<Tag> listTag = null;
    	Long countNews = null;
    	listAuthor = authorService.getAll();
    	listTag = tagService.getAll();
    	countNews = newsService.countAll();
    	model.addAttribute("countNews", countNews);
    	model.addAttribute("listAuthors", listAuthor);
    	model.addAttribute("listTags", listTag);
        
    	return model;
    }
    /**
     * show news by news id
     * @param idNews
     * @param model
     * @return
     */
    @RequestMapping(value = "/news/{idNews}", method = RequestMethod.GET)
    public String showNews(@PathVariable long idNews, @ModelAttribute("pageNum")int pageNum, Model model){
    	NewsInfo newsInfo = null;
    	try{
    		newsInfo = newsService.getFullNews(idNews);
    	}catch(ServiceException e){
    		logger.error("Failed to get news info by newsId=" + idNews + ";", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	int numberNews = 3;
    	model.addAttribute("newsInfo", newsInfo);
    	model.addAttribute(new Comment());
    	model.addAttribute("numNews", pageNum*numberNews);
    	return "shownews";
    }
    
    /**
     * delete comment for news by comment id
     * @param idNews
     * @param idComment
     * @param model
     * @return
     */
    @RequestMapping(value = "/news/{idNews}/{idComment}", method = RequestMethod.GET)
    public String deleteComment(@PathVariable long idNews, @ModelAttribute("pageNum")int pageNum, @PathVariable long idComment, Model model){
    	
    	try{
    		commentService.delete(idComment);
    	}catch(ServiceException e){
    		logger.error("Failed to delete comment by commentId=" + idComment + ";", e);   		
    	}
    	return showNews(idNews, pageNum, model);
    }
    
    @RequestMapping(value = "{idNews}/createcomment", method = RequestMethod.POST)
    public String createComment(@PathVariable long idNews, @ModelAttribute("pageNum")int pageNum,
    		@Valid Comment comment, 
    		BindingResult result, Model model){
    	if(result.hasErrors()){
    		logger.info("Returning shownews.jsp page");
    		
    		return showNews(idNews, pageNum, model);
    	}
    	
    	comment.setIdNews(idNews);
    	Date currentDate = new Date();
    	comment.setCreationDate(new Timestamp(currentDate.getTime()));
    	try{
    		commentService.create(comment);
    	}catch(ServiceException e){
    		logger.error("Failed to create comment=" + comment + ";", e);
    	}
    	return showNews(idNews, pageNum, model);
    }
    /**
     * show next news
     * @param listNewsInfo
     * @param numNews
     * @param currentNewsInfo
     * @param model
     * @return
     */
    @RequestMapping(value="news/next", method = RequestMethod.GET)
    public String nextNews(@ModelAttribute("listNewsInfo")List<NewsInfo> listNewsInfo, 
    		@ModelAttribute("pageNum")int numPage, @ModelAttribute("newsInfo")NewsInfo currentNewsInfo, Model model){
    	NewsInfo resultNewsInfo = null;
    	Iterator<NewsInfo> newsInfoIterator = listNewsInfo.iterator();
    	while(newsInfoIterator.hasNext()){
    		NewsInfo news = newsInfoIterator.next();
    		if(news.getNews().getIdNews().equals(currentNewsInfo.getNews().getIdNews()) && newsInfoIterator.hasNext()){
    			resultNewsInfo = newsInfoIterator.next();
    			break;
    		}
    	}
    	if(resultNewsInfo == null){
    		List<NewsInfo> newListNewsInfo = null;
    		int numberNews = 3;
    		try{
    			numPage++;
    			newListNewsInfo = newsService.paginationNews(numPage == 1 ? 1 : (numPage-1) * numberNews + 1,
    					numPage == 1 ? numberNews : (numPage-1) * numberNews + numberNews);
    		}catch(ServiceException e){
    			logger.error("error from pagination news", e);
        		model.addAttribute("errorTitle", e);
        		return "error";
    		}
    		if(newListNewsInfo != null){
    			if(!newListNewsInfo.isEmpty()){
    				resultNewsInfo = newListNewsInfo.get(0);
    				model.addAttribute("pageNum", numPage);
    		    	model.addAttribute("listNewsInfo", newListNewsInfo);
    			}else {
					resultNewsInfo = currentNewsInfo;
				}
    		}else{
    			resultNewsInfo = currentNewsInfo;
    		}
    	}
    	model.addAttribute("newsInfo", resultNewsInfo);
    	model.addAttribute(new Comment());    	
    	
    	
    	/*List<NewsInfo> listNewsInfo = null;
    	int numberNews = 3;//number news on a page
    	try{
    		listNewsInfo = newsService.paginationNews(numPage == 1 ? 1 :
    			(numPage-1) * numberNews + 1, numPage == 1 ? 
    					numberNews : (numPage-1) * numberNews + numberNews);
    	}catch(ServiceException e){
    		logger.error("error from pagination news", e);
    		model.addAttribute("errorTitle", e);
    		return "error";
    	}
    	model.addAttribute("pageNum", numPage);
    	model.addAttribute("listNewsInfo", listNewsInfo);*/
    	
    	/*Iterator<NewsInfo> newsInfoIterator = listNewsInfo.iterator();
    	NewsInfo resultNewsInfo = null;
    	boolean checkContainsCurrentNewsInfoInListNewsInfo = false;//for pagination news
    	while(newsInfoIterator.hasNext()){
    		NewsInfo news = newsInfoIterator.next();
    		if(news.getNews().getIdNews().equals(currentNewsInfo.getNews().getIdNews())){
    			if(newsInfoIterator.hasNext()){
    				resultNewsInfo = newsInfoIterator.next();
    				break;
    			}
    			checkContainsCurrentNewsInfoInListNewsInfo = true;
    		}
    		
    	}
    	if(resultNewsInfo == null){
    		List<NewsInfo> localListNewsInfo = null;
        	int numberNews = 3;
        	try{
        		if(checkContainsCurrentNewsInfoInListNewsInfo){
        			localListNewsInfo = newsService.paginationNews(numNews+1, numNews+1);
        			if(localListNewsInfo != null){
        				if(!localListNewsInfo.isEmpty()){
        					model.addAttribute("numNews", numNews+1);
        				}
        			}
        		}else{
        			localListNewsInfo = newsService.paginationNews(numNews+1, numNews+1);
        			if(localListNewsInfo != null){
        				if(!localListNewsInfo.isEmpty()){
        					model.addAttribute("numNews", numNews+1);
        				}
        			}
        		}
        	}catch(ServiceException e){
        		logger.error("error from pagination news", e);
        		model.addAttribute("errorTitle", e);
        		return "error";
        	}
        	if(!localListNewsInfo.isEmpty()){
        		resultNewsInfo = localListNewsInfo.get(0);
        	}else{
        		resultNewsInfo = currentNewsInfo;
        	}
    	}
    	model.addAttribute("newsInfo", resultNewsInfo);
    	model.addAttribute(new Comment());*/
    	return "shownews";
    }
    
    @RequestMapping(value="news/previous", method = RequestMethod.GET)
    public String previousNews(@ModelAttribute("listNewsInfo")List<NewsInfo> listNewsInfo, 
    		@ModelAttribute("pageNum")int numPage, @ModelAttribute("newsInfo")NewsInfo currentNewsInfo, Model model){
    	
    	NewsInfo resultNewsInfo = null;
    	Collections.reverse(listNewsInfo);
    	Iterator<NewsInfo> newsInfoIterator = listNewsInfo.iterator();
    	while(newsInfoIterator.hasNext()){
    		NewsInfo news = newsInfoIterator.next();
    		if(news.getNews().getIdNews().equals(currentNewsInfo.getNews().getIdNews()) && newsInfoIterator.hasNext()){
    			resultNewsInfo = newsInfoIterator.next();
    			break;
    		}
    	}
    	Collections.reverse(listNewsInfo);
    	if(resultNewsInfo == null){
    		List<NewsInfo> newListNewsInfo = null;
    		int numberNews = 3;
    		try{
    			numPage--;
    			newListNewsInfo = newsService.paginationNews(numPage == 1 ? 1 : (numPage-1) * numberNews + 1,
    					numPage == 1 ? numberNews : (numPage-1) * numberNews + numberNews);
    		}catch(ServiceException e){
    			logger.error("error from pagination news", e);
        		model.addAttribute("errorTitle", e);
        		return "error";
    		}
    		if(newListNewsInfo != null){
    			if(!newListNewsInfo.isEmpty()){
    				Collections.reverse(newListNewsInfo);
    				resultNewsInfo = newListNewsInfo.get(0);
    				Collections.reverse(newListNewsInfo);
    				model.addAttribute("pageNum", numPage);
    		    	model.addAttribute("listNewsInfo", newListNewsInfo);
    			}else {
					resultNewsInfo = currentNewsInfo;
				}
    		}else{
    			resultNewsInfo = currentNewsInfo;
    		}
    	}
    	model.addAttribute("newsInfo", resultNewsInfo);
    	model.addAttribute(new Comment());    	
    	   	
    	/*Collections.reverse(listNewsInfo);
    	Iterator<NewsInfo> newsInfoIterator = listNewsInfo.iterator();
    	NewsInfo resultNewsInfo = null;
    	boolean checkContainsCurrentNewsInfoInListNewsInfo = false;//for pagination news
    	while(newsInfoIterator.hasNext()){
    		NewsInfo news = newsInfoIterator.next();
    		if(news.getNews().getIdNews().equals(currentNewsInfo.getNews().getIdNews())){
    			if(newsInfoIterator.hasNext()){
    				resultNewsInfo = newsInfoIterator.next();
    				break;
    			}
    			checkContainsCurrentNewsInfoInListNewsInfo = true;
    		}
    		
    	}
    	Collections.reverse(listNewsInfo);
    	if(resultNewsInfo == null){
    		List<NewsInfo> localListNewsInfo = null;
        	int numberNews = 3;
        	try{
        		if(checkContainsCurrentNewsInfoInListNewsInfo){
        			localListNewsInfo = newsService.paginationNews(numNews-numberNews, numNews-numberNews);
        			if(localListNewsInfo != null){
        				if(!localListNewsInfo.isEmpty()){
        					model.addAttribute("numNews", numNews-numberNews);
        				}
        			}
        		}else{
        			localListNewsInfo = newsService.paginationNews(numNews-1, numNews-1);
        			if(localListNewsInfo != null){
        				if(!localListNewsInfo.isEmpty()){
        					model.addAttribute("numNews", numNews-1);
        				}
        			}
        		}
        	}catch(ServiceException e){
        		logger.error("error from pagination news", e);
        		model.addAttribute("errorTitle", e);
        		return "error";
        	}
        	if(localListNewsInfo != null){
        		if(!localListNewsInfo.isEmpty()){
        			resultNewsInfo = localListNewsInfo.get(0);
        		}else{
        			resultNewsInfo = currentNewsInfo;
        		}
        	}else{
    			resultNewsInfo = currentNewsInfo;
    		}
    	}
    	model.addAttribute("newsInfo", resultNewsInfo);
    	model.addAttribute(new Comment());*/
    	return "shownews";
    }

}