package vn.iotstar.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.iotstar.repository.UserInfoRepository;
import vn.iotstar.Entity.*;

@Service
public record UserService(UserInfoRepository repository, PasswordEncoder passwordEndcoder) {
	public String addUser(UserInfor userInfo) {
		userInfo.setPassword(passwordEndcoder.encode(userInfo.getPassword()));
		repository.save(userInfo);
		return "Thêm user thanh công!";

		}
}
