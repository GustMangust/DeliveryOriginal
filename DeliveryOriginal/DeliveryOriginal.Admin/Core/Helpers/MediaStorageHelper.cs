using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Threading.Tasks;

namespace DeliveryOriginal.Admin.Core.Helpers
{
    public static class MediaStorageHelper
    {

        static MediaStorageHelper()
        {
        }

        public static async Task<string> UploadToCloudStorageAsync(string imageString)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "GetImageUrl";

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            var dictionary = new Dictionary<string, string>
            {
                { "ImageString", imageString }
            };

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(dictionary);
                streamWriter.Write(json);
            }

            var response = await httpWebRequest.GetResponseAsync();
            return ReadStreamFromResponse(response);
        }

        public static async Task DeleteFromCloudStorageAsync(string imageUrl)
        {
            var apiRoute = DeliveryOriginalSettings.ApiUrl + "DeleteImage";

            var httpWebRequest = (HttpWebRequest)WebRequest.Create(apiRoute);
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "DELETE";

            var dictionary = new Dictionary<string, string>
            {
                { "ImageUrl", imageUrl }
            };

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = JsonConvert.SerializeObject(dictionary);
                streamWriter.Write(json);
            }

            await httpWebRequest.GetResponseAsync();
        }

        private static string ReadStreamFromResponse(WebResponse response)
        {
            using (Stream responseStream = response.GetResponseStream())
            using (StreamReader sr = new StreamReader(responseStream))
            {
                string strContent = sr.ReadToEnd();
                return strContent;
            }
        }

    }
}