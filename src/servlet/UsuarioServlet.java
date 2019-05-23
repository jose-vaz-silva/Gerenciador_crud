package servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.UsuarioModel;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import dao.UsuarioDao;

@WebServlet("/UsuarioServlet")
@MultipartConfig
public class UsuarioServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	UsuarioDao usuarioDao = new UsuarioDao();
	UsuarioModel usuario = new UsuarioModel();

	public UsuarioServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		String id = request.getParameter("id");

		if (acao.equalsIgnoreCase("listar")) {
			limparCampos();
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.listar());
				request.setAttribute("user", usuario);
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("delete")) {
			usuarioDao.deletar(Long.parseLong(id));
			limparCampos();
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.listar());
				request.setAttribute("user", usuario);
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("edit")) {
			try {
				UsuarioModel usuario = usuarioDao.consultar(Long.parseLong(id));

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("user", usuario);
				request.setAttribute("usuarios", usuarioDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (acao.equalsIgnoreCase("download")){
			try{
				UsuarioModel usuario = usuarioDao.consultar(Long.parseLong(id));
				
				if(usuario != null){
					response.setHeader("content-disposition", "attachment;filename=arquivo."+usuario.getContentType().split("\\/")[1]);
					
					byte[] fotoByteArray = new Base64().decodeBase64(usuario.getImgBase64());
					InputStream is = new ByteArrayInputStream(fotoByteArray);
					
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();
					
					while((read = is.read(bytes)) != -1 ){
						os.write(bytes, 0, read);
					}
					
					os.flush();
					os.close();
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if (acao.equalsIgnoreCase("downloadPdf")){
			try{
				UsuarioModel usuario = usuarioDao.consultar(Long.parseLong(id));
				if(usuario != null){
					response.setHeader("content-disposition", "attachment;filename="
						+usuario.getNome()+"."+usuario.getContentTypeCurriculo().split("\\/")[1]);
					
					byte[] curriculoByteArray = new Base64().decodeBase64(usuario.getCurriculoBase64());
					InputStream is = new ByteArrayInputStream(curriculoByteArray);
					
					int read = 0;
					byte[] bytes = new byte[1024];
					OutputStream os = response.getOutputStream();
					
					while((read = is.read(bytes))!= -1){
						os.write(bytes, 0, read);
					}
					os.flush();
					os.close();
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (acao != null && acao.equalsIgnoreCase("cancel")) {
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.listar());
				request.setAttribute("user", usuario);
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String id = request.getParameter("id");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String nome = request.getParameter("nome");
			String fone = request.getParameter("fone");
			String cep = request.getParameter("cep");
			String rua = request.getParameter("rua");
			String bairro = request.getParameter("bairro");
			String cidade = request.getParameter("cidade");
			String estado = request.getParameter("estado");
			String ibge = request.getParameter("ibge");

			usuario.setId(!id.isEmpty() ? Long.parseLong(id) : null);
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

			boolean podeInserir = true;
			try {

				/* Inicio file upload imagem e pdf. */
				if (ServletFileUpload.isMultipartContent(request)) {
					Part imagem = request.getPart("foto");
					Part curriculo = request.getPart("curriculo");

					/*String imagemBase64 = new Base64()
							.encodeBase64String(InputStreamToByteArray(imagem
									.getInputStream()));*/
					byte[] imagemByteArray = IOUtils.toByteArray(imagem.getInputStream());
					byte[] curriculoByteArray = IOUtils.toByteArray(curriculo.getInputStream());
					
					String imagemBase64 = new Base64().encodeBase64String(imagemByteArray);
					String curriculoBase64 = new Base64().encodeBase64String(curriculoByteArray);

					usuario.setImgBase64(imagemBase64);
					usuario.setCurriculoBase64(curriculoBase64);
					usuario.setContentType(imagem.getContentType());
					usuario.setContentTypeCurriculo(curriculo.getContentType());
					
				}
				/* Fim file upload imagem e pdf */

				if (id == null || id.isEmpty()) {
					if (!usuarioDao.loginCadastrado(login)) {
						request.setAttribute("msg", "Usuário já existe.");
						podeInserir = false;
					} else if (!usuarioDao.senhaCadastradaSalvar(senha)) {
						request.setAttribute("msg", "senha já existe.");
						podeInserir = false;
					}

				} else if (id != null && !id.isEmpty()) {
					if (!usuarioDao.loginCadastradoUpdate(login,
							Long.parseLong(id))) {
						request.setAttribute("msg",
								"Usuário já exite para outro id.");
						podeInserir = false;
					}
				}

				if (login.isEmpty() || login == null) {
					request.setAttribute("msg", "Informe o login.");
					podeInserir = false;
				} else if (nome.isEmpty() || nome == null) {
					request.setAttribute("msg", "Informe o nome.");
					podeInserir = false;
				} else if (senha.isEmpty() || senha == null) {
					request.setAttribute("msg", "Informe a senha.");
					podeInserir = false;
				} else if (fone.isEmpty() || fone == null) {
					request.setAttribute("msg", "Informe o telefone.");
					podeInserir = false;
				}

				if (id == null || id.isEmpty() && podeInserir) {
					usuarioDao.salvar(usuario);
				} else if (id != null && !id.isEmpty() && podeInserir) {
					usuarioDao.atualizar(usuario);
				}

				if (!podeInserir) {
					request.setAttribute("user", usuario);
				}

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroUsuario.jsp");
				request.setAttribute("usuarios", usuarioDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private byte[] InputStreamToByteArray(InputStream imagem) throws Exception {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = imagem.read();

		while (reads != -1) {
			baos.write(reads);
			reads = imagem.read();
		}
		return baos.toByteArray();

	}

	private void limparCampos() {
		usuario.setId(null);
		usuario.setLogin(null);
		usuario.setSenha(null);
		usuario.setNome(null);
		usuario.setFone(null);
		usuario.setCep(null);
		usuario.setRua(null);
		usuario.setBairro(null);
		usuario.setCidade(null);
		usuario.setEstado(null);
		usuario.setIbge(null);
	}
}
