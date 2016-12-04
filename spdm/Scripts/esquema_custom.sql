CREATE SCHEMA ped;

--
-- TOC entry 211 (class 1259 OID 70522)
-- Name: sq_cliente; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_cliente
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 212 (class 1259 OID 70524)
-- Name: sq_cliente_produto; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_cliente_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 186 (class 1259 OID 70138)
-- Name: sq_grupo; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_grupo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 187 (class 1259 OID 70140)
-- Name: sq_grupo_log; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_grupo_log
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 188 (class 1259 OID 70142)
-- Name: sq_grupo_tela_acao; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_grupo_tela_acao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 189 (class 1259 OID 70144)
-- Name: sq_grupo_tela_acao_log; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_grupo_tela_acao_log
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 213 (class 1259 OID 70526)
-- Name: sq_pedido; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_pedido
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 214 (class 1259 OID 70528)
-- Name: sq_pedido_produto; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_pedido_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 215 (class 1259 OID 70530)
-- Name: sq_produto; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_produto
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 216 (class 1259 OID 70532)
-- Name: sq_receita; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_receita
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 190 (class 1259 OID 70146)
-- Name: sq_recuperar_senha; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_recuperar_senha
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 191 (class 1259 OID 70148)
-- Name: sq_tela_acao; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_tela_acao
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 192 (class 1259 OID 70150)
-- Name: sq_tela_acao_log; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_tela_acao_log
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 193 (class 1259 OID 70152)
-- Name: sq_usuario; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_usuario
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 194 (class 1259 OID 70154)
-- Name: sq_usuario_grupo; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_usuario_grupo
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 195 (class 1259 OID 70156)
-- Name: sq_usuario_log; Type: SEQUENCE; Schema: ped; Owner: -
--

CREATE SEQUENCE sq_usuario_log
    START WITH 5
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 196 (class 1259 OID 70158)
-- Name: tb_acao; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_acao (
    id_acao bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 217 (class 1259 OID 70534)
-- Name: tb_cliente; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_cliente (
    id_cliente bigint NOT NULL,
    des_cliente character varying(200) NOT NULL,
    flg_ativo character varying(1) NOT NULL,
    dat_cadastro timestamp with time zone NOT NULL,
    id_usuario_cad bigint NOT NULL,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    hor_limite character varying(5) NOT NULL
);


--
-- TOC entry 2341 (class 0 OID 0)
-- Dependencies: 217
-- Name: TABLE tb_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_cliente IS 'TABELA DE CLIENTES(ESTABELECIMENTOS)';


--
-- TOC entry 2342 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.id_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.id_cliente IS 'PK DA TABELA CLIENTE';


--
-- TOC entry 2343 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.des_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.des_cliente IS 'DESCRICAO DO CLIENTE';


--
-- TOC entry 2344 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2345 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2346 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO';


--
-- TOC entry 2347 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2348 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 2349 (class 0 OID 0)
-- Dependencies: 217
-- Name: COLUMN tb_cliente.hor_limite; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente.hor_limite IS 'HORA LIMITE PARA OS PEDIDOS DO DIA';


--
-- TOC entry 218 (class 1259 OID 70537)
-- Name: tb_cliente_produto; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_cliente_produto (
    id_cliente_produto bigint NOT NULL,
    id_cliente bigint,
    id_produto bigint,
    flg_ativo character varying(1),
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint
);


--
-- TOC entry 2350 (class 0 OID 0)
-- Dependencies: 218
-- Name: TABLE tb_cliente_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_cliente_produto IS 'TABELA QUE RELACIONA OS PRODUTOS DE CADA CLIENTE';


--
-- TOC entry 2351 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.id_cliente_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.id_cliente_produto IS 'PK DA TABELA CLIENTE PRODUTO';


--
-- TOC entry 2352 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.id_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.id_cliente IS 'ID DO CLIENTE';


--
-- TOC entry 2353 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.id_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.id_produto IS 'ID DO PRODUTO';


--
-- TOC entry 2354 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2355 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2356 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO';


--
-- TOC entry 2357 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2358 (class 0 OID 0)
-- Dependencies: 218
-- Name: COLUMN tb_cliente_produto.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_cliente_produto.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 197 (class 1259 OID 70161)
-- Name: tb_conselho; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_conselho (
    id_conselho bigint NOT NULL,
    descricao character varying(50)
);


--
-- TOC entry 198 (class 1259 OID 70164)
-- Name: tb_especialidade; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_especialidade (
    id_especialidade bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 199 (class 1259 OID 70167)
-- Name: tb_estado; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_estado (
    id_estado bigint NOT NULL,
    descricao character varying(100),
    sigla character varying(2)
);


--
-- TOC entry 200 (class 1259 OID 70170)
-- Name: tb_grupo; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_grupo (
    id_grupo bigint NOT NULL,
    descricao character varying(100),
    flg_ativo character varying(1),
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint
);


--
-- TOC entry 201 (class 1259 OID 70173)
-- Name: tb_grupo_log; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_grupo_log (
    id_grupo_log bigint NOT NULL,
    id_grupo bigint NOT NULL,
    descricao character varying(100),
    flg_ativo character varying(1),
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    log_mapping character varying(100)
);


--
-- TOC entry 202 (class 1259 OID 70176)
-- Name: tb_grupo_tela_acao; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_grupo_tela_acao (
    id_grupo_tela_acao bigint NOT NULL,
    id_grupo bigint,
    id_tela_acao bigint,
    flg_ativo character varying(1),
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint
);


--
-- TOC entry 203 (class 1259 OID 70179)
-- Name: tb_grupo_tela_acao_log; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_grupo_tela_acao_log (
    id_grupo_tela_acao_log bigint NOT NULL,
    id_grupo_tela_acao bigint NOT NULL,
    id_grupo bigint,
    id_tela_acao bigint,
    flg_ativo character varying(1),
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    log_mapping character varying(100)
);


--
-- TOC entry 204 (class 1259 OID 70182)
-- Name: tb_parametro; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_parametro (
    id_parametro bigint NOT NULL,
    descricao character varying(200) NOT NULL,
    valor character varying(200) NOT NULL
);


--
-- TOC entry 2359 (class 0 OID 0)
-- Dependencies: 204
-- Name: TABLE tb_parametro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_parametro IS 'TABELA DE PARAMETRO GLOBAL DOS SISTEMAS';


--
-- TOC entry 2360 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_parametro.id_parametro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_parametro.id_parametro IS 'PK DA TABELA PARAMETRO';


--
-- TOC entry 2361 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_parametro.descricao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_parametro.descricao IS 'DESCRICAO DO PARAMETRO';


--
-- TOC entry 2362 (class 0 OID 0)
-- Dependencies: 204
-- Name: COLUMN tb_parametro.valor; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_parametro.valor IS 'VALOR DO PARAMETRO';


--
-- TOC entry 219 (class 1259 OID 70540)
-- Name: tb_pedido; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_pedido (
    id_pedido bigint NOT NULL,
    id_cliente bigint NOT NULL,
    dat_pedido date NOT NULL,
    flg_ativo character varying(1) NOT NULL,
    dat_cadastro timestamp with time zone NOT NULL,
    id_usuario_cad bigint NOT NULL,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    obs_pedido character varying(400)
);


--
-- TOC entry 2363 (class 0 OID 0)
-- Dependencies: 219
-- Name: TABLE tb_pedido; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_pedido IS 'TABELA DE PEDIDO';


--
-- TOC entry 2364 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.id_pedido; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.id_pedido IS 'PK DA TABELA DE PEDIDO';


--
-- TOC entry 2365 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.id_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.id_cliente IS 'ID DO CLIENTE DO PEDIDO';


--
-- TOC entry 2366 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.dat_pedido; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.dat_pedido IS 'DATA REAL DO PEDIDO';


--
-- TOC entry 2367 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2368 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2369 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO';


--
-- TOC entry 2370 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2371 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 2372 (class 0 OID 0)
-- Dependencies: 219
-- Name: COLUMN tb_pedido.obs_pedido; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido.obs_pedido IS 'OBSERVACAO DO PEDIDO';


--
-- TOC entry 220 (class 1259 OID 70543)
-- Name: tb_pedido_produto; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_pedido_produto (
    id_pedido_produto bigint NOT NULL,
    id_pedido bigint NOT NULL,
    id_produto bigint NOT NULL,
    qtd_solicitada numeric(10,0) NOT NULL,
    flg_ativo character varying(1) NOT NULL,
    dat_cadastro timestamp with time zone NOT NULL,
    id_usuario_cad bigint NOT NULL,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    CONSTRAINT ck_qtd_solicitada CHECK ((qtd_solicitada > (0)::numeric))
);


--
-- TOC entry 2373 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.id_pedido_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.id_pedido_produto IS 'PK DA TABELA PEDIDO PRODUTO';


--
-- TOC entry 2374 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.id_pedido; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.id_pedido IS 'ID DO PEDIDO';


--
-- TOC entry 2375 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.id_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.id_produto IS 'ID DO PRODUTO';


--
-- TOC entry 2376 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.qtd_solicitada; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.qtd_solicitada IS 'QUANTIDADE SOLICITADA DO PRODUTO';


--
-- TOC entry 2377 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2378 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2379 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO DO REGISTRO';


--
-- TOC entry 2380 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2381 (class 0 OID 0)
-- Dependencies: 220
-- Name: COLUMN tb_pedido_produto.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_pedido_produto.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 221 (class 1259 OID 70547)
-- Name: tb_produto; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_produto (
    id_produto bigint NOT NULL,
    des_produto character varying(200) NOT NULL,
    id_receita bigint NOT NULL,
    qtd_lot_minimo bigint NOT NULL,
    qtd_multiplo bigint NOT NULL,
    flg_ativo character varying(1) NOT NULL,
    dat_cadastro timestamp with time zone NOT NULL,
    id_usuario_cad bigint NOT NULL,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    CONSTRAINT ck_qtd_lot_min CHECK ((qtd_lot_minimo > 0)),
    CONSTRAINT ck_qtd_multiplo CHECK ((qtd_multiplo > 0))
);


--
-- TOC entry 2382 (class 0 OID 0)
-- Dependencies: 221
-- Name: TABLE tb_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_produto IS 'TABELA DE PRODUTOS';


--
-- TOC entry 2383 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.id_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.id_produto IS 'PK DA TABELA PRODUTO';


--
-- TOC entry 2384 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.des_produto; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.des_produto IS 'DESCRICAO DO PRODUTO';


--
-- TOC entry 2385 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.id_receita; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.id_receita IS 'ID DA RECEITA DO PRODUTO';


--
-- TOC entry 2386 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.qtd_lot_minimo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.qtd_lot_minimo IS 'QUANTIDADE DE LOTE MINIMO A SER PEDIDO DO PRODUTO';


--
-- TOC entry 2387 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.qtd_multiplo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.qtd_multiplo IS 'QUANTIDADE MULTIPLO QUE PODE SER PEDIDO O PRODUTO';


--
-- TOC entry 2388 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2389 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2390 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO';


--
-- TOC entry 2391 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2392 (class 0 OID 0)
-- Dependencies: 221
-- Name: COLUMN tb_produto.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_produto.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 222 (class 1259 OID 70552)
-- Name: tb_receita; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_receita (
    id_receita bigint NOT NULL,
    des_receita character varying(100) NOT NULL,
    flg_ativo character varying(1) NOT NULL,
    dat_cadastro timestamp with time zone NOT NULL,
    id_usuario_cad bigint NOT NULL,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint
);


--
-- TOC entry 2393 (class 0 OID 0)
-- Dependencies: 222
-- Name: TABLE tb_receita; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_receita IS 'TABELA DA RECEITA DOS PRODUTOS';


--
-- TOC entry 2394 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.id_receita; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.id_receita IS 'PK DA TABELA RECEITA';


--
-- TOC entry 2395 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.des_receita; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.des_receita IS 'DESCRICAO DA RECEITA';


--
-- TOC entry 2396 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.flg_ativo; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.flg_ativo IS 'FLAG DE ATIVO';


--
-- TOC entry 2397 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 2398 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.id_usuario_cad; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.id_usuario_cad IS 'ID DO USUARIO DE CADASTRO';


--
-- TOC entry 2399 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.dat_alteracao; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.dat_alteracao IS 'DATA DE ALTERACAO DO REGISTRO';


--
-- TOC entry 2400 (class 0 OID 0)
-- Dependencies: 222
-- Name: COLUMN tb_receita.id_usuario_alt; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_receita.id_usuario_alt IS 'ID DO USUARIO DE ALTERACAO';


--
-- TOC entry 205 (class 1259 OID 70185)
-- Name: tb_recuperar_senha; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_recuperar_senha (
    id_recuperar_senha bigint NOT NULL,
    id_usuario bigint,
    hash character varying(200),
    dat_cadastro timestamp with time zone NOT NULL
);


--
-- TOC entry 2401 (class 0 OID 0)
-- Dependencies: 205
-- Name: TABLE tb_recuperar_senha; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON TABLE tb_recuperar_senha IS 'TABELA DE RECUPERAR A SENHA DO USUARIO';


--
-- TOC entry 2402 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN tb_recuperar_senha.id_recuperar_senha; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_recuperar_senha.id_recuperar_senha IS 'PK DA TABELA';


--
-- TOC entry 2403 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN tb_recuperar_senha.id_usuario; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_recuperar_senha.id_usuario IS 'ID DO USUARIO DE RECUPERACAO DE SENHA';


--
-- TOC entry 2404 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN tb_recuperar_senha.hash; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_recuperar_senha.hash IS 'STRING HASH COM O LINK DE RECUPERAR SENHA';


--
-- TOC entry 2405 (class 0 OID 0)
-- Dependencies: 205
-- Name: COLUMN tb_recuperar_senha.dat_cadastro; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_recuperar_senha.dat_cadastro IS 'DATA DE CADASTRO DO REGISTRO';


--
-- TOC entry 206 (class 1259 OID 70188)
-- Name: tb_sistema; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_sistema (
    id_sistema bigint NOT NULL,
    descricao character varying(100)
);


--
-- TOC entry 207 (class 1259 OID 70191)
-- Name: tb_tela_acao; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_tela_acao (
    id_tela_acao bigint NOT NULL,
    descricao character varying(200),
    pagina character varying(100),
    id_sistema bigint,
    id_acao bigint,
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    flg_ativo character varying(1)
);


--
-- TOC entry 208 (class 1259 OID 70194)
-- Name: tb_tela_acao_log; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_tela_acao_log (
    id_tela_acao_log bigint NOT NULL,
    id_tela_acao bigint NOT NULL,
    descricao character varying(200),
    pagina character varying(100),
    id_sistema bigint,
    id_acao bigint,
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    flg_ativo character varying(1),
    log_mapping character varying(100)
);


--
-- TOC entry 209 (class 1259 OID 70197)
-- Name: tb_usuario; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_usuario (
    id_usuario bigint NOT NULL,
    nome character varying(250),
    login character varying(100),
    senha character varying(100),
    email character varying(250),
    cpf character varying(20),
    telefone character varying(20),
    dat_nascimento date,
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    flg_ativo character varying(1),
    id_especialidade bigint,
    id_conselho bigint,
    num_conselho numeric(15,0),
    cep character varying(20),
    logradouro character varying(200),
    num_endereco numeric(6,0),
    bairro character varying(200),
    cidade character varying(200),
    id_estado bigint,
    complemento character varying(200),
    referencia character varying(200),
    id_grupo bigint,
    flg_seguranca character varying(1),
    id_cliente bigint
);


--
-- TOC entry 2406 (class 0 OID 0)
-- Dependencies: 209
-- Name: COLUMN tb_usuario.id_cliente; Type: COMMENT; Schema: ped; Owner: -
--

COMMENT ON COLUMN tb_usuario.id_cliente IS 'ID DO CLIENTE QUE O USUARIO PERTENCE';


--
-- TOC entry 210 (class 1259 OID 70203)
-- Name: tb_usuario_log; Type: TABLE; Schema: ped; Owner: -
--

CREATE TABLE tb_usuario_log (
    id_usuario_log bigint NOT NULL,
    id_usuario bigint NOT NULL,
    nome character varying(250),
    login character varying(100),
    senha character varying(100),
    email character varying(250),
    cpf character varying(20),
    telefone character varying(20),
    dat_nascimento date,
    dat_cadastro timestamp with time zone,
    id_usuario_cad bigint,
    dat_alteracao timestamp with time zone,
    id_usuario_alt bigint,
    flg_ativo character varying(1),
    id_especialidade bigint,
    id_conselho bigint,
    num_conselho numeric(15,0),
    cep character varying(20),
    logradouro character varying(200),
    num_endereco numeric(6,0),
    bairro character varying(200),
    cidade character varying(200),
    id_estado bigint,
    complemento character varying(200),
    referencia character varying(200),
    id_grupo bigint,
    flg_seguranca character varying(1),
    log_mapping character varying(100)
);


--
-- TOC entry 2407 (class 0 OID 0)
-- Dependencies: 211
-- Name: sq_cliente; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_cliente', 1, true);


--
-- TOC entry 2408 (class 0 OID 0)
-- Dependencies: 212
-- Name: sq_cliente_produto; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_cliente_produto', 1, true);


--
-- TOC entry 2409 (class 0 OID 0)
-- Dependencies: 186
-- Name: sq_grupo; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_grupo', 11, true);


--
-- TOC entry 2410 (class 0 OID 0)
-- Dependencies: 187
-- Name: sq_grupo_log; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_grupo_log', 6, true);


--
-- TOC entry 2411 (class 0 OID 0)
-- Dependencies: 188
-- Name: sq_grupo_tela_acao; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_grupo_tela_acao', 150, true);


--
-- TOC entry 2412 (class 0 OID 0)
-- Dependencies: 189
-- Name: sq_grupo_tela_acao_log; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_grupo_tela_acao_log', 34, true);


--
-- TOC entry 2413 (class 0 OID 0)
-- Dependencies: 213
-- Name: sq_pedido; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_pedido', 1, true);


--
-- TOC entry 2414 (class 0 OID 0)
-- Dependencies: 214
-- Name: sq_pedido_produto; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_pedido_produto', 1, true);


--
-- TOC entry 2415 (class 0 OID 0)
-- Dependencies: 215
-- Name: sq_produto; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_produto', 1, true);


--
-- TOC entry 2416 (class 0 OID 0)
-- Dependencies: 216
-- Name: sq_receita; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_receita', 1, true);


--
-- TOC entry 2417 (class 0 OID 0)
-- Dependencies: 190
-- Name: sq_recuperar_senha; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_recuperar_senha', 1, true);


--
-- TOC entry 2418 (class 0 OID 0)
-- Dependencies: 191
-- Name: sq_tela_acao; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_tela_acao', 110, true);


--
-- TOC entry 2419 (class 0 OID 0)
-- Dependencies: 192
-- Name: sq_tela_acao_log; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_tela_acao_log', 14, true);


--
-- TOC entry 2420 (class 0 OID 0)
-- Dependencies: 193
-- Name: sq_usuario; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_usuario', 2, true);


--
-- TOC entry 2421 (class 0 OID 0)
-- Dependencies: 194
-- Name: sq_usuario_grupo; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_usuario_grupo', 1, true);


--
-- TOC entry 2422 (class 0 OID 0)
-- Dependencies: 195
-- Name: sq_usuario_log; Type: SEQUENCE SET; Schema: ped; Owner: -
--

SELECT pg_catalog.setval('sq_usuario_log', 2, true);


--
-- TOC entry 2310 (class 0 OID 70158)
-- Dependencies: 196
-- Data for Name: tb_acao; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_acao (id_acao, descricao) VALUES (1, 'PREPARAINSERIR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (2, 'INSERIR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (5, 'PREPARAALTERAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (6, 'ALTERAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (7, 'PREPARAPESQUISAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (8, 'PESQUISAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (9, 'ATIVAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (10, 'INATIVAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (11, 'PREPARACONFIGURAR');
INSERT INTO tb_acao (id_acao, descricao) VALUES (12, 'CONFIGURAR');


--
-- TOC entry 2331 (class 0 OID 70534)
-- Dependencies: 217
-- Data for Name: tb_cliente; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2332 (class 0 OID 70537)
-- Dependencies: 218
-- Data for Name: tb_cliente_produto; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2311 (class 0 OID 70161)
-- Dependencies: 197
-- Data for Name: tb_conselho; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_conselho (id_conselho, descricao) VALUES (1, 'CRM');
INSERT INTO tb_conselho (id_conselho, descricao) VALUES (2, 'CRF');
INSERT INTO tb_conselho (id_conselho, descricao) VALUES (3, 'CREFISIO');
INSERT INTO tb_conselho (id_conselho, descricao) VALUES (4, 'COREN');
INSERT INTO tb_conselho (id_conselho, descricao) VALUES (5, 'CRO');
INSERT INTO tb_conselho (id_conselho, descricao) VALUES (6, 'CRN');


--
-- TOC entry 2312 (class 0 OID 70164)
-- Dependencies: 198
-- Data for Name: tb_especialidade; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (1, 'MÉDICO');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (2, 'ODONTÓLOGO');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (3, 'NUTRICIONISTA');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (4, 'ENFERMEIRO');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (5, 'FISIOTERAPEUTA');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (6, 'PSICÓLOGO');
INSERT INTO tb_especialidade (id_especialidade, descricao) VALUES (999, 'RECEPCIONISTA');


--
-- TOC entry 2313 (class 0 OID 70167)
-- Dependencies: 199
-- Data for Name: tb_estado; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (2, 'ALAGOAS', 'AL');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (3, 'AMAZONAS', 'AM');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (4, 'AMAPÁ', 'AP');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (5, 'BAHIA', 'BA');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (6, 'CEARÁ', 'CE');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (7, 'DISTRITO FEDERAL', 'DF');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (8, 'ESPÍRITO SANTO', 'ES');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (9, 'GOIÁS', 'GO');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (10, 'MARANHÃO', 'MA');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (11, 'MINAS GERAIS', 'MG');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (12, 'MATO GROSSO DO SUL', 'MS');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (13, 'MATO GROSSO', 'MT');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (14, 'PARÁ', 'PA');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (15, 'PARAÍBA', 'PB');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (16, 'PERNAMBUCO', 'PE');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (17, 'PIAUÍ', 'PI');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (18, 'PARANÁ', 'PR');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (19, 'RIO DE JANEIRO', 'RJ');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (20, 'RIO GRANDE DO NORTE', 'RN');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (21, 'RORAIMA', 'RR');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (22, 'RONDÔNIA', 'RO');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (23, 'RIO GRANDE DO SUL', 'RS');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (24, 'SANTA CATARINA', 'SC');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (25, 'SERGIPE', 'SE');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (26, 'SÃO PAULO', 'SP');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (27, 'TOCANTINS', 'TO');
INSERT INTO tb_estado (id_estado, descricao, sigla) VALUES (1, 'ACRE', 'AC');


--
-- TOC entry 2314 (class 0 OID 70170)
-- Dependencies: 200
-- Data for Name: tb_grupo; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_grupo (id_grupo, descricao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (11, 'GERENTE', 'S', '2016-05-31 15:43:49.992-03', 1, NULL, NULL);
INSERT INTO tb_grupo (id_grupo, descricao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (9, 'FUNCIONARIO', 'S', '2016-05-31 15:43:28.731-03', 1, NULL, NULL);
INSERT INTO tb_grupo (id_grupo, descricao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (10, 'CLIENTE', 'S', '2016-05-31 15:43:37.025-03', 1, NULL, NULL);


--
-- TOC entry 2315 (class 0 OID 70173)
-- Dependencies: 201
-- Data for Name: tb_grupo_log; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2316 (class 0 OID 70176)
-- Dependencies: 202
-- Data for Name: tb_grupo_tela_acao; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (81, 10, 42, 'S', '2016-06-18 01:50:05.392-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (82, 10, 41, 'S', '2016-06-18 01:50:05.4-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (83, 9, 49, 'N', '2016-06-30 20:19:50.051-03', 1, '2016-06-30 20:55:12.448-03', 1);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (84, 11, 49, 'S', '2016-06-30 20:55:23.148-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (85, 11, 50, 'S', '2016-06-30 20:56:40.894-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (86, 11, 51, 'S', '2016-06-30 20:56:54.036-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (87, 11, 52, 'S', '2016-06-30 20:57:47.573-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (88, 11, 53, 'S', '2016-06-30 20:57:47.577-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (89, 11, 56, 'S', '2016-06-30 20:57:47.58-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (90, 11, 54, 'S', '2016-06-30 20:57:47.583-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (91, 11, 55, 'S', '2016-06-30 20:57:47.585-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (92, 11, 57, 'S', '2016-07-11 20:54:43.367-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (93, 11, 64, 'S', '2016-07-11 20:58:22.406-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (94, 11, 62, 'S', '2016-07-11 20:58:22.409-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (95, 11, 61, 'S', '2016-07-11 20:58:22.411-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (96, 11, 60, 'S', '2016-07-11 20:58:22.414-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (97, 11, 58, 'S', '2016-07-11 20:58:22.416-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (98, 11, 63, 'S', '2016-07-11 20:58:22.419-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (99, 11, 59, 'S', '2016-07-11 20:58:22.421-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (55, 11, 30, 'S', '2016-05-31 15:53:18.224-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (56, 11, 28, 'S', '2016-05-31 15:53:18.232-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (57, 11, 27, 'S', '2016-05-31 15:53:18.236-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (58, 11, 26, 'S', '2016-05-31 15:53:18.238-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (59, 11, 24, 'S', '2016-05-31 15:53:18.241-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (60, 11, 29, 'S', '2016-05-31 15:53:18.244-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (61, 11, 25, 'S', '2016-05-31 15:53:18.246-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (62, 11, 23, 'S', '2016-05-31 15:53:18.249-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (63, 11, 48, 'S', '2016-05-31 15:53:18.252-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (64, 11, 46, 'S', '2016-05-31 15:53:18.254-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (65, 11, 45, 'S', '2016-05-31 15:53:18.256-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (66, 11, 44, 'S', '2016-05-31 15:53:18.258-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (67, 11, 42, 'S', '2016-05-31 15:53:18.261-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (68, 11, 47, 'S', '2016-05-31 15:53:18.263-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (69, 11, 43, 'S', '2016-05-31 15:53:18.265-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (70, 11, 41, 'S', '2016-05-31 15:53:18.269-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (71, 11, 38, 'S', '2016-05-31 15:53:18.271-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (72, 11, 36, 'S', '2016-05-31 15:53:18.274-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (73, 11, 40, 'S', '2016-05-31 15:53:18.277-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (74, 11, 35, 'S', '2016-05-31 15:53:18.281-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (75, 11, 34, 'S', '2016-05-31 15:53:18.284-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (76, 11, 32, 'S', '2016-05-31 15:53:18.288-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (77, 11, 37, 'S', '2016-05-31 15:53:18.291-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (78, 11, 39, 'S', '2016-05-31 15:53:18.294-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (79, 11, 33, 'S', '2016-05-31 15:53:18.296-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (80, 11, 31, 'S', '2016-05-31 15:53:18.299-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (100, 10, 69, 'S', '2016-08-29 21:15:24.186-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (101, 10, 71, 'S', '2016-08-29 21:15:24.188-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (102, 10, 68, 'S', '2016-08-29 21:15:24.191-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (103, 10, 66, 'S', '2016-08-29 21:15:24.193-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (104, 10, 70, 'S', '2016-08-29 21:15:24.195-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (105, 10, 67, 'S', '2016-08-29 21:15:24.197-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (106, 10, 65, 'S', '2016-08-29 21:15:24.199-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (107, 9, 106, 'S', '2016-08-29 21:26:26.633-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (108, 9, 105, 'S', '2016-08-29 21:26:26.637-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (109, 11, 106, 'S', '2016-08-29 21:27:31.261-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (110, 11, 105, 'S', '2016-08-29 21:27:31.264-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (111, 11, 96, 'S', '2016-08-29 21:27:31.266-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (112, 11, 95, 'S', '2016-08-29 21:27:31.268-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (113, 11, 94, 'S', '2016-08-29 21:27:31.27-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (114, 11, 93, 'S', '2016-08-29 21:27:31.273-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (115, 11, 92, 'S', '2016-08-29 21:27:31.275-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (116, 11, 91, 'S', '2016-08-29 21:27:31.278-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (117, 11, 90, 'S', '2016-08-29 21:27:31.28-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (118, 11, 89, 'S', '2016-08-29 21:27:31.282-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (119, 11, 86, 'S', '2016-08-29 21:27:31.284-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (120, 11, 85, 'S', '2016-08-29 21:27:31.286-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (121, 11, 84, 'S', '2016-08-29 21:27:31.288-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (122, 11, 82, 'S', '2016-08-29 21:27:31.29-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (123, 11, 80, 'S', '2016-08-29 21:27:31.292-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (124, 11, 83, 'S', '2016-08-29 21:27:31.294-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (125, 11, 81, 'S', '2016-08-29 21:27:31.296-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (126, 11, 79, 'S', '2016-08-29 21:27:31.297-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (127, 11, 77, 'S', '2016-08-29 21:27:31.299-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (128, 11, 88, 'S', '2016-08-29 21:27:31.301-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (129, 11, 78, 'S', '2016-08-29 21:27:31.303-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (130, 11, 75, 'S', '2016-08-29 21:27:31.305-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (131, 11, 73, 'S', '2016-08-29 21:27:31.307-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (132, 11, 76, 'S', '2016-08-29 21:27:31.308-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (133, 11, 74, 'S', '2016-08-29 21:27:31.31-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (134, 11, 72, 'S', '2016-08-29 21:27:31.312-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (135, 11, 104, 'S', '2016-08-29 21:27:31.314-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (136, 11, 103, 'S', '2016-08-29 21:27:31.317-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (137, 11, 102, 'S', '2016-08-29 21:27:31.319-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (138, 11, 101, 'S', '2016-08-29 21:27:31.321-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (139, 11, 100, 'S', '2016-08-29 21:27:31.323-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (140, 11, 99, 'S', '2016-08-29 21:27:31.324-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (141, 11, 98, 'S', '2016-08-29 21:27:31.326-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (142, 11, 97, 'S', '2016-08-29 21:27:31.328-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (143, 9, 110, 'S', '2016-09-11 17:56:45.595-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (144, 9, 109, 'S', '2016-09-11 17:56:45.607-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (145, 9, 108, 'S', '2016-09-11 17:56:45.613-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (146, 9, 107, 'S', '2016-09-11 17:56:45.616-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (147, 11, 110, 'S', '2016-09-11 17:56:54.349-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (148, 11, 109, 'S', '2016-09-11 17:56:54.352-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (149, 11, 108, 'S', '2016-09-11 17:56:54.355-03', 1, NULL, NULL);
INSERT INTO tb_grupo_tela_acao (id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt) VALUES (150, 11, 107, 'S', '2016-09-11 17:56:54.358-03', 1, NULL, NULL);


--
-- TOC entry 2317 (class 0 OID 70179)
-- Dependencies: 203
-- Data for Name: tb_grupo_tela_acao_log; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_grupo_tela_acao_log (id_grupo_tela_acao_log, id_grupo_tela_acao, id_grupo, id_tela_acao, flg_ativo, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, log_mapping) VALUES (34, 83, 9, 49, 'S', '2016-06-30 20:19:50.051-03', 1, NULL, NULL, 'REMOVIDO DAS PERMISSÕES POR: Grupo - Configurar');


--
-- TOC entry 2318 (class 0 OID 70182)
-- Dependencies: 204
-- Data for Name: tb_parametro; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_parametro (id_parametro, descricao, valor) VALUES (1, 'CAMINHOAPP', 'http://www.specialepanificacoes.com/spdm/');


--
-- TOC entry 2333 (class 0 OID 70540)
-- Dependencies: 219
-- Data for Name: tb_pedido; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2334 (class 0 OID 70543)
-- Dependencies: 220
-- Data for Name: tb_pedido_produto; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2335 (class 0 OID 70547)
-- Dependencies: 221
-- Data for Name: tb_produto; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2336 (class 0 OID 70552)
-- Dependencies: 222
-- Data for Name: tb_receita; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2319 (class 0 OID 70185)
-- Dependencies: 205
-- Data for Name: tb_recuperar_senha; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2320 (class 0 OID 70188)
-- Dependencies: 206
-- Data for Name: tb_sistema; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_sistema (id_sistema, descricao) VALUES (1, 'SISTEMA DE SEGURANÇA');
INSERT INTO tb_sistema (id_sistema, descricao) VALUES (2, 'SISTEMA DE CLÍNICA');
INSERT INTO tb_sistema (id_sistema, descricao) VALUES (3, 'SISTEMA SPECIALE');


--
-- TOC entry 2321 (class 0 OID 70191)
-- Dependencies: 207
-- Data for Name: tb_tela_acao; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (41, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 7, '2016-05-31 15:50:46.932-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (42, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 8, '2016-05-31 15:51:07.446-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (43, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 1, '2016-05-31 15:51:27.23-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (44, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 2, '2016-05-31 15:51:36.811-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (45, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 10, '2016-05-31 15:51:48.682-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (46, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 9, '2016-05-31 15:51:58.068-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (47, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 5, '2016-05-31 15:52:11.109-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (48, 'TELA RESPONSÁVEL POR USUÁRIO', 'USUARIO', 1, 6, '2016-05-31 15:52:22.591-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (49, 'TELA DE SERVIÇO', 'SERVICO', 2, 7, '2016-06-30 20:16:14.225-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (50, 'TELA DE SERVIÇO', 'SERVICO', 2, 8, '2016-06-30 20:16:29.701-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (51, 'TELA DE SERVIÇO', 'SERVICO', 2, 1, '2016-06-30 20:16:44.659-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (52, 'TELA DE SERVIÇO', 'SERVICO', 2, 2, '2016-06-30 20:16:58.46-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (53, 'TELA DE SERVIÇO', 'SERVICO', 2, 5, '2016-06-30 20:18:29.332-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (54, 'TELA DE SERVIÇO', 'SERVICO', 2, 6, '2016-06-30 20:18:38.958-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (55, 'TELA DE SERVIÇO', 'SERVICO', 2, 9, '2016-06-30 20:19:04.025-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (56, 'TELA DE SERVIÇO', 'SERVICO', 2, 10, '2016-06-30 20:19:21.552-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (57, 'TELA DE CONVENIO', 'CONVENIO', 2, 7, '2016-07-11 20:53:58.248-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (58, 'TELA DE CONVENIO', 'CONVENIO', 2, 8, '2016-07-11 20:55:24.829-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (59, 'TELA DE CONVENIO', 'CONVENIO', 2, 1, '2016-07-11 20:55:47.667-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (60, 'TELA DE CONVENIO', 'CONVENIO', 2, 2, '2016-07-11 20:56:02.901-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (61, 'TELA DE CONVENIO', 'CONVENIO', 2, 10, '2016-07-11 20:56:32.596-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (62, 'TELA DE CONVENIO', 'CONVENIO', 2, 9, '2016-07-11 20:56:45.352-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (23, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 7, '2016-05-31 15:38:51.753-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (24, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 8, '2016-05-31 15:39:08.006-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (25, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 1, '2016-05-31 15:39:24.334-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (26, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 2, '2016-05-31 15:39:45.737-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (27, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 10, '2016-05-31 15:41:47.338-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (28, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 9, '2016-05-31 15:41:59.776-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (29, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 5, '2016-05-31 15:42:14.452-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (30, 'TELA RESPONSÁVEL POR TELAACAO', 'TELAACAO', 1, 6, '2016-05-31 15:42:23.586-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (31, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 7, '2016-05-31 15:46:25.874-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (32, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 8, '2016-05-31 15:46:39.487-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (33, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 1, '2016-05-31 15:46:51.128-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (34, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 2, '2016-05-31 15:47:14.422-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (35, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 10, '2016-05-31 15:47:34.244-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (36, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 9, '2016-05-31 15:47:44.228-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (37, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 5, '2016-05-31 15:48:01.977-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (38, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 6, '2016-05-31 15:48:10.811-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (39, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 11, '2016-05-31 15:48:28.741-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (40, 'TELA RESPONSÁVEL PRO GRUPO', 'GRUPO', 1, 12, '2016-05-31 15:48:38.993-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (63, 'TELA DE CONVENIO', 'CONVENIO', 2, 5, '2016-07-11 20:57:22.328-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (64, 'TELA DE CONVENIO', 'CONVENIO', 2, 6, '2016-07-11 20:57:43.144-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (65, 'TELA DE PEDIDO', 'PEDIDO', 3, 7, '2016-08-29 21:13:16.567-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (66, 'TELA DE PEDIDO', 'PEDIDO', 3, 8, '2016-08-29 21:13:28.066-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (67, 'TELA DE PEDIDO', 'PEDIDO', 3, 1, '2016-08-29 21:13:39.53-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (68, 'TELA DE PEDIDO', 'PEDIDO', 3, 2, '2016-08-29 21:13:52.02-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (69, 'TELA DE PEDIDO', 'PEDIDO', 3, 6, '2016-08-29 21:14:06.407-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (70, 'TELA DE PEDIDO', 'PEDIDO', 3, 5, '2016-08-29 21:14:16.296-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (71, 'TELA DE PEDIDO', 'PEDIDO', 3, 10, '2016-08-29 21:14:37.062-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (72, 'TELA DE RECEITA', 'RECEITA', 3, 7, '2016-08-29 21:16:23.309-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (73, 'TELA DE RECEITA', 'RECEITA', 3, 8, '2016-08-29 21:16:33.457-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (74, 'TELA DE RECEITA', 'RECEITA', 3, 1, '2016-08-29 21:16:50.616-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (75, 'TELA DE RECEITA', 'RECEITA', 3, 2, '2016-08-29 21:17:01.099-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (76, 'TELA DE RECEITA', 'RECEITA', 3, 5, '2016-08-29 21:17:16.14-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (77, 'TELA DE RECEITA', 'RECEITA', 3, 6, '2016-08-29 21:17:53.736-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (78, 'TELA DE RECEITA', 'RECEITA', 3, 10, '2016-08-29 21:18:04.368-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (79, 'TELA DE PRODUTO', 'PRODUTO', 3, 7, '2016-08-29 21:18:38.689-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (80, 'TELA DE PRODUTO', 'PRODUTO', 3, 8, '2016-08-29 21:18:47.833-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (81, 'TELA DE PRODUTO', 'PRODUTO', 3, 1, '2016-08-29 21:18:56.907-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (82, 'TELA DE PRODUTO', 'PRODUTO', 3, 2, '2016-08-29 21:19:06.075-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (83, 'TELA DE PRODUTO', 'PRODUTO', 3, 5, '2016-08-29 21:19:16.768-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (84, 'TELA DE PRODUTO', 'PRODUTO', 3, 10, '2016-08-29 21:19:35.465-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (85, 'TELA DE PRODUTO', 'PRODUTO', 3, 9, '2016-08-29 21:19:49.709-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (86, 'TELA DE PRODUTO', 'PRODUTO', 3, 6, '2016-08-29 21:20:08.845-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (87, 'TELA DE PEDIDO', 'PEDIDO', 3, 9, '2016-08-29 21:20:43.468-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (88, 'TELA DE RECEITA', 'RECEITA', 3, 9, '2016-08-29 21:21:04.972-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (89, 'TELA DE CLIENTE', 'CLIENTE', 3, 7, '2016-08-29 21:21:41.655-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (90, 'TELA DE CLIENTE', 'CLIENTE', 3, 1, '2016-08-29 21:21:51.057-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (91, 'TELA DE CLIENTE', 'CLIENTE', 3, 5, '2016-08-29 21:21:59.836-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (92, 'TELA DE CLIENTE', 'CLIENTE', 3, 8, '2016-08-29 21:22:10.454-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (93, 'TELA DE CLIENTE', 'CLIENTE', 3, 2, '2016-08-29 21:22:19.567-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (94, 'TELA DE CLIENTE', 'CLIENTE', 3, 10, '2016-08-29 21:22:28.959-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (95, 'TELA DE CLIENTE', 'CLIENTE', 3, 9, '2016-08-29 21:22:41.327-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (96, 'TELA DE CLIENTE', 'CLIENTE', 3, 6, '2016-08-29 21:22:48.926-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (97, 'TELA DE USUÁRIO', 'USUARIO', 3, 7, '2016-08-29 21:23:11.885-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (98, 'TELA DE USUÁRIO', 'USUARIO', 3, 1, '2016-08-29 21:23:21.567-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (99, 'TELA DE USUÁRIO', 'USUARIO', 3, 5, '2016-08-29 21:23:48.984-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (100, 'TELA DE USUÁRIO', 'USUARIO', 3, 8, '2016-08-29 21:24:01.693-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (101, 'TELA DE USUÁRIO', 'USUARIO', 3, 2, '2016-08-29 21:24:11.975-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (102, 'TELA DE USUÁRIO', 'USUARIO', 3, 10, '2016-08-29 21:24:19.399-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (103, 'TELA DE USUÁRIO', 'USUARIO', 3, 9, '2016-08-29 21:24:33.43-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (104, 'TELA DE USUÁRIO', 'USUARIO', 3, 6, '2016-08-29 21:24:39.63-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (105, 'RELATÓRIO DE PRODUÇÃO DIÁRIA', 'PRODUCAODIA', 3, 7, '2016-08-29 21:25:35.356-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (106, 'RELATÓRIO DE PRODUÇÃO DIÁRIA', 'PRODUCAODIA', 3, 8, '2016-08-29 21:25:55.279-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (107, 'RELATÓRIO DE OBSERVAÇÕES', 'OBSERVACAOPEDIDO', 3, 7, '2016-09-11 17:55:24.365-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (108, 'RELATÓRIO DE OBSERVAÇÕES', 'OBSERVACAOPEDIDO', 3, 8, '2016-09-11 17:55:45.52-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (109, 'RELATÓRIO DE LOGÍSTICA DIÁRIA', 'LOGISTICADIA', 3, 7, '2016-09-11 17:56:08.584-03', 1, NULL, NULL, 'S');
INSERT INTO tb_tela_acao (id_tela_acao, descricao, pagina, id_sistema, id_acao, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo) VALUES (110, 'RELATÓRIO DE LOGÍSTICA DIÁRIA', 'LOGISTICADIA', 3, 8, '2016-09-11 17:56:25.571-03', 1, NULL, NULL, 'S');


--
-- TOC entry 2322 (class 0 OID 70194)
-- Dependencies: 208
-- Data for Name: tb_tela_acao_log; Type: TABLE DATA; Schema: ped; Owner: -
--



--
-- TOC entry 2323 (class 0 OID 70197)
-- Dependencies: 209
-- Data for Name: tb_usuario; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_usuario (id_usuario, nome, login, senha, email, cpf, telefone, dat_nascimento, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo, id_especialidade, id_conselho, num_conselho, cep, logradouro, num_endereco, bairro, cidade, id_estado, complemento, referencia, id_grupo, flg_seguranca, id_cliente) VALUES (1, 'Usuário Administrador do Sistema', 'ADM', '202cb962ac59075b964b07152d234b70', 'contato@specialepanificacoes.com', '023.460.243-08', '(99) 99999-9999', '2016-11-24', '2016-01-26 12:32:14.057688-02', 1, '2016-11-23 23:51:22.32-02', 1, 'S', 1, 1, 111, '60743230', 'RUA 3', 295, 'PASSARÉ', 'FORTALEZA', 6, '', '', 11, 'S', NULL);


--
-- TOC entry 2324 (class 0 OID 70203)
-- Dependencies: 210
-- Data for Name: tb_usuario_log; Type: TABLE DATA; Schema: ped; Owner: -
--

INSERT INTO tb_usuario_log (id_usuario_log, id_usuario, nome, login, senha, email, cpf, telefone, dat_nascimento, dat_cadastro, id_usuario_cad, dat_alteracao, id_usuario_alt, flg_ativo, id_especialidade, id_conselho, num_conselho, cep, logradouro, num_endereco, bairro, cidade, id_estado, complemento, referencia, id_grupo, flg_seguranca, log_mapping) VALUES (2, 1, 'USUÁRIO ADMINISTRADOR DOS SISTEMAS DA A2DM', 'ADM', '202cb962ac59075b964b07152d234b70', 'CONTATO@A2DM.COM.BR', '023.460.243-08', '(99) 99999-9999', '1100-01-01', '2016-01-26 12:32:14.057688-02', 1, '2016-11-23 20:53:51.778-02', 1, 'S', 1, 1, 111, '60743230', 'RUA 3', 295, 'PASSARÉ', 'FORTALEZA', 6, '', '', 11, 'S', NULL);


--
-- TOC entry 2111 (class 2606 OID 70361)
-- Name: pk_acao; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_acao
    ADD CONSTRAINT pk_acao PRIMARY KEY (id_acao);


--
-- TOC entry 2141 (class 2606 OID 70556)
-- Name: pk_cliente; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_cliente
    ADD CONSTRAINT pk_cliente PRIMARY KEY (id_cliente);


--
-- TOC entry 2143 (class 2606 OID 70558)
-- Name: pk_cliente_produto; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_cliente_produto
    ADD CONSTRAINT pk_cliente_produto PRIMARY KEY (id_cliente_produto);


--
-- TOC entry 2113 (class 2606 OID 70363)
-- Name: pk_conselho; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_conselho
    ADD CONSTRAINT pk_conselho PRIMARY KEY (id_conselho);


--
-- TOC entry 2115 (class 2606 OID 70365)
-- Name: pk_especialidade; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_especialidade
    ADD CONSTRAINT pk_especialidade PRIMARY KEY (id_especialidade);


--
-- TOC entry 2117 (class 2606 OID 70367)
-- Name: pk_estado; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_estado
    ADD CONSTRAINT pk_estado PRIMARY KEY (id_estado);


--
-- TOC entry 2119 (class 2606 OID 70369)
-- Name: pk_grupo; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo
    ADD CONSTRAINT pk_grupo PRIMARY KEY (id_grupo);


--
-- TOC entry 2121 (class 2606 OID 70371)
-- Name: pk_grupo_log; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_log
    ADD CONSTRAINT pk_grupo_log PRIMARY KEY (id_grupo_log);


--
-- TOC entry 2123 (class 2606 OID 70373)
-- Name: pk_grupo_tela_acao; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao
    ADD CONSTRAINT pk_grupo_tela_acao PRIMARY KEY (id_grupo_tela_acao);


--
-- TOC entry 2125 (class 2606 OID 70375)
-- Name: pk_grupo_tela_acao_log; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao_log
    ADD CONSTRAINT pk_grupo_tela_acao_log PRIMARY KEY (id_grupo_tela_acao_log);


--
-- TOC entry 2127 (class 2606 OID 70377)
-- Name: pk_parametro; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_parametro
    ADD CONSTRAINT pk_parametro PRIMARY KEY (id_parametro);


--
-- TOC entry 2145 (class 2606 OID 70560)
-- Name: pk_pedido; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_pedido
    ADD CONSTRAINT pk_pedido PRIMARY KEY (id_pedido);


--
-- TOC entry 2147 (class 2606 OID 70562)
-- Name: pk_pedido_produto; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_pedido_produto
    ADD CONSTRAINT pk_pedido_produto PRIMARY KEY (id_pedido_produto);


--
-- TOC entry 2149 (class 2606 OID 70564)
-- Name: pk_produto; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_produto
    ADD CONSTRAINT pk_produto PRIMARY KEY (id_produto);


--
-- TOC entry 2151 (class 2606 OID 70566)
-- Name: pk_receita; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_receita
    ADD CONSTRAINT pk_receita PRIMARY KEY (id_receita);


--
-- TOC entry 2129 (class 2606 OID 70379)
-- Name: pk_recuperar_senha; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_recuperar_senha
    ADD CONSTRAINT pk_recuperar_senha PRIMARY KEY (id_recuperar_senha);


--
-- TOC entry 2131 (class 2606 OID 70381)
-- Name: pk_sistema; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_sistema
    ADD CONSTRAINT pk_sistema PRIMARY KEY (id_sistema);


--
-- TOC entry 2133 (class 2606 OID 70383)
-- Name: pk_tela_acao; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao
    ADD CONSTRAINT pk_tela_acao PRIMARY KEY (id_tela_acao);


--
-- TOC entry 2135 (class 2606 OID 70385)
-- Name: pk_tela_acao_log; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT pk_tela_acao_log PRIMARY KEY (id_tela_acao_log);


--
-- TOC entry 2137 (class 2606 OID 70387)
-- Name: pk_usuario; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT pk_usuario PRIMARY KEY (id_usuario);


--
-- TOC entry 2139 (class 2606 OID 70389)
-- Name: pk_usuario_log; Type: CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT pk_usuario_log PRIMARY KEY (id_usuario_log);


--
-- TOC entry 2153 (class 2606 OID 70937)
-- Name: fk_grupo_log_grupo; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_log
    ADD CONSTRAINT fk_grupo_log_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id_grupo);


--
-- TOC entry 2156 (class 2606 OID 70942)
-- Name: fk_grupo_tela_acao_grupo; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao
    ADD CONSTRAINT fk_grupo_tela_acao_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id_grupo);


--
-- TOC entry 2157 (class 2606 OID 70947)
-- Name: fk_grupo_tela_acao_grupo; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao_log
    ADD CONSTRAINT fk_grupo_tela_acao_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id_grupo);


--
-- TOC entry 2158 (class 2606 OID 70952)
-- Name: fk_grupo_tela_acao_log_grupo_tela_acao; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao_log
    ADD CONSTRAINT fk_grupo_tela_acao_log_grupo_tela_acao FOREIGN KEY (id_grupo_tela_acao) REFERENCES tb_grupo_tela_acao(id_grupo_tela_acao);


--
-- TOC entry 2160 (class 2606 OID 71122)
-- Name: fk_grupo_tela_acao_tela_acao; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao_log
    ADD CONSTRAINT fk_grupo_tela_acao_tela_acao FOREIGN KEY (id_tela_acao) REFERENCES tb_tela_acao(id_tela_acao);


--
-- TOC entry 2159 (class 2606 OID 70972)
-- Name: fk_grupo_tela_acao_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_tela_acao_log
    ADD CONSTRAINT fk_grupo_tela_acao_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2154 (class 2606 OID 70992)
-- Name: fk_grupo_usuario_cad; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_log
    ADD CONSTRAINT fk_grupo_usuario_cad FOREIGN KEY (id_usuario_cad) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2161 (class 2606 OID 70997)
-- Name: fk_recuperar_senha_usuario; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_recuperar_senha
    ADD CONSTRAINT fk_recuperar_senha_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2162 (class 2606 OID 71002)
-- Name: fk_tela_acao_acao; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao
    ADD CONSTRAINT fk_tela_acao_acao FOREIGN KEY (id_acao) REFERENCES tb_acao(id_acao);


--
-- TOC entry 2165 (class 2606 OID 71007)
-- Name: fk_tela_acao_acao; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT fk_tela_acao_acao FOREIGN KEY (id_acao) REFERENCES tb_acao(id_acao);


--
-- TOC entry 2166 (class 2606 OID 71012)
-- Name: fk_tela_acao_log_tela_acao; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT fk_tela_acao_log_tela_acao FOREIGN KEY (id_tela_acao) REFERENCES tb_tela_acao(id_tela_acao);


--
-- TOC entry 2163 (class 2606 OID 71017)
-- Name: fk_tela_acao_sistema; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao
    ADD CONSTRAINT fk_tela_acao_sistema FOREIGN KEY (id_sistema) REFERENCES tb_sistema(id_sistema);


--
-- TOC entry 2167 (class 2606 OID 71022)
-- Name: fk_tela_acao_sistema; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT fk_tela_acao_sistema FOREIGN KEY (id_sistema) REFERENCES tb_sistema(id_sistema);


--
-- TOC entry 2164 (class 2606 OID 71027)
-- Name: fk_tela_acao_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao
    ADD CONSTRAINT fk_tela_acao_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2168 (class 2606 OID 71032)
-- Name: fk_tela_acao_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT fk_tela_acao_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2169 (class 2606 OID 71042)
-- Name: fk_tela_acao_usuario_cad; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_tela_acao_log
    ADD CONSTRAINT fk_tela_acao_usuario_cad FOREIGN KEY (id_usuario_cad) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2152 (class 2606 OID 71047)
-- Name: fk_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo
    ADD CONSTRAINT fk_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2155 (class 2606 OID 71052)
-- Name: fk_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_grupo_log
    ADD CONSTRAINT fk_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2170 (class 2606 OID 71057)
-- Name: fk_usuario_cliente; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_cliente FOREIGN KEY (id_cliente) REFERENCES tb_cliente(id_cliente);


--
-- TOC entry 2171 (class 2606 OID 71062)
-- Name: fk_usuario_conselho; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_conselho FOREIGN KEY (id_conselho) REFERENCES tb_conselho(id_conselho);


--
-- TOC entry 2172 (class 2606 OID 71067)
-- Name: fk_usuario_especialidade; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_especialidade FOREIGN KEY (id_especialidade) REFERENCES tb_especialidade(id_especialidade);


--
-- TOC entry 2173 (class 2606 OID 71072)
-- Name: fk_usuario_estado; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_estado FOREIGN KEY (id_estado) REFERENCES tb_estado(id_estado);


--
-- TOC entry 2177 (class 2606 OID 71077)
-- Name: fk_usuario_estado; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT fk_usuario_estado FOREIGN KEY (id_estado) REFERENCES tb_estado(id_estado);


--
-- TOC entry 2174 (class 2606 OID 71082)
-- Name: fk_usuario_grupo; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id_grupo);


--
-- TOC entry 2178 (class 2606 OID 71087)
-- Name: fk_usuario_grupo; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT fk_usuario_grupo FOREIGN KEY (id_grupo) REFERENCES tb_grupo(id_grupo);


--
-- TOC entry 2179 (class 2606 OID 71092)
-- Name: fk_usuario_log_usuario; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT fk_usuario_log_usuario FOREIGN KEY (id_usuario) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2175 (class 2606 OID 71097)
-- Name: fk_usuario_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2180 (class 2606 OID 71102)
-- Name: fk_usuario_usuario_alt; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT fk_usuario_usuario_alt FOREIGN KEY (id_usuario_alt) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2176 (class 2606 OID 71107)
-- Name: fk_usuario_usuario_cad; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario
    ADD CONSTRAINT fk_usuario_usuario_cad FOREIGN KEY (id_usuario_cad) REFERENCES tb_usuario(id_usuario);


--
-- TOC entry 2181 (class 2606 OID 71112)
-- Name: fk_usuario_usuario_cad; Type: FK CONSTRAINT; Schema: ped; Owner: -
--

ALTER TABLE ONLY tb_usuario_log
    ADD CONSTRAINT fk_usuario_usuario_cad FOREIGN KEY (id_usuario_cad) REFERENCES tb_usuario(id_usuario);


-- Completed on 2016-11-25 21:59:45

--
-- PostgreSQL database dump complete
--

