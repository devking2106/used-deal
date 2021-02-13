DROP TABLE IF EXISTS BOARD;
DROP TABLE IF EXISTS LOCATION;
DROP TABLE IF EXISTS USER;
CREATE TABLE BOARD
(
    ID            LONG PRIMARY KEY AUTO_INCREMENT,
    USER_ID       LONG          NOT NULL,
    LOCATION_ID   LONG          NOT NULL,
    LOCATION_NAME VARCHAR(30)   NOT NULL,
    TITLE         VARCHAR(255)  NOT NULL,
    CONTENT       VARCHAR(1000) NOT NULL,
    PRICE         INTEGER       NOT NULL,
    CATEGORY_ID   INTEGER       NOT NULL,
    STATUS        VARCHAR(20)   NOT NULL,
    REG_DATE      DATETIME      NOT NULL,
    MOD_DATE      DATETIME      NOT NULL,
    BOARD_DATE    DATETIME      NOT NULL,
    IS_PULL       TINYINT(1) NOT NULL,
    LATITUDE      DOUBLE        NOT NULL,
    LONGITUDE     DOUBLE        NOT NULL
) ENGINE=InnoDB;

CREATE TABLE LOCATION
(
    ID               LONG PRIMARY KEY,
    LOCATION_NAME    VARCHAR(30) NOT NULL,
    PROVINCE         VARCHAR(10) NOT NULL,
    CITY             VARCHAR(10) NOT NULL,
    TOWN             VARCHAR(10),
    LATITUDE         DOUBLE      NOT NULL,
    LONGITUDE        DOUBLE      NOT NULL,
    RELATED_LOCATION VARCHAR(200)
) ENGINE=InnoDB;

CREATE TABLE USER
(
    ID            LONG PRIMARY KEY AUTO_INCREMENT,
    USER_ID       VARCHAR(20)  NOT NULL UNIQUE,
    LOCATION_NAME VARCHAR(20),
    LOCATION_ID   LONG,
    PASSWORD      VARCHAR(50)  NOT NULL,
    NICKNAME      VARCHAR(10)  NOT NULL,
    PHONE         VARCHAR(20)  NOT NULL,
    EMAIL         VARCHAR(30)  NOT NULL,
    REG_DATE      DATETIME     NOT NULL,
    MOD_DATE      DATETIME     NOT NULL,
    LOGIN_DATE    DATETIME     NOT NULL,
    ROLE          VARCHAR(10)  NOT NULL,
    STATUS        VARCHAR(10)  NOT NULL,
    SALE_COUNT    LONG         NOT NULL,
    IMAGE_PATH    VARCHAR(255) NOT NULL,
    LATITUDE      DOUBLE,
    LONGITUDE     DOUBLE,
    AUTH_COUNT    LONG
) ENGINE=InnoDB;
