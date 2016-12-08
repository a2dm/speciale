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

  
  
  
  -- Table: ped.tb_mensagem_destinatario

-- DROP TABLE ped.tb_mensagem_destinatario;

CREATE TABLE ped.tb_mensagem_destinatario
(
  id_mensagem_destinatario bigint NOT NULL,
  id_mensagem bigint NOT NULL,
  id_cliente bigint,
  CONSTRAINT pk_mensagem_destinatario PRIMARY KEY (id_mensagem_destinatario),
  CONSTRAINT fk_mensagem_destinatario_cliente FOREIGN KEY (id_cliente)
      REFERENCES ped.tb_cliente (id_cliente) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_mensagem_destinatario_mensagem FOREIGN KEY (id_mensagem)
      REFERENCES ped.tb_mensagem (id_mensagem) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ped.tb_mensagem_destinatario
  OWNER TO postgres;

  
  
  
  -- Sequence: ped.sq_mensagem

-- DROP SEQUENCE ped.sq_mensagem;

CREATE SEQUENCE ped.sq_mensagem
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE ped.sq_mensagem
  OWNER TO postgres;

  
  
  -- Sequence: ped.sq_mensagem_destinatario

-- DROP SEQUENCE ped.sq_mensagem_destinatario;

CREATE SEQUENCE ped.sq_mensagem_destinatario
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 2
  CACHE 1;
ALTER TABLE ped.sq_mensagem_destinatario
  OWNER TO postgres;
