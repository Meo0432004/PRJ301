CREATE Or Alter TRIGGER trg_UpdateProductQuantity
ON [Transaction]
AFTER INSERT
AS
BEGIN
    -- Update the product quantity for each inserted transaction
    UPDATE Product
    SET productQuantity = productQuantity - i.quantity
    FROM Product p
    JOIN inserted i ON p.productID = i.productID
    WHERE i.transactionType = 'Buy' 
      AND p.productQuantity >= i.quantity; -- Optional: Ensure product quantity doesn't go negative
END;

