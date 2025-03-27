package bankai.shizuku.ichigo.common.request;

//import lombok.Data;

/**
 * 分页请求
 */

public class PageRequest extends BaseRequest {

    /**
     * 每页记录数
     */
    protected Integer size = 10;

    /**
     * 当前页
     */
    protected Integer current = 1;


}
