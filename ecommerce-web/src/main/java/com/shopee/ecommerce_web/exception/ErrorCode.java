package com.shopee.ecommerce_web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    TAG_NOT_EXISTED(1008,"tag not existed!", HttpStatus.NOT_FOUND),
    TAG_EXISTED(1009, "tag already existed", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1010, "product not found", HttpStatus.NOT_FOUND),
    PRODUCT_VARIANT_NOT_FOUND(1011, "productVariant not found!", HttpStatus.NOT_FOUND),
    CART_NOT_FOUND(1012, "cart not found", HttpStatus.NOT_FOUND),
    CART_ITEM_NOT_FOUND(1013, "cartItem not found", HttpStatus.NOT_FOUND),
    INVENTORY_NOT_EXISTED(1014, "inventory not exist!", HttpStatus.NOT_FOUND),
    PRODUCT_INVENTORY_NOT_FOUND(1015,"inventoryProduct not found!", HttpStatus.NOT_FOUND),
    NOTIFICATION_NOT_FOUND(1016, "notification not found!", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND(1017, "order not found!", HttpStatus.NOT_FOUND),
    ORDER_ITEM_NOT_FOUND(1018, "orderItem not found!", HttpStatus.NOT_FOUND),
    PAYMENT_NOT_FOUND(1019, "payment not found!", HttpStatus.NOT_FOUND),
    SHIPPING_TRACKER_NOT_FOUND(1020, "shipping tracker not found", HttpStatus.NOT_FOUND),
    ADDRESS_NOT_FOUND(1021, "address not found!", HttpStatus.NOT_FOUND),
    PASSWORD_EXISTED(1022, "password existed!", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}