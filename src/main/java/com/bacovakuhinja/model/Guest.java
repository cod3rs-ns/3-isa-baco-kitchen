package com.bacovakuhinja.model;

import javax.persistence.*;

@Entity
@Table(name = "guests")
@PrimaryKeyJoinColumn(name = "g_id")
public class Guest extends User {

    @Column(name = "g_id", insertable = false, updatable = false)
    private Integer guestId;

    @Column(name = "g_info")
    private String guestInfo;

    public Guest() {
        super();
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public String getGuestInfo() {
        return guestInfo;
    }

    public void setGuestInfo(String guestInfo) {
        this.guestInfo = guestInfo;
    }
}
