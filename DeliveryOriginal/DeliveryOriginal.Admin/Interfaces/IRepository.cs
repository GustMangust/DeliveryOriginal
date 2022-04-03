using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Interfaces
{
    public interface IRepository<T> where T : class
    {
        Task Insert(T entity);
        void Delete(int id);
        void Update(T entity);
        T Get(int id);
        Task<List<T>> GetAll();
    }
}
