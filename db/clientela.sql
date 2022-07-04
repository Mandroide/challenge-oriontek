--
-- PostgreSQL database dump
--

-- Dumped from database version 12.11 (Ubuntu 12.11-0ubuntu0.20.04.1)
-- Dumped by pg_dump version 12.11 (Ubuntu 12.11-0ubuntu0.20.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

create function direccion_mostrar()
    returns TABLE(id integer, clienteid integer, nombre character varying,
    codigopostal character varying, ciudad character varying, pais character varying, estatus character
                 )
    language plpgsql
as
$$
BEGIN
    RETURN QUERY
        SELECT DireccionId,
               ClienteId,
               DireccionNombre,
               DireccionCodigoPostal,
               DireccionCiudad,
               DIreccionPais,
               DireccionEstatus
        FROM ONLY Direccion
        ORDER BY DireccionId DESC, Clienteid DESC
        LIMIT 200;
END;
$$;

alter function cliente_mostrar() owner to postgres;

DO
$do$
    BEGIN
        IF NOT EXISTS (
                SELECT                       -- SELECT list can stay empty for this
                FROM   pg_catalog.pg_roles
                WHERE  rolname = 'usuario') THEN

            CREATE ROLE usuario LOGIN ENCRYPTED PASSWORD '1234';
        END IF;
    END
$do$;

DROP DATABASE IF EXISTS clientela;
--
-- Name: clientela; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE clientela WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.UTF-8' LC_CTYPE = 'en_US.UTF-8';


ALTER DATABASE clientela OWNER TO postgres;

\connect clientela

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: cliente_buscar(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cliente_buscar(nom character varying) RETURNS TABLE(id integer, nombre character varying, email character varying, telefono character varying, estatus character)
    LANGUAGE plpgsql
AS $$ BEGIN
    RETURN QUERY
        SELECT ClienteId id,
               ClienteNombre nombre,
               ClienteEmail email,
               ClienteTelefono telefono,
               ClienteEstatus estatus
        FROM Cliente
        WHERE ClienteNombre LIKE CONCAT(nom, '%')
        ORDER BY ClienteId DESC
        LIMIT 200;
END;$$;


ALTER FUNCTION public.cliente_buscar(nom character varying) OWNER TO postgres;

--
-- Name: cliente_buscaractivos(character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cliente_buscaractivos(nom character varying) RETURNS TABLE(id integer, nombre character varying, email character varying, telefono character varying)
    LANGUAGE plpgsql
AS $$BEGIN
    RETURN QUERY
        SELECT ClienteId AS Id,
               ClienteNombre AS Nombre,
               ClienteEmail AS Email,
               ClienteTelefono AS Telefono
        FROM Cliente
        WHERE ClienteNombre LIKE CONCAT(nom, '%')
          AND ClienteEstatus = 'A';
END;$$;


ALTER FUNCTION public.cliente_buscaractivos(nom character varying) OWNER TO postgres;

--
-- Name: cliente_mostrar(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cliente_mostrar() RETURNS TABLE(id integer, nombre character varying, email character varying, telefono character varying, estatus character)
    LANGUAGE plpgsql
AS $$ BEGIN
    RETURN QUERY
        SELECT "ClienteId",
               "ClienteNombre",
               "ClienteEmail",
               "ClienteTelefono",
               "ClienteEstatus"
        FROM ONLY "Cliente"
        ORDER BY "ClienteID" DESC
        LIMIT 200;
END;$$;


ALTER FUNCTION public.cliente_mostrar() OWNER TO postgres;

--
-- Name: cliente_mostraractivos(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.cliente_mostraractivos() RETURNS TABLE(id integer, nombre character varying, email character varying, telefono character varying, estatus character)
    LANGUAGE plpgsql
AS $$ BEGIN
    RETURN QUERY
        SELECT "ClienteId",
               "ClienteNombre",
               "ClienteEmail",
               "ClienteTelefono",
               "ClienteEstatus"
        FROM "Cliente"
        WHERE "ClienteEstatus" = 'A'
        ORDER BY "ClienteId" DESC
        LIMIT 200;
END;$$;


ALTER FUNCTION public.cliente_mostraractivos() OWNER TO postgres;

--
-- Name: direccion_insertar(integer, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.direccion_insertar(clienteid integer, nombre character varying, codigopostal character varying, ciudad character varying, pais character varying) RETURNS void
    LANGUAGE plpgsql
AS $$ BEGIN
    INSERT INTO direccion(clienteid, direccionnombre, direccioncodigopostal, direccionciudad, direccionpais)
    VALUES
        (
            clienteid, nombre, codigoPostal, ciudad, pais);
END;   $$;


ALTER FUNCTION public.direccion_insertar(clienteid integer, nombre character varying, codigopostal character varying, ciudad character varying, pais character varying) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: Cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Cliente" (
                                  "ClienteId" integer NOT NULL,
                                  "ClienteNombre" character varying(100) NOT NULL,
                                  "ClienteEmail" character varying(254),
                                  "ClienteTelefono" character varying(15) NOT NULL,
                                  "ClienteEstatus" character(1) NOT NULL
);


ALTER TABLE public."Cliente" OWNER TO postgres;

--
-- Name: Cliente_ClienteId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Cliente_ClienteId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Cliente_ClienteId_seq" OWNER TO postgres;

--
-- Name: Cliente_ClienteId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Cliente_ClienteId_seq" OWNED BY public."Cliente"."ClienteId";


--
-- Name: Direccion; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."Direccion" (
                                    "DireccionId" integer NOT NULL,
                                    "ClienteId" integer,
                                    "DireccionNombre" character varying(60) NOT NULL,
                                    "DireccionCiudad" character varying(15) NOT NULL,
                                    "DireccionCodigoPostal" character varying(10) NOT NULL,
                                    "DireccionPais" character varying(55) NOT NULL
);


ALTER TABLE public."Direccion" OWNER TO postgres;

--
-- Name: Direccion_DireccionId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."Direccion_DireccionId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public."Direccion_DireccionId_seq" OWNER TO postgres;

--
-- Name: Direccion_DireccionId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."Direccion_DireccionId_seq" OWNED BY public."Direccion"."DireccionId";


--
-- Name: Cliente ClienteId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cliente" ALTER COLUMN "ClienteId" SET DEFAULT nextval('public."Cliente_ClienteId_seq"'::regclass);


--
-- Name: Direccion DireccionId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Direccion" ALTER COLUMN "DireccionId" SET DEFAULT nextval('public."Direccion_DireccionId_seq"'::regclass);


--
-- Data for Name: Cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Cliente" ("ClienteId", "ClienteNombre", "ClienteEmail", "ClienteTelefono", "ClienteEstatus") FROM stdin;
\.


--
-- Data for Name: Direccion; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public."Direccion" ("DireccionId", "DireccionNombre", "DireccionCiudad", "DireccionCodigoPostal", "DireccionPais", "ClienteId") FROM stdin;
\.


--
-- Name: Cliente_ClienteId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Cliente_ClienteId_seq"', 1, false);


--
-- Name: Direccion_DireccionId_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."Direccion_DireccionId_seq"', 1, false);


--
-- Name: Cliente cliente_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Cliente"
    ADD CONSTRAINT cliente_pk PRIMARY KEY ("ClienteId");


--
-- Name: Direccion direccion_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Direccion"
    ADD CONSTRAINT direccion_pk PRIMARY KEY ("DireccionId");


--
-- Name: cliente_clienteemail_uindex; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX cliente_clienteemail_uindex ON public."Cliente" USING btree ("ClienteEmail");


--
-- Name: Direccion direccion_cliente_clienteid_fk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."Direccion"
    ADD CONSTRAINT direccion_cliente_clienteid_fk FOREIGN KEY ("ClienteId") REFERENCES public."Cliente"("ClienteId") ON DELETE CASCADE;


--
-- Name: DATABASE clientela; Type: ACL; Schema: -; Owner: postgres
--

GRANT CONNECT ON DATABASE clientela TO usuario;


--
-- Name: TABLE "Cliente"; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,REFERENCES,TRIGGER,UPDATE ON TABLE public."Cliente" TO usuario;


--
-- Name: TABLE "Direccion"; Type: ACL; Schema: public; Owner: postgres
--

GRANT SELECT,INSERT,REFERENCES,TRIGGER,UPDATE ON TABLE public."Direccion" TO usuario;



--
-- PostgreSQL database dump complete
--

