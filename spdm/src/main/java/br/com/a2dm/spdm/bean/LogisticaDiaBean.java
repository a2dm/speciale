package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.brcmn.util.validacoes.ValidaPermissao;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.ObservacaoLogistica;
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.entity.PedidoProduto;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.ObservacaoLogisticaService;
import br.com.a2dm.spdm.service.PedidoService;
import br.com.a2dm.spdm.service.ReceitaService;


@RequestScoped
@ManagedBean
public class LogisticaDiaBean extends AbstractBean<Pedido, PedidoService>
{
	private List<Cliente> listaCliente;
	
	private Integer qtdClientes;
	private Integer qtdEspeciais;
	private Integer qtdTradicionais;
	
	private String obsLogistica;	
	private String msgObservacao;
	
	
	public LogisticaDiaBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "logisticaDia";
		this.pageTitle = "Logística do Dia";
		
		MenuControl.ativarMenu("flgMenuRel");
		MenuControl.ativarSubMenu("flgMenuRelLog");
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
		
		//INICIANDO A LEGENDA ZERADA
		this.setQtdClientes(0);
		this.setQtdEspeciais(0);
		this.setQtdTradicionais(0);
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
				
				//FOI CRIADO O SET PARA GARANTIR QUE NAO CONTARA O CLIENTE 2X, CASO O MESMO REALIZE MAIS DE UM PEDIDO NO DIA
				Set<BigInteger> listaClientes = new LinkedHashSet<BigInteger>();
				
				Integer qtdEspecial = 0;
				Integer qtdTradicional = 0;
				
				for (Pedido pedido : lista)
				{
					listaClientes.add(pedido.getCliente().getIdCliente());
					
					for (PedidoProduto pedidoProduto : pedido.getListaPedidoProduto())
					{
						pedidoProduto.getPedido().setCliente(new Cliente());
						pedidoProduto.getPedido().getCliente().setDesCliente(pedido.getCliente().getDesCliente());
						
						if(pedidoProduto.getProduto().getIdReceita().intValue() == ReceitaService.RECEITA_ESPECIAL)
						{
							qtdEspecial += pedidoProduto.getQtdSolicitada().intValue();
						}
						else
						{
							qtdTradicional += pedidoProduto.getQtdSolicitada().intValue();
						}
					}
				}
				
				this.setQtdClientes(listaClientes.size());
				this.setQtdEspeciais(qtdEspecial);
				this.setQtdTradicionais(qtdTradicional);
												
				this.setSearchResult(lista);
				completarPosPesquisar();
				
				//OBSERVACAO
				ObservacaoLogistica observacaoLogistica = new ObservacaoLogistica();
				observacaoLogistica.setDatRelatorio(this.getSearchObject().getDatPedido());
				
				observacaoLogistica = ObservacaoLogisticaService.getInstancia().get(observacaoLogistica, 0);
				
				if(observacaoLogistica == null
						|| observacaoLogistica.getDesObservacao() == null
						|| observacaoLogistica.getDesObservacao().trim().equals(""))
				{
					this.setMsgObservacao(null);
				}
				else
				{
					this.setMsgObservacao("Obs: " + observacaoLogistica.getDesObservacao());
				}
				
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
	
	public void preparaObservacao(ActionEvent event)
	{
		try
		{
			ObservacaoLogistica observacaoLogistica = new ObservacaoLogistica();
			observacaoLogistica.setDatRelatorio(this.getSearchObject().getDatPedido());
			
			observacaoLogistica = ObservacaoLogisticaService.getInstancia().get(observacaoLogistica, 0);
			
			if(observacaoLogistica == null)
			{
				this.setObsLogistica(null);
			}
			else
			{
				this.setObsLogistica(observacaoLogistica.getDesObservacao());
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
	
	public void salvarObservacao(ActionEvent event)
	{
		try
		{
			ObservacaoLogistica observacaoLogistica = new ObservacaoLogistica();
			observacaoLogistica.setDatRelatorio(this.getSearchObject().getDatPedido());
			observacaoLogistica.setDesObservacao(this.getObsLogistica());
			
			observacaoLogistica = ObservacaoLogisticaService.getInstancia().salvar(observacaoLogistica);
			
			if(observacaoLogistica == null
					|| observacaoLogistica.getDesObservacao() == null
					|| observacaoLogistica.getDesObservacao().trim().equals(""))
			{
				this.setMsgObservacao(null);
			}
			else
			{
				this.setMsgObservacao("Obs: " + observacaoLogistica.getDesObservacao());
			}
			
			FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "A observação foi salva com sucesso.", null));
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
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public void configuraRelatorio(Map parameters, HttpServletRequest request)
	{
		this.REPORT_NAME = "logistica-dia";
		
		parameters.put("IMG_LOGO", request.getRealPath("images/logo-new3.jpg"));
		parameters.put("DAT_PEDIDO", new SimpleDateFormat("dd/MM/yyyy").format(((Pedido)this.getListaReport().get(0)).getListaPedidoProduto().get(0).getPedido().getDatPedido()));
		
		parameters.put("QTD_CLIENTES", this.getQtdClientes());
		parameters.put("QTD_ESPECIAIS", this.getQtdEspeciais());
		parameters.put("QTD_TRADICIONAIS", this.getQtdTradicionais());
		
		parameters.put("OBSERVACAO", this.getMsgObservacao());		
	}
	
	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

		if (!ValidaPermissao.getInstancia().verificaPermissao("logisticaDia", acao))
		{
			temAcesso = false;
			HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			try
			{
				rp.sendRedirect("/spdm/pages/acessoNegado.jsf");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
		return temAcesso;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Integer getQtdClientes() {
		return qtdClientes;
	}

	public void setQtdClientes(Integer qtdClientes) {
		this.qtdClientes = qtdClientes;
	}

	public Integer getQtdEspeciais() {
		return qtdEspeciais;
	}

	public void setQtdEspeciais(Integer qtdEspeciais) {
		this.qtdEspeciais = qtdEspeciais;
	}

	public Integer getQtdTradicionais() {
		return qtdTradicionais;
	}

	public void setQtdTradicionais(Integer qtdTradicionais) {
		this.qtdTradicionais = qtdTradicionais;
	}

	public String getObsLogistica() {
		return obsLogistica;
	}

	public void setObsLogistica(String obsLogistica) {
		this.obsLogistica = obsLogistica;
	}

	public String getMsgObservacao() {
		return msgObservacao;
	}

	public void setMsgObservacao(String msgObservacao) {
		this.msgObservacao = msgObservacao;
	}
}