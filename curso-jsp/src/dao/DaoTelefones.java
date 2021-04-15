package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Telefones;
import connection.SingleConnection;

public class DaoTelefones {

	private Connection connection;

	public DaoTelefones() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Telefones telefone) {
		try {
			String sql = "insert into telefones (numero, tipo, usuario) values (?,?,?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, telefone.getNumero());
			stmt.setString(2, telefone.getTipo());
			stmt.setLong(3, telefone.getUsuario());

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

	public List<Telefones> listar(Long user) {

		List<Telefones> lista = new ArrayList<Telefones>();

		String sql = "select * from telefones where usuario = " + user;
		PreparedStatement stmt;
		try {
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Telefones telefone = new Telefones();

				telefone.setId(rs.getLong("id"));
				telefone.setNumero(rs.getString("numero"));
				telefone.setTipo(rs.getString("tipo"));
				telefone.setUsuario(rs.getLong("usuario"));

				lista.add(telefone);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	public void delete(String id) {
		try {
			String sql = "delete from telefones where id = " + id;
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

}
