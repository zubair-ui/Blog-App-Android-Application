﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace BlogAppServer.Models
{
    public class Blog
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public int UserId { get; set; }
    }
}