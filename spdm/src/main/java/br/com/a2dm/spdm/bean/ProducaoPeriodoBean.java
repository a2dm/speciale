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
import br.com.a2dm.spdm.entity.Pedido;
import br.com.a2dm.spdm.entity.Produto;
import br.com.a2dm.spdm.service.PedidoService;

@RequestScoped
@ManagedBean
public class ProducaoPeriodoBean extends AbstractBean<Pedido, PedidoService>
{	
	private String qtdTotalMassaStr;
	private String stringDataInicio;
	private String stringDataFim;
	
	public ProducaoPeriodoBean()
	{
		super(PedidoService.getInstancia());
		this.ACTION_SEARCH = "producaoPeriodo";
		this.pageTitle = "Produção por Período";
		
		MenuControl.ativarMenu("flgMenuRel");
		MenuControl.ativarSubMenu("flgMenuRelPer");
	}
	
	@Override
	protected void setValoresDefault() throws Exception
	{
		this.getSearchObject().setDatPedidoInicio(new Date());
		this.getSearchObject().setDatPedidoFim(new Date());
		this.atualizarStringDataInicio(this.getSearchObject().getDatPedidoInicio());
		this.atualizarStringDataFim(this.getSearchObject().getDatPedidoFim());
	}
	
	public void atualizarStringDataInicioSearch()
	{
		this.atualizarStringData(this.getSearchObject().getDatPedido());
	}
	
	public void atualizarStringDataFinalSearch()
	{
		this.atualizarStringData(this.getSearchObject().getDatPedidoFim());
	}
	
	public String atualizarStringData(Date data)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);
		
		String nome = "";
		
		int dia = calendar.get(Calendar.DAY_OF_WEEK);
		
		switch(dia)
		{
		  case Calendar.SUNDAY: nome = "Domingo";break;
		  case Calendar.MONDAY: nome = "Segunda-feira";break;
		  case Calendar.TUESDAY: nome = "Terça-feira";break;
		  case Calendar.WEDNESDAY: nome = "Quarta-feira";break;
		  case Calendar.THURSDAY: nome = "Quinta-feira";break;
		  case Calendar.FRIDAY: nome = "Sexta-feira";break;
		  case Calendar.SATURDAY: nome = "Sábado";break;
		}
		
		return nome;
	}
	
	public void atualizarStringDataInicio(Date data) {
		setStringDataInicio(atualizarStringData(data));
	}
	
	public void atualizarStringDataFim(Date data) {
		setStringDataFim(atualizarStringData(data));
	}
	
	@Override
	public String preparaPesquisar()
	{
		try
		{
			if(validarAcesso(Variaveis.ACAO_PREPARA_PESQUISAR))
			{
				this.getSearchObject().setDatPedidoInicio(new Date());
				this.getSearchObject().setDatPedidoFim(new Date());
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
		if(this.getSearchObject().getDatPedidoInicio() == null
				|| this.getSearchObject().getDatPedidoInicio().toString().trim().equals(""))
		{
			throw new Exception("O campo Data do Pedido Inicial é obrigatório.");
		}
		
		if(this.getSearchObject().getDatPedidoFim() == null
				|| this.getSearchObject().getDatPedidoFim().toString().trim().equals(""))
		{
			throw new Exception("O campo Data do Pedido Final é obrigatório.");
		}
		
		if(this.getSearchObject().getDatPedidoFim().before(this.getSearchObject().getDatPedidoInicio()))
		{
			throw new Exception("O campo Data do Pedido Final não pode ser menor que o Data do Pedido Inicial.");
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
				completarPesquisar();
				validarCampoTexto();
				List<Pedido> lista = PedidoService.getInstancia().pesquisarProducaoPeriodo(this.getSearchObject());
				
				this.setSearchResult(lista);
				completarPosPesquisar();
				
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
			this.setSearchResult(new ArrayList<Pedido>());
		}
	}
	
	@Override
	@SuppressWarnings({ "unchecked", "deprecation", "rawtypes" })
	public void configuraRelatorio(Map parameters, HttpServletRequest request)
	{
		this.REPORT_NAME = "producao-periodo";
		
		parameters.put("IMG_LOGO", request.getRealPath("images/logo-new3.jpg"));
		parameters.put("DAT_PEDIDO_INICIAL", new SimpleDateFormat("dd/MM/yyyy").format(((Pedido) this.getListaReport().get(0)).getDatPedidoInicio()));
		parameters.put("DAT_PEDIDO_FINAL", new SimpleDateFormat("dd/MM/yyyy").format(((Pedido) this.getListaReport().get(0)).getDatPedidoFim()));
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

	public String getQtdTotalMassaStr() {
		return qtdTotalMassaStr;
	}

	public void setQtdTotalMassaStr(String qtdTotalMassaStr) {
		this.qtdTotalMassaStr = qtdTotalMassaStr;
	}

	public String getStringDataInicio() {
		return stringDataInicio;
	}

	public void setStringDataInicio(String stringDataInicio) {
		this.stringDataInicio = stringDataInicio;
	}

	public String getStringDataFim() {
		return stringDataFim;
	}

	public void setStringDataFim(String stringDataFim) {
		this.stringDataFim = stringDataFim;
	}
	
	
}