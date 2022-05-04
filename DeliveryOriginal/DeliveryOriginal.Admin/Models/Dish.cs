using System.Collections.Generic;

namespace DeliveryOriginal.Admin.Models
{
    public class Dish
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public Category Category { get; set; }
        public string Description { get; set; }
        public string ImageUrl { get; set; }
        public decimal Cost { get; set; }
    }

    public class DishVM : Dish
    {
        public int CategoryId { get; set; }
    }

    public class TopDishVM
    {
        public string Name { get; set; }
        public int Value { get; set; }
    }
}
