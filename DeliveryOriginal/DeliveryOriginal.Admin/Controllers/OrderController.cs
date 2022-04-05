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

        public ActionResult Index()
        {
            return View();
        }

        public async Task<ActionResult> OrderDashboard(int? defaultOrderId, OrderOrderBy orderBy = OrderOrderBy.OrderNumberDesc)
        {
            var orders = await UnitOfWork.OrderRepository.GetAll();

            var model = new OrderDashboardVM
            {
                Orders = orders,
                SelectedOrder = defaultOrderId.HasValue ? orders.Where(order => order.Id == default).FirstOrDefault() : null,
                OrderOrderBy = orderBy
            };

            return View(model);
        }
    }
}