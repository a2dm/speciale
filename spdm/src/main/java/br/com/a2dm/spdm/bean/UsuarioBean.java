package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import br.com.a2dm.brcmn.entity.Estado;
import br.com.a2dm.brcmn.entity.Grupo;
import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.service.EstadoService;
import br.com.a2dm.brcmn.service.GrupoService;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.brcmn.util.validacoes.ValidaPermissao;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.service.ClienteService;

@RequestScoped
@ManagedBean
public class UsuarioBean extends AbstractBean<Usuario, UsuarioService>
{
	private JSFUtil util = new JSFUtil();
	
	private String login;
	
	private List<Grupo> listaGrupo;
	
	private List<Estado> listaEstado;
	
	private List<Cliente> listaCliente;
	
	private String siglaEstado;
	
	private String flgVisualizarCliente;

	
	public UsuarioBean()
	{
		super(UsuarioService.getInstancia());
		this.ACTION_SEARCH = "usuario";
		this.pageTitle = "Manutenção / Usuário";
		
		MenuControl.ativarMenu("flgMenuMan");
		MenuControl.ativarSubMenu("flgMenuManUsr");
	}
	
	
	@Override
	protected void completarPesquisar() throws Exception
	{
		this.getSearchObject().setFiltroMap(new HashMap<String, Object>());
		this.getSearchObject().getFiltroMap().put("likeLogin", this.getLogin());
		
		if(this.getSearchObject().getFlgAtivo().equals("T"))
		{
			this.getSearchObject().setFlgAtivo(null);
		}
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFlgAtivo("T");
		this.setFlgVisualizarCliente("N");
	}
	
	@Override
	protected void setListaPesquisa() throws Exception
	{
		//LISTA DE CLIENTES
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
	
	@Override
	protected void setListaInserir() throws Exception
	{
		//LISTA DE ESTADOS
		List<Estado> resultEst = EstadoService.getInstancia().pesquisar(new Estado(), 0);
		
		Estado est = new Estado();
		est.setDescricao("Escolha o Estado");
		
		List<Estado> listaEstado = new ArrayList<Estado>();
		listaEstado.add(est);
		listaEstado.addAll(resultEst);
		
		this.setListaEstado(listaEstado);
		
		//LISTA DE GRUPOS
		Grupo grupo = new Grupo();
		grupo.setFlgAtivo("S");
		List<Grupo> resultGrp = GrupoService.getInstancia().pesquisar(grupo, 0);
		
		Grupo grp = new Grupo();
		grp.setDescricao("Escolha o Grupo");
		
		List<Grupo> listaGrupo = new ArrayList<Grupo>();
		listaGrupo.add(grp);
		listaGrupo.addAll(resultGrp);
		
		this.setListaGrupo(listaGrupo);
		
		//LISTA DE CLIENTES
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
	
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity().getNome() == null || this.getEntity().getNome().trim().equals(""))
		{
			throw new Exception("O campo Nome é obrigatório.");
		}
		
		if(this.getEntity().getEmail() == null || this.getEntity().getEmail().trim().equals(""))
		{
			throw new Exception("O campo E-mail é obrigatório.");
		}
		
		if(this.getEntity().getLogin() == null || this.getEntity().getLogin().trim().equals(""))
		{
			throw new Exception("O campo Login é obrigatório.");
		}
		
		if(this.getEntity().getCpf() == null || this.getEntity().getCpf().trim().equals(""))
		{
			throw new Exception("O campo Cpf é obrigatório.");
		}
		
		if(this.getEntity().getTelefone() == null || this.getEntity().getTelefone().trim().equals(""))
		{
			throw new Exception("O campo Telefone é obrigatório.");
		}
		
		if(this.getEntity().getDataNascimento() == null || this.getEntity().getDataNascimento().toString().trim().equals(""))
		{
			throw new Exception("O campo Data de Nascimento é obrigatório.");
		}
		
		if(this.getEntity().getCep() == null || this.getEntity().getCep().trim().equals(""))
		{
			throw new Exception("O campo Cep é obrigatório.");
		}
		
		if(this.getEntity().getLogradouro() == null || this.getEntity().getLogradouro().trim().equals(""))
		{
			throw new Exception("O campo Logradouro é obrigatório.");
		}
		
		if(this.getEntity().getNumEndereco() == null || this.getEntity().getNumEndereco().longValue() <= 0)
		{
			throw new Exception("O campo Número da Residência é obrigatório.");
		}
		
		if(this.getEntity().getBairro() == null || this.getEntity().getBairro().trim().equals(""))
		{
			throw new Exception("O campo Bairro é obrigatório.");
		}
		
		if(this.getEntity().getCidade() == null || this.getEntity().getCidade().trim().equals(""))
		{
			throw new Exception("O campo Cidade é obrigatório.");
		}
		
		if(this.getSiglaEstado() == null || this.getSiglaEstado().trim().equals(""))
		{
			throw new Exception("O campo Estado é obrigatório.");
		}
		
		if(this.getEntity().getIdGrupo() == null || this.getEntity().getIdGrupo().longValue() <= 0)
		{
			throw new Exception("O campo Grupo é obrigatório.");
		}
		
		if(this.getEntity().getIdGrupo().intValue() == GrupoService.GRUPO_CLIENTE.intValue())
		{
			if(this.getEntity().getIdCliente() == null || this.getEntity().getIdGrupo().longValue() <= 0)
			{
				throw new Exception("O campo Cliente é obrigatório.");
			}
		}
	}

	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setDataCadastro(new Date());
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setFlgSeguranca("N");
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
		
		Estado estado = new Estado();
		estado.setSigla(this.getSiglaEstado());
		estado = EstadoService.getInstancia().get(estado, 0);
		
		this.getEntity().setIdEstado(estado.getIdEstado());
	}
	
	@Override
	public void preparaAlterar() 
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_ALTERAR))
			{
				super.preparaAlterar();
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(getEntity().getIdUsuario());
				usuario = UsuarioService.getInstancia().get(usuario, UsuarioService.JOIN_ESTADO
																   | UsuarioService.JOIN_GRUPO	
																   | UsuarioService.JOIN_USUARIO_CAD
																   | UsuarioService.JOIN_USUARIO_ALT);
				
				Estado estado = new Estado();
				estado.setIdEstado(this.getEntity().getIdEstado());				
				estado = EstadoService.getInstancia().get(estado, 0);
				
				if(usuario.getIdCliente() != null
						&& usuario.getIdCliente().intValue() > 0)
				{
					this.setFlgVisualizarCliente("S");
				}
				else
				{
					this.setFlgVisualizarCliente("N");
				}
				
				this.setSiglaEstado(estado.getSigla());
				setEntity(usuario);
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
		
		this.getEntity().setDataAlteracao(new Date());
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
	}
	
	public void ativar() 
	{		
		try
		{
			if(this.getEntity() != null)
			{
				if(validarAcesso(Variaveis.ACAO_ATIVAR))
				{
					UsuarioService.getInstancia().ativar(this.getEntity());
					
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
					UsuarioService.getInstancia().inativar(this.getEntity());
					
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
	
	public void atualizarCliente()
	{
		if(this.getEntity().getIdGrupo() != null)
		{
			if(this.getEntity().getIdGrupo().intValue() == GrupoService.GRUPO_CLIENTE.intValue())
			{
				this.setFlgVisualizarCliente("S");
			}
			else
			{
				this.setFlgVisualizarCliente("N");
			}
		}
		else
		{
			this.setFlgVisualizarCliente("N");
		}
		
		this.getEntity().setIdCliente(null);
	}
	
	public void visualizar()
	{
		try
		{
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(this.getEntity().getIdUsuario());
			
			usuario = UsuarioService.getInstancia().get(usuario, UsuarioService.JOIN_USUARIO_CAD
															   | UsuarioService.JOIN_USUARIO_ALT
															   | UsuarioService.JOIN_ESTADO
															   | UsuarioService.JOIN_GRUPO);
			
			if(usuario != null)
			{
				if(usuario.getIdCliente() != null
						&& usuario.getIdCliente().intValue() > 0)
				{
					Cliente cliente = new Cliente();
					cliente.setIdCliente(usuario.getIdCliente());
					cliente = ClienteService.getInstancia().get(cliente, 0);
					
					usuario.setDesCliente(cliente.getDesCliente());
				}
			}
			
			this.setEntity(usuario);
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

		if (!ValidaPermissao.getInstancia().verificaPermissao("usuario", acao))
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
	public void cancelar(ActionEvent event)
	{
		super.cancelar(event);
		this.getSearchObject().setFlgAtivo("T");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public List<Grupo> getListaGrupo() {
		return listaGrupo;
	}

	public void setListaGrupo(List<Grupo> listaGrupo) {
		this.listaGrupo = listaGrupo;
	}

	public List<Estado> getListaEstado() {
		return listaEstado;
	}

	public void setListaEstado(List<Estado> listaEstado) {
		this.listaEstado = listaEstado;
	}

	public String getSiglaEstado() {
		return siglaEstado;
	}

	public void setSiglaEstado(String siglaEstado) {
		this.siglaEstado = siglaEstado;
	}

	public String getFlgVisualizarCliente() {
		return flgVisualizarCliente;
	}

	public void setFlgVisualizarCliente(String flgVisualizarCliente) {
		this.flgVisualizarCliente = flgVisualizarCliente;
	}

	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}