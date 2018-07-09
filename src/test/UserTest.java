package test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import cn.smbms.dao.user.UserMapper;
import cn.smbms.pojo.Address;
import cn.smbms.pojo.User;
import cn.smbms.util.MybatisUtil;

public class UserTest {
	private Logger logger = Logger.getLogger(UserTest.class);
	
	@Before
	public void setUp(){
		
	}
	
	
	@Test
	public void test() throws Exception{
		List<User> users = null;
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		//users =	sqlSession.selectList("cn.smbms.dao.user.UserMapper.getUserList");
		users =  sqlSession.getMapper(UserMapper.class).getUserList(null, 1);
		for (User user : users) {
			logger.debug("user----->"+user.getUserName()+user.getUserCode());
		}
		MybatisUtil.closeSession(sqlSession);
	}
	
	@Test
	public void testCount()throws Exception{
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		int count = sqlSession.selectOne("cn.smbms.dao.user.UserMapper.count");
		logger.debug("count------->"+count);
		MybatisUtil.closeSession(sqlSession);
		
	}
	
	@Test
	public void testGetUserListByUserName(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		List<User> users = sqlSession.getMapper(UserMapper.class).getUserListByUserName("张");
		for (User user : users) {
			logger.debug("username"+user.getUserName());
		}
		MybatisUtil.closeSession(sqlSession);		
	}
	
	//增加
	@Test
	public void testAdd() throws ParseException{
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		int count = 0 ;
		User user = new User();
		user.setUserCode("test001");
		user.setUserName("测试用户001");
		user.setUserPassword("1234567");
		user.setGender(1);
		user.setPhone("13478972345");
		user.setBirthday(new SimpleDateFormat("yyyy-mm-dd").parse("1984-12-12"));
		user.setUserRole(1);
		user.setAddress("测试地址");
		user.setCreatedBy(1);
		user.setCreationDate(new Date());
		count = sqlSession.getMapper(UserMapper.class).add(user);
		
		sqlSession.commit();
		MybatisUtil.closeSession(sqlSession);
	}
	
	/*
	 * 修改
	 */
	@Test
	public void testUpdatePwd(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		int count = sqlSession.getMapper(UserMapper.class).updatePwd(16, "123456a");
		sqlSession.commit();
		MybatisUtil.closeSession(sqlSession);
		logger.debug("count------------>"+count);
	}
	
	@Test
	public void testDeleteById(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		int count = sqlSession.getMapper(UserMapper.class).deleteById(16);
		sqlSession.commit();
		
		MybatisUtil.closeSession(sqlSession);
		logger.debug("count------------->"+count);
	}
	
	/**
	 * 多表查询
	 */
	@Test
	public void testGetUserListByRoleId(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		List<User> users = new ArrayList<>();
		users = sqlSession.getMapper(UserMapper.class).getUserListByRoleId(3);
		sqlSession.commit();
		System.out.println("------------");
		MybatisUtil.closeSession(sqlSession);
		for (User user : users) {
			logger.debug("username:"+user.getUserName()+"role:"+user.getRole().getId()+"roleName:"+user.getRole().getRoleName());
		}
		
	}
	
	/**
	 * 一对多
	 */
	@Test
	public void testgetAddressListByUserId(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		List<User> users = new ArrayList<>();
		users = sqlSession.getMapper(UserMapper.class).getAddressListByUserId(1);
		sqlSession.commit();
		System.out.println("------------");
		MybatisUtil.closeSession(sqlSession);
		for (User user : users) {
			logger.debug("username:"+user.getUserName()+user.getAddressList()+"\t\t<未映射字段>password---->"+user.getUserPassword());
			for (Address address : user.getAddressList()) {
				System.out.println("address:"+address.getId()+"\t\ttel"+address.getTel()+"\t\tpostCode"+address.getPostCode());
			}
		}
	}
	
	//根据roleId列表查询信息
	@Test
	public void testGetUserByRoleId_foreach_array(){
		SqlSession sqlSession = MybatisUtil.creatSqlSession();
		Integer[] roleIds = {2,3};
		List<User> users = sqlSession.getMapper(UserMapper.class).getUserByRoleId_foreach_array(roleIds);
		sqlSession.commit();
		MybatisUtil.closeSession(sqlSession);
		for (User user : users) {
			logger.debug("username:"+user.getUserName()+user.getUserCode()+"\t\t<未映射字段>password---->"+user.getUserPassword());
		}
	}
}