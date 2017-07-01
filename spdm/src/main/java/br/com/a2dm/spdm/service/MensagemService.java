package br.com.a2dm.spdm.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;

import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.entity.UsuarioDevice;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.A2DMHbNgc;
import br.com.a2dm.brcmn.util.HibernateUtil;
import br.com.a2dm.brcmn.util.RestritorHb;
import br.com.a2dm.brcmn.util.jsf.JSFUtil;
import br.com.a2dm.spdm.entity.Cliente;
import br.com.a2dm.spdm.entity.Mensagem;
import br.com.a2dm.spdm.entity.MensagemDestinatario;
import br.com.a2dm.spdm.notification.PushNotification;

public class MensagemService extends A2DMHbNgc<Mensagem>
{
	private static MensagemService instancia = null;
	
	private JSFUtil util = new JSFUtil();
	
	public static final int JOIN_USUARIO_CAD = 1;
	
	public static final int JOIN_USUARIO_ALT = 2;
	
	public static final int JOIN_MENSAGEM_CLIENTE = 4;
	
	public static final int JOIN_MENSAGEM_CLIENTE_CLIENTE = 8;
		
	@SuppressWarnings("rawtypes")
	private static Map filtroPropriedade = new HashMap();
	
	@SuppressWarnings("rawtypes")
	private static Map restritores = new HashMap();
	
	public static MensagemService getInstancia()
	{
		if (instancia == null)
		{
			instancia = new MensagemService();
		}
		return instancia;
	}
	
	public MensagemService()
	{
		adicionarFiltro("idMensagem", RestritorHb.RESTRITOR_EQ,"idMensagem");
		adicionarFiltro("desReceita", RestritorHb.RESTRITOR_LIKE, "desReceita");
		adicionarFiltro("desReceita", RestritorHb.RESTRITOR_EQ, "filtroMap.desReceita");
		adicionarFiltro("flgAtivo", RestritorHb.RESTRITOR_EQ, "flgAtivo");
		adicionarFiltro("flgEnviada", RestritorHb.RESTRITOR_EQ, "flgEnviada");
		adicionarFiltro("datMensagem", RestritorHb.RESTRITOR_EQ, "datMensagem");
	}
	
	@Override
	public Mensagem inserir(Session sessao, Mensagem vo) throws Exception
	{
		validarInserir(sessao, vo);
		sessao.save(vo);
		
		if(vo.getListaCliente() != null
				&& vo.getListaCliente().size() >= 0)
		{
			for (Cliente cliente : vo.getListaCliente())
			{
				MensagemDestinatario mensagemDestinatario = new MensagemDestinatario();
				mensagemDestinatario.setIdMensagem(vo.getIdMensagem());
				mensagemDestinatario.setIdCliente(cliente.getIdCliente());				
				
				MensagemDestinatarioService.getInstancia().inserir(sessao, mensagemDestinatario);
			}
		}
		
		return vo;
	}
	
	@Override
	public Mensagem alterar(Session sessao, Mensagem vo) throws Exception
	{		
		sessao.merge(vo);
		
		MensagemDestinatario mensagemDestinatario = new MensagemDestinatario();
		mensagemDestinatario.setIdMensagem(vo.getIdMensagem());
		
		//VERIFICAR SE ESTA EM TRANSACTION ***
		Query query = sessao.createQuery("delete MensagemDestinatario where idMensagem = :idMensagem");
		query.setParameter("idMensagem", vo.getIdMensagem());
		query.executeUpdate();
		//***
				
		if(vo.getListaCliente() != null
				&& vo.getListaCliente().size() >= 0)
		{
			for (Cliente cliente : vo.getListaCliente())
			{
				MensagemDestinatario objInsert = new MensagemDestinatario();
				objInsert.setIdMensagem(vo.getIdMensagem());
				objInsert.setIdCliente(cliente.getIdCliente());				
				
				MensagemDestinatarioService.getInstancia().inserir(sessao, objInsert);
			}
		}
		
		return vo;
	}	
	
	
	public Mensagem inativar(Mensagem vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = inativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("S");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public Mensagem inativar(Session sessao, Mensagem vo) throws Exception
	{
		Mensagem mensagem = new Mensagem();
		mensagem.setIdMensagem(vo.getIdMensagem());
		mensagem = this.get(sessao, mensagem, 0);
				
		vo.setFlgAtivo("N");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		sessao.merge(vo);
		
		return vo;
	}
	
	public Mensagem ativar(Mensagem vo) throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		Transaction tx = sessao.beginTransaction();
		try
		{
			vo = ativar(sessao, vo);
			tx.commit();
			return vo;
		}
		catch (Exception e)
		{
			vo.setFlgAtivo("N");
			tx.rollback();
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}
	
	public Mensagem ativar(Session sessao, Mensagem vo) throws Exception
	{
		Mensagem mensagem = new Mensagem();
		mensagem.setIdMensagem(vo.getIdMensagem());
		mensagem = this.get(sessao, mensagem, 0);
		
		vo.setFlgAtivo("S");
		vo.setIdUsuarioAlt(util.getUsuarioLogado().getIdUsuario());
		vo.setDatAlteracao(new Date());
		
		super.alterar(sessao, vo);
		
		return vo;
	}
	
	@Override
	protected Criteria montaCriteria(Session sessao, int join)
	{
		Criteria criteria = sessao.createCriteria(Mensagem.class);
		
		if ((join & JOIN_MENSAGEM_CLIENTE) != 0)
	    {
			criteria.createAlias("listaMensagemCliente", "listaMensagemCliente", JoinType.LEFT_OUTER_JOIN);	
			
			if ((join & JOIN_MENSAGEM_CLIENTE_CLIENTE) != 0)
		    {
				criteria.createAlias("listaMensagemCliente.cliente", "cliente", JoinType.LEFT_OUTER_JOIN);
		    }
	    }
		
		if ((join & JOIN_USUARIO_CAD) != 0)
	    {
	         criteria.createAlias("usuarioCad", "usuarioCad");
	    }
		
		if ((join & JOIN_USUARIO_ALT) != 0)
	    {
			criteria.createAlias("usuarioAlt", "usuarioAlt", JoinType.LEFT_OUTER_JOIN);
	    }
		
		return criteria;
	}
		
	@Override
	protected void setarOrdenacao(Criteria criteria, Mensagem vo, int join)
	{
		criteria.addOrder(Order.asc("desMensagem"));
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
	
	public void processJob() throws Exception
	{
		Session sessao = HibernateUtil.getSession();
		sessao.setFlushMode(FlushMode.COMMIT);
		try
		{
			this.processJob(sessao);
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			sessao.close();
		}
	}

	public void processJob(Session sessao) throws Exception {
		
		try {
			Mensagem mensagem = new Mensagem();
			mensagem.setFlgAtivo("S");
			mensagem.setFlgEnviada("N");
			mensagem.setDatMensagem(new Date());
			
			List<Mensagem> listMensagem = this.pesquisar(sessao, mensagem, JOIN_MENSAGEM_CLIENTE);
			
			if (listMensagem != null && listMensagem.size() > 0) {
				for (Mensagem element : listMensagem) {
					String horMensagem = element.getHorMensagem();
					String[] array = horMensagem.split(":");
					
					int horasMensagem = Integer.parseInt(array[0]);
					int minutosMensagem = Integer.parseInt(array[1]);
					
					Calendar c = Calendar.getInstance();
					c.setTime(new Date());
					c.set(Calendar.HOUR_OF_DAY, horasMensagem);
					c.set(Calendar.MINUTE, minutosMensagem);
					c.set(Calendar.SECOND, 0);
					c.set(Calendar.MILLISECOND, 0);

					Date dataMensagem = c.getTime();
					Date dataAtual = new Date();
					
					if (dataAtual.after(dataMensagem)) {
						if (element.getListaMensagemCliente() != null && element.getListaMensagemCliente().size() > 0) {
							for (MensagemDestinatario elementMensagemDestinatario : element.getListaMensagemCliente()) {
								Usuario usuario = new Usuario();
								usuario.setIdCliente(elementMensagemDestinatario.getIdCliente());
								usuario.setFlgAtivo("S");
								
								List<Usuario> listUsuario = UsuarioService.getInstancia().pesquisar(sessao, usuario, UsuarioService.JOIN_DEVICE);
								
								if (listUsuario != null && listUsuario.size() > 0) {
									for (Usuario elementUsuario : listUsuario) {
										if (elementUsuario.getListaUsuarioDevice() != null && elementUsuario.getListaUsuarioDevice().size() > 0) {
											for (UsuarioDevice elementUsuarioDevice : elementUsuario.getListaUsuarioDevice()) {
												PushNotification.send(elementUsuarioDevice.getIdDevice(), element.getDesMensagem(), null);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
