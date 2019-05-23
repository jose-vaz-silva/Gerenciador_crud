package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.ProdutosModel;
import connection.SingleConnection;

public class ProdutosDao {
	
	private static Connection connection;
	
	public ProdutosDao(){
		connection = SingleConnection.getConnection();
	}
	
	public void salvar(ProdutosModel produtos){
		try{
			String sql = "insert into produtos(nome, quantidade, valor) values(?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			
			insert.setString(1, produtos.getNome());
			insert.setInt(2, produtos.getQuantidade());
			insert.setString(3, produtos.getValor());
			
			insert.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<ProdutosModel> listar() throws Exception{
		List<ProdutosModel> list = new ArrayList<ProdutosModel>();
		
		String sql = "select * from produtos";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		while(result.next()){
			ProdutosModel produtos = new ProdutosModel();
			
			produtos.setId(result.getLong("id"));
			produtos.setNome(result.getString("nome"));
			produtos.setQuantiade(result.getInt("quantidade"));
			produtos.setValor(result.getString("valor"));
			
			list.add(produtos);
		}
		return list;
	}
	
	public void deletar(Long id){
		try{
			String sql = "delete from produtos where id="+id;
			PreparedStatement delete = connection.prepareStatement(sql);
			
			delete.execute();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ProdutosModel consultar(Long id) throws Exception{
		ProdutosModel produtos = new ProdutosModel();
		
		String sql = "select * from produtos where id="+id;
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		while(result.next()){
			produtos.setId(result.getLong("id"));
			produtos.setNome(result.getString("nome"));
			produtos.setQuantiade(result.getInt("quantidade"));
			produtos.setValor(result.getString("valor"));
		}
		return produtos;
	}
	
	public void atualizar(ProdutosModel produtos){
		try{
			String sql = "update produtos set nome=?, quantidade=?, valor=? where id="+produtos.getId();
			PreparedStatement update = connection.prepareStatement(sql);
			
			update.setString(1, produtos.getNome());
			update.setInt(2, produtos.getQuantidade());
			update.setString(3, produtos.getValor());
			
			update.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean produtoCadastrado(String nome)throws Exception{
		String sql = "select count(1) as qtd from produtos where nome='"+nome+"'";
		PreparedStatement select = connection.prepareStatement(sql);
		ResultSet result = select.executeQuery();
		
		if(result.next()){
			return result.getInt("qtd") <= 0;
		}else{
			return false;
		}
	}
		
		public boolean produtoCadastradoUpdate(String nome, Long id)throws Exception{
			String sql = "select count(1) as qtd from produtos where nome='"+nome+"' and id<>"+id;
			PreparedStatement select = connection.prepareStatement(sql);
			ResultSet result = select.executeQuery();
			
			if(result.next()){
				return result.getInt("qtd") <= 0;
			}else{
				return false;
			}
		
	}
}
