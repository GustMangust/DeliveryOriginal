using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize(Role = RoleGroup.Regulars)]
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

        [HttpGet]
        public ActionResult CreateUser()
        {
            var user = new User();

            return View(user);
        }

        [HttpPost]
        public async Task<ActionResult> CreateUser(User user)
        {
            await UnitOfWork.UserRepository.Insert(user);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task<ActionResult> EditUser(int id)
        {
            var user = await UnitOfWork.UserRepository.Get(id);

            return View(user);
        }

        [HttpPost]
        public async Task<ActionResult> EditUser(User user)
        {
            await UnitOfWork.UserRepository.Update(user);

            return RedirectToAction("Index");
        }

        [HttpGet]
        public async Task DeleteUser(int userId)
        {
            await UnitOfWork.UserRepository.Delete(userId);
        }
    }
}