package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import br.com.a2dm.spdm.entity.ObservacaoProducao;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.service.ObservacaoProducaoService;
import br.com.a2dm.spdm.service.ProdutoService;


@RequestScoped
@ManagedBean
public class ProducaoDiaBean extends AbstractBean<Produto, ProdutoService>
{	
	private Double qtdTotalMassa;
	
	private String obsProducao;
	
	private String msgObservacao;
	
	public ProducaoDiaBean()
	{
		super(ProdutoService.getInstancia());
		this.ACTION_SEARCH = "producaoDia";
		this.pageTitle = "Produção do Dia";
		
		MenuControl.ativarMenu("flgMenuRel");
		MenuControl.ativarSubMenu("flgMenuRelPed");
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		
		this.getSearchObject().setDatPedido(c.getTime());		
		this.pesquisar(null);
	}
	
	@Override
	public String preparaPesquisar()
	{
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
	public void pesquisar(ActionEvent event)
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PESQUISAR))
			{
				validarPesquisar();
				
				List<Produto> lista = ProdutoService.getInstancia().pesquisarProducaoDia(this.getSearchObject());								
				this.setSearchResult(lista);
				
				double qtdTotalMassa = 0;
				
				for (Produto produto : lista)
				{
					qtdTotalMassa += produto.getQtdMassaCrua().intValue();
				}
				
				if(qtdTotalMassa > 0)
				{
					qtdTotalMassa = qtdTotalMassa / 1000;
				}
				
				this.setQtdTotalMassa(qtdTotalMassa);
				
				//OBSERVACAO
				ObservacaoProducao observacaoProducao = new ObservacaoProducao();
				observacaoProducao.setDatRelatorio(this.getSearchObject().getDatPedido());
				
				observacaoProducao = ObservacaoProducaoService.getInstancia().get(observacaoProducao, 0);
				
				if(observacaoProducao == null
						|| observacaoProducao.getDesObservacao() == null
						|| observacaoProducao.getDesObservacao().trim().equals(""))
				{
					this.setMsgObservacao(null);
				}
				else
				{
					this.setMsgObservacao("Obs: " + observacaoProducao.getDesObservacao());
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
			this.setSearchResult(new ArrayList<Produto>());
		}
	}
	
	public void preparaObservacao(ActionEvent event)
	{
		try
		{
			ObservacaoProducao observacaoProducao = new ObservacaoProducao();
			observacaoProducao.setDatRelatorio(this.getSearchObject().getDatPedido());
			
			observacaoProducao = ObservacaoProducaoService.getInstancia().get(observacaoProducao, 0);
			
			if(observacaoProducao == null)
			{
				this.setObsProducao(null);
			}
			else
			{
				this.setObsProducao(observacaoProducao.getDesObservacao());
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
			this.setSearchResult(new ArrayList<Produto>());
		}
	}
	
	public void salvarObservacao(ActionEvent event)
	{
		try
		{
			ObservacaoProducao observacaoProducao = new ObservacaoProducao();
			observacaoProducao.setDatRelatorio(this.getSearchObject().getDatPedido());
			observacaoProducao.setDesObservacao(this.getObsProducao());
			
			observacaoProducao = ObservacaoProducaoService.getInstancia().salvar(observacaoProducao);
			
			if(observacaoProducao == null
					|| observacaoProducao.getDesObservacao() == null
					|| observacaoProducao.getDesObservacao().trim().equals(""))
			{
				this.setMsgObservacao(null);
			}
			else
			{
				this.setMsgObservacao("Obs: " + observacaoProducao.getDesObservacao());
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
			this.setSearchResult(new ArrayList<Produto>());
		}
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public void configuraRelatorio(Map parameters, HttpServletRequest request)
	{
		this.REPORT_NAME = "producao-dia";
		
		parameters.put("IMG_LOGO", request.getRealPath("images/logo-new3.jpg"));
		parameters.put("DAT_PRODUCAO", new SimpleDateFormat("dd/MM/yyyy").format(((Produto)this.getListaReport().get(0)).getDatPedido()));
		parameters.put("STR_MASSA", "Kg de MASSA: " + this.getQtdTotalMassa());
		parameters.put("OBSERVACAO", this.getMsgObservacao());
	}
	
	@Override
	protected boolean validarAcesso(String acao)
	{
		boolean temAcesso = true;

		if (!ValidaPermissao.getInstancia().verificaPermissao("producaoDia", acao))
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

	public Double getQtdTotalMassa() {
		return qtdTotalMassa;
	}

	public void setQtdTotalMassa(Double qtdTotalMassa) {
		this.qtdTotalMassa = qtdTotalMassa;
	}

	public String getObsProducao() {
		return obsProducao;
	}

	public void setObsProducao(String obsProducao) {
		this.obsProducao = obsProducao;
	}

	public String getMsgObservacao() {
		return msgObservacao;
	}

	public void setMsgObservacao(String msgObservacao) {
		this.msgObservacao = msgObservacao;
	}
}