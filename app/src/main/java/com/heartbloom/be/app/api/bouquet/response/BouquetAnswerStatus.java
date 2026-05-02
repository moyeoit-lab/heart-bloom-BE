package com.heartbloom.be.app.api.bouquet.response;

public enum BouquetAnswerStatus {

    WAITING_FOR_MY_ANSWER,
    WAITING_FOR_COUNTERPART_ANSWER,
    COMPLETED,
    NOT_ANSWERED;

    public static BouquetAnswerStatus of(boolean myAnswered, boolean counterpartAnswered) {
        if (myAnswered && counterpartAnswered) {
            return COMPLETED;
        }
        if (myAnswered) {
            return WAITING_FOR_COUNTERPART_ANSWER;
        }
        if (counterpartAnswered) {
            return WAITING_FOR_MY_ANSWER;
        }
        return NOT_ANSWERED;
    }

}
