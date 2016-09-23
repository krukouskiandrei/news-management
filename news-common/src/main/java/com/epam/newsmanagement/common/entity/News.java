package com.epam.newsmanagement.common.entity;



import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Represents News Model corresponding to table in dao model
 */
public class News implements Serializable {

    private static final long serialVersionUID = 7473837282737294822L;
    private static final SimpleDateFormat dayMonthYearFormatter = new SimpleDateFormat("MM/dd/yyyy");
    
    private Long idNews;
    
    @NotEmpty @Size(min=2, max=30)
    private String title;
    
    @NotEmpty @Size(min=10, max=100)
    private String shortText;
    
    @NotEmpty @Size(min=30, max=2000)
    private String fullText;
    
    private Timestamp creationDate;
    private Date modificationDate;

    public News(){}

    public Long getIdNews(){
        return idNews;
    }
    public void setIdNews(Long idNews){
        this.idNews = idNews;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getShortText(){
        return shortText;
    }
    public void setShortText(String shortText){
        this.shortText = shortText;
    }
    public String getFullText(){
        return fullText;
    }
    public void setFullText(String fullText){
        this.fullText = fullText;
    }
    public Timestamp getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Timestamp creationDate){
        this.creationDate = creationDate;
    }
    public Date getModificationDate(){
        return modificationDate;
    }
    public void setModificationDate(Date modificationDate){
        this.modificationDate = modificationDate;
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

        News news = (News) object;
        if(idNews != null ? !idNews.equals(news.getIdNews()) : news.getIdNews() != null){
            return false;
        }
        if(title != null ? !title.equals(news.getTitle()) : news.getTitle() != null){
            return false;
        }
        if(shortText != null ? !shortText.equals(news.getShortText()) : news.getShortText() != null){
            return false;
        }
        if (fullText != null ? !fullText.equals(news.getFullText()) : news.getFullText() != null){
            return false;
        }
        if(creationDate != null ? !creationDate.equals(news.getCreationDate()) : news.getCreationDate() != null){
            return false;
        }
        if (modificationDate != null ? !modificationDate.equals(news.getModificationDate())
                : news.getModificationDate() != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = idNews != null ? idNews.hashCode() : 0;
        hash = 41 * hash + (title != null ? title.hashCode() : 0);
        hash = 41 * hash + (shortText != null ? shortText.hashCode() : 0);
        hash = 41 * hash + (fullText != null ? fullText.hashCode() : 0);
        hash = 41 * hash + (creationDate != null ? creationDate.hashCode() : 0);
        hash = 41 * hash + (modificationDate != null ? modificationDate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "News [idNews=" + idNews + ", title=" + title + ", " +
                "shortText=" + shortText + ", fullText=" + fullText + ", " +
                "creationDate=" + creationDate + ", modificationDate=" + modificationDate + "]";
    }




}