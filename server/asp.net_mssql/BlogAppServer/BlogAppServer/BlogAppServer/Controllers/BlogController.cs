using BlogAppServer.DataAccess;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;

namespace BlogAppServer.Controllers
{
    [RoutePrefix("api")]
    public class BlogController : ApiController
    {
        [HttpPost]
        [Route("register_user")]
        public IHttpActionResult RegisterUser([FromBody] dynamic data)
        {
            string userName = data.userName;
            int userId = Database.RegisterUser(userName);
            return Ok(new { userId = userId, status = true, responseCode = 0, message = "User registered successfully" });
        }

        [HttpPost]
        [Route("add_blog")]
        public IHttpActionResult AddBlog([FromBody] dynamic data)
        {
            int userId = data.userId;
            string title = data.title;
            string description = data.description;
            int blogId = Database.AddBlog(userId, title, description);
            return Ok(new { blogId = blogId, status = true, responseCode = 0, message = "Blog inserted successfully" });
        }

        [HttpPost]
        [Route("get_blogs")]
        public IHttpActionResult GetBlogs([FromBody] dynamic data)
        {
            int userId = data.userId;
            var blogs = Database.GetBlogs(userId);
            if (blogs.Count > 0)
            {
                return Ok(new { status = true, responseCode = 0, message = "Blogs are available", blogs = blogs });
            }
            else
            {
                return Ok(new { status = false, responseCode = 104, message = "Blogs are not available" });
            }
        }

        [HttpPost]
        [Route("update_blog")]
        public IHttpActionResult UpdateBlog([FromBody] dynamic data)
        {
            int blogId = data.blogId;
            string title = data.title;
            string description = data.description;
            bool isUpdated = Database.UpdateBlog(blogId, title, description);
            if (isUpdated)
            {
                return Ok(new { blogId = blogId, status = true, responseCode = 0, message = "Blog updated successfully" });
            }
            else
            {
                return Ok(new { status = false, responseCode = 105, message = "Blog updation failed" });
            }
        }

        [HttpPost]
        [Route("delete_blog")]
        public IHttpActionResult DeleteBlog([FromBody] dynamic data)
        {
            int blogId = data.blogId;
            bool isDeleted = Database.DeleteBlog(blogId);
            if (isDeleted)
            {
                return Ok(new { blogId = blogId, status = true, responseCode = 0, message = "Blog deleted successfully" });
            }
            else
            {
                return Ok(new { status = false, responseCode = 106, message = "Blog deletion failed" });
            }
        }
    }
}