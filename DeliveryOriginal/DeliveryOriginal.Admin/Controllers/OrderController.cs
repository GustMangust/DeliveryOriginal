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

            var dashboardOrders = GetDashboardOrders(orders);

            var model = new OrderDashboardVM
            {
                Orders = dashboardOrders,
                SelectedOrder = defaultOrderId.HasValue ? dashboardOrders.Where(order => order.Id == defaultOrderId).FirstOrDefault() : null,
                OrderOrderBy = orderBy
            };

            return View(model);
        }

        [HttpGet]
        public async Task<ActionResult> RenderDashboardOrderDetailsPartial(int? orderId = null)
        {
            if (orderId.HasValue)
            {
                var orders = await UnitOfWork.OrderRepository.GetAll();
                var dashboardOrders = GetDashboardOrders(orders);
                var order = dashboardOrders.Where(o => o.Id == orderId).FirstOrDefault();

                if (order != null)
                {
                    return PartialView("_OrderDetails", order);
                }
            }

            return PartialView("_OrderDetails", null);
        }

        private List<OrderDetailsVM> GetDashboardOrders(List<Order> orders)
        {
            var dashboardOrders = new List<OrderDetailsVM>();
            foreach (var order in orders)
            {
                var dashboardOrder = new OrderDetailsVM
                {
                    Id = order.Id,
                    Address = order.Address,
                    CurrentEmployee = order.CurrentEmployee,
                    Customer = order.Customer,
                    OrderedDishes = order.OrderedDishes,
                    Status = order.Status,
                    SubmittedAt = order.SubmittedAt
                };
                if (dashboardOrder?.OrderedDishes != null)
                {
                    foreach (var dish in dashboardOrder?.OrderedDishes)
                    {
                        dashboardOrder.TotalCost += dish.Dish.Cost;
                    }
                }

                dashboardOrders.Add(dashboardOrder);
            }

            return dashboardOrders;
        }
    }
}