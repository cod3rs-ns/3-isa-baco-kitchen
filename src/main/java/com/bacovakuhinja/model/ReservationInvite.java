package com.bacovakuhinja.model;

/**
 * Class used only for ReservationInvite Parameters
 */
public class ReservationInvite {

    private Reservation reservation;
    private Integer tableId;
    private Integer restaurantId;

    public ReservationInvite(Reservation reservation, Integer tableId, Integer restaurantId) {
        this.reservation = reservation;
        this.tableId = tableId;
        this.restaurantId = restaurantId;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
