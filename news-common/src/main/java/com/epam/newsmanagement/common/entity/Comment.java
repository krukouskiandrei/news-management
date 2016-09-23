package com.epam.newsmanagement.common.entity;


import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.validation.constraints.Size;

/**
 * Represents Comment Model corresponding to table in dao model
 */
public class Comment implements Serializable {

    private static final long serialVersionUID = 2847395837493847383L;
    private static final SimpleDateFormat dayMonthYearFormatter = new SimpleDateFormat("MM/dd/yyyy");

    private Long idComment;
    private Long idNews;
    @NotEmpty @Size(min=2, max=300)
    private String commentText;
    private Timestamp creationDate;

    public Comment(){}
    public Long getIdComment(){
        return idComment;
    }
    public void setIdComment(Long idComment){
        this.idComment = idComment;
    }
    public Long getIdNews(){
        return idNews;
    }
    public void setIdNews(Long idNews){
        this.idNews = idNews;
    }
    public String getCommentText(){
        return commentText;
    }
    public void setCommentText(String commentText){
        this.commentText = commentText;
    }
    public Timestamp getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate){
        this.creationDate = creationDate;
    }
    public String getDate(){
    	String date = null;
    	if(creationDate == null){
    		return date;
    	}else{
    		return dayMonthYearFormatter.format((java.util.Date) creationDate);
    	}
    }
    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        Comment comment = (Comment) object;
        if(idComment != null ? !idComment.equals(comment.getIdComment()) : comment.getIdComment() != null){
            return false;
        }
        if(idNews != null ? !idNews.equals(comment.getIdNews()) : comment.getIdNews() != null){
            return false;
        }
        if(commentText != null ? !commentText.equals(comment.getCommentText()) : comment.getCommentText() != null){
            return false;
        }
        if (creationDate != null ? !creationDate.equals(comment.getCreationDate()) : comment.getCreationDate() != null){
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        int hash = idComment != null ? idComment.hashCode() : 0;
        hash = 41 * hash + (idNews != null ? idNews.hashCode() : 0);
        hash = 41 * hash + (commentText != null ? commentText.hashCode() : 0);
        hash = 41 * hash + (creationDate != null ? creationDate.hashCode() : 0);
        return hash;
    }
    @Override
    public String toString(){
        return "Comment [idComment=" + idComment + ", idNews=" + idNews + ", commentText=" + commentText + ", " +
                "creationDate=" + creationDate + "]";
    }

}