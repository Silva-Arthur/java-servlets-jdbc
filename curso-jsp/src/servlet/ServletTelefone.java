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
import beans.Telefones;
import dao.DaoTelefones;
import dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class ServletTelefone extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario dao = new DaoUsuario();

	private DaoTelefones daoTelefones = new DaoTelefones();

	public ServletTelefone() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			BeanCursoJsp usuario = new BeanCursoJsp();
			String acao = request.getParameter("acao");		
			String msg = "";
			
			if (acao != null && !acao.isEmpty()) {
				if (acao.equalsIgnoreCase("delete")) {
					String id = request.getParameter("id");
					if (!id.isEmpty() && id != null) {
						daoTelefones.delete(id);		
						usuario = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
						msg += "Excluído com sucesso!";
					}
				} else {
					String user = request.getParameter("user");
					usuario = dao.consultar(user);
					request.getSession().setAttribute("userEscolhido", usuario);
				}
			}

			request.setAttribute("msg", msg);
			request.setAttribute("listaItens", daoTelefones.listar(usuario.getId()));
			RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");			
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		BeanCursoJsp usuario = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
		String numero = request.getParameter("numero");
		String tipo = request.getParameter("tipo");
		String msg = "";
		String acao = request.getParameter("acao");	
		
		if (acao != null && acao.equalsIgnoreCase("voltar")) {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
			try {
				request.setAttribute("usuarios", new DaoUsuario().listar());
				view.forward(request, response);
			} catch (ServletException | IOException | SQLException e) {
				e.printStackTrace();
			}
		} else {
			if (numero != null && !numero.isEmpty()) {
				
				Telefones telefone = new Telefones();
				
				telefone.setNumero(numero);
				telefone.setTipo(tipo);
				telefone.setUsuario(usuario.getId());
				
				msg = "Salva com sucesso!";
				
				daoTelefones.salvar(telefone);		
			} else {
				msg = "Informe o numero do telefone!";
			}
			
			try {
				RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
				request.setAttribute("listaItens", daoTelefones.listar(usuario.getId()));
				request.getSession().setAttribute("userEscolhido", usuario);
				request.setAttribute("msg", msg);
				view.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
	}

}
