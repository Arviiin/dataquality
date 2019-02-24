package com.arviiin.dataquality.interceptor;

/**
 * 登录拦截器
 */
/*@Component
public class LoginRequredInterceptor implements HandlerInterceptor {

    private static final Logger logger= LoggerFactory.getLogger(LoginRequredInterceptor.class);

    @Autowired
    HostHolder hostHolder;//这样user就可以在不同线程里都有想应的变量

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (hostHolder.getUser() == null){
            response.sendRedirect("/reglogin?next="+request.getRequestURI());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}*/
