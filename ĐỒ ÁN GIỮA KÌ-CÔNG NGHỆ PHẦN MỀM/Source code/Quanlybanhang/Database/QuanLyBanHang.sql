USE [master]
GO
/****** Object:  Database [QuanLyBanHang]    Script Date: 21/05/2025 12:08:10 PM ******/
CREATE DATABASE [QuanLyBanHang]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'QuanLyBanHang', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.QUANGVINH\MSSQL\DATA\QuanLyBanHang.mdf' , SIZE = 73728KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'QuanLyBanHang_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL16.QUANGVINH\MSSQL\DATA\QuanLyBanHang_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [QuanLyBanHang] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [QuanLyBanHang].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ARITHABORT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [QuanLyBanHang] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [QuanLyBanHang] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET  ENABLE_BROKER 
GO
ALTER DATABASE [QuanLyBanHang] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [QuanLyBanHang] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET RECOVERY FULL 
GO
ALTER DATABASE [QuanLyBanHang] SET  MULTI_USER 
GO
ALTER DATABASE [QuanLyBanHang] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [QuanLyBanHang] SET DB_CHAINING OFF 
GO
ALTER DATABASE [QuanLyBanHang] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [QuanLyBanHang] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [QuanLyBanHang] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [QuanLyBanHang] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'QuanLyBanHang', N'ON'
GO
ALTER DATABASE [QuanLyBanHang] SET QUERY_STORE = ON
GO
ALTER DATABASE [QuanLyBanHang] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [QuanLyBanHang]
GO
/****** Object:  Table [dbo].[Admin]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Admin](
	[Admin_ID] [varchar](20) NOT NULL,
	[Admin_Name] [nvarchar](255) NOT NULL,
	[Gender] [varchar](10) NULL,
	[Email] [varchar](255) NULL,
	[Contact] [varchar](15) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[Image] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bill_Exported]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill_Exported](
	[Invoice_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NULL,
	[Total_Product] [int] NOT NULL,
	[Description] [varchar](50) NULL,
 CONSTRAINT [PK_Bill_Exported_1] PRIMARY KEY CLUSTERED 
(
	[Invoice_No] ASC,
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bill_Exported_Details]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill_Exported_Details](
	[Invoice_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NULL,
	[Product_ID] [varchar](20) NOT NULL,
	[IMei_No] [varchar](50) NOT NULL,
	[Unit_Price] [decimal](10, 2) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Discount_Values] [decimal](10, 2) NULL,
	[Total_Price_Before] [decimal](15, 2) NOT NULL,
	[Total_Price_After] [decimal](15, 2) NOT NULL,
	[Date_Exported] [date] NOT NULL,
	[Time_Exported] [time](7) NOT NULL,
 CONSTRAINT [PK_Bill_Exported_Details_1] PRIMARY KEY CLUSTERED 
(
	[Invoice_No] ASC,
	[Admin_ID] ASC,
	[IMei_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bill_Imported]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill_Imported](
	[Invoice_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Total_Product] [int] NOT NULL,
	[Total_Price] [decimal](18, 2) NOT NULL,
 CONSTRAINT [PK_Bill_Imported] PRIMARY KEY CLUSTERED 
(
	[Invoice_No] ASC,
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bill_Imported_Details]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill_Imported_Details](
	[Invoice_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Product_ID] [varchar](50) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Unit_Price] [decimal](18, 2) NOT NULL,
	[Total_Price] [decimal](18, 2) NOT NULL,
	[Date_Imported] [date] NOT NULL,
	[Time_Imported] [time](7) NOT NULL,
 CONSTRAINT [PK_Bill_Imported_Details] PRIMARY KEY CLUSTERED 
(
	[Invoice_No] ASC,
	[Admin_ID] ASC,
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Cart]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Cart](
	[Customer_ID] [varchar](50) NOT NULL,
	[Product_ID] [varchar](50) NOT NULL,
	[Quantity] [int] NULL,
 CONSTRAINT [PK_Cart] PRIMARY KEY CLUSTERED 
(
	[Customer_ID] ASC,
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Category]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Category](
	[Category_ID] [varchar](50) NOT NULL,
	[Category_Name] [nvarchar](225) NOT NULL,
	[Sup_ID] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Category] PRIMARY KEY CLUSTERED 
(
	[Category_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Customer]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Customer](
	[Customer_ID] [varchar](50) NOT NULL,
	[Full_Name] [nvarchar](255) NOT NULL,
	[Gender] [varchar](10) NULL,
	[Date_Of_Birth] [date] NOT NULL,
	[Email] [varchar](255) NULL,
	[Contact] [varchar](15) NOT NULL,
	[Address] [nvarchar](255) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[Status] [varchar](20) NOT NULL,
 CONSTRAINT [PK__Customer__72140EDFE625E238] PRIMARY KEY CLUSTERED 
(
	[Customer_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [UQ__Customer__A9D1053419D73A93] UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[IMei_Product]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[IMei_Product](
	[IMei_No] [varchar](50) NOT NULL,
	[Product_ID] [varchar](50) NOT NULL,
	[State] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[IMei_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Insurance]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Insurance](
	[Insurance_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NULL,
	[Describle_customer] [varchar](50) NULL,
	[Start_Date_Insurance] [date] NOT NULL,
	[End_Date_Insurance] [date] NOT NULL,
 CONSTRAINT [PK_Insurance] PRIMARY KEY CLUSTERED 
(
	[Insurance_No] ASC,
	[Admin_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Insurance_Details]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Insurance_Details](
	[Insurance_No] [varchar](50) NOT NULL,
	[Admin_ID] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NULL,
	[Product_ID] [varchar](50) NOT NULL,
	[IMei_No] [varchar](50) NOT NULL,
	[Description] [nvarchar](255) NULL,
	[Date_Insurance] [date] NOT NULL,
	[Time_Insurance] [time](7) NOT NULL,
 CONSTRAINT [PK_Insurance_Details] PRIMARY KEY CLUSTERED 
(
	[Insurance_No] ASC,
	[Admin_ID] ASC,
	[IMei_No] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders](
	[Order_No] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NOT NULL,
	[Total_Quantity_Product] [int] NOT NULL,
	[Total_Price] [decimal](15, 2) NOT NULL,
	[Payment] [varchar](20) NOT NULL,
	[Date_Order] [date] NULL,
	[Time_Order] [time](7) NULL,
 CONSTRAINT [PK__Orders__792E2DA7B22E4759] PRIMARY KEY CLUSTERED 
(
	[Order_No] ASC,
	[Customer_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Orders_Details]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Orders_Details](
	[Order_No] [varchar](20) NOT NULL,
	[Customer_ID] [varchar](50) NOT NULL,
	[Product_ID] [varchar](50) NOT NULL,
	[Price] [decimal](10, 2) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Date_Order] [date] NOT NULL,
	[Time_Order] [time](7) NOT NULL,
	[Status] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_Orders_Details] PRIMARY KEY CLUSTERED 
(
	[Order_No] ASC,
	[Customer_ID] ASC,
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product](
	[Product_ID] [varchar](50) NOT NULL,
	[Product_Name] [nvarchar](255) NOT NULL,
	[CPU] [varchar](100) NOT NULL,
	[Ram] [varchar](100) NOT NULL,
	[Graphics_Card] [varchar](100) NOT NULL,
	[Operating_System] [varchar](20) NOT NULL,
	[Price] [decimal](10, 2) NOT NULL,
	[Quantity] [int] NOT NULL,
	[Warranty_Period] [nvarchar](50) NOT NULL,
	[Status] [varchar](20) NOT NULL,
	[Spoiled_Quantity] [int] NOT NULL,
	[Category_ID] [varchar](50) NULL,
	[Image] [varchar](255) NOT NULL,
 CONSTRAINT [PK__Product__9834FB9AFB3C9D93] PRIMARY KEY CLUSTERED 
(
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Product_Stock]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Product_Stock](
	[Product_ID] [varchar](50) NOT NULL,
	[Quantity_Stock] [int] NOT NULL,
 CONSTRAINT [PK__Product___9834FB9AC357FE73] PRIMARY KEY CLUSTERED 
(
	[Product_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Supplier]    Script Date: 21/05/2025 12:08:11 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Supplier](
	[Sup_ID] [varchar](100) NOT NULL,
	[Sup_Name] [nvarchar](250) NOT NULL,
	[Address] [nvarchar](255) NOT NULL,
	[Contact] [varchar](100) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[Sup_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Bill_Exported] ADD  CONSTRAINT [DF__Bill_Expo__Total__3FD07829]  DEFAULT ((0)) FOR [Total_Product]
GO
ALTER TABLE [dbo].[Bill_Imported] ADD  CONSTRAINT [DF__Bill_Impo__Total__5A846E65]  DEFAULT ((0)) FOR [Total_Product]
GO
ALTER TABLE [dbo].[Bill_Imported] ADD  CONSTRAINT [DF__Bill_Impo__Total__5B78929E]  DEFAULT ((0.00)) FOR [Total_Price]
GO
ALTER TABLE [dbo].[Customer] ADD  CONSTRAINT [DF_Customer_Status]  DEFAULT ('Inactive') FOR [Status]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF__Orders__Total_Qu__66EA454A]  DEFAULT ((0)) FOR [Total_Quantity_Product]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF__Orders__Total_Pr__67DE6983]  DEFAULT ((0.00)) FOR [Total_Price]
GO
ALTER TABLE [dbo].[Orders] ADD  CONSTRAINT [DF__Orders__Payment__00AA174D]  DEFAULT ('Cash') FOR [Payment]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF__Product__Operati__79FD19BE]  DEFAULT ('N/A') FOR [Operating_System]
GO
ALTER TABLE [dbo].[Product] ADD  CONSTRAINT [DF__Product__Warrant__45544755]  DEFAULT (N'12 tháng') FOR [Warranty_Period]
GO
ALTER TABLE [dbo].[Bill_Exported]  WITH CHECK ADD  CONSTRAINT [FK_BillExported_Admin] FOREIGN KEY([Admin_ID])
REFERENCES [dbo].[Admin] ([Admin_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Exported] CHECK CONSTRAINT [FK_BillExported_Admin]
GO
ALTER TABLE [dbo].[Bill_Exported]  WITH CHECK ADD  CONSTRAINT [FK_BillExported_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Exported] CHECK CONSTRAINT [FK_BillExported_Customer]
GO
ALTER TABLE [dbo].[Bill_Exported_Details]  WITH CHECK ADD  CONSTRAINT [FK_BillExportedDetails_Invoice] FOREIGN KEY([Invoice_No], [Admin_ID])
REFERENCES [dbo].[Bill_Exported] ([Invoice_No], [Admin_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Exported_Details] CHECK CONSTRAINT [FK_BillExportedDetails_Invoice]
GO
ALTER TABLE [dbo].[Bill_Imported]  WITH CHECK ADD  CONSTRAINT [FK_BillImported_Admin] FOREIGN KEY([Admin_ID])
REFERENCES [dbo].[Admin] ([Admin_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Imported] CHECK CONSTRAINT [FK_BillImported_Admin]
GO
ALTER TABLE [dbo].[Bill_Imported_Details]  WITH CHECK ADD  CONSTRAINT [FK_BillImported] FOREIGN KEY([Invoice_No], [Admin_ID])
REFERENCES [dbo].[Bill_Imported] ([Invoice_No], [Admin_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Imported_Details] CHECK CONSTRAINT [FK_BillImported]
GO
ALTER TABLE [dbo].[Bill_Imported_Details]  WITH CHECK ADD  CONSTRAINT [FK_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Bill_Imported_Details] CHECK CONSTRAINT [FK_Product]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_Customer]
GO
ALTER TABLE [dbo].[Cart]  WITH CHECK ADD  CONSTRAINT [FK_Cart_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
GO
ALTER TABLE [dbo].[Cart] CHECK CONSTRAINT [FK_Cart_Product]
GO
ALTER TABLE [dbo].[Category]  WITH CHECK ADD  CONSTRAINT [FK_Category_Supplier] FOREIGN KEY([Sup_ID])
REFERENCES [dbo].[Supplier] ([Sup_ID])
GO
ALTER TABLE [dbo].[Category] CHECK CONSTRAINT [FK_Category_Supplier]
GO
ALTER TABLE [dbo].[IMei_Product]  WITH CHECK ADD  CONSTRAINT [FK_Product_IMei] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[IMei_Product] CHECK CONSTRAINT [FK_Product_IMei]
GO
ALTER TABLE [dbo].[Insurance]  WITH CHECK ADD  CONSTRAINT [FK_Insurance_Admin] FOREIGN KEY([Admin_ID])
REFERENCES [dbo].[Admin] ([Admin_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Insurance] CHECK CONSTRAINT [FK_Insurance_Admin]
GO
ALTER TABLE [dbo].[Insurance]  WITH CHECK ADD  CONSTRAINT [FK_Insurance_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Insurance] CHECK CONSTRAINT [FK_Insurance_Customer]
GO
ALTER TABLE [dbo].[Insurance_Details]  WITH CHECK ADD  CONSTRAINT [FK_InsuranceDetails_Insurance] FOREIGN KEY([Insurance_No], [Admin_ID])
REFERENCES [dbo].[Insurance] ([Insurance_No], [Admin_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Insurance_Details] CHECK CONSTRAINT [FK_InsuranceDetails_Insurance]
GO
ALTER TABLE [dbo].[Orders]  WITH CHECK ADD  CONSTRAINT [FK_Orders_Customer] FOREIGN KEY([Customer_ID])
REFERENCES [dbo].[Customer] ([Customer_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Orders] CHECK CONSTRAINT [FK_Orders_Customer]
GO
ALTER TABLE [dbo].[Orders_Details]  WITH CHECK ADD  CONSTRAINT [FK_OrdersDetails_Orders] FOREIGN KEY([Order_No], [Customer_ID])
REFERENCES [dbo].[Orders] ([Order_No], [Customer_ID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Orders_Details] CHECK CONSTRAINT [FK_OrdersDetails_Orders]
GO
ALTER TABLE [dbo].[Product_Stock]  WITH CHECK ADD  CONSTRAINT [FK_ProductStock_Product] FOREIGN KEY([Product_ID])
REFERENCES [dbo].[Product] ([Product_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Product_Stock] CHECK CONSTRAINT [FK_ProductStock_Product]
GO
ALTER TABLE [dbo].[Bill_Exported_Details]  WITH CHECK ADD  CONSTRAINT [CK__Bill_Expo__Quant__05A3D694] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Bill_Exported_Details] CHECK CONSTRAINT [CK__Bill_Expo__Quant__05A3D694]
GO
ALTER TABLE [dbo].[Bill_Exported_Details]  WITH CHECK ADD  CONSTRAINT [CK__Bill_Expo__Quant__41EDCAC5] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Bill_Exported_Details] CHECK CONSTRAINT [CK__Bill_Expo__Quant__41EDCAC5]
GO
ALTER TABLE [dbo].[Bill_Exported_Details]  WITH CHECK ADD  CONSTRAINT [CK__Bill_Expo__Quant__72910220] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Bill_Exported_Details] CHECK CONSTRAINT [CK__Bill_Expo__Quant__72910220]
GO
ALTER TABLE [dbo].[Orders_Details]  WITH CHECK ADD  CONSTRAINT [CK__Orders_De__Quant__0697FACD] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Orders_Details] CHECK CONSTRAINT [CK__Orders_De__Quant__0697FACD]
GO
ALTER TABLE [dbo].[Orders_Details]  WITH CHECK ADD  CONSTRAINT [CK__Orders_De__Quant__19DFD96B] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Orders_Details] CHECK CONSTRAINT [CK__Orders_De__Quant__19DFD96B]
GO
ALTER TABLE [dbo].[Orders_Details]  WITH CHECK ADD  CONSTRAINT [CK__Orders_De__Quant__73852659] CHECK  (([Quantity]>(0)))
GO
ALTER TABLE [dbo].[Orders_Details] CHECK CONSTRAINT [CK__Orders_De__Quant__73852659]
GO
USE [master]
GO
ALTER DATABASE [QuanLyBanHang] SET  READ_WRITE 
GO
