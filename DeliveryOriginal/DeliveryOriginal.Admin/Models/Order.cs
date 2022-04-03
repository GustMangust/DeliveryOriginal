using System;
using System.Collections.Generic;

namespace DeliveryOriginal.Admin.Models
{
    public class Order
    {
        public int Id { get; set; }
        public string Status { get; set; }
        public DateTime Date { get; set; }
        public string Address { get; set; }
        public virtual User Customer { get; set; }
        public virtual User CurrentEmployee { get; set; }
        public virtual ICollection<OrderedDish> OrderedDishes { get; set; }
    }
}
