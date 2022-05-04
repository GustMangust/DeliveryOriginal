using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using DeliveryOriginal.Admin.Core.Repositories;
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

        private DishRepository _dishRepository;
        public DishRepository DishRepository => _dishRepository ?? (_dishRepository = new DishRepository());

        private CategoryRepository _categoryRepository;
        public IRepository<Category> CategoryRepository => _categoryRepository ?? (_categoryRepository = new CategoryRepository());

        private OrderRepository _orderRepository;
        public IRepository<Order> OrderRepository => _orderRepository ?? (_orderRepository = new OrderRepository());

    }

}
