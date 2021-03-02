package me.devking2106.useddeal.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import me.devking2106.useddeal.dto.UserDetailDto;
import me.devking2106.useddeal.dto.UserFindDto;
import me.devking2106.useddeal.entity.User;

@Mapper
public interface UserMapper {

	User findById(long userId);

	int save(User userInfo);

	boolean idCheck(String userId);

	List<UserFindDto> findByNickName(String nickname);

	User findByIdAndPassword(@Param("id") String id, @Param("password") String password);
}
