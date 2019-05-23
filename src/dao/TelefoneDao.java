package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.TelefoneModel;
import connection.SingleConnection;

public class TelefoneDao {

	private static Connection connection;

	public TelefoneDao() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(TelefoneModel telefone) {
		try {
			String sql = "insert into telefones(numero, tipo,usuario) values(?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);

			insert.setString(1, telefone.getNumero());
			insert.setString(2, telefone.getTipo());
			insert.setLong(3, telefone.getUsuario());

			insert.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<TelefoneModel> listar(Long usuarioId) throws Exception {
		List<TelefoneModel> list = new ArrayList<TelefoneModel>();

		String sql = "select * from telefones where usuario = " + usuarioId;
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();

		while (result.next()) {
			TelefoneModel telefone = new TelefoneModel();

			telefone.setId(result.getLong("id"));
			telefone.setNumero(result.getString("numero"));
			telefone.setTipo(result.getString("tipo"));
			telefone.setUsuario(result.getLong("usuario"));

			list.add(telefone);
		}
		return list;
	}

	public void deletar(Long telefoneId) {
		try {
			String sql = "delete from telefones where id=" + telefoneId;
			PreparedStatement delete = connection.prepareStatement(sql);
			
			delete.execute();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean TelefoneCadastrado(String telefone)throws Exception{
		String sql = "select count(1) as qtd from telefones where numero='"+telefone+"'";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		if(result.next()){
			return result.getInt("qtd") <= 0;
		}else{
			return false;
		}
	}
}
