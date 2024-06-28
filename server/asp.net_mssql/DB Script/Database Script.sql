-- Database: db_blogs_app

USE master;
GO

-- Create the database if it doesn't exist
IF NOT EXISTS (SELECT name FROM sys.databases WHERE name = N'db_blogs_app')
BEGIN
    CREATE DATABASE db_blogs_app;
END;
GO

USE db_blogs_app;
GO

-- Drop existing tables if they exist
IF OBJECT_ID('dbo.blog', 'U') IS NOT NULL
    DROP TABLE dbo.blog;
GO

IF OBJECT_ID('dbo.[user]', 'U') IS NOT NULL
    DROP TABLE dbo.[user];
GO

-- Create table structure for `user`
CREATE TABLE dbo.[user] (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    [name] NVARCHAR(100) NOT NULL,
    date_time DATETIME DEFAULT GETDATE()
);
GO

-- Insert data into `user`
INSERT INTO dbo.[user] ([name], date_time) VALUES
('Mr. Ahmed', '2020-04-06 18:23:42'),
('Mr. Shahid', '2020-04-06 18:54:22'),
('Mr. Kamran', '2020-04-06 18:54:27'),
('Daniyal Nawaz', '2020-04-06 20:50:44');
GO

-- Create table structure for `blog`
CREATE TABLE dbo.blog (
    id INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(100) NOT NULL,
    description NVARCHAR(500) NOT NULL,
    date_time DATETIME DEFAULT GETDATE(),
    user_id INT NOT NULL,
    CONSTRAINT FK_blog_user FOREIGN KEY (user_id) REFERENCES dbo.[user] (id)
);
GO

-- Insert data into `blog`
INSERT INTO dbo.blog (title, description, date_time, user_id) VALUES
('Android App Development', 'Android App Development Description', '2020-04-06 18:29:40', 1),
('Android App Development', 'Android App Development Description', '2020-04-06 18:53:49', 1),
('React Native Updated', 'React Native Updated Description', '2020-04-06 18:54:53', 2),
('Flutter', 'Flutter Description', '2020-04-06 18:55:16', 3);
GO


