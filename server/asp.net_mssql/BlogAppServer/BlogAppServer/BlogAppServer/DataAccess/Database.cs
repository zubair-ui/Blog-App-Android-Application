using BlogAppServer.Models;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;

namespace BlogAppServer.DataAccess
{
    public static class Database
    {
        private static string connectionString = "Data Source=DESKTOP-MBRBA5T;Initial Catalog=db_blogs_app;Integrated Security=True;Trust Server Certificate=True";

        public static int RegisterUser(string userName)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "INSERT INTO [User] (Name) OUTPUT INSERTED.Id VALUES (@Name)";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@Name", userName);
                connection.Open();
                return (int)command.ExecuteScalar();
            }
        }

        public static int AddBlog(int userId, string title, string description)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "INSERT INTO Blog (Title, Description, UserId) OUTPUT INSERTED.Id VALUES (@Title, @Description, @UserId)";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@Title", title);
                command.Parameters.AddWithValue("@Description", description);
                command.Parameters.AddWithValue("@UserId", userId);
                connection.Open();
                return (int)command.ExecuteScalar();
            }
        }

        public static List<Blog> GetBlogs(int userId)
        {
            List<Blog> blogs = new List<Blog>();

            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "SELECT * FROM Blog WHERE UserId = @UserId";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@UserId", userId);
                connection.Open();
                using (SqlDataReader reader = command.ExecuteReader())
                {
                    while (reader.Read())
                    {
                        blogs.Add(new Blog
                        {
                            Id = (int)reader["Id"],
                            Title = (string)reader["Title"],
                            Description = (string)reader["Description"],
                            UserId = (int)reader["UserId"]
                        });
                    }
                }
            }

            return blogs;
        }

        public static bool UpdateBlog(int blogId, string title, string description)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "UPDATE Blog SET Title = @Title, Description = @Description WHERE Id = @Id";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@Id", blogId);
                command.Parameters.AddWithValue("@Title", title);
                command.Parameters.AddWithValue("@Description", description);
                connection.Open();
                return command.ExecuteNonQuery() > 0;
            }
        }

        public static bool DeleteBlog(int blogId)
        {
            using (SqlConnection connection = new SqlConnection(connectionString))
            {
                string query = "DELETE FROM Blog WHERE Id = @Id";
                SqlCommand command = new SqlCommand(query, connection);
                command.Parameters.AddWithValue("@Id", blogId);
                connection.Open();
                return command.ExecuteNonQuery() > 0;
            }
        }
    }
}
