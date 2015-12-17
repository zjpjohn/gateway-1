/*
 * Copyright (c) 2008, 2015, OneAPM and/or its affiliates. All rights reserved. 
 */
package com.blueocn.api.vo;

import com.blueocn.api.enums.UserTypeEnum;

import java.io.Serializable;

/**
 * Title: UserVo
 * Description:
 *
 * @author Yufan
 * @version 1.0.0
 * @since 2015-12-17 21:40
 */
public class UserVo implements Serializable {
    private static final long serialVersionUID = 2252192347249740853L;

    private String userName;

    private String userIdentity;

    private String userPassword;

    private UserTypeEnum userType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEnum userType) {
        this.userType = userType;
    }
}