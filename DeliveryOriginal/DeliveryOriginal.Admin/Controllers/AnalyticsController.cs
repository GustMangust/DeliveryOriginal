using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Models;
using System.Web.Mvc;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize(Role = RoleGroup.Regulars)]
    public class AnalyticsController : Controller
    {
        public ActionResult Index()
        {
            ViewBag.Message = "Your application description page.";

            return View();
        }
    }
}