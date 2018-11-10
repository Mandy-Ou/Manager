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
 * Servlet重定向路径总结: 相对路径：从当前请求的路径查找资源的路径
 * 相对路径如果servlet的别名（DD文件中的<url-pattern>元素值）中包含目录，会造成重定向资环查找失败
 * 绝对路径：第一个"/"表示服务器根目录 /虚拟项目名/资源路径名 所以，以后写重定向的时候写绝对路径比较好
 * 
 * Servlet请求转发： /表示项目根目录
 * request.getRequestDispatcher("/资源路径").forward(request,response);
 * 
 * @author o1310
 * 
 */
public class UserServlet extends HttpServlet {
	// 声明日志对象
	Logger logger = Logger.getLogger(UserServlet.class);
	// 获取service层对象
	UserService us = new UserServiceImpl();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// 设置请求编码格式
		request.setCharacterEncoding("utf-8");
		// 设置响应编码格式
		response.setContentType("text/html;charset=utf-8");
		// 获取操作符
		String oper = request.getParameter("oper");
		if ("login".equals(oper)) {
			// 调用登陆处理方法
			checkUserLogin(request, response);
		} else if ("pwd".equals(oper)) {
			// 调用修改密码功能
			userChangePwd(request, response);
		} else if ("out".equals(oper)) {
			// 调用退出功能
			userOut(request, response);
		} else if ("show".equals(oper)) {
			// 调用显示所有用户功能
			userShow(request, response);
		} else if ("register".equals(oper)) {
			// 调用注册功能
			userReg(request,response);
		} else {
			logger.debug("没有找到对应的操作符：" + oper);
		}
	}

	//用户注册功能
	private void userReg(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		//获取请求信息
//		String uname = request.getParameter("uname");
//		String pwd = request.getParameter("pwd");
//		String sex = request.getParameter("sex");
//		int age = request.getParameter("age")!=""?Integer.parseInt(request.getParameter("age")):0;
//		String birth = request.getParameter("birth");
		
		//用BeanUtils简化了JavaBean
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
		
		
		//处理请求信息
		//调用业务层处理
		int index = us.userRegService(u);
		//返回响应结果
		if(index > 0){
			//获取session
			HttpSession hs = request.getSession();
			hs.setAttribute("flag", 2);
			//重定向
			response.sendRedirect("login.jsp");
		}else{
			
		}
	}

	//显示所有用户信息
	private void userShow(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//处理请求
			//调用service()
			List<User> lu = us.userShowService();
			//判断
			if(lu != null){
				//将查询的用户数据存储到request对象
				request.setAttribute("lu", lu);
				//请求转发
				request.getRequestDispatcher("/user/showUser.jsp").forward(request,response);
				return;
			}
			
	}

	// 用户修改密码功能
	private void userChangePwd(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 获取新密码数据
		String newPwd = request.getParameter("newPwd");
		// 从session中获取用户信息
		User u = (User) request.getSession().getAttribute("user");
		int uid = u.getUid();
		// 处理请求
		//调用service处理
		int index = us.userChangePwdService(newPwd,uid);
		if(index >0){
			//获取session对象
			HttpSession hs = request.getSession();
			hs.setAttribute("flag", 1);
			//重定向到登陆页面（不用请求转发，是因为请求转发会分享session中的内容，但是此时用户已经修改了密码，所以用重定向）
			response.sendRedirect("/manager/login.jsp");
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	// 用户退出
	private void userOut(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 得到会话对象
		HttpSession session = request.getSession();
		// 强制销毁session
		session.invalidate();
		// 重定向到登录页面
		response.sendRedirect("/manager/login.jsp");

	}

	// 处理登录
	private void checkUserLogin(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		// 获取请求信息
		String uname = request.getParameter("uname");
		String pwd = request.getParameter("pwd");
		// 处理请求信息

		// 校验
		User u = us.checkUserLoginService(uname, pwd);
		if (u != null) {
			// 获取session对象
			HttpSession hs = request.getSession();
			// 将用户数据存储到session中
			hs.setAttribute("user", u);
			// 重定向
			response.sendRedirect("/manager/main/main.jsp");
		} else {
			//
			request.setAttribute("flag", 0);
			// 请求转发
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}
		// 响应处理结果
		// 直接响应
		// 请求转发

	}

}
