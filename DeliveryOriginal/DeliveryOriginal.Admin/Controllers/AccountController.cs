using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    public class AccountController : Controller
    {
        [AllowAnonymous]
        public ActionResult Login(string returnUrl)
        {
            if (User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Index", "Home");
            }

            ViewBag.ReturnUrl = !string.IsNullOrEmpty(returnUrl)
                                && (returnUrl.ToLower().Contains("logoff") || returnUrl.ToLower().Contains("error/render"))
                              ? string.Empty : returnUrl;
            ClearSessionCookie();

            return View();
        }

        private void ClearSessionCookie()
        {
            var sessionCookie = Response.Cookies["sc"] ?? new HttpCookie("sc");

            sessionCookie.Value = string.Empty;
            sessionCookie.Expires = DateTime.MinValue;
        }

    }
}