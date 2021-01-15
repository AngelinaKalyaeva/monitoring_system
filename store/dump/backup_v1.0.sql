--
-- PostgreSQL database dump
--

-- Dumped from database version 11.10
-- Dumped by pg_dump version 11.10

-- Started on 2020-12-10 20:36:48

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

DROP DATABASE marketdb;
--
-- TOC entry 2849 (class 1262 OID 16393)
-- Name: marketdb; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE marketdb WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Russian_Russia.1251' LC_CTYPE = 'Russian_Russia.1251';


ALTER DATABASE marketdb OWNER TO postgres;

\connect marketdb

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
-- TOC entry 2841 (class 0 OID 16411)
-- Dependencies: 199
-- Data for Name: Courier; Type: TABLE DATA; Schema: production; Owner: postgres
--

INSERT INTO production."Courier" (id, name, phone_number, status) VALUES (1, 'Иванов Иван Иванович', '+7 910 872 63 47', 'free');
INSERT INTO production."Courier" (id, name, phone_number, status) VALUES (2, 'Петров Петр Петрович', '+7 890 728 37 29', 'free');


--
-- TOC entry 2840 (class 0 OID 16403)
-- Dependencies: 198
-- Data for Name: Customer; Type: TABLE DATA; Schema: production; Owner: postgres
--

INSERT INTO production."Customer" (id, name, email, phone_number) VALUES (1, 'Семенов Сергей', 'test@mail.ru', '+7 839 666 37 82');


--
-- TOC entry 2842 (class 0 OID 16419)
-- Dependencies: 200
-- Data for Name: Order; Type: TABLE DATA; Schema: production; Owner: postgres
--

INSERT INTO production."Order" (id, order_date, receive_date, adress, status, courier_id, customer_id, price, currency) VALUES (1, '2020-10-19 09:23:54', '2020-10-19 10:23:54', 'г. Санкт-Петербург, ул.Торжковская, д.15, кв. 264', 'done', 1, 1, 215, 'RUR');


--
-- TOC entry 2843 (class 0 OID 16437)
-- Dependencies: 201
-- Data for Name: OrderDetail; Type: TABLE DATA; Schema: production; Owner: postgres
--

INSERT INTO production."OrderDetail" (order_id, product_id, count) VALUES (1, 1, 1);
INSERT INTO production."OrderDetail" (order_id, product_id, count) VALUES (1, 5, 3);
INSERT INTO production."OrderDetail" (order_id, product_id, count) VALUES (1, 6, 1);


--
-- TOC entry 2839 (class 0 OID 16395)
-- Dependencies: 197
-- Data for Name: Product; Type: TABLE DATA; Schema: production; Owner: postgres
--

INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (1, 'Xлеб', 40, 'RUR', 'Черный хлеб', true, 10);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (2, 'Вода', 20, 'RUR', 'Вода без газа', true, 20);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (3, 'Молоко', 80, 'RUR', 'Свежее молоко', true, 35);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (4, 'Яблоко', 30, 'RUR', 'Яблоко зеленое', true, 100);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (5, 'Апельсин', 50, 'RUR', 'Апельсин свежий', true, 57);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (6, 'Мандарин', 25, 'RUR', 'Новогодний мандарин', true, 230);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (7, 'Кокос', 200, 'RUR', 'Самый вкусный кокос', true, 10);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (8, 'Огурец', 25, 'RUR', 'Свежий огурец', true, 200);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (9, 'Картофель', 15, 'RUR', 'Мытый картофель', true, 150);
INSERT INTO production."Product" (id, name, price, currency, description, availability, count) VALUES (10, 'Морковь', 17, 'RUR', 'Морковь мытая', true, 220);


-- Completed on 2020-12-10 20:36:54

--
-- PostgreSQL database dump complete
--

