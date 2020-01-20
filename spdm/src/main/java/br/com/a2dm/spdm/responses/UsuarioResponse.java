package br.com.a2dm.spdm.responses;

import java.math.BigInteger;
import java.util.Date;

import br.com.a2dm.brcmn.entity.Estado;
import br.com.a2dm.brcmn.entity.Grupo;
import br.com.a2dm.brcmn.entity.Usuario;

public class UsuarioResponse {

	private String nome;

	private String login;

	private String senha;

	private String email;

	private String cpf;

	private String telefone;

	private Date dataNascimento;

	private Date dataCadastro;

	private BigInteger idUsuarioCad;

	private Usuario usuarioCad;

	private Date dataAlteracao;

	private BigInteger idUsuarioAlt;

	private Usuario usuarioAlt;

	private String flgAtivo;

	private BigInteger idEspecialidade;

	private BigInteger idConselho;

	private BigInteger numConselho;

	private String cep;

	private String logradouro;

	private BigInteger numEndereco;

	private String bairro;

	private String cidade;

	private BigInteger idEstado;

	private Estado estado;

	private String complemento;

	private String referencia;

	private BigInteger idGrupo;

	private Grupo grupo;

	private String flgSeguranca;

	private BigInteger idCliente;

	private String desCliente;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public BigInteger getIdUsuarioCad() {
		return idUsuarioCad;
	}

	public void setIdUsuarioCad(BigInteger idUsuarioCad) {
		this.idUsuarioCad = idUsuarioCad;
	}

	public Usuario getUsuarioCad() {
		return usuarioCad;
	}

	public void setUsuarioCad(Usuario usuarioCad) {
		this.usuarioCad = usuarioCad;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public BigInteger getIdUsuarioAlt() {
		return idUsuarioAlt;
	}

	public void setIdUsuarioAlt(BigInteger idUsuarioAlt) {
		this.idUsuarioAlt = idUsuarioAlt;
	}

	public Usuario getUsuarioAlt() {
		return usuarioAlt;
	}

	public void setUsuarioAlt(Usuario usuarioAlt) {
		this.usuarioAlt = usuarioAlt;
	}

	public String getFlgAtivo() {
		return flgAtivo;
	}

	public void setFlgAtivo(String flgAtivo) {
		this.flgAtivo = flgAtivo;
	}

	public BigInteger getIdEspecialidade() {
		return idEspecialidade;
	}

	public void setIdEspecialidade(BigInteger idEspecialidade) {
		this.idEspecialidade = idEspecialidade;
	}

	public BigInteger getIdConselho() {
		return idConselho;
	}

	public void setIdConselho(BigInteger idConselho) {
		this.idConselho = idConselho;
	}

	public BigInteger getNumConselho() {
		return numConselho;
	}

	public void setNumConselho(BigInteger numConselho) {
		this.numConselho = numConselho;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public BigInteger getNumEndereco() {
		return numEndereco;
	}

	public void setNumEndereco(BigInteger numEndereco) {
		this.numEndereco = numEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public BigInteger getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(BigInteger idEstado) {
		this.idEstado = idEstado;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public BigInteger getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(BigInteger idGrupo) {
		this.idGrupo = idGrupo;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public String getFlgSeguranca() {
		return flgSeguranca;
	}

	public void setFlgSeguranca(String flgSeguranca) {
		this.flgSeguranca = flgSeguranca;
	}

	public BigInteger getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(BigInteger idCliente) {
		this.idCliente = idCliente;
	}

	public String getDesCliente() {
		return desCliente;
	}

	public void setDesCliente(String desCliente) {
		this.desCliente = desCliente;
	}

}