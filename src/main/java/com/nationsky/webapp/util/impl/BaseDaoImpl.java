package com.nationsky.webapp.util.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nationsky.model.CodeServerUrl;
import com.nationsky.webapp.controller.PooledConn;
import com.nationsky.webapp.util.IBaseDao;

/**
 * 数据库操作类，主要封装了 增、删、改、查等方法 <br>
 * JDBC方式
 * @author LeiPeng
 * 
 */
public class BaseDaoImpl implements IBaseDao{
	private PooledConn pooledConn;
	public BaseDaoImpl(CodeServerUrl codeServerUrl){
		pooledConn.config(codeServerUrl.getUrl(), codeServerUrl.getUsername(), codeServerUrl.getPassword());
		pooledConn.createPool();
		pooledConn = pooledConn.getInstance();
	}
	
	/**
	 * 插入数据库
	 * 
	 * @param tableName 表名
	 * @param fields 字段名["name,pass,desc"]
	 * @param values 插入值["'admin','123','xxxxxx'"]
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	public int insert(String tableName, String fields, String values)
			throws Exception {
		int result = 0;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("insert into ").append(tableName).append(" (").append(
					fields).append(") ").append(" values(").append(values)
					.append(")");
			System.out.println("[SQL]：" + sql);
			result = pooledConn.executeUpdate(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行添加sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.close();
		}
		return result;
	}

	/**
	 * 插入数据库,是否返回ID
	 * 
	 * @param tableName 表名
	 * @param fields 字段名["name,pass,desc"]
	 * @param values 插入值["'admin','123','xxxxxx'"]
	 * @param isResultId 是否返回ID,true/false
	 * @return 失败0/成功>0/新添加数据的自增ID
	 * @throws Exception 异常统一向页面抛
	 */
	public int insert(String tableName, String fields, String values,
			boolean isResultId) throws Exception {
		int result = 0;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("insert into ").append(tableName).append(" (").append(
					fields).append(") ").append(" values(").append(values)
					.append(")");
			System.out.println("[SQL]：" + sql);
			result = pooledConn.executeUpdate(sql.toString(), isResultId);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行添加sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return result;
	}

	/**
	 * 修改数据
	 * 
	 * @param tableName 表名
	 * @param settings sql语句中set后面的部分[name='user',pass='123',age='12']
	 * @param whereStr 条件,where后面的部分[1=1 and id=12 and name='user1']
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	public int update(String tableName, String settings, String whereStr)
			throws Exception {
		int result = 0;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("update ").append(tableName).append(" set ").append(
					settings);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where ").append(whereStr);
			}
			System.out.println("[SQL]：" + sql);
			result = pooledConn.executeUpdate(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行修改sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return result;
	}

	/**
	 * 删除数据
	 * 
	 * @param tableName 表名
	 * @param whereStr 条件where后面的条件[id=1 and name='user1']
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	public int delete(String tableName, String whereStr) throws Exception {
		StringBuilder sql = new StringBuilder();
		int result = 0;
		ResultSet rs = null;
		try {
			sql.append("delete from ").append(tableName);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where ").append(whereStr);
			}
			System.out.println("[SQL]：" + sql);
			result = pooledConn.executeUpdate(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行删除sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return result;
	}

	/**
	 * 查询所有数据
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[ and id=1 and name='user1']
	 * @return 结果 List<Map<String, Object>>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	public  List<Map<String, Object>> select(String tableName, String fields,
			String whereStr) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		try {
			sql.append("select ").append(fields).append(" from ").append(
					tableName);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where 1=1 ").append(whereStr);
			}

			System.out.println("[SQL]：" + sql);
			  rs = pooledConn.executeQuery(sql.toString());

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = rsmd.getColumnLabel(i + 1);
					String columnClassName = rsmd.getColumnClassName(i + 1);
					if (columnClassName.equals("java.sql.Timestamp")) {
						if (rs.getObject(columnName) == null) {
							map.put(columnName, null);
						} else {
//							map.put(columnName, ((Timestamp) rs
//									.getObject(columnName)).getTime());
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String formatstr = format.format(((Timestamp) rs
									.getObject(columnName)).getTime());
							map.put(columnName,formatstr);
						}
					} else if (columnClassName.equals("java.sql.Date")) {
						map.put(columnName, rs.getString(columnName));
					} else {
						map.put(columnName, rs.getObject(columnName));
					}
				}
				list.add(map);
			}
			
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("执行查看sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return list;
	}

	/**
	 * 分页查询所有数据
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[ and id=1 and name='user1']
	 * @param pageNow 当前页
	 * @param pageSize 每页显示多少条数据
	 * @return 结果 List<Map<String, Object>>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	public List<Map<String, Object>> select(String tableName, String fields,
			String whereStr, Long pageNow, Long pageSize) throws Exception {
		Map<String, Object> map = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		try {
			sql.append("select ").append(fields).append(" from ").append(
					tableName);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where 1=1 ").append(whereStr);
			}
			if (pageNow != null && pageNow > 0) {
				Long start = (pageNow - 1) * pageSize;
				sql.append(" limit ").append(start + "," + pageSize);
			}
			System.out.println("[SQL]：" + sql);
			  rs = pooledConn.executeQuery(sql.toString());

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			while (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = rsmd.getColumnLabel(i + 1);
					String columnClassName = rsmd.getColumnClassName(i + 1);
					if (columnClassName.equals("java.sql.Timestamp")) {
						if (rs.getObject(columnName) == null) {
							map.put(columnName, null);
						} else {
							map.put(columnName, ((Timestamp) rs
									.getObject(columnName)).getTime());
						}
					} else if (columnClassName.equals("java.sql.Date")) {
						map.put(columnName, rs.getString(columnName));
					} else {
						map.put(columnName, rs.getObject(columnName));
					}
				}
				list.add(map);
			}
		} catch (Exception e) {
//			e.printStackTrace();
			throw new Exception("执行查看sql语句：" + sql.toString() + "错误!");
		} finally {
			
			pooledConn.closed(rs);
		}
		return list;
	}

	/**
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[id=1 and name='user1']
	 * @return 结果 Map<String, Object>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	@SuppressWarnings("static-access")
	public Map<String, Object> selectOne(String tableName, String fields,
			String whereStr) throws Exception {
		Map<String, Object> map = null;
		StringBuilder sql = new StringBuilder();
		ResultSet rs = null;
		try {
			sql.append("select ").append(fields).append(" from ").append(
					tableName);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where 1=1 ").append(whereStr);
			}
			System.out.println("[SQL]：" + sql);
			  rs = pooledConn.executeQuery(sql.toString());

			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();

			if (rs.next()) {
				map = new HashMap<String, Object>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = rsmd.getColumnLabel(i + 1);
					String columnClassName = rsmd.getColumnClassName(i + 1);

					if (columnClassName.equals("java.sql.Timestamp")) {
						if (rs.getObject(columnName) == null) {
							map.put(columnName, null);
						} else {
							map.put(columnName, ((Timestamp) rs
									.getObject(columnName)).getTime());
//							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//							String formatstr = format.format(((Timestamp) rs
//									.getObject(columnName)).getTime());
//							map.put(columnName,formatstr);
						}
					} else if (columnClassName.equals("java.sql.Date")) {
						map.put(columnName, rs.getString(columnName));
					} else {
						map.put(columnName, rs.getObject(columnName));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行查看sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return map;
	}

	/**
	 * 查询总行数
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[id=1 and name='user1']
	 * @return 结果 总行数。
	 * @throws Exception 异常统一向页面抛
	 */
	@SuppressWarnings("static-access")
	public int count(String tableName, String fields, String whereStr)
			throws Exception {
		int count = 0;
		ResultSet rs = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("select count(").append(fields).append(") from ")
					.append(tableName);
			if ((whereStr != null) && (!"".equals(whereStr))) {
				sql.append(" where 1=1 ").append(whereStr);
			}
			System.out.println("[SQL]：" + sql);
			  rs = pooledConn.executeQuery(sql.toString());
			if (rs.next()) {
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("执行查看sql语句：" + sql.toString() + "错误!");
		} finally {
			pooledConn.closed(rs);
		}
		return count;
	}
	
	public void alertTable(String sql) {
		boolean rs = false;
		try {
			System.out.println("[SQL]：" + sql);
			  rs = pooledConn.executeInsert(sql.toString());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new Exception("执行查看sql语句：" + sql.toString() + "错误!");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}