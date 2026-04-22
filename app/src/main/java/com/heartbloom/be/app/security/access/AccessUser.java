package com.heartbloom.be.app.security.access;

public interface AccessUser {

    Long getId();

    String getName();

    String getEmail();

    boolean isDeleted();

}
