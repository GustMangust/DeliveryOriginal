using DeliveryOriginal.Admin.Identity;
using DeliveryOriginal.Admin.Interfaces;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize]
    public class UserController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public UserController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            var users = await UnitOfWork.UserRepository.GetAll();

            return View(users);
        }
    }
}