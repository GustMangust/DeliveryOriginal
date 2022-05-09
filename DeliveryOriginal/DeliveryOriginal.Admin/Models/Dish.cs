using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace DeliveryOriginal.Admin.Models
{
    public class Dish
    {
        public int Id { get; set; }
        [Required]
        public string Name { get; set; }
        public Category Category { get; set; }
        [Required]
        public string Description { get; set; }
        public string ImageUrl { get; set; }
        [Required]
        [Range(0, 9999999999999999.99)]
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
