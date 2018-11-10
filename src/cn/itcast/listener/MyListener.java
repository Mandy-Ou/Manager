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

	// application�����ʼ��
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// ��ȡapplication
		ServletContext sc = event.getServletContext();
		// ��application����洢������ͳ����������
		sc.setAttribute("count", 0);
	}

	//session����������������
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// ��ȡServletContext����
		ServletContext sc = event.getSession().getServletContext();
		// ��ȡ����ͳ�������ı���
		int count = (Integer) sc.getAttribute("count");
		sc.setAttribute("count", ++count);
	}

	//session������ʱ�������Լ�
	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// ��ȡServletContext����
		ServletContext sc = event.getSession().getServletContext();
		// ��ȡ����ͳ�������ı���
		int count = (Integer) sc.getAttribute("count");
		sc.setAttribute("count", --count);

	}

}
