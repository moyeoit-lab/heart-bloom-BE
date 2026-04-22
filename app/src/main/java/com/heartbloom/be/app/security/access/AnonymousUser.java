package com.heartbloom.be.app.security.access;

import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BaseErrorCode;

public class AnonymousUser implements AccessUser {

    @Override
    public Long getId() {
        throw new ServiceException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public String getName() {
        throw new ServiceException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public String getEmail() {
        throw new ServiceException(BaseErrorCode.UNAUTHORIZED);
    }

    @Override
    public boolean isDeleted() {
        throw new ServiceException(BaseErrorCode.UNAUTHORIZED);
    }

}
