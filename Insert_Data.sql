INSERT INTO Category (categoryID, categoryName, categoryDescription)
VALUES
('SS', 'Samsung', 'Category for Samsung products'),  
('GG', 'Google', 'Category for Google products'),    
('IP', 'Apple', 'Category for Apple products');  

INSERT INTO Product (productID, categoryID, productName, productDescription, productPrice, productQuantity, productImage)
VALUES
('prod001', 'SS', 'Samsung Galaxy S22 Ultra', 'Description of Samsung Galaxy S22 Ultra', 20000000, 10, '/images/SS_S22Ultra.jpg'),
('prod002', 'SS', 'Samsung Galaxy S23 Ultra', 'Description of Samsung Galaxy S23 Ultra', 25000000, 15, '/images/SS_S23Ultra.jpg'),
('prod003', 'SS', 'Samsung Galaxy S24 Ultra', 'Description of Samsung Galaxy S24 Ultra', 30000000, 20, '/images/SS_S24Ultra.jpg'),
('prod004', 'GG', 'Google Pixel 6 Pro', 'Description of Google Pixel 6 Pro', 20000000, 10, '/images/GG_P6Pro.jpg'),
('prod005', 'GG', 'Google Pixel 7 Pro', 'Description of Google Pixel 7 Pro', 25000000, 15, '/images/GG_P7Pro.jpg'),
('prod006', 'GG', 'Google Pixel 8 Pro', 'Description of Google Pixel 8 Pro', 30000000, 20, '/images/GG_P8Pro.jpg'),
('prod007', 'IP', 'Iphone 13 Pro Max', 'Description of Iphone 13 Pro Max', 20000000, 10, '/images/IP_IP13ProMax.jpg'),
('prod008', 'IP', 'Iphone 14 Pro Max', 'Description of Iphone 14 Pro Max', 25000000, 15, '/images/IP_IP14ProMax.jpg'),
('prod009', 'IP', 'Iphone 15 Pro Max', 'Description of Iphone 15 Pro Max', 30000000, 20, '/images/IP_IP15ProMax.jpg');

Insert Into [Admin](username, [password], fullname, email, phone) 
values
('admin1', '111', 'Name of admin1', 'admin1@gmail.com', '123456789'),
('admin2', '222', 'Name of admin2', 'admin2@gmail.com', '111111111')

Insert Into CurrentAdmin(id, adminID) values (1, 1)