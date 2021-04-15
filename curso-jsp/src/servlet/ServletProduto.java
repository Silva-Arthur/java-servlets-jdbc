package servlet;

import java.io.IOException;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.BeanProduto;
import dao.DaoProduto;

@WebServlet("/salvarProduto")
public class ServletProduto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DaoProduto dao = new DaoProduto();

	public ServletProduto() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String id = request.getParameter("id");
		boolean listar = true;

		if (id != null && !id.isEmpty() && acao != null && !acao.isEmpty()) {
			if (acao.equalsIgnoreCase("editar")) {
				/*EDITAR*/
				request.setAttribute("obj", dao.consultar(id));
				listar = false;
			} else if (acao.equalsIgnoreCase("excluir")) {
				/*ECLUIR*/
				dao.delete(id);
				listar = true;
			}
		}

		if (listar) {
			request.setAttribute("listaItens", dao.listar());			
		}
		
		/*REDIRECIONAR TELA*/
		try {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/cadastroProduto.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String msg = "";
		BeanProduto produto = new BeanProduto();
		boolean podeInserir = true;

		if (acao == null || acao.isEmpty()) {
			String id = request.getParameter("id").isEmpty() ? null : request.getParameter("id");
			String nome = request.getParameter("nome");
			String quantidade = request.getParameter("quantidade");
			String valor = request.getParameter("valor");
			
			produto.setId(id != null ? Long.parseLong(id) : null);
			produto.setQuantidade(quantidade != null && !quantidade.isEmpty() ? Double.parseDouble(quantidade) : 0.0);
			produto.setValor(valor != null && !valor.isEmpty() ? Double.parseDouble(valor) : 0.0);
			produto.setNome(nome);
			
			if (nome.isEmpty() || nome == null) {
				podeInserir = false;
				msg += "Nome deve ser informado!";
			} else if (produto.getQuantidade() <= 0.0) {
				podeInserir = false;
				msg += "Quantidade deve ser informada!";
			} else if (produto.getValor() <= 0.0) {
				podeInserir = false;
				msg += "Valor deve ser informado!";
			}
			
			if (podeInserir) {
				if (id == null || id.isEmpty()) {
					/* NOVO */
					msg = dao.validarProduto(nome) ? null : "Já existe um produto com o mesmo nome!";
					
					if (msg == null || msg.isEmpty()) {
						dao.salvar(produto);
					}
				} else {
					/* ATUALIZAR */
					msg = dao.validarProdutoUpdate(nome, id) ? null : "Já existe um produto com o mesmo nome!";
					
					if (msg == null || msg.isEmpty()) {
						dao.atualizar(produto);
					}
				}				
			}
		}

		/* REDIRECIONAR TELA */
		try {
			RequestDispatcher view = request.getRequestDispatcher("/cadastroProduto.jsp");
			request.setAttribute("listaItens", dao.listar());
			if (msg != null && !msg.isEmpty()) {
				request.setAttribute("msg", msg);
				request.setAttribute("obj", produto);
			}
			view.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
