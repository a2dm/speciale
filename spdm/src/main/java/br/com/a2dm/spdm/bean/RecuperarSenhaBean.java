package br.com.a2dm.spdm.bean;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.a2dm.brcmn.entity.RecuperarSenha;
import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.service.RecuperarSenhaService;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.jsf.AbstractBean;


@RequestScoped
@ManagedBean
public class RecuperarSenhaBean extends AbstractBean<Usuario, UsuarioService>
{
	private String novaSenha;
	private String confirmaNovaSenha;
	private BigInteger idUsuario;
	
	
	public RecuperarSenhaBean() throws Exception
	{
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		
		String chave = request.getParameter("chave");
		
		if(chave == null
				|| !chave.trim().equals("!@*B"))
		{
			String parametro = request.getParameter("b");
			
			if(parametro != null
					&& !parametro.trim().equals(""))
			{
				
				RecuperarSenha recuperarSenha = new RecuperarSenha();
				recuperarSenha.setHash(parametro);
				recuperarSenha = RecuperarSenhaService.getInstancia().get(recuperarSenha, 0);
				
				if(recuperarSenha != null)
				{
					GregorianCalendar limite = new GregorianCalendar();
					limite.setTime(recuperarSenha.getDatCadastro());
					limite.add(Calendar.HOUR, 1);
					Date dataLimite = limite.getTime();
					
					Date dataAcesso = new Date();		
					
					if(!dataAcesso.before(dataLimite))
					{
						RecuperarSenhaService.getInstancia().deletar(recuperarSenha);					
						response.sendRedirect(request.getContextPath() + "/pages/recuperarSenhaExpired.jsf");
					}
					
					this.setIdUsuario(recuperarSenha.getIdUsuario());
				}
				else
				{
					response.sendRedirect(request.getContextPath() + "/pages/recuperarSenhaExpired.jsf");
				}
			}
			else
			{
				response.sendRedirect(request.getContextPath() + "/pages/recuperarSenhaExpired.jsf");
			}
		}
	}
	
	public String alterarSenha() 
	{
		try
		{
			this.validarAlterar();
			
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(this.getIdUsuario());
			usuario.setNovaSenha(this.getNovaSenha());
			
			RecuperarSenhaService.getInstancia().atualizarNovaSenha(usuario);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua Senha foi alterada com sucesso!", null));
			return "login";
			
		}
		catch (Exception e)
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
	        return null;
		}
	}
	
	private void validarAlterar() throws Exception
	{
		if(this.getNovaSenha() == null
				|| this.getNovaSenha().trim().equals(""))
		{
			throw new Exception("O campo Nova Senha é obrigtório!");
		}
		
		if(this.getConfirmaNovaSenha() == null
				|| this.getConfirmaNovaSenha().trim().equals(""))
		{
			throw new Exception("O campo Confirma Nova Senha é obrigtório!");
		}
		
		if(!this.getNovaSenha().trim().equals(this.getConfirmaNovaSenha().trim()))
		{
			throw new Exception("O campo Confirma Nova Senha deve ser igual ao campo Nova Senha!");
		}
		
		if(this.getNovaSenha().length() < 5)
		{
			throw new Exception("O campo nova senha deve conter pelo menos 5 caracteres!");
		}
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfirmaNovaSenha() {
		return confirmaNovaSenha;
	}

	public void setConfirmaNovaSenha(String confirmaNovaSenha) {
		this.confirmaNovaSenha = confirmaNovaSenha;
	}

	public BigInteger getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(BigInteger idUsuario) {
		this.idUsuario = idUsuario;
	}
}