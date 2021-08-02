CREATE DATABASE sns DEFAULT CHARACTER SET utf8;

CREATE TABLE `user`
(
    id         bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    principal  varchar(30),
    email      varchar(50),
    name       varchar(30),
    profile    varchar(100),
    created_at datetime,
    updated_at datetime
);

CREATE TABLE follow
(
    follower_id  bigint NOT NULL,
    following_id bigint NOT NULL,
    created_at   datetime,
    updated_at   datetime,
    CONSTRAINT pk_follow PRIMARY KEY (follower_id, following_id),
    CONSTRAINT fk_follower FOREIGN KEY (follower_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_following FOREIGN KEY (following_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_follower_user ON follow (follower_id);
CREATE INDEX fk_following_user ON follow (following_id);

CREATE TABLE post
(
    id         bigint NOT NULL PRIMARY KEY,
    user_id    bigint NOT NULL,
    content    text,
    created_at datetime,
    updated_at datetime,
    CONSTRAINT unq_post_user_id UNIQUE (user_id),
    CONSTRAINT fk_post_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE comment
(
    id         bigint NOT NULL PRIMARY KEY,
    content    text   NOT NULL,
    user_id    bigint NOT NULL,
    post_id    bigint NOT NULL,
    parent_id  bigint NOT NULL,
    created_at datetime,
    updated_at datetime,
    CONSTRAINT fk_comment_parent_0 FOREIGN KEY (parent_id) REFERENCES comment (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_comment_post_0 FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_comment_user_0 FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX fk_comment_post_parent ON comment (post_id, parent_id);
CREATE INDEX fk_comment_post ON comment (post_id);
CREATE INDEX fk_comment_user ON comment (user_id);

CREATE TABLE image
(
    id         bigint       NOT NULL PRIMARY KEY,
    post_id    bigint       NOT NULL,
    path       varchar(100) NOT NULL,
    created_at datetime,
    updated_at datetime,
    CONSTRAINT fk_image_post_0 FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE INDEX fk_image_post ON image (post_id);

CREATE TABLE `like`
(
    user_id    bigint NOT NULL,
    post_id    bigint NOT NULL,
    created_at datetime,
    updated_at datetime,
    CONSTRAINT pk_like PRIMARY KEY (user_id, post_id),
    CONSTRAINT fk_like_user FOREIGN KEY (user_id) REFERENCES `user` (id) ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_like_post FOREIGN KEY (post_id) REFERENCES post (id) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE INDEX fk_like_post ON `like` (post_id);
