package vn.iotstar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.iotstar.Entity.UserInfor;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfor, Integer>{
	Optional<UserInfor> findByName(String username);
}
