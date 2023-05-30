package com.deloitte.baseapp.configs.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({

        // Define multiple @ApiResponse annotations within the @ApiResponses annotation
        @ApiResponse(
                responseCode = "200",
                description = "Resource found"),

        @ApiResponse(
                responseCode = "404",
                description = "Resource not found"),


        @ApiResponse(
                responseCode = "400",
                description = "Bad Request"
//                content = {@Content(
//                        mediaType = "application/json",
//                        schema = @Schema(
//                                implementation = ErrorResponse.class)

        )})

@SecurityRequirement(name = "bearerAuth")

public @interface ReadApiResponse {
}