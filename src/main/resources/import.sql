insert into USER(id, user_id, password, name, email) values(1, 'prettykara', '1111', '남상범', 'cuteprettykara@gmail.com');
insert into USER(id, user_id, password, name, email) values(2, 'prettykara2', '2222', '남상범2', 'cuteprettykara2@gmail.com');

insert into QUESTION(id, writer_id, title, contents, create_date, count_of_answer) values(1, 1, '테스트 타이틀1', '테스트 내용1', CURRENT_TIMESTAMP(), 0);
insert into QUESTION(id, writer_id, title, contents, create_date, count_of_answer) values(2, 2, '테스트 타이틀2', '테스트 내용2', CURRENT_TIMESTAMP(), 0);