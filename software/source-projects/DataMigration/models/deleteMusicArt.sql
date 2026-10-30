
ALTER TABLE AppUser
DROP CONSTRAINT recommendedBy_fk;
ALTER TABLE PaymentMethod
DROP CONSTRAINT user_fk;
ALTER TABLE Product
DROP CONSTRAINT category_fk;
ALTER TABLE RelatedProducts
DROP CONSTRAINT product_id_fk;
ALTER TABLE RelatedProducts
DROP CONSTRAINT product_related_id_fk;
ALTER TABLE UserOrder
DROP CONSTRAINT refUser_fk;
ALTER TABLE Item
DROP CONSTRAINT order_fk;
ALTER TABLE Item
DROP CONSTRAINT includeProduct_fk;
ALTER TABLE Discount
DROP CONSTRAINT orderForDiscount_fk;

ALTER TABLE AppUser
DROP CONSTRAINT card_number_key;

ALTER TABLE AppUser
DROP CONSTRAINT user_key;
ALTER TABLE PaymentMethod
DROP CONSTRAINT paymentMethod_key;
ALTER TABLE Product
DROP CONSTRAINT product_key;
ALTER TABLE Category
DROP CONSTRAINT category_key;
ALTER TABLE RelatedProducts
DROP CONSTRAINT related_key;
ALTER TABLE UserOrder
DROP CONSTRAINT order_key;
ALTER TABLE Item
DROP CONSTRAINT item_key;
ALTER TABLE Discount
DROP CONSTRAINT discount_key;

DELETE FROM AppUser;
DROP TABLE AppUser;

DELETE FROM PaymentMethod;
DROP TABLE PaymentMethod;

DELETE FROM Product;
DROP TABLE Product;

DELETE FROM Category;
DROP TABLE Category;

DELETE FROM RelatedProducts;
DROP TABLE RelatedProducts;

DELETE FROM UserOrder;
DROP TABLE UserOrder;

DELETE FROM Item;
DROP TABLE Item;

DELETE FROM Discount;
DROP TABLE Discount;
COMMIT;

