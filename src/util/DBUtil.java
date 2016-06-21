package util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtil {
	public static ComboPooledDataSource dataSource;
	private Connection connect;
	static{
		//�����ô���ķ�ʽȥʵ��c3p0������
		dataSource = new ComboPooledDataSource();
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/wechat");
        dataSource.setUser("root");
        dataSource.setPassword("root");
        dataSource.setMaxPoolSize(20);
        dataSource.setAcquireIncrement(5);
        dataSource.setAcquireRetryDelay(1000);
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(1000);
        
        try {
			dataSource.setDriverClass("com.mysql.jdbc.Driver");
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public DBUtil() {
	}
	
	/**
	 * ������Դ�л�ȡ���ݿ�����
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnect() throws SQLException{
        //����C3P0���ӳ�,������c3p0-config.xml�ļ�
        
        Connection conn = dataSource.getConnection();
        return conn;
	} 
}
