CREATE TABLE filmplus.comment (
                               id SERIAL PRIMARY KEY ,
                               film_id INTEGER NOT NULL ,
                               user_id  INTEGER NOT NULL
);
