using DeliveryOriginal.Admin.Interfaces;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Repositories
{
    public class OrderedDishRepository : IRepository<OrderedDish>
    {
        public OrderedDishRepository()
        { }

        public async Task Insert(OrderedDish entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "OrderedDish/Add";

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
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "OrderedDish/Delete";

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "DELETE";

            var values = new Dictionary<string, string>
            {
                { "Id", id.ToString() }
            };

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(values);
                streamWriter.Write(json);
            }

            await httpWebRequest.GetResponseAsync();
        }

        public async Task Update(OrderedDish entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "OrderedDish/Update";

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

        public OrderedDish Get(int id)
        {
            // find entity by id at database
            return null;
        }

        public async Task<List<OrderedDish>> GetAll()
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "OrderedDish/Get";
                var responseString = await client.GetStringAsync(apiRoute);
                return JsonConvert.DeserializeObject<List<OrderedDish>>(responseString);
            }
        }
    }
}