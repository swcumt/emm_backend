package com.nationsky.webapp.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;
import com.nationsky.webapp.util.Utils;

public class PooledConn {
	private static String DBClassName = null;
	private static String DBUrl = null;
	private static String DBUser = null;
	private static String DBPassword = null;

	private static ComboPooledDataSource cpds = null;

	private static PooledConn instance = null;

	private static final ThreadLocal<Connection> threadLocalConn = new ThreadLocal<Connection>();
	private static final ThreadLocal<PreparedStatement> threadLocalSt = new ThreadLocal<PreparedStatement>();


	protected PooledConn() {
	}

	public static synchronized void config(String hostIp, String name, String pass) {
		DBUrl = Utils.getPropertiesValue("MDM_DB_JDBC_STR") + hostIp + Utils.getPropertiesValue("MDM_DB_JDBC_CONFIG_STR");
//		DBUrl = "jdbc:mysql://" + hostIp + "?useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";
		DBUser = name;
		DBPassword = pass;
		if (cpds == null) {
			cpds = new ComboPooledDataSource();
		}
		cpds.setJdbcUrl(DBUrl);
		cpds.setUser(DBUser);
		cpds.setPassword(DBPassword);
	}

	public static void createPool(){
		try {
			
			DBClassName = Utils.getPropertiesValue("jdbc.driver");
//			DBUrl = Utils.getPropertiesValue("jdbc.url");
//			DBUser = Utils.getPropertiesValue("jdbc.username");
//			DBPassword = Utils.getPropertiesValue("jdbc.password");

			if (cpds == null) {
				cpds = new ComboPooledDataSource();
			}
			cpds.setDriverClass(DBClassName);
			cpds.setJdbcUrl(DBUrl);
			cpds.setUser(DBUser);
			cpds.setPassword(DBPassword);
			// 设置连接池的最小连接数
			cpds.setMinPoolSize(Integer.valueOf(Utils.getPropertiesValue("MinPoolSize")).intValue());
			// 定义在从数据库获取新连接失败后重复尝试的次数
			cpds.setAcquireIncrement(Integer.valueOf(Utils.getPropertiesValue("AcquireIncrement")).intValue());
			// 设置连接池的最大连接数，mysql默认最大连接数为100
			cpds.setMaxPoolSize(Integer.valueOf(Utils.getPropertiesValue("MaxPoolSize")).intValue());
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3
			cpds.setAcquireIncrement(Integer.valueOf(Utils.getPropertiesValue("AcquireIncrement")).intValue());
			cpds.setMaxStatements(Integer.valueOf(Utils.getPropertiesValue("MaxStatements")).intValue());
			// 最大空闲时间，60秒内未使用则连接被丢弃。若为0则永不丢弃
			cpds.setMaxIdleTime(Integer.valueOf(Utils.getPropertiesValue("MaxIdleTime")).intValue());
			// 定义在从数据库获取新连接失败后重复尝试的次数。默认值: 30// ；小于等于0表示无限次
			cpds.setAcquireRetryAttempts(Integer.valueOf(Utils.getPropertiesValue("AcquireRetryAttempts")).intValue());
			// 当连接池连接耗尽时，客户端调用getConnection()后等待获取新连接的时间,如设为0则无限期等待
			cpds.setCheckoutTimeout(Integer.valueOf(Utils.getPropertiesValue("CheckoutTimeout")).intValue());
			// 关闭连接时，是否提交未提交的事务，默认为false，即关闭连接，回滚未提交的事务
			cpds.setAutoCommitOnClose(Boolean.valueOf(Utils.getPropertiesValue("AutoCommitOnClose")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static PooledConn getInstance() {
		if (instance == null) {
			instance = new PooledConn();
		}
		return instance;
	}

	public void close() {
		try {
			DataSources.destroy(cpds);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String sql) {
		System.out.println("[JDBC]"+cpds.getJdbcUrl());
		System.out.println("[USER]"+cpds.getUser());
		System.out.println("[PASS]"+cpds.getPassword());
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = cpds.getConnection();
			threadLocalConn.set(conn);
			st = conn.prepareStatement(sql);
			threadLocalSt.set(st);
			rs = st.executeQuery();
		} catch (SQLException e) {
			try {
				st.close();
				conn.close();
				threadLocalConn.remove();
				threadLocalSt.remove();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return rs;
	}

	public boolean executeInsert(String sql) {
		System.out.println("[JDBC]"+cpds.getJdbcUrl());
		System.out.println("[USER]"+cpds.getUser());
		System.out.println("[PASS]"+cpds.getPassword());
		boolean result = false;
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = cpds.getConnection();
			threadLocalConn.set(conn);
			st = conn.prepareStatement(sql);
			threadLocalSt.set(st);
			result = st.execute();
		} catch (Exception e) {
			try {
				st.close();
				conn.close();
				threadLocalConn.remove();
				threadLocalSt.remove();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
	}

	public int executeUpdate(String sql) {
		System.out.println("[JDBC]"+cpds.getJdbcUrl());
		System.out.println("[USER]"+cpds.getUser());
		System.out.println("[PASS]"+cpds.getPassword());
		int result = 0;
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = cpds.getConnection();
			threadLocalConn.set(conn);
			st = conn.prepareStatement(sql);
			threadLocalSt.set(st);
			result = st.executeUpdate();
		} catch (Exception e) {
			try {
				st.close();
				conn.close();
				threadLocalConn.remove();
				threadLocalSt.remove();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return result;
	}

	public void closed(ResultSet rs) {
		System.out.println("[JDBC]"+cpds.getJdbcUrl());
		System.out.println("[USER]"+cpds.getUser());
		System.out.println("[PASS]"+cpds.getPassword());
		try {
			if (rs != null) {
				rs.close();
			}
			PreparedStatement st = threadLocalSt.get();
			if (st != null) {
				st.close();
				threadLocalSt.remove();
			}
			Connection conn = threadLocalConn.get();
			if (conn != null) {
				conn.close();
				threadLocalConn.remove();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对数据库执行修改,是否返回ID
	 * @param sql SQL
	 * @param isResultId 是否返回ID
	 * @return
	 */
	public int executeUpdate(String sql, boolean isResultId) {
		System.out.println("[JDBC]"+cpds.getJdbcUrl());
		System.out.println("[USER]"+cpds.getUser());
		System.out.println("[PASS]"+cpds.getPassword());
		int id = 0;
		if (!isResultId) {
			return executeUpdate(sql);
		} else {
			ResultSet rs = null;
			Connection conn = null;
			PreparedStatement st = null;
			try {
				conn = cpds.getConnection();
				threadLocalConn.set(conn);
				st = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
				threadLocalSt.set(st);
				st.executeUpdate();
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					id = rs.getInt(1);
				}
			} catch (SQLException e) {
				try {
					st.close();
					conn.close();
					threadLocalConn.remove();
					threadLocalSt.remove();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return id;
	}

}
