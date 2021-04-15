package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.BeanCursoJsp;
import connection.SingleConnection;

public class DaoUsuario {

	private Connection connection;

	public DaoUsuario() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(BeanCursoJsp usuario) {
		try {
			String sql = "insert into usuario (login, senha, nome, fone, cep, rua, bairro, cidade, estado, ibge) "
					+ "values (?,?,?,?,?,?,?,?,?,?)";
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
			insert.execute();
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

	public List<BeanCursoJsp> listar() throws SQLException {
		List<BeanCursoJsp> listar = new ArrayList<BeanCursoJsp>();

		String sql = "select * from usuario";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultSet = statement.executeQuery();

		while (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();

			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setFone(resultSet.getString("fone"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));
			
			listar.add(beanCursoJsp);
		}

		return listar;
	}

	public void delete(String id) {
		try {
			String sql = "delete from usuario where id = '" + id + "'";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.execute();
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

	public BeanCursoJsp consultar(String id) throws Exception {
		String sql = "select * from usuario where id = " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			BeanCursoJsp beanCursoJsp = new BeanCursoJsp();
			
			beanCursoJsp.setLogin(resultSet.getString("login"));
			beanCursoJsp.setSenha(resultSet.getString("senha"));
			beanCursoJsp.setId(resultSet.getLong("id"));
			beanCursoJsp.setNome(resultSet.getString("nome"));
			beanCursoJsp.setFone(resultSet.getString("fone"));
			beanCursoJsp.setCep(resultSet.getString("cep"));
			beanCursoJsp.setRua(resultSet.getString("rua"));
			beanCursoJsp.setBairro(resultSet.getString("bairro"));
			beanCursoJsp.setCidade(resultSet.getString("cidade"));
			beanCursoJsp.setEstado(resultSet.getString("estado"));
			beanCursoJsp.setIbge(resultSet.getString("ibge"));

			return beanCursoJsp;
		}

		return null;
	}

	public boolean validarLogin(String login) throws Exception {
		String sql = "select count(*) as qtd from usuario where login = '" + login + "'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			return resultSet.getLong("qtd") <= 0;
		}
		return false;
	}

	public void atualizar(BeanCursoJsp usuario) {
		try {
			String sql = "update usuario set login=?, senha=?, nome=?, fone=?, cep=?, rua=?, bairro=?, cidade=?, estado=?, ibge=? where id=" + usuario.getId();
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, usuario.getLogin());
			statement.setString(2, usuario.getSenha());
			statement.setString(3, usuario.getNome());
			statement.setString(4, usuario.getFone());
			statement.setString(5, usuario.getCep());
			statement.setString(6, usuario.getRua());
			statement.setString(7, usuario.getBairro());
			statement.setString(8, usuario.getCidade());
			statement.setString(9, usuario.getEstado());
			statement.setString(10, usuario.getIbge());
			statement.executeUpdate();
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

	public boolean validarLoginUpdate(String login, String id) throws Exception {
		String sql = "select count(*) as qtd from usuario where login = '" + login + "' and id <> " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			return resultSet.getLong("qtd") <= 0;
		}
		return false;
	}

	public boolean validarSenha(String senha) throws Exception {
		String sql = "select count(*) as qtd from usuario where senha = '" + senha + "'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			return resultSet.getLong("qtd") <= 0;
		}
		return false;
	}

	public boolean validarSenhaUpdate(String senha, String id) throws Exception {
		String sql = "select count(*) as qtd from usuario where senha = '" + senha + "' and id <> " + id;
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			return resultSet.getLong("qtd") <= 0;
		}
		return false;
	}

}
