package com.yc.net.back;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDAO {
	
	DbHelper db=new DbHelper();
	
	public int update(String cardno,float money) throws SQLException{
		
		String sql="update account set balance=balance+? where accountid=?";
		return db.update(sql, money,cardno);
	}
	
	public int register(String uname,String cardno,String pwd) throws SQLException{
		String sql="insert into user values(?,?,?)";
		return db.update(sql,uname, cardno,pwd);
	}
	public int withdraw(String cardno,float money) throws SQLException{
		
		String sql="update account set balance=balance-? where accountid=?";
		return db.update(sql, money,cardno);
	}
	
	public int transfer(String cardno01,String cardno02,float money) throws SQLException{
		String sql01="update account set balance=balance-? where accountid=?";
		String sql02="update account set balance=balance+? where accountid=?";
		List<String> sqls=new ArrayList<String>();
		
		List<List<Object>> params=new ArrayList<List<Object>>();
		List<Object> param01=new ArrayList<Object>();
		List<Object> param02=new ArrayList<Object>();
		param01.add(money);
		param01.add(cardno01);
		param02.add(money);
		param02.add(cardno02);
		sqls.add(sql01);
		params.add(param01);
		sqls.add(sql02);
		params.add(param02);
		System.out.println(sqls.toString());
		return db.update(sqls, params);
	}
}
