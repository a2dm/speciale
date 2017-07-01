package br.com.a2dm.spdm.job;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.a2dm.spdm.service.MensagemService;

@Component
@EnableScheduling
public class ScheduleJob
{
	@Scheduled(cron="*/50 * * * * *")
	public void carregarMensagens() {
		try
		{
			MensagemService.getInstancia().processJob();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(e.getMessage());
	        message.setSeverity(FacesMessage.SEVERITY_ERROR);
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
}