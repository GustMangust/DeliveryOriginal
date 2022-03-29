using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    public class UserController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }
    }
}