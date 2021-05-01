package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.codec.binary.Base64;

import beans.BeanCursoJsp;
import dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private DaoUsuario daoUsuario = new DaoUsuario();

	public Usuario() {
		super();
	}

	@SuppressWarnings("static-access")
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
			} else if (acao.equalsIgnoreCase("download")) {
				BeanCursoJsp usuario = daoUsuario.consultar(user);
				if (usuario != null) {
					String tipo = request.getParameter("tipo");
					
					if (tipo != null && !tipo.isEmpty()) {
						
						String contentType = "";
						byte[] fileBytes = null;
						
						if (tipo.equalsIgnoreCase("imagem")) {
							contentType = usuario.getContentType();
							/*Converte a base64 da imagem do banco para byte[]*/
							fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());
						} else if (tipo.equalsIgnoreCase("curriculo")) {
							contentType = usuario.getContentTypeCurriculo();
							/*Converte a base64 do pdf do banco para byte[]*/
							fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
						}
						
						/*Setando no response o arquiv que vamos utilizar*/
						response.setHeader("Content-Disposition", 
								"attachment;filename=arquivo." + contentType.split("\\/")[1]);
												
						/*Coloca os bytes em um objeto de entrada para processar*/
						InputStream is = new ByteArrayInputStream(fileBytes);
						
						/*Início da resposta para o navegador*/
						int read = 0;
						byte[] bytes = new byte[1024];
						OutputStream os = response.getOutputStream();
						
						while ((read = is.read(bytes)) != -1) {
							os.write(bytes, 0, read);
						}
						
						/*Finaliza*/
						os.flush();
						
						/*Fecha o fluxo, o processo*/
						os.close();						
					}					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
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
			
			usuario.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			
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
				
				/*INICIO - File upload de imagens e pdf*/
				if (ServletFileUpload.isMultipartContent(request)) {
					
					/*FOTO*/
					Part imagemFoto = request.getPart("foto");
					if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {
							String fotoBase64 = new Base64().
									encodeBase64String(converteStremParaByte(imagemFoto.getInputStream()));
							
							usuario.setFotoBase64(fotoBase64);
							usuario.setContentType(imagemFoto.getContentType());
					} 
					
					/*CURRICULO*/
					Part curriculoPdf = request.getPart("curriculo");
					if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
							String curriculoBase64 = new Base64().encodeBase64String(
									converteStremParaByte(curriculoPdf.getInputStream()));
							
							usuario.setCurriculoBase64(curriculoBase64);
							usuario.setContentTypeCurriculo(curriculoPdf.getContentType());							
						
					}
				}
				/*FIM - File upload de imagens e pdf*/
				
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
							if (usuario.getFotoBase64() == null || usuario.getFotoBase64().isEmpty()) {
								BeanCursoJsp usuarioAux = daoUsuario.consultarFoto(id);
								usuario.setFotoBase64(usuarioAux.getFotoBase64());
								usuario.setContentType(usuarioAux.getContentType());
							}
							if (usuario.getCurriculoBase64() == null || usuario.getCurriculoBase64().isEmpty()) {
								BeanCursoJsp usuarioAux = daoUsuario.consultarCurriculo(id);
								usuario.setCurriculoBase64(usuarioAux.getCurriculoBase64());
								usuario.setContentTypeCurriculo(usuarioAux.getContentTypeCurriculo());
							}
							
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
	
	/*Converte a entreda de dados da img para um array de bytes*/
	private byte[] converteStremParaByte(InputStream imagem) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();
		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		
		return baos.toByteArray();
	}

}
