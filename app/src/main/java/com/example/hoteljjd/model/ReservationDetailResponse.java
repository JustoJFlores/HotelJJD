package com.example.hoteljjd.model;

import java.util.List;

public class ReservationDetailResponse {
    private boolean success;
    private List<ReservationDetail> data;

    // Getter and setter
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ReservationDetail> getData() {
        return data;
    }

    public void setData(List<ReservationDetail> data) {
        this.data = data;
    }
}
