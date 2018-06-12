package xin.pxyu.json;
import java.util.Date;
/*
建立AbstractJsonObject(Json数据的基类)，
JSON本身并没有对这些字段做任何定义，
一般接口返回code代表操作状态，msg是描述信息，data是请求的业务数据。
json对象
 */
public class AbstractJsonObject {
    /*
    http协议的响应报文：
    1）状态行
    状态码：1xx 2xx 3xx 4xx 5xx code
    解释状态码的术语 msg
    http版本 1.1

     */
    private String code;//状态码==》code代表操作状态
    private String msg;//返回信息
    private Long time=new Date().getTime(); //getTime()方法返回自1970年1月1日00:00:00 GMT已经过去的毫秒

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTime() { return time; }

    public void setTime(Long time) {
        this.time = time;
    }
    public void setContent(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public void setStatusObject(StatusObject statusObject){
        this.code= statusObject.getCode();
        this.msg= statusObject.getMsg();
    }
}

