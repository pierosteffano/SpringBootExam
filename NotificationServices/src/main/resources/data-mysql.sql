CREATE TABLE IF NOT EXISTS Notifications (
                                        notification_id LONG NOT NULL AUTO_INCREMENT,
                                        primary key (notification_id),
                                        order_id VARCHAR(100) NOT NULL,
                                        notification_email VARCHAR(100) NOT NULL,
                                        notification_status VARCHAR(15) NOT NULL
) ENGINE=INNODB;
