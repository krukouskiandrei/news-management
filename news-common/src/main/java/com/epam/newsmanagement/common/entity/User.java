package com.epam.newsmanagement.common.entity;

import java.io.Serializable;

/**
 * Represents User Model corresponding to table in dao model
 */
public class User implements Serializable {

    private static final long serialVersionUID = 2314231232131231231L;

    private Long idUser;
    private String userName;
    private String login;
    private String password;
    private Role role;

    public User(){

    }

    public Long getIdUser(){
        return idUser;
    }
    public void setIdUser(Long idUser){
        this.idUser = idUser;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }
    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public Role getRole(){
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }

    @Override
    public boolean equals(Object object){
        if(this == object){
            return true;
        }
        if(object == null || getClass() !=  object.getClass()){
            return false;
        }

        User user = (User) object;
        if(idUser != null ? !idUser.equals(user.getIdUser()) : user.getIdUser() != null){
            return false;
        }
        if(userName != null ? !userName.equals(user.getUserName()) : user.getUserName() != null){
            return false;
        }
        if(login != null ? !login.equals(user.getLogin()) : user.getLogin() != null){
            return false;
        }
        if (password != null ? !password.equals(user.getPassword()) : user.getPassword() != null){
            return false;
        }
        if(role != user.getRole()){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode(){
        int hash = idUser != null ? idUser.hashCode() : 0;
        hash = 41 * hash + (userName != null ? userName.hashCode() : 0);
        hash = 41 * hash + (login != null ? login.hashCode() : 0);
        hash = 41 * hash + (password != null ? password.hashCode() : 0);
        hash = 41 * hash + (role != null ? role.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString(){
        return "User [idUser=" + idUser + ", userName=" + userName + ", " +
                "login=" + login + ", password=" + password + ", role=" + role +"];";
    }

}
