using System.Data.Entity;
using DeliveryOriginal.DAL.Models;

namespace DeliveryOriginal.DAL
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext()
            : base("DefaultConnection")
        {
            Configuration.ProxyCreationEnabled = true;
            Configuration.LazyLoadingEnabled = true;
        }

        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }

        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<Dish> Dish { get; set; }
        public virtual DbSet<OrderedDish> OrderedDish { get; set; }
        public virtual DbSet<Order> Order { get; set; }
    }
}
