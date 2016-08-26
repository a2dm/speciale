package br.com.a2dm.spdm.bean;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.service.PedidoProdutoService;
import br.com.a2dm.spdm.service.PedidoService;
import br.com.a2dm.spdm.service.ProdutoService;


@RequestScoped
@ManagedBean
public class PedidoBean extends AbstractBean<Pedido, PedidoService>
{
	private Integer tpPesquisaProduto;
	private BigInteger filtroCodigoProduto;	
	private String filtroDescricaoProduto;	
	private BigInteger codigoProduto;	
	private Produto produto;
	private BigInteger qtdSolicitada;
	private BigInteger idProdutoRemover;
	private List<Produto> listaProduto;
	private List<Produto> listaProdutoResult;
	
	private final String LISTA_PRODUTOS_SESSAO = "produtos";
	
	private Pedido pedido;
	private BigInteger idPedidoInativar;
	
	private JSFUtil util = new JSFUtil();
	
	public PedidoBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "pedido";
		this.pageTitle = "Pedido";
		
		MenuControl.ativarMenu("flgMenuPed");		
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setDatPedido(new Date());		
		this.pesquisar(null);
	}
	
	@Override
	protected void setDefaultInserir() throws Exception
	{		
		this.getEntity().setDatPedido(new Date());
		this.setTpPesquisaProduto(1);
		this.setProduto(new Produto());
		this.setListaProdutoResult(new ArrayList<Produto>());
		this.atualizarFiltroProduto();
	}
	
	@Override
	public void pesquisar(ActionEvent event)
    {	   
		try
		{
			if(validarAcesso(Variaveis.ACAO_PESQUISAR))
			{
				validarPesquisar();
				
				Pedido pedido = new Pedido();
				pedido.setFiltroMap(new HashMap<String, Object>());
				pedido.getFiltroMap().put("flgAtivoPedidoProduto", "S");
				pedido.setFlgAtivo("S");
				pedido.setDatPedido(this.getSearchObject().getDatPedido());
				pedido.setIdCliente(util.getUsuarioLogado().getIdCliente());
				
				pedido = PedidoService.getInstancia().get(pedido, PedidoService.JOIN_USUARIO_CAD
																| PedidoService.JOIN_PEDIDO_PRODUTO
																| PedidoService.JOIN_PEDIDO_PRODUTO_PRODUTO
																| PedidoService.JOIN_CLIENTE);
				this.setPedido(pedido);
				completarPosPesquisar();
				setCurrentState(STATE_SEARCH);
			}
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if(e.getMessage() == null)
				FacesContext.getCurrentInstance().addMessage("", message);
			else
				FacesContext.getCurrentInstance().addMessage(null, message);
			this.setPedido(new Pedido());
		}
    }
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDatPedido() == null
				|| this.getEntity().getDatPedido().toString().trim().equals(""))
		{
			throw new Exception("O campo Data do Pedido é obrigatório!");
		}
		
		if(this.getListaProdutoResult() == null
				|| this.getListaProdutoResult().size() <= 0)
		{
			throw new Exception("Pelo menos um produto deve ser adicionado ao pedido!");
		}
		
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataHoje = c.getTime();
		
		if(this.getEntity().getDatPedido().before(dataHoje))
		{
			throw new Exception("O campo Data do Pedido não pode ser menor que a Data Atual!");
		}
		
		for (Produto produto : this.getListaProdutoResult())
		{
			if(produto.getQtdSolicitada() == null
					|| produto.getQtdSolicitada().intValue() <= 0)
			{
				throw new Exception("A Quantidade do produto: " + produto.getIdProduto() + " - " + produto.getDesProduto() + " não foi preenchida!");
			}
			
			if(produto.getQtdLoteMinimo().intValue() > produto.getQtdSolicitada().intValue())
			{
				throw new Exception("O Lote Mínimo do produto: " + produto.getIdProduto() + " - " + produto.getDesProduto() + " não foi atingida! Quantidade de Lote Mínimo: " + produto.getQtdLoteMinimo());
			}
			
			if(produto.getQtdSolicitada().intValue() % produto.getQtdMultiplo().intValue() != 0)
			{
				throw new Exception("A Quantidade do produto: " + produto.getIdProduto() + " - " + produto.getDesProduto() + " deve ser solicitada em múltiplo de "+ produto.getQtdMultiplo() +"!");
			}
		}
	}
	
	public void preparaAlterar()
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_ALTERAR))
			{
				util.getSession().removeAttribute(LISTA_PRODUTOS_SESSAO);
				
				this.setTpPesquisaProduto(1);
				this.setProduto(new Produto());
				this.setListaProdutoResult(new ArrayList<Produto>());
				this.atualizarFiltroProduto();
				
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setIdPedido(this.getPedido().getIdPedido());
				pedidoProduto.setFlgAtivo("S");
				
				List<PedidoProduto> listaPedidoProduto = PedidoProdutoService.getInstancia().pesquisar(pedidoProduto, PedidoProdutoService.JOIN_PRODUTO);
				
				this.setListaProdutoResult(new ArrayList<Produto>());
				List<Produto> listaProdutoSessao = new ArrayList<Produto>();				
				
				for (PedidoProduto obj : listaPedidoProduto)
				{
					obj.getProduto().setQtdSolicitada(obj.getQtdSolicitada());
					this.getListaProdutoResult().add(obj.getProduto());
					listaProdutoSessao.add(obj.getProduto());
				}
				
				util.getSession().setAttribute(LISTA_PRODUTOS_SESSAO, listaProdutoSessao);
				
				setCurrentState(STATE_EDIT);
				setListaAlterar();
			}
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if(e.getMessage() == null)
				FacesContext.getCurrentInstance().addMessage("", message);
			else
				FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void completarAlterar() throws Exception 
	{
		this.setEntity(pedido);
		this.validarInserir();
		this.getEntity().setDatAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		
		List<Produto> listaSessao = (List<Produto>) util.getSession().getAttribute(LISTA_PRODUTOS_SESSAO);
		List<Produto> listaRemovidos = new ArrayList<Produto>();
		List<Produto> listaAdicionados = new ArrayList<Produto>();
		List<Produto> listaMantidos = new ArrayList<Produto>();
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
				produto.setQtdSolicitada(produtoResult.getQtdSolicitada());
				produto.setFlgAtivo("S");
				listaAdicionados.add(produto);
			}
		}
		
		for (Produto produtoSessao : listaSessao)
		{
			for(Produto produtoResult : this.getListaProdutoResult())
			{
				if(produtoResult.getIdProduto().intValue() == produtoSessao.getIdProduto().intValue())
				{
					produtoResult.setFlgAtivo(null);
					listaMantidos.add(produtoResult);
					break;
				}	
			}
		}
		
		listaFinal.addAll(listaRemovidos);
		listaFinal.addAll(listaAdicionados);
		listaFinal.addAll(listaMantidos);
		
		this.getEntity().setListaProduto(listaFinal);
	}
	
	@Override
	protected void validarPesquisar() throws Exception
	{
		if(this.getSearchObject().getDatPedido() == null
				|| this.getSearchObject().getDatPedido().toString().trim().equals(""))
		{
			throw new Exception("O campo Data do Pedido é obrigatório");
		}
	}
	
	public void atualizarFiltroProduto()
	{
		this.setFiltroCodigoProduto(null);
		this.setFiltroDescricaoProduto(null);
		this.setCodigoProduto(null);
		this.getProduto().setDesProduto(null);
		this.getProduto().setIdProduto(null);
		this.setQtdSolicitada(null);
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
		this.getEntity().setIdCliente(util.getUsuarioLogado().getIdCliente());
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setDatCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setListaProduto(this.getListaProdutoResult());
	}
	
	public void pesquisarProdutos()
	{
		try
		{
			if(util.getUsuarioLogado().getIdCliente() == null
					|| util.getUsuarioLogado().getIdCliente().intValue() <= 0)
			{
				throw new Exception("Você precisa ser um Cliente para realizar pedidos no sistema!");
			}
			
			Produto produto = new Produto();
			produto.setFlgAtivo("S");
			produto.setFiltroMap(new HashMap<String, Object>());
			produto.getFiltroMap().put("flgAtivoClienteProduto", "S");
			produto.getFiltroMap().put("idCliente", util.getUsuarioLogado().getIdCliente());
			
			
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
				
				produto = ProdutoService.getInstancia().get(produto, ProdutoService.JOIN_CLIENTE_PRODUTO);
				
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
					List<Produto> lista = ProdutoService.getInstancia().pesquisar(produto, ProdutoService.JOIN_CLIENTE_PRODUTO);
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
			//PRODUTO E QUANTIDADE OBRIGATORIOS
			if(this.getProduto() == null
					|| this.getProduto().getIdProduto() == null
					|| this.getProduto().getIdProduto().intValue() <= 0)
			{
				throw new Exception("O campo Produto é obrigatório!");			
			}
			
			if(this.getQtdSolicitada() == null
					|| this.getQtdSolicitada().intValue() <= 0)
			{
				throw new Exception("O campo Quantidade é obrigatório!");			
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
			produto = ProdutoService.getInstancia().get(produto, 0);
			produto.setQtdSolicitada(this.getQtdSolicitada());
			
			this.getListaProdutoResult().add(produto);
			
			this.atualizarFiltroProduto();
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
	
	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_INATIVAR))
				{
					Pedido pedido = PedidoService.getInstancia().inativar(this.getPedido());
					this.setPedido(pedido);
					
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
	public void cancelar(ActionEvent event)
	{
		this.getSearchObject().setDatPedido(new Date());		
		this.pesquisar(null);
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
	
	public boolean isProcurarPorCodigo() {
		return (this.tpPesquisaProduto == 1 ? true : false);
	}

	public BigInteger getQtdSolicitada() {
		return qtdSolicitada;
	}

	public void setQtdSolicitada(BigInteger qtdSolicitada) {
		this.qtdSolicitada = qtdSolicitada;
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public BigInteger getIdPedidoInativar() {
		return idPedidoInativar;
	}

	public void setIdPedidoInativar(BigInteger idPedidoInativar) {
		this.idPedidoInativar = idPedidoInativar;
	}
}