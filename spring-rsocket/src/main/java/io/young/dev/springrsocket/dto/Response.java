package io.young.dev.springrsocket.dto;

import io.young.dev.springrsocket.dto.error.ErrorEvent;

import java.util.Objects;

public class Response<T> {

    ErrorEvent errorResponse;
    T successResponse;

    public Response() {
    }

    public Response(ErrorEvent errorResponse) {
        this.errorResponse = errorResponse;
    }

    public Response(T successResponse) {
        this.successResponse = successResponse;
    }

    public boolean hasError() {
        return Objects.nonNull(this.errorResponse);
    }

    public ErrorEvent getErrorResponse() {
        return errorResponse;
    }

    public T getSuccessResponse() {
        return successResponse;
    }

    public static <T> Response<T> with(T t) {
        return new Response<>(t);
    }

    public static <T> Response<T> with(ErrorEvent errorResponse) {
        return new Response<>(errorResponse);
    }
}
