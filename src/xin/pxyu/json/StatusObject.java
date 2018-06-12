package xin.pxyu.json;
/*
StatusObject是一个状态对象，封装了状态码(code)和状态信息(msg）
code代表操作状态，msg是描述信息
http:响应报文（响应码+描述信息+http版本+数据实体）
状态对象
 */
public class StatusObject {
    private String code;
    private String msg;
public StatusObject(String code,String msg){
    super();
    this.msg=msg;
    this.code=code;
}
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
}
