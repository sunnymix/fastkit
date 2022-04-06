package net.fastkit.core.rest;

/**
 * @author sunnymix
 */
public class Response<T> {

    // *** prop ***

    private boolean success;
    private T data;
    private Integer errCode;
    private String errMessage;

    // *** static ***

    public static <T> Response<T> error(Integer errCode, String errMessage) {
        return new Response<>(false, null, errCode, errMessage);
    }

    // *** constructor, getter, setter ***

    public Response() {
    }

    public Response(boolean success, T data, Integer errCode, String errMessage) {
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", data=" + data +
                ", errCode=" + errCode +
                ", errMessage='" + errMessage + '\'' +
                '}';
    }

}
