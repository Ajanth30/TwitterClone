package com.Myapps.Twitter.Models.Response;

import com.Myapps.Twitter.Enum.ResponseEnum;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({ "status", "data"})
public class ApiResponse {

    private  ResponseEnum status;

    private Object data;

    public static ApiResponse success(Object data){
        return new ApiResponse(ResponseEnum.SUCCESS,data);
    }
    public static ApiResponse failure(Object data){
        return new ApiResponse(ResponseEnum.FAILURE,data);
    }







}
