package br.com.a2dm.spdm.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.spdm.entity.ObservacaoLogistica;

public class ObservacaoLogisticaService extends A2DMHbNgc<ObservacaoLogistica>
{
	private static ObservacaoLogisticaService instancia = null;
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ObservacaoLogisticaService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ObservacaoLogisticaService();
		}
		return instancia;
	}
	
	public ObservacaoLogisticaService()
	{
		adicionarFiltro("idObservacaoLogistica", RestritorHb.RESTRITOR_EQ,"idObservacaoLogistica");
		adicionarFiltro("datRelatorio", RestritorHb.RESTRITOR_EQ,"datRelatorio");				
	}
	
	
	public ObservacaoLogistica salvar(ObservacaoLogistica vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = salvar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public ObservacaoLogistica salvar(Session sessao, ObservacaoLogistica vo) throws Exception
	{
		//VERIFICA SE EXISTE OBSERVACAO PARA O DIA
		ObservacaoLogistica observacaoLogistica = new ObservacaoLogistica();
		observacaoLogistica.setDatRelatorio(vo.getDatRelatorio());
		
		observacaoLogistica = this.get(observacaoLogistica, 0);
		
		//SE NAO EXISTIR, INSERE
		if(observacaoLogistica == null)
		{
			observacaoLogistica = this.inserir(sessao, vo);
		}
		else
		{
			//SE EXISTIR ALTERA
			observacaoLogistica.setDesObservacao(vo.getDesObservacao());
			observacaoLogistica = this.alterar(sessao, observacaoLogistica);
		}
		
		return observacaoLogistica;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(ObservacaoLogistica.class);
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, ObservacaoLogistica vo, int join)
	{
		criteria.addOrder(Order.asc("datRelatorio"));
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
