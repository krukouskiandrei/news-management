package com.epam.newsmanagement.common.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Represents User Model corresponding to table in dao model
 */
public class Tag implements Serializable {

    private static final long serialVersionUID = 3298243987234987249L;

    private Long idTag;
    @NotEmpty
    private String tagName;

    public Tag(){}

    public Long getIdTag(){
        return idTag;
    }
    public void setIdTag(Long idTag){
        this.idTag = idTag;
    }
    public String getTagName(){
        return tagName;
    }
    public void setTagName(String tagName){
        this.tagName = tagName;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        Tag tag = (Tag) object;
        if(idTag != null ? !idTag.equals(tag.getIdTag()) : tag.getIdTag() != null){
            return false;
        }
        if(tagName != null ? !tagName.equals(tag.getTagName()) : tag.getTagName() != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = idTag != null ? idTag.hashCode() : 0;
        hash = 41 * hash + (tagName != null ? tagName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "Tag [idTag=" + idTag + ", tagName=" + tagName + "]";
    }

}