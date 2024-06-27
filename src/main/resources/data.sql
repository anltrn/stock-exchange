INSERT INTO STOCK_EXCHANGE (name, description, live_in_market)
VALUES ('NYSE', 'New York Stock Exchange', false);
INSERT INTO STOCK_EXCHANGE ( name, description, live_in_market)
VALUES ('BIST', 'Borsa istanbul', false);
INSERT INTO STOCK_EXCHANGE ( name, description, live_in_market)
VALUES ('NASDAQ', 'Nasdaq', false);
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('AAPL', 'Apple Inc.', 10.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('TSLA', 'TSLA Inc.', 20.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('BOING', 'BOING', 30.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('MCRSFT', 'MCRSFT Inc.', 40.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('TAB', 'TAB', 50.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('TCELL', 'TCELL Inc.', 60.0, '2023-06-01 10:00:00');
INSERT INTO STOCK ( name, description, current_price, last_update)
VALUES ('TPRS', 'TPRS Inc.', 70.0, '2023-06-01 10:00:00');
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (1, 1);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (1, 2);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (2, 1);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (2, 2);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (3, 1);
INSERT INTO STOCK_EXCHANGE_STOCKS (stock_exchange_id, stock_id)
VALUES (3, 2);