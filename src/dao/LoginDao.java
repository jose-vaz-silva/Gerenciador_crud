package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connection.SingleConnection;

public class LoginDao {
	private static Connection connection = null;
		
	
	public LoginDao(){
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws Exception{
		String sql = "select * from usuario where login='"+login+"' and senha='"+senha+"'";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		if(result.next()){
			return true;
		}else{
			return false;
		}
	}
}
