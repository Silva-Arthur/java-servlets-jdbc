package servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String acao = request.getParameter("acao");
			String user = request.getParameter("user");
			if (acao.equalsIgnoreCase("delete")) {
				daoUsuario.delete(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("editar")) {
				BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("user", beanCursoJsp);
				view.forward(request, response);
			} else if (acao.equalsIgnoreCase("listartodos")) {
				/*
				 * Chmaou no meu via servlet, e aqui no dispatcher eu indico para qual jsp quero
				 * direcionar
				 */
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean salvar = true;
		String acao = request.getParameter("acao");
		String msg = "";
		boolean podeInserir = true;

		if (acao != null && acao.equalsIgnoreCase("reset")) {
			try {
				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String id = request.getParameter("id");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");

			BeanCursoJsp usuario = new BeanCursoJsp();
			usuario.setLogin(login);
			usuario.setSenha(senha);
			usuario.setNome(nome);
			usuario.setFone(fone);
			usuario.setCep(cep);
			usuario.setRua(rua);
			usuario.setBairro(bairro);
			usuario.setCidade(cidade);
			usuario.setEstado(estado);
			usuario.setIbge(ibge);
			
			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
			
			if(login == null || login.isEmpty()) {
				podeInserir = false;
				msg += "Login deve ser informado!";
			} else if (senha == null || senha.isEmpty()) {
				podeInserir = false;
				msg += "Senha deve ser informada!";
			} else if (nome == null || nome.isEmpty()) {
				podeInserir = false;
				msg += "Nome deve ser informado!";
			} else if (fone == null || fone.isEmpty()) {
				podeInserir = false;
				msg += "Telefone deve ser informado!";
			}  
			
			try {
				if (podeInserir) {					
					if (id == null || id.isEmpty()) {
						salvar = daoUsuario.validarLogin(login);
						if (!salvar) {
							msg += "Usuário já existe com o mesmo login!\n";
						}
						salvar = daoUsuario.validarSenha(senha);
						if (!salvar) {
							msg += "Usuário já existe com a mesma senha!";
						}
						
						if (salvar) {
							daoUsuario.salvar(usuario);
						}
					} else {
						salvar = daoUsuario.validarLoginUpdate(login, id);
						if (!salvar) {
							msg += "Usuário já existe com o mesmo login!\n";
						}
						salvar = daoUsuario.validarSenhaUpdate(senha, id);
						if (!salvar) {
							msg += "Usuário já existe com a mesma senha!";
						}
						
						if (salvar) {
							daoUsuario.atualizar(usuario);
						}
					}					
				}
				
				if (msg != null && !msg.isEmpty()) {
					request.setAttribute("user", usuario);
				} else {
					msg += "Salvo com sucesso!";
				}
				request.setAttribute("msg", msg);

				RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
				request.setAttribute("usuarios", daoUsuario.listar());
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
