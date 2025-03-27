package bankai.shizuku.ichigo.common.request;

import bankai.shizuku.ichigo.common.utils.IdUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;

import java.io.Serializable;

/**
 * 基础请求
 */
@Data
public class BaseRequest implements Serializable {
    private String requestId;
    private Long timestamp;
    private HttpServletRequest request;
    private HttpServletResponse response;
    {
        requestId = IdUtil.uuid();
        timestamp = System.currentTimeMillis();
    }


}
