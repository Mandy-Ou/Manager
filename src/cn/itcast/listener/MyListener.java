package cn.itcast.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyListener implements HttpSessionListener, ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	// application对象初始化
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// 获取application
		ServletContext sc = event.getServletContext();
		// 在application对象存储变量来统计在线人数
		sc.setAttribute("count", 0);
	}

	//session被创建是人数自增
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// 获取ServletContext对象
		ServletContext sc = event.getSession().getServletContext();
		// 获取在线统计人数的变量
		int count = (Integer) sc.getAttribute("count");
		sc.setAttribute("count", ++count);
	}

	//session被销毁时是人数自减
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// 获取ServletContext对象
		ServletContext sc = event.getSession().getServletContext();
		// 获取在线统计人数的变量
		int count = (Integer) sc.getAttribute("count");
		sc.setAttribute("count", --count);

	}

}
