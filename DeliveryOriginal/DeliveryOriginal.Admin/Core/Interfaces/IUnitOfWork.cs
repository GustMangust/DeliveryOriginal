using DeliveryOriginal.Admin.Models;

namespace DeliveryOriginal.Admin.Core.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        IRepository<Dish> DishRepository { get; }
        IRepository<Category> CategoryRepository { get; }
        IRepository<Order> OrderRepository { get; }
    }
}
