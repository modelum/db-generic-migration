--
-- PostgreSQL database dump
--

\restrict WIn7E7EIqkJ3Kp1iPQwmVEpCZjNwmyKOO1FQKHXYszbS2JeMBdUmMnHZDIvie8m

-- Dumped from database version 16.14 (Debian 16.14-1.pgdg13+1)
-- Dumped by pg_dump version 16.14 (Debian 16.14-1.pgdg13+1)

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
-- Name: dbms_random(integer, integer); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.dbms_random(integer, integer) RETURNS integer
    LANGUAGE plpgsql STRICT
    AS $_$
                DECLARE
                start_int ALIAS FOR $1;
                end_int ALIAS FOR $2;
                BEGIN
                RETURN trunc(random() * (end_int-start_int + 1) + start_int);
                END;
                $_$;


ALTER FUNCTION public.dbms_random(integer, integer) OWNER TO music;

--
-- Name: delivery(integer, integer); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.delivery(integer, integer) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
                DECLARE
                d_w_id		ALIAS FOR $1;
                d_o_carrier_id  ALIAS FOR $2;
                loop_counter	SMALLINT;
                d_id_in_array	SMALLINT[] := ARRAY[1,2,3,4,5,6,7,8,9,10];
                d_id_array		SMALLINT[];
                o_id_array 		INT[];
                c_id_array 		INT[];
                order_count		SMALLINT;
                sum_amounts     NUMERIC[];

                customer_count INT;
                BEGIN
                WITH new_order_delete AS (
                DELETE
                FROM new_order as del_new_order
                USING UNNEST(d_id_in_array) AS d_ids
                WHERE no_d_id = d_ids
                AND no_w_id = d_w_id
                AND del_new_order.no_o_id = (select min (select_new_order.no_o_id)
                from new_order as select_new_order
                where no_d_id = d_ids
                and no_w_id = d_w_id)
                RETURNING del_new_order.no_o_id, del_new_order.no_d_id
                )
                SELECT array_agg(no_o_id), array_agg(no_d_id)
                FROM new_order_delete
                INTO o_id_array, d_id_array;

                UPDATE orders
                SET o_carrier_id = d_o_carrier_id
                FROM UNNEST(o_id_array, d_id_array) AS ids(o_id, d_id)
                WHERE orders.o_id = ids.o_id
                AND o_d_id = ids.d_id
                AND o_w_id = d_w_id;

                WITH order_line_update AS (
                UPDATE order_line
                SET ol_delivery_d = current_timestamp
                FROM UNNEST(o_id_array, d_id_array) AS ids(o_id, d_id)
                WHERE ol_o_id = ids.o_id
                AND ol_d_id = ids.d_id
                AND ol_w_id = d_w_id
                RETURNING ol_d_id, ol_o_id, ol_amount
                )
                SELECT array_agg(ol_d_id), array_agg(c_id), array_agg(sum_amount)
                FROM ( SELECT ol_d_id,
                ( SELECT DISTINCT o_c_id FROM orders WHERE o_id = ol_o_id AND o_d_id = ol_d_id AND o_w_id = d_w_id) AS c_id,
                sum(ol_amount) AS sum_amount
                FROM order_line_update
                GROUP BY ol_d_id, ol_o_id ) AS inner_sum
                INTO d_id_array, c_id_array, sum_amounts;

                UPDATE customer
                SET c_balance = COALESCE(c_balance,0) + ids_and_sums.sum_amounts
                FROM UNNEST(d_id_array, c_id_array, sum_amounts) AS ids_and_sums(d_id, c_id, sum_amounts)
                WHERE customer.c_id = ids_and_sums.c_id
                AND c_d_id = ids_and_sums.d_id
                AND c_w_id = d_w_id;

                RETURN 1;

                EXCEPTION
                WHEN serialization_failure OR deadlock_detected OR no_data_found
                THEN ROLLBACK;
                END;
                $_$;


ALTER FUNCTION public.delivery(integer, integer) OWNER TO music;

--
-- Name: neword(integer, integer, integer, integer, integer, integer); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.neword(integer, integer, integer, integer, integer, integer) RETURNS numeric
    LANGUAGE plpgsql
    AS $_$
                DECLARE
                no_w_id		        ALIAS FOR $1;
                no_max_w_id	        ALIAS FOR $2;
                no_d_id		        ALIAS FOR $3;
                no_c_id		        ALIAS FOR $4;
                no_o_ol_cnt	        ALIAS FOR $5;
                no_d_next_o_id	    ALIAS FOR $6;
                no_c_discount	    NUMERIC;
                no_c_last			VARCHAR;
                no_c_credit			VARCHAR;
                no_d_tax			NUMERIC;
                no_w_tax			NUMERIC;
                no_s_quantity		NUMERIC;
                no_o_all_local		SMALLINT;
                rbk					SMALLINT;
                item_id_array 		INT[];
                supply_wid_array	INT[];
                quantity_array		SMALLINT[];
                order_line_array	SMALLINT[];
                stock_dist_array	CHAR(24)[];
                s_quantity_array	SMALLINT[];
                price_array			NUMERIC(5,2)[];
                amount_array		NUMERIC(5,2)[];
                BEGIN
                no_o_all_local := 1;
                SELECT c_discount, c_last, c_credit, w_tax
                INTO no_c_discount, no_c_last, no_c_credit, no_w_tax
                FROM customer, warehouse
                WHERE warehouse.w_id = no_w_id AND customer.c_w_id = no_w_id AND customer.c_d_id = no_d_id AND customer.c_id = no_c_id;

                --#2.4.1.4
                rbk := round(DBMS_RANDOM(1,100));
                --#2.4.1.5
                FOR loop_counter IN 1 .. no_o_ol_cnt
                LOOP
                IF ((loop_counter = no_o_ol_cnt) AND (rbk = 1))
                THEN
                item_id_array[loop_counter] := 100001;
                ELSE
                item_id_array[loop_counter] := round(DBMS_RANDOM(1,100000));
                END IF;

                --#2.4.1.5.2
                IF ( round(DBMS_RANDOM(1,100)) > 1 )
                THEN
                supply_wid_array[loop_counter] := no_w_id;
                ELSE
                no_o_all_local := 0;
                supply_wid_array[loop_counter] := 1 + MOD(CAST (no_w_id + round(DBMS_RANDOM(0,no_max_w_id-1)) AS INT), no_max_w_id);
                END IF;

                --#2.4.1.5.3
                quantity_array[loop_counter] := round(DBMS_RANDOM(1,10));
                order_line_array[loop_counter] := loop_counter;
                END LOOP;

                UPDATE district SET d_next_o_id = d_next_o_id + 1 WHERE d_id = no_d_id AND d_w_id = no_w_id RETURNING d_next_o_id - 1, d_tax INTO no_d_next_o_id, no_d_tax;

                INSERT INTO ORDERS (o_id, o_d_id, o_w_id, o_c_id, o_entry_d, o_ol_cnt, o_all_local) VALUES (no_d_next_o_id, no_d_id, no_w_id, no_c_id, current_timestamp, no_o_ol_cnt, no_o_all_local);
                INSERT INTO NEW_ORDER (no_o_id, no_d_id, no_w_id) VALUES (no_d_next_o_id, no_d_id, no_w_id);

                SELECT array_agg ( i_price )
                INTO price_array
                FROM UNNEST(item_id_array) item_id
                LEFT JOIN item ON i_id = item_id;

                IF no_d_id = 1
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_01 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 2
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_02 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 3
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_03 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 4
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_04 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 5
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_05 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 6
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_06 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 7
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_07 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 8
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_08 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 9
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_09 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                ELSIF no_d_id = 10
                THEN
                WITH stock_update AS (
                UPDATE stock
                SET s_quantity = ( CASE WHEN s_quantity < (item_stock.quantity + 10) THEN s_quantity + 91 ELSE s_quantity END) - item_stock.quantity
                FROM UNNEST(item_id_array, supply_wid_array, quantity_array, price_array)
                AS item_stock (item_id, supply_wid, quantity, price)
                WHERE stock.s_i_id = item_stock.item_id
                AND stock.s_w_id = item_stock.supply_wid
                AND stock.s_w_id = ANY(supply_wid_array)
                RETURNING stock.s_dist_10 as s_dist, stock.s_quantity, ( item_stock.quantity + item_stock.price * ( 1 + no_w_tax + no_d_tax ) * ( 1 - no_c_discount ) ) amount
                )
                SELECT array_agg ( s_dist ), array_agg ( s_quantity ), array_agg ( amount )
                FROM stock_update
                INTO stock_dist_array, s_quantity_array, amount_array;
                END IF;

                INSERT INTO order_line (ol_o_id, ol_d_id, ol_w_id, ol_number, ol_i_id, ol_supply_w_id, ol_quantity, ol_amount, ol_dist_info)
                SELECT no_d_next_o_id,
                no_d_id,
                no_w_id,
                data.line_number,
                data.item_id,
                data.supply_wid,
                data.quantity,
                data.amount,
                data.stock_dist
                FROM UNNEST(order_line_array,
                item_id_array,
                supply_wid_array,
                quantity_array,
                amount_array,
                stock_dist_array)
                AS data( line_number, item_id, supply_wid, quantity, amount, stock_dist);

                no_s_quantity := 0;
                FOR loop_counter IN 1 .. no_o_ol_cnt
                LOOP
                no_s_quantity := no_s_quantity + CAST( amount_array[loop_counter] AS NUMERIC);
                END LOOP;

                RETURN no_s_quantity;

                EXCEPTION
                WHEN serialization_failure OR deadlock_detected OR no_data_found
                THEN ROLLBACK;
                END;
                $_$;


ALTER FUNCTION public.neword(integer, integer, integer, integer, integer, integer) OWNER TO music;

--
-- Name: ostat(integer, integer, integer, integer, character varying); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.ostat(integer, integer, integer, integer, character varying) RETURNS SETOF record
    LANGUAGE plpgsql
    AS $_$
                DECLARE
                os_w_id		ALIAS FOR $1;
                os_d_id		ALIAS FOR $2;
                os_c_id	 	ALIAS FOR $3;
                byname		ALIAS FOR $4;
                os_c_last	ALIAS FOR $5;
                out_os_c_id	INTEGER;
                out_os_c_last	VARCHAR;
                os_c_first	VARCHAR;
                os_c_middle	VARCHAR;
                os_c_balance	NUMERIC;
                os_o_id		INTEGER;
                os_entdate	TIMESTAMP;
                os_o_carrier_id	INTEGER;
                os_ol 		RECORD;
                namecnt		INTEGER;
                c_name CURSOR FOR
                SELECT c_balance, c_first, c_middle, c_id
                FROM customer
                WHERE c_last = os_c_last AND c_d_id = os_d_id AND c_w_id = os_w_id
                ORDER BY c_first;
                BEGIN
                IF ( byname = 1 )
                THEN
                SELECT count(c_id) INTO namecnt
                FROM customer
                WHERE c_last = os_c_last AND c_d_id = os_d_id AND c_w_id = os_w_id;
                IF ( MOD (namecnt, 2) = 1 )
                THEN
                namecnt := (namecnt + 1);
                END IF;
                OPEN c_name;
                FOR loop_counter IN 0 .. cast((namecnt/2) AS INTEGER)
                LOOP
                FETCH c_name
                INTO os_c_balance, os_c_first, os_c_middle, os_c_id;
                END LOOP;
                close c_name;
                ELSE
                SELECT c_balance, c_first, c_middle, c_last
                INTO os_c_balance, os_c_first, os_c_middle, os_c_last
                FROM customer
                WHERE c_id = os_c_id AND c_d_id = os_d_id AND c_w_id = os_w_id;
                END IF;
                SELECT o_id, o_carrier_id, o_entry_d
                INTO os_o_id, os_o_carrier_id, os_entdate
                FROM
                (SELECT o_id, o_carrier_id, o_entry_d
                FROM orders where o_d_id = os_d_id AND o_w_id = os_w_id and o_c_id=os_c_id
                ORDER BY o_id DESC) AS SUBQUERY
                LIMIT 1;
                FOR os_ol IN
                SELECT ol_i_id, ol_supply_w_id, ol_quantity, ol_amount, ol_delivery_d, out_os_c_id, out_os_c_last, os_c_first, os_c_middle, os_c_balance, os_o_id, os_entdate, os_o_carrier_id
                FROM order_line
                WHERE ol_o_id = os_o_id AND ol_d_id = os_d_id AND ol_w_id = os_w_id
                LOOP
                RETURN NEXT os_ol;
                END LOOP;
                EXCEPTION
                WHEN serialization_failure OR deadlock_detected OR no_data_found
                THEN ROLLBACK;
                END;
                $_$;


ALTER FUNCTION public.ostat(integer, integer, integer, integer, character varying) OWNER TO music;

--
-- Name: payment(integer, integer, integer, integer, integer, integer, numeric, character varying, character varying, numeric); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.payment(integer, integer, integer, integer, integer, integer, numeric, character varying, character varying, numeric) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
                DECLARE
                p_w_id			ALIAS FOR $1;
                p_d_id			ALIAS FOR $2;
                p_c_w_id		ALIAS FOR $3;
                p_c_d_id		ALIAS FOR $4;
                p_c_id_in		ALIAS FOR $5;
                byname			ALIAS FOR $6;
                p_h_amount		ALIAS FOR $7;
                p_c_last_in		ALIAS FOR $8;
                p_c_credit_in	ALIAS FOR $9;
                p_c_balance_in	ALIAS FOR $10;
                p_c_balance     NUMERIC(12, 2);
                p_c_credit      CHAR(2);
                p_c_last		VARCHAR(16);
                p_c_id			INTEGER;
                p_w_street_1            VARCHAR(20);
                p_w_street_2            VARCHAR(20);
                p_w_city                VARCHAR(20);
                p_w_state               CHAR(2);
                p_w_zip                 CHAR(9);
                p_d_street_1            VARCHAR(20);
                p_d_street_2            VARCHAR(20);
                p_d_city                VARCHAR(20);
                p_d_state               CHAR(2);
                p_d_zip                 CHAR(9);
                p_c_first               VARCHAR(16);
                p_c_middle              CHAR(2);
                p_c_street_1            VARCHAR(20);
                p_c_street_2            VARCHAR(20);
                p_c_city                VARCHAR(20);
                p_c_state               CHAR(2);
                p_c_zip                 CHAR(9);
                p_c_phone               CHAR(16);
                p_c_since				TIMESTAMP;
                p_c_credit_lim          NUMERIC(12, 2);
                p_c_discount            NUMERIC(4, 4);
                tstamp					TIMESTAMP;
                p_d_name				VARCHAR(11);
                p_w_name				VARCHAR(11);
                p_c_new_data			VARCHAR(500);

                name_count SMALLINT;

                c_byname CURSOR FOR
                SELECT c_first, c_middle, c_id,
                c_street_1, c_street_2, c_city, c_state, c_zip,
                c_phone, c_credit, c_credit_lim,
                c_discount, c_balance, c_since
                FROM customer
                WHERE c_w_id = p_c_w_id AND c_d_id = p_c_d_id AND c_last = p_c_last
                ORDER BY c_first;
                BEGIN
                tstamp := current_timestamp;
                p_c_id := p_c_id_in;
                p_c_balance := p_c_balance_in;
                p_c_last := p_c_last_in;
                p_c_credit := p_c_credit_in;

                UPDATE warehouse
                SET w_ytd = w_ytd + p_h_amount
                WHERE w_id = p_w_id
                RETURNING w_street_1, w_street_2, w_city, w_state, w_zip, w_name
                INTO p_w_street_1, p_w_street_2, p_w_city, p_w_state, p_w_zip, p_w_name;

                UPDATE district
                SET d_ytd = d_ytd + p_h_amount
                WHERE d_w_id = p_w_id AND d_id = p_d_id
                RETURNING d_street_1, d_street_2, d_city, d_state, d_zip, d_name
                INTO p_d_street_1, p_d_street_2, p_d_city, p_d_state, p_d_zip, p_d_name;

                IF ( byname = 1 )
                THEN
                SELECT count(c_last) INTO name_count
                FROM customer
                WHERE c_last = p_c_last AND c_d_id = p_c_d_id AND c_w_id = p_c_w_id;
                OPEN c_byname;
                FOR loop_counter IN 1 .. cast( name_count/2 AS INT)
                LOOP
                FETCH c_byname
                INTO p_c_first, p_c_middle, p_c_id, p_c_street_1, p_c_street_2, p_c_city, p_c_state, p_c_zip, p_c_phone, p_c_credit, p_c_credit_lim, p_c_discount, p_c_balance, p_c_since;
                END LOOP;
                CLOSE c_byname;
                ELSE
                SELECT c_first, c_middle, c_last,
                c_street_1, c_street_2, c_city, c_state, c_zip,
                c_phone, c_credit, c_credit_lim,
                c_discount, c_balance, c_since
                INTO p_c_first, p_c_middle, p_c_last,
                p_c_street_1, p_c_street_2, p_c_city, p_c_state, p_c_zip,
                p_c_phone, p_c_credit, p_c_credit_lim,
                p_c_discount, p_c_balance, p_c_since
                FROM customer
                WHERE c_w_id = p_c_w_id AND c_d_id = p_c_d_id AND c_id = p_c_id;
                END IF;

                IF p_c_credit = 'BC'
                THEN
                UPDATE customer
                SET c_balance = p_c_balance - p_h_amount,
                c_data = substr ((p_c_id || ' ' ||
                p_c_d_id || ' ' ||
                p_c_w_id || ' ' ||
                p_d_id || ' ' ||
                p_w_id || ' ' ||
                to_char (p_h_amount, '9999.99') || ' | ') || c_data, 1, 500)
                WHERE c_w_id = p_c_w_id AND c_d_id = p_c_d_id AND c_id = p_c_id
                RETURNING c_balance, c_data INTO p_c_balance, p_c_new_data;
                ELSE
                UPDATE customer
                SET c_balance = p_c_balance - p_h_amount
                WHERE c_w_id = p_c_w_id AND c_d_id = p_c_d_id AND c_id = p_c_id
                RETURNING c_balance, ' ' INTO p_c_balance, p_c_new_data;
                END IF;

                INSERT INTO history (h_c_d_id, h_c_w_id, h_c_id, h_d_id,h_w_id, h_date, h_amount, h_data)
                VALUES (p_c_d_id, p_c_w_id, p_c_id, p_d_id,	p_w_id, tstamp, p_h_amount, p_w_name || ' ' || p_d_name);

                RETURN p_c_id;

                EXCEPTION
                WHEN serialization_failure OR deadlock_detected OR no_data_found
                THEN ROLLBACK;
                END;
                $_$;


ALTER FUNCTION public.payment(integer, integer, integer, integer, integer, integer, numeric, character varying, character varying, numeric) OWNER TO music;

--
-- Name: slev(integer, integer, integer); Type: FUNCTION; Schema: public; Owner: music
--

CREATE FUNCTION public.slev(integer, integer, integer) RETURNS integer
    LANGUAGE plpgsql
    AS $_$
                DECLARE
                st_w_id			ALIAS FOR $1;
                st_d_id			ALIAS FOR $2;
                threshold		ALIAS FOR $3;
                stock_count		INTEGER;
                BEGIN
                SELECT COUNT(DISTINCT (s_i_id)) INTO stock_count
                FROM order_line, stock, district
                WHERE ol_w_id = st_w_id
                AND ol_d_id = st_d_id
                AND d_w_id=st_w_id
                AND d_id=st_d_id
                AND (ol_o_id < d_next_o_id)
                AND ol_o_id >= (d_next_o_id - 20)
                AND s_w_id = st_w_id
                AND s_i_id = ol_i_id
                AND s_quantity < threshold;

                RETURN stock_count;
                EXCEPTION
                WHEN serialization_failure OR deadlock_detected OR no_data_found
                THEN ROLLBACK;
                END;
                $_$;


ALTER FUNCTION public.slev(integer, integer, integer) OWNER TO music;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: customer; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.customer (
    c_since timestamp with time zone NOT NULL,
    c_id integer NOT NULL,
    c_w_id integer NOT NULL,
    c_d_id smallint NOT NULL,
    c_payment_cnt smallint NOT NULL,
    c_delivery_cnt smallint NOT NULL,
    c_first character varying(16) NOT NULL,
    c_middle character(2) NOT NULL,
    c_last character varying(16) NOT NULL,
    c_street_1 character varying(20) NOT NULL,
    c_street_2 character varying(20) NOT NULL,
    c_city character varying(20) NOT NULL,
    c_state character(2) NOT NULL,
    c_zip character(9) NOT NULL,
    c_phone character(16) NOT NULL,
    c_credit character(2) NOT NULL,
    c_credit_lim numeric(12,2) NOT NULL,
    c_discount numeric(4,4) NOT NULL,
    c_balance numeric(12,2) NOT NULL,
    c_ytd_payment numeric(12,2) NOT NULL,
    c_data character varying(500) NOT NULL
);


ALTER TABLE public.customer OWNER TO music;

--
-- Name: district; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.district (
    d_w_id integer NOT NULL,
    d_next_o_id integer NOT NULL,
    d_id smallint NOT NULL,
    d_ytd numeric(12,2) NOT NULL,
    d_tax numeric(4,4) NOT NULL,
    d_name character varying(10) NOT NULL,
    d_street_1 character varying(20) NOT NULL,
    d_street_2 character varying(20) NOT NULL,
    d_city character varying(20) NOT NULL,
    d_state character(2) NOT NULL,
    d_zip character(9) NOT NULL
);


ALTER TABLE public.district OWNER TO music;

--
-- Name: history; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.history (
    h_date timestamp with time zone NOT NULL,
    h_c_id integer,
    h_c_w_id integer NOT NULL,
    h_w_id integer NOT NULL,
    h_c_d_id smallint NOT NULL,
    h_d_id smallint NOT NULL,
    h_amount numeric(6,2) NOT NULL,
    h_data character varying(24) NOT NULL
);


ALTER TABLE public.history OWNER TO music;

--
-- Name: item; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.item (
    i_id integer NOT NULL,
    i_im_id integer NOT NULL,
    i_name character varying(24) NOT NULL,
    i_price numeric(5,2) NOT NULL,
    i_data character varying(50) NOT NULL
);


ALTER TABLE public.item OWNER TO music;

--
-- Name: new_order; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.new_order (
    no_w_id integer NOT NULL,
    no_o_id integer NOT NULL,
    no_d_id smallint NOT NULL
);


ALTER TABLE public.new_order OWNER TO music;

--
-- Name: order_line; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.order_line (
    ol_delivery_d timestamp with time zone,
    ol_o_id integer NOT NULL,
    ol_w_id integer NOT NULL,
    ol_i_id integer NOT NULL,
    ol_supply_w_id integer NOT NULL,
    ol_d_id smallint NOT NULL,
    ol_number smallint NOT NULL,
    ol_quantity smallint NOT NULL,
    ol_amount numeric(6,2),
    ol_dist_info character(24)
);


ALTER TABLE public.order_line OWNER TO music;

--
-- Name: orders; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.orders (
    o_entry_d timestamp with time zone NOT NULL,
    o_id integer NOT NULL,
    o_w_id integer NOT NULL,
    o_c_id integer NOT NULL,
    o_d_id smallint NOT NULL,
    o_carrier_id smallint,
    o_ol_cnt smallint NOT NULL,
    o_all_local smallint NOT NULL
);


ALTER TABLE public.orders OWNER TO music;

--
-- Name: stock; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.stock (
    s_i_id integer NOT NULL,
    s_w_id integer NOT NULL,
    s_ytd integer NOT NULL,
    s_quantity smallint NOT NULL,
    s_order_cnt smallint NOT NULL,
    s_remote_cnt smallint NOT NULL,
    s_dist_01 character(24) NOT NULL,
    s_dist_02 character(24) NOT NULL,
    s_dist_03 character(24) NOT NULL,
    s_dist_04 character(24) NOT NULL,
    s_dist_05 character(24) NOT NULL,
    s_dist_06 character(24) NOT NULL,
    s_dist_07 character(24) NOT NULL,
    s_dist_08 character(24) NOT NULL,
    s_dist_09 character(24) NOT NULL,
    s_dist_10 character(24) NOT NULL,
    s_data character varying(50) NOT NULL
);


ALTER TABLE public.stock OWNER TO music;

--
-- Name: warehouse; Type: TABLE; Schema: public; Owner: music
--

CREATE TABLE public.warehouse (
    w_id integer NOT NULL,
    w_name character varying(10) NOT NULL,
    w_street_1 character varying(20) NOT NULL,
    w_street_2 character varying(20) NOT NULL,
    w_city character varying(20) NOT NULL,
    w_state character(2) NOT NULL,
    w_zip character(9) NOT NULL,
    w_tax numeric(4,4) NOT NULL,
    w_ytd numeric(16,2) NOT NULL
);


ALTER TABLE public.warehouse OWNER TO music;

--
-- Name: customer customer_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_i1 PRIMARY KEY (c_w_id, c_d_id, c_id);


--
-- Name: district district_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.district
    ADD CONSTRAINT district_i1 PRIMARY KEY (d_w_id, d_id);


--
-- Name: item item_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_i1 PRIMARY KEY (i_id);


--
-- Name: new_order new_order_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.new_order
    ADD CONSTRAINT new_order_i1 PRIMARY KEY (no_w_id, no_d_id, no_o_id);


--
-- Name: order_line order_line_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.order_line
    ADD CONSTRAINT order_line_i1 PRIMARY KEY (ol_w_id, ol_d_id, ol_o_id, ol_number);


--
-- Name: orders orders_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_i1 PRIMARY KEY (o_w_id, o_d_id, o_id);


--
-- Name: stock stock_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.stock
    ADD CONSTRAINT stock_i1 PRIMARY KEY (s_i_id, s_w_id);


--
-- Name: warehouse warehouse_i1; Type: CONSTRAINT; Schema: public; Owner: music
--

ALTER TABLE ONLY public.warehouse
    ADD CONSTRAINT warehouse_i1 PRIMARY KEY (w_id);


--
-- Name: customer_i2; Type: INDEX; Schema: public; Owner: music
--

CREATE UNIQUE INDEX customer_i2 ON public.customer USING btree (c_w_id, c_d_id, c_last, c_first, c_id);


--
-- Name: orders_i2; Type: INDEX; Schema: public; Owner: music
--

CREATE UNIQUE INDEX orders_i2 ON public.orders USING btree (o_w_id, o_d_id, o_c_id, o_id);


--
-- PostgreSQL database dump complete
--

\unrestrict WIn7E7EIqkJ3Kp1iPQwmVEpCZjNwmyKOO1FQKHXYszbS2JeMBdUmMnHZDIvie8m

