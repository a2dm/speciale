package br.com.a2dm.spdm.bean;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
	private double qtdTotalMassa;
	
	private String qtdTotalMassaStr;
	
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
					produto.setQtdMassa(produto.getQtdMassaCrua().doubleValue() * produto.getQtdSolicitada().doubleValue());
					qtdTotalMassa += produto.getQtdMassa();
					produto.setQtdMassaInt((int) produto.getQtdMassa());
				}
				
				if (qtdTotalMassa > 0)
				{
					qtdTotalMassa = qtdTotalMassa / 1000;
				}
				
				this.setQtdTotalMassa(qtdTotalMassa);
				this.setQtdTotalMassaStr(Double.toString(qtdTotalMassa).replace(".", ",")); 
				
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
		if (this.getListaReport() != null
				&& this.getListaReport().size() > 0) 
		{
			List<Produto> list = new ArrayList<>();
			list.addAll(this.getListaReport());
		
			Double qtdMassaTotal = 0.0;
			String receitaAnterior = "";
			for (Produto produto : list) 
			{
				if (!produto.getReceita().getDesReceita().equalsIgnoreCase(receitaAnterior)) {
					qtdMassaTotal = 0.0;
				}
				produto.setQtdMassaStr(new DecimalFormat("#,##0", new DecimalFormatSymbols (new Locale ("pt", "BR"))).format(produto.getQtdMassa()));
				qtdMassaTotal += produto.getQtdMassa();
				produto.setQtdMassaTotalStr(new DecimalFormat("#,##0", new DecimalFormatSymbols (new Locale ("pt", "BR"))).format(qtdMassaTotal));
				receitaAnterior = produto.getReceita().getDesReceita();
			}
		}
		
		this.REPORT_NAME = "producao-dia";
		
		parameters.put("IMG_LOGO", request.getRealPath("images/logo-new3.jpg"));
		parameters.put("DAT_PRODUCAO", new SimpleDateFormat("dd/MM/yyyy").format(((Produto)this.getListaReport().get(0)).getDatPedido()));
		parameters.put("TOTAL_DIA", this.getQtdTotalMassaStr());
		parameters.put("STR_MASSA", "TOTAL DO DIA: " + this.getQtdTotalMassaStr() + "kg");
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

	public double getQtdTotalMassa() {
		return qtdTotalMassa;
	}

	public void setQtdTotalMassa(double qtdTotalMassa) {
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

	public String getQtdTotalMassaStr() {
		return qtdTotalMassaStr;
	}

	public void setQtdTotalMassaStr(String qtdTotalMassaStr) {
		this.qtdTotalMassaStr = qtdTotalMassaStr;
	}
}