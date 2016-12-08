-- Table: ped.tb_mensagem

-- DROP TABLE ped.tb_mensagem;

CREATE TABLE ped.tb_mensagem
(
  id_mensagem bigint NOT NULL,
  dat_mensagem date,
  hor_mensagem character varying(5),
  des_mensagem character varying(100) NOT NULL,
  dat_cadastro timestamp without time zone NOT NULL,
  id_usuario_cad bigint NOT NULL,
  dat_alteracao timestamp without time zone,
  id_usuario_alt bigint,
  flg_ativo character varying(1),
  CONSTRAINT pk_mensagem PRIMARY KEY (id_mensagem),
  CONSTRAINT fk_mensagem_usuario_alt FOREIGN KEY (id_usuario_alt)
      REFERENCES ped.tb_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_mensagem_usuario_cad FOREIGN KEY (id_usuario_cad)
      REFERENCES ped.tb_usuario (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ped.tb_mensagem
  OWNER TO postgres;
