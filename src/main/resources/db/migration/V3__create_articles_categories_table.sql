CREATE TABLE articles_categories (
  id SERIAL PRIMARY KEY,
  category_id INTEGER,
  article_id INTEGER
);

ALTER TABLE articles_categories ADD FOREIGN KEY (category_id) REFERENCES categories (id);
ALTER TABLE articles_categories ADD FOREIGN KEY (article_id) REFERENCES articles (id);
