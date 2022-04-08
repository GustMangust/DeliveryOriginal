using DeliveryOriginal.Admin.Models;

namespace DeliveryOriginal.Admin.Core.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        IRepository<Dish> DishRepository { get; }
        IRepository<OrderedDish> OrderedDishRepository { get; }
        IRepository<Order> OrderRepository { get; }
    }
}
