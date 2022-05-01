using DeliveryOriginal.Admin.Core.Identity;
using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using System.Web.Mvc;
using System.Linq;
using System.Threading.Tasks;
using System.Globalization;

namespace DeliveryOriginal.Admin.Controllers
{
    [CustomAuthorize(RoleGroup.AnalyticsAdministrators, RoleGroup.SuperAdministrator)]
    public class AnalyticsController : Controller
    {
        protected readonly IUnitOfWork UnitOfWork;
        public AnalyticsController()
        {
            UnitOfWork = new UnitOfWork();
        }

        public async Task<ActionResult> Index()
        {
            var orders = await UnitOfWork.OrderRepository.GetAll();

            var ordersByMonth = orders.GroupBy(x => (object)(new { Month = CultureInfo.CurrentCulture.DateTimeFormat.GetMonthName(x.SubmittedAt.Value.Month) }))
                                      .ToDictionary(g => g.Key, g => g.Count());
            var ordersIncomeByMonth = orders.GroupBy(x => (object)(new { Month = CultureInfo.CurrentCulture.DateTimeFormat.GetMonthName(x.SubmittedAt.Value.Month) }))
                                            .ToDictionary(g => g.Key, (g) => { return g.Sum(order => order.Dishes.Sum(d => d.Cost));
            });

            var model = new AnalyticsVM
            {
                OrdersByMonth = ordersByMonth,
                OrdersIncomeByMonth = ordersIncomeByMonth
            };

            return View(model);
        }
    }
}