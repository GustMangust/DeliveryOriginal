using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DeliveryOriginal.DAL.Interfaces
{
    public interface IRepository<T> where T : class
    {
        void Insert(T entity);
        void Delete(int id);
        void Update(T entity);
        T Get(int id);
        IQueryable<T> GetAll();
        IQueryable<T> GetAll(params string[] navigationProperties);
        void DeleteRange(IEnumerable<T> items);
        void CreateRange(IEnumerable<T> items);
    }
}
