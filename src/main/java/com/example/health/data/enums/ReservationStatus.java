package com.example.health.data.enums;

public enum ReservationStatus {
    Reserved(1), Changed(2), Canceled(3), Done(4);

    private final int statusCode;

    ReservationStatus(int i) {
        this.statusCode = i;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
