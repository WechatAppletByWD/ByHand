package xin.pxyu.util;
import javax.servlet.*;
import java.io.IOException;
public class Counter implements Filter {
    private int hitCount;
    public void init(FilterConfig filterConfig){
        hitCount=0;
    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain){
        hitCount++;
        System.out.println(hitCount);
        try{
            filterChain.doFilter(request,response);
        }catch(IOException i){
          System.out.println("IOException");
          i.printStackTrace();
        }catch(ServletException s){
            System.out.println("ServletException");
            s.printStackTrace();
        }
    }
    public void destroy(){}
}
