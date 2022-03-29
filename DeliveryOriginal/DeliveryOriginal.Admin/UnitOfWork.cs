using DeliveryOriginal.DAL.Interfaces;
using DeliveryOriginal.DAL.Models;
using System.Data.Entity;
using System.Data.Entity.Validation;
using System.Diagnostics;
using System.Threading.Tasks;

namespace DeliveryOriginal.DAL
{
    public class UnitOfWork : IUnitOfWork
    {
        public UnitOfWork(DbContext context)
        {
            Context = context;
        }

        private DbContext Context { get; set; }

        public void Commit()
        {
            try
            {
                Context.SaveChanges();
            }
            catch (DbEntityValidationException dbEx)
            {
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        Trace.TraceInformation("Property: {0} Error: {1}", validationError.PropertyName, validationError.ErrorMessage);
                    }
                }
            }
        }

        public async Task CommitAsync()
        {
            try
            {
                await Context.SaveChangesAsync();
            }
            catch (DbEntityValidationException dbEx)
            {
                foreach (var validationErrors in dbEx.EntityValidationErrors)
                {
                    foreach (var validationError in validationErrors.ValidationErrors)
                    {
                        Trace.TraceInformation("Property: {0} Error: {1}", validationError.PropertyName, validationError.ErrorMessage);
                    }
                }
            }
        }

        private Repository<User> _userRepository;
        public IRepository<User> UserRepository => _userRepository ?? (_userRepository = new Repository<User>(Context));

        private Repository<Dish> _dishRepository;
        public IRepository<Dish> DishRepository => _dishRepository ?? (_dishRepository = new Repository<Dish>(Context));

        private Repository<OrderedDish> _orderedDishRepository;
        public IRepository<OrderedDish> OrderedDishRepository => _orderedDishRepository ?? (_orderedDishRepository = new Repository<OrderedDish>(Context));

        private Repository<Order> _orderRepository;
        public IRepository<Order> OrderRepository => _orderRepository ?? (_orderRepository = new Repository<Order>(Context));

    }

}
