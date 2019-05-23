package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProdutosModel;
import dao.ProdutosDao;

@WebServlet("/ProdutosServlet")
public class ProdutosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ProdutosDao produtosDao = new ProdutosDao();
	ProdutosModel produtos = new ProdutosModel();

	public ProdutosServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String acao = request.getParameter("acao");
		String id = request.getParameter("id");

		if (acao.equalsIgnoreCase("listar")) {
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", produtosDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("delete")) {
			produtosDao.deletar(Long.parseLong(id));
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", produtosDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (acao.equalsIgnoreCase("edit")) {
			try {
				ProdutosModel produtos = produtosDao.consultar(Long
						.parseLong(id));

				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("item", produtos);
				request.setAttribute("produtos", produtosDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String acao = request.getParameter("acao");
		if(acao!= null && acao.equalsIgnoreCase("cancel")){
			try {
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("cadastroProdutos.jsp");
				request.setAttribute("produtos", produtosDao.listar());
				dispatcher.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String quantidade = request.getParameter("quantidade");
		String valor = request.getParameter("valor");

		produtos.setId(!id.isEmpty() ? Long.parseLong(id) : null);
		produtos.setNome(nome);
		produtos.setQuantiade(!quantidade.isEmpty() ? Integer.parseInt(quantidade) : 0);
		produtos.setValor(valor);

		try {
			boolean podeInserir = true;
			if (id == null || id.isEmpty()) {
				if (!produtosDao.produtoCadastrado(nome)) {
					request.setAttribute("msg", "Produto já cadastrado.");	
					podeInserir = false;
					}
			}
			if ( !id.isEmpty() && id != null) {
				if (!produtosDao.produtoCadastradoUpdate(nome, Long.parseLong(id))) {
					request.setAttribute("msg",
							"Produto já cadastrado para outro id.");
					podeInserir = false;
				}
			}
			
			if(nome.isEmpty() || nome == null){
				request.setAttribute("msg", "Informe o nome do produdo.");
				podeInserir = false;
			}else if(quantidade.isEmpty() || quantidade == null){
				request.setAttribute("msg", "Informe a quantidade de produtos.");
				podeInserir = false;
			}else if(valor.isEmpty() || valor == null){
				request.setAttribute("msg", "Infome o valor do produto.");
				podeInserir = false;
			}
			
			if (id == null || id.isEmpty()
					&& podeInserir) {
				produtosDao.salvar(produtos);
			} else if (!id.isEmpty() && id != null
					&& podeInserir) {
				produtosDao.atualizar(produtos);
			}
			
			if(!podeInserir){
				request.setAttribute("item", produtos);
			}

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("cadastroProdutos.jsp");
			request.setAttribute("produtos", produtosDao.listar());
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
}
