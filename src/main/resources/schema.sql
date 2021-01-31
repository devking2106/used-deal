DROP TABLE IF EXISTS BOARD;
CREATE TABLE BOARD
(
    ID LONG PRIMARY KEY AUTO_INCREMENT,
    USER_ID  LONG       NOT NULL ,
    LOCATION_ID LONG       NOT NULL,
    LOCATION_NAME VARCHAR(30) NOT NULL,
    TITLE    VARCHAR(255)  NOT NULL,
    CONTENT  VARCHAR(1000) NOT NULL,
    PRICE    INTEGER       NOT NULL,
    CATEGORY_ID  INTEGER   NOT NULL,
    STATUS  VARCHAR(20) NOT NULL,
    REG_DATE  DATETIME  NOT NULL,
    MOD_DATE  DATETIME  NOT NULL,
    BOARD_DATE  DATETIME  NOT NULL,
    IS_PRICE_SUGGEST TINYINT(1) NOT NULL,
    IS_PULL  TINYINT(1)    NOT NULL,
    LATITUDE DOUBLE        NOT NULL,
    LONGITUDE DOUBLE       NOT NULL
);
