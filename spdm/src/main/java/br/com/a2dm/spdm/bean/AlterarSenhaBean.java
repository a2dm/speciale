package br.com.a2dm.spdm.bean;

import java.util.Date;

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


@RequestScoped
@ManagedBean
public class AlterarSenhaBean extends AbstractBean<Usuario, UsuarioService>
{
	private String novaSenha;
	
	private String novaSenhaConfirm;
	
	private JSFUtil util = new JSFUtil();
	
	public AlterarSenhaBean()
	{
		super(UsuarioService.getInstancia());
		this.ACTION_SEARCH = "alterarSenha";
		this.pageTitle = "Senha";		
	}
	
	public String preparaAlterarSenha()
	{
		try
		{
			Usuario usuario = new Usuario();
			usuario.setNome(util.getUsuarioLogado().getNome());			
			this.setEntity(usuario);
			setCurrentState(STATE_EDIT);		
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		return ACTION_SEARCH;
	}

	protected void validarAlterar() throws Exception
	{
		if(this.getEntity().getSenha() == null
				|| this.getEntity().getSenha().trim().equals(""))
		{
			throw new Exception("O campo Senha Atual é obrigatório.");
		}
		
		if(this.getNovaSenha() == null
				|| this.getNovaSenha().trim().equals(""))
		{
			throw new Exception("O campo Nova Senha é obrigatório.");
		}
		
		if(this.getNovaSenhaConfirm() == null
				|| this.getNovaSenhaConfirm().trim().equals(""))
		{
			throw new Exception("O campo Confirme a Nova Senha é obrigatório.");
		}
		
		if(!this.getNovaSenha().equals(this.getNovaSenhaConfirm()))
		{
			throw new Exception("O campo Nova Senha deve ser igual ao campo Confirme Nova Senha.");
		}
		
		if(this.getNovaSenha().length() < 5)
		{
			throw new Exception("A Nova Senha deve conter pelo menos 5 caracteres.");
		}
	}
	
	public void alterarSenha(ActionEvent event)
	{
		try
		{
			this.validarAlterar();
			this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
			this.getEntity().setDataAlteracao(new Date());
			
			setEntity(UsuarioService.getInstancia().alterarSenha(getEntity(), this.getNovaSenha()));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua senha foi alterada com sucesso.", null));
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
	   
	public void alterarSenha() throws Exception
	{
		this.validarInserir();
		this.getEntity().setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		this.getEntity().setDataAlteracao(new Date());
	}
	
	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getNovaSenhaConfirm() {
		return novaSenhaConfirm;
	}

	public void setNovaSenhaConfirm(String novaSenhaConfirm) {
		this.novaSenhaConfirm = novaSenhaConfirm;
	}
}
