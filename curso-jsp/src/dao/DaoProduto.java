package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanProduto;
import connection.SingleConnection;

public class DaoProduto {
	
	private Connection connection;
	
	public DaoProduto() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanProduto produto) {
		try {
			String sql = "insert into produto (nome, quantidade, valor) values (?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			
			stmt.execute();
			connection.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public List<BeanProduto> listar() {
		
		List<BeanProduto> lista = new ArrayList<BeanProduto>();
		
		String sql = "select * from produto";
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				BeanProduto produto = new BeanProduto();
				
				produto.setId(rs.getLong("id"));
				produto.setNome(rs.getString("nome"));
				produto.setQuantidade(rs.getLong("quantidade"));
				produto.setValor(rs.getDouble("valor"));
				
				lista.add(produto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}

	public void delete(String id) {
		try {
			String sql = "delete from produto where id = " + id;
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.execute();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	public BeanProduto consultar(String id) {
		BeanProduto produto = new BeanProduto();
		try {
			
			String sql = "select * from produto where id = " + id;
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {				
				produto.setId(rs.getLong("id"));
				produto.setNome(rs.getString("nome"));
				produto.setQuantidade(rs.getLong("quantidade"));
				produto.setValor(rs.getDouble("valor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produto;
	}

	public void atualizar(BeanProduto produto) {
		try {
			String sql = "update produto set nome = ?, quantidade = ?, valor = ? where id = " + produto.getId();
			
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, produto.getNome());
			stmt.setDouble(2, produto.getQuantidade());
			stmt.setDouble(3, produto.getValor());
			
			stmt.execute();
			connection.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public boolean validarProduto(String nome)  {
		String sql = "select count(*) as qtd from produto where nome = '" + nome + "'";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getLong("qtd") <= 0;			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean validarProdutoUpdate(String nome, String id) {
		String sql = "select count(*) as qtd from produto where nome = '" + nome + "' and id <> " + id;
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				return resultSet.getLong("qtd") <= 0;			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
