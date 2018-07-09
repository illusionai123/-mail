package cn.smbms.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	private static SqlSessionFactory factory;
	static{
		String resource="mybatis-config.xml";
		InputStream is;
		try {
			is = Resources.getResourceAsStream(resource);
			factory = new SqlSessionFactoryBuilder().build(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static SqlSession creatSqlSession(){
		return factory.openSession(false);
	}
	
	public static void closeSession(SqlSession sqlSession){
		if(sqlSession!=null){
			sqlSession.close();
		}
	}
}
