package cn.itcast.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import cn.itcast.service.impl.UserServiceImpl;
import cn.itcast.util.JdbcUtil;

/**
 * Servlet�ض���·���ܽ�: ���·�����ӵ�ǰ�����·��������Դ��·��
 * ���·�����servlet�ı�����DD�ļ��е�<url-pattern>Ԫ��ֵ���а���Ŀ¼��������ض����ʻ�����ʧ��
 * ����·������һ��"/"��ʾ��������Ŀ¼ /������Ŀ��/��Դ·���� ���ԣ��Ժ�д�ض����ʱ��д����·���ȽϺ�
 * 
 * Servlet����ת���� /��ʾ��Ŀ��Ŀ¼
 * request.getRequestDispatcher("/��Դ·��").forward(request,response);
 * 
 * @author o1310
 * 
 */
public class UserServlet extends HttpServlet {
	// ������־����
	Logger logger = Logger.getLogger(UserServlet.class);
	// ��ȡservice�����
	UserService us = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ������������ʽ
		request.setCharacterEncoding("utf-8");
		// ������Ӧ�����ʽ
		response.setContentType("text/html;charset=utf-8");
		// ��ȡ������
		String oper = request.getParameter("oper");
		if ("login".equals(oper)) {
			// ���õ�½������
			checkUserLogin(request, response);
		} else if ("pwd".equals(oper)) {
			// �����޸����빦��
			userChangePwd(request, response);
		} else if ("out".equals(oper)) {
			// �����˳�����
			userOut(request, response);
		} else if ("show".equals(oper)) {
			// ������ʾ�����û�����
			userShow(request, response);
		} else if ("register".equals(oper)) {
			// ����ע�Ṧ��
			userReg(request,response);
		} else {
			logger.debug("û���ҵ���Ӧ�Ĳ�������" + oper);
		}
	}

	//�û�ע�Ṧ��
	private void userReg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//��ȡ������Ϣ
//		String uname = request.getParameter("uname");
//		String pwd = request.getParameter("pwd");
//		String sex = request.getParameter("sex");
//		int age = request.getParameter("age")!=""?Integer.parseInt(request.getParameter("age")):0;
//		String birth = request.getParameter("birth");
		
		//��BeanUtils����JavaBean
		User u = JdbcUtil.copyToBean(request, User.class);
		
//		String[] bs = null;
//		StringBuffer sb = new StringBuffer();
//		if(birth != ""){
//				bs = birth.split("/");
//				for(int i = 0 ;i <bs.length;i++){
//					if(i<bs.length -1){
//						sb.append(bs[i]);
//						sb.append("-");
//					}else{
//						sb.append(bs[i]);
//					}
//				}
//		}
//		User u = new User(0,uname,pwd,sex,age,sb.toString());
		
		//User u = new User(0,uname,pwd,sex,age,birth);
		
		
		//����������Ϣ
		//����ҵ��㴦��
		int index = us.userRegService(u);
		//������Ӧ���
		if(index > 0){
			//��ȡsession
			HttpSession hs = request.getSession();
			hs.setAttribute("flag", 2);
			//�ض���
			response.sendRedirect("login.jsp");
		}else{
			
		}
	}

	//��ʾ�����û���Ϣ
	private void userShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//��������
			//����service()
			List<User> lu = us.userShowService();
			//�ж�
			if(lu != null){
				//����ѯ���û����ݴ洢��request����
				request.setAttribute("lu", lu);
				//����ת��
				request.getRequestDispatcher("/user/showUser.jsp").forward(request,response);
				return;
			}
			
	}

	// �û��޸����빦��
	private void userChangePwd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// ��ȡ����������
		String newPwd = request.getParameter("newPwd");
		// ��session�л�ȡ�û���Ϣ
		User u = (User) request.getSession().getAttribute("user");
		int uid = u.getUid();
		// ��������
		//����service����
		int index = us.userChangePwdService(newPwd,uid);
		if(index >0){
			//��ȡsession����
			HttpSession hs = request.getSession();
			hs.setAttribute("flag", 1);
			//�ض��򵽵�½ҳ�棨��������ת��������Ϊ����ת�������session�е����ݣ����Ǵ�ʱ�û��Ѿ��޸������룬�������ض���
			response.sendRedirect("/manager/login.jsp");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// �û��˳�
	private void userOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// �õ��Ự����
		HttpSession session = request.getSession();
		// ǿ������session
		session.invalidate();
		// �ض��򵽵�¼ҳ��
		response.sendRedirect("/manager/login.jsp");

	}

	// �����¼
	private void checkUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// ��ȡ������Ϣ
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		// ����������Ϣ

		// У��
		User u = us.checkUserLoginService(uname, pwd);
		if (u != null) {
			// ��ȡsession����
			HttpSession hs = request.getSession();
			// ���û����ݴ洢��session��
			hs.setAttribute("user", u);
			// �ض���
			response.sendRedirect("/manager/main/main.jsp");
		} else {
			//
			request.setAttribute("flag", 0);
			// ����ת��
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}
		// ��Ӧ������
		// ֱ����Ӧ
		// ����ת��

	}

}
