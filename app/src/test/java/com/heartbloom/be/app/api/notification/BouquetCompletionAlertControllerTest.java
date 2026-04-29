package com.heartbloom.be.app.api.notification;

import com.heartbloom.be.app.api.notification.response.BouquetCompletionAlertResponse;
import com.heartbloom.be.app.api.notification.response.GetBouquetCompletionAlertsResponse;
import com.heartbloom.be.app.security.access.RequestUserArgumentResolver;
import com.heartbloom.be.app.service.notification.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BouquetCompletionAlertControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private BouquetCompletionAlertController bouquetCompletionAlertController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bouquetCompletionAlertController)
                .setCustomArgumentResolvers(new RequestUserArgumentResolver())
                .build();
    }

    @Test
    @DisplayName("완성 꽃다발 확인 알림 목록을 조회할 수 있다")
    void getBouquetCompletionAlerts() throws Exception {
        // given
        GetBouquetCompletionAlertsResponse response = new GetBouquetCompletionAlertsResponse(
                List.of(new BouquetCompletionAlertResponse(
                        1L,
                        10L,
                        "생일 꽃다발",
                        "image-url",
                        LocalDateTime.of(2026, 4, 29, 12, 0)
                ))
        );
        given(notificationService.getBouquetCompletionAlerts(any())).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/bouquet-completion-alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.alerts[0].alertId").value(1L))
                .andExpect(jsonPath("$.data.alerts[0].bouquetId").value(10L));
    }

    @Test
    @DisplayName("완성 꽃다발 확인 알림을 읽음 처리할 수 있다")
    void readBouquetCompletionAlert() throws Exception {
        // when & then
        mockMvc.perform(patch("/api/v1/bouquet-completion-alerts/{alertId}/read", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));

        verify(notificationService).readBouquetCompletionAlert(eq(1L), any());
    }

}
