package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.brcmn.util.validacoes.ValidaPermissao;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.PedidoService;


@RequestScoped
@ManagedBean
public class ObservacaoPedidoBean extends AbstractBean<Pedido, PedidoService>
{
	private List<Cliente> listaCliente;
	
	public ObservacaoPedidoBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "observacaoPedido";
		this.pageTitle = "Observações dos Pedidos";
		
		MenuControl.ativarMenu("flgMenuRel");
		MenuControl.ativarSubMenu("flgMenuRelObs");
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
	public String preparaPesquisar() {
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_PESQUISAR))
			{
				this.getSearchObject().setDatPedido(new Date());		
				this.pesquisar(null);
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
		return ACTION_SEARCH;
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
	protected void completarPesquisar() throws Exception
	{
		this.getSearchObject().setFlgAtivo("S");
		this.getSearchObject().setFiltroMap(new HashMap<String, Object>());
		this.getSearchObject().getFiltroMap().put("obsNotNull", "NOTNULL");
	}
	
	@Override
	protected int getJoinPesquisar()
	{
		return PedidoService.JOIN_CLIENTE;
	}
	
	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

		if (!ValidaPermissao.getInstancia().verificaPermissao("observacaoPedido", acao))
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
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public void configuraRelatorio(Map parameters, HttpServletRequest request) {
		this.REPORT_NAME = "observacao";
		
		parameters.put("IMG_LOGO", request.getRealPath("images/logo.png"));
		parameters.put("DAT_PEDIDO", new SimpleDateFormat("dd/MM/yyyy").format(((Pedido)this.getListaReport().get(0)).getDatPedido()));
	}
		
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}