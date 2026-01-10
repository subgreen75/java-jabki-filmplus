CREATE TABLE filmplus.friends (
                                  id SERIAL PRIMARY KEY ,
                                  user_id INTEGER NOT NULL ,
                                  friend_user_id  INTEGER NOT NULL
);