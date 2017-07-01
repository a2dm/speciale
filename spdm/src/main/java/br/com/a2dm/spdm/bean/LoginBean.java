package br.com.a2dm.spdm.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.service.RecuperarSenhaService;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.criptografia.CriptoMD5;
import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.config.MenuControl;


@RequestScoped
@ManagedBean
public class LoginBean extends AbstractBean<Usuario, UsuarioService>
{
	private Usuario usuario;
	private String emailRecuperarSenha;
	private String mensagem;
	
	private static final String ACAO_SUCESSO = "principal";
	private static final String ACAO_LOGOUT  = "login";
	
	public LoginBean()
	{
		usuario = new Usuario(); 
	}

	public String login()
	{
		JSFUtil util = new JSFUtil();
		
		try
		{
			//VALIDANDO CAMPO LOGIN			
			if(this.usuario.getLogin() == null 
					|| this.usuario.getLogin().trim().equals(""))
			{
				//MENSAGEM DE VALIDACAO
				
				FacesMessage message = new FacesMessage("O campo Login é obrigatório!");
		        message.setSeverity(FacesMessage.SEVERITY_ERROR);
		        FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
			
			//VALIDANDO CAMPO SENHA
			if(this.usuario.getSenha() == null 
					|| this.usuario.getSenha().trim().equals(""))
			{
				//MENSAGEM DE VALIDACAO
				
				FacesMessage message = new FacesMessage("O campo Senha é obrigatório!");
		        message.setSeverity(FacesMessage.SEVERITY_ERROR);
		        FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}

			Usuario usuario = new Usuario();
			usuario.setLogin(this.usuario.getLogin().toUpperCase().trim());			
			usuario.setSenha(CriptoMD5.stringHexa(this.usuario.getSenha().toUpperCase()));
			usuario.setFlgAtivo("S");
			
			usuario = UsuarioService.getInstancia().get(usuario, 0);
			
			if(usuario != null)
			{
				util.getSession().setAttribute("loginUsuario", usuario);
				MenuControl.ativarMenu("flgMenuPed");
				return ACAO_SUCESSO;
			}
			else
			{
				FacesMessage message = new FacesMessage("Login ou Senha incorretos!");
		        message.setSeverity(FacesMessage.SEVERITY_ERROR);
		        FacesContext.getCurrentInstance().addMessage(null, message);
				return null;
			}
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage("A conexão foi restabelecida!");
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
	        FacesContext.getCurrentInstance().addMessage(null, message);
			return null;
		}		
		
	}
	
	public void preparaRecuperarSenha(ActionEvent event)
	{
		this.setMensagem(null);
		this.setEmailRecuperarSenha(null);
	}
	
	public void recuperarSenha(ActionEvent event)
	{
		try
		{
			this.setMensagem(null);
			
			if(this.getEmailRecuperarSenha() == null
					|| this.getEmailRecuperarSenha().trim().equals(""))
			{
				throw new Exception("Informe seu E-mail para o sistema enviar o link de recuperação de senha!");
			}
			
			RecuperarSenhaService.getInstancia().gerarHash(this.getEmailRecuperarSenha());
			
			FacesMessage message = new FacesMessage("O link de recuperação de senha foi enviado para o seu e-mail!");
	        message.setSeverity(FacesMessage.SEVERITY_INFO);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		catch (Exception e)
		{
			this.setMensagem(e.getMessage());
		}
	}
	
	public String logout()
	{
		
		JSFUtil util = new JSFUtil();
		util.getSession().invalidate();
		
		return ACAO_LOGOUT;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEmailRecuperarSenha() {
		return emailRecuperarSenha;
	}

	public void setEmailRecuperarSenha(String emailRecuperarSenha) {
		this.emailRecuperarSenha = emailRecuperarSenha;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
