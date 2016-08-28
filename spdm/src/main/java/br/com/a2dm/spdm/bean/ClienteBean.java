package br.com.a2dm.spdm.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.ClienteProduto;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.ProdutoService;


@RequestScoped
@ManagedBean
public class ClienteBean extends AbstractBean<Cliente, ClienteService>
{
	private Integer tpPesquisaProduto;	
	private BigInteger filtroCodigoProduto;	
	private String filtroDescricaoProduto;	
	private BigInteger codigoProduto;
	private BigInteger idProdutoRemover;
	private Produto produto;
	private List<Produto> listaProduto;
	private List<Produto> listaProdutoResult;
	
	private final String LISTA_PRODUTOS_SESSAO = "produtos";
	
	private JSFUtil util = new JSFUtil();
	
	public ClienteBean()
	{
		super(ClienteService.getInstancia());
		this.ACTION_SEARCH = "cliente";
		this.pageTitle = "Manutenção / Cliente";
		
		MenuControl.ativarMenu("flgMenuMan");
		MenuControl.ativarSubMenu("flgMenuManCli");
	}
	
	@Override
	protected void setListaInserir() throws Exception
	{
		this.iniciaListaProdutos();
		
		Produto produto = new Produto();
		produto.setFlgAtivo("S");
		
		List<Produto> lista = ProdutoService.getInstancia().pesquisar(produto, 0);	
		this.getListaProduto().addAll(lista);
	}
	
	@Override
	protected void completarPesquisar() throws Exception
	{
		if(this.getSearchObject().getFlgAtivo() != null
				&& this.getSearchObject().getFlgAtivo().equals("T"))
		{
			this.getSearchObject().setFlgAtivo(null);
		}
	}
	
	@Override
	protected int getJoinPesquisar()
	{
		return ClienteService.JOIN_USUARIO_CAD
			 | ClienteService.JOIN_USUARIO_ALT;				
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFlgAtivo("T");
	}
	
	@Override
	protected void setDefaultInserir() throws Exception
	{
		this.getEntity().setHorLimite("07:00");
		this.setTpPesquisaProduto(1);
		this.setProduto(new Produto());
		this.setListaProdutoResult(new ArrayList<Produto>());
		this.atualizarFiltroProduto();
	}
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDesCliente() == null
				|| this.getEntity().getDesCliente().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getHorLimite() == null
				|| this.getEntity().getHorLimite().trim().equals(""))
		{
			throw new Exception("O campo Hora Limite é obrigatório!");
		}
		
		if(this.getEntity().getHorLimite().trim().length() < 5)
		{
			throw new Exception("Favor informar a Hora Limite no formato correto. Ex: 09:00.");
		}
	}
	
	@Override
	public void preparaAlterar() 
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_ALTERAR))
			{
				util.getSession().removeAttribute(LISTA_PRODUTOS_SESSAO);
				
				super.preparaAlterar();
				Cliente cliente = new Cliente();
				cliente.setIdCliente(getEntity().getIdCliente());
				
				cliente = ClienteService.getInstancia().get(cliente, ClienteService.JOIN_CLIENTE_PRODUTO
						   										   | ClienteService.JOIN_CLIENTE_PRODUTO_PRODUTO
						   										   | ClienteService.JOIN_CLIENTE_PRODUTO_PRODUTO_RECEITA
						   										   | ClienteService.JOIN_USUARIO_CAD
						   										   | ClienteService.JOIN_USUARIO_ALT);
							
				this.setListaProdutoResult(new ArrayList<Produto>());				
				List<Produto> listaProdutoSessao = new ArrayList<Produto>();
				
				for (ClienteProduto clienteProduto : cliente.getListaClienteProduto())
				{
					this.getListaProdutoResult().add(clienteProduto.getProduto());
					listaProdutoSessao.add(clienteProduto.getProduto());
				}				
				
				setEntity(cliente);
				
				this.setTpPesquisaProduto(1);
				this.setProduto(new Produto());
				this.atualizarFiltroProduto();
				
				util.getSession().setAttribute(LISTA_PRODUTOS_SESSAO, listaProdutoSessao);
			}
		}
	    catch (Exception e)
	    {
	       FacesMessage message = new FacesMessage(e.getMessage());
	       message.setSeverity(FacesMessage.SEVERITY_ERROR);
	       FacesContext.getCurrentInstance().addMessage(null, message);
	    }
	}

	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_INATIVAR))
				{
					ClienteService.getInstancia().inativar(this.getEntity());
					
					FacesMessage message = new FacesMessage("Registro inativado com sucesso!");
					message.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void completarAlterar() throws Exception 
	{
		this.validarInserir();
		this.getEntity().setDatAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		
		List<Produto> listaSessao = (List<Produto>) util.getSession().getAttribute(LISTA_PRODUTOS_SESSAO);
		List<Produto> listaRemovidos = new ArrayList<Produto>();
		List<Produto> listaAdicionados = new ArrayList<Produto>();
		List<Produto> listaFinal = new ArrayList<Produto>();
		
		for (Produto produtoSessao : listaSessao)
		{
			boolean flag = true;
			
			for(Produto produtoResult : this.getListaProdutoResult())
			{
				if(produtoResult.getIdProduto().intValue() == produtoSessao.getIdProduto().intValue())
				{
					flag = false;
				}	
			}
			
			if(flag)
			{
				//GUARDAR TODOS OS IDS DOS PRODUTOS REMOVIDOS COM FLAG N
				Produto produto = new Produto();
				produto.setIdProduto(produtoSessao.getIdProduto());
				produto.setFlgAtivo("N");
				listaRemovidos.add(produto);
			}
		}
		
		for (Produto produtoResult : this.getListaProdutoResult())
		{
			boolean flag = true;
			
			for (Produto produtoSessao : listaSessao)
			{
				if(produtoSessao.getIdProduto().intValue() == produtoResult.getIdProduto().intValue())
				{
					flag = false;
				}
			}
			
			if(flag)
			{
				//GUARDAR TODOS OS IDS DOS PRODUTOS ADICIONADOS COM FLAG S
				Produto produto = new Produto();
				produto.setIdProduto(produtoResult.getIdProduto());
				produto.setFlgAtivo("S");
				listaAdicionados.add(produto);
			}
		}
		
		listaFinal.addAll(listaRemovidos);
		listaFinal.addAll(listaAdicionados);
		
		this.getEntity().setListaProduto(listaFinal);
	}
	
	public void ativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_ATIVAR))
				{
					ClienteService.getInstancia().ativar(this.getEntity());
					
					FacesMessage message = new FacesMessage("Registro ativado com sucesso!");
					message.setSeverity(FacesMessage.SEVERITY_INFO);
					FacesContext.getCurrentInstance().addMessage(null, message);
				}
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}		
	}
	
	public void visualizar()
	{
		try
		{
			Cliente cliente = new Cliente();
			cliente.setIdCliente(this.getEntity().getIdCliente());
						
			cliente = ClienteService.getInstancia().get(cliente, ClienteService.JOIN_CLIENTE_PRODUTO
															   | ClienteService.JOIN_CLIENTE_PRODUTO_PRODUTO
															   | ClienteService.JOIN_CLIENTE_PRODUTO_PRODUTO_RECEITA
															   | ClienteService.JOIN_USUARIO_CAD
															   | ClienteService.JOIN_USUARIO_ALT);
			
			this.setEntity(cliente);
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void atualizarFiltroProduto()
	{
		this.setFiltroCodigoProduto(null);
		this.setFiltroDescricaoProduto(null);
		this.setCodigoProduto(null);		
		this.getProduto().setDesProduto(null);
		this.getProduto().setIdProduto(null);
		this.iniciaListaProdutos();
	}
	
	private void iniciaListaProdutos()
	{
		ArrayList<Produto> lista = new ArrayList<Produto>();
		Produto produto = new Produto();
		produto.setIdProduto(null);
		produto.setDesProduto("Escolha o Produto");		
		lista.add(produto);
		
		this.setListaProduto(lista);
	}
	
	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setDatCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setListaProduto(this.getListaProdutoResult());
	}
	
	public void pesquisarProdutos()
	{
		try
		{
			Produto produto = new Produto();
			produto.setFlgAtivo("S");			
			
			if(this.tpPesquisaProduto == 1)
			{
				if(this.filtroCodigoProduto == null 
						|| this.filtroCodigoProduto.intValue() == 0)
				{
					produto.setIdProduto(new BigInteger("-1"));
				}
				else
				{
					produto.setIdProduto(this.filtroCodigoProduto);
				}
				produto = ProdutoService.getInstancia().get(produto, 0);
				
				if(produto == null)
				{
					this.setFiltroCodigoProduto(null);
					this.setCodigoProduto(null);
					this.getProduto().setDesProduto(null);
					this.getProduto().setIdProduto(null);
				}
				else
				{				
					this.setProduto(produto);
					this.setCodigoProduto(produto.getIdProduto());
				}
			}
			else
			{
				if(this.tpPesquisaProduto == 2)
				{
 					produto.setDesProduto(this.filtroDescricaoProduto);
					
					this.iniciaListaProdutos();
					List<Produto> lista = ProdutoService.getInstancia().pesquisar(produto, 0);	
					this.getListaProduto().addAll(lista);
				}
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void adicionarProduto(ActionEvent event)
	{
		try
		{
			//PRODUTO OBRIGATORIO
			if(this.getProduto() == null
					|| this.getProduto().getIdProduto() == null
					|| this.getProduto().getIdProduto().intValue() <= 0)
			{
				throw new Exception("O campo Produto é obrigatório!");			
			}
			
			//VALIDAR PRODUTO EXISTENTE
			if(this.listaProdutoResult == null)
			{
				this.setListaProdutoResult(new ArrayList<Produto>());
			}
			else
			{
				for (Produto produto : listaProdutoResult)
				{
					if(this.getProduto().getIdProduto().intValue() == produto.getIdProduto().intValue())
					{
						throw new Exception("Este produto já está adicionado na lista!");
					}
				}
			}
			
			//ADICIONANDO O PRODUTO
			Produto produto = new Produto();
			produto.setIdProduto(this.getProduto().getIdProduto());
			produto = ProdutoService.getInstancia().get(produto, ProdutoService.JOIN_RECEITA);
			
			this.getListaProdutoResult().add(produto);
			this.getProduto().setIdProduto(null);	
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void removerProduto()
	{
		if(this.getListaProdutoResult() != null)
		{
			for (Produto produto : listaProdutoResult)
			{
				if(produto.getIdProduto().intValue() == this.getIdProdutoRemover().intValue())
				{
					this.getListaProdutoResult().remove(produto);
					return;
				}
			}
		}
	}
	
	@Override
	public void cancelar(ActionEvent event)
	{
		super.cancelar(event);
		this.getSearchObject().setFlgAtivo("T");
		util.getSession().removeAttribute(LISTA_PRODUTOS_SESSAO);
	}

	public Integer getTpPesquisaProduto() {
		return tpPesquisaProduto;
	}

	public void setTpPesquisaProduto(Integer tpPesquisaProduto) {
		this.tpPesquisaProduto = tpPesquisaProduto;
	}

	public BigInteger getFiltroCodigoProduto() {
		return filtroCodigoProduto;
	}

	public void setFiltroCodigoProduto(BigInteger filtroCodigoProduto) {
		this.filtroCodigoProduto = filtroCodigoProduto;
	}

	public String getFiltroDescricaoProduto() {
		return filtroDescricaoProduto;
	}

	public void setFiltroDescricaoProduto(String filtroDescricaoProduto) {
		this.filtroDescricaoProduto = filtroDescricaoProduto;
	}
	
	public boolean isProcurarPorCodigo() {
		return (this.tpPesquisaProduto == 1 ? true : false);
	}

	public BigInteger getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(BigInteger codigoProduto) {
		this.codigoProduto = codigoProduto;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<Produto> getListaProdutoResult() {
		return listaProdutoResult;
	}

	public void setListaProdutoResult(List<Produto> listaProdutoResult) {
		this.listaProdutoResult = listaProdutoResult;
	}

	public BigInteger getIdProdutoRemover() {
		return idProdutoRemover;
	}

	public void setIdProdutoRemover(BigInteger idProdutoRemover) {
		this.idProdutoRemover = idProdutoRemover;
	}
}
