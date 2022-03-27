using DeliveryOriginal.DAL.Models;
using System.Threading.Tasks;

namespace DeliveryOriginal.DAL.Interfaces
{
    public interface IUnitOfWork
    {
        void Commit();
        Task CommitAsync();
        IRepository<User> UserRepository { get; }
        IRepository<Dish> DishRepository { get; }
        IRepository<OrderedDish> OrderedDishRepository { get; }
        IRepository<Order> OrderRepository { get; }
    }
}
