package org.whh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.whh.base.ServiceBase;
import org.whh.dao.UserDao;
import org.whh.entity.User;
import org.whh.util.CryptoHelper;
import org.whh.web.CommonException;

@Service
public class UserService extends ServiceBase {
	@Autowired
	private UserDao userDao;

	public void register(User user) throws CommonException {
		if (isNull(user)) {
			throw new CommonException("用户信息为空");
		}
		if (isNull(user.getName())) {
			throw new CommonException("用户名不能为空");
		}
		if (isNull(user.getPassword())) {
			throw new CommonException("密码不能为空");
		}
		User oldUser = userDao.findByName(user.getName());
		if (!isNull(oldUser)) {
			throw new CommonException("该用户名已被注册");
		}
		String firstMD5 = CryptoHelper.getMd5(user.getPassword());
		String password = CryptoHelper.getMd5(firstMD5);
		user.setPassword(password);
		userDao.save(user);
	}

	public Long login(String name, String password) throws CommonException {
		if (isNull(name)) {
			throw new CommonException("用户名不能为空");
		}
		if (isNull(password)) {
			throw new CommonException("密码不能为空");
		}
		User user = userDao.findByName(name);
		if (isNull(user)) {
			throw new CommonException("用户名或密码不正确");
		}
		String firstMD5 = CryptoHelper.getMd5(password);
		String result = CryptoHelper.getMd5(firstMD5);
		String realPassword = user.getPassword();
		if (!result.equals(realPassword)) {
			throw new CommonException("用户名或密码不正确");
		}
		return user.getId();
	}
}
