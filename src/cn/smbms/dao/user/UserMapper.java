package cn.smbms.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.smbms.pojo.Address;
import cn.smbms.pojo.User;

public interface UserMapper {
	public List<User> getUserList(@Param("userName")String userName,@Param("id")Integer id);
	
	public List<User> getUserListByUserName(String userName);
	
	//增加
	public int add(User user);
	
	//修改密码
	public int updatePwd(@Param("id")Integer id,@Param("userPassword")String userPassword);

	//根据id删除信息
	public int deleteById(@Param("id")Integer id);
	
	//多表查询
	public List<User> getUserListByRoleId(@Param("userRole")Integer userRole);
	
	//一对多
	public List<User> getAddressListByUserId(@Param("id")Integer id); 
	
	//根据roleId列表查询信息
	public List<User> getUserByRoleId_foreach_array(Integer[] roleIds);
}
