package com.epam.newsmanagement.common.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Class contains information about news
 */
public class NewsInfo implements Serializable {

    private static final long serialVersionUID = 8382927492947593853L;

    @Valid
    private News news;
    @NotNull
    private Author author;
    @NotNull
    private List<Tag> tags;
    private List<Comment> comments;

    public NewsInfo(){}

    public News getNews(){
        return news;
    }
    public void setNews(News news){
        this.news = news;
    }
    public Author getAuthor(){
        return author;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    public List<Tag> getTags(){
        return tags;
    }
    public void setTags(List<Tag> tags){
        this.tags = tags;
    }
    public List<Comment> getComments(){
        return comments;
    }
    public void setComments(List<Comment> comments){
        this.comments = comments;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        NewsInfo newsInfo = (NewsInfo) object;
        if(news != null ? !news.equals(newsInfo.getNews()) : newsInfo.getNews() != null){
            return false;
        }
        if (author != null ? !author.equals(newsInfo.getAuthor()) : newsInfo.getAuthor() != null){
            return false;
        }
        if(tags != null ? !tags.equals(newsInfo.getTags()) : newsInfo.getTags() != null){
            return false;
        }
        if(comments != null ? !comments.equals(newsInfo.getComments()) : newsInfo.getComments() != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = news != null ? news.hashCode() : 0;
        hash = hash * 41 + (author != null ? author.hashCode() : 0);
        hash = hash * 41 + (tags != null ? tags.hashCode() : 0);
        hash = hash * 41 + (comments != null ? comments.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "NewsInfo [news=" + news.toString() + ", author=" + author.toString() + ", " +
                "tags=" + tags.toString() + ", comments=" + comments.toString() + "]";
    }

}