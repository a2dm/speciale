package br.com.a2dm.spdm.controllers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.a2dm.brcmn.entity.Usuario;
import br.com.a2dm.brcmn.service.UsuarioService;
import br.com.a2dm.brcmn.util.criptografia.CriptoMD5;
import br.com.a2dm.spdm.exceptions.ApiException;

@Path("login")
public class LoginController {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String login(Usuario usuarioDto) throws Exception {
		
		Usuario usuario = new Usuario();

		try {
			
			usuario.setLogin(usuarioDto.getLogin().toUpperCase().trim());
			usuario.setSenha(CriptoMD5.stringHexa(usuarioDto.getSenha().toUpperCase()));
			usuario.setFlgAtivo("S");

			usuario = UsuarioService.getInstancia().get(usuario, 0);
		} catch (ApiException ex) {
			ex.getMessage();
		}
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		return mapper.writeValueAsString(usuario);
	}
}