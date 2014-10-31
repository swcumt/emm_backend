package com.nationsky.webapp.util;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作接口类，主要封装了 增、删、改、查等方法 <br>
 * JDBC方式
 */
public interface IBaseDao {
	
	/**
	 * 插入数据库
	 * 
	 * @param tableName 表名
	 * @param fields 字段名["name,pass,desc"]
	 * @param values 插入值["'admin','123','xxxxxx'"]
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	int insert(String tableName, String fields, String values) throws Exception;

	/**
	 * 插入数据库
	 * 
	 * @param tableName 表名
	 * @param fields 字段名["name,pass,desc"]
	 * @param values 插入值["'admin','123','xxxxxx'"]
	 * @param isResultId 是否返回ID,true/false
	 * @return 失败0/成功>0/新添加数据的自增ID
	 * @throws Exception 异常统一向页面抛
	 */
	int insert(String tableName, String fields, String values,boolean isResultId) throws Exception;

	/**
	 * 修改数据
	 * 
	 * @param tableName 表名
	 * @param settings sql语句中set后面的部分[name='user',pass='123',age='12']
	 * @param whereStr 条件,where后面的部分[1=1 and id=12 and name='user1']
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	int update(String tableName, String settings, String whereStr) throws Exception;

	/**
	 * 删除数据
	 * 
	 * @param tableName 表名
	 * @param whereStr 条件where后面的条件[id=1 and name='user1']
	 * @return 失败0/成功>0
	 * @throws Exception 异常统一向页面抛
	 */
	int delete(String tableName, String whereStr) throws Exception;

	/**
	 * 查询所有数据
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[ and id=1 and name='user1']
	 * @return 结果 List<Map<String, Object>>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	List<Map<String, Object>> select(String tableName, String fields, String whereStr) throws Exception;
	
	/**
	 * 查询所有数据
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[ and id=1 and name='user1']
	 * @param pageNow 当前页
	 * @param pageSize 每页显示多少条数据
	 * @return 结果 List<Map<String, Object>>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	List<Map<String, Object>> select(String tableName, String fields, String whereStr, Long pageNow, Long pageSize) throws Exception; 
	
	/**
	 * 查询单个对象数据
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[id=1 and name='user1']
	 * @return 结果 Map<String, Object>类型。Map的Key为大写字母
	 * @throws Exception 异常统一向页面抛
	 */
	Map<String, Object> selectOne(String tableName, String fields, String whereStr) throws Exception;


	/**
	 * 查询总行数
	 * 
	 * @param tableName 表明
	 * @param fields 字段名["name,pass,desc"]
	 * @param whereStr 条件[id=1 and name='user1']
	 * @return 结果 总行数。
	 * @throws Exception 异常统一向页面抛
	 */
	int count(String tableName, String fields, String whereStr) throws Exception ;
	
	public void alertTable(String sql);
}