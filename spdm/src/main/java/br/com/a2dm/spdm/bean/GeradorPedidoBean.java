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
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.PedidoProdutoService;
import br.com.a2dm.spdm.service.PedidoService;
import br.com.a2dm.spdm.service.ProdutoService;

@RequestScoped
@ManagedBean
public class GeradorPedidoBean extends AbstractBean<Pedido, PedidoService>
{
	
	private List<Cliente> listaCliente;
	private String stringData;
	private Produto produto;
	private Produto produtoSelecionado;
	private List<Produto> listaProduto;
	private Pedido pedido;
	private List<Pedido> listaPedidoResult;
	
	private JSFUtil util = new JSFUtil();
	private BigInteger qtdSolicitada;
	private BigInteger idPedidoSelecionado;
	private BigInteger idClienteSelecionado;
	private BigInteger idClienteRemover;
	private BigInteger idProdutoRemover;
	private String desClienteSelecionado;
	private Date datPedidoSelecionada;
	
	public GeradorPedidoBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "geradorPedido";
		this.pageTitle = "Gerador de Pedido";
		
		MenuControl.ativarMenu("flgMenGerPed");		
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		//DATA DE HOJE
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		this.getSearchObject().setDatPedido(c.getTime());		
		this.pesquisar(null);
		
		//CARREGANDO LISTA DE CLIENTES		
		Cliente cliente = new Cliente();
		cliente.setFlgAtivo("S");		
		List<Cliente> resultCli = ClienteService.getInstancia().pesquisar(cliente, 0);
		
		Cliente cli = new Cliente();
		cli.setDesCliente("Todos os Clientes");
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		listaCliente.add(cli);
		listaCliente.addAll(resultCli);
		
		this.setListaCliente(listaCliente);
	}
	
	@Override
	protected void setDefaultInserir() throws Exception
	{		
		this.setListaPedidoResult(new ArrayList<Pedido>());
	}
	
	public void buscarProdutos() throws Exception {
		this.setProduto(new Produto());
		this.iniciaListaProdutos();
		
		if (this.getEntity().getIdCliente() != null) 
		{
			Produto produto = new Produto();
			produto.setFlgAtivo("S");
			produto.setFiltroMap(new HashMap<String, Object>());
			produto.getFiltroMap().put("flgAtivoClienteProduto", "S");
			produto.getFiltroMap().put("idCliente", this.getEntity().getIdCliente());
			
			List<Produto> lista = ProdutoService.getInstancia().pesquisar(produto, ProdutoService.JOIN_CLIENTE_PRODUTO);
			this.getListaProduto().addAll(lista);
		}
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
	
	public void buscarInformacoes() throws Exception {
		if (this.getProduto().getIdProduto() != null && this.getProduto().getIdProduto().intValue() > 0) {
			Produto produto = new Produto();
			produto.setIdProduto(this.getProduto().getIdProduto());
			produtoSelecionado = ProdutoService.getInstancia().get(produto, 0);
//			this.setQtdSolicitada(produtoSelecionado.getQtdLoteMinimo());
		} else {
			setProdutoSelecionado(null);
		}
	}
	
	@Override
	protected void validarPesquisar() throws Exception
	{
		if(this.getSearchObject().getDatPedido() == null
				|| this.getSearchObject().getDatPedido().toString().trim().equals(""))
		{
			throw new Exception("O campo Data é obrigatório.");
		}
	}
	
	@Override
	public void preparaAlterar() {
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_ALTERAR))
			{
				PedidoProduto pedidoProduto = new PedidoProduto();
				pedidoProduto.setIdPedido(this.getIdPedidoSelecionado());
				pedidoProduto.setFlgAtivo("S");
				
				List<PedidoProduto> listaPedidoProduto = PedidoProdutoService.getInstancia().pesquisar(pedidoProduto, PedidoProdutoService.JOIN_PRODUTO | PedidoProdutoService.JOIN_PEDIDO);
				
				if (listaPedidoProduto != null && listaPedidoProduto.size() > 0) {
					Pedido pedido = new Pedido();
					pedido.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
					pedido.setDatAlteracao(new Date());
					pedido.setFlgAtivo("S");
					pedido.setIdPedido(listaPedidoProduto.get(0).getPedido().getIdPedido());
					pedido.setObsPedido(listaPedidoProduto.get(0).getPedido().getObsPedido());
					pedido.setDatPedido(listaPedidoProduto.get(0).getPedido().getDatPedido());
					
					Cliente cliente = new Cliente();
					cliente.setIdCliente(this.getIdClienteSelecionado());
					cliente.setDesCliente(this.getDesClienteSelecionado());
					pedido.setCliente(cliente);
					
					if (pedido.getCliente().getListaProduto() == null) {
						pedido.getCliente().setListaProduto(new ArrayList<>());
					}
					
					for (PedidoProduto element : listaPedidoProduto) {
						Produto produto = new Produto();
						produto.setIdProduto(element.getProduto().getIdProduto());
						produto.setDesProduto(element.getProduto().getDesProduto());
						produto.setQtdSolicitada(element.getQtdSolicitada());
						
						pedido.getCliente().getListaProduto().add(produto);
					}
					this.setListaPedidoResult(new ArrayList<>());
					this.getListaPedidoResult().add(0, pedido);
				}
				
				getEntity().setIdCliente(this.getIdClienteSelecionado());
				buscarProdutos();
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
	
	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_INATIVAR))
				{
					Pedido pedido = new Pedido();
					util.getUsuarioLogado().setIdCliente(this.getIdClienteSelecionado());
					pedido.setIdPedido(this.getIdPedidoSelecionado());
					pedido = PedidoService.getInstancia().inativar(pedido);
					this.setPedido(pedido);

					this.setSearchResult(new ArrayList<>());
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
	public void pesquisar(ActionEvent event)
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PESQUISAR))
			{
				validarPesquisar();
				completarPesquisar();
				validarCampoTexto();
				List<Pedido> lista = PedidoService.getInstancia().pesquisarLogisticaDia(this.getSearchObject());
				this.setSearchResult(lista);
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
			this.setSearchResult(new ArrayList<Pedido>());
		}
	}
	
	public void adicionarClienteProduto(ActionEvent event)
	{
		try
		{
			Boolean existeCliente = false;
			//CLIENTE, PRODUTO E QUANTIDADE OBRIGATORIOS
			if(this.getEntity().getIdCliente() == null
					|| this.getEntity().getIdCliente().intValue() <= 0)
			{
				throw new Exception("O campo Cliente é obrigatório!");			
			}
			
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
			
			if(produtoSelecionado.getQtdLoteMinimo().intValue() > getQtdSolicitada().intValue())
			{
				throw new Exception("O Lote Mínimo do produto " + produtoSelecionado.getDesProduto() + " não foi atingida! Quantidade de Lote Mínimo: " + produtoSelecionado.getQtdLoteMinimo());
			}
			
			if(getQtdSolicitada().intValue() % produtoSelecionado.getQtdMultiplo().intValue() != 0)
			{
				throw new Exception("A Quantidade do produto " + produtoSelecionado.getDesProduto() + " deve ser solicitada em múltiplo de "+ produtoSelecionado.getQtdMultiplo() +"!");
			}
			
			//VALIDAR PRODUTO EXISTENTE
			if(this.listaPedidoResult == null)
			{
				this.setListaPedidoResult(new ArrayList<Pedido>());
			}
			else
			{
				for (Pedido element : listaPedidoResult)
				{
					if(element.getCliente().getIdCliente().intValue() == this.getEntity().getIdCliente().intValue())
					{
						existeCliente = true;
						for (Produto elementProduto : element.getCliente().getListaProduto()) {
							if (elementProduto.getIdProduto().intValue() == produto.getIdProduto().intValue()) {
								throw new Exception("Este produto já está adicionado na lista para o cliente selecionado!");
							}
						}
						
						//ADICIONANDO O PRODUTO
						Produto produto = new Produto();
						produto.setIdProduto(this.getProduto().getIdProduto());
						produto = ProdutoService.getInstancia().get(produto, 0);
						produto.setQtdSolicitada(this.getQtdSolicitada());
						
						element.getCliente().getListaProduto().add(produto);
					}
				}
			}
			
			if (!existeCliente) {
				Produto produto = new Produto();
				produto.setIdProduto(this.getProduto().getIdProduto());
				produto = ProdutoService.getInstancia().get(produto, 0);
				produto.setQtdSolicitada(this.getQtdSolicitada());
				
				Cliente cliente = new Cliente();
				cliente.setIdCliente(this.getEntity().getIdCliente());
				cliente = ClienteService.getInstancia().get(cliente, 0);
				
				Pedido pedido = new Pedido();
				pedido.setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
				pedido.setDatCadastro(new Date());
				pedido.setFlgAtivo("S");
				pedido.setDatPedido(new Date());
				pedido.setCliente(cliente);
				
				if (pedido.getCliente().getListaProduto() == null) {
					pedido.getCliente().setListaProduto(new ArrayList<>());
				}
				pedido.getCliente().getListaProduto().add(produto);
			
				this.getListaPedidoResult().add(0, pedido);
			}
			this.getProduto().setIdProduto(null);
			this.setQtdSolicitada(null);
			this.setProdutoSelecionado(null);
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	protected void validarInserir() throws Exception
	{
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		Date dataHoje = c.getTime();
		
		if (getListaPedidoResult() == null || getListaPedidoResult().size() <= 0) {
			throw new Exception("Pelo menos 1 pedido deve ser adicionado!");
		}
		
		for (Pedido element : getListaPedidoResult()) {
			if (element.getDatPedido() == null
					|| element.getDatPedido().toString().trim().equals(""))
			{
				throw new Exception("No pedido do cliente " + element.getCliente().getDesCliente() + ", o campo Data da Produção é obrigatório!");
			}
			
			if(element.getDatPedido().before(dataHoje))
			{
				throw new Exception("No pedido do cliente " + element.getCliente().getDesCliente() + ", o campo Data da Produção não pode ser menor que a Data Atual!");
			}
		}
	}
	
	public void alterar(ActionEvent event) 
    {
      try
      {
    	  if(validarAcesso(Variaveis.ACAO_ALTERAR))
    	  {
    		  validarInserir();
    		  PedidoService.getInstancia().alterarGeradorPedido(getListaPedidoResult());
    		  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro alterado com sucesso", null));
    		  
    		  this.cancelar(event);
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
	
	public void inserir(ActionEvent event) 
    {
      try
      {
    	  if(validarAcesso(Variaveis.ACAO_INSERIR))
    	  {
    		  validarInserir();
    		  completarInserir();
    		  PedidoService.getInstancia().inserirGeradorPedido(getListaPedidoResult());
    		  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro inserido com sucesso", null));
    		  
    		  this.cancelar(event);
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
	
	public void removerPedido()
	{
		if(this.getListaPedidoResult() != null)
		{
			for (Pedido element : listaPedidoResult)
			{
				if(element.getCliente().getIdCliente().intValue() == this.getIdClienteRemover().intValue())
				{
					this.getListaPedidoResult().remove(element);
					return;
				}
			}
		}
	}
	
	public void removerProduto()
	{
		for (Pedido element : listaPedidoResult)
		{
			if(element.getCliente().getIdCliente().intValue() == this.getIdClienteRemover().intValue())
			{
				for (Produto elementProduto : element.getCliente().getListaProduto()) {
					if(elementProduto.getIdProduto().intValue() == this.getIdProdutoRemover().intValue()) 
					{
						element.getCliente().getListaProduto().remove(elementProduto);
						break;
					}
				}
				
				if (element.getCliente().getListaProduto() == null || element.getCliente().getListaProduto().size() <= 0) {
					this.getListaPedidoResult().remove(element);
					return;
				}
			}
		}
	}
	
	public void atualizarStringData(Date data)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		
		String nome = "";
		
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		
		switch(dia)
		{
		  case Calendar.SUNDAY: nome = "Domingo";break;
		  case Calendar.MONDAY: nome = "Segunda-feira";break;
		  case Calendar.TUESDAY: nome = "Terça-feira";break;
		  case Calendar.WEDNESDAY: nome = "Quarta-feira";break;
		  case Calendar.THURSDAY: nome = "Quinta-feira";break;
		  case Calendar.FRIDAY: nome = "Sexta-feira";break;
		  case Calendar.SATURDAY: nome = "Sábado";break;
		}
				
		this.setStringData(nome);
	}

	public String getStringData() {
		return stringData;
	}

	public void setStringData(String stringData) {
		this.stringData = stringData;
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public BigInteger getQtdSolicitada() {
		return qtdSolicitada;
	}

	public void setQtdSolicitada(BigInteger qtdSolicitada) {
		this.qtdSolicitada = qtdSolicitada;
	}

	public List<Pedido> getListaPedidoResult() {
		return listaPedidoResult;
	}

	public void setListaPedidoResult(List<Pedido> listaPedidoResult) {
		this.listaPedidoResult = listaPedidoResult;
	}

	public BigInteger getIdClienteRemover() {
		return idClienteRemover;
	}

	public void setIdClienteRemover(BigInteger idClienteRemover) {
		this.idClienteRemover = idClienteRemover;
	}

	public Date getDatPedidoSelecionada() {
		return datPedidoSelecionada;
	}

	public void setDatPedidoSelecionada(Date datPedidoSelecionada) {
		this.datPedidoSelecionada = datPedidoSelecionada;
	}

	public BigInteger getIdProdutoRemover() {
		return idProdutoRemover;
	}

	public void setIdProdutoRemover(BigInteger idProdutoRemover) {
		this.idProdutoRemover = idProdutoRemover;
	}

	public BigInteger getIdPedidoSelecionado() {
		return idPedidoSelecionado;
	}

	public void setIdPedidoSelecionado(BigInteger idPedidoSelecionado) {
		this.idPedidoSelecionado = idPedidoSelecionado;
	}

	public BigInteger getIdClienteSelecionado() {
		return idClienteSelecionado;
	}

	public void setIdClienteSelecionado(BigInteger idClienteSelecionado) {
		this.idClienteSelecionado = idClienteSelecionado;
	}

	public String getDesClienteSelecionado() {
		return desClienteSelecionado;
	}

	public void setDesClienteSelecionado(String desClienteSelecionado) {
		this.desClienteSelecionado = desClienteSelecionado;
	}
}