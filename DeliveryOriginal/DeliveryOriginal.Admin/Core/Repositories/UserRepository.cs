using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Core.Repositories
{
    public class UserRepository : IRepository<User>
    {
        public UserRepository()
        { }

        public async Task Insert(User entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Add";

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(entity);
                streamWriter.Write(json);
            }

            await httpWebRequest.GetResponseAsync();
        }

        public async Task Delete(int id)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Delete?Id=" + id;

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "DELETE";

            await httpWebRequest.GetResponseAsync();
        }

        public async Task Update(User entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Update";

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "PUT";

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(entity);
                streamWriter.Write(json);
            }

            await httpWebRequest.GetResponseAsync();
        }

        public async Task<User> Get(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/Get?Id=" + id;
                var responseString = await client.GetStringAsync(apiRoute);
                return JsonConvert.DeserializeObject<User>(responseString);
            }
        }

        public async Task<List<User>> GetAll()
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "User/GetAll";
                var responseString = await client.GetStringAsync(apiRoute);
                return JsonConvert.DeserializeObject<List<User>>(responseString);
            }
        }
    }

}
