package br.com.a2dm.spdm.bean;

import java.util.Date;
import java.util.HashMap;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.brcmn.util.ws.WebServiceCep;
import br.com.a2dm.spdm.config.MenuControl;

@RequestScoped
@ManagedBean
public class UsuarioBean extends AbstractBean<Usuario, UsuarioService>
{
	private JSFUtil util = new JSFUtil();
	
	private String login;
	
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
	}
	
	public void buscarCep()
	{
		try
		{
			if(this.getEntity().getCep() != null
					&& !this.getEntity().getCep().equals(""))
			{
				String cep = this.getEntity().getCep().replace("-", "");
				
				WebServiceCep webServiceCep = WebServiceCep.searchCep(cep);
				
				this.getEntity().setLogradouro(webServiceCep.getLogradouroFull().toUpperCase());
				this.getEntity().setBairro(webServiceCep.getBairro().toUpperCase());
				this.getEntity().setCidade(webServiceCep.getCidade().toUpperCase());
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
		
		if(this.getEntity().getIdEspecialidade() == null || this.getEntity().getIdEspecialidade().longValue() <= 0)
		{
			throw new Exception("O campo Especialidade é obrigatório.");
		}
		
		if(this.getEntity().getIdConselho() == null || this.getEntity().getIdConselho().longValue() <= 0)
		{
			throw new Exception("O campo Conselho é obrigatório.");
		}
		
		if(this.getEntity().getNumConselho() == null || this.getEntity().getNumConselho().longValue() <= 0)
		{
			throw new Exception("O campo Número do Conselho é obrigatório.");
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
		
		if(this.getEntity().getIdEstado() == null || this.getEntity().getIdEstado().longValue() <= 0)
		{
			throw new Exception("O campo Estado é obrigatório.");
		}
		
		if(this.getEntity().getIdGrupo() == null || this.getEntity().getIdGrupo().longValue() <= 0)
		{
			throw new Exception("O campo Grupo é obrigatório.");
		}
	}

	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setDataCadastro(new Date());
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());
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
				usuario = UsuarioService.getInstancia().get(usuario, 0);
				
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
	
	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

//		if (!ValidaPermissao.getInstancia().verificaPermissao("usuario", acao))
//		{
//			temAcesso = false;
//			HttpServletResponse rp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//			try
//			{
//				rp.sendRedirect("/spdm/pages/acessoNegado.jsf");
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
		
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
}