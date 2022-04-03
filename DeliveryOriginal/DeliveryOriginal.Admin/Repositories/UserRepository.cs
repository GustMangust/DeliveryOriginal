using DeliveryOriginal.Admin.Interfaces;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin
{
    public class UserRepository : IRepository<User>
    {
        public UserRepository()
        { }

        public async Task Insert(User entity)
        {
            if (entity != null)
            {
                using (HttpClient client = new HttpClient())
                {
                    var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Add";

                    var values = new Dictionary<string, string>
                    {
                        { "Login", entity.Login },
                        { "Password", entity.Password },
                        { "FullName", entity.FullName },
                        { "Role", entity.Role }
                    };

                    var content = new FormUrlEncodedContent(values);

                    var response = await client.PostAsync(apiRoute, content);
                }
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

        public void Update(User entity)
        {
            if (entity != null)
            {
                // update entity at database
            }
            else
            {
                throw new ArgumentNullException();
            }
        }

        public User Get(int id)
        {
            // find entity by id at database
            return null;
        }

        public async Task<List<User>> GetAll()
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Get";
                var responseString = await client.GetStringAsync(apiRoute);
                var users = JsonConvert.DeserializeObject<List<User>>(responseString);
                return users;
            }
        }
    }

}
