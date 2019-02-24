package com.arviiin.dataquality.interceptor;

/**
 * 写拦截器
 */
/*@Component
public class PassportInterceptor implements HandlerInterceptor {

    private static final Logger logger= LoggerFactory.getLogger(PassportInterceptor.class);

    @Autowired
    LoginTicketMapper loginTicketMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    HostHolder hostHolder;//这样user就可以在不同线程里都有想应的变量

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()){
                if (cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null){
            LoginTicketBean loginTicket = loginTicketMapper.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() !=0){
                return true;
            }
            User user = userMapper.getUserById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null){
            modelAndView.addObject("user", hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
    }
}*/
