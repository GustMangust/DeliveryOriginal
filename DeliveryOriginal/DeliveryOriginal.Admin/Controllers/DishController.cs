using DeliveryOriginal.Admin.Identity;
using DeliveryOriginal.Admin.Interfaces;
using System.Threading.Tasks;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize]
    public class DishController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public DishController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            var dishes = await UnitOfWork.DishRepository.GetAll();

            return View(dishes);
        }
    }
}