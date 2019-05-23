package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.TelefoneModel;
import model.UsuarioModel;
import dao.TelefoneDao;
import dao.UsuarioDao;

@WebServlet("/TelefoneServlet")
public class TelefoneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UsuarioDao usuarioDao = new UsuarioDao();
	UsuarioModel usuario = new UsuarioModel();
	TelefoneModel telefoneModel = new TelefoneModel();
	TelefoneDao telefoneDao = new TelefoneDao();

	public TelefoneServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String acao = request.getParameter("acao");
		String telefoneId = request.getParameter("telefoneId");
		try {
			UsuarioModel usuario = usuarioDao.consultar(Long.parseLong(user));
			request.getSession().setAttribute("user", usuario);
			request.setAttribute("user", usuario);
			
			if (acao.equalsIgnoreCase("delete")) {
				telefoneDao.deletar(Long.parseLong(telefoneId));

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroTelefone.jsp");
				request.setAttribute("telefones",
						telefoneDao.listar(usuario.getId()));
				dispatcher.forward(request, response);

			} else if (acao.equalsIgnoreCase("listar")) {

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroTelefone.jsp");
				request.setAttribute("telefones",
						telefoneDao.listar(usuario.getId()));

				dispatcher.forward(request, response);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String usuario = request.getParameter("id");
		String telefone = request.getParameter("telefone");
		String tipo = request.getParameter("tipo");

		telefoneModel.setNumero(telefone);
		telefoneModel.setTipo(tipo);
		telefoneModel.setUsuario(Long.parseLong(usuario));
		try{
		boolean podeInserir = true;
		
		if(!telefoneDao.TelefoneCadastrado(telefone)){
			request.setAttribute("msg", "Telefone já cadastrado.");
			podeInserir = false;
		}else if (telefone.isEmpty() || telefone == null){
			request.setAttribute("msg", "Informe o telefone.");
			podeInserir = false;
		}else if (tipo.isEmpty() || tipo == null){
			request.setAttribute("msg", "Informe o tipo.");
			podeInserir = false;
		}
		
		if(podeInserir){
		telefoneDao.salvar(telefoneModel);
		}
		 
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("cadastroTelefone.jsp");
			request.setAttribute("telefones",
					telefoneDao.listar(Long.parseLong(usuario)));
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
