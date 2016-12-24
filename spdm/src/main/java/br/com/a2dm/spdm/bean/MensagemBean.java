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
import br.com.a2dm.spdm.entity.Mensagem;
import br.com.a2dm.spdm.entity.MensagemDestinatario;
import br.com.a2dm.spdm.service.ClienteService;
import br.com.a2dm.spdm.service.MensagemService;


@RequestScoped
@ManagedBean
public class MensagemBean extends AbstractBean<Mensagem, MensagemService>
{
	private Cliente cliente;
	private List<Cliente> listaCliente;
	private List<Cliente> listaClienteResult;
	private BigInteger idClienteRemover;
	
	private Date datInicio;
	private Date datFim;
	
	private JSFUtil util = new JSFUtil();
	
	public MensagemBean()
	{
		super(MensagemService.getInstancia());
		this.ACTION_SEARCH = "mensagem";
		this.pageTitle = "Mensagem / Agendamento";
		
		MenuControl.ativarMenu("flgMenuMsg");
		MenuControl.ativarSubMenu("flgMenuMsgAgn");
	}
	
	@Override
	protected void setListaPesquisa() throws Exception
	{
		this.iniciaListaClientes();
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.setCliente(new Cliente());
	}

	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDatMensagem() == null
				|| this.getEntity().getDatMensagem().toString().equals(""))
		{
			throw new Exception("O campo Data é obrigatório!");
		}		
		
		if(this.getEntity() == null
				|| this.getEntity().getHorMensagem() == null
				|| this.getEntity().getHorMensagem().trim().equals(""))
		{
			throw new Exception("O campo Hora é obrigatório!");
		}
		
		if(this.getEntity().getHorMensagem().trim().length() < 5)
		{
			throw new Exception("Favor informar a Hora no formato correto. Ex: 09:00.");
		}
		
		if(this.getEntity().getDesMensagem().trim().length() < 5)
		{
			throw new Exception("O campo Mensagem é obrigatório!");
		}
		
		if(this.getListaClienteResult() == null
				|| this.getListaClienteResult().size() <= 0)
		{
			throw new Exception("Pelo menos um Cliente deve ser informado!");
		}
	}
	
	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setDatCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setListaCliente(this.getListaClienteResult());
	}
	
	public void adicionarCliente(ActionEvent event)
	{
		try
		{
			//CLIENTE OBRIGATORIO
			if(this.getCliente() == null
					|| this.getCliente().getIdCliente() == null
					|| this.getCliente().getIdCliente().intValue() <= 0)
			{
				throw new Exception("O campo Cliente é obrigatório!");			
			}
			
			//VALIDAR PRODUTO EXISTENTE
			if(this.listaClienteResult == null)
			{
				this.setListaClienteResult(new ArrayList<Cliente>());
			}
			else
			{
				for (Cliente cliente : listaClienteResult)
				{
					if(this.getCliente().getIdCliente().intValue() == cliente.getIdCliente().intValue())
					{
						throw new Exception("Este cliente já está adicionado na lista!");
					}
				}
			}
			
			//ADICIONANDO O PRODUTO
			Cliente cliente = new Cliente();
			cliente.setIdCliente(this.getCliente().getIdCliente());
			cliente = ClienteService.getInstancia().get(cliente, 0);
			
			this.getListaClienteResult().add(0, cliente);
			this.getCliente().setIdCliente(null);
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	public void removerCliente()
	{
		if(this.getListaClienteResult() != null)
		{
			for (Cliente cliente : listaClienteResult)
			{
				if(cliente.getIdCliente().intValue() == this.getIdClienteRemover().intValue())
				{
					this.getListaClienteResult().remove(cliente);
					return;
				}
			}
		}
	}
	
	public void ativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_ATIVAR))
				{
					MensagemService.getInstancia().ativar(this.getEntity());
					
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
	
	public void inativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_INATIVAR))
				{
					MensagemService.getInstancia().inativar(this.getEntity());
					
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
	public void preparaAlterar() 
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_ALTERAR))
			{
				super.preparaAlterar();
				Mensagem mensagem = new Mensagem();
				mensagem.setIdMensagem(getEntity().getIdMensagem());
				
				mensagem = MensagemService.getInstancia().get(mensagem, MensagemService.JOIN_MENSAGEM_CLIENTE
						   										   	  | MensagemService.JOIN_MENSAGEM_CLIENTE_CLIENTE
						   										      | MensagemService.JOIN_USUARIO_CAD
						   										      |	MensagemService.JOIN_USUARIO_ALT);
							
				this.setListaClienteResult(new ArrayList<Cliente>());				
				
				for (MensagemDestinatario mensagemCliente : mensagem.getListaMensagemCliente())
				{
					this.getListaClienteResult().add(mensagemCliente.getCliente());					
				}				
				
				setEntity(mensagem);
				
				this.setCliente(new Cliente());
				this.iniciaListaClientes();
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
	protected void completarAlterar() throws Exception 
	{
		this.validarInserir();
		this.getEntity().setDatAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setListaCliente(this.getListaClienteResult());
	}
	
	public void visualizar()
	{
		try
		{
			Mensagem mensagem = new Mensagem();
			mensagem.setIdMensagem(this.getEntity().getIdMensagem());
						
			mensagem = MensagemService.getInstancia().get(mensagem, MensagemService.JOIN_USUARIO_CAD
																  | MensagemService.JOIN_USUARIO_ALT
																  | MensagemService.JOIN_MENSAGEM_CLIENTE
																  | MensagemService.JOIN_MENSAGEM_CLIENTE_CLIENTE);
			
			this.setEntity(mensagem);
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	private void iniciaListaClientes() throws Exception
	{
		Cliente cliente = new Cliente();
		cliente.setFlgAtivo("S");
		List<Cliente> resultCli = ClienteService.getInstancia().pesquisar(cliente, 0);
		
		Cliente cli = new Cliente();
		cli.setDesCliente("Escolha o Cliente");
		
		List<Cliente> listaCliente = new ArrayList<Cliente>();
		listaCliente.add(cli);
		listaCliente.addAll(resultCli);
		
		this.setListaCliente(listaCliente);
	}
	
	
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaClienteResult() {
		return listaClienteResult;
	}

	public void setListaClienteResult(List<Cliente> listaClienteResult) {
		this.listaClienteResult = listaClienteResult;
	}

	public BigInteger getIdClienteRemover() {
		return idClienteRemover;
	}

	public void setIdClienteRemover(BigInteger idClienteRemover) {
		this.idClienteRemover = idClienteRemover;
	}

	public Date getDatInicio() {
		return datInicio;
	}

	public void setDatInicio(Date datInicio) {
		this.datInicio = datInicio;
	}

	public Date getDatFim() {
		return datFim;
	}

	public void setDatFim(Date datFim) {
		this.datFim = datFim;
	}
}