package cn.itcast.service;

import java.util.List;

import cn.itcast.pojo.User;

public interface UserService {
	/**
	 * 校验用户登录
	 * @param uname 用户名
	 * @param pwd 用户密码
	 * @return 参回查询到的用户信息
	 */
	User checkUserLoginService(String uname, String pwd);

	/**
	 * 修改用户密码
	 * @param newPwd 新密码
	 * @param uid 用户id
	 * @return
	 */
	int userChangePwdService(String newPwd, int uid);

	/**
	 * 获取所有的用户信息
	 * @return
	 */
	List<User> userShowService();

	/**
	 * 用户注册
	 * @param u
	 * @return
	 */
	int userRegService(User u);
	
	/**
	 * 根据用户名获取用户对象
	 * @param uname
	 * @return
	 */
	User getUserByNameService(String uname);
}
