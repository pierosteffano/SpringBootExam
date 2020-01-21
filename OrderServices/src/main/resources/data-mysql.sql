CREATE TABLE IF NOT EXISTS orders (
                                        order_id LONG NOT NULL AUTO_INCREMENT,
                                        primary key (order_id),
                                        shopper_rut VARCHAR(100) NOT NULL,
                                        order_total FLOAT NOT NULL,
                                        creation_date TIMESTAMP NOT NULL,
                                        order_status VARCHAR(15) NOT NULL DEFAULT 'CREATED'
) ENGINE=INNODB;
CREATE TABLE IF NOT EXISTS products (
                                        product_id LONG NOT NULL AUTO_INCREMENT,
                                      primary key (product_id),
                                      name VARCHAR(100) NOT NULL,
                                      price FLOAT NOT NULL
) ENGINE=INNODB;
CREATE TABLE IF NOT EXISTS order_items (
                                        order_item_id LONG NOT NULL  AUTO_INCREMENT,
                                        primary key (order_item_id),
                                        orderId LONG NOT NULL ,
                                        productId VARCHAR(100) NOT NULL,
                                        quantity FLOAT NOT NULL,
                                        FOREIGN KEY (orderId) REFERENCES orders(order_id) ON DELETE CASCADE,
                                        FOREIGN KEY (productId) REFERENCES products(product_id) ON DELETE CASCADE
) ENGINE=INNODB;


