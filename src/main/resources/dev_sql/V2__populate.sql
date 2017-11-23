INSERT INTO video_types (type_value, price, currency_code) VALUES ('New release', 40, 'SEK');
INSERT INTO video_types (type_value, price, currency_code) VALUES ('Regular film', 30, 'SEK');
INSERT INTO video_types (type_value, price, currency_code) VALUES ('Old film', 30, 'SEK');

INSERT INTO customers (bonus_points, total_orders) VALUES (0, 0);
INSERT INTO customers (bonus_points, total_orders) VALUES (0, 0);

INSERT INTO VIDEOS (title, video_type_value) VALUES ('Matrix 11', 'New release');
INSERT INTO VIDEOS (title, video_type_value) VALUES ('Spider Man', 'Regular film');
INSERT INTO VIDEOS (title, video_type_value) VALUES ('Spider Man 2', 'Regular film');
INSERT INTO VIDEOS (title, video_type_value) VALUES ('Out of Africa', 'Old film');
