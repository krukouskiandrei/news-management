INSERT INTO customer(user_id, user_name, login, password)
SELECT customer_sequence.nextval, dbms_random.string('A', 10), dbms_random.string('A', 10), dbms_random.string('A', 10)
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO roles(user_id, role_name)
SELECT LEVEL, dbms_random.string('A', 5)
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO news(news_id, title, short_text, full_text, creation_date, modification_date)
SELECT news_sequence.nextval, dbms_random.string('A', 20), dbms_random.string('A', 75), dbms_random.string('A', 500), to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)), to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000))
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO author(author_id, author_name, expired)
SELECT author_sequence.nextval, dbms_random.string('A', 10), to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100))
CONNECT BY LEVEL <= 30;
INSERT INTO news_author(news_id, author_id)
SELECT LEVEL, LEVEL
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO comments(comment_id, news_id, comment_text, creation_date)
SELECT comment_sequence.nextval, TRUNC(DBMS_RANDOM.VALUE (1, 30)), dbms_random.string('A', 50), to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100))
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO tag(tag_id, tag_name)
SELECT tag_sequence.nextval, dbms_random.string('A', 15)
FROM dual
CONNECT BY LEVEL <= 30;
INSERT INTO news_tag(news_id, tag_id)
SELECT LEVEL, TRUNC(DBMS_RANDOM.VALUE (1, 30))
FROM dual
CONNECT BY LEVEL <= 30;



INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Круковский', 'krukouski', 'krukouski');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Молотов', 'molotok', 'molot12213');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Берканян', 'berckore', 'berck3342');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Файзулин', 'faizolol', 'faizoleo34');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Хомяков', 'homcka43', 'homcka9348');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Даронин', 'dorastar', 'dobest8373');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Бурков', 'burack45', 'bura8302f');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Гуронов', 'gura39kse', 'guks83kssf');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Каренин', 'annakar', 'anna934d');
INSERT INTO CUSTOMER (USER_ID, USER_NAME, LOGIN, PASSWORD)
VALUES(CUSTOMER_SEQUENCE.NEXTVAL, 'Байко', 'boicko34', 'boicko392f');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(1,'ADMIN');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(2,'GUEST');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(3,'AUTHOR');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(4,'USER');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(5,'MODER');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(6,'GUEST');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(7,'AUTHOR');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(8,'USER');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(9,'USER');
INSERT INTO ROLES (USER_ID, ROLE_NAME) VALUES(10,'GUEST');


INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'The ExoMars spacecraft', 'is on its way to the
red planet right now,',
'a gas that’s linked to life, but could be coming from other sources too.
Nicolas Thomas, CaSSIS instrument project scientist, from University of
Bern in Switzerland explains how his instrument on ExoMars could
help solve the methane mystery.
ExoMars is a joint ESA-Roscosmos project,
and includes the 2016 Trace Gas Orbiter and a small lander,
and in 2020 the ExoMars rover, which will drill
below the Martian surface to search for signs of life now or in the past.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'CaSSIS is designed', '“CaSSIS is designed to look at dynamic',
'processes on the surface of Mars, and so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'CaSSIS is', 'CaSSIS CaSSIS is designedCaSSIS is designed look at dynamic',
' processes on the surface of Mars, and so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'methane on Marsis', 'CaSSIS CaSSIS is be producing methane on
Mars designedCaSSIS',
' processes on the be producing methane on Mars be producing methane on Mars
surface of Mars, and be producing methane on Mars so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'apart from life', 'CaSSIS apart from life methane on
Mars designedCaSSIS',
' processes on the be producing methane on Mars be producing methane on Mars
surface of Mars, apart from life apart from life apart from lifeso we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'surface of Mars', 'CaSSIS surface of Mars on
Mars designedCaSSIS',
' processes surface of Mars on Mars be producing methane on Mars
surface of Mars, apart from life apart surface of Mars lifeso we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'So, looking for', 'So, looking for Mars designedCaSSIS',
' So, looking for surface of Mars, apart from life apart
surface of Mars lifeso we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'There might trapped there,', 'There might be gas trapped there,SIS',
' So, looking for surface of Mars, apart from life apart
surface of Mars lifeThere might be gas trapped there, There might be gas trapped there,
so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'Production of methane', 'There mProduction of methane trapped there,SIS',
' Production of methane So, looking for surface of Mars, apart from life apart
surface Production of methaneof Mars lifeThere might be gas trapped there, There might be gas trapped there,
so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'very difficult', 'There very difficult trapped there,SIS',
' Production of methane So, very difficult very difficult apart
surface Production of methaneof Mars lifeThere might be gas trapped there, There might be gas trapped there,
so we’ll be looking at things that are happening,
changes that are occurring on the surface, Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));
INSERT INTO NEWS(NEWS_ID, TITLE, SHORT_TEXT, FULL_TEXT, CREATION_DATE, MODIFICATION_DATE)
VALUES(NEWS_SEQUENCE.NEXTVAL, 'There might trapped there,', 'There might be gas trapped there,SIS',
' So, looking for surface of Mars, apart from life apart
surface of Mars lifeThere might be gas trapped there, There might be gas trapped there,
so we’ll be looking at things that are happening,
changes that are occurring on the surface,” Thomas says.',
to_date('2013-11-13', 'yyyy-mm-dd')+TRUNC(DBMS_RANDOM.VALUE (0, 100)),
to_date('2013-11-13', 'yyyy-mm-dd')+trunc(dbms_random.value(1,1000)));




INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Достоевкий', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Толстой', '15-DEC-17 12.00.00.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Пушкин', '15-DEC-19 11.20.20.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Ломоносов', '05-DEC-18 09.02.10.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Гоголь', '12-DEC-17 11.12.12.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Тургенев', '13-DEC-17 11.14.10.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Твордовский', '16-DEC-19 12.50.00.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Гумелев', '17-DEC-20 12.23.30.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Есенин', '18-DEC-18 12.07.09.000000000 AM');
INSERT INTO AUTHOR(AUTHOR_ID, AUTHOR_NAME, EXPIRED)
VALUES(AUTHOR_SEQUENCE.NEXTVAL, 'Ахматова', '19-DEC-19 11.19.19.000000000 AM');


INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 2, 'This exposes fresh material', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 3, 'There migh', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 4, 'be gas trapped there', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 5, 'Production of methane', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 6, 'material from under', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 7, 'surface of Mars', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 8, 'Mars, apart from life', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 10, 'So what could be producing', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 11, 'We also have ', '25-DEC-17 12.00.00.000000000 AM');
INSERT INTO COMMENTS(COMMENT_ID, NEWS_ID, COMMENT_TEXT, CREATION_DATE)
VALUES(COMMENT_SEQUENCE.NEXTVAL, 12, 'CaSSIS is designed ', '25-DEC-17 12.00.00.000000000 AM');


INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'html');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'nav');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'div');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'meta');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'body');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'nav');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'script');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'link');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'head');
INSERT INTO TAG(TAG_ID, TAG_NAME) VALUES(TAG_SEQUENCE.NEXTVAL, 'footer');

INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(2, 1);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(3, 2);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(4, 3);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(5, 7);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(6, 8);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(7, 9);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(8, 11);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(10, 12);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(11, 13);
INSERT INTO NEWS_AUTHOR(NEWS_ID, AUTHOR_ID) VALUES(12, 14);

INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(2, 10);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(3, 9);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(4, 8);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(5, 7);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(6, 6);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(7, 5);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(8, 4);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(10, 3);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(11, 2);
INSERT INTO NEWS_TAG(NEWS_ID, TAG_ID) VALUES(12, 1);