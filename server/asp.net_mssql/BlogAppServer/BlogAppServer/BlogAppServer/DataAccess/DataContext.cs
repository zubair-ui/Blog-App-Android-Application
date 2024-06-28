using BlogAppServer.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Web;

namespace BlogAppServer.DataAccess
{
    public class DataContext: DbContext
    {
        public DataContext() : base("DefaultConnection") { }

        public DbSet<User> Users { get; set; }
        public DbSet<Blog> Blogs { get; set; }
    }
}