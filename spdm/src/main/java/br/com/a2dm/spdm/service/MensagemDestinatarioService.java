package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.MensagemDestinatario;

public class MensagemDestinatarioService extends A2DMHbNgc<MensagemDestinatario>
{
	private static MensagemDestinatarioService instancia = null;
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static MensagemDestinatarioService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new MensagemDestinatarioService();
		}
		return instancia;
	}
	
	public MensagemDestinatarioService()
	{
		adicionarFiltro("idMensagem", RestritorHb.RESTRITOR_EQ, "idMensagem");
	}	
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(MensagemDestinatario.class);				
		return criteria;
	}
	
	@Override
	@SuppressWarnings("rawtypes")
	protected Map restritores() 
	{
		return restritores;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected Map filtroPropriedade() 
	{
		return filtroPropriedade;
	}
}
