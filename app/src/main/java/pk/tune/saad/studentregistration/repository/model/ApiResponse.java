package pk.tune.saad.studentregistration.repository.model;

public class ApiResponse {

    public static final String LOADING = "loading";
    public static final String SUCCESS = "success";
    public static final String ERROR = "error";
    public final String status;
    public final Object responseData;
    public final Throwable error;

    private ApiResponse(String status, Object responseData, Throwable error) {
        this.status = status;
        this.responseData = responseData;
        this.error = error;
    }

    public static ApiResponse loading(){
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse result(Object object){
        return new ApiResponse(SUCCESS, object, null);
    }

    public static ApiResponse error(Throwable throwable){
        return new ApiResponse(ERROR, null, throwable);
    }
}
