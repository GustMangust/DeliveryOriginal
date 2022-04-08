using System;
using System.Collections.Generic;

namespace DeliveryOriginal.Admin.Models
{
    public class Order
    {
        public int Id { get; set; }
        public DateTime? SubmittedAt { get; set; }
        public OrderStatus Status { get; set; }
        public string Address { get; set; }
        public virtual User Customer { get; set; }
        public virtual User CurrentEmployee { get; set; }
        public virtual ICollection<OrderedDish> OrderedDishes { get; set; }
    }

    public class OrderDetailsVM : Order
    {
        public decimal TotalCost { get; set; }
    }

    public class OrderDashboardVM
    {
        public List<OrderDetailsVM> Orders { get; set; }
        public OrderDetailsVM SelectedOrder { get; set; }
        public OrderOrderBy OrderOrderBy { get; set; }
    }
}
