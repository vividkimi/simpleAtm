package com.bear.robotics.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private Status status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime serverDatetime;
    private T data;

    private ApiResponse(Status status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.serverDatetime = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Status.SUCCESS, Status.SUCCESS.getMessage(), data);
    }


    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(Status.FAIL, message, null);
    }

    public static <T> ApiResponse<T> majorFail(String message) {
        return new ApiResponse<>(Status.MAJOR_FAILURE, message, null);
    }


    public static <T> ApiResponse<T> of(Boolean isSuccess, String message) {

        return new ApiResponse<>(isSuccess ? Status.SUCCESS : Status.FAIL, message, null);
    }
}
