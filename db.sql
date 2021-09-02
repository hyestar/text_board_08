DROP DATABASE IF EXISTS text_board;
CREATE DATABASE text_board;
USE text_board;

# 게시물 테이블 생성
CREATE TABLE article(
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(100) NOT NULL,
    `body` TEXT NOT NULL
);

SELECT * FROM article;

-- INSERT INTO article 
-- SET regDate = NOW(),
-- updateDate = NOW(), 
-- title = CONCAT('제목', ROUND(RAND()*10)), 
-- `body` = CONCAT('내용', ROUND(RAND()*10));

# 회원 테이블 생성
CREATE TABLE `member` (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    loginId CHAR(20) NOT NULL,
    loginPw CHAR(100) NOT NULL,
    `name` CHAR(200) NOT NULL
);

SELECT * FROM `member`;