using DeliveryOriginal.Admin.Core.Interfaces;
using DeliveryOriginal.Admin.Models;
using Newtonsoft.Json;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Core.Repositories
{
    public class OrderRepository : IRepository<Order>
    {
        public OrderRepository()
        { }

        public async Task Insert(Order entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "Order/Add";

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
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "Order/Delete?Id=" + id;

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "DELETE";

            await httpWebRequest.GetResponseAsync();
        }

        public async Task Update(Order entity)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "Order/Update";

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

        public async Task<Order> Get(int id)
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "Order/Get?Id=" + id;
                var responseString = await client.GetStringAsync(apiRoute);
                return JsonConvert.DeserializeObject<Order>(responseString);
            }
        }

        public async Task<List<Order>> GetAll()
        {
            using (HttpClient client = new HttpClient())
            {
                var apiRoute = DeliveryOriginalSettings.ApiUrl + "Order/GetAll";
                var responseString = await client.GetStringAsync(apiRoute);
                return JsonConvert.DeserializeObject<List<Order>>(responseString);
            }
        }

    }
}