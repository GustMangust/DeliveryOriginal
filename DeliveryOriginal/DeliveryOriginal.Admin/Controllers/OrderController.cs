using DeliveryOriginal.Admin.Interfaces;
using DeliveryOriginal.Admin.Models;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    public class OrderController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public OrderController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            //await UnitOfWork.UserRepository.Insert(new User
            //{
            //    Login = "GOD",
            //    Password = "TestPass123505050505050505050505050505050",
            //    FullName = "Aliaksei",
            //    Role = "StringRole"
            //});

            List<User> users = await UnitOfWork.UserRepository.GetAll();

            var user = users.FirstOrDefault();

            user.FullName = "ДИМОС ОБНОВЛЁН";

            await UnitOfWork.UserRepository.Update(user);

            users = await UnitOfWork.UserRepository.GetAll();

            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Your contact page.";

            return View();
        }
    }
}