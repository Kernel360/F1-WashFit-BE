CREATE TABLE if not exists ConcernedProduct
(
    prdt_no                    VARCHAR(255) PRIMARY KEY,
    prdt_name                  VARCHAR(255) NOT NULL,
    slfsfcfst_no               VARCHAR(255) NOT NULL,
    item                       VARCHAR(255) NOT NULL,
    comp_nm                    VARCHAR(255) NOT NULL,
    inspected_organization     VARCHAR(255),
    issued_date                DATE,
    upper_item                 VARCHAR(255),
    product_type               VARCHAR(255),
    renewed_type               VARCHAR(255),
    safety_inspection_standard TEXT,
    kid_protect_package        VARCHAR(255),
    manufacture_nation         VARCHAR(255),
    product_definition         VARCHAR(255),
    manufacture                TEXT,
    created_at                 DATE         NOT NULL DEFAULT CURRENT_DATE,
    created_by                 VARCHAR(255) NOT NULL DEFAULT 'admin',
    modified_at                DATE,
    modified_by                VARCHAR(255)
);