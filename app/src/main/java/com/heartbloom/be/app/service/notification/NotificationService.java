package com.heartbloom.be.app.service.notification;

import com.heartbloom.be.app.api.notification.response.GetBouquetCompletionAlertsResponse;
import com.heartbloom.be.app.application.notification.implementation.NotificationManager;
import com.heartbloom.be.app.security.access.AccessUser;
import com.heartbloom.be.app.security.access.AuthenticateUser;
import com.heartbloom.be.common.exception.ServiceException;
import com.heartbloom.be.common.exception.code.BaseErrorCode;
import com.heartbloom.be.common.exception.code.NotificationErrorCode;
import com.heartbloom.be.common.time.TimeProvider;
import com.heartbloom.be.core.model.domain.notification.Notification;
import com.heartbloom.be.core.repository.domain.notification.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationManager notificationManager;
    private final TimeProvider timeProvider;

    @Transactional(readOnly = true)
    public GetBouquetCompletionAlertsResponse getBouquetCompletionAlerts(AccessUser user) {
        validateAuthenticated(user);

        return GetBouquetCompletionAlertsResponse.of(
                notificationRepository.queryUnreadBouquetCompletionAlerts(user.getId())
        );
    }

    @Transactional
    public void readBouquetCompletionAlert(Long alertId, AccessUser user) {
        validateAuthenticated(user);

        Notification notification = notificationManager.findById(alertId)
                .orElseThrow(() -> new ServiceException(NotificationErrorCode.NOT_FOUND));

        if (!notification.isOwnedBy(user.getId())) {
            throw new ServiceException(NotificationErrorCode.ACCESS_DENIED);
        }

        notificationManager.save(notification.read(timeProvider.now()));
    }

    private void validateAuthenticated(AccessUser user) {
        if (!(user instanceof AuthenticateUser)) {
            throw new ServiceException(BaseErrorCode.UNAUTHORIZED);
        }
    }

}
