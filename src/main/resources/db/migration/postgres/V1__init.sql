CREATE TABLE customers ( 
	id                   serial  NOT NULL,
	bonus_points         smallint,
	total_orders         smallint,
	CONSTRAINT unq_customers_id UNIQUE (id) ,
	CONSTRAINT pk_customers_id PRIMARY KEY (id)
);

CREATE TABLE video_types ( 
	type_value           varchar(20)  NOT NULL,
	price                DECIMAL(20, 4),
	currency_code        varchar(3),
	CONSTRAINT pk_type_value PRIMARY KEY (type_value),
	CONSTRAINT uq_type_value_currency_code UNIQUE(type_value, currency_code)
);

CREATE TABLE videos ( 
	id                   serial  NOT NULL,
	title                varchar(255),
	video_type_value      varchar(20),
	CONSTRAINT unq_videos_id UNIQUE (id),
	CONSTRAINT pk_videos_id PRIMARY KEY (id),
	CONSTRAINT unq_videos_title UNIQUE (title),
	CONSTRAINT fk_video_types FOREIGN KEY (video_type_value) REFERENCES video_types(type_value)  
);

CREATE TABLE orders ( 
	id                   serial  NOT NULL,
	price                DECIMAL(20, 4),
	surcharges           DECIMAL(20, 4),
	customer_id          integer,
	currency_code        varchar(3),
	is_order_returned    boolean default false,
	CONSTRAINT unq_orders_id UNIQUE (id) ,
	CONSTRAINT pk_orders_id PRIMARY KEY (id),
	CONSTRAINT fk_orders_customers FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE 
);

CREATE INDEX idx_orders_customer_id ON orders (customer_id);

CREATE TABLE videos_orders ( 
	order_id             integer,
	video_id             integer,
	days                 smallint,
	CONSTRAINT fk_videos_orders_videos FOREIGN KEY (video_id) REFERENCES videos(id) ON DELETE SET NULL,
	CONSTRAINT fk_videos_orders_orders FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);


CREATE INDEX idx_videos_orders_video_id ON videos_orders (video_id);

CREATE INDEX idx_videos_orders_order_id ON videos_orders (order_id);
