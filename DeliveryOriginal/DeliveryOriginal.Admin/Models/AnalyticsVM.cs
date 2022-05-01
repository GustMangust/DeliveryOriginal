using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace DeliveryOriginal.Admin.Models
{
    public class AnalyticsVM
    {
        public Dictionary<object, int> OrdersByMonth { get; set; }
        public Dictionary<object, decimal> OrdersIncomeByMonth { get; set; }
    }
}