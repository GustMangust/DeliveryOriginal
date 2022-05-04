using DeliveryOriginal.Admin.Core.Repositories;
using DeliveryOriginal.Admin.Models;

namespace DeliveryOriginal.Admin.Core.Interfaces
{
    public interface IUnitOfWork
    {
        IRepository<User> UserRepository { get; }
        DishRepository DishRepository { get; }
        IRepository<Category> CategoryRepository { get; }
        IRepository<Order> OrderRepository { get; }
    }
}
