package com.epam.newsmanagement.common.entity;

import java.io.Serializable;
import java.util.List;

/**
 * This is object that contain parameters for news searching
 * you can search news by author and tags
 */
public class SearchParameter implements Serializable {

    private static final Long serialVersionUID = 2398423489723432423L;

    private Author author;
    private List<Tag> tagList;

    public SearchParameter(){}

    public Author getAuthor(){
        return author;
    }
    public void setAuthor(Author author){
        this.author = author;
    }
    public List<Tag> getTagList(){
        return tagList;
    }
    public void setTagList(List<Tag> tagList){
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        SearchParameter searchParameter = (SearchParameter) object;
        if(author != null ? !author.equals(searchParameter.getAuthor()) : searchParameter.getAuthor() != null){
            return false;
        }
        if (tagList != null ? !tagList.equals(searchParameter.getTagList()) : searchParameter.getTagList() != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = author != null ? author.hashCode() : 0;
        hash = 41 * hash + (tagList != null ? tagList.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "SearchParameter [author=" + author.toString() + ", tagList=" + tagList + "]";
    }

}
