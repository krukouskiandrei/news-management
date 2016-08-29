CREATE TABLE customer(
	user_id number(20) NOT NULL,
	user_name nvarchar2(50) NOT NULL,
	login varchar2(30) NOT NULL,
	password varchar2(30) NOT NULL,
	CONSTRAINT user_pk PRIMARY KEY (user_id)
);
CREATE TABLE roles(
	user_id number(20) NOT NULL,
	role_name varchar2(50) NOT NULL,
	CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES customer(user_id)
	ON DELETE CASCADE
);
CREATE TABLE news(
	news_id number(20) NOT NULL,
	title nvarchar2(30) NOT NULL,
	short_text nvarchar2(100) NOT NULL,
	full_text nvarchar2(2000) NOT NULL,
	creation_date timestamp NOT NULL,
	modification_date date NOT NULL,
	CONSTRAINT news_pk PRIMARY KEY (news_id)
);
CREATE TABLE author(
	author_id number(20) NOT NULL,
	author_name nvarchar2(30) NOT NULL,
	expired timestamp,
	CONSTRAINT author_pk PRIMARY KEY (author_id)
);
CREATE TABLE news_author(
	news_id number(20) NOT NULL,
	author_id number(20) NOT NULL,
	CONSTRAINT fk_news FOREIGN KEY (news_id) REFERENCES news(news_id)
	ON DELETE CASCADE,
	CONSTRAINT fk_author FOREIGN KEY (author_id) REFERENCES author(author_id)
	ON DELETE CASCADE,
	CONSTRAINT news_author_pk PRIMARY KEY (news_id, author_id)

);
CREATE TABLE comments(
	comment_id number(20) NOT NULL,
	news_id number(20) NOT NULL,
	comment_text nvarchar2(100) NOT NULL,
	creation_date timestamp NOT NULL,
	CONSTRAINT comments_pk PRIMARY KEY (comment_id),
	CONSTRAINT fk_news_comments FOREIGN KEY (news_id) REFERENCES news(news_id)
	ON DELETE CASCADE
);
CREATE TABLE tag(
	tag_id number(20) NOT NULL,
	tag_name nvarchar2(30) NOT NULL,
	CONSTRAINT tag_pk PRIMARY KEY (tag_id)
);
CREATE TABLE news_tag(
	news_id number(20) NOT NULL,
	tag_id number(20) NOT NULL,
	CONSTRAINT fk_news_tag FOREIGN KEY (news_id) REFERENCES news(news_id)
	ON DELETE CASCADE,
	CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
	ON DELETE CASCADE,
	CONSTRAINT news_tag_pk PRIMARY KEY (news_id, tag_id)
);
CREATE SEQUENCE customer_sequence
START WITH 1
INCREMENT BY 1
MINVALUE 1;
CREATE SEQUENCE news_sequence
START WITH 1
INCREMENT BY 1
MINVALUE 1;
CREATE SEQUENCE tag_sequence
START WITH 1
INCREMENT BY 1
MINVALUE 1;
CREATE SEQUENCE author_sequence
START WITH 1
INCREMENT BY 1;
CREATE SEQUENCE comment_sequence
START WITH 1
INCREMENT BY 1;
