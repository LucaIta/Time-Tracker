--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.2
-- Dumped by pg_dump version 9.5.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

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
-- Name: lap_times; Type: TABLE; Schema: public; Owner: noahkittleson
--

CREATE TABLE lap_times (
    id integer NOT NULL,
    start_time bigint,
    end_time bigint,
    task_id integer,
    run_id integer
);


ALTER TABLE lap_times OWNER TO noahkittleson;

--
-- Name: lap_times_id_seq; Type: SEQUENCE; Schema: public; Owner: noahkittleson
--

CREATE SEQUENCE lap_times_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lap_times_id_seq OWNER TO noahkittleson;

--
-- Name: lap_times_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: noahkittleson
--

ALTER SEQUENCE lap_times_id_seq OWNED BY lap_times.id;


--
-- Name: routines; Type: TABLE; Schema: public; Owner: noahkittleson
--

CREATE TABLE routines (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE routines OWNER TO noahkittleson;

--
-- Name: routines_id_seq; Type: SEQUENCE; Schema: public; Owner: noahkittleson
--

CREATE SEQUENCE routines_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE routines_id_seq OWNER TO noahkittleson;

--
-- Name: routines_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: noahkittleson
--

ALTER SEQUENCE routines_id_seq OWNED BY routines.id;


--
-- Name: routines_tasks; Type: TABLE; Schema: public; Owner: noahkittleson
--

CREATE TABLE routines_tasks (
    id integer NOT NULL,
    routine_id integer,
    task_id integer
);


ALTER TABLE routines_tasks OWNER TO noahkittleson;

--
-- Name: routines_tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: noahkittleson
--

CREATE SEQUENCE routines_tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE routines_tasks_id_seq OWNER TO noahkittleson;

--
-- Name: routines_tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: noahkittleson
--

ALTER SEQUENCE routines_tasks_id_seq OWNED BY routines_tasks.id;


--
-- Name: runs; Type: TABLE; Schema: public; Owner: noahkittleson
--

CREATE TABLE runs (
    id integer NOT NULL,
    routine_id integer,
    task_index integer,
    lap_id integer
);


ALTER TABLE runs OWNER TO noahkittleson;

--
-- Name: runs_id_seq; Type: SEQUENCE; Schema: public; Owner: noahkittleson
--

CREATE SEQUENCE runs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE runs_id_seq OWNER TO noahkittleson;

--
-- Name: runs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: noahkittleson
--

ALTER SEQUENCE runs_id_seq OWNED BY runs.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: noahkittleson
--

CREATE TABLE tasks (
    id integer NOT NULL,
    name character varying,
    goal_time bigint
);


ALTER TABLE tasks OWNER TO noahkittleson;

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: noahkittleson
--

CREATE SEQUENCE tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_id_seq OWNER TO noahkittleson;

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: noahkittleson
--

ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY lap_times ALTER COLUMN id SET DEFAULT nextval('lap_times_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY routines ALTER COLUMN id SET DEFAULT nextval('routines_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY routines_tasks ALTER COLUMN id SET DEFAULT nextval('routines_tasks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY runs ALTER COLUMN id SET DEFAULT nextval('runs_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq'::regclass);


--
-- Data for Name: lap_times; Type: TABLE DATA; Schema: public; Owner: noahkittleson
--

COPY lap_times (id, start_time, end_time, task_id, run_id) FROM stdin;
1	1463699477773	1463699559975	1	3
2	1463699739851	1463699741790	1	3
3	1463700052671	1463700053975	2	4
4	1463713629958	1463713654344	1	6
5	1463713654379	\N	2	6
6	1463718815576	1463718822540	1	2
7	1463718822574	1463718834412	2	2
8	1463719633174	\N	2	3
9	1463719668815	1463719690871	1	7
11	1463720435634	1463720444425	1	8
12	1463720444459	1463720455392	2	8
13	1463720455421	\N	3	8
10	1463719690908	1463721554627	2	7
14	1463721554778	1463721635164	3	7
15	1463721635222	1463721658175	4	7
16	1463721658214	1463721661406	5	7
17	1463726502498	\N	1	9
18	1463726545913	1463726549535	1	9
19	1463726549573	1463726553474	2	9
20	1463726553508	1463726564244	3	9
21	1463726564278	1463726569796	4	9
22	1463726569831	1463726578991	5	9
23	1463727449356	1463727457566	1	10
24	1463727457597	1463727460363	2	10
25	1463727460397	1463727461482	3	10
26	1463727461609	1463727463047	4	10
27	1463727463090	1463727469370	5	10
\.


--
-- Name: lap_times_id_seq; Type: SEQUENCE SET; Schema: public; Owner: noahkittleson
--

SELECT pg_catalog.setval('lap_times_id_seq', 27, true);


--
-- Data for Name: routines; Type: TABLE DATA; Schema: public; Owner: noahkittleson
--

COPY routines (id, name) FROM stdin;
1	RoutineTest
\.


--
-- Name: routines_id_seq; Type: SEQUENCE SET; Schema: public; Owner: noahkittleson
--

SELECT pg_catalog.setval('routines_id_seq', 1, true);


--
-- Data for Name: routines_tasks; Type: TABLE DATA; Schema: public; Owner: noahkittleson
--

COPY routines_tasks (id, routine_id, task_id) FROM stdin;
1	1	1
2	1	2
3	1	3
4	1	4
5	1	5
\.


--
-- Name: routines_tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: noahkittleson
--

SELECT pg_catalog.setval('routines_tasks_id_seq', 5, true);


--
-- Data for Name: runs; Type: TABLE DATA; Schema: public; Owner: noahkittleson
--

COPY runs (id, routine_id, task_index, lap_id) FROM stdin;
1	1	\N	\N
4	1	\N	\N
5	1	\N	\N
6	1	1	5
2	1	1	7
3	1	1	8
8	1	2	13
7	1	4	16
9	1	4	22
10	1	4	27
\.


--
-- Name: runs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: noahkittleson
--

SELECT pg_catalog.setval('runs_id_seq', 10, true);


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: noahkittleson
--

COPY tasks (id, name, goal_time) FROM stdin;
1	Jumping jack 50 times	50
2	100 jacks	6
3	jacks x 150	25
4	pumpkin test	2
5	Testing Task Creation	3723000
\.


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: noahkittleson
--

SELECT pg_catalog.setval('tasks_id_seq', 5, true);


--
-- Name: lap_times_pkey; Type: CONSTRAINT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY lap_times
    ADD CONSTRAINT lap_times_pkey PRIMARY KEY (id);


--
-- Name: routines_pkey; Type: CONSTRAINT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY routines
    ADD CONSTRAINT routines_pkey PRIMARY KEY (id);


--
-- Name: routines_tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY routines_tasks
    ADD CONSTRAINT routines_tasks_pkey PRIMARY KEY (id);


--
-- Name: runs_pkey; Type: CONSTRAINT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY runs
    ADD CONSTRAINT runs_pkey PRIMARY KEY (id);


--
-- Name: tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: noahkittleson
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: noahkittleson
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM noahkittleson;
GRANT ALL ON SCHEMA public TO noahkittleson;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

