--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: lap_times; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE lap_times (
    id integer NOT NULL,
    start_time bigint,
    end_time bigint,
    task_id integer
);


ALTER TABLE lap_times OWNER TO "Guest";

--
-- Name: lap_times_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE lap_times_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lap_times_id_seq OWNER TO "Guest";

--
-- Name: lap_times_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE lap_times_id_seq OWNED BY lap_times.id;


--
-- Name: routines; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE routines (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE routines OWNER TO "Guest";

--
-- Name: routines_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE routines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE routines_id_seq OWNER TO "Guest";

--
-- Name: routines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE routines_id_seq OWNED BY routines.id;


--
-- Name: routines_tasks; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE routines_tasks (
    id integer NOT NULL,
    routine_id integer,
    task_id integer
);


ALTER TABLE routines_tasks OWNER TO "Guest";

--
-- Name: routines_tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE routines_tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE routines_tasks_id_seq OWNER TO "Guest";

--
-- Name: routines_tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE routines_tasks_id_seq OWNED BY routines_tasks.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE tasks (
    id integer NOT NULL,
    name character varying,
    goal_time bigint
);


ALTER TABLE tasks OWNER TO "Guest";

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_id_seq OWNER TO "Guest";

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY lap_times ALTER COLUMN id SET DEFAULT nextval('lap_times_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY routines ALTER COLUMN id SET DEFAULT nextval('routines_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY routines_tasks ALTER COLUMN id SET DEFAULT nextval('routines_tasks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq'::regclass);


--
-- Data for Name: lap_times; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY lap_times (id, start_time, end_time, task_id) FROM stdin;
\.


--
-- Name: lap_times_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('lap_times_id_seq', 1, false);


--
-- Data for Name: routines; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY routines (id, name) FROM stdin;
\.


--
-- Name: routines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('routines_id_seq', 1, false);


--
-- Data for Name: routines_tasks; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY routines_tasks (id, routine_id, task_id) FROM stdin;
\.


--
-- Name: routines_tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('routines_tasks_id_seq', 1, false);


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY tasks (id, name, goal_time) FROM stdin;
1	Jumping jack 50 times	50
\.


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('tasks_id_seq', 1, true);


--
-- Name: lap_times_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY lap_times
    ADD CONSTRAINT lap_times_pkey PRIMARY KEY (id);


--
-- Name: routines_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY routines
    ADD CONSTRAINT routines_pkey PRIMARY KEY (id);


--
-- Name: routines_tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY routines_tasks
    ADD CONSTRAINT routines_tasks_pkey PRIMARY KEY (id);


--
-- Name: tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

