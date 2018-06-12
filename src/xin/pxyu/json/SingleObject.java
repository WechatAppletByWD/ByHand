package xin.pxyu.json;
/*
建立Json对象类SingleObject，
http协议的数据实体（单个json对象)
实体对象
 */
public class SingleObject extends AbstractJsonObject {

   private Object object;//用来封装单个的数据实体列

   public Object getObject(){
    return object;
}

   public void setObject(Object object){
    this.object=object;
}

}
