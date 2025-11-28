CREATE TABLE filmplus.grade (
                                  id SERIAL PRIMARY KEY ,
                                  film_id INTEGER NOT NULL ,
                                  user_id  INTEGER NOT NULL,
                                  grade INT CHECK (grade>=1 AND grade <= 10)
);
