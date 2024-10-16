CREATE PROCEDURE GetCategoriesWithQuantity
AS
BEGIN
    SELECT 
        c.categoryID, 
        c.categoryName, 
        c.categoryDescription,
        SUM(p.productQuantity) AS categoryQuantity
    FROM 
        Category c
    LEFT JOIN 
        Product p ON c.categoryID = p.categoryID
    GROUP BY 
        c.categoryID, 
        c.categoryName, 
        c.categoryDescription;
END;
