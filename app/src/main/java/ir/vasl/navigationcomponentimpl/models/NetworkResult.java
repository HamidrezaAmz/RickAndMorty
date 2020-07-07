package ir.vasl.navigationcomponentimpl.models;

import android.text.TextUtils;

import ir.vasl.navigationcomponentimpl.utils.GlobalEnum.ResultEnum;

public class NetworkResult {

    ResultEnum resultType = ResultEnum.Idle;
    String message = "";
    String code = "";

    String originCall = "";

    public NetworkResult(ResultEnum resultType) {
        this.resultType = resultType;
    }

    public NetworkResult(ResultEnum resultType, String message) {
        this.resultType = resultType;
        this.message = message;
    }

    private void parseMessageForCode(String message) {
        String[] split = message.split(" ");
        if (split.length > 0 && TextUtils.isDigitsOnly(split[split.length - 1]))
            this.code = split[split.length - 1];
    }

    public NetworkResult(ResultEnum resultType, String message, String originCall) {
        this.resultType = resultType;
        this.message = message;
        this.originCall = originCall;
    }

    public NetworkResult(ResultEnum resultType, String message, String originCall, int code) {
        this.resultType = resultType;
        this.message = message;
        this.originCall = originCall;
        this.code = String.valueOf(code);
    }

    public NetworkResult(ResultEnum resultType, String message, int code) {
        this.resultType = resultType;
        this.message = message;
        this.code = String.valueOf(code);
    }

    public NetworkResult() {
    }

    public ResultEnum getResultType() {
        return resultType;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getOriginCall() {
        return originCall;
    }
}
