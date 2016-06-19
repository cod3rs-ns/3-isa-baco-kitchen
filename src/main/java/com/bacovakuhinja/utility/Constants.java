package com.bacovakuhinja.utility;

// Class for constants
public final class Constants {

    public static final class Authorization {
        public static final String SECRET_KEY  = "SergioRamos";
        public static final String CLAIMS_BODY = "user";
        public static final String LOGGED_USER = "LoggedUser";
    }

    public static final class Registration {
        public static final String STATUS_NOT_VERIFIED = "not_verified";
        public static final String STATUS_VERIFIED = "verified";
    }

    public static final class FriendshipStatus {
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";
        public static final String WAITING  = "waiting";
    }

    public static final class OfferStatus {
        public static final String ACCEPTED = "accepted";
        public static final String CLOSED   = "closed";
        public static final String REJECTED = "rejected";
    }

    public static final class OrderStatus {
        public static final String CREATED   = "CREATED";
        public static final String ACCEPTED  = "ACCEPTED";
        public static final String FINISHED  = "FINISHED";
        public static final String DELIVERED = "DELIVERED";
    }

    public static final class MenuItemType {
        public static final String FOOD  = "food";
        public static final String DRINK = "drink";
    }

    public static final class Reservation {
        public static final String INVITED  = "invited";
        public static final String OWNER    = "owner";
        public static final String ACCEPTED = "accepted";
        public static final String REJECTED = "rejected";
    }

    public static final class UserRoles {
        public static final String EVERYONE  = "all";
        public static final String WAITER    = "waiter";
        public static final String BARTENDER = "bartender";
        public static final String COOK      = "cook";
        public static final String GUEST     = "guest";
        public static final String RESTAURANT_MANAGER  = "restaurant_manager";
        public static final String RESTAURANT_PROVIDER = "restaurant_provider";
        public static final String SYSTEM_MANAGER      = "system_manager";
    }

    public static final class MailParameters {
        public static final long   TOKEN_EXPIRE_TIME = 1800000;
        public static final String TOKEN_CONFIRM_LINK = "http://localhost:8091/api/registration-confirm?token=";
        public static final String HOST_NAME = "smtp.gmail.com";
        public static final int    HOST_PORT = 587;
        public static final String AUTH_USER = "bacovakuhinja@gmail.com";
        public static final String AUTH_PASS = "jedanjebaco";
    }

    public static final class NotificationOrderStatus {
        public static final String NEW  = "new";
        public static final String REMOVE    = "remove";
        public static final String UPDATE = "update";
    }
}
