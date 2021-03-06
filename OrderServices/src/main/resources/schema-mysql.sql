CREATE TABLE IF NOT EXISTS orders (
                                        order_id INT NOT NULL AUTO_INCREMENT,
                                        primary key (order_id),
                                        shopper_rut VARCHAR(100) NOT NULL,
                                        shopper_email VARCHAR(100) NOT NULL,
                                        order_total FLOAT NOT NULL,
                                        creation_date TIMESTAMP NOT NULL,
                                        order_status VARCHAR(15) NOT NULL DEFAULT 'CREATED'
) ENGINE=INNODB;
CREATE TABLE IF NOT EXISTS products (
                                        product_id INT NOT NULL AUTO_INCREMENT,
                                      primary key (product_id),
                                      name VARCHAR(100) NOT NULL,
                                      price FLOAT NOT NULL
) ENGINE=INNODB;
CREATE TABLE IF NOT EXISTS order_items (
                                        order_item_id INT NOT NULL  AUTO_INCREMENT,
                                        primary key (order_item_id),
                                        order_id INT NOT NULL ,
                                        product_id INT NOT NULL,
                                        quantity FLOAT NOT NULL,
                                        FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE,
                                        FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
) ENGINE=INNODB;
