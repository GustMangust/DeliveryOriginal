using DeliveryOriginal.Admin.Interfaces;
using DeliveryOriginal.Admin.Models;
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
            await UnitOfWork.UserRepository.Insert(new User
            {
                Login = "GOD",
                FullName = "Aliaksei Mukavozchyk",
                Password = "TestPassword123",
                Role = "StringRole"
            });

            var users = await UnitOfWork.UserRepository.GetAll();

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