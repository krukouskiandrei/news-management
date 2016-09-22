package com.epam.newsmanagement.common.entity;

import java.io.Serializable;

/**
 * User roles in the system
 */
public class Role implements Serializable {

    private static final long serialVersionUID = 2342342342342342L;

    private Long userId;
    private String roleName;

    public Role(){}

    public Long getUserId(){
        return userId;
    }
    public void setUserId(Long userId){
        this.userId = userId;
    }
    public String getRoleName(){
        return roleName;
    }
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        Role role = (Role) object;
        if(userId != null ? !userId.equals(role.getUserId()) : role.getUserId() != null){
            return false;
        }
        if(roleName != null ? !roleName.equals(role.getUserId()) : role.getRoleName() != null){
            return false;
        }
        return true;
    }
    @Override
    public int hashCode(){
        int hash = userId != null ? userId.hashCode() : 0;
        hash = 41 * hash + (roleName != null ? roleName.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "Role [userId=" + userId + ", roleName=" + roleName + "]";
    }


}