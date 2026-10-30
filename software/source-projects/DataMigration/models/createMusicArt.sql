
CREATE TABLE AppUser (

   id int NOT NULL ,
   card_number varchar(5) NOT NULL ,
   name varchar(20) NOT NULL ,
   email varchar(25) NOT NULL ,
   password varchar(10) NOT NULL ,
   address varchar(25) NOT NULL ,
   country varchar(15) NOT NULL ,
   registrationDate date NOT NULL ,
   recommendedBy int NOT NULL ,
   storeValuation int NOT NULL ,
   premium int NOT NULL ,
   date_premium date NOT NULL ,
   premium_fee int NOT NULL 
);

CREATE TABLE PaymentMethod (

   user_id int NOT NULL ,
   id int NOT NULL ,
   methodName varchar(15) NOT NULL ,
   methodInfo varchar(30) NOT NULL 
);

CREATE TABLE Product (

   id int NOT NULL ,
   name varchar(25) NOT NULL ,
   price int NOT NULL ,
   description varchar(40) NOT NULL ,
   imageURL varchar(30) NOT NULL ,
   manufacturer varchar(20) NOT NULL ,
   stock int NOT NULL ,
   category int NOT NULL 
);

CREATE TABLE Category (

   id int NOT NULL ,
   description varchar(25) NOT NULL 
);

CREATE TABLE RelatedProducts (

   product_id int NOT NULL ,
   product_related_id int NOT NULL ,
   affinity int NOT NULL 
);

CREATE TABLE UserOrder (

   user_id int NOT NULL ,
   id int NOT NULL ,
   orderDate date NOT NULL ,
   totalPrice int NOT NULL 
);

CREATE TABLE Item (

   user_id int NOT NULL ,
   order_id int NOT NULL ,
   id int NOT NULL ,
   product_id int NOT NULL ,
   amount int NOT NULL ,
   subtotal int NOT NULL 
);

CREATE TABLE Discount (

   id int NOT NULL ,
   user_id int NOT NULL ,
   order_id int NOT NULL ,
   description varchar(25) NOT NULL ,
   discountRatio int NOT NULL 
);

ALTER TABLE AppUser
ADD CONSTRAINT user_key PRIMARY KEY (id);
ALTER TABLE PaymentMethod
ADD CONSTRAINT paymentMethod_key PRIMARY KEY (user_id, id);
ALTER TABLE Product
ADD CONSTRAINT product_key PRIMARY KEY (id);
ALTER TABLE Category
ADD CONSTRAINT category_key PRIMARY KEY (id);
ALTER TABLE RelatedProducts
ADD CONSTRAINT related_key PRIMARY KEY (product_id, product_related_id);
ALTER TABLE UserOrder
ADD CONSTRAINT order_key PRIMARY KEY (user_id, id);
ALTER TABLE Item
ADD CONSTRAINT item_key PRIMARY KEY (user_id, order_id, id);
ALTER TABLE Discount
ADD CONSTRAINT discount_key PRIMARY KEY (id);

ALTER TABLE AppUser
ADD CONSTRAINT card_number_key UNIQUE (card_number);

ALTER TABLE AppUser
ADD CONSTRAINT recommendedBy_fk FOREIGN KEY (recommendedBy) REFERENCES AppUser (id);
ALTER TABLE PaymentMethod
ADD CONSTRAINT user_fk FOREIGN KEY (user_id) REFERENCES AppUser (id);
ALTER TABLE Product
ADD CONSTRAINT category_fk FOREIGN KEY (category) REFERENCES Category (id);
ALTER TABLE RelatedProducts
ADD CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES Product (id);
ALTER TABLE RelatedProducts
ADD CONSTRAINT product_related_id_fk FOREIGN KEY (product_related_id) REFERENCES Product (id);
ALTER TABLE UserOrder
ADD CONSTRAINT refUser_fk FOREIGN KEY (user_id) REFERENCES AppUser (id);
ALTER TABLE Item
ADD CONSTRAINT order_fk FOREIGN KEY (user_id, order_id) REFERENCES UserOrder (user_id, id);
ALTER TABLE Item
ADD CONSTRAINT includeProduct_fk FOREIGN KEY (product_id) REFERENCES Product (id);
ALTER TABLE Discount
ADD CONSTRAINT orderForDiscount_fk FOREIGN KEY (user_id, order_id) REFERENCES UserOrder (user_id, id);