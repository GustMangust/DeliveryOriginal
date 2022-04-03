using DeliveryOriginal.Admin.Models;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        //IRepository<Dish> DishRepository { get; }
        //IRepository<OrderedDish> OrderedDishRepository { get; }
        //IRepository<Order> OrderRepository { get; }
    }
}
