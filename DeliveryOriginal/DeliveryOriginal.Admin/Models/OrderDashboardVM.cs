using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DeliveryOriginal.Admin.Models
{
    public class OrderDashboardVM
    {
        public List<OrderDetailsVM> Orders { get; set; }
        public OrderDetailsVM SelectedOrder { get; set; }
        public OrderOrderBy OrderOrderBy { get; set; }
    }
}