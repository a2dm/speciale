package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletResponse;

import br.com.a2dm.brcmn.util.jsf.AbstractBean;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.brcmn.util.jsf.Variaveis;
import br.com.a2dm.brcmn.util.validacoes.ValidaPermissao;
import br.com.a2dm.spdm.config.MenuControl;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.entity.Receita;
import br.com.a2dm.spdm.service.ProdutoService;
import br.com.a2dm.spdm.service.ReceitaService;


@RequestScoped
@ManagedBean
public class ProdutoBean extends AbstractBean<Produto, ProdutoService>
{
	private List<Receita> listaReceita;
	
	private JSFUtil util = new JSFUtil();
	
	public ProdutoBean()
	{
		super(ProdutoService.getInstancia());
		this.ACTION_SEARCH = "produto";
		this.pageTitle = "Manutenção / Produto";
		
		MenuControl.ativarMenu("flgMenuMan");
		MenuControl.ativarSubMenu("flgMenuManPrd");
	}
	
	@Override
	protected void setListaPesquisa() throws Exception
	{
		//LISTA DE CLIENTES
		Receita receita = new Receita();
		receita.setFlgAtivo("S");
		List<Receita> resultRec = ReceitaService.getInstancia().pesquisar(receita, 0);
		
		Receita rec = new Receita();
		rec.setDesReceita("Escolha a Receita");
		
		List<Receita> listaReceita = new ArrayList<Receita>();
		listaReceita.add(rec);
		listaReceita.addAll(resultRec);
		
		this.setListaReceita(listaReceita);
	}
	
	@Override
	protected void completarPesquisar() throws Exception
	{
		if(this.getSearchObject().getFlgAtivo() != null
				&& this.getSearchObject().getFlgAtivo().equals("T"))
		{
			this.getSearchObject().setFlgAtivo(null);
		}
	}
	
	@Override
	protected int getJoinPesquisar()
	{
		return ProdutoService.JOIN_RECEITA
			 | ProdutoService.JOIN_USUARIO_CAD
			 | ProdutoService.JOIN_USUARIO_ALT;
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setFlgAtivo("T");
	}
		
	@Override
	protected void validarInserir() throws Exception
	{
		if(this.getEntity() == null
				|| this.getEntity().getDesProduto() == null
				|| this.getEntity().getDesProduto().trim().equals(""))
		{
			throw new Exception("O campo Descrição é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getIdReceita() == null
				|| this.getEntity().getIdReceita().intValue() <= 0)
		{
			throw new Exception("O campo Receita é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getQtdLoteMinimo() == null
				|| this.getEntity().getQtdLoteMinimo().intValue() <= 0)
		{
			throw new Exception("O campo Lote Mínimo é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getQtdMultiplo() == null
				|| this.getEntity().getQtdMultiplo().intValue() <= 0)
		{
			throw new Exception("O campo Qtd Múltiplo é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getQtdMassaCrua() == null
				|| this.getEntity().getQtdMassaCrua().intValue() < 0)
		{
			throw new Exception("O campo Qtd Massa Crua é obrigatório!");
		}
		
		if(this.getEntity() == null
				|| this.getEntity().getQtdMassaAssada() == null
				|| this.getEntity().getQtdMassaAssada().intValue() < 0)
		{
			throw new Exception("O campo Qtd Massa Assada é obrigatório!");
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
					ProdutoService.getInstancia().inativar(this.getEntity());
					
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
	protected void completarAlterar() throws Exception 
	{
		this.validarInserir();
		this.getEntity().setDatAlteracao(new Date());
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
					ProdutoService.getInstancia().ativar(this.getEntity());
					
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
	
	public void visualizar()
	{
		try
		{
			Produto produto = new Produto();
			produto.setIdProduto(this.getEntity().getIdProduto());
			
			produto = ProdutoService.getInstancia().get(produto, ProdutoService.JOIN_RECEITA
															   | ProdutoService.JOIN_USUARIO_CAD
															   | ProdutoService.JOIN_USUARIO_ALT);
			
			this.setEntity(produto);
		}
		catch (Exception e) 
		{
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	@Override
	protected void completarInserir() throws Exception
	{
		this.getEntity().setFlgAtivo("S");
		this.getEntity().setDatCadastro(new Date());
		this.getEntity().setIdUsuarioCad(util.getUsuarioLogado().getIdUsuario());		
	}
		
	@Override
	public void cancelar(ActionEvent event)
	{
		super.cancelar(event);
		this.getSearchObject().setFlgAtivo("T");
	}

	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

		if (!ValidaPermissao.getInstancia().verificaPermissao("produto", acao))
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
	
	public List<Receita> getListaReceita() {
		return listaReceita;
	}

	public void setListaReceita(List<Receita> listaReceita) {
		this.listaReceita = listaReceita;
	}
}
