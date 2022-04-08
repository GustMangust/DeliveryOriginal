using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;

namespace DeliveryOriginal.Admin.Controllers
{
    public class AccountController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public AccountController()
        {
            UnitOfWork = new UnitOfWork();
        }

        // GET: Account
        public ActionResult Index()
        {
            return View();
        }

        [AllowAnonymous]
        [HttpGet]
        public ActionResult Login(string ReturnUrl = "")
        {
            if (User.Identity.IsAuthenticated)
            {
                return LogOut();
            }
            ViewBag.ReturnUrl = ReturnUrl;
            return View();
        }

        [HttpPost]
        public ActionResult Login(LoginViewModel model, string ReturnUrl = "")
        {
            if (ModelState.IsValid)
            {
                if (Membership.ValidateUser(model.Login, model.Password))
                {
                    var user = (CustomMembershipUser)Membership.GetUser(model.Login, false);
                    if (user != null)
                    {
                        User userModel = new User()
                        {
                            Id = user.Id,
                            Login = user.Login,
                            FullName = user.FullName,
                            Role = user.Role
                        };

                        string userData = JsonConvert.SerializeObject(userModel);
                        FormsAuthenticationTicket authTicket = new FormsAuthenticationTicket
                            (
                                1, model.Login, DateTime.Now, DateTime.Now.AddMinutes(60), false, userData
                            );

                        string enTicket = FormsAuthentication.Encrypt(authTicket);
                        HttpCookie faCookie = new HttpCookie("sc", enTicket);
                        Response.Cookies.Add(faCookie);
                    }

                    if (Url.IsLocalUrl(ReturnUrl))
                    {
                        return Redirect(ReturnUrl);
                    }
                    else
                    {
                        return RedirectToAction("OrderDashboard", "Order");
                    }
                }
            }
            ModelState.AddModelError("", "Something Wrong : Username or Password invalid ^_^ ");
            return View(model);
        }

        [HttpGet]
        public ActionResult Register()
        {
            return View();
        }

        [HttpPost]
        public async Task<ActionResult> Register(RegisterViewModel model)
        {
            bool statusRegistration = false;
            string messageRegistration = string.Empty;

            if (ModelState.IsValid)
            {
                // Email Verification
                var users = await UnitOfWork.UserRepository.GetAll();
                var user = users?.Where(u => u.Login == model.Login)?.FirstOrDefault();

                if (user != null)
                {
                    ModelState.AddModelError("Warning Email", "Sorry: Login already Exists");
                    return View(model);
                }

                //Save User Data
                var newUser = new User()
                {
                    Login = model.Login,
                    FullName = model.FullName,
                    Password = model.Password,
                    Role = RoleGroup.Regulars
                };

                await UnitOfWork.UserRepository.Insert(newUser);

                //Verification Email
                messageRegistration = "Your account has been created successfully. ^_^";
                statusRegistration = true;
            }
            else
            {
                messageRegistration = "Something Wrong!";
            }
            ViewBag.Message = messageRegistration;
            ViewBag.Status = statusRegistration;

            return View(model);
        }

        public ActionResult LogOut()
        {
            HttpCookie cookie = new HttpCookie("sc", "");
            cookie.Expires = DateTime.Now.AddYears(-1);
            Response.Cookies.Add(cookie);

            FormsAuthentication.SignOut();
            return RedirectToAction("Login", "Account", null);
        }

    }
}