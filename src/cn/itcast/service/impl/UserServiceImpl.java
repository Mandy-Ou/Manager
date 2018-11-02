package cn.itcast.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import cn.itcast.dao.UserDao;
import cn.itcast.dao.impl.UserDaoImpl;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;

public class UserServiceImpl implements UserService {
	//声明Dao层对象
	UserDao ud = new UserDaoImpl();
	//声明日志对象
	Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	//用户登录
	@Override
	public User checkUserLoginService(String uname, String pwd) {
		//打印日志
		logger.debug(uname+"发起登录请求");
		User u = ud.checkUserLoginDao(uname, pwd);
		//判断
		if(u != null){
			logger.debug(uname + "登录成功");
		}else{
			logger.debug(uname + "登录失败");
		}
		return u;
	}

	//修改用户密码
	@Override
	public int userChangePwdService(String newPwd, int uid) {
		logger.debug(uid+":发起密码修改请求");
		int index = ud.userChangePwdDao(newPwd,uid);
		if(index > 0){
			logger.debug(uid+":密码修改成功");
		}else{
			logger.debug(uid+":密码修改失败");
		}
		return index;
	}

	//获取所有的用户信息
	@Override
	public List<User> userShowService() {
		List<User> lu = ud.userShowDao();
		logger.debug("显示所有用户信息："+lu);
		return lu;
	}

	//用户注册功能
	@Override
	public int userRegService(User u) {
		return ud.userRegDao(u);
	}

	@Override
	public User getUserByNameService(String uname) {
		return ud.getUserByName(uname);
	}

}
