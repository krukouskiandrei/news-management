package com.epam.newsmanagement.common.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Represents Author Model corresponding to table in dao model
 */
public class Author implements Serializable {

<<<<<<< HEAD
    private static final long serialVersionUID = 3244234324253424254L;
=======
    private static final Long serialVersionUID = 3244234324253424254L;
>>>>>>> 10563c064db22e544a6b8280f9d38a6262005b53

    private Long idAuthor;
    @NotBlank
    @Length(max = 30)
    private String authorName;
    private Timestamp expiredDate;

    public Author(){}

    public Long getIdAuthor(){
        return idAuthor;
    }
    public void setIdAuthor(Long idAuthor){
        this.idAuthor = idAuthor;
    }
    public String getAuthorName(){
        return authorName;
    }
    public void setAuthorName(String authorName){
        this.authorName = authorName;
    }
    public Timestamp getExpiredDate(){
        return expiredDate;
    }
    public void setExpiredDate(Timestamp expiredDate){
        this.expiredDate = expiredDate;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        Author author = (Author) object;
        if(idAuthor != null ? !idAuthor.equals(author.getIdAuthor()) : author.getIdAuthor() != null){
            return false;
        }
        if(authorName != null ? !authorName.equals(author.getAuthorName()) : author.getAuthorName() != null){
            return false;
        }
        if(expiredDate != null ? !expiredDate.equals(author.getExpiredDate()) : author.getExpiredDate() != null){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = idAuthor != null ? idAuthor.hashCode() : 0;
        hash = 41 * hash + (authorName != null ? authorName.hashCode() : 0);
        hash = 41 * hash + (expiredDate != null ? expiredDate.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "Author [idAuthor=" + idAuthor + ", authorName=" + authorName + ", " +
                "expiredDate=" + expiredDate +"]";
    }


}
