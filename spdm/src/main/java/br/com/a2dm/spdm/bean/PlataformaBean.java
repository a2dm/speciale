package br.com.a2dm.spdm.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.PedidoService;

@RequestScoped
@ManagedBean
public class PlataformaBean extends AbstractBean<Pedido, PedidoService>
{
	private List<Cliente> listaCliente;
	private Integer qtdPlataformaWEB;
	private Integer qtdPlataformaAPP;
	
	public PlataformaBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "plataforma";
		this.pageTitle = "Plataforma";
		
		MenuControl.ativarMenu("flgMenuPla");
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
		this.setQtdPlataformaAPP(0);
		this.setQtdPlataformaWEB(0);
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
			if (validarAcesso(Variaveis.ACAO_PESQUISAR))
			{
				validarPesquisar();
				
				Pedido pedido = new Pedido();
				pedido.setFlgAtivo("S");
				pedido.setDatPedido(this.getSearchObject().getDatPedido());
				pedido.setIdCliente(this.getSearchObject().getIdCliente());
				
				List<Pedido> lista = PedidoService.getInstancia().pesquisar(pedido, PedidoService.JOIN_CLIENTE);
				
				qtdPlataformaWEB = 0;
				qtdPlataformaAPP = 0;
				
				if (lista != null && lista.size() > 0) {
					for (Pedido element : lista) {
						if (element.getPlataforma() != null && !element.getPlataforma().equalsIgnoreCase(""))
						{
							if (element.getPlataforma().equalsIgnoreCase(PedidoService.PLATAFORMA_WEB)) 
							{
								qtdPlataformaWEB++;
							} 
							else if (element.getPlataforma().equalsIgnoreCase(PedidoService.PLATAFORMA_APP)) 
							{
								qtdPlataformaAPP++;
							}
						}
					}
				}
				this.setSearchResult(lista);
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
		}
    }

	public Integer getQtdPlataformaWEB() {
		return qtdPlataformaWEB;
	}

	public void setQtdPlataformaWEB(Integer qtdPlataformaWEB) {
		this.qtdPlataformaWEB = qtdPlataformaWEB;
	}

	public Integer getQtdPlataformaAPP() {
		return qtdPlataformaAPP;
	}

	public void setQtdPlataformaAPP(Integer qtdPlataformaAPP) {
		this.qtdPlataformaAPP = qtdPlataformaAPP;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}