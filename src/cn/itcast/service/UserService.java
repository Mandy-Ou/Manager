package cn.itcast.service;

import java.util.List;

import cn.itcast.pojo.User;

public interface UserService {
	/**
	 * У���û���¼
	 * @param uname �û���
	 * @param pwd �û�����
	 * @return �λز�ѯ�����û���Ϣ
	 */
	User checkUserLoginService(String uname, String pwd);

	/**
	 * �޸��û�����
	 * @param newPwd ������
	 * @param uid �û�id
	 * @return
	 */
	int userChangePwdService(String newPwd, int uid);

	/**
	 * ��ȡ���е��û���Ϣ
	 * @return
	 */
	List<User> userShowService();

	/**
	 * �û�ע��
	 * @param u
	 * @return
	 */
	int userRegService(User u);
	
	/**
	 * �����û�����ȡ�û�����
	 * @param uname
	 * @return
	 */
	User getUserByNameService(String uname);
}
