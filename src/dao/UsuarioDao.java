package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.UsuarioModel;
import connection.SingleConnection;

public class UsuarioDao {
	
	private static Connection connection =  null;
	
	public UsuarioDao(){
		connection = SingleConnection.getConnection();
	}
	
	public List<UsuarioModel> listar()throws Exception{
		List<UsuarioModel> list = new ArrayList<UsuarioModel>();
		
		String sql = "select * from usuario";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		while(result.next()){
			UsuarioModel usuario = new UsuarioModel();
			
			usuario.setId(result.getLong("id"));
			usuario.setLogin(result.getString("login"));
			usuario.setSenha(result.getString("senha"));
			usuario.setNome(result.getString("nome"));
			usuario.setFone(result.getString("fone"));
			usuario.setCep(result.getString("cep"));
			usuario.setRua(result.getString("rua"));
			usuario.setBairro(result.getString("bairro"));
			usuario.setCidade(result.getString("cidade"));
			usuario.setEstado(result.getString("estado"));
			usuario.setIbge(result.getString("ibge"));
			usuario.setImgBase64(result.getString("imagem"));
			usuario.setContentType(result.getString("contentType"));
			usuario.setCurriculoBase64(result.getString("curriculo"));
			usuario.setContentTypeCurriculo(result.getString("contentTypeCurriculo"));
			list.add(usuario);
		}
		
		return list;
	}

	public void salvar(UsuarioModel usuario){
		try{
			String sql = "insert into usuario(login, senha, nome, fone, cep, rua, bairro, cidade, estado, ibge, imagem, contentType,"
					+ "curriculo, contentTypeCurriculo) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			
			insert.setString(1, usuario.getLogin());
			insert.setString(2, usuario.getSenha());
			insert.setString(3, usuario.getNome());
			insert.setString(4, usuario.getFone());
			insert.setString(5, usuario.getCep());
			insert.setString(6, usuario.getRua());
			insert.setString(7, usuario.getBairro());
			insert.setString(8, usuario.getCidade());
			insert.setString(9, usuario.getEstado());
			insert.setString(10, usuario.getIbge());
			insert.setString(11, usuario.getImgBase64());
			insert.setString(12, usuario.getContentType());
			insert.setString(13, usuario.getCurriculoBase64());
			insert.setString(14, usuario.getContentTypeCurriculo());

			
			insert.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean loginCadastrado(String login)throws Exception {
		String sql = "select count(1) as qtd from usuario where login='"+login+"'";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		if(result.next()){
			return result.getInt("qtd") <= 0;
		}else{
			return false;
		}
	}
		public boolean senhaCadastrada(String senha, Long id) throws Exception{
			String sql = "select count(1) as qtd from usuario where senha='"+senha+"' and id <>"+id;
			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet result = select.executeQuery();
			
			if(result.next()){
				return result.getInt("qtd") <= 0;
			}else{
				return false;
			}
		}
		public boolean senhaCadastradaSalvar(String senha) throws Exception{
			String sql = "select count(1) as qtd from usuario where senha='"+senha+"'";
			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet result = select.executeQuery();
			
			if(result.next()){
				return result.getInt("qtd") <= 0;
			}else{
				return false;
			}
		}
	
	public boolean loginCadastradoUpdate(String login, Long id) throws Exception{
		String sql = "select count(1) as qtd from usuario where login='"+login+"' and id <>"+id;
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		if(result.next()){
			return result.getInt("qtd") <= 0;
		}else{
			return false;
		}
	}
	
	public void deletar(Long id){
		try{
			String sql = "delete from usuario where id="+id;
			PreparedStatement delete = connection.prepareStatement(sql);
			
			delete.execute();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void atualizar(UsuarioModel usuario){
		try{
			String sql = "update usuario set login=?, senha=?, nome=?, fone=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, ibge=?,"
					+ "imagem=?, contentType=?, curriculo=?, contentTypeCurriculo=? where id="+usuario.getId();
			PreparedStatement update = connection.prepareStatement(sql);
			
			update.setString(1, usuario.getLogin());
			update.setString(2, usuario.getSenha());
			update.setString(3, usuario.getNome());
			update.setString(4, usuario.getFone());
			update.setString(5, usuario.getCep());
			update.setString(6, usuario.getRua());
			update.setString(7, usuario.getBairro());
			update.setString(8, usuario.getCidade());
			update.setString(9, usuario.getEstado());
			update.setString(10, usuario.getIbge());
			update.setString(11, usuario.getImgBase64());
			update.setString(12, usuario.getContentType());
			update.setString(13, usuario.getCurriculoBase64());
			update.setString(14, usuario.getContentTypeCurriculo());
			
			update.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public UsuarioModel consultar(Long id) throws Exception {
		UsuarioModel usuario = new UsuarioModel();
		
		String sql = "select * from usuario where id="+id;
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		while(result.next()){
			usuario.setId(result.getLong("id"));
			usuario.setLogin(result.getString("login"));
			usuario.setSenha(result.getString("senha"));
			usuario.setNome(result.getString("nome"));
			usuario.setFone(result.getString("fone"));
			usuario.setCep(result.getString("cep"));
			usuario.setRua(result.getString("rua"));
			usuario.setBairro(result.getString("bairro"));
			usuario.setCidade(result.getString("cidade"));
			usuario.setEstado(result.getString("estado"));
			usuario.setIbge(result.getString("ibge"));
			usuario.setImgBase64(result.getString("imagem"));
			usuario.setContentType(result.getString("contentType"));
			usuario.setCurriculoBase64(result.getString("curriculo"));
			usuario.setContentTypeCurriculo(result.getString("contentTypeCurriculo"));
			
		}
		return usuario;
	}

}
