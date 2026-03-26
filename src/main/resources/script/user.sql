-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
                              id bigserial NOT NULL,
                              username varchar(50) NOT NULL,
                              email varchar(100) NOT NULL,
                              age int4 NULL,
                              create_time timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                              update_time timestamp DEFAULT CURRENT_TIMESTAMP NULL,
                              "content" jsonb NULL,
                              CONSTRAINT users_pkey PRIMARY KEY (id),
                              CONSTRAINT users_username_key UNIQUE (username)
);
INSERT INTO public.users
(id, username, email, age, create_time, update_time, "content")
VALUES(1, '张三', 'zhangsan@example.com', 25, '2026-03-26 23:52:40.704', '2026-03-26 23:52:40.704', '{"code": 1, "name": "test"}'::jsonb);
INSERT INTO public.users
(id, username, email, age, create_time, update_time, "content")
VALUES(2, '李四', 'lisi@example.com', 28, '2026-03-26 23:52:40.704', '2026-03-26 23:52:40.704', NULL);
INSERT INTO public.users
(id, username, email, age, create_time, update_time, "content")
VALUES(3, '王五', 'wangwu@example.com', 30, '2026-03-26 23:52:40.704', '2026-03-26 23:52:40.704', NULL);