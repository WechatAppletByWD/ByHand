package xin.pxyu.json;
import java.util.List;
/*
建立Json数组类ListObject,用来封装数据实体
http协议的数据实体（json对象的list)
实体集
 */
public class ListObject extends AbstractJsonObject {

    private List<?> data;//用来封装数据实体列表

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data =data;
    }
}
