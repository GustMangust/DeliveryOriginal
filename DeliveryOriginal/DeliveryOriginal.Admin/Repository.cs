using DeliveryOriginal.DAL.Interfaces;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;

namespace DeliveryOriginal.DAL
{
    public class Repository<T> : IRepository<T>
        where T : class
    {
        internal DbContext Context;
        public Repository(DbContext context)
        {
            Context = context;
        }

        public void Insert(T entity)
        {
            if (entity != null)
            {
                // add entity to database
            }
            else
            {
                throw new ArgumentNullException();
            }
        }

        public void Delete(int id)
        {
            var entity = id; // find entity by id at database
            if (entity != null)
            {
                // remove entity from database
            }
            else
            {
                throw new ArgumentNullException();
            }
        }

        public void Update(T entity)
        {
            if (entity != null)
            {
                // update entity at database
                Context.Entry(entity).State = EntityState.Modified;
            }
            else
            {
                throw new ArgumentNullException();
            }
        }

        public T Get(int id)
        {
            // find entity by id at database
            return null;
        }

        public IQueryable<T> GetAll()
        {
            // get all entities of current class from database
            return null;
        }
    }

}
