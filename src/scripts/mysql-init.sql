CREATE TABLE IF NOT EXISTS beer (
                                    id VARCHAR(255) NOT NULL,
                                    beer_name VARCHAR(255) NOT NULL,
                                    beer_style TINYINT CHECK (beer_style BETWEEN 0 AND 3),
                                    created_date DATETIME(6),
                                    price DECIMAL(38,2) NOT NULL,
                                    quantity_on_hand INTEGER,
                                    upc VARCHAR(255) NOT NULL,
                                    update_date DATETIME(6),
                                    version BIGINT,
                                    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS customer (
                                        id VARCHAR(255) NOT NULL,
                                        created_date DATETIME(6),
                                        customer_name VARCHAR(255),
                                        last_modified_date DATETIME(6),
                                        version BIGINT,
                                        PRIMARY KEY (id)
) ENGINE=InnoDB;