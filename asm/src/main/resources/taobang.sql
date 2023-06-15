create database java5_store
go
use java5_store

create table Customer(
	ID int identity(1,1) primary key,
	fullname nvarchar(200),
	email varchar(100) unique,
	passwords varchar(100),
	phone varchar(100),
	age int,
	gender bit default 0,
	addresss nvarchar(100),
	active bit default 1,
	admins bit default 0
)

create table category(
	id int identity(1,1) primary key,
	names nvarchar(100)
)

create table Product(
	id int identity(1,1) primary key,
	names nvarchar(100),
	quantity int,
	img nvarchar(100),
	descript nvarchar(max),
	price money,
	date_at datetime default getdate(),
	date_delete datetime,
	category_Id int,
	existss bit
	foreign key(category_Id) references category(id)
)



CREATE TABLE ORDERS
(
    ORDER_ID     INT IDENTITY (1,1) PRIMARY KEY,
    CUSTOMER_ID  int,
    ORDER_DATE   DATETIME,
    TOTAL_AMOUNT DECIMAL(18, 2),
	addresss nvarchar(500),
	phone varchar(100),
	STATUSS		 INT,
    CONSTRAINT fk_ORDERS_Customer_cus FOREIGN KEY (CUSTOMER_ID) REFERENCES Customer (ID),
);


CREATE TABLE ORDERITEMS
(
    ORDER_ITEM_ID INT IDENTITY (1,1) PRIMARY KEY,
    QUANTITY      INT,
    PRICE         DECIMAL(18, 2),
    PRODUCT_ID    INT,
    ORDER_ID      INT,
	ITEM_RETURN	  INT,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES Product (id)
)

CREATE TABLE UPDATESTATUS(
	id int identity(1,1) primary key,
	ORDER_ID int,
	gmail  varchar(100),
	date_update datetime,
	descriptions nvarchar(1000),
	FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ORDER_ID),
)

CREATE TABLE PRICEBETWEEN(
	ID 			INT IDENTITY (1,1) PRIMARY KEY,
	NAMES NVARCHAR(255),
	IDCATEGORY		int,
	PRICE		NVARCHAR(255),
)


go
drop trigger IF EXISTS Update_Product_When_Insert_OrderItems
GO
CREATE TRIGGER Update_Product_When_Insert_OrderItems ON ORDERITEMS after insert
AS
BEGIN
	update Product
	set quantity = quantity - (SELECT QUANTITY FROM inserted)
	where id = (SELECT PRODUCT_ID FROM inserted)
END

go
drop trigger IF EXISTS Update_Product_Where_Quantity_Bigger_Zero
GO
CREATE TRIGGER Update_Product_Where_Quantity_Bigger_Zero ON Product after UPDATE
AS
BEGIN
	if exists (select * from inserted where quantity < 0)
		begin
			rollback transaction
		end
END

go
drop trigger IF EXISTS Update_Product_When_Remove_OrderItems
GO
CREATE TRIGGER Update_Product_When_Remove_OrderItems ON ORDERITEMS after UPDATE
AS
BEGIN
	if exists (select * from inserted where ITEM_RETURN = 0)
	begin
		update Product
		set quantity = quantity + (select i.quantity from inserted i)
		where id = (select i.PRODUCT_ID from inserted i)
		declare @idOrder int = (select i.ORDER_ID from inserted i)
		if not exists (select * from ORDERS o join ORDERITEMS i 
				on o.ORDER_ID = i.ORDER_ID
		where o.ORDER_ID =  @idOrder and i.ITEM_RETURN > 0)
		begin
			update ORDERS
			set STATUSS = 0
			where ORDER_ID = @idOrder
		end
	end
END

go
drop trigger IF EXISTS Update_Product_When_Update_ItemReturn_OrderItems
GO
CREATE TRIGGER Update_Product_When_Update_ItemReturn_OrderItems ON ORDERITEMS after UPDATE
AS
BEGIN
	if exists (select * from inserted where ITEM_RETURN = 3)
	begin
		update Product
		set quantity = quantity + (select i.quantity from inserted i)
		where id = (select i.PRODUCT_ID from inserted i)
	end
END

go
drop trigger IF EXISTS Update_Product_When_Giam_Quantity_OrderItems
GO
CREATE TRIGGER Update_Product_When_Giam_Quantity_OrderItems ON ORDERITEMS after UPDATE
AS
BEGIN
	if exists (select * from inserted where QUANTITY > 5 or QUANTITY <1)
		begin
			rollback transaction
		end

	declare @old int = (select QUANTITY from deleted)
	declare @new int = (select QUANTITY from inserted)

	declare @quantity int = @old - @new

	update Product
	set quantity = quantity + @quantity
	where id = (select i.PRODUCT_ID from deleted i)

END

go
drop trigger IF EXISTS Update_Product_When_Update_Order
go
CREATE TRIGGER Update_Product_When_Update_Order on Orders after update
as
begin
	if exists (select * from inserted where STATUSS = 0)
		begin
			Declare @id int = (select ORDER_ID from inserted)
			Declare @dataAo table(id int identity(1,1),idProduct int, quantity int)
			insert into @dataAo(idProduct,quantity) 
				select PRODUCT_ID,QUANTITY from ORDERITEMS where ORDER_ID = @id
			--chuẩn bị để chạy while
			DECLARE @RowCount INT = (SELECT max(id) FROM @dataAo);
			DECLARE @Counter INT = 1;

			--chạy while
			While @Counter <= @RowCount
			begin
				update Product
				set quantity = quantity + (select datas.quantity from @dataAo datas where datas.id = @Counter)
				where id = (select datas.idProduct from @dataAo datas where datas.id = @Counter)
				
				SET @Counter = @Counter + 1;
			end
		end
end


go
insert into PRICEBETWEEN(NAMES,PRICE,IDCATEGORY)
values
--Laptop
(N'Dưới 5 triệu',N'0,5000000.0',1),
(N'5-10 triệu',N'5000000.0,10000000.0',1),
(N'10-15 triệu',N'10000000.0,15000000.0',1),
(N'15-20 triệu',N'15000000.0,20000000.0',1),
(N'20-30 triệu',N'20000000.0,30000000.0',1),
(N'Trên 30 triệu',N'30000000.0,100000000.0',1),

--mobile
(N'Dưới 1 triệu',N'0.0,1000000.0',2),
(N'1-4 triệu',N'1000000.0,4000000.0',2),
(N'4-10 triệu',N'4000000.0,10000000.0',2),
(N'10-20 triệu',N'10000000.0,20000000.0',2),
(N'20-30 triệu',N'20000000.0,30000000.0',2),

--tablets
(N'Dưới 3 triệu',N'0.0,3000000.0',3),
(N'3-6 triệu',N'3000000.0,6000000.0',3),
(N'6-10 triệu',N'6000000.0,10000000.0',3),
(N'Trên 10 triệu',N'10000000.0,100000000.0',3),

--phụ kiện
(N'Dưới 300 K',N'0.0,300000.0',4),
(N'3-600 K',N'300000.0,600000.0',4),
(N'6-1000 K',N'600000.0,1000000.0',4),
(N'Trên 1 triệu',N'1000000.0,100000000.0',4)

select * from ORDERITEMS where ORDER_ITEM_ID = 24621