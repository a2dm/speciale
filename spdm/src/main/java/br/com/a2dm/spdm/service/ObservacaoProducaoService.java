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
import br.com.a2dm.spdm.entity.ObservacaoProducao;

public class ObservacaoProducaoService extends A2DMHbNgc<ObservacaoProducao>
{
	private static ObservacaoProducaoService instancia = null;
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static ObservacaoProducaoService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new ObservacaoProducaoService();
		}
		return instancia;
	}
	
	public ObservacaoProducaoService()
	{
		adicionarFiltro("idObservacaoProducao", RestritorHb.RESTRITOR_EQ,"idObservacaoProducao");
		adicionarFiltro("datRelatorio", RestritorHb.RESTRITOR_EQ,"datRelatorio");				
	}
	
	
	public ObservacaoProducao salvar(ObservacaoProducao vo) throws Exception
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
	
	public ObservacaoProducao salvar(Session sessao, ObservacaoProducao vo) throws Exception
	{
		//VERIFICA SE EXISTE OBSERVACAO PARA O DIA
		ObservacaoProducao observacaoProducao = new ObservacaoProducao();
		observacaoProducao.setDatRelatorio(vo.getDatRelatorio());
		
		observacaoProducao = this.get(observacaoProducao, 0);
		
		//SE NAO EXISTIR, INSERE
		if(observacaoProducao == null)
		{
			observacaoProducao = this.inserir(sessao, vo);
		}
		else
		{
			//SE EXISTIR ALTERA
			observacaoProducao.setDesObservacao(vo.getDesObservacao());
			observacaoProducao = this.alterar(sessao, observacaoProducao);
		}
		
		return observacaoProducao;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(ObservacaoProducao.class);
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, ObservacaoProducao vo, int join)
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
