using System.Collections.Generic;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Core.Interfaces
{
    public interface IRepository<T> where T : class
    {
        Task Insert(T entity);
        Task Delete(int id);
        Task Update(T entity);
        T Get(int id);
        Task<List<T>> GetAll();
    }
}
