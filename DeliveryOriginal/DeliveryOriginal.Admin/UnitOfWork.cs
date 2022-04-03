using DeliveryOriginal.Admin.Interfaces;
using DeliveryOriginal.Admin.Models;
using System.Data.Entity;
using System.Data.Entity.Validation;
using System.Diagnostics;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin
{
    public class UnitOfWork : IUnitOfWork
    {
        public UnitOfWork()
        { }

        private UserRepository _userRepository;
        public IRepository<User> UserRepository => _userRepository ?? (_userRepository = new UserRepository());

        //private Repository<Dish> _dishRepository;
        //public IRepository<Dish> DishRepository => _dishRepository ?? (_dishRepository = new Repository<Dish>(Context));

        //private Repository<OrderedDish> _orderedDishRepository;
        //public IRepository<OrderedDish> OrderedDishRepository => _orderedDishRepository ?? (_orderedDishRepository = new Repository<OrderedDish>(Context));

        //private Repository<Order> _orderRepository;
        //public IRepository<Order> OrderRepository => _orderRepository ?? (_orderRepository = new Repository<Order>(Context));

    }

}
