package com.epam.newsmanagement.common.service.impl;

import com.epam.newsmanagement.common.dao.AuthorDAO;
import com.epam.newsmanagement.common.dao.CommentDAO;
import com.epam.newsmanagement.common.dao.NewsDAO;
import com.epam.newsmanagement.common.dao.TagDAO;
import com.epam.newsmanagement.common.entity.Author;
import com.epam.newsmanagement.common.entity.Comment;
import com.epam.newsmanagement.common.entity.News;
import com.epam.newsmanagement.common.entity.NewsInfo;
import com.epam.newsmanagement.common.entity.SearchParameter;
import com.epam.newsmanagement.common.entity.Tag;
import com.epam.newsmanagement.common.exception.dao.DAOException;
import com.epam.newsmanagement.common.exception.service.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testing {@link AuthorServiceImpl}
 */
public class NewsServiceImplTest {

    @Mock
    private NewsDAO newsDAO;
    @Mock
    private AuthorDAO authorDAO;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private CommentDAO commentDAO;
    @InjectMocks
    private NewsServiceImpl newsService;

    /**
     * initializing mock elements
     */
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /**
     * testing method crete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCreate() throws ServiceException, DAOException {
        News news = new News();
        newsService.create(news);
        verify(newsDAO).create(news);
    }

    /**
     * testing method getById()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetById() throws ServiceException, DAOException{
        News newsTo = new News();
        Long newsId = new Long(1);
        when(newsDAO.getById(newsId)).thenReturn(newsTo);
        News newsFrom = newsService.getById(newsId);
        Assert.assertEquals(newsFrom, newsTo);
        verify(newsDAO).getById(newsId);
    }

    /**
     * testing method getAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAll() throws ServiceException, DAOException{
        News news1 = new News();
        News news2 = new News();
        news1.setIdNews(new Long(1));
        news2.setIdNews(new Long(2));
        List<News> newsListTo = new ArrayList<>();
        newsListTo.add(news1);
        newsListTo.add(news2);
        when(newsDAO.getAll()).thenReturn(newsListTo);
        List<News> newsListFrom = newsService.getAll();
        Assert.assertEquals(newsListTo, newsListFrom);
        verify(newsDAO).getAll();
    }

    /**
     * testing method countAll()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testCountAll() throws ServiceException, DAOException{
        Long countTo = new Long(2);
        when(newsDAO.countAll()).thenReturn(countTo);
        Long countFrom = newsService.countAll();
        Assert.assertEquals(countFrom, countTo);
        verify(newsDAO).countAll();
    }

    /**
     * testing method update()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testUpdate() throws ServiceException, DAOException{
        News news = new News();
        newsService.update(news);
        verify(newsDAO).update(news);
    }

    /**
     * testing method delete()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testDelete() throws ServiceException, DAOException{
        Long newsId = new Long(1);
        newsService.delete(newsId);
        verify(newsDAO).delete(newsId);
    }
    
    /**
     * testing method getAllNewsWithInfo()
     * @throws ServiceException
     * @throws DAOException
     */
    @Test
    public void testGetAllNewsWithInfo() throws ServiceException, DAOException{
    	List<NewsInfo> listNewsInfo = new ArrayList<>();
    	Long id1 = new Long(1);
    	Long id2 = new Long(2);
    	NewsInfo newsInfo1 = new NewsInfo();
    	NewsInfo newsInfo2 = new NewsInfo();
    	News news1 = new News();
    	news1.setIdNews(id1);
    	News news2 = new News();
    	news2.setIdNews(id2);
    	newsInfo1.setNews(news1);
    	newsInfo2.setNews(news2);
    	List<News> newsList = new ArrayList<>();
    	newsList.add(news1);
    	newsList.add(news2);
    	when(newsDAO.getAll()).thenReturn(newsList);
    	Author author1 = new Author();
    	author1.setIdAuthor(id1);
    	Author author2 = new Author();
    	author2.setIdAuthor(id2);
    	newsInfo1.setAuthor(author1);
    	newsInfo2.setAuthor(author2);
    	when(authorDAO.getAuthorForNews(id1)).thenReturn(author1);
    	when(authorDAO.getAuthorForNews(id2)).thenReturn(author2);
    	Tag tag1 = new Tag();
    	tag1.setIdTag(id1);
    	Tag tag2 = new Tag();
    	tag2.setIdTag(id2);
    	List<Tag> listTag1 = new ArrayList<>();
    	listTag1.add(tag1);
    	List<Tag> listTag2 = new ArrayList<>();
    	listTag2.add(tag2);
    	newsInfo1.setTags(listTag1);
    	newsInfo2.setTags(listTag2);
    	when(tagDAO.getAllTagsForNews(id1)).thenReturn(listTag1);
    	when(tagDAO.getAllTagsForNews(id2)).thenReturn(listTag2);
    	Comment comment1 = new Comment();
    	comment1.setIdComment(id1);
    	Comment comment2 = new Comment();
    	comment2.setIdComment(id2);
    	List<Comment> listComment1 = new ArrayList<>();
    	listComment1.add(comment1);
    	List<Comment> listComment2 = new ArrayList<>();
    	listComment2.add(comment2);
    	newsInfo1.setComments(listComment1);
    	newsInfo2.setComments(listComment2);
    	when(commentDAO.getCommentList(id1)).thenReturn(listComment1);
    	when(commentDAO.getCommentList(id2)).thenReturn(listComment2);
    	listNewsInfo.add(newsInfo1);
    	listNewsInfo.add(newsInfo2);
    	List<NewsInfo> resultListNewsInfo = newsService.getAllNewsWithInfo();
    	Assert.assertEquals(listNewsInfo, resultListNewsInfo);
    	verify(commentDAO).getCommentList(id1);
    	verify(commentDAO).getCommentList(id2);
    	verify(tagDAO).getAllTagsForNews(id1);
    	verify(tagDAO).getAllTagsForNews(id2);
    	verify(authorDAO).getAuthorForNews(id1);
    	verify(authorDAO).getAuthorForNews(id2);
    	verify(newsDAO).getAll();
    }
    /**
     * testing method paginationNews(int, int)
     * @throws DAOException
     * @throws ServiceException
     */
    @Test
    public void testPaginationnNews() throws DAOException, ServiceException{
    	List<NewsInfo> listNewsInfo = new ArrayList<>();
    	Long id1 = new Long(1);
    	Long id2 = new Long(2);
    	int from = 1; int to = 5;
    	NewsInfo newsInfo1 = new NewsInfo();
    	NewsInfo newsInfo2 = new NewsInfo();
    	News news1 = new News();
    	news1.setIdNews(id1);
    	News news2 = new News();
    	news2.setIdNews(id2);
    	newsInfo1.setNews(news1);
    	newsInfo2.setNews(news2);
    	List<News> newsList = new ArrayList<>();
    	newsList.add(news1);
    	newsList.add(news2);
    	when(newsDAO.paginationNews(from, to)).thenReturn(newsList);
    	Author author1 = new Author();
    	author1.setIdAuthor(id1);
    	Author author2 = new Author();
    	author2.setIdAuthor(id2);
    	newsInfo1.setAuthor(author1);
    	newsInfo2.setAuthor(author2);
    	when(authorDAO.getAuthorForNews(id1)).thenReturn(author1);
    	when(authorDAO.getAuthorForNews(id2)).thenReturn(author2);
    	Tag tag1 = new Tag();
    	tag1.setIdTag(id1);
    	Tag tag2 = new Tag();
    	tag2.setIdTag(id2);
    	List<Tag> listTag1 = new ArrayList<>();
    	listTag1.add(tag1);
    	List<Tag> listTag2 = new ArrayList<>();
    	listTag2.add(tag2);
    	newsInfo1.setTags(listTag1);
    	newsInfo2.setTags(listTag2);
    	when(tagDAO.getAllTagsForNews(id1)).thenReturn(listTag1);
    	when(tagDAO.getAllTagsForNews(id2)).thenReturn(listTag2);
    	Comment comment1 = new Comment();
    	comment1.setIdComment(id1);
    	Comment comment2 = new Comment();
    	comment2.setIdComment(id2);
    	List<Comment> listComment1 = new ArrayList<>();
    	listComment1.add(comment1);
    	List<Comment> listComment2 = new ArrayList<>();
    	listComment2.add(comment2);
    	newsInfo1.setComments(listComment1);
    	newsInfo2.setComments(listComment2);
    	when(commentDAO.getCommentList(id1)).thenReturn(listComment1);
    	when(commentDAO.getCommentList(id2)).thenReturn(listComment2);
    	listNewsInfo.add(newsInfo1);
    	listNewsInfo.add(newsInfo2);
    	List<NewsInfo> resultListNewsInfo = newsService.paginationNews(from, to);
    	Assert.assertEquals(listNewsInfo, resultListNewsInfo);
    	verify(commentDAO).getCommentList(id1);
    	verify(commentDAO).getCommentList(id2);
    	verify(tagDAO).getAllTagsForNews(id1);
    	verify(tagDAO).getAllTagsForNews(id2);
    	verify(authorDAO).getAuthorForNews(id1);
    	verify(authorDAO).getAuthorForNews(id2);
    	verify(newsDAO).paginationNews(from, to);
    }
    
    @Test
    public void testSearchNews() throws DAOException, ServiceException{
    	Long idNews = new Long(1);
    	Long idAuthor = new Long(1);
    	Long idTag = new Long(1);
    	Long idComment = new Long(1);
    	News news = new News();
    	news.setIdNews(idNews);
    	Author author = new Author();
    	author.setIdAuthor(idAuthor);
    	Tag tag = new Tag();
    	tag.setIdTag(idTag);
    	Comment comment = new Comment();
    	comment.setIdComment(idComment);
    	List<Tag> tagList = new ArrayList<>();
    	tagList.add(tag);
    	List<Comment> commentList = new ArrayList<>();
    	commentList.add(comment);
    	List<News> newsList = new ArrayList<>();
    	newsList.add(news);
    	NewsInfo newsInfo = new NewsInfo();
    	newsInfo.setAuthor(author);
    	newsInfo.setNews(news);
    	newsInfo.setTags(tagList);
    	newsInfo.setComments(commentList);
    	List<NewsInfo> newsInfoList = new ArrayList<>();
    	newsInfoList.add(newsInfo);
    	when(newsDAO.getNewsByAuthorAndTag(idTag, idAuthor)).thenReturn(newsList);
    	when(newsDAO.getNewsByAuthor(idAuthor)).thenReturn(newsList);
    	when(newsDAO.getNewsByTag(idTag)).thenReturn(newsList);
    	when(authorDAO.getAuthorForNews(idNews)).thenReturn(author);
    	when(tagDAO.getAllTagsForNews(idNews)).thenReturn(tagList);
    	when(commentDAO.getCommentList(idNews)).thenReturn(commentList);
    	SearchParameter searchParameter = new SearchParameter();
    	searchParameter.setAuthor(author);
    	searchParameter.setTagList(tagList);
    	List<NewsInfo> resultNewsInfoList = newsService.searchNews(searchParameter);
    	Assert.assertEquals(newsInfoList, resultNewsInfoList);
    }
}