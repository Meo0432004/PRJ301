CREATE TABLE Category (
    categoryID VARCHAR(50) PRIMARY KEY,
    categoryName VARCHAR(100) NOT NULL,
	categoryDescription NVARCHAR(1000)
);

CREATE TABLE Product (
    productID VARCHAR(50) PRIMARY KEY,
	categoryID VARCHAR(50) NOT NULL,
    productName VARCHAR(100) NOT NULL,
    productDescription TEXT,
	productPrice DECIMAL(18, 2) NOT NULL,
    productQuantity INT,
    productImage VARCHAR(255),
    FOREIGN KEY (categoryID) REFERENCES Category(categoryID)
);

CREATE TABLE [Admin] (
    adminID INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) NOT NULL UNIQUE,
    [password] VARCHAR(255) NOT NULL,
    fullname VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15)
);

CREATE TABLE CurrentAdmin (
    id INT PRIMARY KEY,
    adminID INT NOT NULL,
    FOREIGN KEY (adminID) REFERENCES [Admin](adminID)
);


CREATE TABLE [User] (
    userID INT PRIMARY KEY IDENTITY(1,1),
    username VARCHAR(50) UNIQUE,
    [password] VARCHAR(255),
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    userProvinceCity VARCHAR(255),
    userDistrict VARCHAR(100),
    userType VARCHAR(10) NOT NULL CHECK (userType IN ('Customer', 'Guest')),
    userCreateAt DATETIME DEFAULT GETDATE()
);

CREATE TABLE [Transaction] (
    transactionID INT PRIMARY KEY IDENTITY(1,1),
    adminID INT,
    productID VARCHAR(50) NOT NULL,
    quantity INT NOT NULL,
    transactionDate DATETIME DEFAULT GETDATE(),
    transactionType VARCHAR(50) NOT NULL CHECK (transactionType IN ('Buy', 'In', 'Out')),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (adminID) REFERENCES [Admin](adminID)
);

CREATE TABLE Review (
    reviewID INT PRIMARY KEY IDENTITY(1,1),
    productID VARCHAR(50) NOT NULL,
    customerID INT NOT NULL,
    reviewText TEXT,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    reviewDate DATETIME DEFAULT GETDATE(),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (customerID) REFERENCES [User](userID)
);

CREATE TABLE [Order] (
    orderID INT PRIMARY KEY IDENTITY(1,1),
    userID INT NOT NULL,
    totalPrice DECIMAL(18, 2) NOT NULL,
    orderStatus VARCHAR(50) DEFAULT 'Pending',
    [date] DATETIME DEFAULT GETDATE(),
    isShipping BIT DEFAULT 0, -- 0: không cần địa chỉ giao hàng, 1: cần địa chỉ giao hàng
    FOREIGN KEY (userID) REFERENCES [User](userID)
);

CREATE TABLE Shipping (
    shippingID INT PRIMARY KEY IDENTITY(1,1),
    orderID INT NOT NULL,
	userID INT NOT NULL,
    shippingProvinceCity VARCHAR(255) NOT NULL,
    shippingDistrict VARCHAR(100) NOT NULL,
    FOREIGN KEY (orderID) REFERENCES [Order](orderID),
	FOREIGN KEY (userID) References [User](userID)
);

CREATE TABLE OrderItems (
    orderItemID INT PRIMARY KEY IDENTITY(1,1),
    orderID INT NOT NULL,
    productID VARCHAR(50) NOT NULL,
    unitPrice DECIMAL(18, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (orderID) REFERENCES [Order](orderID),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);
