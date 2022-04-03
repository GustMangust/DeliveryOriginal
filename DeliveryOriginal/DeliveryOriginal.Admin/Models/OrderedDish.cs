namespace DeliveryOriginal.Admin.Models
{
    public class OrderedDish
    {
        public int Id { get; set; }
        public virtual Dish Dish { get; set; }
        public virtual Order Order { get; set; }
    }
}
